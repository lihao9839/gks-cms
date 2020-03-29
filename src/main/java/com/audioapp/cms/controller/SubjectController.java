package com.audioapp.cms.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.audioapp.cms.constant.ResultEnum;
import com.audioapp.cms.constant.UserConstant;
import com.audioapp.cms.dto.*;
import com.audioapp.cms.service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.mapper.SubjectDTOMapper;
import com.audioapp.cms.service.SubjectService;
import com.audioapp.cms.service.UserService;

/**
 * 专栏管理
 * @author Administrator
 *
 */
@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SubjectDTOMapper subjectMapper;
	
	@Autowired
	private UserService userService;

	@Autowired
	private HtmlService htmlService;
	
	/**
	 * 跳转专栏列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSubject")
	public String goSubject(HttpServletRequest request, Model model){
		//查询用户信息
		UserDTO loginUser = userService.getLoginUserInfo(request);
		Map<String, Object> paramMap = new HashMap<>();
		//如果是讲师，则只能看到自己的专栏
		if(UserConstant.USER_ROLE_TYPE_TEACH.equals(loginUser.getRoleType())) {
			paramMap.put("teacherId", loginUser.getId());
		}
		List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);
		model.addAttribute("subjectList", subjectList);
		model.addAttribute("roleType",loginUser.getRoleType());
		return "subject_list";
	}
	
	/**
	 * 查询专栏列表
	 * @param request
	 * @return
	 */
	public Map<String, Object> getSubjectList(HttpServletRequest request){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("subjectName", request.getParameter("subjectName"));
		paramMap.put("states", request.getParameter("states"));
		List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);
		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("subjectList", subjectList);
		//添加用户角色
		UserDTO loginUser = userService.getLoginUserInfo(request);
		resultData.put("roleType",loginUser.getRoleType());
		return ResultHelper.succMap(resultData);
	}

	/**
	 * 跳转新增专栏页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddSubject")
	public String goAddSubject(HttpServletRequest request, Model model){
		//权限控制：讲师权限，不能修改订阅价格、分享得免费课程
		UserDTO loginUser = userService.getLoginUserInfo(request);
		model.addAttribute("roleType",loginUser.getRoleType());
		Map<String, Object> paramMap = new HashMap<>();
		if(UserConstant.USER_ROLE_TYPE_TEACH.equals(loginUser.getRoleType())){
			paramMap.put("userName", loginUser.getUserName());
		}
		List<UserListDTO> userList = userService.getUserList(paramMap);
		model.addAttribute("userList", userList);
		return "subject_add";
	}

	/**
	 * 新增专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/addSubject")
	@ResponseBody
	public Map<String, Object> addSubject(HttpServletRequest request){
		try {
			SubjectDTO subject = this.getSubject(request);
			if (subjectService.addSubject(subject)) {
				return ResultHelper.succMap();
			}
			return ResultHelper.errMap();
		}catch (Exception e){
			e.printStackTrace();
			return ResultHelper.errMap();
		}
	}
	
	/**
	 * 专栏详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/goSubjectDetail")
	public String goSubjectDetail(HttpServletRequest request, Model model){
		long id = Long.valueOf(request.getParameter("id"));
		SubjectDetailDTO subject = subjectService.getSubjectDetail(id);
		model.addAttribute("subject", subject);
		return "subject_detail";
	}
	
	/**
	 * 跳转编辑专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/goEditSubject")
	public String goEditSubject(HttpServletRequest request, Model model){
		//权限控制：讲师权限，不能修改订阅价格、分享得免费课程
		UserDTO loginUser = userService.getLoginUserInfo(request);
		model.addAttribute("roleType",loginUser.getRoleType());
		//查询专栏信息
		long id = Long.valueOf(request.getParameter("id"));
		SubjectDetailDTO subject = subjectService.getSubjectDetail(id);
		model.addAttribute("subject", subject);
		//查询讲师列表
		Map<String, Object> paramMap = new HashMap<>();
		List<UserListDTO> userList = userService.getUserList(paramMap);
		model.addAttribute("userList", userList);
		return "subject_edit";
	}
	
	/**
	 * 编辑专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/editSubject")
	@ResponseBody
	public Map<String, Object> editSubject(HttpServletRequest request){
		try {
			SubjectDTO subject = this.getSubject(request);
			if (subjectMapper.updateByPrimaryKeySelective(subject) > 0) {
				return ResultHelper.succMap();
			}
			return ResultHelper.errMap();
		}catch(Exception e){
			e.printStackTrace();
			return ResultHelper.errMap();
		}
	}
	
	/**
	 * 撤回/退回专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/rollbackSubject")
	@ResponseBody
	public Map<String, Object> rollbackSubject(HttpServletRequest request){
		SubjectDTO subject = new SubjectDTO();
		subject.setId(Long.valueOf(request.getParameter("id")));
		subject.setStates("0");
		subjectMapper.updateByPrimaryKeySelective(subject);
		return ResultHelper.succMap();
	}
	
	/**
	 * 发布专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/publishSubject")
	@ResponseBody
	public Map<String, Object> publishSubject(HttpServletRequest request){
		Long subjectId = Long.valueOf(request.getParameter("id"));
		//校验专栏金额
		SubjectDetailDTO subjectDetailDTO = subjectMapper.selectDetailByPrimaryKey(subjectId);
		if(subjectDetailDTO.getPrice() <= 0){
			return ResultHelper.errMap(ResultEnum.PRICE_NULL);
		}
		SubjectDTO subject = new SubjectDTO();
		subject.setId(subjectId);
		subject.setStates("1");
		subject.setPublishDate(new Date());
		subjectMapper.updateByPrimaryKeySelective(subject);
		return ResultHelper.succMap();
	}
	
	/**
	 * 提交/下架专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/submitSubject")
	@ResponseBody
	public Map<String, Object> submitSubject(HttpServletRequest request){
		SubjectDTO subject = new SubjectDTO();
		subject.setId(Long.valueOf(request.getParameter("id")));
		subject.setStates("2");
		subjectMapper.updateByPrimaryKeySelective(subject);
		return ResultHelper.succMap();
	}
	
	/**
	 *  删除专栏
	 * @param request
	 * @return
	 */
	@RequestMapping("/clearSubject")
	@ResponseBody
	public Map<String, Object> clearSubject(HttpServletRequest request){
		SubjectDTO subject = new SubjectDTO();
		subject.setId(Long.valueOf(request.getParameter("id")));
		subject.setStates("3");
		subjectMapper.updateByPrimaryKeySelective(subject);
		return ResultHelper.succMap();
	}
	
	private SubjectDTO getSubject(HttpServletRequest request){
		SubjectDTO subject = new  SubjectDTO();
		if(null != request.getParameter("id")) {
			subject.setId(Long.valueOf(request.getParameter("id")));
		}
		subject.setSubjectName(request.getParameter("subjectName"));
		subject.setTeacherId(Long.valueOf(request.getParameter("teacherId")));
		subject.setTeacherInterduce(request.getParameter("userIntroduce"));
		if(StringUtils.isEmpty(request.getParameter("subscribePrice"))){
			subject.setPrice(0D);
		}else {
			BigDecimal price = new BigDecimal(request.getParameter("subscribePrice"));
			price = price.multiply(new BigDecimal(100));
			subject.setPrice(price.doubleValue());//扩大100倍入库
		}
		subject.setPicUrl(request.getParameter("subjectPicUrl"));
		subject.setSubjectIntroduce(request.getParameter("subjectIntroduce"));
		subject.setSubjectIntroduceDetail(htmlService.handleHtmlImg(request.getParameter("subjectIntroduceDetail")));
		subject.setFreeCourseFlag(request.getParameter("freeCourseFlag"));
		UserDTO loginUser = userService.getLoginUserInfo(request);
		if(subject.getId() == null) {
			subject.setCreatedBy(loginUser.getUserName());
			subject.setCreatedDate(new Date());
		}
		subject.setUpdatedBy(loginUser.getUserName());
		subject.setUpdatedDate(new Date());
		return subject;
	}
}

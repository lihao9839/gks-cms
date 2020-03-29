package com.audioapp.cms.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.audioapp.cms.constant.ResultEnum;
import com.audioapp.cms.constant.UserConstant;
import com.audioapp.cms.dto.*;
import com.audioapp.cms.service.HtmlService;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.mapper.CourseDTOMapper;
import com.audioapp.cms.service.CourseService;
import com.audioapp.cms.service.SubjectService;
import com.audioapp.cms.service.UserService;

/**
 * 课程管理
 * @author Administrator
 *
 */
@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseDTOMapper courseMapper;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private UserService userService;

	@Autowired
	private HtmlService htmlService;

	@RequestMapping("/goCourse")
	public String goCourse(HttpServletRequest request, Model model) {
		//查询条件
		String searchSubjectId = request.getParameter("searchSubjectId");
		String searchSubjectName = request.getParameter("searchSubjectName");
		model.addAttribute("searchSubjectId", searchSubjectId);

		//查询用户信息
		UserDTO loginUser = userService.getLoginUserInfo(request);
		Map<String, Object> paramMap = new HashMap<>();
		if (UserConstant.USER_ROLE_TYPE_TEACH.equals(loginUser.getRoleType())) {
			paramMap.put("teacherId", loginUser.getId());
		}
		if (!StringUtils.isEmpty(searchSubjectId)) {
			paramMap.put("subjectId", searchSubjectId);
		}
		List<CourseListDTO> courseList = courseService.getCourseList(paramMap);
		model.addAttribute("courseList", courseList);
		model.addAttribute("roleType", loginUser.getRoleType());
		//查询专栏列表
		List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);

		//补充专栏名称搜索条件
		if (StringUtils.isEmpty(searchSubjectName)) {
			for (SubjectListDTO dto : subjectList) {
				if (String.valueOf(dto.getId()).equals(searchSubjectId)) {
					searchSubjectName = dto.getSubjectName();
				}
			}
		}
		model.addAttribute("searchSubjectName", searchSubjectName);
		model.addAttribute("subjectList", subjectList);
		return "course_list";
	}

	/**
	 * 跳转新增课程页面
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddCourse")
	public String goAddCourse(HttpServletRequest request, Model model) {
		//权限控制：讲师权限，不能修改可试听
		UserDTO loginUser = userService.getLoginUserInfo(request);
		model.addAttribute("roleType", loginUser.getRoleType());
		//查询条件
		String searchSubjectId = request.getParameter("searchSubjectId");
		model.addAttribute("searchSubjectId", searchSubjectId);
		Map<String, Object> paramMap = new HashMap<>();
		if (UserConstant.USER_ROLE_TYPE_TEACH.equals(loginUser.getRoleType())) {
			paramMap.put("teacherId", loginUser.getId());
		}
		List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);
		model.addAttribute("subjectList", subjectList);
		return "course_add";
	}

	/**
	 * 新增课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addCourse")
	@ResponseBody
	public Map<String, Object> addCourse(HttpServletRequest request) {
		try {
			CourseDTO course = this.getCourse(request);
			if (course.getCourseType().equals("1") && StringUtils.isEmpty(course.getAudioUrl())) {
				return ResultHelper.errMap(ResultEnum.PIC_URL_NULL);
			}
			if (courseService.addCourse(course)) {
				return ResultHelper.succMap();
			}
			return ResultHelper.errMap();
		}catch (Exception e){
			e.printStackTrace();
			return ResultHelper.errMap();
		}
	}

	/**
	 * 课程详情
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/goCourseDetail")
	public String goCourseDetail(HttpServletRequest request, Model model) {
		//查询条件
		String searchSubjectId = request.getParameter("searchSubjectId");
		model.addAttribute("searchSubjectId", searchSubjectId);
		long id = Long.valueOf(request.getParameter("id"));
		CourseDetailDTO course = courseMapper.selectByPrimaryKey(id);
		model.addAttribute("course", course);
		Map<String, Object> paramMap = new HashMap<>();
		List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);
		model.addAttribute("subjectList", subjectList);
		return "course_detail";
	}


	/**
	 * 编辑课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/goEditCourse")
	public String goEditCourse(HttpServletRequest request, Model model) {
		//权限控制：讲师权限，不能修改可试听
		UserDTO loginUser = userService.getLoginUserInfo(request);
		model.addAttribute("roleType", loginUser.getRoleType());
		//查询条件
		String searchSubjectId = request.getParameter("searchSubjectId");
		model.addAttribute("searchSubjectId", searchSubjectId);
		Long courseId = Long.valueOf(request.getParameter("id"));
		CourseDetailDTO course = courseMapper.selectByPrimaryKey(courseId);
		model.addAttribute("course", course);
		Map<String, Object> paramMap = new HashMap<>();
		List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);
		model.addAttribute("subjectList", subjectList);
		return "course_edit";
	}


	/**
	 * 更新课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateCourse")
	@ResponseBody
	public Map<String, Object> updateCourse(HttpServletRequest request) {
		try {
			CourseDTO course = this.getCourse(request);
			if (courseMapper.updateByPrimaryKeySelective(course) > 0) {
				return ResultHelper.succMap();
			}
			return ResultHelper.errMap();
		}catch (Exception e){
			e.printStackTrace();
			return ResultHelper.errMap();
		}
	}

	/**
	 * 发布课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/publishCourse")
	@ResponseBody
	public Map<String, Object> publishCourse(HttpServletRequest request) {
		CourseDTO course = new CourseDTO();
		course.setId(Long.valueOf(request.getParameter("id")));
		course.setStatus("1");
		course.setPublishDate(new Date());
		courseMapper.updateByPrimaryKeySelective(course);
		return ResultHelper.succMap();
	}

	/**
	 * 删除课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCourse")
	@ResponseBody
	public Map<String, Object> deleteCourse(HttpServletRequest request) {
		CourseDTO course = new CourseDTO();
		course.setId(Long.valueOf(request.getParameter("id")));
		course.setStatus("2");
		course.setPublishDate(null);
		courseMapper.updateByPrimaryKeySelective(course);
		return ResultHelper.succMap();
	}

	/**
	 * 下架课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/offlineCourse")
	@ResponseBody
	public Map<String, Object> offlineCourse(HttpServletRequest request) {
		CourseDTO course = new CourseDTO();
		course.setId(Long.valueOf(request.getParameter("id")));
		course.setStatus("0");
		course.setPublishDate(null);
		courseMapper.updateByPrimaryKeySelective(course);
		return ResultHelper.succMap();
	}

	/**
	 * 恢复课程
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/fallbackCourse")
	@ResponseBody
	public Map<String, Object> fallbackCourse(HttpServletRequest request) {
		CourseDTO course = new CourseDTO();
		course.setId(Long.valueOf(request.getParameter("id")));
		course.setStatus("0");
		courseMapper.updateByPrimaryKeySelective(course);
		return ResultHelper.succMap();
	}

	private CourseDTO getCourse(HttpServletRequest request) {
		CourseDTO course = new CourseDTO();
		if (null != request.getParameter("id")) {
			course.setId(Long.valueOf(request.getParameter("id")));
		}
		course.setSubjectId(Long.valueOf(request.getParameter("subjectId")));
		course.setCourseName(request.getParameter("courseName"));
		course.setCourseType(request.getParameter("courseType"));
		course.setFreeFlag(request.getParameter("freeFlag"));
		course.setPicUrl(request.getParameter("picUrl"));
		course.setAudioUrl(request.getParameter("audioUrl"));
		course.setAudioLength(this.getAudioLength(course.getAudioUrl()));
		course.setCourseContent(htmlService.handleHtmlImg(request.getParameter("courseContent")));
		UserDTO user = userService.getLoginUserInfo(request);
		if (null == course.getId()) {
			course.setCreatedBy(user.getUserName());
			course.setCreatedDate(new Date());
		}
		course.setUpdatedBy(user.getUserName());
		course.setUpdatedDate(new Date());
		if (null != request.getParameter("sort")) {
			course.setSort(Integer.valueOf(request.getParameter("sort")));
		}
		return course;
	}

	/**
	 * 获取音频播放长度
	 * @param url
	 * @return
	 */
	private String getAudioLength(String url){
		try {
			URL urlFile = new URL(url);
			URLConnection con = null;
			try {
				con = urlFile.openConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int b = con.getContentLength();
			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			Bitstream bt = new Bitstream(bis);
			Header h = bt.readFrame();
			int time = (int) h.total_ms(b);
			int min = time / 1000 / 60;
			int sec = time / 1000 % 60;
			return min + ":" + sec;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "4:00";
	}
}

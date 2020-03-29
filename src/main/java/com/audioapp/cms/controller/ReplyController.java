package com.audioapp.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.audioapp.cms.constant.ResultEnum;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.audioapp.cms.dto.ReplyListDTO;
import com.audioapp.cms.service.ReplyService;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.audioapp.cms.constant.UserConstant.USER_ROLE_TYPE_TEACH;

/**
 * 评论管理
 * @author Administrator
 *
 */
@Controller
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	@Autowired
	private UserService userService;

	
	@RequestMapping("/goSubjectReply")
    public String goSubjectReply(HttpServletRequest request, Model model){
		Map<String, Object> paramMap = new HashMap<>();
		//用户信息
		UserDTO loginUser = userService.getLoginUserInfo(request);
    	if(USER_ROLE_TYPE_TEACH.equals(loginUser.getRoleType())){
			paramMap.put("teacherId", loginUser.getId());
		}
		List<ReplyListDTO> replyList = replyService.getSubjectReplayList(paramMap);
    	model.addAttribute("replyList", replyList);
    	return "reply_subject_list";
    }
	
	@RequestMapping("/goCourseReply")
    public String goCourseReply(HttpServletRequest request, Model model){
		//用户信息
		UserDTO loginUser = userService.getLoginUserInfo(request);
		Map<String, Object> paramMap = new HashMap<>();
		if(USER_ROLE_TYPE_TEACH.equals(loginUser.getRoleType())){
			paramMap.put("userName", loginUser.getUserName());
		}
    	List<ReplyListDTO> replyList = replyService.getCourseReplayList(paramMap);
    	model.addAttribute("replyList", replyList);
    	return "reply_course_list";
    }
	
	@RequestMapping("/goUserAdvice")
    public String goUserAdvice(HttpServletRequest request, Model model){
		Map<String, Object> paramMap = new HashMap<>();
    	List<ReplyListDTO> replyList = replyService.getUserReplayList(paramMap);
    	model.addAttribute("replyList", replyList);
    	return "reply_advice_list";
    }

	/**
	 * 评论
	 * @param request
	 * @return
	 */
	@RequestMapping("/reply/reply")
	@ResponseBody
	public Map<String, Object> reply(HttpServletRequest request){
		Map<String, Object> paramMap = new HashMap<>();
		long id = Long.valueOf(request.getParameter("replyId"));
		String teacherReply = request.getParameter("teacherReply");
		if(replyService.reply(id, teacherReply) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}

	/**
	 * 撤回
	 * @param request
	 * @return
	 */
	@RequestMapping("/reply/replyBack")
	@ResponseBody
	public Map<String, Object> replyBack(HttpServletRequest request){
		long id = Long.valueOf(request.getParameter("id"));
		if(replyService.replyBack(id) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}

	/**
	 * 发布该评论
	 * @param request
	 * @return
	 */
	@RequestMapping("/reply/publish")
	@ResponseBody
	public Map<String, Object> publish(HttpServletRequest request){
		long id = Long.valueOf(request.getParameter("id"));
		if(replyService.publishReply(id) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}

	/**
	 * 屏蔽
	 * @param request
	 * @return
	 */
	@RequestMapping("/reply/recall")
	@ResponseBody
	public Map<String, Object> recall(HttpServletRequest request, Model model){
		long id = Long.valueOf(request.getParameter("id"));
		if(replyService.recallReply(id) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}
	
}

package com.audioapp.cms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.audioapp.cms.constant.ResultEnum;
import com.audioapp.cms.dto.MessageDTO;
import com.audioapp.cms.dto.MessageListDTO;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.mapper.MessageDTOMapper;
import com.audioapp.cms.service.MessageService;
import com.audioapp.cms.service.UserService;

/**
 * 消息管理
 * @author Administrator
 *
 */
@Controller
public class MessageController extends BaseController{
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageDTOMapper messageMapper;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转消息管理页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goMessage")
	public String goMessage(HttpServletRequest request, Model model){
		Map<String, Object> paramMap = new HashMap<>();
		List<MessageListDTO> messageList = messageService.getMessageList(paramMap);
		model.addAttribute("messageList", messageList);
		return "message_list";
	}
	
	/**
	 * 查询消息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMessageList")
	public String getMessageList(HttpServletRequest request, Model model){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("keyWord", request.getParameter("keyWord"));
		paramMap.put("status", request.getParameter("status"));
		List<MessageListDTO> messageList = messageService.getMessageList(paramMap);
		model.addAttribute("messageList", messageList);
		model.addAttribute("keyWord",request.getParameter("keyWord"));
		model.addAttribute("status",request.getParameter("status"));
		return "message_list";
	}
	
	/**
	 * 跳转新增消息页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goAddMessage")
	public String goAddMessage(HttpServletRequest request, Model model){
		return "message_add";
	}
	
	/**
	 * 新增消息
	 * @param request
	 * @return
	 */
	@PostMapping("/addMessage")
	@ResponseBody
	public Map<String, Object> addMessage(HttpServletRequest request){
		MessageDTO message = this.getMessage(request);
		//校验发送时间
		Date date = new Date();
		if(date.after(message.getSendDate())) {
			return ResultHelper.errMap(ResultEnum.DATETIME_EXPIRED);
		}

		//入库
		UserDTO user = userService.getLoginUserInfo(request);
		message.setCreatedBy(user.getUserName());
		message.setCreatedDate(new Date());
		message.setUpdatedBy(user.getUserName());
		message.setUpdatedDate(new Date());
		message.setStatus("0");//未发送
		if(messageMapper.insert(message) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}
	
	/**
	 * 跳转消息详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/goMessageDetail")
	public String goMessageDetail(HttpServletRequest request, Model model){
		MessageListDTO message = messageMapper.selectListDTOByPrimaryKey(Long.valueOf(request.getParameter("messageId")));
		model.addAttribute("message", message);
		return "message_detail";
	}
	
	/**
	 * 跳转消息修改页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goEditMessage")
	public String goEditMessage(HttpServletRequest request, Model model){
		MessageListDTO message = messageMapper.selectListDTOByPrimaryKey(Long.valueOf(request.getParameter("messageId")));
		model.addAttribute("message", message);
		return "message_edit";
	}
	
	/**
	 * 更新消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateMessage")
	@ResponseBody
	public Map<String, Object> updateMessage(HttpServletRequest request){
		MessageDTO message = this.getMessage(request);
		UserDTO user = userService.getLoginUserInfo(request);
		Date date = new Date();
		if(date.before(message.getSendDate())) {
			return ResultHelper.errMap(ResultEnum.DATETIME_EXPIRED);
		}
		message.setUpdatedBy(user.getUserName());
		message.setUpdatedDate(new Date());
		message.setStatus("0");
		if(messageMapper.updateByPrimaryKeySelective(message) > 0) {
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}
	
	/**
	 * 取消消息
	 * @param request
	 * @return
	 */
	@PostMapping("/cancelMessage")
	@ResponseBody
	public Map<String, Object> cancelMessage(HttpServletRequest request){
		MessageDTO message = new MessageDTO();
		message.setId(Long.valueOf(request.getParameter("messageId")));
		message.setStatus("X");
		if(messageMapper.updateByPrimaryKeySelective(message) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}
	
	/**
	 * 获取消息
	 * @param request
	 * @return
	 */
	private MessageDTO getMessage(HttpServletRequest request){
		MessageDTO message = new MessageDTO();
		message.setTitle(request.getParameter("title"));
		message.setContent(request.getParameter("content"));
		String id = request.getParameter("id");
		String sendDateStr = request.getParameter("sendDate");
		SimpleDateFormat formatter;
		if(!StringUtils.isEmpty(id)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
		}else{
			//首次创建
			formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
		}
		Date sendDate = new Date();
		try{
			sendDate = formatter.parse(sendDateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		message.setSendDate(sendDate);
		message.setAllFlag(request.getParameter("allFlag"));
		if(StringUtils.isEmpty(id)) {
			message.setId(snowflake.nextId());
		}else {
			message.setId(Long.valueOf(id));
		}
		return message;
	}
}

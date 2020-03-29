package com.audioapp.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.audioapp.cms.dto.AppUserListDTO;
import com.audioapp.cms.dto.SubscribeListDTO;
import com.audioapp.cms.service.AppUserService;

@Controller
public class AppUserController {
	
	@Autowired
	private AppUserService appUserService;
	
	@RequestMapping("/goAppUser")
	public String goAppUser(HttpServletRequest request, Model model){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<AppUserListDTO> appUserList = appUserService.getAppUserList(paramMap);
		model.addAttribute("appUserList", appUserList);
		return "app_user_list";
	}
	
	@RequestMapping("/goSubscribList")
	public String  goSubscribList(HttpServletRequest request, Model model){
		String userName = request.getParameter("userName");
		List<SubscribeListDTO> subscribeList = appUserService.getSubscribeList(userName);
		model.addAttribute("subscribeList", subscribeList);
		return "app_user_subscribe_list";
	}
}

package com.audioapp.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.audioapp.cms.dto.MenuDTO;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.service.MenuService;
import com.audioapp.cms.service.UserService;

@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getMenuList")
	public String getMenuList(HttpServletRequest request, Model model){
		UserDTO user = userService.getLoginUserInfo(request);
		List<MenuDTO> menuList = menuService.getMenuList(user.getRoleType());
		model.addAttribute("menuList",menuList);
		return "menu";
	}

}

package com.audioapp.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.audioapp.cms.dto.MenuDTO;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.service.MenuService;
import com.audioapp.cms.service.UserService;

/**
 * 跳转首页
 * @author Administrator
 *
 */
@Controller
public class HomeController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/home")
	public String toHome(HttpServletRequest request, Model model){
		UserDTO loginUser = userService.getLoginUserInfo(request);
		String roleType = loginUser.getRoleType();
		List<MenuDTO> menuList = menuService.getMenuList(roleType);
		model.addAttribute("menuList",menuList);
		model.addAttribute("user",loginUser);
		return "home";
	}
}

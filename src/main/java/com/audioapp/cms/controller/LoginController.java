package com.audioapp.cms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.audioapp.cms.constant.ResultEnum;
import com.audioapp.cms.dto.MenuDTO;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.service.MenuService;
import com.audioapp.cms.service.RedisService;
import com.audioapp.cms.service.UserService;
import com.audioapp.cms.utils.Md5Util;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录控制
 * @author Administrator
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/goLogin")
	public ModelAndView  goLogin(HttpServletRequest reqeust) {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String userName = request.getParameter("userName");
		if(StringUtils.isEmpty(userName)) {
			return ResultHelper.errMap(ResultEnum.USER_NAME_NULL);
		}
		
		String password = request.getParameter("password");
		if(StringUtils.isEmpty(password)) {
			return ResultHelper.errMap(ResultEnum.PASSWORD_NULL);
		}
		
		//1、根据userName查询数据库中存的加密password
		String passwordInDb = userService.getPasswordInDB(userName);
		//2、校验
		if(!StringUtils.isEmpty(passwordInDb) && Md5Util.validPassword(password, passwordInDb)){
			String tokenKey = "CMS_TOKEN_" + userName.toUpperCase();
			//2.1 校验当前是否有已登录用户
			String tokenValue = redisService.get(tokenKey);
			if(StringUtils.isEmpty(tokenValue)) {
				return ResultHelper.errMap(ResultEnum.USER_IS_LOGIN);
			}
			//3、生成cookie，跳转首页
			UserDTO dto = userService.getUserByUserName(userName);
			if(dto == null){
				return ResultHelper.errMap();
			}
			if(dto.getStatus().equals("0")){
				return ResultHelper.errMap(ResultEnum.T_USER_UNOPEN);
			}
			tokenValue = JSONObject.toJSONString(dto);
			redisService.set(tokenKey, tokenValue, 1800L);//失效时间设置为0.5小时
			Cookie cookie = new Cookie("CMS_TOKEN", tokenKey);
			cookie.setPath("/");
			cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(cookie);
			StringBuffer urlStrBuff = request.getRequestURL();
			urlStrBuff.delete(urlStrBuff.length()-5, urlStrBuff.length());
			urlStrBuff.append("home");
			Map<String, Object> resultData = new HashMap<>();
			resultData.put("url", urlStrBuff.toString());
			return ResultHelper.succMap(resultData);
		}
		return ResultHelper.errMap(ResultEnum.USER_NAME_OR_PASSWORD_ERROR);
	}
	
	@PostMapping("/updateUserPassword")
	@ResponseBody
	public Map<String, Object> updateUserPassword(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String updateUserName = request.getParameter("updateUserName");
		if(StringUtils.isEmpty(updateUserName)) {
			return ResultHelper.errMap(ResultEnum.USER_NAME_NULL);
		}
		
		String updatePassword = request.getParameter("updatePassword");
		if(StringUtils.isEmpty(updatePassword)) {
			return ResultHelper.errMap(ResultEnum.PASSWORD_NULL);
		}
		
		String updateNewPassword = request.getParameter("updateNewPassword");
		if(StringUtils.isEmpty(updateNewPassword)) {
			return ResultHelper.errMap(ResultEnum.NEW_PASSWORD_ID_NULL);
		}
		
		String updateConfirmNewPassword = request.getParameter("updateConfirmNewPassword");
		if(StringUtils.isEmpty(updateConfirmNewPassword)) {
			return ResultHelper.errMap(ResultEnum.CONFIRM_NEW_PASSWORD_ID_NULL);
		}
		
		if(!updateConfirmNewPassword.equals(updateNewPassword)){
			return ResultHelper.errMap(ResultEnum.CONFIRM_NEW_PASSWORD_ID_ERROR);
		}
		
		//1、根据userName查询数据库中存的加密password
		String passwordInDb = userService.getPasswordInDB(updateUserName);
		//2、校验
		if(!StringUtils.isEmpty(passwordInDb) && Md5Util.validPassword(updatePassword, passwordInDb)){
			//3、生成cookie，跳转首页
			UserDTO dto = userService.getUserByUserName(updateUserName);
			if(dto == null){
				ResultHelper.errMap();
			}
			//4、修改密码
			dto.setPassword(Md5Util.getEncryptedPwd(updateNewPassword));
			dto.setUpdatedBy(dto.getUserName());
			dto.setUpdatedDate(new Date());
			Map<String, Object> map = userService.updateUser(dto);
			if(!"000000".equals(map.get("resultCode"))){
				return map;
			}
			//5、保存cookie
			String tokenKey = "CMS_TOKEN_" + updateUserName.toUpperCase();
			String tokenValue = JSONObject.toJSONString(dto);
			redisService.set(tokenKey, tokenValue, 12 * 3600L);
			Cookie cookie = new Cookie("CMS_TOKEN", tokenKey);
			cookie.setPath("/");
			cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(cookie);
			StringBuffer urlStrBuff = request.getRequestURL();
			urlStrBuff.delete(urlStrBuff.length()-18, urlStrBuff.length());
			urlStrBuff.append("home.html");
			Map<String, Object> resultData = new HashMap<>();
			resultData.put("url", urlStrBuff.toString());
			return ResultHelper.succMap(resultData);
		}
		return ResultHelper.errMap(ResultEnum.USER_NAME_OR_PASSWORD_ERROR);
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie : cookies){
				if("CMS_TOKEN".equals(cookie.getName())){
					cookie.setValue(null);  
                    cookie.setMaxAge(0);// 立即销毁cookie  
                    cookie.setPath("/");  
                    System.out.println("被删除的cookie名字为:"+cookie.getName());  
                    response.addCookie(cookie);
                    response.sendRedirect("goLogin");
				}
			}
		}
	}
}

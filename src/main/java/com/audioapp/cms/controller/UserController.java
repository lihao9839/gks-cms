package com.audioapp.cms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.dto.UserListDTO;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.service.UserService;

/**
 * 管理用户控制器
 * @author Administrator
 *
 */
@Controller
public class UserController extends BaseController{
	
    @Autowired
    private UserService userService;
    
    /**
     * 跳转用户管理
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/goUser")
    public String goUser(HttpServletRequest request, Model model){
    	Map<String, Object> paramMap = new HashMap<>();
    	List<UserListDTO> userList = userService.getUserList(paramMap);
    	model.addAttribute("userList", userList);
    	return "user_list";
    }

    //查询用户
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletRequest request){
        long userId = Long.valueOf(request.getParameter("id"));
        UserDTO user = userService.getUserById(userId);
        if(ObjectUtils.isEmpty(user)){
        	return ResultHelper.errMap();
        }
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("user", user);
        resultData.put("id", String.valueOf(user.getId()));
        return ResultHelper.succMap(resultData);
    }
    
    //新增用户
    @PostMapping("/addUser")
    @ResponseBody
    public Map<String,Object> addUser(HttpServletRequest request){
    	UserDTO userDTO = this.getUserDTO(request);
    	return userService.addUser(userDTO);
    }
    
    //查询用户列表
    @RequestMapping("/getUserList")
    public String getUserList(HttpServletRequest request, Model model) {
    	String userName = request.getParameter("userName");
    	String realName = request.getParameter("realName");
    	String roleType = request.getParameter("roleType");
    	String status = request.getParameter("status");
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("userName", userName);
    	paramMap.put("realName", realName);
    	paramMap.put("roleType", roleType);
    	paramMap.put("status", status);
    	List<UserListDTO> userList = userService.getUserList(paramMap);
    	model.addAttribute(userList);
    	return "user_list";
    }
    
    //开通权限
    @PostMapping("/openPermission")
    @ResponseBody
    public Map<String, Object> openPermission(HttpServletRequest request) {
    	String id = request.getParameter("id");
    	return userService.openPermission(id);
    }
    
    //关闭权限
    @PostMapping("/closePermission")
    @ResponseBody
    public Map<String, Object> closePermission(HttpServletRequest request) {
    	String id = request.getParameter("id");
    	return userService.closePermission(id);
    }
    
    //关闭权限
    @PostMapping("/resetPassword")
    @ResponseBody
    public Map<String, Object> resetPassword(HttpServletRequest request) {
    	String id = request.getParameter("id");
    	return userService.resetPassword(id);
    }
    
    //更新用户
    @PostMapping("/updateUser")
    @ResponseBody
    public Map<String, Object> updateUser(HttpServletRequest request) {
    	UserDTO dto = new UserDTO();
    	UserDTO loginUser = userService.getLoginUserInfo(request);
    	String id = request.getParameter("id");
    	dto.setId(Long.valueOf(id));
    	dto.setUserName(request.getParameter("userName"));
    	dto.setRealName(request.getParameter("realName"));
    	dto.setRoleType(request.getParameter("roleType"));//1-管理员，2-讲师
		dto.setUpdatedBy(loginUser.getUserName());
		dto.setUpdatedDate(new Date());
    	return userService.updateUser(dto);
    }
    
    private UserDTO getUserDTO(HttpServletRequest request) {
    	UserDTO dto = new UserDTO();
    	UserDTO loginUser = userService.getLoginUserInfo(request);
    	dto.setUserName(request.getParameter("userName"));
    	dto.setRealName(request.getParameter("realName"));
    	dto.setRoleType(request.getParameter("roleType"));//1-管理员，2-讲师
		dto.setCreatedBy(loginUser.getUserName());
		dto.setCreatedDate(new Date());
		dto.setUpdatedBy(loginUser.getUserName());
		dto.setUpdatedDate(new Date());
    	return dto;
    }
}

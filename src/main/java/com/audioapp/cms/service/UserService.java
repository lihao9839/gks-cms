package com.audioapp.cms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.dto.UserListDTO;

public interface UserService {
	
	UserDTO getLoginUserInfo(HttpServletRequest request);

    public UserDTO getUserById(long userId);//根据id查询用户
    
    Map<String, Object> addUser(UserDTO dto);//新增用户
    
    List<UserListDTO> getUserList(Map<String, Object> paramMap);//查询用户列表
    
    String getPasswordInDB(String userName);//根据用户名查询数据库密码
    
    UserDTO getUserByUserName(String userName);
    
    Map<String, Object> openPermission(String id);//开通权限
    
    Map<String, Object> closePermission(String id);//关闭权限
    
    Map<String, Object> resetPassword(String id);//重设密码
    
    Map<String, Object> updateUser(UserDTO userDTO);//修改
}

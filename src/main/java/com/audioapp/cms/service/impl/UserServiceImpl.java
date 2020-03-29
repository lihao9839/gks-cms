package com.audioapp.cms.service.impl;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.audioapp.cms.constant.ResultEnum;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.dto.UserListDTO;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.mapper.UserDTOMapper;
import com.audioapp.cms.service.RedisService;
import com.audioapp.cms.service.UserService;

import com.audioapp.cms.utils.Md5Util;


@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Value(value="${inipassword}")
	String inipassword;
	
    @Autowired
    private UserDTOMapper userMapper;
    
    @Autowired
    private RedisService redisService;
    
    public UserDTO getUserById(long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

	@Override
	public List<UserListDTO> getUserList(Map<String, Object> paramMap) {
		return userMapper.getUserList(paramMap);
	}

	@Override
	public Map<String, Object> addUser(UserDTO dto) {
		//校验用户名重复
		UserDTO userCheck = userMapper.selectByUserName(dto.getUserName());
		if(null != userCheck){
			return ResultHelper.errMap(ResultEnum.USER_NAME_REPEAT);
		}
		String password = Md5Util.getEncryptedPwd(inipassword.trim());
		dto.setId(snowflake.nextId());
		dto.setPassword(password);
		dto.setStatus("0");
		if(userMapper.insert(dto) > 0){
			return ResultHelper.succMap();
		}
		
		return ResultHelper.errMap();
	}

	@Override
	public String getPasswordInDB(String userName) {
		UserDTO user = userMapper.selectByUserName(userName);
		if(user != null){
			return user.getPassword();
		}else{
			return "";
		}
	}

	@Override
	public UserDTO getUserByUserName(String userName) {
		return userMapper.selectByUserName(userName);
	}

	@Override
	public UserDTO getLoginUserInfo(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie : cookies){
				if("CMS_TOKEN".equals(cookie.getName())){
					String tokenKey = cookie.getValue();
					String tokenValue = redisService.get(tokenKey);
					return JSONObject.parseObject(tokenValue, UserDTO.class);
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> openPermission(String id) {
		if(StringUtils.isEmpty(id)){
			return ResultHelper.errMap(ResultEnum.BUSINESS_ID_NULL);
		}
		UserDTO user = new UserDTO();
		user.setId(Long.valueOf(id));
		user.setStatus("1");
		if(userMapper.updateByPrimaryKeySelective(user) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}

	@Override
	public Map<String, Object> closePermission(String id) {
		if(StringUtils.isEmpty(id)){
			return ResultHelper.errMap(ResultEnum.BUSINESS_ID_NULL);
		}
		UserDTO user = new UserDTO();
		user.setId(Long.valueOf(id));
		user.setStatus("0");
		if(userMapper.updateByPrimaryKeySelective(user) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}

	@Override
	public Map<String, Object> resetPassword(String id) {
		if(StringUtils.isEmpty(id)){
			return ResultHelper.errMap(ResultEnum.BUSINESS_ID_NULL);
		}
		UserDTO user = new UserDTO();
		user.setId(Long.valueOf(id));
		String password = Md5Util.getEncryptedPwd(inipassword.trim());
		user.setPassword(password);
		if(userMapper.updateByPrimaryKeySelective(user) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}

	@Override
	public Map<String, Object> updateUser(UserDTO userDTO) {
		if(userMapper.updateByPrimaryKeySelective(userDTO) > 0){
			return ResultHelper.succMap();
		}
		return ResultHelper.errMap();
	}
}
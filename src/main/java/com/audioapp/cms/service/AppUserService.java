package com.audioapp.cms.service;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.AppUserDTO;
import com.audioapp.cms.dto.AppUserListDTO;
import com.audioapp.cms.dto.SubscribeListDTO;

/**
 * APP用户
 * @author Administrator
 *
 */
public interface AppUserService {
	
	List<AppUserListDTO> getAppUserList(Map<String, Object> paramMap);
	
	List<SubscribeListDTO> getSubscribeList(String userName);

}

package com.audioapp.cms.service;

import java.util.List;

import com.audioapp.cms.dto.MenuDTO;

public interface MenuService {
	
	List<MenuDTO> getMenuList(String roleType);
}

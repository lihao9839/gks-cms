package com.audioapp.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.audioapp.cms.dto.MenuDTO;
import com.audioapp.cms.mapper.MenuDTOMapper;
import com.audioapp.cms.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDTOMapper menuMapper;
	
	
	@Override
	public List<MenuDTO> getMenuList(String roleType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleType", roleType);
		paramMap.put("parentCode", "0");
		List<MenuDTO> menuList = menuMapper.getMenuListByParentCode(paramMap);
		for(MenuDTO menu : menuList){
			paramMap.put("parentCode", menu.getMenuCode());
			List<MenuDTO> childMenuList = menuMapper.getMenuListByParentCode(paramMap);
			if(!CollectionUtils.isEmpty(childMenuList)){
				menu.setChildMenuList(childMenuList);
			}
		}
		return menuList;
	}

}

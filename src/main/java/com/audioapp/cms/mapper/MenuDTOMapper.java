package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.MenuDTO;

@Mapper
public interface MenuDTOMapper {
	
    int deleteByPrimaryKey(Long id);
    
    List<MenuDTO> getMenuList();
    
    List<MenuDTO> getMenuListByParentCode(Map<String, Object> paramMap);

    int insert(MenuDTO record);

    int insertSelective(MenuDTO record);

    MenuDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuDTO record);

    int updateByPrimaryKey(MenuDTO record);
}
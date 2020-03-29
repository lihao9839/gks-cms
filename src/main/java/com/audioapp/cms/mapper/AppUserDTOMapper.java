package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.AppUserDTO;
import com.audioapp.cms.dto.AppUserListDTO;

@Mapper
public interface AppUserDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppUserDTO record);

    int insertSelective(AppUserDTO record);

    AppUserDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppUserDTO record);

    int updateByPrimaryKey(AppUserDTO record);
    
    List<AppUserListDTO> getAppUserDTOList(Map<String, Object> paramMap);

    //查询已订阅信息
    Map<String, Object> countSubjectOrdered(String userName);
}
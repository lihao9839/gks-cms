package com.audioapp.cms.mapper;

import com.audioapp.cms.dto.AppUserMessageDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppUserMessageDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppUserMessageDTO record);

    int insertSelective(AppUserMessageDTO record);

    AppUserMessageDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppUserMessageDTO record);

    int updateByPrimaryKey(AppUserMessageDTO record);
}
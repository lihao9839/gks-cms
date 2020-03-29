package com.audioapp.cms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.PicDTO;

@Mapper
public interface PicDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PicDTO record);

    int insertSelective(PicDTO record);

    PicDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PicDTO record);

    int updateByPrimaryKey(PicDTO record);
    
    PicDTO selectByType(String type);
}
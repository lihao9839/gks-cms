package com.audioapp.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.AppOrderSuccessDTO;
import com.audioapp.cms.dto.SubscribeListDTO;

@Mapper
public interface AppOrderSuccessDTOMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(AppOrderSuccessDTO record);

    int insertSelective(AppOrderSuccessDTO record);

    AppOrderSuccessDTO selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(AppOrderSuccessDTO record);

    int updateByPrimaryKey(AppOrderSuccessDTO record);
    
    List<SubscribeListDTO> getSubscribeList(String userName);
}
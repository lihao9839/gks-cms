package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.MessageDTO;
import com.audioapp.cms.dto.MessageListDTO;

@Mapper
public interface MessageDTOMapper {
    int deleteByPrimaryKey(long id);

    int insert(MessageDTO record);

    int insertSelective(MessageDTO record);

    MessageDTO selectByPrimaryKey(long id);
    
    MessageListDTO selectListDTOByPrimaryKey(long id);

    int updateByPrimaryKeySelective(MessageDTO record);

    int updateByPrimaryKeyWithBLOBs(MessageDTO record);

    int updateByPrimaryKey(MessageDTO record);
    
    List<MessageListDTO> getMessageList(Map<String, Object> paramMap);
}
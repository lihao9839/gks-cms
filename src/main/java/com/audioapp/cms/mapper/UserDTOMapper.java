package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.dto.UserListDTO;

@Mapper
public interface UserDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDTO record);

    int insertSelective(UserDTO record);

    UserDTO selectByPrimaryKey(Long id);
    
    UserDTO selectByUserName(String userName);

    int updateByPrimaryKeySelective(UserDTO record);

    int updateByPrimaryKey(UserDTO record);
   
    List<UserListDTO> getUserList(Map<String, Object> paramMap);
   
}
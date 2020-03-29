package com.audioapp.cms.mapper;

import com.audioapp.cms.dto.AppUserBaseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppUserBaseDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppUserBaseDTO record);

    int insertSelective(AppUserBaseDTO record);

    AppUserBaseDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppUserBaseDTO record);

    int updateByPrimaryKey(AppUserBaseDTO record);

    List<AppUserBaseDTO> getAppUserBaseList(Map<String, Object> paramMap);
}
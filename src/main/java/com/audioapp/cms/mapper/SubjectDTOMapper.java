package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.SubjectDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.SubjectDTO;
import com.audioapp.cms.dto.SubjectListDTO;

@Mapper
public interface SubjectDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SubjectDTO record);

    int insertSelective(SubjectDTO record);

    SubjectDTO selectByPrimaryKey(Long id);

    SubjectDetailDTO selectDetailByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubjectDTO record);

    int updateByPrimaryKeyWithBLOBs(SubjectDTO record);

    int updateByPrimaryKey(SubjectDTO record);
    
    List<SubjectListDTO> getSubjectList(Map<String, Object> paramMap);
}
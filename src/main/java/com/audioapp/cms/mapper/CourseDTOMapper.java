package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.CourseDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.CourseDTO;
import com.audioapp.cms.dto.CourseListDTO;

@Mapper
public interface CourseDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CourseDTO record);

    int insertSelective(CourseDTO record);

    CourseDetailDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseDTO record);

    int updateByPrimaryKeyWithBLOBs(CourseDTO record);

    int updateByPrimaryKey(CourseDTO record);
    
    List<CourseListDTO> getCourseList(Map<String, Object> paramMap);
}
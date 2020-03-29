package com.audioapp.cms.service;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.CourseDTO;
import com.audioapp.cms.dto.CourseListDTO;

/**
 * 课程服务
 * @author Administrator
 *
 */
public interface CourseService {
	
	List<CourseListDTO> getCourseList(Map<String, Object> paramMap);
	
	boolean addCourse(CourseDTO course);
	
	CourseDTO getCourseDetail(long id);
	
	boolean updateCourse(CourseDTO course);
}

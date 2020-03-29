package com.audioapp.cms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audioapp.cms.dto.CourseDTO;
import com.audioapp.cms.dto.CourseListDTO;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.mapper.CourseDTOMapper;
import com.audioapp.cms.service.CourseService;
import com.audioapp.cms.service.UserService;

@Service
public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

	@Autowired
	private CourseDTOMapper courseMapper; 
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<CourseListDTO> getCourseList(Map<String, Object> paramMap) {
		return courseMapper.getCourseList(paramMap);
	}

	@Override
	public boolean addCourse(CourseDTO course) {
		course.setId(snowflake.nextId());
		course.setStatus("0");
		if(courseMapper.insert(course) > 0){
			return true;
		}
		return false;
	}

	@Override
	public CourseDTO getCourseDetail(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateCourse(CourseDTO course) {
		// TODO Auto-generated method stub
		return false;
	}

}

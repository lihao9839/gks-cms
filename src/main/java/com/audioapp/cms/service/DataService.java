package com.audioapp.cms.service;

import com.audioapp.cms.dto.TotalListDTO;

import java.util.Map;

public interface DataService {

	Map<String, Object> getYesData();

	Map<String, Object> getSevenData();

	Map<String, Object> getMonthData();

	Map<String, Object> getTotalData();

	TotalListDTO getTotalList(Map<String, Object> paramMap);

	Map<String, Object> getSubjectCount(Long subjectId);

	int getSubjectLearnCount(Long subjectId);

	int getShareCount(Long subjectId);

	int getCourseShareCnt(Long courseId);

	int getCourseLearnCnt(Long courseId);

	int getCourseThumbsupCnt(Long courseId);

	int getFreeCourseCnt(Long courseId);

	Map<String, Object> getSubjectOpenCnt(Map<String, Object> paramMap);
}

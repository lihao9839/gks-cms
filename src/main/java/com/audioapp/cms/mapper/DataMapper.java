package com.audioapp.cms.mapper;

import com.audioapp.cms.dto.TotalListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface DataMapper {

	int getYesNewUsers(Map<String, Object> paramMap);

	int getActUsers(Map<String, Object> paramMap);

	int getYesSubAmt(Map<String, Object> paramMap);

	int getTotalUsers(Map<String, Object> paramMap);

	int getTotalAmt();

	Map<String, Object> getSubInfo(Map<String, Object> paramMap);

	int getLearnTimes(Map<String, Object> paramMap);

	int getCourseShareCount(Map<String, Object> paramMap);

	int getSubjectShareCount(Long subjectId);

	int getCourseThumbsupCnt(Long courseId);

	int getFreeCourseCnt(Long courseId);

	Map<String, Object> getOpenCnt(Map<String, Object> paramMap);
}
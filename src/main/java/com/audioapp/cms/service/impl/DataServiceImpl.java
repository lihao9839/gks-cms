package com.audioapp.cms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.audioapp.cms.dto.TotalListDTO;
import com.audioapp.cms.mapper.DataMapper;
import com.audioapp.cms.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataServiceImpl extends BaseServiceImpl implements DataService {

	@Autowired
	private DataMapper dataMapper;

	@Override
	public Map<String, Object> getYesData() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate", DateUtil.beginOfDay(DateUtil.yesterday()));
		paramMap.put("endDate", DateUtil.endOfDay(DateUtil.yesterday()));
		Map<String, Object> yesMap = new HashMap<>();
		yesMap.put("yesNewUsers", dataMapper.getYesNewUsers(paramMap));
		yesMap.put("yesActUsers", dataMapper.getActUsers(paramMap));
		BigDecimal yesSubAmt = new BigDecimal(dataMapper.getYesSubAmt(paramMap));
		yesSubAmt = yesSubAmt.divide(new BigDecimal(100));
		yesMap.put("yesSubAmt", yesSubAmt.longValue());
		return yesMap;
	}

	@Override
	public Map<String, Object> getSevenData() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate",DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(),-7)));
		paramMap.put("endDate",DateUtil.endOfDay(DateUtil.offsetDay(DateUtil.date(),-1)));
		Map<String, Object> sevenMap = new HashMap<>();
		sevenMap.put("sevenActUsers", dataMapper.getActUsers(paramMap));
		return sevenMap;
	}

	@Override
	public Map<String, Object> getMonthData() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate",DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(),-30)));
		paramMap.put("endDate",DateUtil.endOfDay(DateUtil.offsetDay(DateUtil.date(),-1)));
		Map<String, Object> monthMap = new HashMap<>();
		monthMap.put("monthActUsers", dataMapper.getActUsers(paramMap));
		return monthMap;
	}

	@Override
	public Map<String, Object> getTotalData() {
		Map<String, Object> totalMap = new HashMap<>();
		totalMap.put("totalUsers", dataMapper.getTotalUsers(null));
		BigDecimal totalAmt = new BigDecimal(dataMapper.getTotalAmt());
		totalAmt = totalAmt.divide(new BigDecimal(100));
		totalMap.put("totalSubAmt", totalAmt);
		return totalMap;
	}

	@Override
	public TotalListDTO getTotalList(Map<String, Object> paramMap) {
		TotalListDTO dto = new TotalListDTO();
		dto.setRegUsers(String.valueOf(dataMapper.getTotalUsers(paramMap)));
		paramMap.put("type", "h5");
		dto.setH5Users(String.valueOf(dataMapper.getTotalUsers(paramMap)));
		paramMap.put("type", "app");
		dto.setAppUsers(String.valueOf(dataMapper.getTotalUsers(paramMap)));
		Map<String, Object> subInfo = dataMapper.getSubInfo(paramMap);
		dto.setLenTimes(String.valueOf(dataMapper.getLearnTimes(paramMap)));
		dto.setSubUsers(String.valueOf(subInfo.get("userCount")));
		dto.setSubTimes(String.valueOf(subInfo.get("orderCount")));
		BigDecimal amtCount = new BigDecimal(String.valueOf(subInfo.get("amtCount")));
		dto.setSubAmt(String.valueOf(amtCount.divide(new BigDecimal(100)).doubleValue()));
		dto.setActUsers(String.valueOf(dataMapper.getActUsers(paramMap)));
		return dto;
	}

	@Override
	public Map<String, Object> getSubjectCount(Long subjectId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("subjectId", subjectId);
		Map<String, Object> resultMap = dataMapper.getSubInfo(paramMap);
		BigDecimal amtCount = new BigDecimal(String.valueOf(resultMap.get("amtCount")));
		resultMap.put("amtCount", String.valueOf(amtCount.divide(new BigDecimal(100)).doubleValue()));
		return resultMap;
	}

	@Override
	public int getSubjectLearnCount(Long subjectId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("subjectId", subjectId);
		return dataMapper.getLearnTimes(paramMap);
	}

	@Override
	public int getShareCount(Long subjectId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("subjectId", subjectId);
		int courseCount = dataMapper.getCourseShareCount(paramMap);
		return dataMapper.getSubjectShareCount(subjectId) + courseCount;
	}

	@Override
	public int getCourseShareCnt(Long courseId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("courseId", courseId);
		return dataMapper.getCourseShareCount(paramMap);
	}

	@Override
	public int getCourseLearnCnt(Long courseId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("courseId", courseId);
		return dataMapper.getLearnTimes(paramMap);
	}

	@Override
	public int getCourseThumbsupCnt(Long courseId) {
		return dataMapper.getCourseThumbsupCnt(courseId);
	}

	@Override
	public int getFreeCourseCnt(Long courseId) {
		return dataMapper.getFreeCourseCnt(courseId);
	}

	@Override
	public Map<String, Object> getSubjectOpenCnt(Map<String, Object> paramMap) {
		paramMap.put("actionType", 2);
		return dataMapper.getOpenCnt(paramMap);
	}
}

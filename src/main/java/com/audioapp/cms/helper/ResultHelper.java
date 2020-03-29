package com.audioapp.cms.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.audioapp.cms.constant.ResultEnum;

public class ResultHelper {
	
	public static Map<String, Object> succMap(){
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode", ResultEnum.SUCCESS.getResultCode());
		resultMap.put("resultMsg", ResultEnum.SUCCESS.getResultMsg());
		return resultMap;
	}
	
	public static Map<String, Object> errMap(){
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode",	 ResultEnum.ERROR.getResultCode());
		resultMap.put("resultMsg", ResultEnum.ERROR.getResultMsg());
		return resultMap;
	}
	
	public static Map<String, Object> succMap(Map<String, Object> resultData){
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode", ResultEnum.SUCCESS.getResultCode());
		resultMap.put("resultMsg", ResultEnum.SUCCESS.getResultMsg());
		resultMap.put("resultData", resultData);
		return resultMap;
	}

	public static Map<String, Object> errMap(ResultEnum rEnum){
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode",	 rEnum.getResultCode());
		resultMap.put("resultMsg", rEnum.getResultMsg());
		return resultMap;
	}
}

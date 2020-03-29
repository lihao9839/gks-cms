package com.audioapp.cms.dto;

import lombok.Data;

@Data
public class TotalDataDTO {
	private String yesNewUsers;//昨天新增用户
	private String yesActUsers;//昨日活跃用户
	private String yesSubAmt;//昨日订阅金额
	private String totalUsers;//总用户
	private String sevenDayAct;//7日活跃用户
	private String monthAct;//30日活跃用户
	private String totalSubAmt;//历史总订阅金额
}

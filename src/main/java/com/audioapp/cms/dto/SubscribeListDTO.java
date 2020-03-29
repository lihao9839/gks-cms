package com.audioapp.cms.dto;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class SubscribeListDTO extends AppOrderSuccessDTO {
	
	private String subjectName;//专栏名称
	private String subscribDateStr;//订阅时间
	private String subscribAmount;//订阅金额
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubscribDateStr() {//订阅时间设置为创建时间
		return DateUtil.format(this.getCreateDate(), DateConstant.DATE_TIME_FORMATE);
	}
}

package com.audioapp.cms.dto;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;

public class MessageListDTO extends MessageDTO {
	
	private String sendDateStr;
	private String createdDateStr;
	private String statusStr;
	
	public String getSendDateStr() {
		return DateUtil.format(this.getSendDate(), DateConstant.DATE_TIME_FORMATE);
	}

	public String getCreatedDateStr() {
		return DateUtil.format(this.getCreatedDate(), DateConstant.DATE_TIME_FORMATE);
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}

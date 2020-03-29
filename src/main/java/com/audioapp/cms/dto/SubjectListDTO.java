package com.audioapp.cms.dto;

import java.math.BigDecimal;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class SubjectListDTO extends SubjectDTO {
	
	private String realName;//主讲人姓名
	private String userName;//主讲人账号
	private String statesStr;//发布状态中文
	private String createdDateStr;//创建时间 中文
	private String publishDateStr;//发布时间 中文
	private String actualPrice;
	private String subCnt;//订阅次数
	private String subAmt;//订阅总金额
	private String subLenCnt;//专栏学习次数
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatesStr() {
		return statesStr;
	}
	public void setStatesStr(String statesStr) {
		this.statesStr = statesStr;
	}
	public String getCreatedDateStr() {
		return DateUtil.format(this.getCreatedDate(), DateConstant.DATE_TIME_FORMATE);
	}
	public String getPublishDateStr() {
		return DateUtil.format(this.getPublishDate(), DateConstant.DATE_TIME_FORMATE);
	}
	public String getActualPrice() {
		BigDecimal price = new BigDecimal(super.getPrice());
		price = price.divide(new BigDecimal(100));
		return price.toString();
	}
	public void setActualPrice(String actualPrice) {
		this.actualPrice = actualPrice;
	}
}

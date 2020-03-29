package com.audioapp.cms.dto;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class CourseListDTO extends CourseDTO {
	
	private String subjectName;//专栏名称
	private String realName;//主讲人
	private String statusStr;//发布状态：0-未发布，1-已发布
	private String createdDateStr;// 创建时间  中文
	private String publishDateStr;//发布时间 中文
	private String learnCnt;//学习次数
	private String shareCnt;//分享次数
	private String thumbsupCnt;//收藏次数
	private String freeCourseCnt;//免费课程次数
	private String courseTypeStr;
	private String freeFlagStr;
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getCreatedDateStr() {
		return DateUtil.format(this.getCreatedDate(), DateConstant.DATE_TIME_FORMATE);
	}

	public String getPublishDateStr() {
		return DateUtil.format(this.getPublishDate(), DateConstant.DATE_TIME_FORMATE);
	}

}

package com.audioapp.cms.dto;

import java.util.Date;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class CourseDataListDTO {
	
	private String CourseName;
	private Date createdDate;
	private String createdDateStr;
	private String status;
	private String statusStr;
	private Date publishDate;
	private String publishDateStr;
	private String learnCount;
	private String shearCount;
	private String collectCount;
	private String freeFlag;
	private String freeFlagStr;
	private String courseType;
	private String courseTypeStr;
	private String freeCourseCount;

	public String getCreatedDateStr() {
		return DateUtil.format(this.getCreatedDate(), DateConstant.DATE_TIME_FORMATE);
	}
	
	public String getPublishDateStr() {
		return DateUtil.format(this.getPublishDate(), DateConstant.DATE_TIME_FORMATE);
	}
	
}

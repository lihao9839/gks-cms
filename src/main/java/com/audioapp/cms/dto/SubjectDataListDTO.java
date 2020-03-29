package com.audioapp.cms.dto;

import java.util.Date;

import lombok.Data;

/**
 * 专栏数据DTO
 * @author Administrator
 *
 */
@Data
public class SubjectDataListDTO {
	
	private String subjectId;
	private String subjectName;
	private String realName;
	private String userName;
	private Date createdDate;
	private String createdDateStr;
	private String status;
	private String statusStr;
	private Date publishDate;
	private String publishDateStr;
	private String subscribePrice;

}

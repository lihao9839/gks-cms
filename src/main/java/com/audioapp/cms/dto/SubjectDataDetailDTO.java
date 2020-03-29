package com.audioapp.cms.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectDataDetailDTO {
	private String subjectName;//专栏名称
	private Long subjectId;//专栏Id
	private String openCount;//查看次数
	private String openPeopleCount;//查看人数
	private String learnCount;//学习次数
	private String replayCount;//评论次数
	private String shareCount;//分享次数
	private String subscribeCount;//订阅次数
	private String subscribeAmount;//订阅金额
	private List<CourseListDTO> courseList;//课程列表
}
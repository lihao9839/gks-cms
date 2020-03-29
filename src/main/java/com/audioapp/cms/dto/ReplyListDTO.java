package com.audioapp.cms.dto;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class ReplyListDTO extends ReplyDTO {

	private String subjectName;//专栏名称
	private String courseName;//课程名称
	private String nickName;//昵称
	private String createdDateStr;
	private String profileUrl;//头像
	private String replayStatusStr;//评论状态
	private String statusStr;//发布状态
	
	public String getCreatedDateStr() {
		return DateUtil.format(this.getCreatedDate(), DateConstant.DATE_TIME_FORMATE);
	}
	
}

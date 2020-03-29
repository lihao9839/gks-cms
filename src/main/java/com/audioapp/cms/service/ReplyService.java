package com.audioapp.cms.service;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.ReplyListDTO;

public interface ReplyService {
	
	List<ReplyListDTO> getSubjectReplayList(Map<String, Object> paramMap);

	List<ReplyListDTO> getCourseReplayList(Map<String, Object> paramMap);

	List<ReplyListDTO> getUserReplayList(Map<String, Object> paramMap);

	int reply(long id, String teacherReply);

	int replyBack(long id);

	int publishReply(long id);

	int recallReply(long id);

	int getReplyCount(long subjectId);

}

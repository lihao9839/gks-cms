package com.audioapp.cms.service.impl;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.constant.ReplyConstant;
import com.audioapp.cms.dto.ReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audioapp.cms.dto.ReplyListDTO;
import com.audioapp.cms.mapper.ReplyDTOMapper;
import com.audioapp.cms.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDTOMapper replyMapper;

	@Override
	public List<ReplyListDTO> getSubjectReplayList(Map<String, Object> paramMap) {
		return replyMapper.getSubjectReplyList(paramMap);
	}

	@Override
	public List<ReplyListDTO> getCourseReplayList(Map<String, Object> paramMap) {
		return replyMapper.getCourseReplyList(paramMap);
	}

	@Override
	public List<ReplyListDTO> getUserReplayList(Map<String, Object> paramMap) {
		return replyMapper.getUserReplyList(paramMap);
	}

	@Override
	public int reply(long id, String teacherReply) {
		ReplyDTO record = new ReplyDTO();
		record.setId(id);
		record.setTeacherReply(teacherReply);
		record.setReplayStatus(ReplyConstant.REPLY_STATUS_REPLY);
		return replyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int replyBack(long id) {
		ReplyDTO record = new ReplyDTO();
		record.setId(id);
		record.setReplayStatus(ReplyConstant.REPLY_STATUS_UNREPLY);
		return replyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int publishReply(long id) {
		ReplyDTO record = new ReplyDTO();
		record.setId(id);
		record.setStatus(ReplyConstant.STATUS_PUBLISH);
		return replyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int recallReply(long id) {
		ReplyDTO record = new ReplyDTO();
		record.setId(id);
		record.setStatus(ReplyConstant.STATUS_UNPUBLISH);
		return replyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int getReplyCount(long subjectId) {
		return replyMapper.getReplyCount(subjectId);
	}
}

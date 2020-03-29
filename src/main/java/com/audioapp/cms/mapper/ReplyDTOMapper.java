package com.audioapp.cms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.audioapp.cms.dto.ReplyDTO;
import com.audioapp.cms.dto.ReplyListDTO;

@Mapper
public interface ReplyDTOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReplyDTO record);

    int insertSelective(ReplyDTO record);

    ReplyDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReplyDTO record);

    int updateByPrimaryKeyWithBLOBs(ReplyDTO record);

    int updateByPrimaryKey(ReplyDTO record);
    
    List<ReplyListDTO> getSubjectReplyList(Map<String, Object> paramMap);

    List<ReplyListDTO> getCourseReplyList(Map<String, Object> paramMap);

    List<ReplyListDTO> getUserReplyList(Map<String, Object> paramMap);

    int getReplyCount(long subjectId);
}
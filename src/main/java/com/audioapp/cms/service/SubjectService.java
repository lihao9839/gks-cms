package com.audioapp.cms.service;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.SubjectDTO;
import com.audioapp.cms.dto.SubjectDetailDTO;
import com.audioapp.cms.dto.SubjectListDTO;

/**
 * 专栏服务
 * @author Administrator
 *
 */
public interface SubjectService {

	List<SubjectListDTO> getSubjectList(Map<String, Object> paramMap);
	
	boolean addSubject(SubjectDTO subject);
	
	boolean updateSubject(SubjectDTO subject);

	SubjectDetailDTO getSubjectDetail(long id);
}

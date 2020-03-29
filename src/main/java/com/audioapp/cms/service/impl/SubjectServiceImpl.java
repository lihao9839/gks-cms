package com.audioapp.cms.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.SubjectDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audioapp.cms.dto.SubjectDTO;
import com.audioapp.cms.dto.SubjectListDTO;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.mapper.SubjectDTOMapper;
import com.audioapp.cms.mapper.UserDTOMapper;
import com.audioapp.cms.service.SubjectService;
import com.audioapp.cms.service.UserService;

@Service
public class SubjectServiceImpl extends BaseServiceImpl implements SubjectService {

	@Autowired
	private SubjectDTOMapper subjectMapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<SubjectListDTO> getSubjectList(Map<String, Object> paramMap) {
		return subjectMapper.getSubjectList(paramMap);
	}

	@Override
	public boolean addSubject(SubjectDTO subject) {
		subject.setId(snowflake.nextId());
		subject.setStates("0");
		if(subjectMapper.insert(subject) > 0){
			return true;
		}
		return false;
	}

	@Override
	public SubjectDetailDTO getSubjectDetail(long id) {
		SubjectDetailDTO subjectDetailDTO = subjectMapper.selectDetailByPrimaryKey(id);
		BigDecimal price = new BigDecimal(subjectDetailDTO.getPrice());
		subjectDetailDTO.setPrice(price.divide(new BigDecimal(100)).doubleValue());
		return subjectDetailDTO;
	}

	@Override
	public boolean updateSubject(SubjectDTO subject) {
		// TODO Auto-generated method stub
		return false;
	}



}

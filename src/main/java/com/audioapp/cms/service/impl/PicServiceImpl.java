package com.audioapp.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audioapp.cms.dto.PicDTO;
import com.audioapp.cms.mapper.PicDTOMapper;
import com.audioapp.cms.service.PicService;

@Service
public class PicServiceImpl extends BaseServiceImpl implements PicService {

	@Autowired
	PicDTOMapper picMapper;
	
	@Override
	public PicDTO getPicByType(String type) {
		return picMapper.selectByType(type);
	}

	@Override
	public int addOrUpdate(PicDTO pic) {
		if(null == picMapper.selectByType(pic.getType())){
			pic.setId(snowflake.nextId());
			return picMapper.insert(pic);
		}else{
			return picMapper.updateByPrimaryKeySelective(pic);
		}
	}

}

package com.audioapp.cms.service;

import com.audioapp.cms.dto.PicDTO;

public interface PicService {
	
	PicDTO getPicByType(String type);
	
	int addOrUpdate(PicDTO pic);

}

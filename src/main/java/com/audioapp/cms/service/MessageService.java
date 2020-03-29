package com.audioapp.cms.service;

import java.util.List;
import java.util.Map;

import com.audioapp.cms.dto.MessageDTO;
import com.audioapp.cms.dto.MessageListDTO;

public interface MessageService {
	
	List<MessageListDTO> getMessageList(Map<String, Object> paramMap);

	String pushMessage(String title, String content) throws Exception;

	void pushMessage();

}

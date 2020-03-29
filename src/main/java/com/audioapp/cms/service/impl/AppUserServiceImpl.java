package com.audioapp.cms.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audioapp.cms.dto.AppUserListDTO;
import com.audioapp.cms.dto.SubscribeListDTO;
import com.audioapp.cms.mapper.AppOrderSuccessDTOMapper;
import com.audioapp.cms.mapper.AppUserDTOMapper;
import com.audioapp.cms.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {
	
	@Autowired
	private AppUserDTOMapper appUserMapper; 
	
	@Autowired
	private AppOrderSuccessDTOMapper orderMapper;

	@Override
	public List<AppUserListDTO> getAppUserList(Map<String, Object> paramMap) {
		List<AppUserListDTO> appUserList = appUserMapper.getAppUserDTOList(paramMap);
		for(AppUserListDTO appUser: appUserList){
			Map<String, Object> subjectMap = appUserMapper.countSubjectOrdered(appUser.getUserName());
			//订阅专栏数
			appUser.setSubscribeSubjectNum(String.valueOf(subjectMap.get("countNum")));
			appUser.setFreeNum(appUser.getFreeNum() + appUser.getUsedFreeNum());
			//订阅金额
			BigDecimal totalPrice = new BigDecimal(String.valueOf(subjectMap.get("amount")));
			totalPrice = totalPrice.divide(new BigDecimal(100));
			appUser.setSubscribeTotalPrice(String.valueOf(totalPrice.doubleValue()));
		}
		return appUserList;
	}

	@Override
	public List<SubscribeListDTO> getSubscribeList(String userName) {
		List<SubscribeListDTO> list = orderMapper.getSubscribeList(userName);
		//处理订阅金额显示
		for(SubscribeListDTO dto : list){
			BigDecimal price = new BigDecimal(String.valueOf(dto.getTotalAmount()));
			price = price.divide(new BigDecimal(100));
			dto.setSubscribAmount(String.valueOf(price.doubleValue()));
		}
		return list;

	}

}

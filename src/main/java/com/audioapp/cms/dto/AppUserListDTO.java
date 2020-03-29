package com.audioapp.cms.dto;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class AppUserListDTO extends AppUserDTO {
	
	private String registerTimeStr;//注册时间
	private String registerTypeStr;//注册方式
	private String remainingFreeNum;//剩余课程数
	private int usedFreeNum;//使用免费课程数
	private String subscribeSubjectNum;//订阅专栏数
	private String subscribeTotalPrice;//订阅金额
	private String lastLoginTimeStr;//最近登录时间

	public String getRegisterTimeStr() {
		return DateUtil.format(this.getRegisterTime(), DateConstant.DATE_TIME_FORMATE);
	}

	public String getRegisterTypeStr() {
		if(super.getRegisterType() == 0){
			return "App-Android";
		}
		if(super.getRegisterType() == 1){
			return "H5";
		}
		return registerTypeStr;
	}


}
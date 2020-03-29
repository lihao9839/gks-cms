package com.audioapp.cms.dto;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;

public class UserListDTO extends UserDTO {
	
    private String createdDateStr;
    private String roleTypeStr;
    private String statusStr;

	public String getCreatedDateStr() {
		return DateUtil.format(this.getCreatedDate(), DateConstant.DATE_TIME_FORMATE);
	}

	public String getRoleTypeStr() {
		return roleTypeStr;
	}

	public void setRoleTypeStr(String roleTypeStr) {
		this.roleTypeStr = roleTypeStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}

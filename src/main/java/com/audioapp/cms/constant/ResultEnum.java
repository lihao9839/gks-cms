package com.audioapp.cms.constant;

public enum ResultEnum {
	
	USER_NAME_NULL("000001", "用户名为空"),
	PASSWORD_NULL("000002", "登录密码为空"),
	USER_NAME_OR_PASSWORD_ERROR("000003", "用户名或密码错误"),
	USER_NAME_REPEAT("000004", "用户名重复"),
	BUSINESS_ID_NULL("000005", "主业务ID为空"),
	NEW_PASSWORD_ID_NULL("000006", "新密码为空"),
	CONFIRM_NEW_PASSWORD_ID_NULL("000007", "确认新密码为空"),
	CONFIRM_NEW_PASSWORD_ID_ERROR("000008", "新密码与确认新密码不一致"),
	USER_IS_LOGIN("000009", "当前用户已登录，请登出后再试"),
	DATETIME_EXPIRED ("000010", "发送时间不能早于当前时间"),
	UPDATE_FAIL ("000011", "更新失败"),
	T_USER_UNOPEN ("000012", "权限未开通"),
	PIC_URL_NULL ("000013", "音频未上传完毕，请检查"),
	PRICE_NULL ("000014", "订阅价格为0，请检查"),
	
	ERROR("999999", "失败"),
	SUCCESS("000000", "成功");

	
	public String resultCode;
	public String resultMsg;
	
	ResultEnum(String resultCode, String resultMsg){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}
	
}

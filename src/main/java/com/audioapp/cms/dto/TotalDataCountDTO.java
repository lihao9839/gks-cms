package com.audioapp.cms.dto;

import lombok.Data;

/**
 * 总体数据DTO
 * @author Administrator
 *
 */
@Data
public class TotalDataCountDTO {
	
	private String registerPeople;/*注册人数*/
	private String appRegister;/*APP注册*/
	private String h5Register;/*H5注册*/
	private String activePeople;/*活跃人数*/
	private String learnTimes;/*学习次数*/
	private String subscribePeople;/*订阅人数*/
	private String subscribeTimes;/*订阅次数*/
	private String subscribeAmount;/*订阅金额*/

}

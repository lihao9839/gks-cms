package com.audioapp.cms.dto;

import lombok.Data;

@Data
public class TotalListDTO {
    private String regUsers;//注册人数
    private String appUsers;//app人数
    private String h5Users;//h5人数
    private String actUsers;//活跃人数
    private String lenTimes;//学习次数
    private String subTimes;//订阅人数
    private String subUsers;//订阅次数
    private String subAmt;//订阅金额
}

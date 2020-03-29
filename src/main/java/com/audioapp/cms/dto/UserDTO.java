package com.audioapp.cms.dto;

import java.util.Date;

import com.audioapp.cms.constant.DateConstant;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    private String userName;

    private String password;

    private String realName;

    private String roleType;

    private String status;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;
    
}
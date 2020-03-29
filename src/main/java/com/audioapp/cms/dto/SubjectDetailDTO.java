package com.audioapp.cms.dto;

import lombok.Data;

import javax.security.auth.Subject;

@Data
public class SubjectDetailDTO extends SubjectDTO {

    private String realName;//讲师姓名
    private String userName;//讲师账号
}

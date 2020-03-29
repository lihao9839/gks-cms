package com.audioapp.cms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SubjectDTO {
    private Long id;

    private String subjectName;

    private Long teacherId;

    private String states;

    private Date publishDate;

    private Double price;

    private String picUrl;

    private String freeCourseFlag;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String subjectIntroduce;//专栏简介
    private String subjectIntroduceDetail;//专栏介绍（详情）
    private String teacherInterduce;//主讲人介绍

    public void setSubjectIntroduce(String subjectIntroduce) {
        this.subjectIntroduce = subjectIntroduce == null ? null : subjectIntroduce.trim();
    }
}
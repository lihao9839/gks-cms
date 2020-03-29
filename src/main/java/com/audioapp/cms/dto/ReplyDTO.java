package com.audioapp.cms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {
    private Long id;

    private Long objectId;

    private String replayContent;

    private String replayStatus;

    private String status;

    private Long thumbNum;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String type;

    private String userName;

    private String teacherReply;

    public void setReplayContent(String replayContent) {
        this.replayContent = replayContent == null ? null : replayContent.trim();
    }

    public void setReplayStatus(String replayStatus) {
        this.replayStatus = replayStatus == null ? null : replayStatus.trim();
    }
}
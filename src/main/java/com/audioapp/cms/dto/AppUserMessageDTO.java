package com.audioapp.cms.dto;

import java.util.Date;

public class AppUserMessageDTO {
    private Long id;

    private String title;

    private String content;

    private Date senddate;

    private Integer isvisible;

    private String username;

    private Date createddate;

    private Date updateddate;

    private Integer ispush;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    public Integer getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(Integer isvisible) {
        this.isvisible = isvisible;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public Integer getIspush() {
        return ispush;
    }

    public void setIspush(Integer ispush) {
        this.ispush = ispush;
    }
}
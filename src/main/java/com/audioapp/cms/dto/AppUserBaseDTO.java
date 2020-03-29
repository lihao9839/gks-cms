package com.audioapp.cms.dto;

import java.util.Date;

public class AppUserBaseDTO {
    private Long id;

    private String username;

    private String nickname;

    private String profileurl;

    private String signature;

    private String refree;

    private Integer freenum;

    private Date registertime;

    private Integer registertype;

    private Date createdate;

    private Date updatedate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl == null ? null : profileurl.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getRefree() {
        return refree;
    }

    public void setRefree(String refree) {
        this.refree = refree == null ? null : refree.trim();
    }

    public Integer getFreenum() {
        return freenum;
    }

    public void setFreenum(Integer freenum) {
        this.freenum = freenum;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public Integer getRegistertype() {
        return registertype;
    }

    public void setRegistertype(Integer registertype) {
        this.registertype = registertype;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}
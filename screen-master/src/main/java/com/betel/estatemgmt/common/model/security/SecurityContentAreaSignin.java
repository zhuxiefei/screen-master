package com.betel.estatemgmt.common.model.security;

import java.util.Date;

public class SecurityContentAreaSignin {
    private String contSignId;

    private String contentId;

    private String signinId;

    private String areaId;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "SecurityContentAreaSignin{" +
                "contSignId='" + contSignId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", signinId='" + signinId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getContSignId() {
        return contSignId;
    }

    public void setContSignId(String contSignId) {
        this.contSignId = contSignId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getSigninId() {
        return signinId;
    }

    public void setSigninId(String signinId) {
        this.signinId = signinId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
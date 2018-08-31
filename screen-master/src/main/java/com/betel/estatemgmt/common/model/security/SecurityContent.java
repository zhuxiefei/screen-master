package com.betel.estatemgmt.common.model.security;

import java.util.Date;

public class SecurityContent {
    private String contentId;

    private String contentNo;

    private String areaId;

    private String signinAddress;

    private String inspectionTime;

    private String contentDesc;

    private Date createTime;

    private Date updateTime;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    @Override
    public String toString() {
        return "SecurityContent{" +
                "contentId='" + contentId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", areaId='" + areaId + '\'' +
                ", signinAddress='" + signinAddress + '\'' +
                ", inspectionTime='" + inspectionTime + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo == null ? null : contentNo.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getSigninAddress() {
        return signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        this.signinAddress = signinAddress == null ? null : signinAddress.trim();
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime == null ? null : inspectionTime.trim();
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc == null ? null : contentDesc.trim();
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
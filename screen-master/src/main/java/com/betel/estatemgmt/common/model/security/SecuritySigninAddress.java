package com.betel.estatemgmt.common.model.security;

import java.util.Date;

public class SecuritySigninAddress {
    private String signinId;

    private String signinAddress;

    private String signinDesc;

    private String areaId;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "SecuritySigninAddress{" +
                "signinId='" + signinId + '\'' +
                ", signinAddress='" + signinAddress + '\'' +
                ", signinDesc='" + signinDesc + '\'' +
                ", areaId='" + areaId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getSigninId() {
        return signinId;
    }

    public void setSigninId(String signinId) {
        this.signinId = signinId == null ? null : signinId.trim();
    }

    public String getSigninAddress() {
        return signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        this.signinAddress = signinAddress == null ? null : signinAddress.trim();
    }

    public String getSigninDesc() {
        return signinDesc;
    }

    public void setSigninDesc(String signinDesc) {
        this.signinDesc = signinDesc == null ? null : signinDesc.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
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
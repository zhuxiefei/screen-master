package com.betel.estatemgmt.common.model.security;

import java.util.Date;

public class SecuritySigninNumber {
    private String numId;

    private Double longitude;

    private Double latitude;

    private String address;

    private String remarks;

    private String recordId;

    private Date createTime;

    private Date updateTime;
    private String signinEmpName;
    private String signinEmpId;

    @Override
    public String toString() {
        return "SecuritySigninNumber{" +
                "numId='" + numId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", remarks='" + remarks + '\'' +
                ", recordId='" + recordId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", signinEmpName='" + signinEmpName + '\'' +
                ", signinEmpId='" + signinEmpId + '\'' +
                '}';
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getSigninEmpName() {
        return signinEmpName;
    }

    public void setSigninEmpName(String signinEmpName) {
        this.signinEmpName = signinEmpName;
    }

    public String getSigninEmpId() {
        return signinEmpId;
    }

    public void setSigninEmpId(String signinEmpId) {
        this.signinEmpId = signinEmpId;
    }
}
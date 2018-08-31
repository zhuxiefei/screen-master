package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class MemberAuth {
    private Long authId;

    private String applicantId;

    private String houseId;

    private String applicantName;

    private String phoneNum;

    private Integer authStatus;

    private String operatorId;

    private Integer reviewStatus;

    private String reviewAdvice;

    private Date reviewTime;

    private Date authTime;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId == null ? null : applicantId.trim();
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName == null ? null : applicantName.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewAdvice() {
        return reviewAdvice;
    }

    public void setReviewAdvice(String reviewAdvice) {
        this.reviewAdvice = reviewAdvice == null ? null : reviewAdvice.trim();
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    @Override
    public String toString() {
        return "MemberAuth{" +
                "authId=" + authId +
                ", applicantId='" + applicantId + '\'' +
                ", houseId=" + houseId +
                ", applicantName='" + applicantName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", authStatus=" + authStatus +
                ", operatorId='" + operatorId + '\'' +
                ", reviewStatus=" + reviewStatus +
                ", reviewAdvice='" + reviewAdvice + '\'' +
                ", reviewTime=" + reviewTime +
                ", authTime=" + authTime +
                '}';
    }
}
package com.betel.estatemgmt.business.web.auth.model;

import java.util.Date;

/**
 * <p>
 * 户主信息
 * </p>
 * ClassName: HouseOwneAuth <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/20 9:22 <br/>
 * Version: 1.0 <br/>
 */
public class HouseOwneAuth {

    /**
     * 认证ID
     */
    private Long authId;

    /**
     * 房屋号
     */
    private String houseNum;

    /**
     * 申请人名称
     */
    private String applicantName;

    /**
     * 申请时间
     */
    private Date authTime;

    /**
     * 认证状态：1为正常，4为已撤销，5为已删除
     */
    private Integer authStatus;

    /**
     * 认证状态：1为认证中，2为认证通过，3为不通过
     */
    private Integer reviewStatus;

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum == null ? null : houseNum.trim();
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName == null ? null : applicantName.trim();
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseOwneAuth{");
        sb.append("authId=").append(authId);
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", applicantName='").append(applicantName).append('\'');
        sb.append(", authTime=").append(authTime);
        sb.append(", authStatus=").append(authStatus);
        sb.append('}');
        return sb.toString();
    }
}

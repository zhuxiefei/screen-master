package com.betel.estatemgmt.business.userapp.auth.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: MemberAuthInfo <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:40 <br/>
 * Version: 1.0 <br/>
 */
public class MemberAuthInfo {

    /**
     * 认证ID
     */
    private Long authId;

    /**
     * 房间号
     */
    private String houseNum;

    /**
     * 申请人名称
     */
    private String applicantName;

    /**
     * 申请人手机号码
     */
    private String phone;

    /**
     * 申请人身份证号
     */
    private String applicantIdNum;

    /**
     * 户主名称
     *
     */
    private String houseOwner;

    /**
     * 申请时间
     */
    private Date authTime;

    /**
     * 通知状态
     */
    private Integer authStatus;

    private Integer reviewStatus;

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
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
        this.houseNum = houseNum;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApplicantIdNum() {
        return applicantIdNum;
    }

    public void setApplicantIdNum(String applicantIdNum) {
        this.applicantIdNum = applicantIdNum;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public Date getAuthTime() {
        if (authTime == null) {
            return null;
        }
        return (Date) authTime.clone();
    }

    public void setAuthTime(Date authTime) {
        if (authTime == null) {
            this.authTime = null;
        } else {
            this.authTime = (Date) authTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberAuthInfo{");
        sb.append("authId=").append(authId);
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", applicantName='").append(applicantName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", applicantIdNum='").append(applicantIdNum).append('\'');
        sb.append(", houseOwner='").append(houseOwner).append('\'');
        sb.append(", authTime=").append(authTime);
        sb.append(", authStatus=").append(authStatus);
        sb.append(", reviewStatus=").append(reviewStatus);
        sb.append('}');
        return sb.toString();
    }
}

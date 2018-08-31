package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * 用户认证申请列表
 * </p>
 * ClassName: UserHouseAuth <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/19 17:01 <br/>
 * Version: 1.0 <br/>
 */
public class UserHouseAuth {
    /**
     * 申请人姓名
     */
    private String applicantName;
    /**
     * 房屋id
     */
    private String houseId;
    /**
     * 楼宇名
     */
    private String buildingName;
    /**
     * 单元
     */
    private String unitName;
    /**
     * 房屋号
     */
    private String houseNum;
    /**
     * 房屋号 楼宇+单元+楼号
     */
    private String houseName;
    /**
     * 认证类型
     */
    private String authType;
    /**
     * 审核状态
     */
    private String reviewStatus;
    /**
     * 认证状态
     */
    private String authStatus;
    /**
     * 认证Id
     */
    private String authId;
    /**
     * 楼宇ID
     */
    private Long buildingId;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserHouseAuth{");
        sb.append("applicantName='").append(applicantName).append('\'');
        sb.append(", houseId=").append(houseId);
        sb.append(", buildingName='").append(buildingName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", houseName='").append(houseName).append('\'');
        sb.append(", authType='").append(authType).append('\'');
        sb.append(", reviewStatus='").append(reviewStatus).append('\'');
        sb.append(", authStatus='").append(authStatus).append('\'');
        sb.append(", authId='").append(authId).append('\'');
        sb.append(", buildingId=").append(buildingId);
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * 用户已拥有的房屋
 * </p>
 * ClassName: $CLASSNAME$ <br/>
 * Author: zhouye  <br/>
 * Date: $DATA$ 14:15$ <br/>
 * Version: $VERSION$ <br/>
 */
public class AuthHouse {

    /**
     * 房屋id
     */
    private String houseId;

    /**
     * 房屋名称
     */
    private String houseName;

    /**
     * 认证id
     */
    private String authId;

    /**
     * 类型 1；户主，2.成员认证
     */
    private int authType;

    /**
     * 认证状态：
     * 1正常
     * 4为已撤销
     * 5为已删除
     */
    private int authStatus;

    /**
     * 审核状态
     * 1为待审核，
     * 2为核准，
     * 3为驳回
     */
    private int reviewStatus;

    private Long buildingId;

    private String applicantName;

    private String unitId;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        return "AuthHouse{" +
                "houseId='" + houseId + '\'' +
                ", houseName='" + houseName + '\'' +
                ", authId='" + authId + '\'' +
                ", authType=" + authType +
                ", authStatus=" + authStatus +
                ", reviewStatus=" + reviewStatus +
                ", buildingId=" + buildingId +
                ", applicantName='" + applicantName + '\'' +
                ", unitId='" + unitId + '\'' +
                '}';
    }
}

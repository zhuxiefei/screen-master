package com.betel.estatemgmt.business.web.auth.model;

/**
 * <p>
 * 认证详情
 * </p>
 * ClassName: HouseOwneAuthInfo <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/16 10:32 <br/>
 * Version: 1.0 <br/>
 */
public class HouseOwneAuthInfo {

    /**
     * 新户主
     */
    private HouseOwneInfo newHouseOwneInfo;

    /**
     * 原户主
     */
    private HouseOwneInfo oldHouseOwneInfo;

    /**
     * 认证ID
     */
    private Long authId;

    /*
    认证状态
     */
    private Integer authStatus;
    /*
    审核状态
     */
    private Integer reviewStatus;
    /*
   审核意见
    */
    private String reviewAdvice;

    public String getReviewAdvice() {
        return reviewAdvice;
    }

    public void setReviewAdvice(String reviewAdvice) {
        this.reviewAdvice = reviewAdvice;
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

    public HouseOwneInfo getNewHouseOwneInfo() {
        return newHouseOwneInfo;
    }

    public void setNewHouseOwneInfo(HouseOwneInfo newHouseOwneInfo) {
        this.newHouseOwneInfo = newHouseOwneInfo;
    }

    public HouseOwneInfo getOldHouseOwneInfo() {
        return oldHouseOwneInfo;
    }

    public void setOldHouseOwneInfo(HouseOwneInfo oldHouseOwneInfo) {
        this.oldHouseOwneInfo = oldHouseOwneInfo;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseOwneAuthInfo{");
        sb.append("newHouseOwneInfo=").append(newHouseOwneInfo);
        sb.append(", oldHouseOwneInfo=").append(oldHouseOwneInfo);
        sb.append(", authId=").append(authId);
        sb.append('}');
        return sb.toString();
    }


}

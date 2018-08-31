package com.betel.estatemgmt.business.web.repair.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 分页维修回参
 * </p>
 * ClassName: RepairPageReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:45 <br/>
 * Version: 1.0 <br/>
 */
public class RepairPageReq extends Page {

    private Long buildingId;

    private Long unitId;

    private String houseNum;

    private String userName;

    private String startTime;

    private String endTime;

    private String orderNo;

    //报修区域:1为个人,2为公共区域
    private Integer orderArea;

    //报修状态:1为待维修，2为已接单，3为已完成 4为已取消
    private Integer orderStatus;

    private String isOthers;

    //1代表按催单次数从多到少排列，0代表不按
    private String isUrge;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getIsUrge() {
        return isUrge;
    }

    public void setIsUrge(String isUrge) {
        this.isUrge = isUrge;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(Integer orderArea) {
        this.orderArea = orderArea;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
    }

    @Override
    public String toString() {
        return "RepairPageReq{" +
                "buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", houseNum='" + houseNum + '\'' +
                ", userName='" + userName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderArea=" + orderArea +
                ", orderStatus=" + orderStatus +
                ", isOthers='" + isOthers + '\'' +
                ", isUrge='" + isUrge + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

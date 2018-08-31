package com.betel.estatemgmt.business.web.house.model;

import java.util.Date;

/**
 * <p>
 * 房屋列表回参
 * </p>
 * ClassName: HouseInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 13:55 <br/>
 * Version: 1.0 <br/>
 */
public class HouseInfo {
    private String houseId;

    private String buildingName;

    private String unitName;

    private String houseNum;

    private String typeName;

    private Date createTime;

    private String userName;

    private Date deliverTime;

    private Double floorArea;

    private Double interFloorArea;

    private Integer buyCounts;

    private Integer rentCounts;

    private Integer displayOrder;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        if(createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDeliverTime() {
        if(deliverTime == null){
            return null;
        }
        return (Date)deliverTime.clone();
    }

    public void setDeliverTime(Date deliverTime) {
        if(deliverTime == null){
            this.deliverTime = null;
        }else{
            this.deliverTime = (Date)deliverTime.clone();
        }
    }

    public Double getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
    }

    public Double getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(Double interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    public Integer getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(Integer buyCounts) {
        this.buyCounts = buyCounts;
    }

    public Integer getRentCounts() {
        return rentCounts;
    }

    public void setRentCounts(Integer rentCounts) {
        this.rentCounts = rentCounts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseInfo{");
        sb.append("houseId=").append(houseId);
        sb.append(", buildingName='").append(buildingName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", deliverTime=").append(deliverTime);
        sb.append(", floorArea=").append(floorArea);
        sb.append(", interFloorArea=").append(interFloorArea);
        sb.append(", buyCounts=").append(buyCounts);
        sb.append(", rentCounts=").append(rentCounts);
        sb.append(", displayOrder=").append(displayOrder);
        sb.append('}');
        return sb.toString();
    }
}

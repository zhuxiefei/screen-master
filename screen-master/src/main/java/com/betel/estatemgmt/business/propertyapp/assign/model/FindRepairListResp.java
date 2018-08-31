package com.betel.estatemgmt.business.propertyapp.assign.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRepairListResp <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/22 14:07 <br/>
 * Version: 1.0 <br/>
 */
public class FindRepairListResp {

    String orderNo;

    int orderStatus;

    String houseNum;

    String buildingName;

    String unitName;

    int orderArea;

    String childName;

    String parentName;

    Date createTime;

    String contactPhone;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
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

    public int getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(int orderArea) {
        this.orderArea = orderArea;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindRepairListResp{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", buildingName='").append(buildingName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", orderArea=").append(orderArea);
        sb.append(", childName='").append(childName).append('\'');
        sb.append(", parentName='").append(parentName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", contactPhone='").append(contactPhone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

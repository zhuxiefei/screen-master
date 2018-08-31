package com.betel.estatemgmt.business.web.repair.model;

import java.util.Date;

/**
 * <p>
 * 分页维修回参
 * </p>
 * ClassName: OrderInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:48 <br/>
 * Version: 1.0 <br/>
 */
public class OrderInfo {

    private String orderNo;

    //房间编号（楼宇单元房号）
    private String houseNo;

    //报修人
    private String userName;

    //报修联系电话
    private String userPhone;

    private Integer orderStatus;

    //报修日期
    private Date createTime;

    private Integer orderArea;

    private String orderType;

    private String orderDesc;

    private Date appointTime;

    private String operatorName;

    private Integer urgeCount;

    public Integer getUrgeCount() {
        return urgeCount;
    }

    public void setUrgeCount(Integer urgeCount) {
        this.urgeCount = urgeCount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        if (createTime == null){
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

    public Integer getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(Integer orderArea) {
        this.orderArea = orderArea;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Date getAppointTime() {
        if (appointTime == null){
            return null;
        }
        return (Date) appointTime.clone();
    }

    public void setAppointTime(Date appointTime) {
        if(appointTime == null){
            this.appointTime = null;
        }else{
            this.appointTime = (Date)appointTime.clone();
        }
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderInfo{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", houseNo='").append(houseNo).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userPhone='").append(userPhone).append('\'');
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", orderArea=").append(orderArea);
        sb.append(", orderType='").append(orderType).append('\'');
        sb.append(", orderDesc='").append(orderDesc).append('\'');
        sb.append(", appointTime=").append(appointTime);
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", urgeCount=").append(urgeCount);
        sb.append('}');
        return sb.toString();
    }
}

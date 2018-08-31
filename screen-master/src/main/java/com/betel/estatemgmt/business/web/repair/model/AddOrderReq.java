package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 新增维修单入参
 * </p>
 * ClassName: AddOrderReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:03 <br/>
 * Version: 1.0 <br/>
 */
public class AddOrderReq {

    private String houseId;

    private String orderContact;

    private String contactPhone;

    private Integer orderArea;

    private Long orderType;

    private String orderDesc;

    private String appointTime;

    private String operatorId;

    private String operatorPhone;

    private String operatorDepartment;

    private String operatorHead;

    private String operatorName;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorDepartment() {
        return operatorDepartment;
    }

    public void setOperatorDepartment(String operatorDepartment) {
        this.operatorDepartment = operatorDepartment;
    }

    public String getOperatorHead() {
        return operatorHead;
    }

    public void setOperatorHead(String operatorHead) {
        this.operatorHead = operatorHead;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getOrderContact() {
        return orderContact;
    }

    public void setOrderContact(String orderContact) {
        this.orderContact = orderContact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(Integer orderArea) {
        this.orderArea = orderArea;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    @Override
    public String toString() {
        return "AddOrderReq{" +
                "houseId=" + houseId +
                ", orderContact='" + orderContact + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", orderArea=" + orderArea +
                ", orderType=" + orderType +
                ", orderDesc='" + orderDesc + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operatorPhone='" + operatorPhone + '\'' +
                ", operatorDepartment='" + operatorDepartment + '\'' +
                ", operatorHead='" + operatorHead + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

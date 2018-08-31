package com.betel.estatemgmt.business.web.repair.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 维修单详情回参
 * </p>
 * ClassName: OrderDetail <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:15 <br/>
 * Version: 1.0 <br/>
 */
public class OrderDetail {

    private String orderNo;

    private String houseNo;

    private String orderContact;

    private String contactPhone;

    private Integer orderArea;

    private String orderType;

    private String orderDesc;

    private Date appointTime;

    private List<String> pictureUrl;

    private String receiverName;

    private String receiver;

    private String receiverPhone;

    private String receiverDepartment;

    private String receiverHead;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverDepartment() {
        return receiverDepartment;
    }

    public void setReceiverDepartment(String receiverDepartment) {
        this.receiverDepartment = receiverDepartment;
    }

    public String getReceiverHead() {
        return receiverHead;
    }

    public void setReceiverHead(String receiverHead) {
        this.receiverHead = receiverHead;
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

    public List<String> getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(List<String> pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDetail{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", houseNo='").append(houseNo).append('\'');
        sb.append(", orderContact='").append(orderContact).append('\'');
        sb.append(", contactPhone='").append(contactPhone).append('\'');
        sb.append(", orderArea=").append(orderArea);
        sb.append(", orderType='").append(orderType).append('\'');
        sb.append(", orderDesc='").append(orderDesc).append('\'');
        sb.append(", appointTime=").append(appointTime);
        sb.append(", pictureUrl=").append(pictureUrl);
        sb.append(", receiverName='").append(receiverName).append('\'');
        sb.append(", receiver='").append(receiver).append('\'');
        sb.append(", receiverPhone='").append(receiverPhone).append('\'');
        sb.append(", receiverDepartment='").append(receiverDepartment).append('\'');
        sb.append(", receiverHead='").append(receiverHead).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

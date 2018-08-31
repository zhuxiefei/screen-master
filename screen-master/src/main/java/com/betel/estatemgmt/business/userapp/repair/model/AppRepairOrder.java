package com.betel.estatemgmt.business.userapp.repair.model;

import java.util.List;

/**
 * <p>
 * 报修记录model
 * </p>
 * ClassName: AppRepairOrder <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:28 <br/>
 * Version: 1.0 <br/>
 */
public class AppRepairOrder {
    /**
     * 房间id
     */
    private String houseId;
    /**
     * 报修订单id
     */
    private String orderNo;
    /**
     * 报修区域
     */
    private Integer orderArea;
    /**
     * 报修类型
     */
    private String orderType;

    /**
     * 报修类型
     */
    private String orderTypeName;

    /**
     * 订单进度
     */
    private int orderStatus;
    /**
     * 是否评论
     */
    private int isReply;
    /**
     * 首张图片的url
     */
    private String pictureUrl;
    /**
     * 维修订单相关图片
     */
    private List<String> pictureUrls;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 接单时间
     */
    private String acceptTime;
    /**
     * 预约时间
     */
    private String appointTime;
    /**
     * 结束时间
     */
    private String finishTime;
    /**
     * 报修联系人姓名
     */
    private String orderContact;
    /**
     * 报修联系人号码
     */
    private String contactPhone;
    /**
     * 维修人员的姓名
     */
    private String operatorName;
    /**
     * 维修人员的号码
     */
    private String operatorPhone;
    /**
     * 维修问题描述
     */
    private String orderDesc;

    /**
     * 图片id
     */
    private String pictureIds;

    /**
     * 用户id
     */
    private String user;

    private List<String> operatorNames;

    private String cancelTime;

    private String estateId;

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    private String houseNo;

    public String getOrderTypeDes() {
        return orderTypeName;
    }

    public void setOrderTypeDes(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(String pictureIds) {
        this.pictureIds = pictureIds;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public List<String> getOperatorNames() {
        return operatorNames;
    }

    public void setOperatorNames(List<String> operatorNames) {
        this.operatorNames = operatorNames;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppRepairOrder{");
        sb.append("houseId='").append(houseId).append('\'');
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append(", orderArea=").append(orderArea);
        sb.append(", orderType='").append(orderType).append('\'');
        sb.append(", orderTypeName='").append(orderTypeName).append('\'');
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", isReply=").append(isReply);
        sb.append(", pictureUrl='").append(pictureUrl).append('\'');
        sb.append(", pictureUrls=").append(pictureUrls);
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", acceptTime='").append(acceptTime).append('\'');
        sb.append(", appointTime='").append(appointTime).append('\'');
        sb.append(", finishTime='").append(finishTime).append('\'');
        sb.append(", orderContact='").append(orderContact).append('\'');
        sb.append(", contactPhone='").append(contactPhone).append('\'');
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", operatorPhone='").append(operatorPhone).append('\'');
        sb.append(", orderDesc='").append(orderDesc).append('\'');
        sb.append(", pictureIds='").append(pictureIds).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", operatorNames=").append(operatorNames);
        sb.append(", cancelTime='").append(cancelTime).append('\'');
        sb.append(", estateId='").append(estateId).append('\'');
        sb.append(", houseNo='").append(houseNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

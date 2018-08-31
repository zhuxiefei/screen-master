package com.betel.estatemgmt.common.model.repair;

import java.util.Date;

public class RepairOrder {
    private String orderNo;

    private String userId;

    private String orderContact;

    private String contactPhone;

    private Integer orderArea;

    private String houseId;

    private Long orderType;

    private String orderDesc;

    private Integer orderStatus;

    private Integer urgeCount;

    private Date lastUrgeTime;

    private String operatorId;

    private String orderPic;

    private Date appointTime;

    private Date acceptTime;

    private Date finishTime;

    private Date createTime;

    private Date cancelTime;

    private Integer isDelete;

    private String description;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrderContact() {
        return orderContact;
    }

    public void setOrderContact(String orderContact) {
        this.orderContact = orderContact == null ? null : orderContact.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(Integer orderArea) {
        this.orderArea = orderArea;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
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
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUrgeCount() {
        return urgeCount;
    }

    public void setUrgeCount(Integer urgeCount) {
        this.urgeCount = urgeCount;
    }

    public Date getLastUrgeTime() {
        return lastUrgeTime;
    }

    public void setLastUrgeTime(Date lastUrgeTime) {
        this.lastUrgeTime = lastUrgeTime;
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "orderNo='" + orderNo + '\'' +
                ", userId='" + userId + '\'' +
                ", orderContact='" + orderContact + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", orderArea=" + orderArea +
                ", houseId=" + houseId +
                ", orderType=" + orderType +
                ", orderDesc='" + orderDesc + '\'' +
                ", orderStatus=" + orderStatus +
                ", urgeCount=" + urgeCount +
                ", lastUrgeTime=" + lastUrgeTime +
                ", operatorId='" + operatorId + '\'' +
                ", orderPic='" + orderPic + '\'' +
                ", appointTime=" + appointTime +
                ", acceptTime=" + acceptTime +
                ", finishTime=" + finishTime +
                ", createTime=" + createTime +
                ", cancelTime=" + cancelTime +
                ", isDelete=" + isDelete +
                ", description='" + description + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
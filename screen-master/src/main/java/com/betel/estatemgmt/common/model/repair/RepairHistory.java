package com.betel.estatemgmt.common.model.repair;

import java.util.Date;

public class RepairHistory {
    private Long historyId;

    private String orderNo;

    private String operatorId;

    private int operatorType;

    private int historyType;

    private String operatorDepartment;

    private String operatorHead;

    private String operatorName;

    private String receiver;

    private String receiverPhone;

    private String receiverHead;

    private String receiverDepartment;

    private String receiverName;

    private String description;

    private Date createTime;

    private String historyDesc;

    public String getHistoryDesc() {
        return historyDesc;
    }

    public void setHistoryDesc(String historyDesc) {
        this.historyDesc = historyDesc;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int operatorType) {
        this.operatorType = operatorType;
    }

    public int getHistoryType() {
        return historyType;
    }

    public void setHistoryType(int historyType) {
        this.historyType = historyType;
    }

    public String getOperatorDepartment() {
        return operatorDepartment;
    }

    public void setOperatorDepartment(String operatorDepartment) {
        this.operatorDepartment = operatorDepartment == null ? null : operatorDepartment.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public String getReceiverHead() {
        return receiverHead;
    }

    public void setReceiverHead(String receiverHead) {
        this.receiverHead = receiverHead == null ? null : receiverHead.trim();
    }

    public String getReceiverDepartment() {
        return receiverDepartment;
    }

    public void setReceiverDepartment(String receiverDepartment) {
        this.receiverDepartment = receiverDepartment == null ? null : receiverDepartment.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatorHead() {
        return operatorHead;
    }

    public void setOperatorHead(String operatorHead) {
        this.operatorHead = operatorHead;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RepairHistory{");
        sb.append("historyId=").append(historyId);
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append(", operatorId='").append(operatorId).append('\'');
        sb.append(", operatorType=").append(operatorType);
        sb.append(", historyType=").append(historyType);
        sb.append(", operatorDepartment='").append(operatorDepartment).append('\'');
        sb.append(", operatorHead='").append(operatorHead).append('\'');
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", receiver='").append(receiver).append('\'');
        sb.append(", receiverPhone='").append(receiverPhone).append('\'');
        sb.append(", receiverHead='").append(receiverHead).append('\'');
        sb.append(", receiverDepartment='").append(receiverDepartment).append('\'');
        sb.append(", receiverName='").append(receiverName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", historyDesc='").append(historyDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
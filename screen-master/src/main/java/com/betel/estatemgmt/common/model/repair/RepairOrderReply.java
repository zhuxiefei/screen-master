package com.betel.estatemgmt.common.model.repair;

import java.util.Date;

public class RepairOrderReply {
    private Long replyId;

    private String orderNo;

    private Integer serviceOnTime;

    private Integer serviceAttitude;

    private Integer serviceQuality;

    private String serviceDesc;

    private Date createTime;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getServiceOnTime() {
        return serviceOnTime;
    }

    public void setServiceOnTime(Integer serviceOnTime) {
        this.serviceOnTime = serviceOnTime;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Integer getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Integer serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc == null ? null : serviceDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RepairOrderReply{" +
                "replyId=" + replyId +
                ", orderNo='" + orderNo + '\'' +
                ", serviceOnTime=" + serviceOnTime +
                ", serviceAttitude=" + serviceAttitude +
                ", serviceQuality=" + serviceQuality +
                ", serviceDesc='" + serviceDesc + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
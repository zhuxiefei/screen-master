package com.betel.estatemgmt.common.model.expense;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseFlow {
    private String flowNo;

    private BigDecimal totalAmount;

    private Integer chargeWay;

    /**
     * 0代表线上缴费  1代表线下缴费
     */
    private Integer isOnline;

    private Integer flowStatus;

    private String billPayer;

    private Date createTime;

    private Date payTime;

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(Integer chargeWay) {
        this.chargeWay = chargeWay;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getBillPayer() {
        return billPayer;
    }

    public void setBillPayer(String billPayer) {
        this.billPayer = billPayer == null ? null : billPayer.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
package com.betel.estatemgmt.business.userapp.pay.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 账单详情model
 * </p>
 * ClassName: PayList <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 13:35 <br/>
 * Version: 1.0 <br/>
 */
public class PayList {
    /**
     *账单编号
     */
    private String billNo;
    /**
     *收费项目的id
     */
    private Long itemId;
    /**
     *到账时间
     */
    private Date deadLine;
    /**
     *账单类别 1:物业 2：水电
     */
    private Integer itemType;
    /**
     *房屋信息
     */
    private String houseInfo;
    /**
    账单状态（1为欠缴费（未缴费），2为已缴费）
     */
    private Integer billStatus;
    /**
     *滞纳金
     */
    private BigDecimal overdueFine;
    /**
     *代缴金额
     */
    private BigDecimal billTotelAmount;
    /**
     *开始时间
     */
    private String startTime;
    /**
     *结束时间
     */
    private String endTime;

    /**
     * 索取票据状态 1 未索取，2：索取，3：已送达
     */
    private Integer demandBillStatus;

    public Integer getDemandBillStatus() {
        return demandBillStatus;
    }

    public void setDemandBillStatus(Integer demandBillStatus) {
        this.demandBillStatus = demandBillStatus;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getDeadLine() {
        return (Date) deadLine.clone();
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = (Date) deadLine.clone();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public BigDecimal getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(BigDecimal overdueFine) {
        this.overdueFine = overdueFine;
    }

    public BigDecimal getBillTotelAmount() {
        return billTotelAmount;
    }

    public void setBillTotelAmount(BigDecimal billTotelAmount) {
        this.billTotelAmount = billTotelAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PayList{" +
                "billNo='" + billNo + '\'' +
                ", itemId=" + itemId +
                ", deadLine=" + deadLine +
                ", itemType=" + itemType +
                ", houseInfo='" + houseInfo + '\'' +
                ", billStatus=" + billStatus +
                ", overdueFine=" + overdueFine +
                ", billTotelAmount=" + billTotelAmount +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", demandBillStatus=" + demandBillStatus +
                '}';
    }
}

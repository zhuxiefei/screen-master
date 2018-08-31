package com.betel.estatemgmt.common.model.expense;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseBill {
    private String billNo;

    private Long itemId;

    private String houseId;

    private String houseOwnerName;

    private String houseOwnerPhone;

    private BigDecimal billAmount;

    private Date deadLine;

    private Date startTime;

    private Date endTime;

    private Integer billStatus;

    private Integer urgeCount;

    private Date lastUrgeTime;

    private Integer demandBillStatus;

    private Date demandBillTime;

    private String flowNo;

    private BigDecimal overdueFine;

    private Integer isPrint;

    private Date createTime;

    private Integer isDelete;

    private Integer itemType;


    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseOwnerName() {
        return houseOwnerName;
    }

    public void setHouseOwnerName(String houseOwnerName) {
        this.houseOwnerName = houseOwnerName == null ? null : houseOwnerName.trim();
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

    public Integer getDemandBillStatus() {
        return demandBillStatus;
    }

    public void setDemandBillStatus(Integer demandBillStatus) {
        this.demandBillStatus = demandBillStatus;
    }

    public Date getDemandBillTime() {
        return demandBillTime;
    }

    public void setDemandBillTime(Date demandBillTime) {
        this.demandBillTime = demandBillTime;
    }

    public String getHouseOwnerPhone() {
        return houseOwnerPhone;
    }

    public void setHouseOwnerPhone(String houseOwnerPhone) {
        this.houseOwnerPhone = houseOwnerPhone == null ? null : houseOwnerPhone.trim();
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public BigDecimal getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(BigDecimal overdueFine) {
        this.overdueFine = overdueFine;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "ExpenseBill{" +
                "billNo='" + billNo + '\'' +
                ", itemId=" + itemId +
                ", houseId=" + houseId +
                ", houseOwnerName='" + houseOwnerName + '\'' +
                ", houseOwnerPhone='" + houseOwnerPhone + '\'' +
                ", billAmount=" + billAmount +
                ", deadLine=" + deadLine +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", billStatus=" + billStatus +
                ", urgeCount=" + urgeCount +
                ", lastUrgeTime=" + lastUrgeTime +
                ", demandBillStatus=" + demandBillStatus +
                ", demandBillTime=" + demandBillTime +
                ", flowNo='" + flowNo + '\'' +
                ", overdueFine=" + overdueFine +
                ", isPrint=" + isPrint +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                ", itemType=" + itemType +
                '}';
    }
}
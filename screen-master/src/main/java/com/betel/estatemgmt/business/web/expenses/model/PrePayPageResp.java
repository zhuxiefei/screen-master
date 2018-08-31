package com.betel.estatemgmt.business.web.expenses.model;

import java.util.Date;

/**
 * <p>
 * 分页查询所有预交费账单回参
 * </p>
 * ClassName: PrePayPageResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/19 12:47 <br/>
 * Version: 1.0 <br/>
 */
public class PrePayPageResp {

    private  String houseInfo;
    private  String houseMaster;
    private  String billStatus;
    private  String phone;
    private  String billingStartTime;
    private  String billingEndTime;
    private  String itemType;
    private  String overduefinePrice;
    private  String billAmount;
    private  String totalAmount;
    private  String createTime;
    private  String billNo;
    private Long itemId;
    private Date deadLine;
    private Integer demandBillStatus;
    private Integer urgeCount;

    public Integer getDemandBillStatus() {
        return demandBillStatus;
    }

    public void setDemandBillStatus(Integer demandBillStatus) {
        this.demandBillStatus = demandBillStatus;
    }

    public Integer getUrgeCount() {
        return urgeCount;
    }

    public void setUrgeCount(Integer urgeCount) {
        this.urgeCount = urgeCount;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getHouseMaster() {
        return houseMaster;
    }

    public void setHouseMaster(String houseMaster) {
        this.houseMaster = houseMaster;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBillingStartTime() {
        return billingStartTime;
    }

    public void setBillingStartTime(String billingStartTime) {
        this.billingStartTime = billingStartTime;
    }

    public String getBillingEndTime() {
        return billingEndTime;
    }

    public void setBillingEndTime(String billingEndTime) {
        this.billingEndTime = billingEndTime;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getOverduefinePrice() {
        return overduefinePrice;
    }

    public void setOverduefinePrice(String overduefinePrice) {
        this.overduefinePrice = overduefinePrice;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrePayPageResp{");
        sb.append("houseInfo='").append(houseInfo).append('\'');
        sb.append(", houseMaster='").append(houseMaster).append('\'');
        sb.append(", billStatus='").append(billStatus).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", billingStartTime='").append(billingStartTime).append('\'');
        sb.append(", billingEndTime='").append(billingEndTime).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append(", overduefinePrice='").append(overduefinePrice).append('\'');
        sb.append(", billAmount='").append(billAmount).append('\'');
        sb.append(", totalAmount='").append(totalAmount).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", billNo='").append(billNo).append('\'');
        sb.append(", itemId=").append(itemId);
        sb.append(", deadLine=").append(deadLine);
        sb.append(", demandBillStatus=").append(demandBillStatus);
        sb.append(", urgeCount=").append(urgeCount);
        sb.append('}');
        return sb.toString();
    }
}

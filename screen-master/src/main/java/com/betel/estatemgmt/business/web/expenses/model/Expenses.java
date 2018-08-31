package com.betel.estatemgmt.business.web.expenses.model;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangjian on 2017/9/17.
 */
public class Expenses {
    private  String houseInfo="";
    private  String houseMaster="";
    private  String billStatus="";
    private  String phone="";
    private  String billingStartTime="";
    private  String billingEndTime="";
    private  String itemType="";
    private  String overduefinePrice="";
    private  String billAmount="0.00";
    private  String totalAmount="0.00";
    private  String receivableTotalAmount="0.00";
    private  String actualTotalAmount="0.00";
    private  String payTime="";
    private  String propertyFee="0.00";
    private  String publicUtilities="0.00";
    private  String chargeWay="";
    private  String createTime="";
    private  List<PayDetail> payDetails=null;
    private  String billNo="";
    private  List<Note> notes=null;
    private  String totalOverdueFinePrice="0.00";
    private  String itemPrice="0.00";
    private  String floorArea="0";
    private  String itemCycle="0";
    private  String deadLine="";
    private  String overdueFine="0";
    private Integer demandBillStatus;
    private Integer urgeCount;
    private Integer isOnline;
    private Date serverTime;

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

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

    public String getReceivableTotalAmount() {
        return receivableTotalAmount;
    }

    public void setReceivableTotalAmount(String receivableTotalAmount) {
        this.receivableTotalAmount = receivableTotalAmount;
    }

    public String getActualTotalAmount() {
        return actualTotalAmount;
    }

    public void setActualTotalAmount(String actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(String propertyFee) {
        this.propertyFee = propertyFee;
    }

    public String getPublicUtilities() {
        return publicUtilities;
    }

    public void setPublicUtilities(String publicUtilities) {
        this.publicUtilities = publicUtilities;
    }

    public String getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(String chargeWay) {
        this.chargeWay = chargeWay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<PayDetail> getPayDetails() {
        return payDetails;
    }

    public void setPayDetails(List<PayDetail> payDetails) {
        this.payDetails = payDetails;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getTotalOverdueFinePrice() {
        return totalOverdueFinePrice;
    }

    public void setTotalOverdueFinePrice(String totalOverdueFinePrice) {
        this.totalOverdueFinePrice = totalOverdueFinePrice;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getItemCycle() {
        return itemCycle;
    }

    public void setItemCycle(String itemCycle) {
        this.itemCycle = itemCycle;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(String overdueFine) {
        this.overdueFine = overdueFine;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Expenses{");
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
        sb.append(", receivableTotalAmount='").append(receivableTotalAmount).append('\'');
        sb.append(", actualTotalAmount='").append(actualTotalAmount).append('\'');
        sb.append(", payTime='").append(payTime).append('\'');
        sb.append(", propertyFee='").append(propertyFee).append('\'');
        sb.append(", publicUtilities='").append(publicUtilities).append('\'');
        sb.append(", chargeWay='").append(chargeWay).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", payDetails=").append(payDetails);
        sb.append(", billNo='").append(billNo).append('\'');
        sb.append(", notes=").append(notes);
        sb.append(", totalOverdueFinePrice='").append(totalOverdueFinePrice).append('\'');
        sb.append(", itemPrice='").append(itemPrice).append('\'');
        sb.append(", floorArea='").append(floorArea).append('\'');
        sb.append(", itemCycle='").append(itemCycle).append('\'');
        sb.append(", deadLine='").append(deadLine).append('\'');
        sb.append(", overdueFine='").append(overdueFine).append('\'');
        sb.append(", demandBillStatus=").append(demandBillStatus);
        sb.append(", urgeCount=").append(urgeCount);
        sb.append(", isOnline=").append(isOnline);
        sb.append(", serverTime=").append(serverTime);
        sb.append('}');
        return sb.toString();
    }
}

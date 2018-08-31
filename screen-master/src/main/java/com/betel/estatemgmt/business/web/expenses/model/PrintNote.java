package com.betel.estatemgmt.business.web.expenses.model;

import java.util.List;

/**
 * Created by zhangjian on 2017/9/19.
 *  打印对象
 */
public class PrintNote {
    private  String houseInfo;
    private  String houseMaster;
    private  String phone;

    private String payTime;
    private  String itemType;
    private  String billingStartTime;
    private  String billingEndTime;

    private String itemPrice;
    private String itemCycle;
    private  String overduefinePrice;
    private  String chargeWay;
    private  String totalBillAmount;

    private  String totalAmount;
    private  String receivableTotalAmount;
    private  String actualTotalAmount;
    private  String totalOverdueFinePrice;

    private Double billAmount;

    private  String billNo;
    private  List<Note> notes;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrintNote{");
        sb.append("houseInfo='").append(houseInfo).append('\'');
        sb.append(", houseMaster='").append(houseMaster).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", payTime='").append(payTime).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append(", billingStartTime='").append(billingStartTime).append('\'');
        sb.append(", billingEndTime='").append(billingEndTime).append('\'');
        sb.append(", itemPrice='").append(itemPrice).append('\'');
        sb.append(", itemCycle='").append(itemCycle).append('\'');
        sb.append(", overduefinePrice='").append(overduefinePrice).append('\'');
        sb.append(", chargeWay='").append(chargeWay).append('\'');
        sb.append(", totalBillAmount='").append(totalBillAmount).append('\'');
        sb.append(", totalAmount='").append(totalAmount).append('\'');
        sb.append(", receivableTotalAmount='").append(receivableTotalAmount).append('\'');
        sb.append(", actualTotalAmount='").append(actualTotalAmount).append('\'');
        sb.append(", totalOverdueFinePrice='").append(totalOverdueFinePrice).append('\'');
        sb.append(", billAmount=").append(billAmount);
        sb.append(", billNo='").append(billNo).append('\'');
        sb.append(", notes=").append(notes);
        sb.append('}');
        return sb.toString();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCycle() {
        return itemCycle;
    }

    public void setItemCycle(String itemCycle) {
        this.itemCycle = itemCycle;
    }

    public String getOverduefinePrice() {
        return overduefinePrice;
    }

    public void setOverduefinePrice(String overduefinePrice) {
        this.overduefinePrice = overduefinePrice;
    }

    public String getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(String chargeWay) {
        this.chargeWay = chargeWay;
    }

    public String getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(String totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
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

    public String getTotalOverdueFinePrice() {
        return totalOverdueFinePrice;
    }

    public void setTotalOverdueFinePrice(String totalOverdueFinePrice) {
        this.totalOverdueFinePrice = totalOverdueFinePrice;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}

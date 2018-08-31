package com.betel.estatemgmt.business.web.expenses.model;

/**
 * Created by zhangjian on 2017/9/19.
 */
public class Note {
    private  String itemType="";
    private  String billingStartTime="";
    private  String billingEndTime="";
    private  String itemPrice="0.00";
    private  String itemCycle="";
    private  String overdueFinePrice="0.00";
    private  String chargeWay="0";
    private  String totalbillAmount="0.00";
    private  String payTime;
    private String price="0.00";

    @Override
    public String toString() {
        return "Note{" +
                "itemType='" + itemType + '\'' +
                ", billingStartTime='" + billingStartTime + '\'' +
                ", billingEndTime='" + billingEndTime + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemCycle='" + itemCycle + '\'' +
                ", overdueFinePrice='" + overdueFinePrice + '\'' +
                ", chargeWay='" + chargeWay + '\'' +
                ", totalbillAmount='" + totalbillAmount + '\'' +
                ", payTime='" + payTime + '\'' +
                '}';
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

    public String getOverdueFinePrice() {
        return overdueFinePrice;
    }

    public void setOverdueFinePrice(String overdueFinePrice) {
        this.overdueFinePrice = overdueFinePrice;
    }

    public String getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(String chargeWay) {
        this.chargeWay = chargeWay;
    }

    public String getTotalbillAmount() {
        return totalbillAmount;
    }

    public void setTotalbillAmount(String totalbillAmount) {
        this.totalbillAmount = totalbillAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.betel.estatemgmt.business.web.expenses.model;

/**
 * Created by zhangjian on 2017/9/17.
 */
public class PayDetail {

    private  String itemType;
    private  String billAmount;
    private  String billingStartTime;
    private  String billingEndTime;
    private  String totalAmount;
    private  String overduefinePrice;

    @Override
    public String toString() {
        return "PayDetail{" +
                "itemType='" + itemType + '\'' +
                ", billAmount='" + billAmount + '\'' +
                ", billingStartTime='" + billingStartTime + '\'' +
                ", billingEndTime='" + billingEndTime + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", overduefinePrice='" + overduefinePrice + '\'' +
                '}';
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOverduefinePrice() {
        return overduefinePrice;
    }

    public void setOverduefinePrice(String overduefinePrice) {
        this.overduefinePrice = overduefinePrice;
    }
}

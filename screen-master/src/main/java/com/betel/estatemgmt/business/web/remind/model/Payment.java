package com.betel.estatemgmt.business.web.remind.model;

/**
 * <p>
 * 缴费提醒定时任务模板
 * </p>
 * ClassName: Payment <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/19 10:28 <br/>
 * Version: 1.0 <br/>
 */
public class Payment {

    private String houseNo;

    private String houseOwnerName;

    private String houseOwnerPhone;

    private String userId;

    private String billingCycle;

    private Integer itemType;

    private Double billAmount;

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public String getHouseOwnerName() {
        return houseOwnerName;
    }

    public void setHouseOwnerName(String houseOwnerName) {
        this.houseOwnerName = houseOwnerName;
    }

    public String getHouseOwnerPhone() {
        return houseOwnerPhone;
    }

    public void setHouseOwnerPhone(String houseOwnerPhone) {
        this.houseOwnerPhone = houseOwnerPhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("houseNo='").append(houseNo).append('\'');
        sb.append(", houseOwnerName='").append(houseOwnerName).append('\'');
        sb.append(", houseOwnerPhone='").append(houseOwnerPhone).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", billingCycle='").append(billingCycle).append('\'');
        sb.append(", itemType=").append(itemType);
        sb.append(", billAmount=").append(billAmount);
        sb.append('}');
        return sb.toString();
    }
}

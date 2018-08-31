package com.betel.estatemgmt.business.web.remind.model;

/**
 * <p>
 * 催缴提醒模型
 * </p>
 * ClassName: OverDue <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/19 13:36 <br/>
 * Version: 1.0 <br/>
 */
public class OverDue {

    private String billNo;

    private String houseNo;

    private String houseOwnerName;

    private String houseOwnerPhone;

    private String userId;

    private Integer itemType;

    private Double billAmount;

    private Double overdueFine;

    private Integer urgeCount;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getUrgeCount() {
        return urgeCount;
    }

    public void setUrgeCount(Integer urgeCount) {
        this.urgeCount = urgeCount;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Double getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(Double overdueFine) {
        this.overdueFine = overdueFine;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OverDue{");
        sb.append("billNo='").append(billNo).append('\'');
        sb.append(", houseNo='").append(houseNo).append('\'');
        sb.append(", houseOwnerName='").append(houseOwnerName).append('\'');
        sb.append(", houseOwnerPhone='").append(houseOwnerPhone).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", itemType=").append(itemType);
        sb.append(", billAmount=").append(billAmount);
        sb.append(", overdueFine=").append(overdueFine);
        sb.append(", urgeCount=").append(urgeCount);
        sb.append('}');
        return sb.toString();
    }
}

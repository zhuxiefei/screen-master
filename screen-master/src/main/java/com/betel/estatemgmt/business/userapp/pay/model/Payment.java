package com.betel.estatemgmt.business.userapp.pay.model;

import java.math.BigDecimal;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: Payment <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 18:19 <br/>
 * Version: 1.0 <br/>
 */
public class Payment {
    //户主名
    private String houseOwner;
    //房屋信息
    private String houseInfo;
    //缴费金额
    private BigDecimal totalAmount;
    //出账时间
    private String createTime;
    //付款时间
    private String payTime;
    //账单编号
    private String billNo;
    //收款人
    private String beneficiary;
    //状态
    private Integer payStatus;
    //收费项目类型
    private Integer itemType;
    //滞纳金
    private BigDecimal overdueFine;
    //支付明细
    private String payDetail;
    //结束时间
    private String endTime;
    //起始时间
    private String startTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(BigDecimal overdueFine) {
        this.overdueFine = overdueFine;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("houseOwner='").append(houseOwner).append('\'');
        sb.append(", houseInfo='").append(houseInfo).append('\'');
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", payTime='").append(payTime).append('\'');
        sb.append(", billNo='").append(billNo).append('\'');
        sb.append(", beneficiary='").append(beneficiary).append('\'');
        sb.append(", payStatus=").append(payStatus);
        sb.append(", itemType=").append(itemType);
        sb.append(", overdueFine=").append(overdueFine);
        sb.append(", payDetail='").append(payDetail).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

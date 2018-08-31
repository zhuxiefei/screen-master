package com.betel.estatemgmt.business.userapp.pay.model;

/**
 * <p>
 * 缴费model
 * </p>
 * ClassName: PayModel <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/19 9:36 <br/>
 * Version: 1.0 <br/>
 */
public class PayModel {
    /*
    账单的编号
     */
    private String billNo;
    /*
    支付的方式
     */
    private Integer payType;

    /*
    支付人的id
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "PayModel{" +
                "billNo='" + billNo + '\'' +
                ", payType=" + payType +
                ", userId='" + userId + '\'' +
                '}';
    }
}

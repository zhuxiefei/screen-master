package com.betel.estatemgmt.business.web.income.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 分页明细回参
 * </p>
 * ClassName: Income <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 13:17 <br/>
 * Version: 1.0 <br/>
 */
public class Income {

    private String flowNo;

    //楼宇单元房号
    private String houseNo;

    private String payer;

    //项目类型：1为物业费，2为公摊水电费
    private Integer itemType;

    //收费时间
    private Date payTime;

    //计费区间：账单起始时间~结束时间
    private String billInterval;

    //出账时间
    private Date createTime;

    //应收金额
    private BigDecimal billAmount;

    //滞纳金额
    private BigDecimal overdueFine;

    //应收合计
    private BigDecimal totalAmount;

    //收费方式
    private Integer chargeWay;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Date getPayTime() {
        if (payTime == null){
            return null;
        }
        return (Date)payTime.clone();
    }

    public void setPayTime(Date payTime) {
        if(payTime == null){
            this.payTime = null;
        }else{
            this.payTime = (Date)payTime.clone();
        }
    }

    public String getBillInterval() {
        return billInterval;
    }

    public void setBillInterval(String billInterval) {
        this.billInterval = billInterval;
    }

    public Date getCreateTime() {
        if(createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getOverdueFine() {
        return overdueFine;
    }

    public void setOverdueFine(BigDecimal overdueFine) {
        this.overdueFine = overdueFine;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(Integer chargeWay) {
        this.chargeWay = chargeWay;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Income{");
        sb.append("flowNo='").append(flowNo).append('\'');
        sb.append(", houseNo='").append(houseNo).append('\'');
        sb.append(", payer='").append(payer).append('\'');
        sb.append(", itemType=").append(itemType);
        sb.append(", payTime=").append(payTime);
        sb.append(", billInterval='").append(billInterval).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", billAmount=").append(billAmount);
        sb.append(", overdueFine=").append(overdueFine);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", chargeWay=").append(chargeWay);
        sb.append('}');
        return sb.toString();
    }
}

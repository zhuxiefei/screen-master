package com.betel.estatemgmt.business.propertyapp.assign.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: RepairReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/23 11:40 <br/>
 * Version: 1.0 <br/>
 */
public class RepairReq {

    String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RepairReq{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

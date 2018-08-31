package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询维修单详情入参
 * </p>
 * ClassName: FindOrderReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:14 <br/>
 * Version: 1.0 <br/>
 */
public class FindOrderReq {

    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindOrderReq{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

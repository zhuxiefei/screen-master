package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询确认单图片入参
 * </p>
 * ClassName: FindPicReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:59 <br/>
 * Version: 1.0 <br/>
 */
public class FindPicReq {
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindPicReq{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询维修类型入参
 * </p>
 * ClassName: FindOrderTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:09 <br/>
 * Version: 1.0 <br/>
 */
public class FindOrderTypeReq {

    private Integer orderArea;

    public Integer getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(Integer orderArea) {
        this.orderArea = orderArea;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindOrderTypeReq{");
        sb.append("orderArea=").append(orderArea);
        sb.append('}');
        return sb.toString();
    }
}

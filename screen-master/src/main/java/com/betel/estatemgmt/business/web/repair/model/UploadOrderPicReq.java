package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 上传图片入参
 * </p>
 * ClassName: UploadOrderPicReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:25 <br/>
 * Version: 1.0 <br/>
 */
public class UploadOrderPicReq {

    private String orderNo;

    private String description;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadOrderPicReq{");
        sb.append("orderNo='").append(orderNo).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

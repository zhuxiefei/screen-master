package com.betel.estatemgmt.business.web.charges.model;

/**
 * <p>
 * 出账入参
 * </p>
 * ClassName: CreateBillReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/19 17:10 <br/>
 * Version: 1.0 <br/>
 */
public class CreateBillReq {

    private String startTime;

    private String endTime;

    private String itemId;

    private String price;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateBillReq{");
        sb.append("startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", itemId='").append(itemId).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

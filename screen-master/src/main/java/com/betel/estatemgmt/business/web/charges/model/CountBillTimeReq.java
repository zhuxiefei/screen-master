package com.betel.estatemgmt.business.web.charges.model;

/**
 * <p>
 * 计算出账时间入参
 * </p>
 * ClassName: CountBillTimeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/19 16:18 <br/>
 * Version: 1.0 <br/>
 */
public class CountBillTimeReq {

    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CountBillTimeReq{");
        sb.append("itemId='").append(itemId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

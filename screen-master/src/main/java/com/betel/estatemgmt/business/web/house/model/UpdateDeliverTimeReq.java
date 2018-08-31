package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 批量修改入住时间入参
 * </p>
 * ClassName: UpdateDeliverTimeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/14 13:54 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateDeliverTimeReq {
    private String houseIds;

    private String deliverTime;

    public String getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String houseIds) {
        this.houseIds = houseIds;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateDeliverTimeReq{");
        sb.append("houseIds='").append(houseIds).append('\'');
        sb.append(", deliverTime='").append(deliverTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

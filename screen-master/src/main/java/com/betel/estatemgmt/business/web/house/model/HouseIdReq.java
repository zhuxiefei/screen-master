package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 房屋编号入参
 * </p>
 * ClassName: HouseIdReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:42 <br/>
 * Version: 1.0 <br/>
 */
public class HouseIdReq {
    private String houseId;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseIdReq{");
        sb.append("houseId=").append(houseId);
        sb.append('}');
        return sb.toString();
    }
}

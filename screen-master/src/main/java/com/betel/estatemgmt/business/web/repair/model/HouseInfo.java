package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询房屋回参
 * </p>
 * ClassName: HouseInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:13 <br/>
 * Version: 1.0 <br/>
 */
public class HouseInfo {

    private String houseId;

    private String houseNum;

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseInfo{");
        sb.append("houseId=").append(houseId);
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

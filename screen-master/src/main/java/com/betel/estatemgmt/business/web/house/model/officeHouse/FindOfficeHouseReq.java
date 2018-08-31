package com.betel.estatemgmt.business.web.house.model.officeHouse;

/**
 * Created by Administrator on 2018/5/9/009.
 */
public class FindOfficeHouseReq {

    private String houseId;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        return "FindOfficeHouseReq{" +
                "houseId='" + houseId + '\'' +
                '}';
    }
}

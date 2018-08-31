package com.betel.estatemgmt.business.web.house.model.officeHouse;

/**
 * Created by Administrator on 2018/5/8/008.
 */
public class DeleteOfficesReq {

    private String houseIds;

    public String getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String houseIds) {
        this.houseIds = houseIds;
    }

    @Override
    public String toString() {
        return "DeleteOfficesReq{" +
                "houseIds='" + houseIds + '\'' +
                '}';
    }
}

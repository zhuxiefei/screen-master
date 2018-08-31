package com.betel.estatemgmt.business.propertyapp.lease.model;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/10 16:25 <br/>
 * Version: 1.0 <br/>
 */
public class HouseAreaResp {


    private String houseArea;

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    @Override
    public String toString() {
        return "HouseAreaResp{" +
                "houseArea='" + houseArea + '\'' +
                '}';
    }
}
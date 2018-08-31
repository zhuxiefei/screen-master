package com.betel.estatemgmt.business.propertyapp.lease.model;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:00 <br/>
 * Version: 1.0 <br/>
 */
public class FindLeaseInfo {

    private String address;

    private String interFloorArea;

    private Integer houseStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(String interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    @Override
    public String toString() {
        return "FindLeaseInfo{" +
                "address='" + address + '\'' +
                ", interFloorArea='" + interFloorArea + '\'' +
                ", houseStatus=" + houseStatus +
                '}';
    }
}
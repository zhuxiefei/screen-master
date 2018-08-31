package com.betel.estatemgmt.business.propertyapp.lease.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 13:57 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllLeaseResp {

    private String houseId;

    private String rentId;

    private String address;

    private Integer houseStatus;

    private Date endTime;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "FindAllLeaseResp{" +
                "houseId='" + houseId + '\'' +
                ", rentId='" + rentId + '\'' +
                ", address='" + address + '\'' +
                ", houseStatus=" + houseStatus +
                ", endTime=" + endTime +
                '}';
    }
}
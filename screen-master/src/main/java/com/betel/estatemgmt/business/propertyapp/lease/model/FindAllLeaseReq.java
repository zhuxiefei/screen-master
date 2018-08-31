package com.betel.estatemgmt.business.propertyapp.lease.model;

import com.betel.estatemgmt.common.Page;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 13:47 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllLeaseReq extends Page{

    private String houseNumber;

    private Integer houseStatus;

    private Date endTime;

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
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
        return "FindAllLeaseReq{" +
                "houseNumber='" + houseNumber + '\'' +
                ", houseStatus=" + houseStatus +
                ", endTime=" + endTime +
                '}';
    }
}
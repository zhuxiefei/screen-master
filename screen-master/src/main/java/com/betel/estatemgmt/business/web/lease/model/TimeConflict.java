package com.betel.estatemgmt.business.web.lease.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 16:59 <br/>
 * Version: 1.0 <br/>
 */
public class TimeConflict {

    private String rentId;

    private String houseId;

    private Date startTime;

    private Date endTime;

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TimeConflict{" +
                "houseId='" + houseId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
package com.betel.estatemgmt.business.web.lease.model;

import com.betel.estatemgmt.common.model.lease.Rent;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 18:42 <br/>
 * Version: 1.0 <br/>
 */
public class WebLeaseInfo extends Rent {
    private String address;

    private Integer isInvalid;

    private String buildingId;

    private String unitId;

    private String houseStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Integer getIsInvalid() {
        return isInvalid;
    }

    public void setIsInvalid(Integer isInvalid) {
        this.isInvalid = isInvalid;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "WebLeaseInfo{" +
                "isInvalid=" + isInvalid +
                ", buildingId='" + buildingId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", houseStatus='" + houseStatus + '\'' +
                '}';
    }
}
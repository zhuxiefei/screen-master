package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class HouseParkingSpace {
    private String spaceNum;

    private String spaceId;

    private String houseId;

    private Integer spaceType;

    private Date createTime;

    private String licensePlate;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getSpaceNum() {
        return spaceNum;
    }

    public void setSpaceNum(String spaceNum) {
        this.spaceNum = spaceNum == null ? null : spaceNum.trim();
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId == null ? null : spaceId.trim();
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Integer getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(Integer spaceType) {
        this.spaceType = spaceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
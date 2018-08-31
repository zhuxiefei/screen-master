package com.betel.estatemgmt.business.web.house.model.officeHouse;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/8/008.
 */
public class FindAllOfficeResp {

    private String houseId;

    private String houseNum;

    private Date createTime;

    private Double interFloorArea;

    private Integer houseStatus;

    private String rentTime;

    private String buildingName;

    private String unitName;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(Double interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    @Override
    public String toString() {
        return "FindAllOfficeResp{" +
                "houseId='" + houseId + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", createTime=" + createTime +
                ", interFloorArea=" + interFloorArea +
                ", houseStatus=" + houseStatus +
                ", rentTime='" + rentTime + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", unitName='" + unitName + '\'' +
                '}';
    }
}

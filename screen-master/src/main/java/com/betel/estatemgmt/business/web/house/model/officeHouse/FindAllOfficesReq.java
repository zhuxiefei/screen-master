package com.betel.estatemgmt.business.web.house.model.officeHouse;

import com.betel.estatemgmt.common.Page;

/**
 * Created by Administrator on 2018/5/8/008.
 */
public class FindAllOfficesReq extends Page{

    private String buildingId;

    private String unitId;

    private String isOthers;

    private String houseNum;

    /**
     * 1为招商中，2为空置中，3为出租中
     */
    private String houseStatus;

    private String estateId;

    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
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

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        return "FindAllOfficesReq{" +
                "buildingId='" + buildingId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", isOthers='" + isOthers + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", houseStatus='" + houseStatus + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

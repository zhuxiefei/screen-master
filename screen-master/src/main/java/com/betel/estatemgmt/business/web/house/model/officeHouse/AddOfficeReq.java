package com.betel.estatemgmt.business.web.house.model.officeHouse;

/**
 * Created by Administrator on 2018/5/8/008.
 */
public class AddOfficeReq {

    private String buildingId;

    private String unitId;

    private String houseNum;

    private String displayOrder;

    private String interFloorArea;

    /**
     * 1为招商中，2为空置中，
     */
    private String houseStatus;

    private String estateId;

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

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(String interFloorArea) {
        this.interFloorArea = interFloorArea;
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
        return "AddOfficeReq{" +
                "buildingId='" + buildingId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", displayOrder='" + displayOrder + '\'' +
                ", interFloorArea='" + interFloorArea + '\'' +
                ", houseStatus='" + houseStatus + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

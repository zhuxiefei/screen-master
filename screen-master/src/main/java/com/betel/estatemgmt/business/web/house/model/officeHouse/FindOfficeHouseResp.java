package com.betel.estatemgmt.business.web.house.model.officeHouse;

/**
 * Created by Administrator on 2018/5/9/009.
 */
public class FindOfficeHouseResp {

    private String houseId;

    private String houseNum;

    private String buildingId;

    private String buildingName;

    private String unitId;

    private String unitName;

    private Integer displayOrder;

    private Double interFloorArea;

    private Integer houseStatus;

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Double getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(Double interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    @Override
    public String toString() {
        return "FindOfficeHouseResp{" +
                "houseId='" + houseId + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", unitId='" + unitId + '\'' +
                ", unitName='" + unitName + '\'' +
                ", displayOrder=" + displayOrder +
                ", interFloorArea=" + interFloorArea +
                ", houseStatus=" + houseStatus +
                '}';
    }
}

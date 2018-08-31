package com.betel.estatemgmt.business.smarthome.model;

/**
 * Created by Administrator on 2018/4/10/010.
 */
public class BuildingResp {

    private String buildingId;

    private String buildingName;

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

    @Override
    public String toString() {
        return "BuildingResp{" +
                "buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                '}';
    }
}

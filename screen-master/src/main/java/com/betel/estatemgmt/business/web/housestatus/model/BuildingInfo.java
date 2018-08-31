package com.betel.estatemgmt.business.web.housestatus.model;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: BuildingInfo <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/13 17:43 <br/>
 * @version: 1.0
 */
public class BuildingInfo {
    private String buildingId;
    private String buildingName;
    private List<UnitInfo> unitList;

    @Override
    public String toString() {
        return "BuildingInfo{" +
                "buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", unitList=" + unitList +
                '}';
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

    public List<UnitInfo> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<UnitInfo> unitList) {
        this.unitList = unitList;
    }
}

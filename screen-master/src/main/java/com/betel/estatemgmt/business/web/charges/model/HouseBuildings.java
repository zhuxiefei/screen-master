package com.betel.estatemgmt.business.web.charges.model;

import com.betel.estatemgmt.common.model.house.Building;

import java.util.List;

/**
 * Created by zhangjian on 2017/9/19.
 */
public class HouseBuildings {
    private List<Building> filtrateBuildingIds;
    private List<Building> buildingIds;
    private List<Building> buildingIdToWeb;

    public List<Building> getFiltrateBuildingIds() {
        return filtrateBuildingIds;
    }

    public void setFiltrateBuildingIds(List<Building> filtrateBuildingIds) {
        this.filtrateBuildingIds = filtrateBuildingIds;
    }

    public List<Building> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Building> buildingIds) {
        this.buildingIds = buildingIds;
    }

    public List<Building> getBuildingIdToWeb() {
        return buildingIdToWeb;
    }

    public void setBuildingIdToWeb(List<Building> buildingIdToWeb) {
        this.buildingIdToWeb = buildingIdToWeb;
    }

    @Override
    public String toString() {
        return "HouseBuildings{" +
                "filtrateBuildingIds=" + filtrateBuildingIds +
                ", BuildingIds=" + buildingIds +
                ", BuildingIdToWeb=" + buildingIdToWeb +
                '}';
    }


}

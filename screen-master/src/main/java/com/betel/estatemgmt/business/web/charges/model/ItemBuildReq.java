package com.betel.estatemgmt.business.web.charges.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjian on 2017/9/20.
 */
public class ItemBuildReq {
    private List<Long> buildingIds = new ArrayList<>();
    private String itemId = "";

    @Override
    public String toString() {
        return "ItemBuildReq{" +
                "buildingIds=" + buildingIds +
                ", itemId='" + itemId + '\'' +
                '}';
    }

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}

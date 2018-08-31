package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询房屋入参
 * </p>
 * ClassName: FindHouseReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:12 <br/>
 * Version: 1.0 <br/>
 */
public class FindHouseReq {

    private String buildingId;

    private String unitId;

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
        final StringBuilder sb = new StringBuilder("FindHouseReq{");
        sb.append("buildingId=").append(buildingId);
        sb.append(", unitId=").append(unitId);
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.building.model;

/**
 * <p>
 * 楼宇信息类
 * </p>
 * ClassName: BuildingInfo <br/>
 * Author: zhangli <br/>
 * Date: 2017/6/20 11:36 <br/>
 * Version: 1.0 <br/>
 */
public class BuildingInfo {
    /**
     * 楼宇id
     */
    private Long buildingId;
    /**
     * 楼宇名称
     */
    private String buildingName;
    /**
     * 楼宇描述
     */
    private String buildingDesc;

    private String displayOrder;

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getBuildingDesc() {
        return buildingDesc;
    }

    public void setBuildingDesc(String buildingDesc) {
        this.buildingDesc = buildingDesc;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
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
        final StringBuilder sb = new StringBuilder("BuildingInfo{");
        sb.append("buildingId=").append(buildingId);
        sb.append(", buildingName='").append(buildingName).append('\'');
        sb.append(", buildingDesc='").append(buildingDesc).append('\'');
        sb.append(", displayOrder='").append(displayOrder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

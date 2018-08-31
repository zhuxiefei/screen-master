package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class BuildingUnit {
    private Long unitId;

    private String unitName;

    private Long buildingId;

    private Date createTime;

    private Integer displayOrder;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuildingUnit{");
        sb.append("unitId=").append(unitId);
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", buildingId=").append(buildingId);
        sb.append(", createTime=").append(createTime);
        sb.append(", displayOrder=").append(displayOrder);
        sb.append('}');
        return sb.toString();
    }
}
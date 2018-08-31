package com.betel.estatemgmt.business.web.building.model;

import java.util.Date;

/**
 * <p>
 * 单元信息类
 * </p>
 * ClassName: Unit <br/>
 * Author: zhangli <br/>
 * Date: 2017/6/20 11:36 <br/>
 * Version: 1.0 <br/>
 */
public class Unit {
    /**
     * 单元id
     */
    private Long unitId;
    /**
     * 单元名称
     */
    private String unitName;
    /**
     * 楼宇id
     */
    private Long buildingId;
    /**
     * 创建时间
     */
    private Date createTime;

    private String displayOrder;

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
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
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Unit{");
        sb.append("unitId=").append(unitId);
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", buildingId=").append(buildingId);
        sb.append(", createTime=").append(createTime);
        sb.append(", displayOrder='").append(displayOrder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

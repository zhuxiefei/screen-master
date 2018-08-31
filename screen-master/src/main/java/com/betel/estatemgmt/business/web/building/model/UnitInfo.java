package com.betel.estatemgmt.business.web.building.model;

/**
 * <p>
 * 单元信息类
 * </p>
 * ClassName: UnitInfo <br/>
 * Author: zhangli <br/>
 * Date: 2017/6/20 11:56 <br/>
 * Version: 1.0 <br/>
 */
public class UnitInfo {
    /**
     * 单元id
     */
    private Long unitId;
    /**
     * 单元名称
     */
    private String unitName;

    private String displayOrder;

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
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
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UnitInfo{");
        sb.append("unitId=").append(unitId);
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", displayOrder='").append(displayOrder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

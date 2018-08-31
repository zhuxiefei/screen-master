package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 导入错误信息回参
 * </p>
 * ClassName: ImportMsg <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 13:59 <br/>
 * Version: 1.0 <br/>
 */
public class ImportMsg {
    private String buildingName;

    private String unitName;

    private String houseNum;

    private String typeName;

    private String displayOrder;

    private String floorArea;

    private String interFloorArea;

    private String failureMsg;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(String interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImportMsg{");
        sb.append("buildingName='").append(buildingName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", displayOrder='").append(displayOrder).append('\'');
        sb.append(", floorArea='").append(floorArea).append('\'');
        sb.append(", interFloorArea='").append(interFloorArea).append('\'');
        sb.append(", failureMsg='").append(failureMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.income.model;

/**
 * <p>
 * 导出明细入参
 * </p>
 * ClassName: ExportReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/18 14:11 <br/>
 * Version: 1.0 <br/>
 */
public class ExportReq {
    private String buildingId;

    private String unitId;

    private String houseNum;

    private String userName;

    private String startTime;

    private String endTime;

    private String itemType;

    private String isOthers;

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

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExportReq{");
        sb.append("buildingId='").append(buildingId).append('\'');
        sb.append(", unitId='").append(unitId).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", itemType='").append(itemType).append('\'');
        sb.append(", isOthers='").append(isOthers).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

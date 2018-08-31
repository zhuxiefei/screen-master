package com.betel.estatemgmt.business.web.income.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 分页搜索明细入参
 * </p>
 * ClassName: IncomePageReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 13:06 <br/>
 * Version: 1.0 <br/>
 */
public class IncomePageReq extends Page {

    private Long buildingId;

    private Long unitId;

    private String houseNum;

    private String userName;

    private String startTime;

    private String endTime;

    private Integer itemType;

    private String isOthers;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
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
        final StringBuilder sb = new StringBuilder("IncomePageReq{");
        sb.append("buildingId=").append(buildingId);
        sb.append(", unitId=").append(unitId);
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", itemType=").append(itemType);
        sb.append(", isOthers='").append(isOthers).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

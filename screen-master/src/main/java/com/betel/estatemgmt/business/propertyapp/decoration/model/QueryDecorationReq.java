package com.betel.estatemgmt.business.propertyapp.decoration.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 物业查询装修列表查询入参
 * </p>
 * ClassName: QueryDecorationReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/25 9:09 <br/>
 * Version: 1.0 <br/>
 */
public class QueryDecorationReq extends Page{

    private String type;

    private String status;

    private String buildingId;

    private String unitId;

    private String houseId;

    private String houseNum;

    private String printStatus;

    private String startTime;

    private String endTime;

    private String isOthers;

    private String estateId;

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
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

    public String getIsOthers() {
        return isOthers;
    }

    public void setIsOthers(String isOthers) {
        this.isOthers = isOthers;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueryDecorationReq{");
        sb.append("type='").append(type).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", buildingId='").append(buildingId).append('\'');
        sb.append(", unitId='").append(unitId).append('\'');
        sb.append(", houseId='").append(houseId).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", printStatus='").append(printStatus).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", isOthers='").append(isOthers).append('\'');
        sb.append(", estateId='").append(estateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

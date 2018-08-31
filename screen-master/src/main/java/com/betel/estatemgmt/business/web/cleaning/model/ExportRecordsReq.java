package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ExportRecordsReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/3/1 10:08 <br/>
 * Version: 1.0 <br/>
 */
public class ExportRecordsReq {

    private String recordIds;

    private String areaId;

    private String typeId;

    private String cycle;

    private String startTime;

    private String endTime;

    private String isStandard;

    private String contentNo;

    private String location;

    private String estateId;

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
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

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExportRecordsReq{");
        sb.append("recordIds='").append(recordIds).append('\'');
        sb.append(", areaId='").append(areaId).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", cycle='").append(cycle).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", isStandard='").append(isStandard).append('\'');
        sb.append(", contentNo='").append(contentNo).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", estateId='").append(estateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

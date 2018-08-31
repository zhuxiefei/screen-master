package com.betel.estatemgmt.business.web.patrol.model;

/**
 * Created by Administrator on 2018/3/6/006.
 */
public class ExportRepairRecordsReq {

    private String recordIds;

    private String typeId;

    private String startTime;

    private String endTime;

    private String equipmentNo;

    private String equipmentName;

    private String location;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ExportRepairRecordsReq{" +
                "recordIds='" + recordIds + '\'' +
                ", typeId='" + typeId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

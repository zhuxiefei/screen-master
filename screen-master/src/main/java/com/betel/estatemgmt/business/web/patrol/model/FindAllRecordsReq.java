package com.betel.estatemgmt.business.web.patrol.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllRecordsReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/26 11:09 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllRecordsReq extends Page{

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
        final StringBuilder sb = new StringBuilder("FindAllRecordsReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", equipmentNo='").append(equipmentNo).append('\'');
        sb.append(", equipmentName='").append(equipmentName).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.patrol.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllRecordsResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/26 11:12 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllRecordsResp {

    private String recordId;

    private String equipmentNo;

    private String equipmentName;

    private String typeName;

    private String location;

    private Date createTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindAllRecordsResp{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", equipmentNo='").append(equipmentNo).append('\'');
        sb.append(", equipmentName='").append(equipmentName).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

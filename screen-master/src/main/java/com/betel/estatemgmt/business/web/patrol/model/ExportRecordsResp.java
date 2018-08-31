package com.betel.estatemgmt.business.web.patrol.model;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/7/007.
 */
public class ExportRecordsResp {

    private String equipmentNo;

    private String equipmentName;

    private String typeName;

    private String location;

    private Date createTime;

    private String equipmentOperator;

    private String operatorPhone;

    private String repairDesc;

    private String repairExpense;

    private String recordQuality;

    private String recordUnit;

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

    public String getEquipmentOperator() {
        return equipmentOperator;
    }

    public void setEquipmentOperator(String equipmentOperator) {
        this.equipmentOperator = equipmentOperator;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getRepairDesc() {
        return repairDesc;
    }

    public void setRepairDesc(String repairDesc) {
        this.repairDesc = repairDesc;
    }

    public String getRepairExpense() {
        return repairExpense;
    }

    public void setRepairExpense(String repairExpense) {
        this.repairExpense = repairExpense;
    }

    public String getRecordQuality() {
        return recordQuality;
    }

    public void setRecordQuality(String recordQuality) {
        this.recordQuality = recordQuality;
    }

    public String getRecordUnit() {
        return recordUnit;
    }

    public void setRecordUnit(String recordUnit) {
        this.recordUnit = recordUnit;
    }

    @Override
    public String toString() {
        return "ExportRecordsResp{" +
                "equipmentNo='" + equipmentNo + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", location='" + location + '\'' +
                ", createTime=" + createTime +
                ", equipmentOperator='" + equipmentOperator + '\'' +
                ", operatorPhone='" + operatorPhone + '\'' +
                ", repairDesc='" + repairDesc + '\'' +
                ", repairExpense='" + repairExpense + '\'' +
                ", recordQuality='" + recordQuality + '\'' +
                ", recordUnit='" + recordUnit + '\'' +
                '}';
    }
}

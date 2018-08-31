package com.betel.estatemgmt.business.web.patrol.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRecordResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/26 13:29 <br/>
 * Version: 1.0 <br/>
 */
public class FindRecordResp {

    private String recordId;

    private String equipmentId;

    private String equipmentNo;

    private String typeName;

    private String equipmentName;

    private Integer isCheck;

    private String location;

    private Date equipmentCreateTime;

    private String equipQuality;

    private String equipUnit;

    private Date deadline;

    private String equipmentProducer;

    private String producerPhone;

    private Date repairTime;

    private String equipmentOperator;

    private String operatorPhone;

    private String repairDesc;

    private String repairExpense;

    private String recordQuality;

    private String recordUnit;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEquipmentCreateTime() {
        return equipmentCreateTime;
    }

    public void setEquipmentCreateTime(Date equipmentCreateTime) {
        this.equipmentCreateTime = equipmentCreateTime;
    }

    public String getEquipQuality() {
        return equipQuality;
    }

    public void setEquipQuality(String equipQuality) {
        this.equipQuality = equipQuality;
    }

    public String getEquipUnit() {
        return equipUnit;
    }

    public void setEquipUnit(String equipUnit) {
        this.equipUnit = equipUnit;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getEquipmentProducer() {
        return equipmentProducer;
    }

    public void setEquipmentProducer(String equipmentProducer) {
        this.equipmentProducer = equipmentProducer;
    }

    public String getProducerPhone() {
        return producerPhone;
    }

    public void setProducerPhone(String producerPhone) {
        this.producerPhone = producerPhone;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
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
        final StringBuilder sb = new StringBuilder("FindRecordResp{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", equipmentId='").append(equipmentId).append('\'');
        sb.append(", equipmentNo='").append(equipmentNo).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", equipmentName='").append(equipmentName).append('\'');
        sb.append(", isCheck=").append(isCheck);
        sb.append(", location='").append(location).append('\'');
        sb.append(", equipmentCreateTime=").append(equipmentCreateTime);
        sb.append(", equipQuality='").append(equipQuality).append('\'');
        sb.append(", equipUnit='").append(equipUnit).append('\'');
        sb.append(", deadline=").append(deadline);
        sb.append(", equipmentProducer='").append(equipmentProducer).append('\'');
        sb.append(", producerPhone='").append(producerPhone).append('\'');
        sb.append(", repairTime=").append(repairTime);
        sb.append(", equipmentOperator='").append(equipmentOperator).append('\'');
        sb.append(", operatorPhone='").append(operatorPhone).append('\'');
        sb.append(", repairDesc='").append(repairDesc).append('\'');
        sb.append(", repairExpense='").append(repairExpense).append('\'');
        sb.append(", recordQuality='").append(recordQuality).append('\'');
        sb.append(", recordUnit='").append(recordUnit).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

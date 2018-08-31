package com.betel.estatemgmt.common.model.patrol;

import java.util.Date;

public class PatrolEquipment {
    private String equipmentId;

    private String equipmentNo;

    private String equipmentType;


    private String typeName;

    private String equipmentName;

    private String equipmentLocation;

    private String equipmentDesc;

    private Integer isCheck;

    private Integer checkCycle;

    private Date equipmentCreateTime;

    private String qualityPeriod;

    private Date createTime;

    private Date updateTime;

    private Date deadline;

    private String equipmentProducer;

    private String producerPhone;

    private String equipmentOperator;

    private String operatorPhone;

    private Integer repairNumber;

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    private Integer unit;

    private String equipmentQRCode;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId == null ? null : equipmentId.trim();
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo == null ? null : equipmentNo.trim();
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType == null ? null : equipmentType.trim();
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName == null ? null : equipmentName.trim();
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation == null ? null : equipmentLocation.trim();
    }

    public String getEquipmentDesc() {
        return equipmentDesc;
    }

    public void setEquipmentDesc(String equipmentDesc) {
        this.equipmentDesc = equipmentDesc == null ? null : equipmentDesc.trim();
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(Integer checkCycle) {
        this.checkCycle = checkCycle;
    }

    public Date getEquipmentCreateTime() {
        return equipmentCreateTime;
    }

    public void setEquipmentCreateTime(Date equipmentCreateTime) {
        this.equipmentCreateTime = equipmentCreateTime;
    }

    public String getQualityPeriod() {
        return qualityPeriod;
    }

    public void setQualityPeriod(String qualityPeriod) {
        this.qualityPeriod = qualityPeriod;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        this.equipmentProducer = equipmentProducer == null ? null : equipmentProducer.trim();
    }

    public String getProducerPhone() {
        return producerPhone;
    }

    public void setProducerPhone(String producerPhone) {
        this.producerPhone = producerPhone == null ? null : producerPhone.trim();
    }

    public String getEquipmentOperator() {
        return equipmentOperator;
    }

    public void setEquipmentOperator(String equipmentOperator) {
        this.equipmentOperator = equipmentOperator == null ? null : equipmentOperator.trim();
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone == null ? null : operatorPhone.trim();
    }

    public Integer getRepairNumber() {
        return repairNumber;
    }

    public void setRepairNumber(Integer repairNumber) {
        this.repairNumber = repairNumber;
    }

    public String getEquipmentQRCode() {
        return equipmentQRCode;
    }

    public void setEquipmentQRCode(String equipmentQRCode) {
        this.equipmentQRCode = equipmentQRCode == null ? null : equipmentQRCode.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "PatrolEquipment{" +
                "equipmentId='" + equipmentId + '\'' +
                ", equipmentNo='" + equipmentNo + '\'' +
                ", equipmentType='" + equipmentType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentLocation='" + equipmentLocation + '\'' +
                ", equipmentDesc='" + equipmentDesc + '\'' +
                ", isCheck=" + isCheck +
                ", checkCycle=" + checkCycle +
                ", equipmentCreateTime=" + equipmentCreateTime +
                ", qualityPeriod='" + qualityPeriod + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deadline=" + deadline +
                ", equipmentProducer='" + equipmentProducer + '\'' +
                ", producerPhone='" + producerPhone + '\'' +
                ", equipmentOperator='" + equipmentOperator + '\'' +
                ", operatorPhone='" + operatorPhone + '\'' +
                ", repairNumber=" + repairNumber +
                ", unit=" + unit +
                ", equipmentQRCode='" + equipmentQRCode + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
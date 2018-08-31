package com.betel.estatemgmt.business.propertyapp.check.model;

/**
 * <p>
 * 查询设备信息接口出参类.
 * </p>
 * ClassName: FindDeviceInfoResp <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 14:15 <br/>
 * Version: 1.0 <br/>
 */
public class FindDeviceInfoResp {

    String equipmentNo;

    String equipmentName;

    String equipmentLocation;

    String equipmentDesc;


    //add
    private String deadline;

    private String equipmentProducer;

    private String producerPhone;

    private String equipmentOperator;

    private String operatorPhone;

    //迭代九
    /**
     * 设备类型
     */
    private String typeName;
    private String isCheck;
    private String checkCycle;
    private String equipmentCreateTime;
    private String repairNumber;
    private String qualityPeriod;
    private String unit;

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

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getEquipmentDesc() {
        return equipmentDesc;
    }

    public void setEquipmentDesc(String equipmentDesc) {
        this.equipmentDesc = equipmentDesc;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(String checkCycle) {
        this.checkCycle = checkCycle;
    }

    public String getEquipmentCreateTime() {
        return equipmentCreateTime;
    }

    public void setEquipmentCreateTime(String equipmentCreateTime) {
        this.equipmentCreateTime = equipmentCreateTime;
    }

    public String getRepairNumber() {
        return repairNumber;
    }

    public void setRepairNumber(String repairNumber) {
        this.repairNumber = repairNumber;
    }

    public String getQualityPeriod() {
        return qualityPeriod;
    }

    public void setQualityPeriod(String qualityPeriod) {
        this.qualityPeriod = qualityPeriod;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "FindDeviceInfoResp{" +
                "equipmentNo='" + equipmentNo + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentLocation='" + equipmentLocation + '\'' +
                ", equipmentDesc='" + equipmentDesc + '\'' +
                ", deadline='" + deadline + '\'' +
                ", equipmentProducer='" + equipmentProducer + '\'' +
                ", producerPhone='" + producerPhone + '\'' +
                ", equipmentOperator='" + equipmentOperator + '\'' +
                ", operatorPhone='" + operatorPhone + '\'' +
                ", typeName='" + typeName + '\'' +
                ", isCheck='" + isCheck + '\'' +
                ", checkCycle='" + checkCycle + '\'' +
                ", equipmentCreateTime='" + equipmentCreateTime + '\'' +
                ", repairNumber='" + repairNumber + '\'' +
                ", qualityPeriod='" + qualityPeriod + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

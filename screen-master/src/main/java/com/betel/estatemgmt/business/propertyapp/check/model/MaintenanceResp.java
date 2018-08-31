package com.betel.estatemgmt.business.propertyapp.check.model;

/**
 * Created by zhangjian on 2018/1/24.
 */
public class MaintenanceResp {

    /**
     * 设备维保记录id
     *
     * @return
     */
    private String equipmentRepairRecordId;
    /**
     * 设备id
     */
    private String equipmentId;
    /**
     * 设备编号
     */
    private String equipmentNo;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备位置
     */
    private String equipmentLocation;
    /**
     * 维修时间
     */
    private String repairTime;
    /**
     * 维修厂商
     */
    private String equipmentOperator;
    /**
     * 维修厂商电话
     */
    private String operatorPhone;
    /**
     * 维修内容
     */
    private String repairDesc;
    /**
     * 维修费用
     */
    private String repairExpense;

    public String getQualityPeriod() {
        return qualityPeriod;
    }

    public void setQualityPeriod(String qualityPeriod) {
        this.qualityPeriod = qualityPeriod;
    }

    /**
     * 维保质保期
     *
     * @return
     */
    private String qualityPeriod;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     * @return
     */
    private String typeName;
    public String getEquipmentRepairRecordId() {
        return equipmentRepairRecordId;
    }

    public void setEquipmentRepairRecordId(String equipmentRepairRecordId) {
        this.equipmentRepairRecordId = equipmentRepairRecordId;
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

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
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
}

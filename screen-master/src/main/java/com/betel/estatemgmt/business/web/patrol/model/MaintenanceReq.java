package com.betel.estatemgmt.business.web.patrol.model;

/**
 * Created by zhangjian on 2018/1/24.
 */
public class MaintenanceReq {
    /**
     * 页码
     */
    private Integer curPage = 1;
    /**
     * 每一页条数
     */
    private Integer pageSize = 10;
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
     * 设备类型id
     */
    private String typeId;
    /**
     * 设备位置
     */
    private String equipmentLocation;
    /**
     * 设备类型
     */
    private String equipmentType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    private String recordId;
    private String equipmentOperator;
    private String operatorPhone;
    private String repairDesc;
    private String repairExpense;
    private String repairTime;
    private String qualityPeriod;
    private String unit;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
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

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
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
        final StringBuilder sb = new StringBuilder("MaintenanceReq{");
        sb.append("curPage=").append(curPage);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", equipmentId='").append(equipmentId).append('\'');
        sb.append(", equipmentNo='").append(equipmentNo).append('\'');
        sb.append(", equipmentName='").append(equipmentName).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", equipmentLocation='").append(equipmentLocation).append('\'');
        sb.append(", equipmentType='").append(equipmentType).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append(", equipmentOperator='").append(equipmentOperator).append('\'');
        sb.append(", operatorPhone='").append(operatorPhone).append('\'');
        sb.append(", repairDesc='").append(repairDesc).append('\'');
        sb.append(", repairExpense='").append(repairExpense).append('\'');
        sb.append(", repairTime='").append(repairTime).append('\'');
        sb.append(", qualityPeriod='").append(qualityPeriod).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

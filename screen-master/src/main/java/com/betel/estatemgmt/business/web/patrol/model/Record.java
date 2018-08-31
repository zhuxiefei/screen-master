package com.betel.estatemgmt.business.web.patrol.model;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: RecordList <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 20:01 <br/>
 * @version: 1.0
 */
public class Record {
    /**
     * 巡检记录id
     */
    private String recordId;


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
     * 是否正常:1为正常,2为不正常
     */
    private String recordStatus;
    /**
     * 巡检周期
     */
    private String checkCycle;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 巡检发布时间
     */
    private String createTime;
    /**
     * 设备生产日期
     */
    private String equipmentCreateTime;
    /**
     * 质保期
     */
    private String qualityPeriod;
    /**
     * 质保期单位
     */
    private String unit;

    private String deadline;

    private String equipmentProducer;

    private String producerPhone;

    private String equipmentOperator;

    private String operatorPhone;
    /**
     * 完成状态:1  未巡检  2 已巡检  3 已逾期
     */
    private String isPatrol;
    /**
     * 巡检设备内容描述
     */
    private String equipmentDesc;
    /**
     * 问题描述:不正常的时候显示
     */
    private String recordDesc;
    /**
     * 巡检人员姓名
     */
    private String employeeName;
    private String updateTime;
    /**
     * 图片url
     */
    private List<String> pictureUrls;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

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

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(String checkCycle) {
        this.checkCycle = checkCycle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEquipmentCreateTime() {
        return equipmentCreateTime;
    }

    public void setEquipmentCreateTime(String equipmentCreateTime) {
        this.equipmentCreateTime = equipmentCreateTime;
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

    public String getIsPatrol() {
        return isPatrol;
    }

    public void setIsPatrol(String isPatrol) {
        this.isPatrol = isPatrol;
    }

    public String getEquipmentDesc() {
        return equipmentDesc;
    }

    public void setEquipmentDesc(String equipmentDesc) {
        this.equipmentDesc = equipmentDesc;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }
}

package com.betel.estatemgmt.business.propertyapp.check.model;

/**
 * <p>
 * 提交巡检信息接口入参类
 * </p>
 * ClassName: SaveCheckInfoReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 17:08 <br/>
 * Version: 1.0 <br/>
 */
public class SaveCheckInfoReq {

    String equipmentId;

    Integer recordStatus;

    String recordDesc;

    String picIds;

    String createTime;

    String serialNo;

    String checkCycle;

    public String getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(String checkCycle) {
        this.checkCycle = checkCycle;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    String employeeName;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    String estateId;

    @Override
    public String toString() {
        return "SaveCheckInfoReq{" +
                "equipmentId='" + equipmentId + '\'' +
                ", recordStatus=" + recordStatus +
                ", recordDesc='" + recordDesc + '\'' +
                ", picIds='" + picIds + '\'' +
                ", createTime='" + createTime + '\'' +
                ", serialNo='" + serialNo + '\'' +
                '}';
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public String getPicIds() {
        return picIds;
    }

    public void setPicIds(String picIds) {
        this.picIds = picIds;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}

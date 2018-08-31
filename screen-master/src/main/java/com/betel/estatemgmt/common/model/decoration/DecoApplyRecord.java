package com.betel.estatemgmt.common.model.decoration;

import java.util.Date;

public class DecoApplyRecord {

    private String recordId;

    private String houseId;

    private String decorationCompany;

    private Date startTime;

    private Date endTime;

    private Integer decorationCycle;

    private String decorationDesc;

    private String status;

    private String reason;

    private String description;

    private Integer printStatus;

    private Date createTime;

    private Date updateTime;

    private Date cancelTime;

    private String cancelReason;

    private String operator;

    private String buildingName;

    private String unitName;

    private String houseName;

    private Integer isDelete;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getDecorationCompany() {
        return decorationCompany;
    }

    public void setDecorationCompany(String decorationCompany) {
        this.decorationCompany = decorationCompany == null ? null : decorationCompany.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDecorationCycle() {
        return decorationCycle;
    }

    public void setDecorationCycle(Integer decorationCycle) {
        this.decorationCycle = decorationCycle;
    }

    public String getDecorationDesc() {
        return decorationDesc;
    }

    public void setDecorationDesc(String decorationDesc) {
        this.decorationDesc = decorationDesc == null ? null : decorationDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Integer printStatus) {
        this.printStatus = printStatus;
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

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DecoApplyRecord{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", houseId=").append(houseId);
        sb.append(", decorationCompany='").append(decorationCompany).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", decorationCycle=").append(decorationCycle);
        sb.append(", decorationDesc='").append(decorationDesc).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", printStatus=").append(printStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", cancelReason='").append(cancelReason).append('\'');
        sb.append(", operator='").append(operator).append('\'');
        sb.append(", buildingName='").append(buildingName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", houseName='").append(houseName).append('\'');
        sb.append(", isDelete=").append(isDelete);
        sb.append('}');
        return sb.toString();
    }
}
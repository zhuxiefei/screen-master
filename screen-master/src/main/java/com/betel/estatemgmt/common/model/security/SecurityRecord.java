package com.betel.estatemgmt.common.model.security;

import java.util.Date;

public class SecurityRecord {
    private String recordId;

    private String contentId;

    private String employeeId;

    private String employeeName;

    private Integer isPatrol;

    private Integer signinNum;

    private Date finishTime;

    private Date createTime;

    private Date updateTime;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    @Override
    public String toString() {
        return "SecurityRecord{" +
                "recordId='" + recordId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", isPatrol=" + isPatrol +
                ", signinNum=" + signinNum +
                ", finishTime=" + finishTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public Integer getIsPatrol() {
        return isPatrol;
    }

    public void setIsPatrol(Integer isPatrol) {
        this.isPatrol = isPatrol;
    }

    public Integer getSigninNum() {
        return signinNum;
    }

    public void setSigninNum(Integer signinNum) {
        this.signinNum = signinNum;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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
}
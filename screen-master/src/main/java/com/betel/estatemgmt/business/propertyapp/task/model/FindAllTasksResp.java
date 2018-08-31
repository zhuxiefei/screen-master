package com.betel.estatemgmt.business.propertyapp.task.model;

import java.util.Date;

/**
 * <p>
 * 查询发出/收到任务列表回参
 * </p>
 * ClassName: FindAllTasksResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 19:23 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllTasksResp {

    private String taskId;
    private String recordId;
    private Integer taskStatus;
    private Date updateTime;
    private String taskType;
    private String createUserName;
    private String executorName;
    private String taskNo;
    private String createUserPhone;
    private String executorPhone;
    private String cloaseTime;
    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }
    public String getCloaseTime() {
        return cloaseTime;
    }

    public void setCloaseTime(String cloaseTime) {
        this.cloaseTime = cloaseTime;
    }
    public String getCreateUserPhone() {
        return createUserPhone;
    }

    public void setCreateUserPhone(String createUserPhone) {
        this.createUserPhone = createUserPhone;
    }

    public String getExecutorPhone() {
        return executorPhone;
    }

    public void setExecutorPhone(String executorPhone) {
        this.executorPhone = executorPhone;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindAllTasksResp{");
        sb.append("taskId='").append(taskId).append('\'');
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", taskType='").append(taskType).append('\'');
        sb.append(", createUserName='").append(createUserName).append('\'');
        sb.append(", executorName='").append(executorName).append('\'');
        sb.append(", taskNo='").append(taskNo).append('\'');
        sb.append(", createUserPhone='").append(createUserPhone).append('\'');
        sb.append(", executorPhone='").append(executorPhone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

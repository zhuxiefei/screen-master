package com.betel.estatemgmt.business.web.task.model;

import java.util.Date;

/**
 * <p>
 * 分页查询任务列表回参
 * </p>
 * ClassName: FindAllTasksResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 11:29 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllTasksResp {

    private String taskId;

    private String taskType;

    private Integer taskStatus;

    private String executorName;

    private Date createTime;

    private Date updateTime;

    private String createUserName;

    private String recordId;

    private String taskDesc;

    private String taskNo;

    private String estateId;

    private Date closeTime;

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public String toString() {
        return "FindAllTasksResp{" +
                "taskId='" + taskId + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskStatus=" + taskStatus +
                ", executorName='" + executorName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUserName='" + createUserName + '\'' +
                ", recordId='" + recordId + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", taskNo='" + taskNo + '\'' +
                ", estateId='" + estateId + '\'' +
                ", closeTime='" + closeTime + '\'' +
                '}';
    }
}

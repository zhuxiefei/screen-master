package com.betel.estatemgmt.business.propertyapp.task.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 查询任务详情回参
 * </p>
 * ClassName: FindTaskResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 19:54 <br/>
 * Version: 1.0 <br/>
 */
public class FindTaskResp {

    private String taskId;
    private String recordId;
    private String typeId;
    private String typeName;
    private Integer taskStatus;
    private Date updateTime;
    private String taskDesc;
    private String executorName;
    private String createUserName;
    private Date refuseTime;
    private Date acceptTime;
    private Date finishTime;
    private String refuseReason;
    private List<String> pictures;
    private String head;
    private String executor;
    private String taskNo;
    private Date closeTime;

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getRefuseTime() {
        return refuseTime;
    }

    public void setRefuseTime(Date refuseTime) {
        this.refuseTime = refuseTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindTaskResp{");
        sb.append("taskId='").append(taskId).append('\'');
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", taskDesc='").append(taskDesc).append('\'');
        sb.append(", executorName='").append(executorName).append('\'');
        sb.append(", createUserName='").append(createUserName).append('\'');
        sb.append(", refuseTime=").append(refuseTime);
        sb.append(", acceptTime=").append(acceptTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", refuseReason='").append(refuseReason).append('\'');
        sb.append(", pictures=").append(pictures);
        sb.append(", head='").append(head).append('\'');
        sb.append(", executor='").append(executor).append('\'');
        sb.append(", taskNo='").append(taskNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

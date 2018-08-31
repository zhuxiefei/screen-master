package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 修改任务入参
 * </p>
 * ClassName: UpdateTaskReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 14:43 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateTaskReq {

    private String taskId;

    private String recordId;

    private String typeId;

    private String taskDesc;

    private String executor;

    private String executorName;

    /**
     * 1为修改任务
     * 2为再次指派被拒绝的任务
     */
    private String type;

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    private String closeTime;

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

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateTaskReq{");
        sb.append("taskId='").append(taskId).append('\'');
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", taskDesc='").append(taskDesc).append('\'');
        sb.append(", executor='").append(executor).append('\'');
        sb.append(", executorName='").append(executorName).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

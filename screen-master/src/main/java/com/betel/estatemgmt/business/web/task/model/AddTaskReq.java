package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 添加任务入参
 * </p>
 * ClassName: AddTaskReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 10:30 <br/>
 * Version: 1.0 <br/>
 */
public class AddTaskReq {

    private String typeId;

    private String taskDesc;

    private String departmentIds;

    private String executors;

    private String executorNames;


    private String closeTime;

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
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

    public String getExecutors() {
        return executors;
    }

    public void setExecutors(String executors) {
        this.executors = executors;
    }

    public String getExecutorNames() {
        return executorNames;
    }

    public void setExecutorNames(String executorNames) {
        this.executorNames = executorNames;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddTaskReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append(", taskDesc='").append(taskDesc).append('\'');
        sb.append(", departmentIds='").append(departmentIds).append('\'');
        sb.append(", executors='").append(executors).append('\'');
        sb.append(", executorNames='").append(executorNames).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

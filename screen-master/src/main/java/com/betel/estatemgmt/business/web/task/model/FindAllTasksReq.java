package com.betel.estatemgmt.business.web.task.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 分页查询任务列表入参
 * </p>
 * ClassName: FindAllTasksReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 11:29 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllTasksReq extends Page {

    private String executorName;

    private String startTime;

    private String endTime;

    private String createUserName;

    private String departmentId;

    private String depId;

    private Integer depth;

    private String taskType;

    private String taskStatus;

    private String taskNo;

    private String estateId;

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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindAllTasksReq{");
        sb.append("executorName='").append(executorName).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", createUserName='").append(createUserName).append('\'');
        sb.append(", departmentId='").append(departmentId).append('\'');
        sb.append(", depId='").append(depId).append('\'');
        sb.append(", depth=").append(depth);
        sb.append(", taskType='").append(taskType).append('\'');
        sb.append(", taskStatus='").append(taskStatus).append('\'');
        sb.append(", taskNo='").append(taskNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

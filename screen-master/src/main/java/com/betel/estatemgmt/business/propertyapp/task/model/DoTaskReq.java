package com.betel.estatemgmt.business.propertyapp.task.model;

/**
 * <p>
 * 指派任务入参
 * </p>
 * ClassName: DoTaskReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/22 10:21 <br/>
 * Version: 1.0 <br/>
 */
public class DoTaskReq {

    private String taskId;
    private String recordId;
    private String type;
    private String refuseReason;
    private String picIds;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getPicIds() {
        return picIds;
    }

    public void setPicIds(String picIds) {
        this.picIds = picIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoTaskReq{");
        sb.append("taskId='").append(taskId).append('\'');
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", refuseReason='").append(refuseReason).append('\'');
        sb.append(", picIds='").append(picIds).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

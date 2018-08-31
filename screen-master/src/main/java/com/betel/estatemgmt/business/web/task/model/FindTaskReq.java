package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 查询任务详情入参
 * </p>
 * ClassName: FindTaskReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 13:31 <br/>
 * Version: 1.0 <br/>
 */
public class FindTaskReq {

    private String taskId;

    private String recordId;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindTaskReq{");
        sb.append("taskId='").append(taskId).append('\'');
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

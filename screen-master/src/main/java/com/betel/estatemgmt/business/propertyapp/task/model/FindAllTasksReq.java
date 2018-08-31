package com.betel.estatemgmt.business.propertyapp.task.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 查询发出/收到任务列表入参
 * </p>
 * ClassName: FindAllTasksReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 19:21 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllTasksReq extends Page {

    /**
     * 1为我发出的
     * 2为我收到的
     */
    private String type;

    /**
     * 任务状态：0代表全部；1为待接受；2为进行中；3为已完成；4为超时未完成
     */
    private String taskStatus;

    private String userId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindAllTasksReq{");
        sb.append("type='").append(type).append('\'');
        sb.append(", taskStatus='").append(taskStatus).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.task.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllRecordReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/5 15:45 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllRecordReq extends Page {
    private String taskNo;

    /**
     * 1为新增任务
     2为删除任务
     3为编辑任务
     4为完成任务
     5为拒绝任务
     6为接受任务
     7为指派任务
     */
    private String historyType;

    private String startTime;

    private String endTime;

    private String estateId;

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
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

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        return "FindAllRecordReq{" +
                "taskNo='" + taskNo + '\'' +
                ", historyType='" + historyType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

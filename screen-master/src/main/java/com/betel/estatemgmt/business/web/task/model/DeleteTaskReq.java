package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 删除任务记录入参
 * </p>
 * ClassName: DeleteTaskReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 15:59 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteTaskReq {

    private String recordIds;

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteTaskReq{");
        sb.append("recordIds='").append(recordIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

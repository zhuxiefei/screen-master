package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 删除任务类型入参
 * </p>
 * ClassName: DeleteTaskTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 10:02 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteTaskTypeReq {

    private String typeIds;

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteTaskTypeReq{");
        sb.append("typeIds='").append(typeIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 查询任务类型详情入参
 * </p>
 * ClassName: FindTaskTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 10:23 <br/>
 * Version: 1.0 <br/>
 */
public class FindTaskTypeReq {

    private String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindTaskTypeReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

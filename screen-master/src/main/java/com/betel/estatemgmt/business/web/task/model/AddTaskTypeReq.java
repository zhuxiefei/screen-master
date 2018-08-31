package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 添加任务类型入参
 * </p>
 * ClassName: AddTaskTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 9:38 <br/>
 * Version: 1.0 <br/>
 */
public class AddTaskTypeReq {

    private String typeName;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddTaskTypeReq{");
        sb.append("typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

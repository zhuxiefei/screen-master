package com.betel.estatemgmt.business.web.task.model;

/**
 * <p>
 * 修改任务类型入参
 * </p>
 * ClassName: UpdateTaskTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/12/21 9:52 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateTaskTypeReq {

    private String typeId;

    private String typeName;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    private String estateId;

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    private String closeTime;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateTaskTypeReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

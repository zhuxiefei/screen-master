package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 编辑资料类别
 * </p>
 * ClassName: UpdateTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/10 11:35 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateTypeReq {

    private String typeId;

    private String typeName;

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
        final StringBuilder sb = new StringBuilder("UpdateTypeReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

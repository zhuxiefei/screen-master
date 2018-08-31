package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 查询资料类别详情回参
 * </p>
 * ClassName: TypeInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 16:08 <br/>
 * Version: 1.0 <br/>
 */
public class TypeInfo {

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
        final StringBuilder sb = new StringBuilder("DocType{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

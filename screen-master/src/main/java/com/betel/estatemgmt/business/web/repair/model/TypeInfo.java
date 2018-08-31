package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询维修类型回参
 * </p>
 * ClassName: TypeInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:10 <br/>
 * Version: 1.0 <br/>
 */
public class TypeInfo {

    private Long typeId;

    private String typeName;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
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
        final StringBuilder sb = new StringBuilder("TypeInfo{");
        sb.append("typeId=").append(typeId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

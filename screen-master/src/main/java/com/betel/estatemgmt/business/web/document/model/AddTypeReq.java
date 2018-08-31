package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 添加资料类别入参
 * </p>
 * ClassName: AddTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 17:05 <br/>
 * Version: 1.0 <br/>
 */
public class AddTypeReq {
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddTypeReq{");
        sb.append("typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: TypeIds <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 17:04 <br/>
 * Version: 1.0 <br/>
 */
public class TypeIds {

    private String typeIds;

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypeIds{");
        sb.append("typeIds='").append(typeIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AddTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 10:55 <br/>
 * Version: 1.0 <br/>
 */
public class AddTypeReq {

    private String typeName;

    private String areaId;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddTypeReq{");
        sb.append("typeName='").append(typeName).append('\'');
        sb.append(", areaId='").append(areaId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DeleteTypeReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 11:08 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteTypeReq {

    private String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteTypeReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

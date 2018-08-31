package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DeleteAreaReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 10:22 <br/>
 * Version: 1.0 <br/>
 */
public class DeleteAreaReq {

    private String areaId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeleteAreaReq{");
        sb.append("areaId='").append(areaId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

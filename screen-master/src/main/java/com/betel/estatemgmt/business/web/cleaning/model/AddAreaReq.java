package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AddAreaReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 9:30 <br/>
 * Version: 1.0 <br/>
 */
public class AddAreaReq {

    private String areaName;

    private String estateId;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddAreaReq{");
        sb.append("areaName='").append(areaName).append('\'');
        sb.append(", estateId='").append(estateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

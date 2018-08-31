package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UpdateAreaReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 11:18 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateAreaReq {

    private String areaId;

    private String areaName;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateAreaReq{");
        sb.append("areaId='").append(areaId).append('\'');
        sb.append(", areaName='").append(areaName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

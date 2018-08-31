package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AddContentReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 13:33 <br/>
 * Version: 1.0 <br/>
 */
public class AddContentReq {

    private String areaId;

    private String typeId;

    private String location;

    private String cycle;

    private String content;

    private String estateId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddContentReq{");
        sb.append("areaId='").append(areaId).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", cycle='").append(cycle).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", estateId='").append(estateId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

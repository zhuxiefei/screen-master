package com.betel.estatemgmt.business.web.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UpdateContentReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 16:02 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateContentReq {

    private String contentId;

    private String areaId;

    private String typeId;

    private String location;

    private String cycle;

    private String content;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateContentReq{");
        sb.append("contentId='").append(contentId).append('\'');
        sb.append(", areaId='").append(areaId).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", cycle='").append(cycle).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

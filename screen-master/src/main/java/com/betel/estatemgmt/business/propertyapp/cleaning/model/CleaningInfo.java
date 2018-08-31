package com.betel.estatemgmt.business.propertyapp.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 11:06 <br/>
 * Version: 1.0 <br/>
 */
public class CleaningInfo {

    String contentId;

    String contentNo;

    String areaName;

    String typeName;

    String location;

    String cycle;

    String content;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
        final StringBuilder sb = new StringBuilder("CleaningInfo{");
        sb.append("contentId='").append(contentId).append('\'');
        sb.append(", contentNo='").append(contentNo).append('\'');
        sb.append(", areaName='").append(areaName).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", cycle='").append(cycle).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

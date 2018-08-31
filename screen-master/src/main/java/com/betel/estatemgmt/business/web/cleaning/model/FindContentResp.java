package com.betel.estatemgmt.business.web.cleaning.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindContentResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 15:41 <br/>
 * Version: 1.0 <br/>
 */
public class FindContentResp {

    private String contentId;

    private String contentNo;

    private String areaName;

    private String typeName;

    private String location;

    private Integer cycle;

    private String content;

    private Date createTime;

    private Date updateTime;

    private String areaId;

    private String typeId;

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

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FindContentResp{" +
                "contentId='" + contentId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", areaName='" + areaName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", location='" + location + '\'' +
                ", cycle=" + cycle +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", areaId='" + areaId + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}

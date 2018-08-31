package com.betel.estatemgmt.business.web.cleaning.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllRecordsResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/3/1 9:23 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllRecordsResp {

    private String recordId;

    private String contentNo;

    private String areaName;

    private String typeName;

    private String location;

    private Integer cycle;

    private Integer isStandard;

    private Date createTime;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public Integer getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Integer isStandard) {
        this.isStandard = isStandard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FindAllRecordsResp{" +
                "recordId='" + recordId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", areaName='" + areaName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", location='" + location + '\'' +
                ", cycle=" + cycle +
                ", isStandard=" + isStandard +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}

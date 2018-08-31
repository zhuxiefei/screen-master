package com.betel.estatemgmt.business.web.cleaning.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindRecordResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/3/1 9:42 <br/>
 * Version: 1.0 <br/>
 */
public class FindRecordResp {

    private String recordId;

    private String contentNo;

    private String areaName;

    private String typeName;

    private String location;

    private Integer cycle;

    private String content;

    private String examiner;

    private Integer isStandard;

    private String problemRecord;

    private Date createTime;

    private List<String> pictureUrls;

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public Integer getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Integer isStandard) {
        this.isStandard = isStandard;
    }

    public String getProblemRecord() {
        return problemRecord;
    }

    public void setProblemRecord(String problemRecord) {
        this.problemRecord = problemRecord;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FindRecordResp{" +
                "recordId='" + recordId + '\'' +
                ", contentNo='" + contentNo + '\'' +
                ", areaName='" + areaName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", location='" + location + '\'' +
                ", cycle=" + cycle +
                ", content='" + content + '\'' +
                ", examiner='" + examiner + '\'' +
                ", isStandard=" + isStandard +
                ", problemRecord='" + problemRecord + '\'' +
                ", createTime=" + createTime +
                ", pictureUrls=" + pictureUrls +
                '}';
    }
}

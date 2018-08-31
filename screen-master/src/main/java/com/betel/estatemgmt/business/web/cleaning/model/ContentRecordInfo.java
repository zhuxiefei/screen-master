package com.betel.estatemgmt.business.web.cleaning.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ContentRecordInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 17:15 <br/>
 * Version: 1.0 <br/>
 */
public class ContentRecordInfo {

    private String recordId;

    private Date createTime;

    private String examiner;

    private Integer isStandard;

    private String problemRecord;

    private List<String> pictureUrls;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentRecordInfo{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", examiner='").append(examiner).append('\'');
        sb.append(", isStandard=").append(isStandard);
        sb.append(", problemRecord='").append(problemRecord).append('\'');
        sb.append(", pictureUrls=").append(pictureUrls);
        sb.append('}');
        return sb.toString();
    }
}

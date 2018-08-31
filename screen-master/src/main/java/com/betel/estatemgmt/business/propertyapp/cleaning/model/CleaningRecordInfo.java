package com.betel.estatemgmt.business.propertyapp.cleaning.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningRecordInfo <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 14:40 <br/>
 * Version: 1.0 <br/>
 */
public class CleaningRecordInfo {

    private String examiner;

    private Date createTime;

    private String isStandard;

    private String problemRecord;

    private List<String> pictureUrls;

    private String recordId;

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CleaningRecordInfo{");
        sb.append("examiner='").append(examiner).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", isStandard='").append(isStandard).append('\'');
        sb.append(", problemRecord='").append(problemRecord).append('\'');
        sb.append(", pictureUrls=").append(pictureUrls);
        sb.append(", recordId='").append(recordId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.common.model.cleaning;

import java.util.Date;

public class CleaningRecord {
    private String recordId;

    private String contentId;

    private String cleaner;

    private String signRecord;

    private Integer isStandard;

    private String problemRecord;

    private String examiner;

    private Date createTime;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String cleaner) {
        this.cleaner = cleaner == null ? null : cleaner.trim();
    }

    public String getSignRecord() {
        return signRecord;
    }

    public void setSignRecord(String signRecord) {
        this.signRecord = signRecord == null ? null : signRecord.trim();
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
        this.problemRecord = problemRecord == null ? null : problemRecord.trim();
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner == null ? null : examiner.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
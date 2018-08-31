package com.betel.estatemgmt.business.propertyapp.cleaning.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AddAssessmentReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 13:53 <br/>
 * Version: 1.0 <br/>
 */
public class AddAssessmentReq {

    private String contentId;

    private String isStandard;

    private String problemRecord;

    private String picIds;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getPicIds() {
        return picIds;
    }

    public void setPicIds(String picIds) {
        this.picIds = picIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddAssessmentReq{");
        sb.append("contentId='").append(contentId).append('\'');
        sb.append(", isStandard='").append(isStandard).append('\'');
        sb.append(", problemRecord='").append(problemRecord).append('\'');
        sb.append(", picIds='").append(picIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

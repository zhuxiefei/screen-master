package com.betel.estatemgmt.business.propertyapp.propertyfeedback.model;

/**
 * <p>
 * 文字意见反馈入参
 * </p>
 * ClassName: AddFeedBackInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 15:21 <br/>
 * Version: 1.0 <br/>
 */
public class AddFeedBackInfo {

    private String feedBackContent;

    private Integer backType;

    private String phoneType;

    private String phoneSystem;

    private String applicationType;

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneSystem() {
        return phoneSystem;
    }

    public void setPhoneSystem(String phoneSystem) {
        this.phoneSystem = phoneSystem;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getFeedBackContent() {
        return feedBackContent;
    }

    public void setFeedBackContent(String feedBackContent) {


        this.feedBackContent = feedBackContent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddFeedBackInfo{");
        sb.append("feedBackContent='").append(feedBackContent).append('\'');
        sb.append(", backType=").append(backType);
        sb.append(", phoneType='").append(phoneType).append('\'');
        sb.append(", phoneSystem='").append(phoneSystem).append('\'');
        sb.append(", applicationType='").append(applicationType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.web.feedback.model;

import java.util.Date;

/**
 * <p>
 * 反馈列表回参
 * </p>
 * ClassName: Feedback <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 14:31 <br/>
 * Version: 1.0 <br/>
 */
public class Feedback {
    /**
     * 反馈编号
     */
    private Long feedbackId;

    /**
     * 反馈人昵称
     */
    private String feedbackName;

    /**
     * 意见类型
     */
    private Integer feedbackType;

    /**
     * App版本
     */
    private String appVersion;

    /**
     * 反馈时间
     */
    private Date createTime;

    /**
     * app类型：1为业主APP，2为物业APP
     */
    private Integer appType;

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Date getCreateTime() {
        if(createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Feedback{");
        sb.append("feedbackId=").append(feedbackId);
        sb.append(", feedbackName='").append(feedbackName).append('\'');
        sb.append(", feedbackType=").append(feedbackType);
        sb.append(", appVersion='").append(appVersion).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", appType=").append(appType);
        sb.append('}');
        return sb.toString();
    }
}

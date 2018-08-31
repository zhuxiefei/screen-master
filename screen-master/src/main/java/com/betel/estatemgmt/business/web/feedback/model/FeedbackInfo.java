package com.betel.estatemgmt.business.web.feedback.model;

import java.util.Date;

/**
 * <p>
 * 反馈详情回参
 * </p>
 * ClassName: FeedbackInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 14:37 <br/>
 * Version: 1.0 <br/>
 */
public class FeedbackInfo {
    /**
     * 反馈人昵称
     */
    private String feedbackName;

    /**
     * 反馈时间
     */
    private Date createTime;

    /**
     * 意见类型
     */
    private Integer feedbackType;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 手机型号
     */
    private String clientModel;

    /**
     * 手机系统版本
     */
    private String clientVersion;

    /**
     * App版本
     */
    private String appVersion;

    private Integer appType;

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
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

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getClientModel() {
        return clientModel;
    }

    public void setClientModel(String clientModel) {
        this.clientModel = clientModel;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeedbackInfo{");
        sb.append("feedbackName='").append(feedbackName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", feedbackType=").append(feedbackType);
        sb.append(", feedbackContent='").append(feedbackContent).append('\'');
        sb.append(", clientModel='").append(clientModel).append('\'');
        sb.append(", clientVersion='").append(clientVersion).append('\'');
        sb.append(", appVersion='").append(appVersion).append('\'');
        sb.append(", appType=").append(appType);
        sb.append('}');
        return sb.toString();
    }
}

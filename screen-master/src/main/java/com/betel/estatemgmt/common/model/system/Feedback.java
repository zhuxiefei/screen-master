package com.betel.estatemgmt.common.model.system;

import java.util.Date;

public class Feedback {
    private Long feedbackId;

    private String authorId;

    private Integer feedbackStatus;

    private Date createTime;

    private String feedbackContent;

    private Integer feedbackType;

    private String clientModel;

    private String clientVersion;

    private String appVersion;

    private Integer appType;

    private String authorName;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
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

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public Integer getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(Integer feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public Date getCreateTime()
    {
        if (createTime == null)
        {
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime)
    {
        if (createTime == null)
        {
            this.createTime = null;
        } else {
            this.createTime = (Date)createTime.clone();
        }
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent == null ? null : feedbackContent.trim();

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Feedback{");
        sb.append("feedbackId=").append(feedbackId);
        sb.append(", authorId='").append(authorId).append('\'');
        sb.append(", feedbackStatus=").append(feedbackStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", feedbackContent='").append(feedbackContent).append('\'');
        sb.append(", feedbackType=").append(feedbackType);
        sb.append(", clientModel='").append(clientModel).append('\'');
        sb.append(", clientVersion='").append(clientVersion).append('\'');
        sb.append(", appVersion='").append(appVersion).append('\'');
        sb.append(", appType=").append(appType);
        sb.append(", authorName='").append(authorName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
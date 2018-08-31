package com.betel.estatemgmt.business.web.feedback.model;

/**
 * <p>
 * 反馈列表入参
 * </p>
 * ClassName: FeedbackPageReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 14:03 <br/>
 * Version: 1.0 <br/>
 */
public class FeedbackPageReq {
    private Integer curPage = 1;

    private Integer pageSize = 10;

    /**
     * 反馈人昵称
     */
    private String feedbackName;

    /**
     * 意见类型
     */
    private String feedbackType;

    /**
     * App版本
     */
    private String appVersion;

    /**
     * 反馈起始时间
     */
    private String startTime;

    /**
     * 反馈结束时间
     */
    private String endTime;

    private String appType;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "FeedbackPageReq{" +
                "curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", feedbackName='" + feedbackName + '\'' +
                ", feedbackType='" + feedbackType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", appType='" + appType + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

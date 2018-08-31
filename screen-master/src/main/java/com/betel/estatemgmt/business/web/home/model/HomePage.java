package com.betel.estatemgmt.business.web.home.model;

/**
 * Created by zhangjian on 2017/8/7.
 */
public class HomePage {
    private String topicToAudit;
    private String postToAudit;
    private String replayToAudit;
    private String activityToAudit;
    private String tradingGoodsToAudit;
    private String tradingReplyToAudit;
    private String reportTopicToAudit;
    private String reportPostToAudit;
    private String maintainToAudit;


    public String getSendReceipt() {
        return sendReceipt;
    }

    public void setSendReceipt(String sendReceipt) {
        this.sendReceipt = sendReceipt;
    }

    private String sendReceipt;

    public String getTopicToAudit() {
        return topicToAudit;
    }

    public void setTopicToAudit(String topicToAudit) {
        this.topicToAudit = topicToAudit;
    }

    public String getPostToAudit() {
        return postToAudit;
    }

    public void setPostToAudit(String postToAudit) {
        this.postToAudit = postToAudit;
    }

    public String getReplayToAudit() {
        return replayToAudit;
    }

    public void setReplayToAudit(String replayToAudit) {
        this.replayToAudit = replayToAudit;
    }

    public String getActivityToAudit() {
        return activityToAudit;
    }

    public void setActivityToAudit(String activityToAudit) {
        this.activityToAudit = activityToAudit;
    }

    public String getTradingGoodsToAudit() {
        return tradingGoodsToAudit;
    }

    public void setTradingGoodsToAudit(String tradingGoodsToAudit) {
        this.tradingGoodsToAudit = tradingGoodsToAudit;
    }

    public String getTradingReplyToAudit() {
        return tradingReplyToAudit;
    }

    public void setTradingReplyToAudit(String tradingReplyToAudit) {
        this.tradingReplyToAudit = tradingReplyToAudit;
    }

    public String getReportTopicToAudit() {
        return reportTopicToAudit;
    }

    public void setReportTopicToAudit(String reportTopicToAudit) {
        this.reportTopicToAudit = reportTopicToAudit;
    }

    public String getReportPostToAudit() {
        return reportPostToAudit;
    }

    public void setReportPostToAudit(String reportPostToAudit) {
        this.reportPostToAudit = reportPostToAudit;
    }

    public String getMaintainToAudit() {
        return maintainToAudit;
    }

    public void setMaintainToAudit(String maintainToAudit) {
        this.maintainToAudit = maintainToAudit;
    }


    @Override
    public String toString() {
        return "HomePage{" +
                "topicToAudit='" + topicToAudit + '\'' +
                ", postToAudit='" + postToAudit + '\'' +
                ", replayToAudit='" + replayToAudit + '\'' +
                ", activityToAudit='" + activityToAudit + '\'' +
                ", tradingGoodsToAudit='" + tradingGoodsToAudit + '\'' +
                ", tradingReplyToAudit='" + tradingReplyToAudit + '\'' +
                ", reportTopicToAudit='" + reportTopicToAudit + '\'' +
                ", reportPostToAudit='" + reportPostToAudit + '\'' +
                ", maintainToAudit='" + maintainToAudit + '\'' +
                ", sendReceipt='" + sendReceipt + '\'' +
                '}';
    }

}

package com.betel.estatemgmt.business.web.feedback.model;

/**
 * <p>
 * 删除反馈入参
 * </p>
 * ClassName: FeedbackIdsReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 14:41 <br/>
 * Version: 1.0 <br/>
 */
public class FeedbackIdsReq {
    /**
     *反馈编号。多个中间用,隔开
     */
    private String feedbackIds;

    public String getFeedbackIds() {
        return feedbackIds;
    }

    public void setFeedbackIds(String feedbackIds) {
        this.feedbackIds = feedbackIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeedbackIdsReq{");
        sb.append("feedbackIds='").append(feedbackIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

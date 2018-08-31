package com.betel.estatemgmt.business.web.feedback.model;

/**
 * <p>
 * 反馈详情入参
 * </p>
 * ClassName: FeedbackIdReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 14:43 <br/>
 * Version: 1.0 <br/>
 */
public class FeedbackIdReq {
    /**
     * 反馈编号
     */
    private Long feedbackId;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeedbackIdReq{");
        sb.append("feedbackId=").append(feedbackId);
        sb.append('}');
        return sb.toString();
    }
}

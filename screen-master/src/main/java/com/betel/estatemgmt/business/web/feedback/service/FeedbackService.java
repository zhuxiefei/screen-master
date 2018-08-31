package com.betel.estatemgmt.business.web.feedback.service;

import com.betel.estatemgmt.business.web.feedback.model.ExportReq;
import com.betel.estatemgmt.business.web.feedback.model.Feedback;
import com.betel.estatemgmt.business.web.feedback.model.FeedbackInfo;
import com.betel.estatemgmt.business.web.feedback.model.FeedbackPageReq;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 意见反馈实现接口
 * </p>
 * ClassName: FeedbackService <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 13:35 <br/>
 * Version: 1.0 <br/>
 */
public interface FeedbackService {
    List<Feedback> findAllFeedback(Paging<Feedback> pager, FeedbackPageReq feedbackPage);

    void deleteFeedback(String feedbackIds);

    com.betel.estatemgmt.common.model.system.Feedback findByFeedbackId(Long feedbackId);

    FeedbackInfo findInfoByFeedbackId(Long feedbackId);

    List<FeedbackInfo> findByFeedbackIds(String feedbackIds);

    List<FeedbackInfo> findByKeywords(ExportReq req);

    List<FeedbackInfo> findFeedbackInfos(String estateId);
}

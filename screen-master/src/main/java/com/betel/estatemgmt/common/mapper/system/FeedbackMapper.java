package com.betel.estatemgmt.common.mapper.system;

import com.betel.estatemgmt.business.web.feedback.model.ExportReq;
import com.betel.estatemgmt.business.web.feedback.model.FeedbackInfo;
import com.betel.estatemgmt.business.web.feedback.model.FeedbackPageReq;
import com.betel.estatemgmt.common.model.system.Feedback;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackMapper {
    int deleteByPrimaryKey(Long feedbackId);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Long feedbackId);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKeyWithBLOBs(Feedback record);

    int updateByPrimaryKey(Feedback record);

    List<com.betel.estatemgmt.business.web.feedback.model.Feedback> findAllFeedback(RowBounds rowBounds, FeedbackPageReq feedbackPageReq);

    void deleteByFeedbackIds(Long[] array);

    FeedbackInfo selectByFeedbackId(long feedbackId);

    List<FeedbackInfo> selectByFeedbackIds(Long[] array);

    List<FeedbackInfo> selectByKeywords(ExportReq exportReq);

    List<FeedbackInfo> selectFeedbackInfos(String estateId);
}
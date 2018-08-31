package com.betel.estatemgmt.business.web.feedback.service.impl;

import com.betel.estatemgmt.business.web.feedback.model.ExportReq;
import com.betel.estatemgmt.business.web.feedback.model.Feedback;
import com.betel.estatemgmt.business.web.feedback.model.FeedbackInfo;
import com.betel.estatemgmt.business.web.feedback.model.FeedbackPageReq;
import com.betel.estatemgmt.business.web.feedback.service.FeedbackService;
import com.betel.estatemgmt.common.mapper.system.FeedbackMapper;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 意见反馈实现类
 * </p>
 * ClassName: FeedbackServiceImpl <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 13:35 <br/>
 * Version: 1.0 <br/>
 */
@Service("FeedbackService")
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Override
    public List<Feedback> findAllFeedback(Paging<Feedback> pager, FeedbackPageReq feedbackPage) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findAllFeedback start========" +
                    "Paging<Feedback>="+pager+",FeedbackPageReq="+feedbackPage);
        }
        List<Feedback> list = feedbackMapper.findAllFeedback(pager.getRowBounds(),feedbackPage);
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findAllFeedback end========List<Feedback>=" +list);
        }
        return list;
    }

    @Override
    public void deleteFeedback(String feedbackIds) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl deleteFeedback start========feedbackIds=" +feedbackIds);
        }
        String[] stringArray = feedbackIds.split(",");
        if(LOG.isDebugEnabled()){
            if (stringArray != null){
                LOG.debug("========feedbackIds to stringArray========" + Arrays.asList(stringArray));
            }
        }
        Long[] longArray = new Long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            //将String数组转为Long数组
            longArray[i] = Long.parseLong(stringArray[i]);
        }
        if(LOG.isDebugEnabled()){
            if (longArray != null){
                LOG.debug("========deleteFeedback========feedbackIdArray" + Arrays.asList(longArray));
            }
        }
        feedbackMapper.deleteByFeedbackIds(longArray);
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl deleteFeedback end========");
        }
    }

    @Override
    public com.betel.estatemgmt.common.model.system.Feedback findByFeedbackId(Long feedbackId) {
        return feedbackMapper.selectByPrimaryKey(feedbackId);
    }

    @Override
    public FeedbackInfo findInfoByFeedbackId(Long feedbackId) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findInfoByFeedbackId start========feedbackId=" + feedbackId);
        }
        FeedbackInfo info = feedbackMapper.selectByFeedbackId(feedbackId);
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findInfoByFeedbackId end========feedbakcInfo=" + info);
        }
        return info;
    }

    @Override
    public List<FeedbackInfo> findByFeedbackIds(String feedbackIds) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findByFeedbackIds start========feedbackIds=" +feedbackIds);
        }
        String[] stringArray = feedbackIds.split(",");
        if(LOG.isDebugEnabled()){
            if (stringArray != null){
                LOG.debug("========feedbackIds to stringArray========" + Arrays.asList(stringArray));
            }
        }
        Long[] longArray = new Long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            //将String数组转为Long数组
            longArray[i] = Long.parseLong(stringArray[i]);
        }
        if(LOG.isDebugEnabled()){
            if (longArray != null){
                LOG.debug("========findByFeedbackIds========feedbackIdArray" + Arrays.asList(longArray));
            }
        }
        List<FeedbackInfo> list = feedbackMapper.selectByFeedbackIds(longArray);
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findByFeedbackIds end========");
        }
        return list;
    }

    @Override
    public List<FeedbackInfo> findByKeywords(ExportReq req) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findByKeywords start========ExportReq="+req);
        }
        List<FeedbackInfo> list = feedbackMapper.selectByKeywords(req);
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findByKeywords end========List<Feedback>=" +list);
        }
        return list;
    }

    @Override
    public List<FeedbackInfo> findFeedbackInfos(String estateId) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findFeedbackInfos start========");
        }
        List<FeedbackInfo> list = feedbackMapper.selectFeedbackInfos(estateId);
        if(LOG.isDebugEnabled()){
            LOG.debug("========FeedbackServiceImpl findFeedbackInfos end========List<Feedback>=" +list);
        }
        return list;
    }
}

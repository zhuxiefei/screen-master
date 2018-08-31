package com.betel.estatemgmt.business.web.feedback.controller;

import com.betel.estatemgmt.business.web.feedback.code.FeedbackCode;
import com.betel.estatemgmt.business.web.feedback.constant.FeedbackDataValidation;
import com.betel.estatemgmt.business.web.feedback.model.*;
import com.betel.estatemgmt.business.web.feedback.service.FeedbackService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 意见反馈接口
 * </p>
 * ClassName: FeedbackController <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/31 13:50 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/feedback")
public class FeedbackController extends BaseController {
    @Autowired
    private FeedbackService feedbackService;

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

    /**
     * <p>
     * 查询反馈列表（分页）
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 15:44
     *
     *
     * @param feedbackPage 分页入参
     * @return response
     */
    @RequiresPermissions(value = "suggest-findAllFeedback")
    @RequestMapping(value = "v1/findAllFeedback", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<Feedback>> findAllFeedback(@RequestBody FeedbackPageReq feedbackPage, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/findAllFeedback start========feedbackPage=" + feedbackPage);
        }
        Response<Paging<Feedback>> response = new Response<>();

        if (!StringUtil.isBlank(feedbackPage.getFeedbackName())
                && !feedbackPage.getFeedbackName().trim().matches(FeedbackDataValidation.FEEDBACK_NAME_RULE)){
            response.setCode(FeedbackCode.NAME_RULE_WRONG);
        }else if (!StringUtil.isBlank(feedbackPage.getAppVersion())
                && !feedbackPage.getAppVersion().trim().matches(FeedbackDataValidation.APP_VERSION_RULE)){
            response.setCode(FeedbackCode.APPVERSION_RULE_WRONG);
        }else if (!StringUtil.isBlank(feedbackPage.getStartTime()) && !validateTime(feedbackPage.getStartTime().trim())){
            response.setCode(FeedbackCode.TIME_RULE_WRONG);
        }else if (!StringUtil.isBlank(feedbackPage.getEndTime()) && !validateTime(feedbackPage.getEndTime().trim())){
            response.setCode(FeedbackCode.TIME_RULE_WRONG);
        }else if (!StringUtil.isBlank(feedbackPage.getStartTime()) && !StringUtil.isBlank(feedbackPage.getEndTime())
                && validateTime(feedbackPage.getStartTime().trim()) && validateTime(feedbackPage.getEndTime().trim())
                && !validateTimeDistance(feedbackPage.getStartTime(),feedbackPage.getEndTime())){
            response.setCode(FeedbackCode.ENDTIME_EARLY_STARTTIME);
        }else {
            Paging<Feedback> pager = new Paging<>(feedbackPage.getCurPage(), feedbackPage.getPageSize());
            //前后去空格
            if (!StringUtil.isEmpty(feedbackPage.getFeedbackName())){
                feedbackPage.setFeedbackName(feedbackPage.getFeedbackName().trim());
            }
            if (!StringUtil.isEmpty(feedbackPage.getAppVersion())){
                feedbackPage.setAppVersion(feedbackPage.getAppVersion().trim());
            }
            if (!StringUtil.isEmpty(feedbackPage.getStartTime())){
                feedbackPage.setStartTime(feedbackPage.getStartTime().trim());
            }
            if (!StringUtil.isEmpty(feedbackPage.getEndTime())){
                feedbackPage.setEndTime(feedbackPage.getEndTime().trim());
            }
            if (!StringUtil.isEmpty(feedbackPage.getFeedbackType())){
                feedbackPage.setFeedbackType(feedbackPage.getFeedbackType().trim());
            }
            //去“_”
            if (!StringUtil.isBlank(feedbackPage.getFeedbackName()) && feedbackPage.getFeedbackName().contains("_")) {
                feedbackPage.setFeedbackName(feedbackPage.getFeedbackName().replace("_","\\_"));
            }
            if (!StringUtil.isBlank(feedbackPage.getAppVersion()) && feedbackPage.getAppVersion().contains("_")) {
                feedbackPage.setAppVersion(feedbackPage.getAppVersion().replace("_","\\_"));
            }
            try {
                feedbackPage.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
                //查询反馈列表
                List<Feedback> fedbackList = feedbackService.findAllFeedback(pager,feedbackPage);
                pager.result(fedbackList);
                response.setData(pager);
            } catch (Exception e) {
                LOG.error("========web/feedback/v1/findAllFeedback error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/findAllFeedback end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除反馈（批量）
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 18:31
     *
     * @param feedbackIdsReq 入参
     * @return response
     */
    @RequiresPermissions(value = "suggest-deleteFeedback")
    @RequestMapping(value = "v1/deleteFeedback", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteFeedback(@RequestBody FeedbackIdsReq feedbackIdsReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/deleteFeedback start========feedbackIdsReq=" + feedbackIdsReq);
        }
        Response<String> response = new Response<>();
        //判断编号是否为空
        if (StringUtil.isBlank(feedbackIdsReq.getFeedbackIds())) {
            response.setCode(FeedbackCode.FEEDBACK_ID_NULL);
        } else {
            try {
                //删除反馈
                feedbackService.deleteFeedback(feedbackIdsReq.getFeedbackIds());
            } catch (Exception e) {
                LOG.error("========web/feedback/v1/deleteFeedback error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/deleteFeedback end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询反馈详情
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 18:48
     *
     * @param feedbackIdReq 入参
     * @return response
     */
    @RequiresPermissions(value = "suggest-findFeedback")
    @RequestMapping(value = "v1/findFeedback", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<FeedbackInfo> findFeedback(@RequestBody FeedbackIdReq feedbackIdReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/findFeedback start========feedbackIdReq=" + feedbackIdReq);
        }
        Response<FeedbackInfo> response = new Response<>();
        //判断编号是否为空
        if (feedbackIdReq.getFeedbackId() == null) {
            response.setCode(FeedbackCode.FEEDBACK_ID_NULL);
        } else if (feedbackService.findByFeedbackId(feedbackIdReq.getFeedbackId()) == null) {
            response.setCode(FeedbackCode.FEEDBACK_IS_DELETE);
        } else {
            try {
                FeedbackInfo info = feedbackService.findInfoByFeedbackId(feedbackIdReq.getFeedbackId());
                response.setData(info);
            } catch (Exception e) {
                LOG.error("========web/feedback/v1/findFeedback error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/findFeedback end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 导出反馈
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 8:55
     * @param exportReq 入参
     * @return response 回参
     *
     * */
    @RequiresPermissions(value = "suggest-exportFeedback")
    @RequestMapping(value = "v1/exportFeedback", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<FeedbackInfo>> exportFeedback(@RequestBody ExportReq exportReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/exportFeedback start========exportReq=" + exportReq);
        }
        Response<List<FeedbackInfo>> response = new Response<>();
        try {
            exportReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            if (!StringUtil.isBlank(exportReq.getFeedbackIds())){
                response.setData(feedbackService.findByFeedbackIds(exportReq.getFeedbackIds()));
            }else if (!StringUtil.isBlank(exportReq.getAppVersion()) || !StringUtil.isBlank(exportReq.getEndTime())
                    || !StringUtil.isBlank(exportReq.getFeedbackName()) || !StringUtil.isBlank(exportReq.getFeedbackType())
                    || !StringUtil.isBlank(exportReq.getStartTime()) || !StringUtil.isBlank(exportReq.getAppType())){
                //前后去空格
                if (!StringUtil.isEmpty(exportReq.getFeedbackName())){
                    exportReq.setFeedbackName(exportReq.getFeedbackName().trim());
                }
                if (!StringUtil.isEmpty(exportReq.getAppVersion())){
                    exportReq.setAppVersion(exportReq.getAppVersion().trim());
                }
                if (!StringUtil.isEmpty(exportReq.getStartTime())){
                    exportReq.setStartTime(exportReq.getStartTime().trim());
                }
                if (!StringUtil.isEmpty(exportReq.getEndTime())){
                    exportReq.setEndTime(exportReq.getEndTime().trim());
                }
                if (!StringUtil.isEmpty(exportReq.getFeedbackType())){
                    exportReq.setFeedbackType(exportReq.getFeedbackType().trim());
                }
                response.setData(feedbackService.findByKeywords(exportReq));
            }else {
                response.setData(feedbackService.findFeedbackInfos(exportReq.getEstateId()));
            }
        } catch (Exception e) {
            LOG.error("========web/feedback/v1/exportFeedback error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/feedback/v1/exportFeedback end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    private boolean validateTime(String time){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }


    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    private boolean validateTimeDistance(String startTime,String endTime){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = format.parse(startTime).getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2){
                flag = false;
            }
        } catch (ParseException e) {}
        return flag;
    }
}

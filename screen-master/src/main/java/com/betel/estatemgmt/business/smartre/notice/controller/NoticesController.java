package com.betel.estatemgmt.business.smartre.notice.controller;

import com.betel.estatemgmt.business.propertyapp.notice.model.NoticeReq;
import com.betel.estatemgmt.business.propertyapp.notice.service.ReplyNoticeService;
import com.betel.estatemgmt.business.smartre.notice.model.NoticeInfoReq;
import com.betel.estatemgmt.business.smartre.notice.model.NoticeResp;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * 帖子回复通知
 * </p>
 * ClassName: NoticeController <br/>
 * Author: jians.z  <br/>
 * Date: 2018/1/31 11:19 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("httpclientProperty/notice")
public class NoticesController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(NoticesController.class);
    @Autowired
    private ReplyNoticeService replyNoticeService;

    /**
     * <p>
     * 插入系统通知
     * </p>
     * Author: jians.z <br/>
     * Date: 2018/1/29 9:21
     *
     * @param noticeReq
     */
    @RequestMapping(value = "v1/push", method = RequestMethod.POST)
    public Response<String> push(HttpServletRequest request, @RequestBody NoticeReq noticeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/push start ========noticeReq=" + noticeReq);
        }
        Response response = new Response();
        try {
            response.setCode(replyNoticeService.push(noticeReq));
        } catch (Exception e) {
            LOG.error("-----push---------error!", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/push end ========response=" + response);
        }
        return response;
    }

//    /**
//     * <p>
//     * 批量插入
//     * </p>
//     * Author: jian.z <br/>
//     * Date: 2017/5/16 16:02
//     *
//     * @param
//     * @return response
//     */
//    @RequestMapping(value = "v1/insertNoticeList", method = RequestMethod.GET)
//    public Response<List<Notice>> insertNoticeList(HttpServletRequest request, String noticeJson) {
//        if (LOG.isInfoEnabled()) {
//            LOG.info("======== app/notice/v1/insertNoticeList start ========noticeReq=" + noticeJson);
//        }
//        Response<List<Notice>> response = new Response<List<Notice>>();
//        try {
////            if (null!=noticeList.getNoticeJson()) {
////                List<Notice> noticeList = noticesReq.getNoticeJson();
////////                List<Notice> noticeList = JsonUtil.jsonToList(noticeReq.getNoticeJson(), Notice.class);
//////                response.setData(replyNoticeService.insertNoticeList(noticeList));
////            }
//        } catch (Exception e) {
//            LOG.error("------insertNoticeList-------rror!", e);
//            response.setCode(FAILURE);
//        }
//        return response;
//    }

    /**
     * <p>
     * 批量插入
     * </p>
     * Author: jian.z <br/>
     * Date: 2017/5/16 16:02
     *
     * @param
     * @return response
     */
    @RequestMapping(value = "v1/insertList", method = RequestMethod.POST)
    public Response<List<NoticeResp>> insertList(HttpServletRequest request, @RequestBody  NoticeInfoReq noticeInfoReq)  {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/insertList start ========noticeReq=" + noticeInfoReq);
        }
        Response<List<NoticeResp>> response = new Response<List<NoticeResp>>();
        try {
            List<NoticeResp> noticeList=replyNoticeService.insertList(noticeInfoReq);
            response.setData(noticeList);
        } catch (Exception e) {
            LOG.error("------insertList-------rror!", e);
            response.setCode(FAILURE);
        }
        return response;
    }
}

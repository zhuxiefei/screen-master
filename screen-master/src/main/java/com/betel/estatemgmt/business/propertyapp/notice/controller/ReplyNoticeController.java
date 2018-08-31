package com.betel.estatemgmt.business.propertyapp.notice.controller;


import com.betel.estatemgmt.business.propertyapp.notice.code.NoticeCode;
import com.betel.estatemgmt.business.propertyapp.notice.constant.NoticeConstant;
import com.betel.estatemgmt.business.propertyapp.notice.model.NoticeId;
import com.betel.estatemgmt.business.propertyapp.notice.model.NoticeReq;
import com.betel.estatemgmt.business.propertyapp.notice.model.SystemNoticeInfo;
import com.betel.estatemgmt.business.propertyapp.notice.model.UnreadNumber;
import com.betel.estatemgmt.business.propertyapp.notice.service.ReplyNoticeService;
import com.betel.estatemgmt.common.Page;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.utils.JsonUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * 帖子回复通知
 * </p>
 * ClassName: ReplyNoticeController <br/>
 * Author: geyf  <br/>
 * Date: 2017/5/16 15:58 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/notice")
public class ReplyNoticeController {

    private static final Logger LOG = LoggerFactory.getLogger(ReplyNoticeController.class);

    @Autowired
    private ReplyNoticeService replyNoticeService;

    /**
     * <p>
     * 查询系统通知的未读数量
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 10:24
     */
    @RequestMapping(value = "/v1/findUnreadNumber", method = RequestMethod.GET)
    public Response findUnreadNumber(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/findSystemNotice start ========");
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        try {
            userId = AESUtil.decrypt(userId); //解密
        } catch (Exception e) {
            LOG.error("findAllTopicReplyNotice error!", e);
            response.setCode(FAILURE);
        }
        try {
            UnreadNumber unreadNumber = replyNoticeService.findUnreadNumber(userId,request);
            response.setData(unreadNumber);
        } catch (Exception e) {
            LOG.error("findUnreadNumber error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/findSystemNotice end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询系统通知详情
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 9:40
     *
     * @param noticeId 通知ID
     */
    @RequestMapping(value = "/v1/findSystemNotice", method = RequestMethod.GET)
    public Response findSystemNotice(NoticeId noticeId) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/findSystemNotice start ========noticeId=" + noticeId);
        }
        Response response = new Response();
        if (noticeId.getNoticeId() != null) {
            try {
                Notice systemNotice = replyNoticeService.findSystemNotice(noticeId.getNoticeId());
                if (systemNotice != null) {
                    if (systemNotice.getNoticeStatus() == NoticeConstant.NOTICE_DELETE) {
                        // 通知被删除
                        response.setCode(NoticeCode.NOTICE_ISDELETE);
                    } else {
                        // 查询成功 设置已读
                        systemNotice.setNoticeStatus(NoticeConstant.NOTICE_READ);
                        replyNoticeService.updateSystemNotice(systemNotice);
                        SystemNoticeInfo systemNoticeInfo = new SystemNoticeInfo();
                        systemNoticeInfo.setNoticeContent(systemNotice.getNoticeContent());
                        systemNoticeInfo.setCreateTime(systemNotice.getCreateTime());
                        response.setData(systemNoticeInfo);
                    }
                }
            } catch (Exception e) {
                LOG.error("findSystemNotice error!", e);
                response.setCode(FAILURE);
            }
        } else {
            // 通知ID为空
            response.setCode(NoticeCode.NOTICE_ID_ISNULL);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/findSystemNotice end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询系统通知列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 9:21
     *
     * @param page
     */
    @RequestMapping(value = "/v1/findAllSystemNotice", method = RequestMethod.GET)
    public Response findAllSystemNotice(HttpServletRequest request, Page page) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/findAllSystemNotice start ========page=" + page);
        }
        Response response = new Response();
        List<SystemNoticeInfo> allSystemNotice;
        List<SystemNoticeInfo> al;
        String userId = request.getHeader("userId");
        try {
            userId = AESUtil.decrypt(userId); //解密
        } catch (Exception e) {
            LOG.error("findAllTopicReplyNotice error!", e);
            response.setCode(FAILURE);
        }
        Paging<SystemNoticeInfo> pager = new Paging<SystemNoticeInfo>(page.getCurPage(), page.getPageSize());
        try {
            allSystemNotice = replyNoticeService.findAllSystemNotice(pager, userId);
            // 根据通知类型设置通知标题
            al = setTitle(allSystemNotice);
            pager.result(al);
            response.setData(pager);
        } catch (Exception e) {
            LOG.error("findAllSystemNotice error!", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== app/notice/v1/findAllSystemNotice end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 根据通知类型设置通知标题
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/7 14:09
     */
    private List<SystemNoticeInfo> setTitle(List<SystemNoticeInfo> al) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (SystemNoticeInfo systemNoticeInfo : al) {
            String noticeTitle;
            switch (systemNoticeInfo.getNoticeType()) {
                case 1:    //新增报修单
                    noticeTitle = "新增报修单";
                    break;
                case 2:    //取消报修单
                    noticeTitle = "取消报修单";
                    break;
                case 3:    //报修单催单
                    noticeTitle = "报修单催单";
                    break;
                case 4:    //报修单指派
                    noticeTitle = "报修单指派";
                    break;
                case 5:    //任务指派
                    noticeTitle = "任务指派";
                    break;
                case 6:    //拒绝任务
                    noticeTitle = "拒绝任务";
                    break;
                case 7:    //接受任务
                    noticeTitle = "接受任务";
                    break;
                case 8:    //完成任务
                    noticeTitle = "完成任务";
                    break;
                case 9:    //删除任务
                    noticeTitle = "删除任务";
                    break;
                case 10:    //添加工作日志
                    noticeTitle = "添加工作日志";
                    break;
                case 11:    //提交装修申请
                    noticeTitle = "提交装修申请";
                    break;
                case 12:    //装修申请被取消
                    noticeTitle = "装修申请被取消";
                    break;
                default:
                    noticeTitle = "系统通知";
                    break;
            }
            systemNoticeInfo.setNoticeTitle(noticeTitle);
            // 时间去除时分秒
            String format = sdf.format(systemNoticeInfo.getCreateTime());
            String[] split = format.split(" ");
            systemNoticeInfo.setCreateDate(split[0]);
        }
        return al;
    }

}

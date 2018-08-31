package com.betel.estatemgmt.business.userapp.notice.controller;

import com.betel.estatemgmt.business.propertyapp.notice.code.NoticeCode;
import com.betel.estatemgmt.business.propertyapp.notice.constant.NoticeConstant;
import com.betel.estatemgmt.business.propertyapp.notice.controller.ReplyNoticeController;
import com.betel.estatemgmt.business.propertyapp.notice.model.NoticeId;
import com.betel.estatemgmt.business.propertyapp.notice.model.SystemNoticeInfo;
import com.betel.estatemgmt.business.propertyapp.notice.model.UnreadNumber;
import com.betel.estatemgmt.business.propertyapp.notice.service.ReplyNoticeService;
import com.betel.estatemgmt.common.Page;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UserNoticeController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/3 8:55 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/notice")
public class UserNoticeController {

    private static final Logger LOG = LoggerFactory.getLogger(UserNoticeController.class);

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
                case 1:    //系统删帖
                    noticeTitle = "系统删帖";
                    break;
                case 2:    //系统删除房屋
                    noticeTitle = "系统删除房屋";
                    break;
                case 3:    //户主认证结果
                    noticeTitle = "户主认证结果";
                    break;
                case 4:    //成员认证结果
                    noticeTitle = "成员认证结果";
                    break;
                case 5:    //移除房屋成员
                    noticeTitle = "户主移除房屋成员";
                    break;
                case 6:    //移除房屋成员
                    noticeTitle = "移除房屋成员";
                    break;
                case 7:    //系统驳回帖子
                    noticeTitle = "系统驳回帖子";
                    break;
                case 8:    //系统驳回楼层
                    noticeTitle = "系统驳回楼层";
                    break;
                case 9:    //系统驳回回复
                    noticeTitle = "系统驳回回复";
                    break;
                case 10:    //系统驳回活动
                    noticeTitle = "系统驳回活动";
                    break;
                case 11:    //系统驳回交易物品
                    noticeTitle = "系统驳回交易物品";
                    break;
                case 12:    //系统驳回交易留言
                    noticeTitle = "系统驳回交易留言";
                    break;
                case 13:    //系统删活动
                case 14:    //系统删活动
                    noticeTitle = "系统删活动";
                    break;
                case 15:    //系统禁言
                    noticeTitle = "系统禁言";
                    break;
                case 16:    //参加活动取消
                    noticeTitle = "参加活动取消";
                    break;
                case 17:    //活动取消
                    noticeTitle = "活动取消";
                    break;
                case 18:    //交易被删除
                    noticeTitle = "交易被删除";
                    break;
                case 19:    //取消禁言
                    noticeTitle = "取消禁言";
                    break;
                case 20:    //取消禁止登录
                    noticeTitle = "取消禁止登录";
                    break;
                case 21:    //维修确认
                    noticeTitle = "维修确认";
                    break;
                case 22:    //缴费提醒
                    noticeTitle = "缴费提醒";
                    break;
                case 23:    //催缴提醒
                    noticeTitle = "催缴提醒";
                    break;
                case 24:    //缴费通知
                    noticeTitle = "缴费通知";
                    break;
                case 25:    //缴费成功通知
                    noticeTitle = "缴费成功通知";
                    break;
                case 26:    //成员添加成功
                    noticeTitle = "成员添加成功";
                    break;
                case 27:    //成员添加成功
                    noticeTitle = "成员添加成功";
                    break;
                case 28:    //户主变更
                    noticeTitle = "户主变更";
                    break;
                case 29:    //成员关系解除
                    noticeTitle = "成员关系解除";
                    break;
                case 30:    //成员注册
                    noticeTitle = "成员注册";
                    break;
                case 31:    //装修申请已通过
                    noticeTitle = "装修申请已通过";
                    break;
                case 32:    //装修申请未通过
                    noticeTitle = "装修申请未通过";
                    break;
                case 33:    //装修申请被取消
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

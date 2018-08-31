package com.betel.estatemgmt.business.propertyapp.notice.service;

import com.betel.estatemgmt.business.propertyapp.notice.model.NoticeReq;
import com.betel.estatemgmt.business.propertyapp.notice.model.SystemNoticeInfo;
import com.betel.estatemgmt.business.propertyapp.notice.model.UnreadNumber;
import com.betel.estatemgmt.business.smartre.notice.model.NoticeInfoReq;
import com.betel.estatemgmt.business.smartre.notice.model.NoticeResp;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 帖子回复通知列表查询的类
 * </p>
 * ClassName: ReplyNoticeService <br/>
 * Author: geyf  <br/>
 * Date: 2017/5/16 15:40 <br/>
 * Version: 1.0
 */
public interface ReplyNoticeService {

    /**
     * <p>
     * 查询所有通知的未读数量
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 10:26
     *
     * @param userId 用户ID
     * @return UnreadNumber 未读数量
     */
    UnreadNumber findUnreadNumber(String userId, HttpServletRequest request) throws Exception;

    /**
     * <p>
     * 修改系统通知状态
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 9:54
     */
    void updateSystemNotice(Notice notice);

    /**
     * <p>
     * 查询系统通知详情
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 9:46
     *
     * @param noticeId
     * @return Notice
     */
    Notice findSystemNotice(Long noticeId);

    /**
     * <p>
     * 查询系统通知列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/8/2 9:27
     *
     * @param userId 用户ID
     * @return List<SystemNoticeInfo>
     */
    List<SystemNoticeInfo> findAllSystemNotice(Paging<SystemNoticeInfo> pager, String userId);

    /**
     * 插入系统通知表
     * Author: jians.z <br/>
     *
     * @param noticeReq
     * @return
     */
    String push(NoticeReq noticeReq);
    /**
     * 系统通知表批量插入
     */
    List<Notice> insertNoticeList(List<Notice> noticeList);
    /**
     * 系统通知表批量插入
     */
    List<NoticeResp> insertList(NoticeInfoReq noticeInfoReq) throws ParseException;
}

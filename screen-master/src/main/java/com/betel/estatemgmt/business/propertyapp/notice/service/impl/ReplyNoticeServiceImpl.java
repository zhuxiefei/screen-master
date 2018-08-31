package com.betel.estatemgmt.business.propertyapp.notice.service.impl;

import com.betel.estatemgmt.business.propertyapp.notice.constant.NoticeConstant;
import com.betel.estatemgmt.business.propertyapp.notice.model.NoticeReq;
import com.betel.estatemgmt.business.propertyapp.notice.model.SystemNoticeInfo;
import com.betel.estatemgmt.business.propertyapp.notice.model.UnreadNumber;
import com.betel.estatemgmt.business.propertyapp.notice.service.ReplyNoticeService;
import com.betel.estatemgmt.business.smartre.notice.model.NoticeInfoReq;
import com.betel.estatemgmt.business.smartre.notice.model.NoticeResp;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.house.HouseAuthNoticeMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 查看帖子回复通知列表
 * </p>
 * ClassName: ReplyNoticeServiceImpl <br/>
 * Author: geyf  <br/>
 * Date: 2017/5/16 15:50 <br/>
 * Version: 1.0
 */
@Service("ReplyNoticeService")
@Transactional
public class ReplyNoticeServiceImpl implements ReplyNoticeService {

    private static final Logger LOG = LoggerFactory.getLogger(ReplyNoticeServiceImpl.class);

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private HouseAuthNoticeMapper houseAuthNoticeMapper;

    @Override
    public UnreadNumber findUnreadNumber(String userId, HttpServletRequest request) throws Exception {
        UnreadNumber unreadNumber = new UnreadNumber();
        //查询回复通知的未读数量
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", AESUtil.encrypt(userId));
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL) + "httpclientSocial/notice/v1/findUnreadNumber", jsonParam, request);
        if (null != response) {
            if (null != response.getData()) {
                JSONObject jsonObject = JSONObject.fromObject(response.getData());
                String reply=jsonObject.get("replyNumber").toString();
                int i=reply.indexOf(".");
                String replyNumber=reply.substring(0,i);
                unreadNumber.setReplyNumber(replyNumber);
            }
        }
        // 查询系统通知的未读数量
        int systemNoticeNumber = noticeMapper.findSystemNoticeNumber(userId);
        unreadNumber.setSystemNumber(String.valueOf(systemNoticeNumber));
        // 查询认证通知的未读数量
        int authNoticeNumber = houseAuthNoticeMapper.findAuthNoticeNumber(userId);
        unreadNumber.setAuthNumber(authNoticeNumber);
        return unreadNumber;
    }


    @Override
    public void updateSystemNotice(Notice notice) {
        noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public Notice findSystemNotice(Long noticeId) {
        Notice notice = noticeMapper.selectByPrimaryKey(noticeId);
        return notice;
    }

    @Override
    public List<SystemNoticeInfo> findAllSystemNotice(Paging<SystemNoticeInfo> pager, String userId) {
        List<SystemNoticeInfo> allSystemNotices = noticeMapper.findAllSystemNotices(pager.getRowBounds(), userId);
        return allSystemNotices;
    }

    @Override
    public String push(NoticeReq noticeReq) {
        List<Notice> noticeList = new ArrayList<>();
        if (!StringUtil.isBlank(noticeReq.getUserId())) {
            String[] userId = Tool.getIdArrOfStringType(noticeReq.getUserId());
            for (int i = 0; i < userId.length; i++) {
                Notice notice = new Notice();
                notice.setNoticeUserId(userId[i]);
                notice.setNoticeContent(noticeReq.getContent());
                notice.setNoticeStatus(Quantity.ONE);
                if (!StringUtil.isBlank(noticeReq.getSendNo())) {
                    notice.setNoticeType(Integer.valueOf(noticeReq.getSendNo()));
                }
                notice.setCreateTime(new Date());
                noticeList.add(notice);
            }
        }
        if (noticeList.size() > Quantity.ZERO) {
            noticeMapper.insertNotices(noticeList);
        }
        if (!StringUtil.isBlank(noticeReq.getPhone())) {
            String[] phone = Tool.getIdArrOfStringType(noticeReq.getPhone());
            for (int i = 0; i < phone.length; i++) {
                //建立推送模型
                SendMessage sendMessage = new SendMessage();
                sendMessage.setSendType(NoticeConstant.SENDTYPE);
                sendMessage.setSendNo(noticeReq.getSendNo());
                sendMessage.setSendTitle(noticeReq.getTitle());
                if (StringUtil.isBlank(noticeReq.getContentSend())) {
                    sendMessage.setSendContent(noticeReq.getContent());
                } else {
                    sendMessage.setSendContent(noticeReq.getContentSend());
                }
                sendMessage.setSendId(noticeList.get(i).getNoticeId().toString());
                //转json
                String pushInfo = GsonUtil.object2Gson(sendMessage);
                List<String> phoneNumList = new ArrayList<>();
                for (int j = 0; j < phone.length; j++) {
                    //获得成员手机号
                    if (null != phone[i]) {
                        phoneNumList.add(phone[i]);
                    }
                }
                //再推送消息给用户
                if (phoneNumList.size() > 0) {
                    MsgPushUtils.pushList(phoneNumList, pushInfo);
                }
            }
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public List<Notice> insertNoticeList(List<Notice> noticeList) {
        return null;
    }

    @Override
    public List<NoticeResp> insertList(NoticeInfoReq noticeInfoReq) throws ParseException {
        List<NoticeResp> curNotice = new ArrayList<>();
        List<String> curUserId = new ArrayList<>();
        List<String> curContent = new ArrayList<>();
        List<String> curNoticeType = new ArrayList<>();
        if (!StringUtil.isBlank(noticeInfoReq.getNoticeUserId())) {
            String[] userId = Tool.getIdArrOfStringType(noticeInfoReq.getNoticeUserId());
            for (int i = 0; i < userId.length; i++) {
                curUserId.add(userId[i]);
            }
        }
        if (!StringUtil.isBlank(noticeInfoReq.getNoticeContent())) {
            String[] content = Tool.getIdArrOfStringType(noticeInfoReq.getNoticeContent());
            for (int i = 0; i < content.length; i++) {
                curContent.add(content[i]);
            }
        }
        if (!StringUtil.isBlank(noticeInfoReq.getNoticeType())) {
            String[] noticeType = Tool.getIdArrOfStringType(noticeInfoReq.getNoticeType());
            for (int i = 0; i < noticeType.length; i++) {
                curNoticeType.add(noticeType[i]);
            }
        }
        //create Notice
        List<Notice> noticeList = new ArrayList<>();
        for (int j = 0; j < curUserId.size(); j++) {
            Notice notice = new Notice();
            notice.setNoticeUserId(curUserId.get(j));
            notice.setNoticeContent(curContent.get(j));
            notice.setNoticeStatus(Integer.valueOf(Quantity.ONE));
            notice.setNoticeType(Integer.valueOf(curNoticeType.get(j)));
            notice.setCreateTime(new Date());
            noticeList.add(notice);
        }
        noticeMapper.insertNotices(noticeList);
        for (int j = 0; j < noticeList.size(); j++) {
            NoticeResp notice = new NoticeResp();
            notice.setNoticeId(noticeList.get(j).getNoticeId().toString());
            curNotice.add(notice);
        }
        return curNotice;
    }
}

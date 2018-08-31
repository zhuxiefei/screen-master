package com.betel.estatemgmt.business.userapp.house.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.HouseInfoApp;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.business.userapp.house.service.AppMemberService;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.house.HouseMember;
import com.betel.estatemgmt.common.model.house.HouseMemberRela;
import com.betel.estatemgmt.common.model.house.MemberAuth;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AppMemberImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/22 15:47 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AppMemberImpl implements AppMemberService {
    private static final Logger LOG = LoggerFactory.getLogger(AppMemberImpl.class);
    @Autowired
    HouseMemberMapper houseMemberMapper;

    @Autowired
    HouseOwnerMapper houseOwnerMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    HouseMemberRelaMapper houseMemberRelaMapper;

    @Autowired
    MemberAuthMapper memberAuthMapper;
    @Autowired
    UserService userService;

    @Override
    public List<Member> findAllMemberByHouseId(String houseId, HttpServletRequest request) throws Exception {
        //查询成员
        List<Member> memberMember = houseMemberMapper.findMemberByHouseId(houseId);
        //查询户主
        Member ownerMember = houseOwnerMapper.findMemberByHouseId(houseId);
        UserAccount userAccount=userService.findByUserId(ownerMember.getUserid(),request);
        if (userAccount!=null){
            ownerMember.setUserAccount(userAccount.getAcctName());
        }
        memberMember.add(ownerMember);
        return memberMember;
    }

    @Autowired
    HttpServletRequest request;

    @Override
    public String deleteMemberBymemberId(String memberId) throws Exception {
        String ownerId = houseMemberMapper.findHouseOwnerByMemberId(memberId);
        String userId = request.getHeader("userId");
        userId = AESUtil.decrypt(userId);
        if (!userId.equals(ownerId)) {
            return HouseCode.NOTOWNER;
        } else {
            // 入通知表
            Notice notice = new Notice();
            //成员房屋关系表
            HouseMemberRela houseMemberRela = houseMemberRelaMapper.findByKey(memberId);
            //成员信息
            HouseMember houseMember = houseMemberMapper.selectByPrimaryKey(memberId);
            HouseInfoApp appHouseInfo = houseMapper.findHouseInfo(houseMemberRela.getHouseId());
            if (appHouseInfo != null && houseMember != null) {
                String centent = "尊敬的用户，你被户主从" + appHouseInfo.getHouseName() + "房间中移除！";
                notice.setNoticeUserId(houseMember.getUserId());
                notice.setNoticeContent(centent);
                notice.setNoticeStatus(HouseConstant.NOTICETYPE);
                notice.setNoticeType(Integer.parseInt(HouseConstant.MEMBERSEND_NO));
                notice.setCreateTime(new Date());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("-------notice----------" + notice);
                }
                noticeMapper.insertSelective(notice);
                // 通知申请人
                SendMessage sendMessage = new SendMessage();
                sendMessage.setSendType(HouseConstant.MEMBER_SEND_TYPE);
                sendMessage.setSendNo(HouseConstant.MEMBERSEND_NO);
                sendMessage.setSendContent(centent);
                sendMessage.setSendId(notice.getNoticeId().toString());
                sendMessage.setSendTitle(HouseConstant.MEMBER_SEND_TITLE);
                String send = GsonUtil.object2Gson(sendMessage);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("-------sendMessage----------" + sendMessage);
                }
                // 通过用户号码,发送通知
                UserAccount userAccount =userService.findByUserId(houseMember.getUserId(),request);
                if (userAccount != null) {
                    MsgPushUtils.push(userAccount.getAcctName(), send);
                }
                //删除认证记录
                MemberAuth auth = memberAuthMapper.findMemberAuth(houseMemberRela.getHouseId(), houseMember.getPhoneNum());
                if (auth != null) {
                    MemberAuth auth1 = new MemberAuth();
                    auth1.setAuthId(auth.getAuthId());
                    auth1.setAuthStatus(HouseConstant.AUTHSTATUSDELETE);
                    memberAuthMapper.updateByPrimaryKeySelective(auth1);
                }
                //删除成员
                HouseMember member = new HouseMember();
                member.setMemberId(memberId);
                member.setMemberStatus(HouseConstant.MEMBER_DELETE_STATUS);
                member.setUpdateTime(new Date(System.currentTimeMillis()));
                houseMemberMapper.updateByPrimaryKeySelective(member);
            }
        }
        return StatusCode.SUCCESS;
    }

}

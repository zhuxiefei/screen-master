package com.betel.estatemgmt.business.userapp.auth.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.auth.constant.AuthConstant;
import com.betel.estatemgmt.business.userapp.auth.model.AppMemberAuth;
import com.betel.estatemgmt.business.userapp.auth.model.AuthNoticeParam;
import com.betel.estatemgmt.business.userapp.auth.model.DealWithAuthReq;
import com.betel.estatemgmt.business.userapp.auth.model.MemberAuthInfo;
import com.betel.estatemgmt.business.userapp.auth.service.MemberAuthService;
import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.HouseInfoApp;
import com.betel.estatemgmt.business.web.auth.constant.HouseAuthConstant;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
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
 * 文件说明
 * </p>
 * ClassName: MemberAuthServiceImpl <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/23 9:37 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class MemberAuthServiceImpl implements MemberAuthService {

    private static  final Logger LOG = LoggerFactory.getLogger(MemberAuthServiceImpl.class);

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseAuthNoticeMapper houseAuthNoticeMapper;

    @Autowired
    private MemberAuthMapper memberAuthMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseMemberMapper houseMemberMapper;

    @Autowired
    HouseOwnerRelaMapper houseOwnerRelaMapper;

    @Autowired
    HouseOwnerMapper houseOwnerMapper;

    @Autowired
    HouseMemberRelaMapper houseMemberRelaMapper;

    @Override
    public String dealWithMemberAuth(DealWithAuthReq dealWithAuthReq, String userId, HttpServletRequest request) throws Exception {
        if(LOG.isDebugEnabled()){
            LOG.debug("========MemberAuthServiceImpl dealWithMemberAuth start========dealWithAuthReq=" + dealWithAuthReq +
            "userId=" +userId);
        }
        // 查询认证信息
        MemberAuth houseAuth = memberAuthMapper.selectByPrimaryKey(dealWithAuthReq.getAuthId());
        // 判断认证状态
        if(houseAuth.getReviewStatus() == AuthConstant.AUTHAGREE || houseAuth.getReviewStatus() == AuthConstant.AUTHREFUSE){
            if(LOG.isDebugEnabled()){
                LOG.debug("========MemberAuthServiceImpl dealWithMemberAuth fail========code=" + HouseCode.AUTHEND);
            }
            // 已审核
            return HouseCode.AUTHEND;
        }
        if(houseAuth.getAuthStatus() == AuthConstant.AUTHREVOKE){
            if(LOG.isDebugEnabled()){
                LOG.debug("========MemberAuthServiceImpl dealWithMemberAuth fail========code=" + HouseCode.AUTHREVOKE);
            }
            // 申请已撤销
            return HouseCode.AUTHREVOKE;
        }
        if(houseAuth.getAuthStatus() == AuthConstant.AUTHDELETE){
            if(LOG.isDebugEnabled()){
                LOG.debug("========MemberAuthServiceImpl dealWithMemberAuth fail========code=" + HouseCode.AUTHDELETE);
            }
            // 申请已删除
            return HouseCode.AUTHDELETE;
        }
        // 通过房屋ID 判断户主是否存在
        HouseOwner houseOwner = houseOwnerMapper.findByHouseId(houseAuth.getHouseId());
        if (houseOwner == null) {
            return HouseCode.NOOWNER;
        }
        if(!houseOwner.getUserId().equals(userId)){
            if(LOG.isDebugEnabled()){
                LOG.debug("========MemberAuthServiceImpl dealWithMemberAuth fail========code=" + HouseCode.OUTOFAUTH);
            }
            // 当前用户不是该房户主 即房屋户主已变更
            return HouseCode.OUTOFAUTH;
        }
        // 可以正常认证
        MemberAuth ha = new MemberAuth();
        ha.setAuthId(houseAuth.getAuthId());
        ha.setOperatorId(userId);
        ha.setReviewTime(new Date());
        ha.setAuthStatus(1);
        if(AuthConstant.ONE.equals(dealWithAuthReq.getAuthStatus())){
            ha.setReviewStatus(2);
        }else{
            ha.setReviewStatus(3);
        }
        HouseInfoApp houseInfoApp = houseMapper.findHouseInfo(houseAuth.getHouseId().toString());
        HouseMember houseMember = houseMemberMapper.findByHouseIdAndPhone(houseAuth.getPhoneNum(),houseAuth.getHouseId());
        StringBuffer sb = new StringBuffer();
        String title = "系统通知";
        sb.append("尊敬的" + houseAuth.getApplicantName() + "用户,");
        if(dealWithAuthReq.getAuthStatus().equals(AuthConstant.AUTHINFOAGREE)){
            sb.append("恭喜您已成功认证为" + houseInfoApp.getHouseName() + "的成员！");
            // 成员认证成功， 修改房屋成员信息表
            if (houseMember!=null){
                HouseMember member = new HouseMember();
                member.setMemberId(houseMember.getMemberId());
                member.setMemberStatus(HouseConstant.TWO);
                member.setUpdateTime(new Date(System.currentTimeMillis()));
                houseMemberMapper.updateByPrimaryKeySelective(member);
            }
        }
        if(dealWithAuthReq.getAuthStatus().equals(AuthConstant.AUTHINFOREFUSE)){
            sb.append("您认证" + houseInfoApp.getHouseName() + "的成员失败！");
            // 成员认证成功， 修改房屋成员信息表
            if (houseMember!=null){
                HouseMember member = new HouseMember();
                member.setMemberId(houseMember.getMemberId());
                member.setMemberStatus(HouseConstant.AUTHSTATUSFAILED);
                member.setUpdateTime(new Date(System.currentTimeMillis()));
                houseMemberMapper.updateByPrimaryKeySelective(member);
            }
        }
        // 修改认证状态 审核人 审核时间
        if(LOG.isDebugEnabled()){
            LOG.debug("========MemberAuthServiceImpl insertHouseAuth ========ha=" + ha);
        }
        memberAuthMapper.updateByPrimaryKeySelective(ha);
        // 入通知表
        Notice notice = new Notice();
        notice.setNoticeUserId(houseAuth.getApplicantId());
        notice.setNoticeContent(sb.toString());
        notice.setNoticeStatus(HouseAuthConstant.NOTICE_STATUS_UNREAD);
        notice.setNoticeType(4);
        notice.setCreateTime(new Date());
        if(LOG.isDebugEnabled()){
            LOG.debug("========MemberAuthServiceImpl insertNotice ========notice=" + notice);
        }
        noticeMapper.insertSelective(notice);
        // 通知申请人
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSendType(AuthConstant.SEND_TYPE_AUTH);
        sendMessage.setSendNo(AuthConstant.SEND_NO_MEMBER);
        sendMessage.setSendContent(notice.getNoticeContent());
        sendMessage.setSendId(notice.getNoticeId().toString());
        sendMessage.setSendTitle(title);
        String send = GsonUtil.object2Gson(sendMessage);
        // 通过用户ID，查询用户手机号
        UserAccount userAccount = userService.findByUserId(houseAuth.getApplicantId(),request);
        if(LOG.isDebugEnabled()){
            LOG.debug("========MemberAuthServiceImpl MsgPush ========acctName=" + userAccount.getAcctName()
            + "send" + send);
        }
        com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(userAccount.getAcctName(), send);
        if(LOG.isDebugEnabled()){
            LOG.debug("========MemberAuthServiceImpl end ========code=" + StatusCode.SUCCESS);
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public MemberAuthInfo findAuthInfo(Long noticeId) {
        HouseAuthNotice houseAuthNotice1 = houseAuthNoticeMapper.selectByPrimaryKey(noticeId);
        if(houseAuthNotice1!=null){
            MemberAuthInfo memberAuthInfo = memberAuthMapper.findMemberAuthInfo(houseAuthNotice1.getAuthId());
            // 设置通知为已读
            HouseAuthNotice houseNotice = new HouseAuthNotice();
            houseNotice.setNoticeId(noticeId);
            houseNotice.setNoticeStatus(AuthConstant.NOTICEISREAD);
            houseAuthNoticeMapper.updateByPrimaryKeySelective(houseNotice);
            return memberAuthInfo;
        }
        return null;
    }

    @Override
    public List<House> findHouseOwnerByuserId(String userId) {
        List<House> houses = houseMapper.selectHouseOwnerByuserId(userId);
        return houses;
    }

    @Override
    public List<AppMemberAuth> findAllAuthNotices(Paging<AppMemberAuth> pager, AuthNoticeParam authNoticeParam) {
        List<AppMemberAuth> allAuthNotice =
                houseAuthNoticeMapper
                        .findAllAuthNotice(pager.getRowBounds(),authNoticeParam.getNoticeUserId(),
                                authNoticeParam.getHouses());
        return allAuthNotice;
    }
}

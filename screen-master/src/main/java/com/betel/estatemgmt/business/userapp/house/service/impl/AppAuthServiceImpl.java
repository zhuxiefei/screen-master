package com.betel.estatemgmt.business.userapp.house.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.*;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: AppAuthServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/21 9:29 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AppAuthServiceImpl implements AppAuthService {
    private static final Logger LOG = LoggerFactory.getLogger(AppAuthServiceImpl.class);
    @Autowired
    MemberAuthMapper memberAuthMapper;

    @Autowired
    HouseOwnerMapper houseOwnerMapper;

    @Autowired
    HouseOwnerRelaMapper houseOwnerRelaMapper;

    @Autowired
    HouseMemberRelaMapper houseMemberRelaMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    HouseMemberMapper houseMemberMapper;

    @Autowired
    HouseAuthNoticeMapper houseAuthNoticeMapper;

    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    UserService userService;

    @Override
    public List<AuthHouse> findHouseAuth(String userId, int type) {
        //查询用户是户主的房屋
        List<AuthHouse> ownerHouse = houseOwnerRelaMapper.findAuthHouse(userId);
        //查询用户是成员的房屋
        List<AuthHouse> memberHouse = houseMemberRelaMapper.findAuthHouse(userId);
        List<AuthHouse> list = new ArrayList<>();
//        if (type == HouseConstant.ONE) {
//            //查询用户申请成员认证的记录
//            List<AuthHouse>  memberAuth = memberAuthMapper.findAuthMember(userId);
//            list.addAll(memberAuth);
//        }
        list.addAll(ownerHouse);
        list.addAll(memberHouse);
        //返回查询结果
        return list;
    }

    @Override
    public String addHouseAuth(MemberAuth houseAuth, String userId, HttpServletRequest request) throws Exception {
        if (houseAuth.getHouseId() == null) {
            return HouseCode.HOUSEIDNULL;
        }
        HouseOwner houseOwner = houseOwnerMapper.findByHouseId(houseAuth.getHouseId());
        if (houseOwner == null) {
            return HouseCode.NOOWNER;
        } else if (houseOwner.getUserId().equals(userId)) {
            return HouseCode.ISWONER;
        } else {
            String code = validateMemberAuth(houseAuth);
            if (code.equals(StatusCode.SUCCESS)) {
                //判断该房间是否有户主
                houseAuth.setAuthTime(new Date());
                houseAuth.setApplicantId(AESUtil.decrypt(request.getHeader("userId")));
                houseAuth.setReviewStatus(HouseConstant.ONE);
                houseAuth.setAuthStatus(HouseConstant.ONE);
                houseAuth.setAuthTime(new Date(System.currentTimeMillis()));
                memberAuthMapper.insertSelective(houseAuth);
                //返回主键
                Long authId = houseAuth.getAuthId();
                //发送成员认证
                HouseAuthNotice notice = new HouseAuthNotice();
                notice.setAuthId(authId);
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeContent("尊敬的用户，你有一个成员认证请求，请查看!");
                notice.setNoticeStatus(HouseConstant.ONE);
                notice.setNoticeUserId(houseOwner.getUserId());
                houseAuthNoticeMapper.insertSelective(notice);
                //建立推送模型
                SendMessage sendMessage = new SendMessage();
                sendMessage.setSendType(HouseConstant.SEND_TYPE);
                sendMessage.setSendNo(HouseConstant.SEND_NO);
                sendMessage.setSendTitle("成员认证");
                sendMessage.setSendContent("尊敬的用户，你有一个成员认证请求，请查看!");
                sendMessage.setSendId(notice.getNoticeId().toString());
                //转json
                String pushInfo = GsonUtil.object2Gson(sendMessage);
                //再推送消息给用户
                UserAccount userAccount = userService.findByUserId(houseOwner.getUserId(), request);
                if (null != userAccount) {
                    //再推送消息给用户
                    MsgPushUtils.push(userAccount.getAcctName(), pushInfo);
                }
                //添加到house_member和house_member_rela
                HouseMember houseMember = new HouseMember();
                houseMember.setCreateTime(new Date(System.currentTimeMillis()));
                houseMember.setRealName(houseAuth.getApplicantName());
                houseMember.setPhoneNum(houseAuth.getPhoneNum());
                houseMember.setMemberStatus(HouseConstant.ONE);
                houseMember.setUpdateTime(new Date(System.currentTimeMillis()));
                houseMember.setUserId(AESUtil.decrypt(request.getHeader("userId")));
                houseMemberMapper.insertSelective(houseMember);
                //返回主键memberId
                String memberId = houseMember.getMemberId();
                HouseMemberRela memberRela = new HouseMemberRela();
                memberRela.setCreateTime(new Date(System.currentTimeMillis()));
                memberRela.setMemberId(memberId);
                memberRela.setHouseId(houseAuth.getHouseId().toString());
                houseMemberRelaMapper.insertSelective(memberRela);
            } else {
                return code;
            }
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public MemberAuth findAuthById(Long authId) {
        return memberAuthMapper.selectByPrimaryKey(authId);
    }

    @Autowired
    HttpServletRequest request;

    @Override
    public String updateAuth(MemberAuth houseAuth) throws Exception {
        MemberAuth updateAuth = new MemberAuth();
        if (houseAuth.getAuthId() == null) {
            return HouseCode.AUTHIDNULL;
        }
        MemberAuth memberAuth = memberAuthMapper.selectByPrimaryKey(houseAuth.getAuthId());
        if (memberAuth != null && houseAuth.getAuthStatus() != null) {
            //判断是否已认证
            if (memberAuth.getReviewStatus() != null && memberAuth.getReviewStatus().equals(HouseConstant.AUTHSTATUSSUCCUSS)){
                return HouseCode.MEMBER_AUTH_SUCCESS;
            }
            //修改状态 4：撤销 5：删除
            updateAuth.setAuthId(houseAuth.getAuthId());
            updateAuth.setAuthStatus(houseAuth.getAuthStatus());
            memberAuthMapper.updateByPrimaryKeySelective(updateAuth);
            //删除成员信息表和关系表
            HouseMember member = houseMemberMapper.findByHouseIdAndPhone(memberAuth.getPhoneNum(), memberAuth.getHouseId());
            if (member != null) {
                HouseMember houseMember = new HouseMember();
                houseMember.setMemberId(member.getMemberId());
                houseMember.setMemberStatus(HouseConstant.MEMBER_DELETE_STATUS);
                houseMemberMapper.updateByPrimaryKeySelective(houseMember);
            }
            HouseAuthNotice notice = new HouseAuthNotice();
            notice.setAuthId(houseAuth.getAuthId());
            notice.setNoticeStatus(HouseConstant.AUTHSTATUSFAILED);
            houseAuthNoticeMapper.updateByPrimaryKeySelective(notice);
        }
        return StatusCode.SUCCESS;
    }

    /**
     * <p>
     * 验证认证是否正确
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 17:11
     */

    private String validateMemberAuth(MemberAuth memberAuth) {
        if (StringUtil.isBlank(memberAuth.getPhoneNum())) {
            return HouseCode.PHONENUM_NULL;
        } else if (!Validate.isMobile(memberAuth.getPhoneNum())) {
            return HouseCode.PHONENUM_FORMAT;
        } else if (StringUtil.isBlank(memberAuth.getApplicantName())) {
            return HouseCode.USERNAMENULL;
        } else if (!Validate.isPeopleName(memberAuth.getApplicantName())) {
            return HouseCode.USERNAMEFORMATWRONG;
        }
        //判断该用户是否已经申请过该房屋或是否已经是该房屋成员
        MemberAuth testAuth = memberAuthMapper.findMemberAuth(memberAuth.getHouseId().toString(), memberAuth.getPhoneNum().trim());
        if (testAuth != null) {
            if (testAuth.getReviewStatus() == 1) {
                return HouseCode.REAPPLY;
            } else if (testAuth.getReviewStatus() == 2) {
                return HouseCode.ISMENMBER;
            }
        }
        //去空格处理
        memberAuth.setApplicantName(memberAuth.getApplicantName());
        memberAuth.setPhoneNum(memberAuth.getPhoneNum());
        return StatusCode.SUCCESS;
    }

    @Override
    public House findHouseByHouseId(String houseId) {
        return houseMapper.selectByPrimaryKey(houseId);
    }


    @Override
    public void addnotice(HouseAuthNotice houseAuthnotice) {
        houseAuthNoticeMapper.insertSelective(houseAuthnotice);
    }

    @Override
    public HouseOwner findBySmsReq(GetSmsReq smsReq, Integer status) {
        return houseOwnerMapper.selectBySmsReq(smsReq.getOwnerPhone(), smsReq.getHouseId(), smsReq.getOwnerName(), status);
    }

    @Override
    public void activateOwner(HouseOwner houseOwner, GetSmsReq smsReq, boolean flag, HttpServletRequest request) throws Exception {
        if (flag) {
            //向旧户主发通知
            HouseOwner owner1 = houseOwnerMapper.selectBySmsReq(smsReq.getOwnerPhone()
                    , smsReq.getHouseId(), smsReq.getOwnerName(), HouseConstant.TWO);
            String houseInfo = houseOwnerRelaMapper.findByOwnerId(owner1.getOwnerId());
            //httpClient
            UserAccount account = userService.findByUserId(houseOwner.getUserId(), request);
            //创建系统通知对象，将通知存到数据库
            Notice notice = new Notice();
            notice.setNoticeStatus(1);
            notice.setNoticeType(HouseConstant.HOUSE_OWNER_CHANAGE);
            notice.setCreateTime(new Date(System.currentTimeMillis()));
            notice.setNoticeUserId(owner1.getUserId());
            notice.setNoticeContent("您的" + houseInfo + "户主信息已经变更，如有疑问，请及时联系物业");
            if (LOG.isDebugEnabled()) {
                LOG.debug("========before addNotice========notice=" + notice);
            }
            noticeMapper.insertSelective(notice);
            //返回主键
            Long noticeId = notice.getNoticeId();
            if (LOG.isDebugEnabled()) {
                LOG.debug("========after addNotice========notice=" + notice);
            }
            SendMessage send = new SendMessage();
            send.setSendId(noticeId.toString());
            send.setSendTitle("户主变更");
            send.setSendNo(HouseConstant.HOUSE_OWNER_CHANAGE.toString());
            send.setSendType(HouseConstant.MEMBER_SEND_TYPE);
            send.setSendContent("您的" + houseInfo + "户主信息已经变更，如有疑问，请及时联系物业");
            if (LOG.isDebugEnabled()) {
                LOG.debug("========SendMessage========" + send);
            }
            //转json
            String pushInfo = GsonUtil.object2Gson(send);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========SendMessage to json========" + pushInfo);
            }
            //推送
            com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(account.getAcctName(), pushInfo);
        }
        houseOwnerMapper.updateByPrimaryKeySelective(houseOwner);
        //查询该房屋下是否有待认证的成员
        List<MemberAuth> list = memberAuthMapper.findByHouseId(smsReq.getHouseId());
        if (list != null && list.size() > 0) {
            for (MemberAuth auth :
                    list) {
                //判断该成员是否已经是户主
                if (auth.getApplicantId().equals(houseOwner.getUserId())) {
                    //将成员失效
                    HouseMember houseMember = houseMemberMapper.findByHouseIdAndPhone(auth.getPhoneNum(), auth.getHouseId());
                    if (houseMember != null) {
                        HouseMember member = new HouseMember();
                        member.setMemberId(houseMember.getMemberId());
                        member.setMemberStatus(HouseConstant.MEMBER_DELETE_STATUS);
                        houseMemberMapper.updateByPrimaryKeySelective(member);
                    }
                    MemberAuth memberAuth = new MemberAuth();
                    memberAuth.setAuthId(auth.getAuthId());
                    memberAuth.setAuthStatus(HouseConstant.AUTHSTATUSDELETE);
                    memberAuthMapper.updateByPrimaryKeySelective(memberAuth);
                } else {
                    //发认证通知
                    HouseAuthNotice notice = new HouseAuthNotice();
                    notice.setAuthId(auth.getAuthId());
                    notice.setCreateTime(new Date(System.currentTimeMillis()));
                    notice.setNoticeContent("尊敬的用户，你有一个成员认证请求，请查看!");
                    notice.setNoticeStatus(HouseConstant.ONE);
                    notice.setNoticeUserId(houseOwner.getUserId());
                    houseAuthNoticeMapper.insertSelective(notice);
                    //建立推送模型
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setSendType(HouseConstant.SEND_TYPE);
                    sendMessage.setSendNo(HouseConstant.SEND_NO);
                    sendMessage.setSendTitle("成员认证");
                    sendMessage.setSendContent("尊敬的用户，你有一个成员认证请求，请查看!");
                    sendMessage.setSendId(notice.getNoticeId().toString());
                    //转json
                    String pushInfo = GsonUtil.object2Gson(sendMessage);
                    //再推送消息给用户
                    UserAccount userAccount = userService.findByUserId(houseOwner.getUserId(), request);
                    if (null != userAccount) {
                        //再推送消息给用户
                        com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(userAccount.getAcctName(), pushInfo);
                    }
                }
            }
        }
    }

    @Override
    public HouseMember findMemBySmsReq(GetSmsReq smsReq) {
        return houseMemberMapper.findMemBySmsReq(smsReq);
    }

    @Override
    public void addMember(GetSmsReq smsReq, HttpServletRequest request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----------addMember start----------------smsReq=" + smsReq);
        }
        //判断该成员是否注册过账号
        UserAccount userAccount = userService.findByAccountName(smsReq.getOwnerPhone(), request);
        if (null == userAccount) {
            //创建成员账号
            String userId = createUser(smsReq, request);
            //添加房屋成员
            addHouseMember(smsReq, userId);
            //发送短信给成员
            HouseInfoApp houseInfo = houseMapper.findHouseInfo(smsReq.getHouseId());
            String content = "您已成为" + houseInfo.getHouseName() + "房屋成员，登录账号：" + smsReq.getOwnerPhone() + "，密码：" + smsReq.getOwnerPhone().substring(5);
            String[] phones = new String[]{smsReq.getOwnerPhone()};
            String[] params = new String[]{houseInfo.getHouseName(),smsReq.getOwnerPhone(),smsReq.getOwnerPhone().substring(5)};
            BaiduSmsUtil.sendSms(BaiduSmsCode.HOUSE_MEMBER_JOIN_TEMPLATE,phones,params);
            //发送系统通知给户主
            snedNoticeToOwner(smsReq, request);
        } else {
            //判断成员是否自己提交过认证
            MemberAuth auth = memberAuthMapper.findUnMemberAuth(smsReq.getHouseId(), userAccount.getUserId());
            HouseMember houseMember = houseMemberMapper.findByHouseIdAndPhone(smsReq.getOwnerPhone(), smsReq.getHouseId());
            if (auth != null && houseMember != null) {
                //修改待认证的成员认证的审核状态为已审核
                MemberAuth memberAuth = new MemberAuth();
                memberAuth.setAuthId(auth.getAuthId());
                memberAuth.setReviewStatus(HouseConstant.AUTHSTATUSSUCCUSS);
                memberAuthMapper.updateByPrimaryKeySelective(memberAuth);
                //修改成员信息表
                HouseMember member = new HouseMember();
                member.setMemberId(houseMember.getMemberId());
                member.setMemberStatus(HouseConstant.TWO);
                member.setUpdateTime(new Date(System.currentTimeMillis()));
                houseMemberMapper.updateByPrimaryKeySelective(member);
                //发送通知给成员
                sendNoticeToMember(userAccount, smsReq);
            } else {
                //添加房屋成员
                addHouseMember(smsReq, userAccount.getUserId());
                //发送通知给成员
                sendNoticeToMember(userAccount, smsReq);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----------addMember end-------------------");
        }
    }

    @Override
    public List<AuthHouse> findMyHomeList(String userId) {
        //查询所有已激活的户主
        List<AuthHouse> ownerHouse = houseOwnerMapper.findMyHomeList(userId);
        //查询所有未删除的成员
        List<AuthHouse> memberHouse = memberAuthMapper.findMyHomeList(userId);
        List<AuthHouse> list = new ArrayList<>();
        list.addAll(ownerHouse);
        list.addAll(memberHouse);
        return list;
    }

    @Override
    public HouseOwner findOwnerBySmsReq(GetSmsReq smsReq, HttpServletRequest request) throws Exception {
        HouseOwner owner = houseOwnerMapper.findByHouseId(smsReq.getHouseId());
        if (owner != null && !StringUtil.isBlank(owner.getUserId())) {
            UserAccount account = userService.findByUserId(owner.getUserId(), request);
            if (account != null && account.getAcctName().equals(smsReq.getOwnerPhone())) {
                return owner;
            }
        }
        return null;
    }

    /**
     * <p>
     * 户主添加成员 给成员发通知
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 14:58
     */
    private void sendNoticeToMember(UserAccount acount, GetSmsReq smsReq) {
        //查询房屋信息
        HouseInfoApp houseInfo = houseMapper.findHouseInfo(smsReq.getHouseId());
        //创建系统通知对象，将通知存到数据库
        Notice notice = new Notice();
        notice.setNoticeStatus(1);
        notice.setNoticeType(HouseConstant.OWNER_ADD_MEMBER_SENDNO2);
        notice.setCreateTime(new Date(System.currentTimeMillis()));
        notice.setNoticeUserId(acount.getUserId());
        notice.setNoticeContent("您已成为" + houseInfo.getHouseName() + "房屋成员");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========before addNotice========notice=" + notice);
        }
        noticeMapper.insertSelective(notice);
        //返回主键
        Long noticeId = notice.getNoticeId();
        if (LOG.isDebugEnabled()) {
            LOG.debug("========after addNotice========notice=" + notice);
        }
        SendMessage send = new SendMessage();
        send.setSendId(noticeId.toString());
        send.setSendTitle("成员添加成功");
        send.setSendNo(HouseConstant.OWNER_ADD_MEMBER_SENDNO2.toString());
        send.setSendType(HouseConstant.MEMBER_SEND_TYPE);
        send.setSendContent("您已成为" + houseInfo.getHouseName() + "房屋成员");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage========" + send);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage to json========" + pushInfo);
        }
        //推送
        com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(acount.getAcctName(), pushInfo);
    }

    /**
     * <p>
     * 户主添加成员，给户主发通知
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 14:28
     */
    private void snedNoticeToOwner(GetSmsReq smsReq, HttpServletRequest request) throws Exception {
        Member member = houseOwnerMapper.findMemberByHouseId(smsReq.getHouseId());
        if (member != null) {
            UserAccount acount = userService.findByAccountName(member.getUserAccount(), request);
            if (acount != null) {
                //创建系统通知对象，将通知存到数据库
                Notice notice = new Notice();
                notice.setNoticeStatus(1);
                notice.setNoticeType(HouseConstant.OWNER_ADD_MEMBER_SENDNO);
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeUserId(acount.getUserId());
                notice.setNoticeContent("手机号为" + smsReq.getOwnerPhone() + "的成员登录账号：" + smsReq.getOwnerPhone()
                        + "，密码：" + smsReq.getOwnerPhone().substring(5));
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========before addNotice========notice=" + notice);
                }
                noticeMapper.insertSelective(notice);
                //返回主键
                Long noticeId = notice.getNoticeId();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========after addNotice========notice=" + notice);
                }
                SendMessage send = new SendMessage();
                send.setSendId(noticeId.toString());
                send.setSendTitle("成员添加成功");
                send.setSendNo(HouseConstant.OWNER_ADD_MEMBER_SENDNO.toString());
                send.setSendType(HouseConstant.MEMBER_SEND_TYPE);
                send.setSendContent("手机号为" + smsReq.getOwnerPhone() + "的成员登录账号：" + smsReq.getOwnerPhone()
                        + "，密码：" + smsReq.getOwnerPhone().substring(5));
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========SendMessage========" + send);
                }
                //转json
                String pushInfo = GsonUtil.object2Gson(send);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========SendMessage to json========" + pushInfo);
                }
                //推送
                com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(acount.getAcctName(), pushInfo);
            }
        }
    }

    /**
     * <p>
     * 添加房屋成员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/16 11:16
     */
    private void addHouseMember(GetSmsReq smsReq, String userId) {
        HouseMember member = new HouseMember();
        member.setUpdateTime(new Date(System.currentTimeMillis()));
        member.setUserId(userId);
        member.setCreateTime(new Date(System.currentTimeMillis()));
        member.setMemberStatus(HouseConstant.OWNER_IS_STATUS);
        member.setPhoneNum(smsReq.getOwnerPhone());
        member.setRealName(smsReq.getOwnerName());
        houseMemberMapper.insertSelective(member);
        //返回memberId
        String memberId = member.getMemberId();
        HouseMemberRela memberRela = new HouseMemberRela();
        memberRela.setCreateTime(new Date(System.currentTimeMillis()));
        memberRela.setHouseId(smsReq.getHouseId());
        memberRela.setMemberId(memberId);
        houseMemberRelaMapper.insertSelective(memberRela);
        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setReviewStatus(HouseConstant.AUTHSTATUSSUCCUSS);
        memberAuth.setPhoneNum(smsReq.getOwnerPhone());
        memberAuth.setApplicantId(userId);
        memberAuth.setApplicantName(smsReq.getOwnerName());
        memberAuth.setAuthTime(new Date(System.currentTimeMillis()));
        memberAuth.setAuthStatus(HouseConstant.ONE);
        memberAuth.setReviewTime(new Date(System.currentTimeMillis()));
        memberAuth.setHouseId(smsReq.getHouseId());
        memberAuthMapper.insertSelective(memberAuth);
    }

    /**
     * <p>
     * 创建成员账号
     * </p>
     * Author: jianz.z <br/>
     * Date: 2017/11/16 11:10
     *
     * @param smsReq
     * @return userId
     */
    private String createUser(GetSmsReq smsReq, HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("ownerPhone", smsReq.getOwnerPhone());
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL) + "httpclientSocial/estatemgmt/v1/createUser", jsonParam, request);
        if (null != response) {
            if (null != response.getData()) {
                JSONObject json = JSONObject.fromObject(response.getData());
                return json.get("userId").toString();
            }
        }
        return null;
    }
}

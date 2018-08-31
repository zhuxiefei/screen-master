package com.betel.estatemgmt.business.web.housestatus.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.web.housestatus.code.HouseStatusCode;
import com.betel.estatemgmt.business.web.housestatus.constant.HouseStatusConstant;
import com.betel.estatemgmt.business.web.housestatus.model.*;
import com.betel.estatemgmt.business.web.housestatus.service.HouseStatusService;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.utils.*;
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
 *
 * @className: HouseStatusServiceImpl <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/13 17:32 <br/>
 * @version: 1.0
 */
@Service
@Transactional
public class HouseStatusServiceImpl implements HouseStatusService {
    private static final Logger LOG = LoggerFactory.getLogger(HouseStatusServiceImpl.class);
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private BuildingUnitMapper buildingUnitMapper;
    @Autowired
    private HouseOwnerMapper houseOwnerMapper;
    @Autowired
    private HouseMemberMapper houseMemberMapper;
    @Autowired
    private HouseMemberRelaMapper houseMemberRelaMapper;
    @Autowired
    private MemberAuthMapper memberAuthMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private HouseOwnerRelaMapper houseOwnerRelaMapper;
    @Autowired
    private HouseTenantMapper houseTenantMapper;
    @Autowired
    private HouseAuthNoticeMapper houseAuthNoticeMapper;
    @Autowired
    private UserService userService;


    @Override
    public BuildingInfo findHouseStatus(HouseStatusReq houseStatusReq,String estateId) {
        if (StringUtil.isBlank(houseStatusReq.getBuildingId()) && StringUtil.isBlank(houseStatusReq.getIsOthers())) {
            return findHouseStatusFirsr(estateId);
        } else {
            //传buildingId
            if (StringUtil.isBlank(houseStatusReq.getIsOthers()) && !StringUtil.isBlank(houseStatusReq.getBuildingId())) {
                //通过buildingId查询
                return findHouseStatusByBuildingId(Long.valueOf(houseStatusReq.getBuildingId().trim()));
            } else if (!StringUtil.isBlank(houseStatusReq.getIsOthers()) && StringUtil.isBlank(houseStatusReq.getBuildingId())) {
                //isOther
                return findHouseVillas();
            } else {
                return findHouseStatusFirsr(estateId);
            }
        }
    }

    @Override
    public HouseStatusInfo findHouseDetail(HouseStatusReq houseStatusReq) {
        return houseMapper.findHouseDetail(houseStatusReq.getHouseId().trim());
    }

    @Override
    public List<HouseStatusOwner> findHouseStatusOwner(String houseId) {
        return houseOwnerMapper.findHouseStatusOwner(houseId);
    }

    @Override
    public List<HouseStatusMember> findHouseStatusMember(String houseId) {
        return houseMemberMapper.findHouseStatusMember(houseId);
    }

    @Override
    public List<HouseStatusTenant> findHouseStatusTenant(HouseStatusReq houseStatusReq) {
        return houseTenantMapper.findHouseStatusTenant(houseStatusReq.getHouseId());
    }

    @Override
    public String addHouseStatusOwner(HouseStatusReq houseStatusReq,HttpServletRequest request) throws Exception {
        //前后去空格
        houseStatusReq = trimHouseStatusOwner(houseStatusReq);
        String code = checkAddHouseStatusOwner(houseStatusReq);
        if (null != code) {
            return code;
        } else if (null != houseOwnerMapper.isOwnerExist(houseStatusReq)) {
            //判读户主是否重复
            return HouseStatusCode.OWNER_PHONE_NUM_NAME_IS_EXIST;
        } else {
            //思路：1、因房屋只有一个户主，那么激活和待激活全部设置失效;
            //      2、发通知给旧户主，旧成员
            List<HouseStatusOwner> houseStatusOwnerList = houseOwnerMapper.findHouseStatusOwnerOnLine(houseStatusReq);
            //迭代九获得用户手机号
            if (houseStatusOwnerList.size()>0){
                UserAccount userAccount=userService.findByUserId(houseStatusOwnerList.get(0).getOwnerUserId(),request);
                if (null!=userAccount){
                    houseStatusOwnerList.get(0).setOwnerPhoneNum(userAccount.getAcctName());
                }
            }
            List<String> ownerIds = new ArrayList<>();
            //如果有设置户主失效
            for (int i = 0; i < houseStatusOwnerList.size(); i++) {
                ownerIds.add(houseStatusOwnerList.get(i).getOwnerId());
            }
            House house = houseMapper.findHouseByHouseId(houseStatusReq.getHouseId());
            if (ownerIds.size() > 0) {
                int flag = houseOwnerMapper.setOldOwenerOver(ownerIds);
                //发送通知给旧户主
                String status = houseStatusOwnerList.get(0).getOwnerStatus();
                String userId = houseStatusOwnerList.get(0).getOwnerUserId();
                String phoneNum = houseStatusOwnerList.get(0).getOwnerPhoneNum();
                if (HouseStatusConstant.STATUS_TWO.equals(status) && null != userId) {
                    Notice notice = new Notice();
                    notice.setNoticeUserId(houseStatusOwnerList.get(0).getOwnerUserId());
                    notice.setNoticeContent("您的" + house.getHouseNum() + "房子户主信息已经变更，如有疑问，请及时联系物业");
                    notice.setNoticeStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
                    notice.setNoticeType(Integer.valueOf(HouseStatusConstant.SENDNO_OWNER_UPDATE));
                    notice.setCreateTime(new Date());
                    int insertSelectiveNotice = noticeMapper.insertSelective(notice);
                    //建立推送模型
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setSendType(HouseStatusConstant.SENDTYPE);
                    sendMessage.setSendNo(HouseStatusConstant.SENDNO_OWNER_UPDATE);
                    sendMessage.setSendTitle("户主变更");
                    sendMessage.setSendContent("您的" + house.getHouseNum() + "房子户主信息已经变更，如有疑问，请及时联系物业");
                    sendMessage.setSendId(notice.getNoticeId().toString());
                    //转json
                    String pushInfo = GsonUtil.object2Gson(sendMessage);
                    //再推送消息给用户
                    if (null != phoneNum) {
                        //再推送消息给用户
                        com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(phoneNum, pushInfo);
                    }
                }
            }
            //思路：1、查询出旧成员信息：待认证、已认证、已失效；
            // 2、删除旧成员信息：旧成员表，房屋成员表，成员认证表；备注：更换户主时旧成员已经全部删除，所以房屋下的成员必是新户主的成员
            //3、通知只发送给已认证的成员
            //---------------查询已认证的成员，旧房屋变更时发送成员通知---------------发生通知开始----------------------
            List<HouseMember> houseValidMemberList = houseMemberMapper.findOldValidMemberByHouseId(houseStatusReq.getHouseId());
            List<Notice> notices = new ArrayList<>();
            for (int i = 0; i < houseValidMemberList.size(); i++) {
                //获得成员手机号：迭代九
                houseValidMemberList.get(i).setPhoneNum(userService.findByUserId(houseValidMemberList.get(i).getUserId(),request).getAcctName());
                Notice notice = new Notice();
                notice.setNoticeUserId(houseValidMemberList.get(i).getUserId());
                notice.setNoticeContent("由于户主身份失效，您已经被移出该房屋(" + house.getHouseNum() + ")，如有疑问，请及时联系物业");
                notice.setNoticeStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
                notice.setNoticeType(Integer.valueOf(HouseStatusConstant.SENDNO_MEMBER_DELETE_SEND_TO_MEMBER));
                notice.setCreateTime(new Date());
                notices.add(notice);
            }

            if (notices.size() > 0) {
                int flag = noticeMapper.insertAuditNotice(notices);
                //建立推送模型
                SendMessage sendMessage = new SendMessage();
                sendMessage.setSendType(HouseStatusConstant.SENDTYPE);
                sendMessage.setSendNo(HouseStatusConstant.SENDNO_MEMBER_DELETE_SEND_TO_MEMBER);
                sendMessage.setSendTitle("成员关系解除");
                sendMessage.setSendContent("由于户主身份失效，您已经被移出该房屋(" + house.getHouseNum() + ")，如有疑问，请及时联系物业");
                sendMessage.setSendId(notices.get(0).getNoticeId().toString());
                //转json
                String pushInfo = GsonUtil.object2Gson(sendMessage);
                List<String> phoneNumList = new ArrayList<>();
                for (int i = 0; i < houseValidMemberList.size(); i++) {
                    //获得成员手机号
                    String phoneNum = houseValidMemberList.get(i).getPhoneNum();
                    if (null != phoneNum) {
                        phoneNumList.add(phoneNum);
                    }
                }
                //再推送消息给用户
                if (phoneNumList.size() > 0) {
                    com.betel.estatemgmt.common.msgpush.MsgPushUtils.pushList(phoneNumList, pushInfo);
                }
            }
            //---------------查询已认证的成员，旧房屋变更时发生通知成员---------------发生通知结束--------------
            //为了解决bug：第一次进来时先增加成员，再添加户主时，关闭弹框后再打开，成员被删除了
            List<HouseMember> houseMemberList = houseMemberMapper.findOldMemberByHouseId(houseStatusReq.getHouseId());
            List<HouseStatusOwner> houseOwners = houseOwnerMapper.findHouseStatusOwner(houseStatusReq.getHouseId());
            if (houseOwners.size() > HouseStatusConstant.ZERO) {
                //---------------查询已认证的成员，删除旧成员信息，成员房屋关系表信息---------------开始----------------------
                int isflag = houseMemberRelaMapper.deleteOldMemberByHouseId(houseStatusReq.getHouseId());
                List<String> memberIds = new ArrayList<>();
                for (int i = 0; i < houseMemberList.size(); i++) {
                    memberIds.add(houseMemberList.get(i).getMemberId());
                }
                if (memberIds.size() > 0) {
                    int isdel = houseMemberMapper.deleteOldMemberByMemberIds(memberIds);
                }
                int oldHouseMember = memberAuthMapper.deletOldHouseMemberAuthHouseId(houseStatusReq.getHouseId());
                //---------------查询已认证的成员，删除旧成员信息，成员房屋关系表信息---------------结束----------------------
            }
            HouseOwner curHouseOwner = new HouseOwner();
            curHouseOwner.setOwnerId(UuidUtil.create());
            curHouseOwner.setRealName(houseStatusReq.getOwnerName());
            curHouseOwner.setPhoneNum(houseStatusReq.getOwnerPhoneNum());
            curHouseOwner.setEthnic(houseStatusReq.getOwnerEthnic());
            curHouseOwner.setResidency(houseStatusReq.getOwnerResidency());
            if (!StringUtil.isBlank(houseStatusReq.getOwnerReligion())) {
                curHouseOwner.setReligion(Integer.valueOf(houseStatusReq.getOwnerReligion()));
            }
            curHouseOwner.setOwnerStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
            curHouseOwner.setCreateTime(new Date());
            curHouseOwner.setUpdateTime(new Date());
            //插入户主表
            int isInsertOwner = houseOwnerMapper.insertSelective(curHouseOwner);
            HouseOwnerRela houseOwnerRela = new HouseOwnerRela();
            houseOwnerRela.setHouseId(houseStatusReq.getHouseId());
            houseOwnerRela.setOwnerId(curHouseOwner.getOwnerId());
            houseOwnerRela.setCreateTime(new Date());
            //判断房屋是否删除
            if (null == houseMapper.selectByPrimaryKey(houseStatusReq.getHouseId())) {
                return HouseStatusCode.HOUSE_IS_NOT_EXIST;
            }
            //插入房屋户主表
            int isInsertOwnerRela = houseOwnerRelaMapper.insertSelective(houseOwnerRela);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String addHouseStatusMember(HouseStatusReq houseStatusReq, HttpServletRequest request) throws Exception {
        long start = System.currentTimeMillis(); //获取开始时间
        //前后去空格
        houseStatusReq = trimHouseStatusOwner(houseStatusReq);
        //参数校验
        String code = checkAddHouseStatusMember(houseStatusReq);
        if (null != code) {
            return code;
        } else if (houseMemberMapper.isMemberExist(houseStatusReq) > 0) {
            //查询成员是否存在：如果存在，返回code，如果不存在
            return HouseStatusCode.MEMBER_PHONE_NUM_NAME_IS_EXIST;
        } else {
            //为解决bugId=3483
            //房屋下查询当前激活的户主：userId！=null
            List<HouseOwner> houseOwnerHasLife = houseOwnerMapper.findOwner(houseStatusReq.getHouseId());
            String phoneNum = null;
            //查询户主的登录账号
            if (houseOwnerHasLife.size() > 0) {
                String ownerUserId = houseOwnerHasLife.get(0).getUserId();

                UserAccount userAccount = userService.findByUserId(ownerUserId,request);
                if (null != userAccount) {
                    phoneNum = userAccount.getAcctName();
                }
                //进行判断重复
                if (houseStatusReq.getMemberPhoneNum().equals(phoneNum)) {
                    return HouseStatusCode.ACCOUNT_HAS_REGISTER_OWNER;
                }
            }
            //思路:判断成员是否有App账号，若没有先注册账号，发送通知给注册成员用户，再插入成员表，房屋成员关系表，成员认证表，发送通知给当前户主
            //1、判读成员是否有App账号
            //没有户主业可以添加成员
            HouseStatusRegisterUser isRegister = validatePhone(
                    houseStatusReq.getMemberName(), houseStatusReq.getMemberPhoneNum(),request);
            boolean sendRegister = false;
            if (null == isRegister) {
                isRegister = registerUser(houseStatusReq.getMemberName(), houseStatusReq.getMemberPhoneNum(),request);
                sendRegister = true;
                //发送短信给成员
                if (null!=isRegister){
                    String content = "您已成功注册账号。登录账号：" + isRegister.getPhoneNum() + "，密码：" + isRegister.getMemberPwd();
                    String[] phones = new String[]{isRegister.getPhoneNum()};
                    String[] params = new String[]{isRegister.getPhoneNum(),isRegister.getMemberPwd()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.REGISTER_PASS_TEMPLATE,phones,params);
                }
            }
            HouseMember houseMember = new HouseMember();
            houseMember.setMemberId(UuidUtil.create());
            houseMember.setRealName(houseStatusReq.getMemberName());
            houseMember.setPhoneNum(houseStatusReq.getMemberPhoneNum());
            if (!StringUtil.isBlank(houseStatusReq.getMemberReligion())) {
                houseMember.setReligion(Integer.valueOf(houseStatusReq.getMemberReligion()));
            }
            houseMember.setEthnic(houseStatusReq.getMemberEthnic());
            houseMember.setResidency(houseStatusReq.getMemberResidency());
            houseMember.setMemberStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
            houseMember.setCreateTime(new Date());
            houseMember.setUpdateTime(new Date());
            if (null!=isRegister){
                houseMember.setUserId(isRegister.getMemberUserId());
            }
            int isInsertMember = houseMemberMapper.insertSelective(houseMember);
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------管理添加成员，插入成员信息-----成功:" + isInsertMember + "条数据------");
            }
            HouseMemberRela houseMemberRela = new HouseMemberRela();
            houseMemberRela.setMemberId(houseMember.getMemberId());
            houseMemberRela.setHouseId(houseStatusReq.getHouseId());
            houseMemberRela.setCreateTime(new Date());
            if (null == houseMapper.selectByPrimaryKey(houseStatusReq.getHouseId())) {
                return HouseStatusCode.HOUSE_IS_NOT_EXIST;
            }
            int isHouseMember = houseMemberRelaMapper.insertSelective(houseMemberRela);
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------管理添加成员，建立房屋成员关系信息-----成功:" + isHouseMember + "条数据------");
            }
            MemberAuth memberAuth = new MemberAuth();
            memberAuth.setApplicantId(isRegister.getMemberUserId());
            memberAuth.setHouseId(houseStatusReq.getHouseId());
            memberAuth.setPhoneNum(isRegister.getPhoneNum());
            memberAuth.setApplicantName(isRegister.getMemberRealName());
            memberAuth.setAuthStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
            memberAuth.setReviewStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
            memberAuth.setAuthTime(new Date());
            int isInsertMemberAuth = memberAuthMapper.insertSelective(memberAuth);
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------管理添加成员，插入成员认证申请表-----成功:" + isInsertMemberAuth + "条数据------");
            }
            //激光推送户主
            if (houseOwnerHasLife.size() > 0) {
                //成员认证通知
                HouseAuthNotice notice = new HouseAuthNotice();
                notice.setAuthId(memberAuth.getAuthId());
                notice.setNoticeUserId(houseOwnerHasLife.get(0).getUserId());
                notice.setNoticeContent("尊敬的用户，你有一个成员认证请求，请查看!");
                notice.setNoticeStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
                notice.setCreateTime(new Date());
                int flag = houseAuthNoticeMapper.insertSelective(notice);
                if (isInsertMemberAuth > 0) {
                    LOG.debug("----------管理添加成员后通知户主确认，插入系统通知信息-----成功:" + flag + "条数据------");
                }
                //建立推送模型
                SendMessage sendMessage = new SendMessage();
                sendMessage.setSendType(HouseStatusConstant.MEMBER_AUTH_SENDTYPE);
                sendMessage.setSendNo(HouseStatusConstant.SENDNO_MEMBER_SEND_TO_OWNER);
                sendMessage.setSendTitle("成员认证");
                sendMessage.setSendContent("尊敬的用户，你有一个成员认证请求，请查看!");
                sendMessage.setSendId(notice.getNoticeId().toString());
                //转json
                String pushInfo = GsonUtil.object2Gson(sendMessage);
                //再推送消息给用户
                if (null != phoneNum) {
                    //再推送消息给用户
                    com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(phoneNum, pushInfo);
                }
                //成员注册账号，发送系统通知给当前户主
                if (sendRegister == true) {
                    Notice noticeRegisterMember = new Notice();
                    noticeRegisterMember.setNoticeUserId(houseOwnerHasLife.get(0).getUserId());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("------------------- 新增未注销成员时发送通知给户主：");
                        LOG.debug("账号" + isRegister.getPhoneNum() + "已经被成功注册账号。登录账号：" + isRegister.getPhoneNum() + "，密码：" + isRegister.getMemberPwd() + "");
                    }
                    noticeRegisterMember.setNoticeContent("账号" + isRegister.getPhoneNum() + "已经被成功注册账号。登录账号：" + isRegister.getPhoneNum() + "，密码：" + isRegister.getMemberPwd() + "");
                    noticeRegisterMember.setNoticeStatus(Integer.valueOf(HouseStatusConstant.STATUS_ONE));
                    noticeRegisterMember.setNoticeType(Integer.valueOf(HouseStatusConstant.REGISTER_MEMBER_SEND_TO_NOW_OWNER));
                    noticeRegisterMember.setCreateTime(new Date());
                    int insertSelectiveNotice = noticeMapper.insertSelective(noticeRegisterMember);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("----------管理添加户主，插入系统通知表-----成功:" + insertSelectiveNotice + "条数据------");
                    }
                    //建立推送模型
                    SendMessage sendMessageRegisterMember = new SendMessage();
                    sendMessageRegisterMember.setSendType(HouseStatusConstant.SENDTYPE);
                    sendMessageRegisterMember.setSendNo(HouseStatusConstant.REGISTER_MEMBER_SEND_TO_NOW_OWNER);
                    sendMessageRegisterMember.setSendTitle("成员注册");
                    sendMessageRegisterMember.setSendContent("账号" + isRegister.getPhoneNum() + "已经被成功注册账号。登录账号：" + isRegister.getPhoneNum() + "，密码：" + isRegister.getMemberPwd() + "");
                    sendMessageRegisterMember.setSendId(noticeRegisterMember.getNoticeId().toString());
                    //转json
                    String pushInfoRegisterMember = GsonUtil.object2Gson(sendMessageRegisterMember);
                    //再推送消息给用户
                    if (null != phoneNum) {
                        //再推送消息给用户
                        com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(phoneNum, pushInfoRegisterMember);
                    }
                }
            }
            long end = System.currentTimeMillis(); //获取结束时间
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------------------------------------------->>>>>>>>>>>>>>>>程序运行时间： " + (end - start) + "ms");
            }
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String addHouseStatusTenant(HouseStatusReq houseStatusReq) {
        //校验
        String code = checkAddHouseStatusTenant(houseStatusReq);
        if (null != code) {
            return code;
        } else {
            //前后去空格
            houseStatusReq = trimHouseStatusOwner(houseStatusReq);
            HouseTenant houseTenant = houseTenantMapper.isTenantExist(houseStatusReq);
            if (null != houseTenant) {
                return HouseStatusCode.TEMANT_PHOME_NUM_NAME_IS_EXIST;
            } else if (!HouseStatusConstant.RELIGION_THREE.equals(houseStatusReq.getHouseStatus())) {
                return HouseStatusCode.HOUSE_STATUS_IS_NOT_RENT;
            } else if (null == houseMapper.selectByPrimaryKey(houseStatusReq.getHouseId())) {
                return HouseStatusCode.HOUSE_IS_NOT_EXIST;
            } else {
                HouseTenant curHouseTenant = new HouseTenant();
                curHouseTenant.setTenantId(UuidUtil.create());
                curHouseTenant.setHouseId(houseStatusReq.getHouseId());
                curHouseTenant.setTenantName(houseStatusReq.getTenantName());
                curHouseTenant.setPhoneNum(houseStatusReq.getPhoneNum());
                curHouseTenant.setEthnic(houseStatusReq.getTenantEthnic());
                curHouseTenant.setResidency(houseStatusReq.getTenantResidency());
                if (!StringUtil.isBlank(houseStatusReq.getTenantReligion())) {
                    curHouseTenant.setReligion(Integer.valueOf(houseStatusReq.getTenantReligion()));
                }
                curHouseTenant.setCreateTime(new Date());
                curHouseTenant.setUpdateTime(new Date());
                houseTenantMapper.insertSelective(curHouseTenant);
                House house = new House();
                house.setHouseId(houseStatusReq.getHouseId());
                house.setHouseStatus(Integer.valueOf(houseStatusReq.getHouseStatus()));
                houseMapper.updateByPrimaryKeySelective(house);
                return StatusCode.SUCCESS;
            }
        }
    }

    @Override
    public String deleteHouseStatusTenant(HouseStatusReq houseStatusReq) {
        if (StringUtil.isBlank(houseStatusReq.getTenantId())) {
            return HouseStatusCode.TENANT_ID_IS_NULL;
        } else {
            houseTenantMapper.deleteByPrimaryKey(houseStatusReq.getTenantId());
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String updateHouseStatus(HouseStatusReq houseStatusReq) {
        String code = checkUpdateHouseStatus(houseStatusReq);
        if (null != code) {
            return code;
        } else {
            House house = new House();
            house.setHouseId(houseStatusReq.getHouseId());
            house.setPetStatus(Integer.valueOf(houseStatusReq.getPetStatus()));
            house.setHouseStatus(Integer.valueOf(houseStatusReq.getHouseStatus()));
            houseMapper.updateByPrimaryKeySelective(house);
            return StatusCode.SUCCESS;
        }
    }

    private String checkUpdateHouseStatus(HouseStatusReq houseStatusReq) {
        if (StringUtil.isBlank(houseStatusReq.getHouseId())) {
            return HouseStatusCode.HOUSE_ID_IS_NULL;
        } else if (StringUtil.isBlank(houseStatusReq.getHouseStatus())) {
            return HouseStatusCode.HOUSE_STATUS_IS_NULL;
        } else {
            boolean one = HouseStatusConstant.STATUS_ONE.equals(houseStatusReq.getHouseStatus());
            boolean two = HouseStatusConstant.STATUS_TWO.equals(houseStatusReq.getHouseStatus());
            boolean three = HouseStatusConstant.STATUS_THREE.equals(houseStatusReq.getHouseStatus());
            if (one == false && two == false && three == false) {
                return HouseStatusCode.HOUSE_STATUS_IS_NOT_EXIST;
            }
            // 是否饲养宠物
            if (!StringUtil.isBlank(houseStatusReq.getPetStatus())) {
                boolean isOne = HouseStatusConstant.STATUS_ONE.equals(houseStatusReq.getPetStatus());
                boolean isTne = HouseStatusConstant.STATUS_ZERO.equals(houseStatusReq.getPetStatus());
                if (isOne == false && isTne == false) {
                    return HouseStatusCode.PETSTATUS_IS_NOT_TRUE;
                }
            } else {
                return HouseStatusCode.PETSTATUS_IS_NULL;
            }
            //判断房屋是否存在
            if (null == houseMapper.selectByPrimaryKey(houseStatusReq.getHouseId().trim())) {
                return HouseStatusCode.HOUSE_IS_NOT_EXIST;
            }
        }
        return null;
    }

    private String checkAddHouseStatusOwner(HouseStatusReq houseStatusReq) {
        if (StringUtil.isBlank(houseStatusReq.getOwnerName())) {
            return HouseStatusCode.OWNER_NAME_IS_NULL;
        } else {
            if (!Validate.isPeopleName(houseStatusReq.getOwnerName().trim())) {
                return HouseStatusCode.OWNER_NAME_IS_NOT_LEGAL;
            }
        }
        if (StringUtil.isBlank(houseStatusReq.getOwnerPhoneNum())) {
            return HouseStatusCode.OWNER_PHONE_NUM_IS_NULL;
        } else {
            if (!Validate.isMobile(houseStatusReq.getOwnerPhoneNum().trim())) {
                return HouseStatusCode.OWNER_PHOME_NUM_IS_NOT_LAGAL;
            }
        }
        //religion 籍贯
        if (!StringUtil.isBlank(houseStatusReq.getOwnerResidency())) {
            if (!houseStatusReq.getOwnerResidency().trim().matches(RegexRule.CHINA_WORD)) {
                return HouseStatusCode.OWNER_RELIGION_IS_NOT_LAGAL;
            }
        }
        //ethnic民族
        if (!StringUtil.isBlank(houseStatusReq.getOwnerEthnic())) {
            if (!houseStatusReq.getOwnerEthnic().trim().matches(RegexRule.CHINESE_RULE)) {
                return HouseStatusCode.OWNER_ETHNIC_IS_NOT_LAGAL;
            }
        }
        // religion	宗教信仰:
        if (!StringUtil.isBlank(houseStatusReq.getMemberReligion())) {
            boolean one = HouseStatusConstant.RELIGION_ONE.equals(houseStatusReq.getMemberReligion());
            boolean two = HouseStatusConstant.RELIGION_TWO.equals(houseStatusReq.getMemberReligion());
            boolean three = HouseStatusConstant.RELIGION_THREE.equals(houseStatusReq.getMemberReligion());
            boolean four = HouseStatusConstant.RELIGION_FOUR.equals(houseStatusReq.getMemberReligion());
            boolean five = HouseStatusConstant.RELIGION_FIVE.equals(houseStatusReq.getMemberReligion());
            if (one == false && two == false && three == false && four == false && five == false) {
                return HouseStatusCode.MEMBER_RELIGION_IS_NOT_LEGAL;
            }
        }
        return null;
    }

    private String checkAddHouseStatusMember(HouseStatusReq houseStatusReq) {
        if (StringUtil.isBlank(houseStatusReq.getMemberName())) {
            return HouseStatusCode.MEMBER_NAME_IS_NULL;
        } else {
            if (!Validate.isPeopleName(houseStatusReq.getMemberName().trim())) {
                return HouseStatusCode.MEMBER_NAME_IS_NOT_LEGAL;
            }
        }
        if (StringUtil.isBlank(houseStatusReq.getMemberPhoneNum())) {
            return HouseStatusCode.MEMBER_PHONE_NUM_IS_NULL;
        } else {
            if (!Validate.isMobile(houseStatusReq.getMemberPhoneNum().trim())) {
                return HouseStatusCode.MEMBER_PHONE_NUM_IS_NOT_LEGAL;
            }
        }
        //religion 籍贯
        if (!StringUtil.isBlank(houseStatusReq.getOwnerResidency())) {
            if (houseStatusReq.getOwnerResidency().trim().matches(RegexRule.CHINA_WORD)) {
                return HouseStatusCode.MEMBER__RESIDENCY_IS_NOT_LEGAL;
            }
        }
        //ethnic民族
        if (!StringUtil.isBlank(houseStatusReq.getMemberEthnic())) {
            if (!houseStatusReq.getMemberEthnic().trim().matches(RegexRule.CHINESE_RULE)) {
                return HouseStatusCode.MEMBER__ETHNIC_IS_NOT_LEGAL;
            }
        }
        // religion	宗教信仰:getMemberReligion
        if (!StringUtil.isBlank(houseStatusReq.getOwnerReligion())) {
            boolean one = HouseStatusConstant.RELIGION_ONE.equals(houseStatusReq.getOwnerReligion());
            boolean two = HouseStatusConstant.RELIGION_TWO.equals(houseStatusReq.getOwnerReligion());
            boolean three = HouseStatusConstant.RELIGION_FOUR.equals(houseStatusReq.getOwnerReligion());
            boolean four = HouseStatusConstant.RELIGION_FIVE.equals(houseStatusReq.getOwnerReligion());
            boolean five = HouseStatusConstant.RELIGION_THREE.equals(houseStatusReq.getOwnerReligion());
            if (one == false && two == false && three == false && four == false && five == false) {
                return HouseStatusCode.OWNER_RELIGION_IS_NOT_EXIST;
            }
        }
        return null;
    }

    private String checkAddHouseStatusTenant(HouseStatusReq houseStatusReq) {
        if (StringUtil.isBlank(houseStatusReq.getTenantName())) {
            return HouseStatusCode.TENANT_NAME_IS_NULL;
        } else {
            if (!Validate.isPeopleName(houseStatusReq.getTenantName().trim())) {
                return HouseStatusCode.TENANT_NAME_IS_NOT_LEGAL;
            }
        }
        if (StringUtil.isBlank(houseStatusReq.getPhoneNum())) {
            return HouseStatusCode.TENANT_PHOME_NUM_IS_NULL;
        } else {
            if (!Validate.isMobile(houseStatusReq.getPhoneNum().trim())) {
                return HouseStatusCode.TENANT_PHOME_NUM_IS_NOT_LAGAL;
            }
        }
        //religion 籍贯
        if (!StringUtil.isBlank(houseStatusReq.getTenantResidency())) {
            if (!houseStatusReq.getTenantResidency().trim().matches(RegexRule.CHINA_WORD)) {
                return HouseStatusCode.TENANT_RESIDENCY_IS_NOT_LEGAL;
            }
        }
        //ethnic民族
        if (!StringUtil.isBlank(houseStatusReq.getTenantEthnic())) {
            if (!houseStatusReq.getTenantEthnic().trim().matches(RegexRule.CHINESE_RULE)) {
                return HouseStatusCode.TENANT_ETHNIC_IS_NOT_LEGAL;
            }
        }
        // religion	宗教信仰:
        if (!StringUtil.isBlank(houseStatusReq.getTenantReligion())) {
            boolean one = HouseStatusConstant.RELIGION_ONE.equals(houseStatusReq.getTenantReligion());
            boolean two = HouseStatusConstant.RELIGION_TWO.equals(houseStatusReq.getTenantReligion());
            boolean three = HouseStatusConstant.RELIGION_THREE.equals(houseStatusReq.getTenantReligion());
            boolean four = HouseStatusConstant.RELIGION_FOUR.equals(houseStatusReq.getTenantReligion());
            boolean five = HouseStatusConstant.RELIGION_FIVE.equals(houseStatusReq.getTenantReligion());
            if (one == false && two == false && three == false && four == false && five == false) {
                return HouseStatusCode.TENANT_RELIGION_IS_NOT_EXIST;
            }
        }
        return null;
    }


    private HouseStatusReq trimHouseStatusOwner(HouseStatusReq houseStatusReq) {
        //户主
        if (!StringUtil.isBlank(houseStatusReq.getOwnerName())) {
            houseStatusReq.setOwnerName(houseStatusReq.getOwnerName().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getOwnerPhoneNum())) {
            houseStatusReq.setOwnerPhoneNum(houseStatusReq.getOwnerPhoneNum().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getOwnerResidency())) {
            houseStatusReq.setOwnerResidency(houseStatusReq.getOwnerResidency().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getOwnerEthnic())) {
            houseStatusReq.setOwnerEthnic(houseStatusReq.getOwnerEthnic().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getOwnerReligion())) {
            houseStatusReq.setOwnerReligion(houseStatusReq.getOwnerReligion().trim());
        }
        //成员
        if (!StringUtil.isBlank(houseStatusReq.getMemberName())) {
            houseStatusReq.setMemberName(houseStatusReq.getMemberName().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getMemberPhoneNum())) {
            houseStatusReq.setMemberPhoneNum(houseStatusReq.getMemberPhoneNum().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getMemberResidency())) {
            houseStatusReq.setMemberResidency(houseStatusReq.getMemberResidency().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getMemberEthnic())) {
            houseStatusReq.setMemberEthnic(houseStatusReq.getMemberEthnic().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getMemberReligion())) {
            houseStatusReq.setMemberReligion(houseStatusReq.getMemberReligion().trim());
        }
        //租户
        if (!StringUtil.isBlank(houseStatusReq.getTenantName())) {
            houseStatusReq.setMemberName(houseStatusReq.getTenantName().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getPhoneNum())) {
            houseStatusReq.setPhoneNum(houseStatusReq.getPhoneNum().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getTenantResidency())) {
            houseStatusReq.setTenantResidency(houseStatusReq.getTenantResidency().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getTenantEthnic())) {
            houseStatusReq.setTenantEthnic(houseStatusReq.getTenantEthnic().trim());
        }
        if (!StringUtil.isBlank(houseStatusReq.getTenantReligion())) {
            houseStatusReq.setTenantReligion(houseStatusReq.getTenantReligion().trim());
        }
        return houseStatusReq;
    }

    private BuildingInfo findHouseStatusFirsr(String estateId) {
        //第一次前端不传参数
        //先取楼宇表，获得第一条楼宇id
        List<Building> buildingList = buildingMapper.findFirtBuilding(estateId);
        if (buildingList.size() > HouseStatusCode.ZERO) {
            Long firstBulidingId = buildingList.get(0).getBuildingId();
            //通过buildingId查询
            return findHouseStatusByBuildingId(firstBulidingId);
        } else {
            return findHouseVillas();
        }
    }

    /**
     * 查询别墅
     *
     * @return
     */
    private BuildingInfo findHouseVillas() {
        //楼宇不存在，查询别墅
        List<HouseStatusInfo> houses = houseMapper.findVillasByFist();
        BuildingInfo buildingInfo = new BuildingInfo();
        List<UnitInfo> unitList = new ArrayList<>();
        UnitInfo unitInfo = new UnitInfo();
        unitInfo.setHouseList(houses);
        unitList.add(unitInfo);
        buildingInfo.setUnitList(unitList);
        return buildingInfo;
    }

    /**
     * 查询普通房屋
     *
     * @param buildingId
     * @return
     */
    private BuildingInfo findHouseStatusByBuildingId(Long buildingId) {

        //获得楼宇信息
        BuildingInfo buildingInfo = new BuildingInfo();
        Building building = buildingMapper.selectByPrimaryKey(buildingId);
        if (null != building) {
            //1、楼宇id
            buildingInfo.setBuildingId(building.getBuildingId().toString());
            //2.楼宇名称
            buildingInfo.setBuildingName(building.getBuildingName());
        }

        //单元集合
        List<UnitInfo> unitList = new ArrayList<>();
        //-----------------------------收集-----------------------有楼宇有单元的房屋对象------------开始
        //思想：1、先收集单元id集合，再遍历单元id集合，查询数据库，获得单元下的房屋集合
        List<String> unitIdSet = new ArrayList<>();
        List<BuildingUnit> buildingUnitList = buildingUnitMapper.selecteHouseStatusUnitByBuildId(buildingId);
        for (int i = 0; i < buildingUnitList.size(); i++) {
            //获得单元id集合
            unitIdSet.add(buildingUnitList.get(i).getUnitId().toString());
        }
        //遍历单元idSet集合查询数据库
        Iterator<String> it = unitIdSet.iterator();
        while (it.hasNext()) {
            String unitId = it.next();
            //查询单元信息
            BuildingUnit buildingUnit = buildingUnitMapper.selectHouseStatusByPrimaryKey(Long.valueOf(unitId));
            UnitInfo unit = new UnitInfo();
            unit.setUnitId(buildingUnit.getUnitId().toString());
            unit.setUnitName(buildingUnit.getUnitName());
            BuildingUnit curbuildingUnit = new BuildingUnit();
            curbuildingUnit.setBuildingId(buildingId);
            curbuildingUnit.setUnitId(Long.valueOf(unitId));
            //房屋信息
            unit.setHouseList(houseMapper.findHouseByUnitId(curbuildingUnit));
            //设置由单元的信息
            unitList.add(unit);
        }
        //-----------------------------收集-----------------------有楼宇有单元的房屋对象------------结束


        //-----------------------------收集-----------------------有楼宇无单元房屋对象------------开始
        //通过第一个楼宇id查询房屋信息
        List<HouseStatusInfo> houseList = houseMapper.findHouseStatus(buildingId);
        UnitInfo unitInfo = new UnitInfo();
        List<HouseStatusInfo> housesNotUnit = new ArrayList<>();
        for (int i = 0; i < houseList.size(); i++) {
            String unitId = houseList.get(i).getUnitId();
            if (StringUtil.isBlank(unitId)) {
                HouseStatusInfo house = new HouseStatusInfo();
                house.setHouseId(houseList.get(i).getHouseId());
                house.setHouseNum(houseList.get(i).getHouseNum());
                house.setHouseStatus(houseList.get(i).getHouseStatus());
                housesNotUnit.add(house);
            }
        }
        //大于0说明存在有楼宇无单元的情况
        if (housesNotUnit.size() > 0) {
            unitInfo.setHouseList(housesNotUnit);
            unitList.add(unitInfo);
        }
        //---------------------------收集-------------------------有楼宇无单元房屋对象------------结束

        //3.设置最后一个单元集合信息属性
        buildingInfo.setUnitList(unitList);
        return buildingInfo;
    }

    /**
     * <p>
     * 验证昵称是否重复
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/16 16:03
     * return 重复 false 未重复 true
     */
    private Boolean validateUserName(String userName,HttpServletRequest request) throws Exception {
        return userService.validateUserName(userName,request);
    }

    /**
     * <p>
     * 生成随机字符串
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/22 9:29
     * return response
     */
    private static String getRandomString(int length) { // length 字符串长度
        StringBuilder buffer = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }


    public HouseStatusRegisterUser registerUser(String memberRealName, String memberPhoneNum,HttpServletRequest request) throws Exception {
        //用户注册
        try {
            return userService.register(memberRealName, memberPhoneNum,request);
        } catch (Exception e) {
            LOG.error("-----------管理员注册成员--------------报错---------", e);
            throw e;
        }
    }

    /**
     * <p>
     * 判断号码是否被注册
     * </p>
     * Author: jians.z <br/>
     * Date: 2017/5/17 13:20
     * 注册返回true，未注册返回false
     */
    private HouseStatusRegisterUser validatePhone(String realName, String phoneNum, HttpServletRequest request) throws Exception {

        HouseStatusRegisterUser hsru = new HouseStatusRegisterUser();
        UserAccount userInfos = userService.findByAccountName(phoneNum,request);
        if (null == userInfos) {
            return null;
        } else {
            //userId
            hsru.setMemberUserId(userInfos.getUserId());
            //手机号
            hsru.setPhoneNum(userInfos.getAcctName());
            //姓名
            hsru.setMemberRealName(realName);
            hsru.setMemberPwd(phoneNum.substring(phoneNum.length() - 6));
            return hsru;
        }
    }
}

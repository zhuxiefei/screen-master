package com.betel.estatemgmt.business.web.house.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.web.ad.model.Pic;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.house.code.HouseCode;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.house.model.*;
import com.betel.estatemgmt.business.web.house.service.HouseService;
import com.betel.estatemgmt.business.web.user.model.Members;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.decoration.DecoApplyRecordMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderPicMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderReplyMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.repair.RepairOrderPic;
import com.betel.estatemgmt.common.model.repair.RepairOrderReply;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 房屋信息维护接口实现类
 * </p>
 * ClassName: HouseServiceImpl <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 12:30 <br/>
 * Version: 1.0 <br/>
 */
@Service("HouseService")
@Transactional
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BuildingUnitMapper buildingUnitMapper;

    @Autowired
    private HouseTypeMapper houseTypeMapper;

    @Autowired
    private HouseOwnerMapper houseOwnerMapper;

    @Autowired
    private HouseOwnerRelaMapper ownerRelaMapper;

    @Autowired
    private HouseMemberMapper houseMemberMapper;

    @Autowired
    private HouseMemberRelaMapper memberRelaMapper;

    @Autowired
    private MemberAuthMapper memberAuthMapper;

    @Autowired
    private HouseAuthNoticeMapper houseAuthNoticeMapper;

    @Autowired
    private HousePicMapper housePicMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private HousePicTypeMapper housePicTypeMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private RepairOrderMapper orderMapper;

    @Autowired
    private RepairOrderReplyMapper replyMapper;

    @Autowired
    private RepairOrderPicMapper orderPicMapper;

    @Autowired
    private HouseParkingSpaceMapper spaceMapper;
    @Autowired
    private UserService userService;

    @Autowired
    DecoApplyRecordMapper applyMapper;

    @Autowired
    private HouseAuthNoticeMapper authNoticeMapper;

    @Autowired
    private HouseTenantMapper tenantMapper;

    private static final Logger LOG = LoggerFactory.getLogger(HouseServiceImpl.class);

    @Override
    public List<HouseInfo> findAllHouse(Paging<HouseInfo> pager, HousePageReq housePageReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========HouseServiceImpl findAllHouse start=========Paging<HouseInfo>=" + pager
                    + ",housePageReq=" + housePageReq);
        }
        List<HouseInfo> list = houseMapper.findAllHouse(pager.getRowBounds(), housePageReq);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========HouseServiceImpl findAllHouse end=========List<HouseInfo>=" + list);
        }
        return list;
    }

    @Override
    public Building findByBuildingId(Long buildingId) {
        return buildingMapper.selectByPrimaryKey(buildingId);
    }

    @Override
    public BuildingUnit findByUnitId(Long unitId) {
        return buildingUnitMapper.selectByPrimaryKey(unitId);
    }

    @Override
    public HouseType findByTypeId(Long typeId) {
        return houseTypeMapper.selectByPrimaryKey(typeId);
    }

    @Override
    public House findByMultipleParams(AddHouseReq addHouseReq) {
        return houseMapper.selectByMultipleParams(addHouseReq);
    }

    @Override
    public void addHouse(House house, UpdateHouseReq houseReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========HouseServiceImpl addHouse start=========house=" + house);
        }
        houseMapper.insertSelective(house);
        String houseId = house.getHouseId();
        //将车位信息封装
        List<HouseParkingSpace> list = new ArrayList<>();
        if (!StringUtil.isBlank(houseReq.getCarSeatNumBuy())) {
            String[] buySpaceNum = houseReq.getCarSeatNumBuy().split(",");
            String[] buyPlate = houseReq.getPlateNumBuy().split(",");
            for (int i = 0; i < buySpaceNum.length; i++) {
                HouseParkingSpace space = new HouseParkingSpace();
                space.setCreateTime(new Date(System.currentTimeMillis()));
                space.setHouseId(houseId);
                space.setLicensePlate(buyPlate[i]);
                space.setSpaceNum(buySpaceNum[i]);
                space.setSpaceType(HouseDataValidation.BUY_PARKING_SPACE);
                list.add(space);
            }
        }
        if (!StringUtil.isBlank(houseReq.getCarSeatNumRent())) {
            String[] rentSpaceNum = houseReq.getCarSeatNumRent().split(",");
            String[] rentPlate = houseReq.getPlateNumRent().split(",");
            for (int i = 0; i < rentSpaceNum.length; i++) {
                HouseParkingSpace space = new HouseParkingSpace();
                space.setCreateTime(new Date(System.currentTimeMillis()));
                space.setHouseId(houseId);
                space.setLicensePlate(rentPlate[i]);
                space.setSpaceNum(rentSpaceNum[i]);
                space.setSpaceType(HouseDataValidation.RENT_PARKING_SPACE);
                list.add(space);
            }
        }
        if (list.size() > 0) {
            spaceMapper.insertList(list);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========HouseServiceImpl addHouse end=========");
        }
    }

    @Override
    public Building findByBuildingName(String buildingName,String estateId) {
        return buildingMapper.selectByBuildingName(buildingName,estateId);
    }

    @Override
    public List<BuildingUnit> findByUnitNameAndBuildingId(String unitName, Long buildingId) {
        return buildingUnitMapper.selectByUnitNameAndBuildingId(unitName, buildingId);
    }

    @Override
    public HouseType findByTypeName(String typeName) {
        return houseTypeMapper.findHouseTypeByTypeName(typeName);
    }

    @Override
    public House findByHouseId(String houseId) {
        return houseMapper.selectByPrimaryKey(houseId);
    }

    @Override
    public HouseInformation findHouseInformationByHouseId(String houseId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========HouseServiceImpl findHouseInformationByHouseId start=========houseId=" + houseId);
        }
        HouseInformation info = houseMapper.selectHouseInformationByHouseId(houseId);
        if (null != info) {
            //添加车位信息
            int buyCounts = 0;
            int rentCounts = 0;
            List<String> carSeatNumBuy = new ArrayList<>();
            List<String> plateNumBuy = new ArrayList<>();
            List<String> carSeatNumRent = new ArrayList<>();
            List<String> plateNumRent = new ArrayList<>();
            List<HouseParkingSpace> list = spaceMapper.findSpacesByHouseId(houseId);
            if (null != list && list.size() > 0) {
                for (HouseParkingSpace space :
                        list) {
                    if (HouseDataValidation.BUY_PARKING_SPACE.equals(space.getSpaceType())) {
                        carSeatNumBuy.add(space.getSpaceNum());
                        plateNumBuy.add(space.getLicensePlate());
                        buyCounts++;
                    } else {
                        carSeatNumRent.add(space.getSpaceNum());
                        plateNumRent.add(space.getLicensePlate());
                        rentCounts++;
                    }
                }
            }
            info.setBuyPark(buyCounts);
            info.setRentalPark(rentCounts);
            info.setCarSeatNumBuy(carSeatNumBuy);
            info.setCarSeatNumRent(carSeatNumRent);
            info.setPlateNumBuy(plateNumBuy);
            info.setPlateNumRent(plateNumRent);
            //户主成员信息
            List<MemberInfo> memberInfos = new ArrayList<>();
            MemberInfo owner = houseOwnerMapper.findInfoByHouseId(houseId);
            if (null != owner) {
                memberInfos.add(owner);
            }
            List<MemberInfo> members = houseMemberMapper.findInfoByHouseId(houseId);
            if (null != members && members.size() > 0) {
                for (MemberInfo in :
                        members) {
                    memberInfos.add(in);
                }
            }
            //租户信息
            List<MemberInfo> tenants = tenantMapper.findInfoByHouseId(houseId);
            if (null != tenants && tenants.size() > 0) {
                for (MemberInfo in :
                        tenants) {
                    memberInfos.add(in);
                }
            }
            info.setMemberInfoList(memberInfos);
            //装修申请
            List<DecorationApply> apply = applyMapper.findByHouseId(houseId);
            if (null != apply) {
                info.setDecorationApply(apply);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========HouseServiceImpl findHouseInformationByHouseId end=========HouseInformation=" + info);
        }
        return info;
    }

    @Override
    public void deleteHouseByHouseIds(String houseIds,HttpServletRequest request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl deleteHouseByHouseIds start========houseIds=" + houseIds);
        }
        String[] stringArray = houseIds.split(",");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========houseIds to stringArray========" + stringArray);
        }
        //删除成员认证列表
        List<Long> authList = new ArrayList<>();
        //删认证通知列表
        List<Long> noticeList = new ArrayList<>();
        //删除图片列表
        List<String> pictureList = new ArrayList<>();
        //删除维修单列表
        List<String> repairList = new ArrayList<>();
        //删除维修评价单列表
        List<Long> replyList = new ArrayList<>();
        //删除维修图片列表
        List<Long> repairPicList = new ArrayList<>();
        //删除车位列表
        List<String> spaceList = new ArrayList<>();
        //删除房屋户主信息列表
        List<String> ownerList = new ArrayList<>();
        //删除房屋成员信息列表
        List<String> memberList = new ArrayList<>();
        //创建系统通知对象，将通知存到数据库
        Notice notice = new Notice();
        notice.setNoticeStatus(1);
        notice.setNoticeType(Integer.parseInt(HouseDataValidation.SEND_NO));
        notice.setCreateTime(new Date(System.currentTimeMillis()));
        for (int i = 0; i < stringArray.length; i++) {
            //查询该房屋信息，用于通知
            HouseInformation info = houseMapper.selectHouseInformationByHouseId(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHouseInformationByHouseId========" + info);
            }
            House house = houseMapper.selectByPrimaryKey(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHouseByHouseId========" + house);
            }
            //查询房屋户主
            HouseOwner houseOwner = houseOwnerMapper.selectByHouseId(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHouseOwnerByHouseId========" + houseOwner);
            }
            if (houseOwner != null) {
                ownerList.add(houseOwner.getOwnerId());
            }
            //查询房屋成员
            List<HouseMember> houseMembers = houseMemberMapper.selectHouseMemberByHouseId(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHouseMemberByHouseId========" + houseMembers);
            }
            if (houseMembers != null && houseMembers.size() > 0) {
                for (HouseMember m :
                        houseMembers) {
                    memberList.add(m.getMemberId());
                }
            }
            //删除车位列表添加数据
            spaceList.add(stringArray[i]);
            //查询所有维修单
            List<RepairOrder> orders = orderMapper.findByHouseId(stringArray[i]);
            if (orders != null && orders.size() > 0) {
                for (RepairOrder order
                        : orders) {
                    repairList.add(order.getOrderNo());
                    if (order.getOrderPic() != null) {
                        pictureList.add(order.getOrderPic());
                        //删除物理路径
                        Pic picture = pictureMapper.selectByPictureId(order.getOrderPic());
                        FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + picture.getPictureUrl());
                    }
                    //查询评价
                    List<RepairOrderReply> replies = replyMapper.findByOrderNo(order.getOrderNo());
                    if (replies != null && replies.size() > 0) {
                        for (RepairOrderReply reply :
                                replies) {
                            replyList.add(reply.getReplyId());
                        }
                    }
                    //查询图片
                    List<RepairOrderPic> pics = orderPicMapper.findByOrderNo(order.getOrderNo());
                    if (pics != null && pics.size() > 0) {
                        for (RepairOrderPic pic :
                                pics) {
                            repairPicList.add(pic.getOpId());
                            pictureList.add(pic.getPictureId());
                            //删除物理路径
                            Pic picture = pictureMapper.selectByPictureId(pic.getPictureId());
                            FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + picture.getPictureUrl());
                        }
                    }
                }
            }
            //查询所有认证
            List<MemberAuth> auths = memberAuthMapper.selectByHouseId(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findMemberAuthByHouseId========" + auths);
            }
            if (auths != null) {
                for (MemberAuth auth :
                        auths) {
                    //删房屋认证列表添加数据
                    authList.add(auth.getAuthId());
                    //删认证通知列表添加数据
                    noticeList.add(auth.getAuthId());
                }
            }
            //查询所有图纸
            List<HousePic> housePicList = housePicMapper.selectByHouseId(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHousePicByHouseId========" + housePicList);
            }
            for (HousePic housePic :
                    housePicList) {
                if (null != housePic.getCadId()) {
                    //删除图片列表添加数据
                    pictureList.add(housePic.getCadId());
                    //删除物理路径
                    Pic pic = pictureMapper.selectByPictureId(housePic.getCadId());
                    FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + pic.getPictureUrl());
                }
                if (null != housePic.getPictureId()) {
                    //删除图片列表添加数据
                    pictureList.add(housePic.getPictureId());
                    //删除物理路径
                    Pic pic = pictureMapper.selectByPictureId(housePic.getPictureId());
                    FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + pic.getPictureUrl());
                }
            }
            //发送通知给户主和成员
            if (info != null) {
                AppHouseInfo appInfo = new AppHouseInfo();
                appInfo.setSendType(HouseDataValidation.SEND_TYPE);
                appInfo.setSendNo(HouseDataValidation.SEND_NO);
                appInfo.setSendTitle("系统删除房屋");
                if (info.getBuildingName() != null && info.getUnitName() != null) {
                    appInfo.setSendContent(info.getBuildingName() + info.getUnitName() + info.getHouseNum() + "房屋已被删除！");
                    notice.setNoticeContent(appInfo.getSendContent());
                } else if (info.getBuildingName() != null && info.getUnitName() == null) {
                    appInfo.setSendContent(info.getBuildingName() + info.getHouseNum() + "房屋已被删除！");
                    notice.setNoticeContent(appInfo.getSendContent());
                } else if (info.getBuildingName() == null && info.getUnitName() == null) {
                    appInfo.setSendContent(info.getHouseNum() + "房屋已被删除！");
                    notice.setNoticeContent(appInfo.getSendContent());
                }
                //消息组件发送，发送给该房屋的户主和成员
                List<String> list = new ArrayList<>();
                //系统通知列表
                List<Notice> notices = new ArrayList<>();
                //查询户主账号
                if (houseOwner != null){
                    UserAccount userAccounOwner = userService.findByUserId( houseOwner.getUserId(),request);
                    userAccounOwner.setAcctName(userAccounOwner.getAcctName());
                    userAccounOwner.setUserId(userAccounOwner.getUserId());
                    //httpClient请求
                    if (!StringUtil.isBlank(houseOwner.getUserId())
                            && userAccounOwner != null) {
                        list.add(userAccounOwner.getAcctName());
                        notice.setNoticeUserId(userAccounOwner.getUserId());
                        notices.add(notice);
                    }
                }
                if (houseMembers != null) {
                    for (HouseMember me : houseMembers) {
                        //查询成员账号
                        UserAccount userAccounMember = userService.findByUserId(me.getUserId(),request);
                        if (!StringUtil.isBlank(me.getUserId()) && userAccounMember != null) {
                            list.add(userAccounMember.getAcctName());
                            notice.setNoticeUserId(userAccounMember.getUserId());
                            notices.add(notice);
                        }
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========deleteHouseByHouseIds========notices=" + notices);
                }
                if (notices.size() > 0) {
                    noticeMapper.addNotice(notices);
                    //获取主键存入推送模型
                    appInfo.setSendId(notices.get(0).getNoticeId());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========sendMessage========" + appInfo);
                    }
                    //转json
                    String pushInfo = GsonUtil.object2Gson(appInfo);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========send users========" + list);
                        LOG.debug("========sendMessage to json========" + pushInfo);
                    }
                    //群推
                    com.betel.estatemgmt.common.msgpush.MsgPushUtils.pushList(list, pushInfo);
                }
            }
        }
        //删车位
        if (spaceList.size() > 0) {
            spaceMapper.deleteByHouseIds(spaceList);
        }
        //删除维修单
        if (repairList.size() != 0) {
            orderMapper.deleteOrderList(repairList);
        }
        //删除维修评价
        if (replyList.size() != 0) {
            replyMapper.deleteReplyList(replyList);
        }
        //删除维修图片
        if (repairPicList.size() != 0) {
            orderPicMapper.deletePicList(repairPicList);
        }
        //删除成员认证
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteHouseByHouseIds========authList=" + authList);
        }
        if (authList.size() != 0) {
            memberAuthMapper.deleteAuthList(authList);
        }
        //删除认证通知
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteHouseByHouseIds========noticeList=" + noticeList);
        }
        if (noticeList.size() != 0) {
            houseAuthNoticeMapper.deleteNoticeList(noticeList);
        }
        //删房屋成员
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteHouseMember========memberList=" + memberList);
        }
        if (memberList.size() > 0) {
            houseMemberMapper.deleteMemberList(memberList);
        }
        //删除房屋成员关系
        memberRelaMapper.deleteRelaList(stringArray);
        //删除房屋户主
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteHouseOwner========ownerList=" + ownerList);
        }
        if (ownerList.size() > 0) {
            houseOwnerMapper.deleteOwnerList(ownerList);
        }
        //删除房屋户主关系
        ownerRelaMapper.deleteRelaList(stringArray);
        //删除图片
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteHouseByHouseIds========pictureList=" + pictureList);
        }
        if (pictureList.size() != 0) {
            pictureMapper.deletePicList(pictureList);
        }
        //删房屋图纸
        housePicMapper.deleteByHouseIds(stringArray);
        //删装修申请
        applyMapper.deleteByHouseIds(stringArray);
        //删房屋
        houseMapper.deleteByHouseIds(stringArray);
        //解绑网关和房屋
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("houseIds", houseIds);
        try {
            HttpClientUtil.post(ConfigManager.read(ConfigName.HOME_PROJECT_URL) + "httpclientHome/device/v1/deleteRela", jsonParam, request);
        }catch (Exception e){
            LOG.error("解绑网关和房屋失败");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl deleteHouseByHouseIds end========");
        }
    }

    @Override
    public String updateHouse(House house, UpdateHouseReq req) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateHouse start========house=" + house + ",UpdateHouseReq" + req);
        }
        houseMapper.updateHouse(house);
        //产权车位数
        Integer buyCounts = Integer.parseInt(req.getBuyPark());
        //租赁车位数
        Integer rentCounts = Integer.parseInt(req.getRentalPark());
        //先删除所有车位，再添加
        spaceMapper.deleteByHouseId(house.getHouseId());
        //创建车位对象
        List<HouseParkingSpace> list = new ArrayList<>();
        if (buyCounts > 0) {
            if (!Validate.isSearchNum(req.getBuyPark(), HouseDataValidation.BUY_PARKING_SIZE)) {
                //产权车位数过多
                return HouseCode.BUY_PARKING_SIZE;
            }
            for (int i = 0; i < buyCounts; i++) {
                String[] buySpaceNum = req.getCarSeatNumBuy().split(",");
                String[] buyPlate = req.getPlateNumBuy().split(",");
                //校验车位车牌
                if (!Validate.isCommonString(buySpaceNum[i], HouseDataValidation.SPACE_NUM_LENGTH)) {
                    //产权车位号字符错误
                    return HouseCode.SPACE_NUM_RULE;
                }
                if (null != spaceMapper.selectByPrimaryKey(buySpaceNum[i])) {
                    //产权车位号已存在
                    return HouseCode.BUY_PARKING_EXIST;
                }
                if (!Validate.isCommonString(buyPlate[i], HouseDataValidation.LICENSE_PLATE_LENGTH)) {
                    //产权车牌号字符错误
                    return HouseCode.LICENSE_PLATE_RULE;
                }
                HouseParkingSpace space = new HouseParkingSpace();
                space.setCreateTime(new Date(System.currentTimeMillis()));
                space.setHouseId(req.getHouseId());
                space.setSpaceType(HouseDataValidation.BUY_PARKING_SPACE);
                space.setSpaceNum(buySpaceNum[i]);
                space.setLicensePlate(buyPlate[i]);
                list.add(space);
            }
        }
        if (rentCounts > 0) {
            if (!Validate.isSearchNum(req.getRentalPark(), HouseDataValidation.BUY_PARKING_SIZE)) {
                //租赁车位数过多
                return HouseCode.RENT_PARKING_SIZE;
            }
            for (int i = 0; i < rentCounts; i++) {
                String[] rentSpaceNum = req.getCarSeatNumRent().split(",");
                String[] rentPlate = req.getPlateNumRent().split(",");
                //校验车位车牌
                if (!Validate.isCommonString(rentSpaceNum[i], HouseDataValidation.SPACE_NUM_LENGTH)) {
                    //产权车位号字符错误
                    return HouseCode.SPACE_NUM_RULE;
                }
                if (null != spaceMapper.selectByPrimaryKey(rentSpaceNum[i])) {
                    //产权车位号已存在
                    return HouseCode.BUY_PARKING_EXIST;
                }
                if (!Validate.isCommonString(rentPlate[i], HouseDataValidation.LICENSE_PLATE_LENGTH)) {
                    //产权车牌号字符错误
                    return HouseCode.LICENSE_PLATE_RULE;
                }
                HouseParkingSpace space = new HouseParkingSpace();
                space.setCreateTime(new Date(System.currentTimeMillis()));
                space.setHouseId(req.getHouseId());
                space.setSpaceType(HouseDataValidation.RENT_PARKING_SPACE);
                space.setSpaceNum(rentSpaceNum[i]);
                space.setLicensePlate(rentPlate[i]);
                list.add(space);
            }
        }
        //批量插入车位信息
        if (list.size() > 0) {
            spaceMapper.insertList(list);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateHouse end========");
        }
        return null;
    }

    @Override
    public List<PictureInfo> findPictureType() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl findPictureType start========");
        }
        List<PictureInfo> list = housePicTypeMapper.findPictureType();
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl findPictureType end========List<PictureInfo>=" + list);
        }
        return list;
    }

    @Override
    public String addPicture(Picture picture) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl addPicture start=========picture=" + picture);
        }
        pictureMapper.insertSelective(picture);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl addPicture end=========picture=" + picture);
        }
        return picture.getPictureId();
    }

    @Override
    public HousePic findHousePicByHouseIdAndPictureType(UploadPictureReq uploadPictureReq) {
        return housePicMapper.findHousePicByHouseIdAndPictureType(uploadPictureReq);
    }

    @Override
    public void updateHousePicture(HousePic housePic) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateHousePicture start=========housePic=" + housePic);
        }
        housePicMapper.updateByPrimaryKeySelective(housePic);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateHousePicture end=========");
        }
    }

    @Override
    public void insertHousePicture(HousePic housePicture) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl insertHousePicture start=========housePicture=" + housePicture);
        }
        housePicMapper.insertSelective(housePicture);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl insertHousePicture end=========");
        }
    }

    @Override
    public HousePic findHousePicByHpId(PicReq deletePicReq) {
        return housePicMapper.selectByPrimaryKey(deletePicReq.getHpId());
    }

    @Override
    public void updateHousePic(PicReq deletePicReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateHousePic start=========deletePicReq=" + deletePicReq);
        }
        //deletePic(deletePicReq);
        housePicMapper.updateHousePic(deletePicReq);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateHousePic end=========");
        }
    }

    @Override
    public void deleteHousePic(PicReq deletePicReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl deleteHousePic start=========deletePicReq=" + deletePicReq);
        }
        //deletePic(deletePicReq);
        housePicMapper.deleteByPrimaryKey(deletePicReq.getHpId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl deleteHousePic end=========");
        }
    }

    //删除图片表及物理文件
    private void deletePic(PicReq deletePicReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl deletePic start========deletePicReq=" + deletePicReq);
        }
        HousePic housePic = housePicMapper.selectByPrimaryKey(deletePicReq.getHpId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("========findHousePicByHousePicId========housePic=" + housePic);
        }
        String fileId;
        if (null != housePic) {
            //判断删除预览图还是cad
            if (0 == deletePicReq.getPictureFlag()) {
                fileId = housePic.getPictureId();
            } else {
                fileId = housePic.getCadId();
            }
            Pic pic = pictureMapper.selectByPictureId(fileId);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findPicByPicId========pic=" + pic);
            }
            if (pic != null) {
                //删除物理文件
                FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + pic.getPictureUrl());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("========deletePic========pictureId=" + fileId);
            }
            //删除图片表记录
            pictureMapper.deleteByPrimaryKey(fileId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl deletePic end========");
        }
    }

    @Override
    public Pic findPicById(String fileId) {
        return pictureMapper.selectByPictureId(fileId);
    }

    @Override
    public PictureInfo findByHouseIdAndTypeName(String houseId, String typeName) {
        return housePicMapper.findByHouseIdAndTypeName(houseId, typeName);
    }

    @Override
    public List<HouseTypeResp> findHouseTypes() {
        return houseTypeMapper.selectHouseTypeResp();
    }

    @Override
    public void addHouseList(List<House> houseList) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl addHouseList start========houseList=" + houseList);
        }
        houseMapper.insertHouseList(houseList);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl addHouseList end========houseList=" + houseList);
        }
    }

    @Override
    public House findByHouseReq(UpdateHouseReq houseReq) {
        return houseMapper.selectByHouseReq(houseReq);
    }

    @Override
    public void updateDeliverTime(UpdateDeliverTimeReq req) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateDeliverTime start========UpdateDeliverTimeReq=" + req);
        }
        String[] stringArray = req.getHouseIds().split(",");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========houseIds to stringArray========" + stringArray);
        }
        houseMapper.updateDeliverTime(stringArray, req.getDeliverTime());
        if (LOG.isDebugEnabled()) {
            LOG.debug("========HouseServiceImpl updateDeliverTime end========");
        }
    }

    @Override
    public HousePicType findByHouseTypeName(String typeName) {
        return housePicTypeMapper.selectByPrimaryKey(typeName);
    }

    @Override
    public void insertPicture(Picture picture) {
        pictureMapper.insertSelective(picture);
    }

    @Override
    public void insertHousePicList(List<HousePic> pics) {
        housePicMapper.insertPicList(pics);
    }

    @Override
    public void updateHousePicList(List<HousePic> pics) {
        housePicMapper.updatePicList(pics);
    }

    @Override
    public HouseParkingSpace findBySpaceNum(String spaceNum) {
        return spaceMapper.selectByPrimaryKey(spaceNum);
    }

    @Override
    public void deleteOwnerMemberByUserId(String[] userIds, HttpServletRequest request) throws Exception {
        ArrayList<Notice> noticeList = new ArrayList<>();
        List<Members> membersList = new ArrayList<>();
        List<String> memberIds = new ArrayList<>();
        List<String> ownerIds = new ArrayList<>();
        List<Long> authList = new ArrayList<>();

        // 查询未失效的成员
        List<Members> membersList1 = houseMemberMapper.selectByUserIds(userIds);
        if (membersList1 != null && membersList1.size() > 0) {
            for (int i = 0; i < membersList1.size(); i++) {
                memberIds.add(membersList1.get(i).getMemberId());
            }
        }

        //查询成员认证信息
        List<MemberAuth> memberAuthList = memberAuthMapper.selectByUserId(userIds);
        if (memberAuthList != null && memberAuthList.size() > 0) {
            for (MemberAuth au :
                    memberAuthList) {
                authList.add(au.getAuthId());
            }
        }

        //查询未失效的户主
        List<Members> membersList2 = houseOwnerMapper.selectByUserIds(userIds);
        if (membersList2 != null && membersList2.size() > 0) {
            for (Members m :
                    membersList2) {
                ownerIds.add(m.getOwnerId());
                //判断户主激活状态
                //已激活，则删除该房屋下的成员
                if (m.getOwnerStatus() != null && m.getOwnerStatus().equals(Quantity.TWO)) {
                    List<Members> memberList3 = houseMemberMapper.findMembersByHouseId(m.getHouseId());
                    if (memberList3 != null && memberList3.size() > 0) {
                        String[] userId = new String[memberList3.size()];
                        for (int i = 0; i < memberList3.size(); i++) {
                            membersList.add(memberList3.get(i));
                            memberIds.add(memberList3.get(i).getMemberId());
                            userId[i] = memberList3.get(i).getUserId();
                        }
                        if (userId.length > 0) {
                            //查询成员认证信息
                            List<MemberAuth> memberAuthList1 = memberAuthMapper.selectByUserId(userId);
                            if (memberAuthList1 != null && memberAuthList1.size() > 0) {
                                for (MemberAuth au :
                                        memberAuthList1) {
                                    authList.add(au.getAuthId());
                                }
                            }
                        }
                    }
                }
            }
        }

        if (memberIds.size() > 0) {
            //删除成员关系表
            memberRelaMapper.deleteByMemberIds(memberIds);
            //删成员信息表
            houseMemberMapper.deleteMemberList(memberIds);
        }
        if (ownerIds.size() > 0) {
            //删除户主关系表
            ownerRelaMapper.deleteByOwnerIds(ownerIds);
            //删户主信息表
            houseOwnerMapper.deleteOwnerList(ownerIds);
        }
        if (authList.size() > 0) {
            //删除成员认证表
            memberAuthMapper.deleteAuthList(authList);
            //删除成员认证通知表
            authNoticeMapper.deleteNoticeList(authList);
        }

        if (membersList.size() > 0) {
            for (Members members : membersList) {
                String content = members.getHouseNum() + "房屋户主已被删除，系统自动解除房屋与成员关系！";
                // 入通知表
                Notice notice = new Notice();
                notice.setNoticeUserId(members.getUserId());
                notice.setNoticeContent(content);
                //认证状态:1 未读
                notice.setNoticeStatus(HouseDataValidation.NOTICESTATUS);
                //通知类型：系统移除房屋成员：用户被管理员取消禁言
                notice.setNoticeType(HouseDataValidation.NOTICETYPE);
                notice.setCreateTime(new Date());
                noticeList.add(notice);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========addNotice========noticeList=" + noticeList);
        }
        if (noticeList != null && noticeList.size() != 0) {
            noticeMapper.addNotice(noticeList);
        }
        if (membersList != null && membersList.size() != 0) {
            for (int i = 0; i < membersList.size(); i++) {
                Members members = membersList.get(i);
                String content = members.getHouseNum() + "房屋户主已被删除，系统自动解除房屋与成员关系！";
                // 通知申请人
                SendMessage sendMessage = new SendMessage();
                //删除成员发送通知类型
                sendMessage.setSendType(HouseDataValidation.SENDTYPE);
                //删除户主成员被移除发送通知编号
                sendMessage.setSendNo(HouseDataValidation.MEMBERSEND);
                sendMessage.setSendContent(content);
                sendMessage.setSendId(noticeList.get(i).getNoticeId().toString());
                sendMessage.setSendTitle(HouseDataValidation.SENDTITLE);
                String send = GsonUtil.object2Gson(sendMessage);
                UserAccount userAccount = userService.findByUserId(members.getUserId(),request);
                if (null != userAccount) {
                    MsgPushUtils.push(userAccount.getAcctName(), send);
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteUser  deleteHouse  end========");
        }
    }

    @Override
    public List<House> findHouseByUserId(String userId) {

        return null;
    }

}

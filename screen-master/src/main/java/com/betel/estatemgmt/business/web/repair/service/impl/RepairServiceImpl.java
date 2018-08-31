package com.betel.estatemgmt.business.web.repair.service.impl;

import com.betel.estatemgmt.business.smartre.user.model.UserReq;
import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.propertyapp.assign.constant.AssignConstant;
import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.web.ad.model.Pic;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.code.RepairCode;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.repair.model.*;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.business.web.repair.util.RepairUtil;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.admin.AdminMapper;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.property.PropertyConfigMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairHistoryMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderPicMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderTypeMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.property.PropertyConfig;
import com.betel.estatemgmt.common.model.repair.RepairHistory;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.repair.RepairOrderPic;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.betel.estatemgmt.shiro.ActiveUser;

import javax.servlet.http.HttpServletRequest;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * 维修管理接口实现类
 * </p>
 * ClassName: RepairServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:10 <br/>
 * Version: 1.0 <br/>
 */
@Service("RepairService")
@Transactional
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairOrderMapper orderMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BuildingUnitMapper unitMapper;

    @Autowired
    private RepairOrderTypeMapper typeMapper;

    @Autowired
    private RepairOrderPicMapper picMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private PropertyConfigMapper configMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private RepairHistoryMapper repairHistoryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    OfficeHouseMapper officeHouseMapper;

    @Autowired
    EstateMapper estateMapper;

    private static final Logger LOG = LoggerFactory.getLogger(RepairServiceImpl.class);

    @Override
    public List<OrderInfo> findAllOrders(Paging<OrderInfo> pager, RepairPageReq repairPageReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl findAllOrders start=========Paging<Income>=" + pager
                    + ",repairPageReq=" + repairPageReq);
        }
        List<OrderInfo> list = null;
        Estate estate = estateMapper.selectByPrimaryKey(repairPageReq.getEstateId());
        if (null != estate){
            //小区楼盘
            if (estate.getEstateType().equals(RepairDataValidation.PROPERTY_ESTATE_TYPE)){
                list = orderMapper.findAllHouseOrders(pager.getRowBounds(), repairPageReq);
                //添加房屋信息
                if (list != null && list.size() > 0) {
                    for (OrderInfo info :
                            list) {
                        if (!StringUtil.isBlank(info.getHouseNo())) {
                            info.setHouseNo(makeHouseNo(info.getHouseNo(),repairPageReq.getEstateId()));
                        }
                    }
                }
            }else {
                //写字楼
                list = orderMapper.findAllOfficeOrders(pager.getRowBounds(), repairPageReq);
                //添加房屋信息
                if (list != null && list.size() > 0) {
                    for (OrderInfo info :
                            list) {
                        if (!StringUtil.isBlank(info.getHouseNo())) {
                            info.setHouseNo(makeHouseNo(info.getHouseNo(),repairPageReq.getEstateId()));
                        }
                    }
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl findAllOrders end=========List<Income>=" + list);
        }
        return list;
    }

    @Override
    public RepairOrder findByOrderNo(String orderNo) {
        return orderMapper.selectByPrimaryKey(orderNo);
    }

    @Override
    public OrderPic findPicByOrderNo(String orderNo) {
        return orderMapper.selectPicByOrderNo(orderNo);
    }

    @Override
    public boolean findByHouseId(String houseId,String estateId) {
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if (null != estate){
            //小区楼盘
            if (estate.getEstateType().equals(RepairDataValidation.PROPERTY_ESTATE_TYPE)){
                if (null == houseMapper.selectByPrimaryKey(houseId)){
                    return false;
                }
            }else {
                //写字楼
                if (null == officeHouseMapper.selectByPrimaryKey(houseId)){
                    return false;
                }
            }
        }else {
            return false;
        }
        return true;
    }

    @Override
    public void addRepairOrder(AddOrderReq orderReq,HttpServletRequest request) throws Exception {
        //新增order
        RepairOrder order = new RepairOrder();
        String orderNo = RepairUtil.getFlowNo();
        order.setOrderNo(orderNo);
        order.setOrderContact(orderReq.getOrderContact());
        order.setContactPhone(orderReq.getContactPhone());
        order.setOrderArea(orderReq.getOrderArea());
        order.setHouseId(orderReq.getHouseId());
        order.setOrderType(orderReq.getOrderType());
        order.setOrderDesc(orderReq.getOrderDesc());
        order.setOrderStatus(RepairDataValidation.ORDER_INORDER_STATUS);
        order.setOperatorId(orderReq.getOperatorId());
        order.setEstateId(orderReq.getEstateId());
        if (!(StringUtil.isBlank(orderReq.getAppointTime()))) {
            order.setAppointTime(DateUtil.toDate(orderReq.getAppointTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setAcceptTime(new Date(System.currentTimeMillis()));
        order.setUrgeCount(0);
        orderMapper.insertSelective(order);
        //新增报修单记录
        RepairHistory history = new RepairHistory();
        Subject subject = SecurityUtils.getSubject();
        //jians.z 2018-1-28
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null != activeUser) {
            history.setOperatorId(activeUser.getUserId());
            history.setOperatorName(activeUser.getAdminName());
        }
        history.setOrderNo(orderNo);
        history.setOperatorType(AssignConstant.OPERATOR_TYPE_WEB);
        history.setHistoryType(AssignConstant.ORDER_ADD);
        history.setCreateTime(new Date());
        history.setHistoryDesc("新增维修单");
        repairHistoryMapper.insertSelective(history);

        //指派操作记录
        RepairHistory repairHistory = new RepairHistory();
        repairHistory.setOrderNo(orderNo);
        //jians.z 2018-1-28
        if (null != activeUser) {
            repairHistory.setOperatorId(activeUser.getUserId());
            repairHistory.setOperatorName(activeUser.getAdminName());
        }
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_WEB);
        repairHistory.setHistoryType(AssignConstant.ORDER_ASSIGN);
        repairHistory.setReceiver(orderReq.getOperatorId());
        repairHistory.setReceiverPhone(orderReq.getOperatorPhone());
        repairHistory.setReceiverHead(orderReq.getOperatorHead());
        repairHistory.setReceiverDepartment(orderReq.getOperatorDepartment());
        repairHistory.setReceiverName(orderReq.getOperatorName());
        repairHistory.setCreateTime(new Date());
        repairHistory.setHistoryDesc("指派给:" + orderReq.getOperatorDepartment() + "-" + orderReq.getOperatorName());
        repairHistoryMapper.insertSelective(repairHistory);
        //给被指派人员推送通知--------------------
        SendMessage send = new SendMessage();
        send.setSendId(orderReq.getOperatorId());
        send.setSendTitle(RepairDataValidation.ADD_REPAIR_SENDTITLE);
        send.setSendNo(RepairDataValidation.ADD_REPAIR_SENDNO);
        send.setSendType(RepairDataValidation.PUSH_SENDTYPE);
        send.setSendContent(RepairDataValidation.ADD_REPAIR_SENDCONTENT);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage========" + send);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage to json========" + pushInfo);
        }
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("empId", orderReq.getOperatorId());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        String phone = null;
        if (null != response && null != response.getData()) {
            JSONObject employee = JSONObject.fromObject(response.getData());
            phone = employee.get("phoneNum").toString();
        }
        if (!StringUtil.isBlank(phone)) {
            //推送
            PropertyPushUtil.push(phone, pushInfo);
        }
    }

    @Override
    public List<TypeInfo> findByArea(Integer orderArea,Integer estateType) {
        return typeMapper.selectByArea(orderArea,estateType);
    }

    @Override
    public List<HouseInfo> findByIds(String buildingId, String unitId,String estateId) {
        List<HouseInfo> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(RepairDataValidation.PROPERTY_ESTATE_TYPE.equals(estate.getEstateType())){
            list = houseMapper.selectByIds(buildingId, unitId);
        }else {
            list = officeHouseMapper.selectByIds(buildingId, unitId);
        }
        return list;
    }

    @Override
    public OrderDetail findDetailByOrderNo(String orderNo,String estateId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl findDetailByOrderNo start=========orderNo=" + orderNo);
        }
        //查询维修单
        OrderDetail orderDetail = orderMapper.selectDetailByOrderNo(orderNo);
        if (orderDetail != null && !StringUtil.isBlank(orderDetail.getHouseNo())) {
            orderDetail.setHouseNo(makeHouseNo(orderDetail.getHouseNo(),estateId));
        }
        List<String> pictureUrl = new ArrayList<>();
        //查询维修单图片
        List<RepairOrderPic> orderPics = picMapper.findByOrderNo(orderNo);
        if (orderPics != null && orderPics.size() > 0) {
            for (RepairOrderPic pic :
                    orderPics) {
                Pic picture = pictureMapper.selectByPictureId(pic.getPictureId());
                if (picture != null) {
                    pictureUrl.add(RepairDataValidation.FILE_SERVER_PATH + picture.getPictureUrl());
                }
            }
        }
        if (orderDetail != null) {
            orderDetail.setPictureUrl(pictureUrl);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl findDetailByOrderNo end=========orderDetail=" + orderDetail);
        }
        return orderDetail;
    }

    @Override
    public void updateOrder(UpdateOrderReq orderReq,HttpServletRequest request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl updateOrder start=========UpdateOrderReq=" + orderReq);
        }
        //修改维修单
        RepairOrder order = new RepairOrder();
        order.setOrderNo(orderReq.getOrderNo());
        order.setOperatorId(orderReq.getOperatorId());
        order.setOrderStatus(RepairDataValidation.ORDER_INORDER_STATUS);
        order.setAcceptTime(new Date(System.currentTimeMillis()));
        orderMapper.updateByPrimaryKeySelective(order);
        //指派操作记录
        RepairHistory repairHistory = new RepairHistory();
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null != activeUser) {
            repairHistory.setOperatorId(activeUser.getUserId());
            repairHistory.setOperatorName(activeUser.getAdminName());
        }
        repairHistory.setOrderNo(orderReq.getOrderNo());
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_WEB);
        repairHistory.setHistoryType(AssignConstant.ORDER_ASSIGN);
        repairHistory.setReceiver(orderReq.getOperatorId());
        repairHistory.setReceiverPhone(orderReq.getOperatorPhone());
        repairHistory.setReceiverHead(orderReq.getOperatorHead());
        repairHistory.setReceiverDepartment(orderReq.getOperatorDepartment());
        repairHistory.setReceiverName(orderReq.getOperatorName());
        repairHistory.setCreateTime(new Date());
        repairHistory.setHistoryDesc("指派给:" + orderReq.getOperatorDepartment() + "-" + orderReq.getOperatorName());
        repairHistoryMapper.insertSelective(repairHistory);

        //查询维修单数据
        OrderDetail orderDetail = orderMapper.selectDetailByOrderNo(orderReq.getOrderNo());
        if (orderDetail != null && !StringUtil.isBlank(orderDetail.getHouseNo())) {
            orderDetail.setHouseNo(makeHouseNo(orderDetail.getHouseNo(), AESUtil.decrypt(request.getHeader("estateId"))));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl selectDetailByOrderNo========orderDetail=" + orderDetail);
        }
        RepairOrder repairOrder = orderMapper.selectByPrimaryKey(orderReq.getOrderNo());
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl selectByPrimaryKey========repairOrder=" + repairOrder);
        }
        if (repairOrder != null) {
            //判断用户是否被删除----------- jian.z 2018-1-21
            UserReq userReq = new UserReq();
            userReq.setUserId(repairOrder.getUserId());
            if (userService.findUser(userReq,request) != null) {
                //发通知
                //创建系统通知对象，将通知存到数据库
                Notice notice = new Notice();
                notice.setNoticeStatus(1);
                notice.setNoticeType(Integer.parseInt(RepairDataValidation.ORDER_SENDNO));
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeUserId(repairOrder.getUserId());
                JSONObject employee = validateOperatorExist(orderReq.getOperatorId(),request);
                String content = null;
                String[] params = null;
                if (repairOrder.getOrderArea().equals(RepairDataValidation.ORDER_AREA_PERSONAL)) {
                    if (StringUtil.isBlank(orderDetail.getOrderDesc())) {
                        content = "尊敬的" + repairOrder.getOrderContact() + "\n您对" + orderDetail.getHouseNo() + "的报修："
                                + orderDetail.getOrderType() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + orderReq.getOperatorPhone();
                        params = new String[]{repairOrder.getOrderContact(),orderDetail.getHouseNo(),
                                orderDetail.getOrderType(),employee.get("employeeName").toString(),
                                orderReq.getOperatorPhone()};
                    } else {
                        content = "尊敬的" + repairOrder.getOrderContact() + "\n您对" + orderDetail.getHouseNo() + "的报修："
                                + orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + orderReq.getOperatorPhone();
                        params = new String[]{repairOrder.getOrderContact(),orderDetail.getHouseNo(),
                                orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc(),
                                employee.get("employeeName").toString(), orderReq.getOperatorPhone()};
                    }
                } else {
                    if (StringUtil.isBlank(orderDetail.getOrderDesc())) {
                        content = "尊敬的" + repairOrder.getOrderContact() + "\n您对公共区域的报修："
                                + orderDetail.getOrderType() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + orderReq.getOperatorPhone();
                        params = new String[]{repairOrder.getOrderContact(),"公共区域",
                                orderDetail.getOrderType(),employee.get("employeeName").toString(),
                                orderReq.getOperatorPhone()};
                    } else {
                        content = "尊敬的" + repairOrder.getOrderContact() + "\n您对公共区域的报修："
                                + orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + orderReq.getOperatorPhone();
                        params = new String[]{repairOrder.getOrderContact(),"公共区域",
                                orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc(),
                                employee.get("employeeName").toString(), orderReq.getOperatorPhone()};
                    }
                }
                notice.setNoticeContent(content);
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
                send.setSendTitle("维修确认");
                send.setSendNo(RepairDataValidation.ORDER_SENDNO);
                send.setSendType(RepairDataValidation.SENDTYPE);
                send.setSendContent(content);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========SendMessage========" + send);
                }
                //转json
                String pushInfo = GsonUtil.object2Gson(send);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========SendMessage to json========" + pushInfo);
                }
                //查询用户账号
//                UserAccount account = userAccountMapper.selectUserPhoneByUserId(repairOrder.getUserId());
                //jianz.z 2018-1-21
                UserAccount account = userService.findByUserId(repairOrder.getUserId(),request);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========findAccountByUserId========" + account);
                }
                if (account != null) {
                    //推送
                    com.betel.estatemgmt.common.msgpush.MsgPushUtils.push(account.getAcctName(), pushInfo);
                    //发短信
                    String[] phones = new String[]{account.getAcctName()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.REPAIR_NOTICE_TEMPLATE,phones,params);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("=========RepairServiceImpl updateOrder end=========");
            }
            //给被指派人员推送通知--------------------
            SendMessage send = new SendMessage();
            send.setSendId(orderReq.getOperatorId());
            send.setSendTitle(RepairDataValidation.ASSIGN_REPAIR_SENDTITLE);
            send.setSendNo(RepairDataValidation.ADD_REPAIR_SENDNO);
            send.setSendType(RepairDataValidation.PUSH_SENDTYPE);
            send.setSendContent(RepairDataValidation.ADD_REPAIR_SENDCONTENT);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========SendMessage========" + send);
            }
            //转json
            String pushInfo = GsonUtil.object2Gson(send);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========SendMessage to json========" + pushInfo);
            }
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("empId", orderReq.getOperatorId());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
            Response response = HttpClientUtil.post(url, jsonParam, request);
            String phone = null;
            if (null != response && null != response.getData()) {
                JSONObject employee = JSONObject.fromObject(response.getData());
                phone = employee.get("phoneNum").toString();
            }
            if (!StringUtil.isBlank(phone)) {
                //推送
                PropertyPushUtil.push(phone, pushInfo);
            }
        }
    }

    @Override
    public void updateCharge(UpdateStandardReq req) {
        PropertyConfig config = new PropertyConfig();
        config.setConfName(req.getConfName());
        config.setConfValue(req.getRepairChargeStandard());
        config.setEstateId(req.getEstateId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl updateCharge=========PropertyConfig=" + config);
        }
        configMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public void uploadPic(Picture pic, UploadOrderPicReq picReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl uploadPic start=========Picture=" + pic + ",picReq=" + picReq);
        }
        //添加图片,返回主键
        pictureMapper.insertSelective(pic);
        String picId = pic.getPictureId();
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========after addPicture=========picId=" + picId);
        }
        RepairOrder order = new RepairOrder();
        order.setOrderNo(picReq.getOrderNo());
        order.setOrderPic(picId);
        order.setOrderStatus(RepairDataValidation.ORDER_FINISH_STATUS);
        order.setDescription(picReq.getDescription());
        order.setFinishTime(new Date(System.currentTimeMillis()));
        //修改维修单
        orderMapper.updateByPrimaryKeySelective(order);
        //完成维修单操作记录
        RepairHistory repairHistory = new RepairHistory();
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        if (null != activeUser) {
            repairHistory.setOperatorId(activeUser.getUserId());
            repairHistory.setOperatorName(activeUser.getAdminName());
        }
        repairHistory.setOrderNo(picReq.getOrderNo());
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_WEB);
        repairHistory.setHistoryType(AssignConstant.ORDER_FINISH);
        repairHistory.setCreateTime(new Date());
        repairHistory.setHistoryDesc("完成维修单");
        repairHistoryMapper.insertSelective(repairHistory);

        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl uploadPic end=========");
        }
    }

    @Override
    public RepairCharge findByConfName(String name,String estateId) {
        return configMapper.selectByConfName(name,estateId);
    }

    @Override
    public OrderInfo findInfoByOrderNo(String orderNo,String estateId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl findInfoByOrderNo start=========orderNo=" + orderNo);
        }
        OrderInfo info = orderMapper.selectInfoByOrderNo(orderNo);
        if (info != null && !StringUtil.isBlank(info.getHouseNo())) {
            info.setHouseNo(makeHouseNo(info.getHouseNo(),estateId));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl findInfoByOrderNo end=========OrderInfo=" + info);
        }
        return info;
    }

    @Override
    public OrderPic uploadEditPic(Picture picture, UploadEditPicReq picReq) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("==========uploadEditPic start===========picture=" + picture + ",UploadEditPicReq=" + picReq);
        }
        //插入到图片表,返回主键
        pictureMapper.insertSelective(picture);
        OrderPic pic = new OrderPic();
        pic.setPicId(picture.getPictureId());
        pic.setPicUrl(RepairDataValidation.FILE_SERVER_PATH + picture.getPictureUrl());
        if (LOG.isDebugEnabled()) {
            LOG.debug("==========uploadEditPic end===========orderPic=" + pic);
        }
        return pic;
    }

    @Override
    public JSONObject validateOperatorExist(String operatorId,HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("executor", operatorId);
        Response response = HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL + "httpclientOA/repair/v1/findExecutorExist", jsonParam, request);
        if (null != response && response.getCode().equals(StatusCode.SUCCESS)) {
            JSONObject json = JSONObject.fromObject(response.getData());
            return json;
        }
        return null;
    }

    @Override
    public String cancelOrder(String orderNo, String userId,HttpServletRequest request) throws Exception {
        RepairOrder order = orderMapper.selectByPrimaryKey(orderNo);
        if (null != order) {
            if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_CANCEL)) {
                return RepairCode.REPAIR_IS_CANCEL;
            }
            if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_END)) {
                return RepairCode.REPAIR_IS_DONE;
            }
        } else {
            return RepairCode.REPAIR_NOT_EXIST;
        }
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setOrderNo(orderNo);
        repairOrder.setCancelTime(new Date());
        repairOrder.setOrderStatus(AssignConstant.REPAIR_CANCEL);
        orderMapper.updateByPrimaryKeySelective(repairOrder);

        //维修取消记录
        RepairHistory repairHistory = new RepairHistory();
        repairHistory.setOrderNo(orderNo);
        repairHistory.setOperatorId(userId);

        Admin admin = adminMapper.selectByPrimaryKey(userId);
        if (null != admin) {
            repairHistory.setOperatorName(admin.getAdminName());
        } else {
            repairHistory.setOperatorName("管理员");
        }
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_WEB);
        repairHistory.setHistoryType(AssignConstant.ORDER_CANCEL);
        repairHistory.setCreateTime(new Date());
        repairHistory.setHistoryDesc("取消维修单");
        repairHistoryMapper.insertSelective(repairHistory);
        //推送通知
//        RepairThread thread = new RepairThread(order,RepairConstant.CANCEL_REPAIR_THREAD);
//        thread.start();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("用户取消报修单（推送给：有报修指派权限的员工和被指派人员）");
                    }
                    //用户取消报修单（推送给：有报修指派权限的员工和被指派人员）
                    JSONObject jsonParam = new JSONObject();
                    Response response = null;
                    try {
                        response = HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL + "httpclientOA/task/v1/findRepairEmps", jsonParam, request);
                    } catch (Exception e) {
                        LOG.error("------push error-----", e);
                    }
                    if (null != response && null != response.getData()) {
                        List<String> empIds = new ArrayList<>();
                        List<String> phones = new ArrayList<>();
                        Type type = new TypeToken<List<FindRepairEmpsResp>>() {
                        }.getType();
                        List<FindRepairEmpsResp> employees = gson.fromJson(response.getData().toString(), type);
                        for (FindRepairEmpsResp employee :
                                employees) {
                            empIds.add(employee.getEmployeeId());
                            phones.add(employee.getPhoneNum());
                        }
                        pushList(empIds, phones,
                                RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT + order.getOrderNo()
                                , Integer.parseInt(RepairConstant.USER_CANCEL_REPAIR_SENDNO)
                                , RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                    }
                    if (!StringUtil.isBlank(order.getOperatorId())) {
                        //用户/物管人员取消已结单报修单（推送给：被指派人员）
                        JSONObject jsonParam1 = new JSONObject();
                        jsonParam1.put("empId", order.getOperatorId());
                        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
                        Response response1 = HttpClientUtil.post(url, jsonParam1, request);
                        String phone = null;
                        String userId = null;
                        if (null != response1 && null != response1.getData()) {
                            JSONObject employee = JSONObject.fromObject(response1.getData());
                            phone = employee.get("phoneNum").toString();
                            userId = employee.get("employeeId").toString();
                        }
                        push(userId, phone, RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT + order.getOrderNo()
                                , Integer.parseInt(RepairConstant.USER_CANCEL_REPAIR_SENDNO)
                                , RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                    }
                } catch (Exception e) {
                    LOG.error("-------cancelRepair push error----------" + e);
                }
            }
        });
        cachedThreadPool.shutdown();
        return null;
    }

    /**
     * 推送通知
     *
     * @param noticeUserId 通知人id（个推）
     * @param phone        通知人手机号（个推）
     * @param content      通知内容
     * @param sendNo       通知编号
     * @param sendTitle    通知标题
     */
    public void push(String noticeUserId, String phone, String content, Integer sendNo, String sendTitle) {
        //创建系统通知对象，将通知存到数据库
        Notice notice = new Notice();
        notice.setNoticeStatus(1);
        notice.setNoticeType(sendNo);
        notice.setCreateTime(new Date(System.currentTimeMillis()));
        notice.setNoticeUserId(noticeUserId);
        notice.setNoticeContent(content);
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
        send.setSendTitle(sendTitle);
        send.setSendNo(sendNo.toString());
        send.setSendType(RepairDataValidation.PUSH_SENDTYPE);
        send.setSendContent(content);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage========" + send);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage to json========" + pushInfo);
        }
        if (!StringUtil.isBlank(phone)) {
            //推送
            PropertyPushUtil.push(phone, pushInfo);
        }
    }

    /**
     * 推送通知
     *
     * @param noticeUserId 通知人Id（群推）
     * @param phones       通知人手机号（群推）
     * @param content      通知内容
     * @param sendNo       通知编号
     * @param sendTitle    通知标题
     */
    public void pushList(List<String> noticeUserId, List<String> phones, String content, Integer sendNo, String sendTitle) {
        List<Notice> notices = new ArrayList<>();
        if (null != noticeUserId && noticeUserId.size() > 0) {
            for (String userId :
                    noticeUserId) {
                //创建系统通知对象，将通知存到数据库
                Notice notice = new Notice();
                notice.setNoticeStatus(1);
                notice.setNoticeType(sendNo);
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeUserId(userId);
                notice.setNoticeContent(content);
                notices.add(notice);
            }
        }
        noticeMapper.insertNotices(notices);
        SendMessage send = new SendMessage();
        send.setSendId(notices.get(0).getNoticeId().toString());
        send.setSendTitle(sendTitle);
        send.setSendNo(sendNo.toString());
        send.setSendType(RepairDataValidation.PUSH_SENDTYPE);
        send.setSendContent(content);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage========" + send);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========SendMessage to json========" + pushInfo);
        }
        if (phones != null && phones.size() > 0) {
            //推送
            PropertyPushUtil.pushList(phones, pushInfo,false);
        }
    }

    @Override
    public List<FindAllRecordResp> findAllRecords(Paging<FindAllRecordResp> paging, FindAllRecordReq recordReq) {
        List<RepairHistory> list = repairHistoryMapper.findAllRecords(paging.getRowBounds(), recordReq);
        List<FindAllRecordResp> recordList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (RepairHistory history :
                    list) {
                FindAllRecordResp resp = new FindAllRecordResp();
                resp.setOrderNo(history.getOrderNo());
                resp.setCreateTime(history.getCreateTime());
                resp.setDescription(history.getHistoryDesc());
                //操作人员
                if (history.getOperatorType() == 1) {
                    resp.setOperatorName(history.getOperatorName());
                } else if (history.getOperatorType() == 2) {
                    resp.setOperatorName(history.getOperatorDepartment() + "-" + history.getOperatorName());
                }
                recordList.add(resp);
            }
        }
        return recordList;
    }

    @Override
    public Response findExecutors(FindExecutorReq executorReq,HttpServletRequest request) throws Exception{
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("empName", executorReq.getEmpName());
        jsonParam.put("phone", executorReq.getPhone());
        jsonParam.put("empNo", executorReq.getEmpNo());
        jsonParam.put("departmentId", executorReq.getDepartmentId());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/repair/v1/findExecutors";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        return response;
    }

    @Override
    public Response findDeptList(HttpServletRequest request) throws Exception{
        JSONObject jsonParam = new JSONObject();
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/repair/v1/findDeptLists";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        return response;
    }

    @Override
    public Response findPositions(DeptReq deptReq,HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("departmentId",deptReq.getDepartmentId());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/repair/v1/findPositions";
        Response response = HttpClientUtil.post(url, jsonParam, request);
        return response;
    }

    @Override
    public Estate findByEstateId(String estateId) {
        return estateMapper.selectByPrimaryKey(estateId) ;
    }

    public String makeHouseNo(String houseId,String estateId) {
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        Long buildingId = 0L;
        Long unitId = 0L;
        String houseNum = "";
        if (estate.getEstateType().equals(RepairDataValidation.PROPERTY_ESTATE_TYPE)){
            House house = houseMapper.selectByPrimaryKey(houseId);
            buildingId = house.getBuildingId();
            unitId = house.getUnitId();
            houseNum = house.getHouseNum();
        }else {
            OfficeHouse house = officeHouseMapper.selectByPrimaryKey(houseId);
            buildingId = house.getBuildingId();
            unitId = house.getUnitId();
            houseNum = house.getHouseNum();
        }

        StringBuffer houseNo = new StringBuffer();
        if (buildingId != null) {
            Building building = buildingMapper.selectByPrimaryKey(buildingId);
            if (building != null) {
                houseNo.append(building.getBuildingName());
            }
        }
        if (unitId != null) {
            BuildingUnit unit = unitMapper.selectByPrimaryKey(unitId);
            if (unit != null) {
                houseNo.append(unit.getUnitName());
            }
        }
        houseNo.append(houseNum);
        return houseNo.toString();
    }
}

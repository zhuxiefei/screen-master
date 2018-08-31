package com.betel.estatemgmt.business.userapp.repair.service.impl;

import com.betel.estatemgmt.business.userapp.pay.constant.PayConstant;
import com.betel.estatemgmt.business.userapp.repair.code.AppRepairCode;
import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.userapp.repair.model.AppRepairOrder;
import com.betel.estatemgmt.business.userapp.repair.model.CancelRepairReq;
import com.betel.estatemgmt.business.userapp.repair.model.RepairPage;
import com.betel.estatemgmt.business.userapp.repair.service.RepairService;
import com.betel.estatemgmt.business.propertyapp.assign.constant.AssignConstant;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.repair.model.Employee;
import com.betel.estatemgmt.business.web.repair.model.FindRepairEmpsResp;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.property.PropertyConfigMapper;
import com.betel.estatemgmt.common.mapper.repair.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.property.PropertyConfig;
import com.betel.estatemgmt.common.model.repair.*;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.IdUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * 报修service层
 * </p>
 * ClassName: RepairServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:17 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class RepairServiceImpl implements RepairService {
    private static final Logger LOG = LoggerFactory.getLogger(RepairServiceImpl.class);

    private static String estateType_house = "1";

    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    RepairOrderMapper repairOrderMapper;

    @Autowired
    RepairOrderTypeMapper repairOrderTypeMapper;

    @Autowired
    RepairOrderPicMapper repairOrderPicMapper;

    @Autowired
    PropertyConfigMapper propertyConfigMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    RepairOrderReplyMapper repairOrderReplyMapper;

    @Autowired
    RepairHistoryMapper repairHistoryMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    EstateMapper estateMapper;

    @Autowired
    OfficeHouseMapper officeHouseMapper;

    /**
     * <p>
     * 上传图片
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/15 9:34
     */
    @Override
    public void addPicture(List<Picture> pictures) {
        pictureMapper.addActivityPics(pictures);
    }

    /**
     * <p>
     * 查询报修类型
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/15 9:34
     */
    @Override
    public List<RepairOrderType> findAllRepairOrderType(String typeType,Integer estateType) {
        return repairOrderTypeMapper.findAllRepairOrderType(typeType,estateType);
    }

    /**
     * 添加报修订单
     * Create By ZhouYe ON 2017/9/15 14:37
     */
    @Override
    public String addRepair(AppRepairOrder appRepairOrder, HttpServletRequest request) throws Exception {
        //声明报修订单
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setEstateId(appRepairOrder.getEstateId());
        repairOrder.setCreateTime(new Date());
        repairOrder.setContactPhone(appRepairOrder.getContactPhone().trim());
        //假如报名区域是个人区域
        if (RepairConstant.ORDER_AREA_PERSON.equals(appRepairOrder.getOrderArea())) {
            if (null == appRepairOrder.getHouseId()) {
                //房屋id不可为空
                return AppRepairCode.HOUSEID_NULL;
            } else {
                Estate estate = estateMapper.selectByPrimaryKey(appRepairOrder.getEstateId());
                if(estateType_house.equals(estate.getEstateType())){
                    House house = houseMapper.selectByPrimaryKey(appRepairOrder.getHouseId());
                    if(house == null){
                        //房屋已删除
                        return AppRepairCode.HOUSE_DELETE;
                    }
                }else {
                    OfficeHouse house = officeHouseMapper.selectByPrimaryKey(appRepairOrder.getHouseId());
                    if(house == null){
                        //房屋已删除
                        return AppRepairCode.HOUSE_DELETE;
                    }
                }
                repairOrder.setHouseId(appRepairOrder.getHouseId());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--repairOrder---------" + repairOrder);
                }
            }
        }
        repairOrder.setOrderArea(appRepairOrder.getOrderArea());
        repairOrder.setOrderType(Long.parseLong(appRepairOrder.getOrderType()));
        //报修描述，不为空进行前后去空格
        if (!StringUtil.isBlank(appRepairOrder.getOrderDesc())) {
            repairOrder.setOrderDesc(appRepairOrder.getOrderDesc().trim());
        }
        repairOrder.setOrderContact(appRepairOrder.getOrderContact());
        repairOrder.setUserId(appRepairOrder.getUser());
        //报修状态默认待维修
        repairOrder.setOrderStatus(RepairConstant.ORDER_STATUS_START);
        //生成报修订单id
        String orderNo = IdUtil.getLocalTrmSeqNum(RepairConstant.BX_FLAG);
        repairOrder.setOrderNo(orderNo);
        //时间字符串转换成时间格式
        if (!StringUtil.isBlank(appRepairOrder.getAppointTime())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            repairOrder.setAppointTime(format.parse(appRepairOrder.getAppointTime()));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("--repairOrder---save------" + repairOrder);
        }
        //催缴默认为0
        repairOrder.setUrgeCount(RepairConstant.ZERO);
        //保存报修订单
        repairOrderMapper.insertSelective(repairOrder);
        //保存相关图片
        if (!StringUtil.isBlank(appRepairOrder.getPictureIds())) {
            String[] picId = appRepairOrder.getPictureIds().split(",");
            List<RepairOrderPic> list = new ArrayList<>();
            for (int i = 0; i < picId.length; i++) {
                RepairOrderPic pic = new RepairOrderPic();
                pic.setOrderNo(orderNo);
                pic.setCreateTime(new Date());
                pic.setPictureId(picId[i]);
                list.add(pic);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("--RepairOrderPics-----save----" + list);
            }
            repairOrderPicMapper.addRepairPics(list);
        }
        //推送通知
//        RepairThread thread = new RepairThread(repairOrder,RepairConstant.ADD_REPAIR_THREAD);
//        thread.start();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (LOG.isDebugEnabled()){
                        LOG.debug("用户新增报修单（推送给：有报修指派权限的员工）");
                    }
                    //用户新增报修单（推送给：有报修指派权限的员工）
                    //查询所有有报修指派权限的员工手机号
                    JSONObject jsonParam = new JSONObject();
                    Response response = HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/findRepairEmps",jsonParam,request);
                    if (null != response && null != response.getData()){
                        //创建推送模型
                        SendMessage send = new SendMessage();
                        send.setSendId("");
                        send.setSendTitle(RepairConstant.USER_ADD_REPAIR_SENDTITLE);
                        send.setSendNo(RepairConstant.USER_ADD_REPAIR_SENDNO);
                        send.setSendType(RepairConstant.PUSH_SENDTYPE);
                        send.setSendContent(RepairConstant.USER_ADD_REPAIR_SENDCONTENT);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========SendMessage========" + send);
                        }
                        //转json
                        String pushInfo = GsonUtil.object2Gson(send);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========SendMessage to json========" + pushInfo);
                        }
                        List<String> phones = new ArrayList<>();
                        Type type = new TypeToken<List<FindRepairEmpsResp>>() {
                        }.getType();
                        List<FindRepairEmpsResp> employees = gson.fromJson(response.getData().toString(), type);
                        for (FindRepairEmpsResp employee:
                                employees) {
                            phones.add(employee.getPhoneNum());
                        }
                        //推送
                        PropertyPushUtil.pushList(phones,pushInfo,false);
                    }
                } catch (Exception e) {
                    LOG.error("-------addRepair push error----------"+e);
                }
            }
        });
        cachedThreadPool.shutdown();
        return StatusCode.SUCCESS;
    }

    /**
     * 查询维修收费标准
     * Create By ZhouYe ON 2017/9/15 15:59
     */
    @Override
    public String findFeeScale(String estateId) {
        PropertyConfig config = new PropertyConfig();
        config.setEstateId(estateId);
        config.setConfName(PayConstant.CHARGESTANDARD);
        //查询维修收费标准
        PropertyConfig pro = propertyConfigMapper.selectByPrimaryKey(config);
        if (pro != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("--propertyConfig----repairfee-----" + pro);
            }
            return pro.getConfValue();
        }
        return null;
    }

    /**
     * 条件查询维修订单
     * Create By ZhouYe ON 2017/9/15 16:38
     */
    @Override
    public List<AppRepairOrder> findAllRepair(RepairPage repairPage) {
        List<AppRepairOrder> repairOrders = repairOrderMapper.findAllRepair(repairPage.getRowBounds(), repairPage);
        for (AppRepairOrder repairOrder : repairOrders) {
            //查询图片
            List<String> pics = repairOrderPicMapper.findPictureUrls(repairOrder.getOrderNo());
            if (LOG.isDebugEnabled()) {
                LOG.debug("--repairOrder----picture-----" + pics);
            }
            if (pics.size() > 0) {
                repairOrder.setPictureUrl(pics.get(0));
            }
            //报修订单的报修类型
            RepairOrderType orderType = repairOrderTypeMapper.selectByPrimaryKey(Long.parseLong(repairOrder.getOrderType()));
            if (LOG.isDebugEnabled()) {
                LOG.debug("--repairOrder----orderType-----" + orderType);
            }
            if (orderType != null) {
                repairOrder.setOrderTypeDes(orderType.getTypeName());
            }
            //已完成的报修订单判断是否评论
            if (repairOrder.getOrderStatus() == RepairConstant.ORDER_STATUS_END) {
                //查询是否评论
                List<RepairOrderReply> repairOrderReplys = repairOrderReplyMapper.findByOrderNo(repairOrder.getOrderNo());
                if (repairOrderReplys.size() > 0) {
                    repairOrder.setIsReply(RepairConstant.ISREPLY);
                } else {
                    repairOrder.setIsReply(RepairConstant.NOTREPLY);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--repairOrder----repairOrderReplys-----" + repairOrderReplys);
                }
            }
        }
        return repairOrders;
    }

    /**
     * 根据orderNo查询维修单
     * Create By ZhouYe ON 2017/9/18 9:31
     */
    @Override
    public AppRepairOrder findRepair(String orderNo) {
        AppRepairOrder appRepairOrder = new AppRepairOrder();
        RepairOrder repairOrder = repairOrderMapper.selectByPrimaryKey(orderNo);
        if (LOG.isDebugEnabled()) {
            LOG.debug("--repairOrder------" + repairOrder);
        }
        if (repairOrder != null) {
            appRepairOrder.setOrderNo(repairOrder.getOrderNo());
            //报修进度
            appRepairOrder.setOrderStatus(repairOrder.getOrderStatus());
            //报修联系人号码
            appRepairOrder.setContactPhone(repairOrder.getContactPhone());
            //报修联系人姓名
            appRepairOrder.setOrderContact(repairOrder.getOrderContact());
            //报修区域
            appRepairOrder.setOrderArea(repairOrder.getOrderArea());
            //报修描述
            appRepairOrder.setOrderDesc(repairOrder.getOrderDesc());
            //完成时间
            appRepairOrder.setFinishTime(dealDate(repairOrder.getFinishTime()));
            //接单时间
            appRepairOrder.setAcceptTime(dealDate(repairOrder.getAcceptTime()));
            //预约时间
            appRepairOrder.setAppointTime(dealDate(repairOrder.getAppointTime()));
            //创建时间
            appRepairOrder.setCreateTime(dealDate(repairOrder.getCreateTime()));
            //报修类型
            RepairOrderType childType = repairOrderTypeMapper.selectByPrimaryKey(repairOrder.getOrderType());
            //取消时间
            appRepairOrder.setCancelTime(dealDate(repairOrder.getCancelTime()));
            RepairOrderType parentType = null;
            String orderType = null;
            if (childType != null) {
                orderType = childType.getTypeName();
                parentType = repairOrderTypeMapper.selectByPrimaryKey(childType.getParentId());
                if (parentType != null) {
                    orderType = parentType.getTypeName() + "-" + childType.getTypeName();
                }
            }
            if (orderType != null) {
                appRepairOrder.setOrderType(orderType);
            }
            //报修图片
            List<String> pics = repairOrderPicMapper.findPictureUrls(repairOrder.getOrderNo());
            if (LOG.isDebugEnabled()) {
                LOG.debug("--repairOrder----pictures-----" + pics);
            }
            appRepairOrder.setPictureUrls(pics);
            RepairHistory repairHistory = repairHistoryMapper.findOperatorInfo(orderNo);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--repairOrder----repairHistory-----" + repairHistory);
            }
            if (repairHistory != null) {
                appRepairOrder.setOperatorName(repairHistory.getReceiverName());
                appRepairOrder.setOperatorPhone(repairHistory.getReceiverPhone());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("--repairOrder----RepairOrderInfo-----" + appRepairOrder);
        }
        return appRepairOrder;
    }

    /**
     * 报修评论
     * Create By ZhouYe ON 2017/9/18 11:05
     */
    @Override
    public String addRepairReply(RepairOrderReply repairOrderReply) {
        RepairOrder repairOrder = repairOrderMapper.selectByPrimaryKey(repairOrderReply.getOrderNo());
        if (LOG.isDebugEnabled()) {
            LOG.debug("--repairOrder----repairOrder-----" + repairOrder);
        }
        if (repairOrder != null) {
            //判断报修是否完成
            if (!repairOrder.getOrderStatus().equals(RepairConstant.ORDER_STATUS_END)) {
                return AppRepairCode.ORDER_NOT_END;
            } else {
                //判断报修是否评论
                List<RepairOrderReply> repairOrderReplys = repairOrderReplyMapper.findByOrderNo(repairOrderReply.getOrderNo());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--repairOrder----repairOrderReplys-----" + repairOrderReplys);
                }
                if (repairOrderReplys.size() > 0) {
                    return AppRepairCode.ORDER_REPLY;
                }
            }
            //报修评论保存
            if (!StringUtil.isBlank(repairOrderReply.getServiceDesc())) {
                repairOrderReply.setServiceDesc(repairOrderReply.getServiceDesc().trim());
            }
            repairOrderReply.setCreateTime(new Date());
            if (LOG.isDebugEnabled()) {
                LOG.debug("--repairOrder----repairOrderReply-----" + repairOrderReply);
            }
            repairOrderReplyMapper.insertSelective(repairOrderReply);
        } else {
            return AppRepairCode.ORDER_NO_NULL;
        }
        return StatusCode.SUCCESS;
    }

    /**
     * <p>
     * 催单
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 10:09
     */
    @Override
    public String urgeRepair(String orderNo,HttpServletRequest request) throws Exception{
        RepairOrder repairOrder = repairOrderMapper.selectByPrimaryKey(orderNo);
        //判断是否绑定房屋，房屋是否存在
        if (repairOrder == null) {
            return AppRepairCode.REPAIR_NULL;
        } else if(repairOrder.getHouseId()!=null){
            House house = houseMapper.selectByPrimaryKey(repairOrder.getHouseId());
            if(house ==null){
                return AppRepairCode.HOUSE_DELETE;
            }
        }
        if (repairOrder.getOrderStatus().equals(RepairConstant.ORDER_STATUS_END)) {
            //订单已完成
            return AppRepairCode.REPAIR_END;
            //预约时间为空
        } else if (repairOrder.getAppointTime() == null) {
            if (repairOrder.getUrgeCount().equals(RepairConstant.ZERO)) {
                //第一次催单
                if (diff(repairOrder.getCreateTime()) < 1){
                    //催单间隔太短
                    return AppRepairCode.FIRST_URGE_DENY;
                }else {
                    //催单成功
                    updateUrgeRepair(orderNo, repairOrder.getUrgeCount() + RepairConstant.ONE,request);
                }
            } else {
                if (diff(repairOrder.getLastUrgeTime()) < 1) {
                    //催单间隔太短
                    return AppRepairCode.URGEREPAIROFTEN;
                }else {
                    //催单成功
                    updateUrgeRepair(orderNo, repairOrder.getUrgeCount() + RepairConstant.ONE,request);
                }
            }
            //预约时间不为空
        } else if (repairOrder.getAppointTime() != null) {
            if (repairOrder.getUrgeCount().equals(RepairConstant.ZERO)) {
                //第一次催单
                //预约维修时间和单子创建时间是否满一小时
                if (diff2(repairOrder.getCreateTime(),repairOrder.getAppointTime()) < 1){
                    //预约维修时间在单子创建时间一小时之内
                    if (repairOrder.getAppointTime().getTime()>System.currentTimeMillis()){
                        //当前时间在预约维修时间之前
                        return AppRepairCode.FIRST_URGE_DENY;
                    }else{
                        //催单成功
                        updateUrgeRepair(orderNo, repairOrder.getUrgeCount() + RepairConstant.ONE,request);
                    }
                }else {
                    //预约维修时间在单子创建时间一小时之外
                    if (diff(repairOrder.getCreateTime())<1){
                        //当前时间在单子创建时间一小时之内
                        return AppRepairCode.FIRST_URGE_DENY;
                    }else {
                        //催单成功
                        updateUrgeRepair(orderNo, repairOrder.getUrgeCount() + RepairConstant.ONE,request);
                    }
                }
            }else {
                if (diff(repairOrder.getLastUrgeTime()) < 1) {
                    //催单间隔太短
                    return AppRepairCode.URGEREPAIROFTEN;
                }else {
                    //催单成功
                    updateUrgeRepair(orderNo, repairOrder.getUrgeCount() + RepairConstant.ONE,request);
                }
            }
        }
        return StatusCode.SUCCESS;
    }


    /**
     * <p>
     * 催单保存
     * orderNo id
     * num 催单次数
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 10:46
     */
    private void updateUrgeRepair(String orderNo, int num,HttpServletRequest request) throws Exception{
        RepairOrder updateRepairOrder = new RepairOrder();
        updateRepairOrder.setOrderNo(orderNo);
        if(num>RepairConstant.THREENINE){
            num = RepairConstant.THREENINE;
        }
        updateRepairOrder.setUrgeCount(num);
        updateRepairOrder.setLastUrgeTime(new Date());
        repairOrderMapper.updateByPrimaryKeySelective(updateRepairOrder);
        RepairOrder order = repairOrderMapper.selectByPrimaryKey(orderNo);
        //推送通知
//        RepairThread thread = new RepairThread(order,RepairConstant.URGE_REPAIR_THREAD);
//        thread.start();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_START)){
                        if (LOG.isDebugEnabled()){
                            LOG.debug("若是未指派状态，给报修指派权限的员工发通知");
                        }
                        //用户催单（待维修状态则推送给：有报修指派权限的员工）
                        JSONObject jsonParam = new JSONObject();
                        Response response = null;
                        try {
                            response = HttpClientUtil.post(
                                    RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/findRepairEmps",jsonParam,request);
                        } catch (Exception e) {
                            LOG.error("------push error-----",e);
                        }
                        if (null != response && null != response.getData()){
                            List<String> phones = new ArrayList<>();
                            List<String> userIds = new ArrayList<>();
                            Type type = new TypeToken<List<FindRepairEmpsResp>>() {
                            }.getType();
                            List<FindRepairEmpsResp> employees = gson.fromJson(response.getData().toString(), type);
                            for (FindRepairEmpsResp employee:
                                    employees) {
                                phones.add(employee.getPhoneNum());
                                userIds.add(employee.getEmployeeId());
                            }
                            pushList(userIds,phones
                                    ,RepairConstant.USER_URGE_REPAIR_SENDCONTENT+order.getOrderNo()
                                    ,Integer.parseInt(RepairConstant.USER_URGE_REPAIR_SENDNO)
                                    ,RepairConstant.USER_URGE_REPAIR_SENDTITLE);
                        }
                    }else if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_DOING)){
                        if (LOG.isDebugEnabled()){
                            LOG.debug("若是已接单状态，给被指派人员发通知");
                        }
                        //用户催单 (已接单状态则推送给：被指派人员）
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("empId", order.getOperatorId());
                        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
                        Response response = HttpClientUtil.post(url, jsonParam, request);
                        String phone = null;
                        String userId = null;
                        if (null != response && null != response.getData()){
                            JSONObject employee = JSONObject.fromObject(response.getData());
                            phone = employee.get("phoneNum").toString();
                            userId = employee.get("employeeId").toString();
                        }
                        push(userId,phone,RepairConstant.USER_URGE_REPAIR_SENDCONTENT+order.getOrderNo()
                                ,Integer.parseInt(RepairConstant.USER_URGE_REPAIR_SENDNO)
                                ,RepairConstant.USER_URGE_REPAIR_SENDTITLE);
                    }
                } catch (Exception e) {
                    LOG.error("-------updateUrgeRepair push error----------"+e);
                }
            }
        });
        cachedThreadPool.shutdown();
    }

    /**
     * 推送通知
     * @param noticeUserId 通知人id（个推）
     * @param phone 通知人手机号（个推）
     * @param content 通知内容
     * @param sendNo 通知编号
     * @param sendTitle 通知标题
     */
    public void push(String noticeUserId, String phone, String content, Integer sendNo, String sendTitle){
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
        if(!StringUtil.isBlank(phone)){
            //推送
            PropertyPushUtil.push(phone,pushInfo);
        }
    }

    /**
     * 推送通知
     * @param noticeUserId 通知人Id（群推）
     * @param phones 通知人手机号（群推）
     * @param content 通知内容
     * @param sendNo 通知编号
     * @param sendTitle 通知标题
     */
    public void pushList(List<String> noticeUserId,List<String> phones, String content, Integer sendNo, String sendTitle){
        List<Notice> notices = new ArrayList<>();
        if (null != noticeUserId && noticeUserId.size()>0){
            for (String userId:
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
        if(phones != null && phones.size()>0){
            //推送
            PropertyPushUtil.pushList(phones,pushInfo,false);
        }
    }

    /**
     * 时间格式处理
     * Create By ZhouYe ON 2017/9/18 9:46
     */
    private String dealDate(Date date) {
        if (date != null) {
            Format df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            return df.format(date);
        }
        return null;
    }

    /**
     * <p>
     * 时间距离现在的小时差
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 10:36
     */
    private int diff(Date date) {
        long from = date.getTime();
        long to = System.currentTimeMillis();
        return (int) ((to - from) / (1000 * 60 * 60));
    }

    /**
     * <p>
     * 前者距离后者的小时差
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 10:36
     */
    private int diff2(Date date1,Date date2) {
        long from = date1.getTime();
        long to = date2.getTime();
        return (int) ((to - from) / (1000 * 60 * 60));
    }

    @Override
    public Integer countWaitRepair(String estateId) {
        return repairOrderMapper.countWaitRepair(estateId);
    }

    @Override
    public Integer countPendingRepair(String userId,String estateId) {
        return repairOrderMapper.countPendingRepair(userId,estateId);
    }

    @Override
    public String cancelRepair(CancelRepairReq repairReq,HttpServletRequest request) throws Exception{
        RepairOrder order = repairOrderMapper.selectByPrimaryKey(repairReq.getOrderNo());
        if (null != order){
            if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_CANCEL)){
                return AppRepairCode.REPAIR_CANCEL;
            }
            if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_END)){
                return AppRepairCode.REPAIR_END;
            }
        }else {
            return AppRepairCode.REPAIR_NULL;
        }
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setOrderNo(repairReq.getOrderNo());
        repairOrder.setCancelTime(new Date());
        repairOrder.setOrderStatus(AssignConstant.REPAIR_CANCEL);
        repairOrderMapper.updateByPrimaryKeySelective(repairOrder);
        //推送通知
//        RepairThread thread = new RepairThread(order,RepairConstant.CANCEL_REPAIR_THREAD);
//        thread.start();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (LOG.isDebugEnabled()){
                        LOG.debug("用户取消报修单（推送给：有报修指派权限的员工和被指派人员）");
                    }
                    //用户取消报修单（推送给：有报修指派权限的员工和被指派人员）
                    JSONObject jsonParam = new JSONObject();
                    Response response = null;
                    try {
                        response = HttpClientUtil.post(
                                RepairDataValidation.OA_PROJECT_URL+"httpclientOA/task/v1/findRepairEmps",jsonParam,request);
                    } catch (Exception e) {
                        LOG.error("------push error-----",e);
                    }
                    if (null != response && null != response.getData()){
                        List<String> empIds = new ArrayList<>();
                        List<String> phones = new ArrayList<>();
                        Type type = new TypeToken<List<FindRepairEmpsResp>>() {
                        }.getType();
                        List<FindRepairEmpsResp> employees = gson.fromJson(response.getData().toString(), type);
                        for (FindRepairEmpsResp employee:
                                employees) {
                            empIds.add(employee.getEmployeeId());
                            phones.add(employee.getPhoneNum());
                        }
                        pushList(empIds,phones,
                                RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT+order.getOrderNo()
                                ,Integer.parseInt(RepairConstant.USER_CANCEL_REPAIR_SENDNO)
                                ,RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                    }
                    if (!StringUtil.isBlank(order.getOperatorId())) {
                        //用户/物管人员取消已结单报修单（推送给：被指派人员）
                        JSONObject jsonParam1 = new JSONObject();
                        jsonParam1.put("empId", order.getOperatorId());
                        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
                        Response response1 = HttpClientUtil.post(url, jsonParam1, request);
                        String phone = null;
                        String userId = null;
                        if (null != response1 && null != response1.getData()){
                            JSONObject employee = JSONObject.fromObject(response1.getData());
                            phone = employee.get("phoneNum").toString();
                            userId = employee.get("employeeId").toString();
                        }
                        push(userId,phone,RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT+order.getOrderNo()
                                ,Integer.parseInt(RepairConstant.USER_CANCEL_REPAIR_SENDNO)
                                ,RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                    }
                } catch (Exception e) {
                    LOG.error("-------cancelRepair push error----------"+e);
                }
            }
        });
        cachedThreadPool.shutdown();
        return null;
    }

    @Override
    public String deleteRepair(String orderNo) {
        RepairOrder r = repairOrderMapper.selectByPrimaryKey(orderNo);
        if(r!=null){
            if(r.getOrderStatus()!=AssignConstant.REPAIR_CANCEL && r.getOrderStatus()!=AssignConstant.REPAIR_FINISH){
                return AppRepairCode.REPAIR_NOT_CANCEL_OR_FINISH;
            }else {
                RepairOrder repairOrder = new RepairOrder();
                repairOrder.setOrderNo(orderNo);
                repairOrder.setIsDelete(RepairConstant.USER_DELETE_REPAIR);
                repairOrderMapper.updateByPrimaryKeySelective(repairOrder);
            }
        }
        return null;
    }

    @Override
    public Estate findByEstateId(String estateId) {
        return estateMapper.selectByPrimaryKey(estateId);
    }
}

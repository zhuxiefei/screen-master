package com.betel.estatemgmt.business.propertyapp.assign.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.repair.code.AppRepairCode;
import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.propertyapp.assign.constant.AssignConstant;
import com.betel.estatemgmt.business.propertyapp.assign.model.AssignRepairReq;
import com.betel.estatemgmt.business.propertyapp.assign.model.FindRepairInfoResp;
import com.betel.estatemgmt.business.propertyapp.assign.model.FindRepairListResp;
import com.betel.estatemgmt.business.propertyapp.assign.service.AssignService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.repair.model.AddOrderReq;
import com.betel.estatemgmt.business.web.repair.model.FindRepairEmpsResp;
import com.betel.estatemgmt.business.web.repair.model.OrderDetail;
import com.betel.estatemgmt.business.web.repair.model.UploadOrderPicReq;
import com.betel.estatemgmt.business.web.repair.util.RepairUtil;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairHistoryMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderPicMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.repair.RepairHistory;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * assignService层实现类
 * </p>
 * ClassName: AssignServiceImpl <br/>
 * Author: cuixx  <br/>
 * Date: 2017/12/22 13:53 <br/>
 * Version: 1.0 <br/>
 */
@Service("sssignService")
@Transactional(rollbackFor = Exception.class)
public class AssignServiceImpl implements AssignService {
    private static final Logger LOG = LoggerFactory.getLogger(AssignServiceImpl.class);

    private static String estateType_house = "1";

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private RepairOrderPicMapper repairOrderPicMapper;

    @Autowired
    private RepairHistoryMapper repairHistoryMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BuildingUnitMapper unitMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private EstateMapper estateMapper;

    @Autowired
    private OfficeHouseMapper officeHouseMapper;


    @Override
    public List<FindRepairListResp> findUnassignList(Paging<FindRepairListResp> page,String estateId) {
        List<FindRepairListResp> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            list = repairOrderMapper.findAllUnassignList(page.getRowBounds(),estateId);
        }else {
            list = repairOrderMapper.findAllOfficeUnassignList(page.getRowBounds(),estateId);
        }
        return list;
    }

    @Override
    public List<FindRepairListResp> findAssignedList(Paging<FindRepairListResp> page,String estateId) {
        List<FindRepairListResp> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            list = repairOrderMapper.findAllAssignedList(page.getRowBounds(),estateId);
        }else {
            list = repairOrderMapper.findAllOfficeAssignedList(page.getRowBounds(),estateId);
        }
        return list;
    }

    @Override
    public Response findRepairInfo(String orderNo,String estateId) {
        Response response = new Response();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        FindRepairInfoResp findRepairInfoResp = new FindRepairInfoResp();
        if(estateType_house.equals(estate.getEstateType())){
            findRepairInfoResp = repairOrderMapper.findRepairInfo(orderNo);
        }else {
            findRepairInfoResp = repairOrderMapper.findOfficeRepairInfo(orderNo);
        }
        if (null != findRepairInfoResp){
            //查询报修单的图片信息
            List<String> pictures = repairOrderPicMapper.findPictureUrls(orderNo);
            findRepairInfoResp.setPictures(pictures);
            //如果已经指派了查询当前维修员的信息
            if (null != findRepairInfoResp.getOperatorId() || !"".equals(findRepairInfoResp.getOperatorId())) {
                RepairHistory repairHistory = repairHistoryMapper.findOperatorInfo(orderNo);
                if (null != repairHistory) {
                    findRepairInfoResp.setOperatorDepartment(repairHistory.getReceiverDepartment());
                    findRepairInfoResp.setOperatorName(repairHistory.getReceiverName());
                    findRepairInfoResp.setPhoto(repairHistory.getReceiverHead());

                    findRepairInfoResp.setAssignDepartment(repairHistory.getOperatorDepartment());
                    findRepairInfoResp.setAssignName(repairHistory.getOperatorName());
                    findRepairInfoResp.setAssginDesc(repairHistory.getDescription());
                }
            }
            //如果是已完成或者已取消 查询指派记录
            List<RepairHistory> records = repairHistoryMapper.findRecordsByOrderNo(orderNo);
            findRepairInfoResp.setRecords(records);
            //如果是已完成还要查询报修确认单的图片
            if (null != findRepairInfoResp.getOrderPic() || !"".equals(findRepairInfoResp.getOrderPic())) {
                Picture p = pictureMapper.selectByPicId(findRepairInfoResp.getOrderPic());
                if (null != p) {
                    findRepairInfoResp.setOrderPic(ConfigManager.read(ConfigName.FILE_SERVER) + p.getPictureUrl());
                }
            }
            response.setData(findRepairInfoResp);
            return response;
        }else {
            response.setCode(AppRepairCode.REPAIR_IS_DELETE);
            return response;
        }
    }

    @Override
    public void updateOperatorAndInsertRecord(AssignRepairReq assignRepairReq, JSONObject json, String assigner,HttpServletRequest request) throws Exception {
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setOrderNo(assignRepairReq.getOrderNo());
        repairOrder.setOrderStatus(AssignConstant.REPAIR_RECEVIED);
        repairOrder.setOperatorId(assignRepairReq.getOperatorId());
        repairOrder.setAcceptTime(new Date(System.currentTimeMillis()));
        repairOrderMapper.updateByPrimaryKeySelective(repairOrder);
        //增加维修指派记录
        RepairHistory repairHistory = new RepairHistory();
        repairHistory.setOrderNo(assignRepairReq.getOrderNo());
        repairHistory.setOperatorId(assigner);
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_APP);
        repairHistory.setHistoryType(AssignConstant.ORDER_ASSIGN);
        if (null != json.get("departmentName")) {
            repairHistory.setOperatorDepartment(json.get("departmentName").toString());
        }
        if (null != json.get("employeeName")) {
            repairHistory.setOperatorName(json.get("employeeName").toString());
        }
        if (null != json.get("photo")) {
            repairHistory.setOperatorHead(json.get("photo").toString());
        }
        repairHistory.setReceiver(assignRepairReq.getOperatorId());
        repairHistory.setReceiverPhone(assignRepairReq.getPhoneNum());
        repairHistory.setReceiverHead(assignRepairReq.getPhoto());
        repairHistory.setReceiverDepartment(assignRepairReq.getOperatorDepartment());
        repairHistory.setReceiverName(assignRepairReq.getOperatorName());
        repairHistory.setDescription(assignRepairReq.getDescription());
        repairHistory.setCreateTime(new Date());
        repairHistory.setHistoryDesc("指派给:" + assignRepairReq.getOperatorDepartment() + "-" + assignRepairReq.getOperatorName());
        repairHistoryMapper.insertSelective(repairHistory);
        //查询维修单数据
        OrderDetail orderDetail = repairOrderMapper.selectDetailByOrderNo(assignRepairReq.getOrderNo());
        if (orderDetail != null && !StringUtil.isBlank(orderDetail.getHouseNo())) {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            orderDetail.setHouseNo(makeHouseNo(orderDetail.getHouseNo(), AESUtil.decrypt(estateId)));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RepairServiceImpl selectDetailByOrderNo========orderDetail=" + orderDetail);
        }
        RepairOrder order = repairOrderMapper.selectByPrimaryKey(assignRepairReq.getOrderNo());
        if (order != null) {
            //判断用户是否被删除
            if (userService.findByUserId(order.getUserId(),request) != null) {
                //发通知
                //创建系统通知对象，将通知存到数据库
                Notice notice = new Notice();
                notice.setNoticeStatus(1);
                notice.setNoticeType(Integer.parseInt(RepairDataValidation.ORDER_SENDNO));
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeUserId(order.getUserId());
                JSONObject employee = validateOperatorExist(assignRepairReq.getOperatorId(),request);
                String content = null;
                String[] params = null;
                if (order.getOrderArea().equals(RepairDataValidation.ORDER_AREA_PERSONAL)) {
                    if (StringUtil.isBlank(orderDetail.getOrderDesc())) {
                        content = "尊敬的" + order.getOrderContact() + "\n您对" + orderDetail.getHouseNo() + "的报修："
                                + orderDetail.getOrderType() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + assignRepairReq.getPhoneNum();
                        params = new String[]{order.getOrderContact(),orderDetail.getHouseNo(),
                                orderDetail.getOrderType(),employee.get("employeeName").toString(),assignRepairReq.getPhoneNum()};
                    } else {
                        content = "尊敬的" + order.getOrderContact() + "\n您对" + orderDetail.getHouseNo() + "的报修："
                                + orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + assignRepairReq.getPhoneNum();
                        params = new String[]{order.getOrderContact(),orderDetail.getHouseNo(),
                                orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc(),
                                employee.get("employeeName").toString(),assignRepairReq.getPhoneNum()};
                    }
                } else {
                    if (StringUtil.isBlank(orderDetail.getOrderDesc())) {
                        content = "尊敬的" + order.getOrderContact() + "\n您对公共区域的报修："
                                + orderDetail.getOrderType() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + assignRepairReq.getPhoneNum();
                        params = new String[]{order.getOrderContact(),"公共区域",
                                orderDetail.getOrderType(),employee.get("employeeName").toString(),assignRepairReq.getPhoneNum()};
                    } else {
                        content = "尊敬的" + order.getOrderContact() + "\n您对公共区域的报修："
                                + orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc() + "。我们已指派维修人员前来维修，有问题和师傅联系。" +
                                employee.get("employeeName").toString() + "," + assignRepairReq.getPhoneNum();
                        params = new String[]{order.getOrderContact(),"公共区域",
                                orderDetail.getOrderType() + "-" + orderDetail.getOrderDesc(),
                                employee.get("employeeName").toString(),assignRepairReq.getPhoneNum()};
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

                UserAccount account = userService.findByUserId(order.getUserId(),request);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========findAccountByUserId========" + account);
                }
                if (account != null) {
                    //推送
                    MsgPushUtils.push(account.getAcctName(), pushInfo);
                    //发短信
                    String[] phones = new String[]{account.getAcctName()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.REPAIR_NOTICE_TEMPLATE,phones,params);
                }
            }
        }
    }

    /**
     * 校验人员是否被删除
     *
     * @param operatorId
     * @return
     * @throws Exception
     */
    public JSONObject validateOperatorExist(String operatorId,HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("executor", operatorId);
        Response response = HttpClientUtil.post(RepairDataValidation.OA_PROJECT_URL + "httpclientOA/repair/v1/findExecutorExist", jsonParam,request);
        if (null != response && response.getCode().equals(StatusCode.SUCCESS)) {
            JSONObject json = JSONObject.fromObject(response.getData());
            return json;
        }
        return null;
    }

    /**
     * 生成房屋信息
     * @param houseId
     * @param estateId
     * @return
     */
    public String makeHouseNo(String houseId,String estateId) {
        StringBuffer houseNo = new StringBuffer();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            House house = houseMapper.selectByPrimaryKey(houseId);
            if(house != null && house.getBuildingId() != null){
                Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
                if (building != null) {
                    houseNo.append(building.getBuildingName());
                }
            }
            if (house != null && house.getUnitId() != null) {
                BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
                if (unit != null) {
                    houseNo.append(unit.getUnitName());
                }
            }
            houseNo.append(house.getHouseNum());
        }else {
            OfficeHouse house = officeHouseMapper.selectByPrimaryKey(String.valueOf(houseId));
            if(house != null && house.getBuildingId() != null){
                Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
                if (building != null) {
                    houseNo.append(building.getBuildingName());
                }
            }
            if (house != null && house.getUnitId() != null) {
                BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
                if (unit != null) {
                    houseNo.append(unit.getUnitName());
                }
            }
            houseNo.append(house.getHouseNum());
        }
        return houseNo.toString();
    }

    @Override
    public String cancelRepair(String orderNo, JSONObject json, String userId,HttpServletRequest request) throws Exception {
        RepairOrder r = repairOrderMapper.selectByPrimaryKey(orderNo);
        if (r != null) {
            if (r.getOrderStatus() == AssignConstant.REPAIR_CANCEL) {
                return AppRepairCode.REPAIR_CANCEL;
            } else if (r.getOrderStatus() == AssignConstant.REPAIR_FINISH) {
                return AppRepairCode.REPAIR_END;
            } else {
                RepairOrder repairOrder = new RepairOrder();
                repairOrder.setOrderNo(orderNo);
                repairOrder.setCancelTime(new Date());
                repairOrder.setOrderStatus(AssignConstant.REPAIR_CANCEL);
                repairOrderMapper.updateByPrimaryKeySelective(repairOrder);

                //维修取消记录
                RepairHistory repairHistory = new RepairHistory();
                repairHistory.setOrderNo(orderNo);
                repairHistory.setOperatorId(userId);
                repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_APP);
                repairHistory.setHistoryType(AssignConstant.ORDER_CANCEL);
                if (null != json.get("departmentName")) {
                    repairHistory.setOperatorDepartment(json.get("departmentName").toString());
                }
                if (null != json.get("employeeName")) {
                    repairHistory.setOperatorName(json.get("employeeName").toString());
                }
                if (null != json.get("photo")) {
                    repairHistory.setOperatorHead(json.get("photo").toString());
                }
                repairHistory.setCreateTime(new Date());
                repairHistory.setHistoryDesc("取消维修单");
                repairHistoryMapper.insertSelective(repairHistory);
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
                            RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT + r.getOrderNo()
                            , Integer.parseInt(RepairConstant.USER_CANCEL_REPAIR_SENDNO)
                            , RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                }
                //用户取消报修单（推送给：被指派人员）
                if (!StringUtil.isBlank(r.getOperatorId())) {
                    //创建系统通知对象，将通知存到数据库
                    Notice notice = new Notice();
                    notice.setNoticeStatus(1);
                    notice.setNoticeType(Integer.parseInt(RepairConstant.USER_CANCEL_REPAIR_SENDNO));
                    notice.setCreateTime(new Date(System.currentTimeMillis()));
                    notice.setNoticeUserId(r.getOperatorId());
                    notice.setNoticeContent(RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT + r.getOrderNo());
                    noticeMapper.insertSelective(notice);
                    if (LOG.isDebugEnabled()){
                        LOG.debug("=======notice======="+notice);
                    }
                    SendMessage send = new SendMessage();
                    send.setSendId(notice.getNoticeId().toString());
                    send.setSendTitle(RepairConstant.USER_CANCEL_REPAIR_SENDTITLE);
                    send.setSendNo(RepairConstant.USER_CANCEL_REPAIR_SENDNO);
                    send.setSendType(RepairDataValidation.PUSH_SENDTYPE);
                    send.setSendContent(RepairConstant.USER_CANCEL_REPAIR_SENDCONTENT + r.getOrderNo());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========SendMessage========" + send);
                    }
                    //转json
                    String pushInfo = GsonUtil.object2Gson(send);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========SendMessage to json========" + pushInfo);
                    }
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam1.put("empId", r.getOperatorId());
                    String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
                    Response response1 = HttpClientUtil.post(url, jsonParam1, request);
                    String phone = null;
                    if (null != response1 && null != response1.getData()){
                        JSONObject employee = JSONObject.fromObject(response1.getData());
                        phone = employee.get("phoneNum").toString();
                    }
                    if(!StringUtil.isBlank(phone)){
                        //推送
                        PropertyPushUtil.push(phone,pushInfo);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void addRepairOrder(AddOrderReq orderReq, JSONObject json, String assigner,HttpServletRequest request) throws Exception {
        //新增order
        RepairOrder order = new RepairOrder();
        String orderNo = RepairUtil.getFlowNo();
        order.setOrderNo(orderNo);
        String estateId = request.getHeader("estateId");
        if (StringUtil.isBlank(estateId)){
            estateId = AESUtil.encrypt("1");
        }
        order.setEstateId(AESUtil.decrypt(estateId));
        order.setOrderContact(orderReq.getOrderContact());
        order.setContactPhone(orderReq.getContactPhone());
        order.setOrderArea(orderReq.getOrderArea());
        //如果是个人区域 就保存房屋id
        if (orderReq.getOrderArea() == 1) {
            order.setHouseId(orderReq.getHouseId());
        }
        order.setOrderType(orderReq.getOrderType());
        order.setOrderDesc(orderReq.getOrderDesc());
        order.setOrderStatus(RepairDataValidation.ORDER_INORDER_STATUS);
        order.setOperatorId(orderReq.getOperatorId());
        if (!(StringUtil.isBlank(orderReq.getAppointTime()))) {
            order.setAppointTime(DateUtil.toDate(orderReq.getAppointTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setAcceptTime(new Date(System.currentTimeMillis()));
        order.setUrgeCount(0);
        repairOrderMapper.insertSelective(order);
        //新增报修单记录
        RepairHistory history = new RepairHistory();
        history.setOrderNo(orderNo);
        history.setOperatorId(assigner);
        history.setOperatorType(AssignConstant.OPERATOR_TYPE_APP);
        history.setHistoryType(AssignConstant.ORDER_ADD);
        if (null != json.get("departmentName")) {
            history.setOperatorDepartment(json.get("departmentName").toString());
        }
        if (null != json.get("employeeName")) {
            history.setOperatorName(json.get("employeeName").toString());
        }
        if (null != json.get("photo")) {
            history.setOperatorHead(json.get("photo").toString());
        }
        history.setCreateTime(new Date());
        history.setHistoryDesc("新增维修单");
        repairHistoryMapper.insertSelective(history);
        //指派操作记录
        RepairHistory repairHistory = new RepairHistory();
        repairHistory.setOrderNo(orderNo);
        repairHistory.setOperatorId(assigner);
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_APP);
        repairHistory.setHistoryType(AssignConstant.ORDER_ASSIGN);
        if (null != json.get("departmentName")) {
            repairHistory.setOperatorDepartment(json.get("departmentName").toString());
        }
        if (null != json.get("employeeName")) {
            repairHistory.setOperatorName(json.get("employeeName").toString());
        }
        if (null != json.get("photo")) {
            repairHistory.setOperatorHead(json.get("photo").toString());
        }
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
        JSONObject jsonParam1 = new JSONObject();
        jsonParam1.put("empId", orderReq.getOperatorId());
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
        Response response1 = HttpClientUtil.post(url, jsonParam1, request);
        String phone = null;
        if (null != response1 && null != response1.getData()){
            JSONObject employee = JSONObject.fromObject(response1.getData());
            phone = employee.get("phoneNum").toString();
        }
        if(!StringUtil.isBlank(phone)){
            //推送
            PropertyPushUtil.push(phone,pushInfo);
        }
    }

    @Override
    public List<FindRepairListResp> findRepairListByStatus(Paging<FindRepairListResp> page, String status, String userId,String estateId) {
        List<FindRepairListResp> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            list = repairOrderMapper.findAllRepairListByStatus(page.getRowBounds(), status, userId, estateId);
        }else {
            list = repairOrderMapper.findAllOfficeRepairListByStatus(page.getRowBounds(), status, userId, estateId);
        }
        return list;
    }

    @Override
    public String deleteRepair(String orderNo, JSONObject json, String userId) throws Exception {
        RepairOrder r = repairOrderMapper.selectByPrimaryKey(orderNo);
        if (r != null) {
            if (r.getOrderStatus() == AssignConstant.REPAIR_CANCEL || r.getOrderStatus() == AssignConstant.REPAIR_FINISH) {
                RepairOrder repairOrder = new RepairOrder();
                repairOrder.setOrderNo(orderNo);
                repairOrder.setIsDelete(AssignConstant.REPAIR_DELETE_ADMIN);
                repairOrderMapper.updateByPrimaryKeySelective(repairOrder);
            } else {
                return AppRepairCode.REPAIR_NOT_CANCEL_OR_FINISH;
            }
        }
        return null;
    }

    @Override
    public void uploadPic(Picture picture, UploadOrderPicReq picReq, JSONObject json, String userId) {
        //添加图片,返回主键
        pictureMapper.insertSelective(picture);
        String picId = picture.getPictureId();

        RepairOrder order = new RepairOrder();
        order.setOrderNo(picReq.getOrderNo());
        order.setOrderPic(picId);
        order.setOrderStatus(RepairDataValidation.ORDER_FINISH_STATUS);
        order.setDescription(picReq.getDescription());
        order.setFinishTime(new Date(System.currentTimeMillis()));
        //修改维修单
        repairOrderMapper.updateByPrimaryKeySelective(order);

        //完成维修单操作记录
        RepairHistory repairHistory = new RepairHistory();
        repairHistory.setOrderNo(picReq.getOrderNo());
        repairHistory.setOperatorId(userId);
        repairHistory.setOperatorType(AssignConstant.OPERATOR_TYPE_APP);
        repairHistory.setHistoryType(AssignConstant.ORDER_FINISH);
        if (null != json.get("departmentName")) {
            repairHistory.setOperatorDepartment(json.get("departmentName").toString());
        }
        if (null != json.get("employeeName")) {
            repairHistory.setOperatorName(json.get("employeeName").toString());
        }
        if (null != json.get("photo")) {
            repairHistory.setOperatorHead(json.get("photo").toString());
        }
        repairHistory.setCreateTime(new Date());
        repairHistory.setHistoryDesc("完成维修单");
        repairHistoryMapper.insertSelective(repairHistory);

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
}

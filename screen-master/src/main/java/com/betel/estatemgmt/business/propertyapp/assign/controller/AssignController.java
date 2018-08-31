package com.betel.estatemgmt.business.propertyapp.assign.controller;

import com.betel.estatemgmt.business.propertyapp.assign.constant.AssignConstant;
import com.betel.estatemgmt.business.propertyapp.assign.model.*;
import com.betel.estatemgmt.business.propertyapp.assign.service.AssignService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.code.RepairCode;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.repair.model.AddOrderReq;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * app维修指派接口类
 * </p>
 * ClassName: AssignController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/22 11:50 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/assign")
public class AssignController {
    private static final Logger LOG = LoggerFactory.getLogger(AssignController.class);

    @Autowired
    private AssignService assignService;

    @Autowired
    private RepairService repairService;

    /**
     * <p>
     * 未指派和已指派报修单列表（分页）
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/22 16:07
     *
     * @param findRepairListReq
     * @return
     */
    @RequestMapping(value = "v1/findRepairList", method = RequestMethod.GET)
    public Response findRepairList(FindRepairListReq findRepairListReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/findRepairList--------------start===findRepairListReq=" + findRepairListReq);
        }
        Response response = new Response();
        List<FindRepairListResp> repairLists = null;
        Paging<FindRepairListResp> page = new Paging<FindRepairListResp>(findRepairListReq.getCurPage(), findRepairListReq.getPageSize());
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (AssignConstant.UNASSIGN.equals(findRepairListReq.getType())) {
                repairLists = assignService.findUnassignList(page, AESUtil.decrypt(estateId));
            } else {
                repairLists = assignService.findAssignedList(page, AESUtil.decrypt(estateId));
            }
            page.result(repairLists);
            response.setData(page);
        } catch (Exception e) {
            LOG.error("---app/assign/v1/findRepairList----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/findRepairList--------------end" + response);
        }
        return response;
    }

    /**
     * <p>
     * 维修单详情
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/23 11:03
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "v1/findRepairInfo", method = RequestMethod.GET)
    public Response findRepairInfo(@RequestParam String orderNo, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/findRepairInfo--------------start===orderNo=" + orderNo);
        }
        Response response = new Response();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            response = assignService.findRepairInfo(orderNo, AESUtil.decrypt(estateId));
        } catch (Exception e) {
            LOG.error("---app/assign/v1/findRepairInfo----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/findRepairInfo--------------end" + response);
        }
        return response;
    }

    /**
     * <p>
     * 指派报修单
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/23 11:03
     *
     * @param assignRepairReq
     * @param request
     * @return
     */
    @RequestMapping(value = "v1/assignRepair", method = RequestMethod.POST)
    public Response assignRepair(@RequestBody AssignRepairReq assignRepairReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/assignRepair--------------start===assignRepairReq=" + assignRepairReq);
        }
        Response response = new Response();
        if (!StringUtil.isEmpty(assignRepairReq.getDescription())) {
            assignRepairReq.setDescription(assignRepairReq.getDescription().trim());
        }
        if (!StringUtil.isBlank(assignRepairReq.getDescription()) && assignRepairReq.getDescription().length() > 500) {
            response.setCode(RepairCode.ASSIGN_DESC_FORMAT_WRONG);
            return response;
        }
        try {
            //获取当前物业人员ID，作为指派者id
            String assigner = request.getHeader("userId");
            //查询指派者的信息
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("employeeId", AESUtil.decrypt(assigner));
            Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/common/v1/findUserInfo", jsonParam, request);
            if (null != r) {
                JSONObject json = JSONObject.fromObject(r.getData());
                //修改维修单的维修人员，并增加维修指派记录
                assignService.updateOperatorAndInsertRecord(assignRepairReq, json, AESUtil.decrypt(assigner), request);
            }
            //发送推送通知给被指派人
            SendMessage send = new SendMessage();
            send.setSendId(assignRepairReq.getOperatorId());
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
            JSONObject jsonParam1 = new JSONObject();
            jsonParam1.put("empId", assignRepairReq.getOperatorId());
            String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findEmpByEmpId";
            Response response1 = HttpClientUtil.post(url, jsonParam1, request);
            String phone = null;
            if (null != response1 && null != response1.getData()) {
                JSONObject employee = JSONObject.fromObject(response1.getData());
                phone = employee.get("phoneNum").toString();
            }
            if (!StringUtil.isBlank(phone)) {
                //推送
                PropertyPushUtil.push(phone, pushInfo);
            }
        } catch (Exception e) {
            LOG.error("===HttpClientUtil post error====url===/app/common/v1/findUserInfo====", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/assignRepair--------------end" + response);
        }
        return response;
    }

    /**
     * <p>
     * 取消维修单
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/23 11:49
     *
     * @param repairReq
     * @return
     */
    @RequestMapping(value = "v1/cancelRepair", method = RequestMethod.POST)
    public Response cancelRepair(@RequestBody RepairReq repairReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/cancelRepair--------------start===repairReq=" + repairReq);
        }
        Response response = new Response();
        try {
            //获取当前物业人员ID，作为指派者id
            String assigner = request.getHeader("userId");
            //查询指派者的信息
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("employeeId", AESUtil.decrypt(assigner));
            Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/common/v1/findUserInfo", jsonParam, request);
            if (null != r) {
                JSONObject json = JSONObject.fromObject(r.getData());
                String code = assignService.cancelRepair(repairReq.getOrderNo(), json, AESUtil.decrypt(assigner), request);
                if (code != null && !"".equals(code)) {
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("===app/assign/v1/cancelRepair====error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/cancelRepair--------------end" + response);
        }
        return response;
    }

    /**
     * <p>
     * 新增报修单
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/23 13:03
     *
     * @param addOrderReq
     * @return
     */
    @RequestMapping(value = "v1/addRepairOrder", method = RequestMethod.POST)
    public Response addRepairOrder(@RequestBody AddOrderReq addOrderReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/addRepairOrder--------------start===addOrderReq=" + addOrderReq);
        }
        Response response = new Response();
        try {
            //获取当前物业人员ID，作为指派者id
            String assigner = request.getHeader("userId");
            //校验
            String code = validateAddRepair(addOrderReq, request);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            } else {
                //查询指派者的信息
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("employeeId", AESUtil.decrypt(assigner));
                Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/common/v1/findUserInfo", jsonParam, request);
                if (null != r) {
                    JSONObject json = JSONObject.fromObject(r.getData());
                    //去空格
                    trimAddReq(addOrderReq);
                    assignService.addRepairOrder(addOrderReq, json, AESUtil.decrypt(assigner), request);
                }
            }
        } catch (Exception e) {
            LOG.error("---------addRepairOrder---error--------", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/addRepairOrder--------------end" + response);
        }
        return response;
    }

    /**
     * <p>
     * 前后去空格
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 11:35
     */
    private AddOrderReq trimAddReq(AddOrderReq orderReq) {
        if (!StringUtil.isEmpty(orderReq.getContactPhone())) {
            orderReq.setContactPhone(orderReq.getContactPhone().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOperatorPhone())) {
            orderReq.setOperatorPhone(orderReq.getOperatorPhone().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOrderContact())) {
            orderReq.setOrderContact(orderReq.getOrderContact().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOrderDesc())) {
            orderReq.setOrderDesc(orderReq.getOrderDesc().trim());
        }
        return orderReq;
    }

    /**
     * <p>
     * 校验新增维修单信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 14:02
     *
     * @param orderReq 新增入参
     * @return code码
     */
    private String validateAddRepair(AddOrderReq orderReq, HttpServletRequest request) throws Exception {
        String code = null;
        if (orderReq.getOrderArea() == 1 && orderReq.getHouseId() == null) {
            code = RepairCode.HOUSEID_NULL_ERROR;
        } else if (orderReq.getHouseId() != null
                && !repairService.findByHouseId(orderReq.getHouseId(), AESUtil.decrypt(request.getHeader("estateId")))) {
            code = RepairCode.HOUSE_NOT_EXIST;
        } else if (StringUtil.isBlank(orderReq.getOrderContact())) {
            code = RepairCode.ORDERCONTACT_NULL_ERROR;
        } else if (!orderReq.getOrderContact().trim().matches(RepairDataValidation.USERNAME_RULE)) {
            code = RepairCode.ORDERCONTACT_RULE_ERROR;
        } else if (StringUtil.isBlank(orderReq.getContactPhone())) {
            code = RepairCode.CONTACTPHONE_NULL_ERROR;
        } else if (!orderReq.getContactPhone().trim().matches(RepairDataValidation.PHONE_RULE)) {
            code = RepairCode.CONTACTPHONE_RULE_ERROR;
        } else if (orderReq.getOrderDesc() != null && orderReq.getOrderDesc().trim().length() > RepairDataValidation.REPAIRDESC_MAX_LENGTH) {
            code = RepairCode.REPAIRDESC_SIZE_ERROR;
        } else if (orderReq.getOrderArea() == null) {
            code = RepairCode.REPAIRAREA_NULL_ERROR;
        } else if (orderReq.getOrderType() == null) {
            code = RepairCode.REPAIRTYPE_NULL_ERROR;
        } else if (!StringUtil.isBlank(orderReq.getAppointTime()) && !validateTime(orderReq.getAppointTime())) {
            code = RepairCode.APPOINTTIME_RULE_ERROR;
        } else if (null == repairService.validateOperatorExist(orderReq.getOperatorId(), request)) {
            code = RepairCode.OPERATOR_IS_DELETE;
        }
        return code;
    }

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    private boolean validateTime(String time) {
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }


    /**
     * <p>
     * 删除已完成、已取消的维修单
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/23 11:49
     *
     * @param repairReq
     * @return
     */
    @RequestMapping(value = "v1/deleteRepair", method = RequestMethod.POST)
    public Response deleteRepair(@RequestBody RepairReq repairReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/deleteRepair--------------start===repairReq=" + repairReq);
        }
        Response response = new Response();
        try {
            //获取当前物业人员ID，作为指派者id
            String assigner = request.getHeader("userId");
            //查询指派者的信息
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("employeeId", AESUtil.decrypt(assigner));
            Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/common/v1/findUserInfo", jsonParam, request);
            if (null != r) {
                JSONObject json = JSONObject.fromObject(r.getData());
                String code = assignService.deleteRepair(repairReq.getOrderNo(), json, AESUtil.decrypt(assigner));
                if (code != null && !"".equals(code)) {
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("===app/assign/v1/deleteRepair====error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/assign/v1/deleteRepair--------------end" + response);
        }
        return response;
    }
}

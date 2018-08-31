package com.betel.estatemgmt.business.web.patrol.controller;

import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.web.patrol.code.EquipmentCode;
import com.betel.estatemgmt.business.web.patrol.code.MaintenanceCode;
import com.betel.estatemgmt.business.web.patrol.code.PatrolCode;
import com.betel.estatemgmt.business.web.patrol.constant.PatrolConstant;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.business.web.patrol.service.PatrolService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.RegexRule;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.TimeUtils;
import com.betel.estatemgmt.utils.Validate;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: PatrolController <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 11:50 <br/>
 * @Version: 1.0
 */
@RestController
@RequestMapping("web/patrol")
public class PatrolController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(PatrolController.class);
    @Autowired
    private PatrolService patrolService;


    /**
     * 查询巡检设备内容设置列表（分页）
     *
     * @return
     */
    @RequiresPermissions("patrolContent-findAllEquipmentList")
    @RequestMapping(value = "v1/findAllEquipmentList", method = RequestMethod.POST)
    public Response<Paging<PatrolEquipmentResp>> findAllEquipmentList(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findAllEquipmentList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<PatrolEquipmentResp>> response = new Response<Paging<PatrolEquipmentResp>>();
        try {
            patrolReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            if (!StringUtil.isEmpty(patrolReq.getEquipmentNo())) {
                patrolReq.setEquipmentNo(patrolReq.getEquipmentNo().trim());
                //校验他的合法性待做
                if (!StringUtil.isBlank(patrolReq.getEquipmentNo()) && !patrolReq.getEquipmentNo().matches(RegexRule.SPECIAL_WORD_RULE)) {
                    response.setCode(PatrolCode.EQUIPMENT_NO_IS_NOT_LEGAL);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getEquipmentNo()) && patrolReq.getEquipmentNo().contains("_")) {
                    patrolReq.setEquipmentNo(patrolReq.getEquipmentNo().replace("_", "\\_"));
                }
            }
            if (!StringUtil.isEmpty(patrolReq.getEquipmentName())) {
                patrolReq.setEquipmentName(patrolReq.getEquipmentName().trim());
                if (!StringUtil.isBlank(patrolReq.getEquipmentName()) && !patrolReq.getEquipmentName().matches(PatrolConstant.EQUIPMENT_NAME_RULE)) {
                    response.setCode(PatrolCode.EQUIPMENT_NAME_IS_NOT_LEGAL);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getEquipmentName()) && patrolReq.getEquipmentName().contains("_")) {
                    patrolReq.setEquipmentName(patrolReq.getEquipmentName().replace("_", "\\_"));
                }
            }
            if (!StringUtil.isEmpty(patrolReq.getStartTime())) {
                patrolReq.setStartTime(patrolReq.getStartTime().trim() + " 00:00:00");
            }
            if (!StringUtil.isEmpty(patrolReq.getEndTime())) {
                patrolReq.setEndTime(patrolReq.getEndTime().trim() + " 23:59:59");
            }
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            }else {
                Paging<PatrolEquipmentResp> pager = new Paging<PatrolEquipmentResp>(patrolReq.getCurPage(), patrolReq.getPageSize());
                List<PatrolEquipmentResp> list = patrolService.findAllEquipmentList(pager, patrolReq);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/findAllEquipmentList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findAllEquipmentList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询巡检设备内容设置详情
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "patrolContent-findEquipmentDetail")
    @RequestMapping(value = "v1/findEquipmentDetail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<PatrolEquipmentResp> findEquipmentDetail(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findEquipmentDetail-------start,patrolReq=" + patrolReq);
        }
        Response<PatrolEquipmentResp> response = new Response<PatrolEquipmentResp>();
        try {
            if (StringUtil.isBlank(patrolReq.getEquipmentId())) {
                response.setCode(PatrolCode.EQUIPMENT_ID_IS_NULL);
            } else {
                PatrolEquipmentResp patrolEquipmentResp = patrolService.findEquipmentDetail(patrolReq);
                if (null == patrolEquipmentResp) {
                    response.setCode(PatrolCode.EQUIPMENT_IS_NOT_EXIST);
                } else {
                    response.setData(patrolEquipmentResp);
                }
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/findEquipmentDetail--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findEquipmentDetail--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询巡检设备内容设置二维码
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "patrolContent-findQrcode")
    @RequestMapping(value = "v1/findQrcode", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<PatrolEquipment> findQrcode(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findQrcode-------start,patrolReq=" + patrolReq);
        }
        Response<PatrolEquipment> response = new Response<PatrolEquipment>();
        try {
            if (StringUtil.isBlank(patrolReq.getEquipmentId())) {
                response.setCode(PatrolCode.EQUIPMENT_ID_IS_NULL);
            } else {
                PatrolEquipment patrolEquipment = patrolService.findQrcode(patrolReq);
                if (null == patrolEquipment) {
                    response.setCode(PatrolCode.EQUIPMENT_IS_NOT_EXIST);
                } else {
                    response.setData(patrolEquipment);
                }
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/findQrcode--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findQrcode--------end,response=" + response);
        }
        return response;
    }

    /**
     * 编辑巡检设备内容设置
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "patrolContent-updateEquipment")
    @RequestMapping(value = "v1/updateEquipment", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateEquipment(@RequestBody PatrolReq patrolReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/updateEquipment-------start,patrolReq=" + patrolReq);
        }
        Response<String> response = new Response<String>();
        try {
            patrolReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            response.setCode(patrolService.updateEquipment(patrolReq));
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/updateEquipment--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/updateEquipment--------end,response=" + response);
        }
        return response;
    }

    /**
     * 删除巡检设备内容设置
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "patrolContent-deleteEquipment")
    @RequestMapping(value = "v1/deleteEquipment", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteEquipment(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/deleteEquipment-------start,deptReq=" + patrolReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setCode(patrolService.deleteEquipment(patrolReq));
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/deleteEquipment--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/deleteEquipment--------end,response=" + response);
        }
        return response;
    }

    /**
     * 新增巡检设备内容设置
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions("patrolContent-addEquipment")
    @RequestMapping(value = "v1/addEquipment", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addEquipment(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/addEquipment-------start,positionReq=" + patrolReq);
        }
        Response<String> response = new Response<String>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                patrolReq.setEstateId(estateId);
                response.setCode(patrolService.addEquipment(patrolReq));
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/addEquipment--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/addEquipment--------end,response=" + response);
        }
        return response;
    }


    /**
     * 查询设施巡检记录列表
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions("facilitiesPatrol-findAllRecordList")
    @RequestMapping(value = "v1/findAllRecordList", method = RequestMethod.POST)
    public Response<Paging<Record>> findAllRecordList(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findAllRecordList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<Record>> response = new Response<Paging<Record>>();
        try {
            if (!StringUtil.isEmpty(patrolReq.getEquipmentNo())) {
                patrolReq.setEquipmentNo(patrolReq.getEquipmentNo().trim());
                //校验他的合法性待做
                if (!StringUtil.isBlank(patrolReq.getEquipmentNo()) && !patrolReq.getEquipmentNo().matches(RegexRule.SPECIAL_WORD_RULE)) {
                    response.setCode(PatrolCode.EQUIPMENT_NO_IS_NOT_LEGAL);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getEquipmentNo()) && patrolReq.getEquipmentNo().contains("_")) {
                    patrolReq.setEquipmentNo(patrolReq.getEquipmentNo().replace("_", "\\_"));
                }
            }
            if (!StringUtil.isEmpty(patrolReq.getEquipmentType())) {
                patrolReq.setEquipmentType(patrolReq.getEquipmentType().trim());
                //校验他的合法性待做
                if (!StringUtil.isBlank(patrolReq.getEquipmentType()) && !patrolReq.getEquipmentType().matches(RegexRule.SPECIAL_WORD_RULE)) {
                    response.setCode(EquipmentCode.TYPE_NAME_ROLE_ERROR);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getEquipmentType()) && patrolReq.getEquipmentType().contains("_")) {
                    patrolReq.setEquipmentType(patrolReq.getEquipmentType().replace("_", "\\_"));
                }
            }
            if (!StringUtil.isEmpty(patrolReq.getEquipmentName())) {
                patrolReq.setEquipmentName(patrolReq.getEquipmentName().trim());
                if (!StringUtil.isBlank(patrolReq.getEquipmentName()) && !patrolReq.getEquipmentName().matches(PatrolConstant.EQUIPMENT_NAME_RULE)) {
                    response.setCode(PatrolCode.EQUIPMENT_NAME_IS_NOT_LEGAL);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getEquipmentName()) && patrolReq.getEquipmentName().contains("_")) {
                    patrolReq.setEquipmentName(patrolReq.getEquipmentName().replace("_", "\\_"));
                }
            }
            if (!StringUtil.isEmpty(patrolReq.getEmployeeName())) {
                patrolReq.setEmployeeName(patrolReq.getEmployeeName().trim());
                if (!StringUtil.isBlank(patrolReq.getEmployeeName()) && !patrolReq.getEmployeeName().trim().matches(RegexRule.NAME_RULE)) {
                    response.setCode(PatrolCode.EMPLOYEE_NAME_IS_NOT_LEGAL);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getEmployeeName()) && patrolReq.getEmployeeName().contains("_")) {
                    patrolReq.setEmployeeName(patrolReq.getEmployeeName().replace("_", "\\_"));
                }
            }

            if (!StringUtil.isEmpty(patrolReq.getStartTime())) {
                if (!StringUtil.isBlank(patrolReq.getStartTime()) && !patrolReq.getStartTime().trim().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
                    response.setCode(PatrolCode.START_TIME_IS_NOT_LEGAL);
                    return response;
                }
                patrolReq.setStartTime(patrolReq.getStartTime().trim() + " 00:00:00");
            }
            if (!StringUtil.isEmpty(patrolReq.getEndTime())) {
                if (!StringUtil.isBlank(patrolReq.getEndTime()) && !patrolReq.getEndTime().trim().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
                    response.setCode(PatrolCode.END_TIME_IS_NOT_LEGAL);
                    return response;
                }
                patrolReq.setEndTime(patrolReq.getEndTime().trim() + " 23:59:59");
            }
            if (TimeUtils.compareDate(patrolReq.getStartTime(), patrolReq.getEndTime(), Validate.TIME_TYPE_YYYY_MM_DD)) {
                response.setCode(PatrolCode.START_TIME_IS_MORE_THAN_END_TIME);
                return response;
            }
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else{
                patrolReq.setEstateId(estateId);
                Paging<Record> pager = new Paging<Record>(patrolReq.getCurPage(), patrolReq.getPageSize());
                List<Record> list = patrolService.findAllRecordList(pager, patrolReq);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/findAllRecordList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findAllRecordList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询设施巡检记录详情
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "facilitiesPatrol-findRecordDetail")
    @RequestMapping(value = "v1/findRecordDetail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Record> findRecordDetail(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findRecordDetail-------start,patrolReq=" + patrolReq);
        }
        Response<Record> response = new Response<Record>();
        try {
            if (StringUtil.isBlank(patrolReq.getRecordId())) {
                response.setCode(PatrolCode.RECORD_ID_IS_NULL);
            } else {
                Record record = patrolService.findRecordDetail(patrolReq);
                if (null == record) {
                    response.setCode(PatrolCode.RECORD_IS_NOT_EXIST);
                } else {
                    response.setData(record);
                }
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/findRecordDetail--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findRecordDetail--------end,response=" + response);
        }
        return response;
    }

    /**
     * 删除设施巡检记录（批量）
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "facilitiesPatrol-deleteRecord")
    @RequestMapping(value = "v1/deleteRecord", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteRecord(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/deleteRecord-------start,deptReq=" + patrolReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setCode(patrolService.deleteRecord(patrolReq));
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/deleteRecord--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/deleteRecord--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询设备类型列表（不分页）
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "patrolContent-findTypeList")
    @RequestMapping(value = "v1/findTypeList", method = RequestMethod.POST)
    public Response<List<EquipmentType>> findTypeList(@RequestBody PatrolReq patrolReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/findTypeList-------start,patrolReq=" + patrolReq);
        }
        Response<List<EquipmentType>> response = new Response<List<EquipmentType>>();
        try {
            //去空格再校验
            patrolReq.setEquipmentType(null);
            patrolReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            List<EquipmentType> list = patrolService.findTypeList(patrolReq);
            response.setData(list);

        } catch (Exception e) {
            LOG.error("--------web/equipment/v1/findTypeList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/findTypeList-------end,response=" + response);
        }
        return response;
    }


    /**
     * 设备管理--查询维保记录列表
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-findAllMaintenanceList")
    @RequestMapping(value = "v1/findAllMaintenanceList", method = RequestMethod.POST)
    public Response<Paging<MaintenanceResp>> findAllMaintenanceList(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/findAllMaintenanceList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<MaintenanceResp>> response = new Response<Paging<MaintenanceResp>>();
        try {
            Paging<MaintenanceResp> pager = new Paging<>(patrolReq.getCurPage(), patrolReq.getPageSize());
            //去空格再校验
            String code = checkParm(trim(patrolReq));
            if (null == code) {
                List<MaintenanceResp> list = patrolService.findAllMaintenanceList(pager, patrolReq);
                pager.result(list);
                response.setData(pager);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("--------web/equipment/v1/findAllMaintenanceList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/findAllMaintenanceList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 设备管理--维保记录导出
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-exportList")
    @RequestMapping(value = "v1/exportList", method = RequestMethod.POST)
    public Response<List<MaintenanceResp>> exportList(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/exportList-------start,patrolReq=" + patrolReq);
        }
        Response<List<MaintenanceResp>> response = new Response<List<MaintenanceResp>>();
        try {
            //去空格再校验
            String code = checkParm(trim(patrolReq));
            if (null == code) {
                List<MaintenanceResp> list = patrolService.exportList(patrolReq);
                response.setData(list);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("--------web/equipment/v1/exportList--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/exportList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 设施巡检记录--维保保记录列表
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "facilitiesPatrol-findAllRecordMaintenanceList")
    @RequestMapping(value = "v1/findAllRecordMaintenanceList", method = RequestMethod.POST)
    public Response<Paging<MaintenanceResp>> findAllRecordMaintenanceList(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findAllRecordMaintenanceList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<MaintenanceResp>> response = new Response<Paging<MaintenanceResp>>();
        try {
            Paging<MaintenanceResp> pager = new Paging<>(patrolReq.getCurPage(), patrolReq.getPageSize());
            //去空格再校验
            String code = checkParm(trim(patrolReq));
            if (null == code) {
                List<MaintenanceResp> list = patrolService.findAllMaintenanceList(pager, patrolReq);
                pager.result(list);
                response.setData(pager);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/findAllRecordMaintenanceList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/findAllRecordMaintenanceList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 设施巡检记录--导出维报记录
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "facilitiesPatrol-exportRecordList")
    @RequestMapping(value = "v1/exportRecordList", method = RequestMethod.POST)
    public Response<List<MaintenanceResp>> exportRecordList(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/exportRecordList-------start,patrolReq=" + patrolReq);
        }
        Response<List<MaintenanceResp>> response = new Response<List<MaintenanceResp>>();
        try {
            //去空格再校验
            String code = checkParm(trim(patrolReq));
            if (null == code) {
                List<MaintenanceResp> list = patrolService.exportList(patrolReq);
                response.setData(list);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("--------web/patrol/v1/exportRecordList--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/patrol/v1/exportRecordList-------end,response=" + response);
        }
        return response;
    }

    private PatrolReq trim(PatrolReq patrolReq) {
        if (!StringUtil.isEmpty(patrolReq.getEquipmentId())) {
            patrolReq.setEquipmentId(patrolReq.getEquipmentId().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getStartTime())) {
            patrolReq.setStartTime(patrolReq.getStartTime().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEndTime())) {
            patrolReq.setEndTime(patrolReq.getEndTime().trim());
        }
        return patrolReq;
    }

    private String checkParm(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getEquipmentId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        }
        //查看设备是否删除
        if (!StringUtil.isBlank(patrolReq.getEquipmentId())) {
            PatrolEquipment patrolEquipment = patrolService.selectByPrimaryKey(patrolReq.getEquipmentId());
            if (null == patrolEquipment) {
                return MaintenanceCode.EQUIPMENT_IS_DELETE;
            }
        }
        if (!StringUtil.isBlank(patrolReq.getStartTime())) {
            if (!patrolReq.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(patrolReq.getEndTime())) {
            if (!patrolReq.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(patrolReq.getStartTime(), patrolReq.getEndTime(), RegexRule.TIME_FORMAT_Y_M_D)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }
        return null;
    }
}

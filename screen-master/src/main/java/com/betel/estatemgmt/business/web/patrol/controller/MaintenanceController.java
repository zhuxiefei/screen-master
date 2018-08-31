package com.betel.estatemgmt.business.web.patrol.controller;

import com.betel.estatemgmt.business.web.patrol.code.MaintenanceCode;
import com.betel.estatemgmt.business.web.patrol.constant.PatrolConstant;
import com.betel.estatemgmt.business.web.patrol.model.*;
import com.betel.estatemgmt.business.web.patrol.service.MaintenanceService;
import com.betel.estatemgmt.business.web.task.util.WorkUtil;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.StringUtil;
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
 * @ClassName: RepairRecordControler <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 11:50 <br/>
 * @Version: 1.0
 */
@RestController
@RequestMapping("web/maintenance")
public class MaintenanceController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(EquipmentTypeController.class);
    @Autowired
    private MaintenanceService maintenanceService;

    /**
     * <p>
     * 查询设备信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions("maintenance-selectByEquipment")
    @RequestMapping(value = "v1/selectByEquipment", method = RequestMethod.POST, consumes = "application/json")
    public Response<PatrolEquipment> selectByEquipment(@RequestBody PatrolReq patrolReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/selectByEquipment------start-" + patrolReq);
        }
        Response<PatrolEquipment> response = new Response<>();
        try {
            patrolReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            PatrolEquipment equipment = maintenanceService.selectByEquipment(patrolReq);
            if (null == equipment) {
                response.setCode(MaintenanceCode.EQUIPMENT_IS_DELETE);
            } else {
                response.setData(equipment);
            }
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/selectByEquipment----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/selectByEquipment------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加维保记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param maintenanceReq
     * @return
     */
    @RequiresPermissions("maintenance-addRecord")
    @RequestMapping(value = "v1/addRecord", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addRecord(@RequestBody MaintenanceReq maintenanceReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/addRecord------start-" + maintenanceReq);
        }
        Response<String> response = new Response<>();
        try {
            String code = maintenanceService.addMaintenance(maintenanceReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----web/equipment/v1/addRecord----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/addRecord------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 模糊查询设备编号
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/26 10:45
     *
     * @param equipNoReq
     * @return
     */
    @RequiresPermissions("maintenance-findEquipmentNos")
    @RequestMapping(value = "v1/findEquipmentNos", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<String>> findEquipmentNos(@RequestBody FindEquipNoReq equipNoReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findEquipmentNos------start-" + equipNoReq);
        }
        Response<List<String>> response = new Response<>();
        try {
            if (!StringUtil.isEmpty(equipNoReq.getEquipmentNo())) {
                equipNoReq.setEquipmentNo(equipNoReq.getEquipmentNo().trim());
            }
            if (!StringUtil.isBlank(equipNoReq.getEquipmentNo())
                    && !Validate.isCommonString(equipNoReq.getEquipmentNo(), PatrolConstant.EQUIP_NO_MAX_LENGTH)) {
                response.setCode(MaintenanceCode.EQUIPMENT_NO_RULE);
                return response;
            }
            if (!StringUtil.isBlank(equipNoReq.getEquipmentNo()) && equipNoReq.getEquipmentNo().contains("_")) {
                equipNoReq.setEquipmentNo(equipNoReq.getEquipmentNo().replace("_", "\\_"));
            }
            response.setData(maintenanceService.findEquipNos(equipNoReq.getEquipmentNo(),AESUtil.decrypt(request.getHeader("estateId"))));
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/findEquipmentNos----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findEquipmentNos------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询所有设备类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/26 10:45
     *
     * @return
     */
    @RequiresPermissions("maintenance-findEquipmentTypes")
    @RequestMapping(value = "v1/findEquipmentTypes", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<EquipmentType>> findEquipmentTypes(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findEquipmentTypes------start-");
        }
        Response<List<EquipmentType>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                response.setData(maintenanceService.findEquipTypes(estateId));
            }
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/findEquipmentTypes----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findEquipmentTypes------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页查询维保记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/26 10:45
     *
     * @param req
     * @return
     */
    @RequiresPermissions("maintenance-findAllRepairRecords")
    @RequestMapping(value = "v1/findAllRepairRecords", method = RequestMethod.POST, consumes = "application/json")
    public Response<Paging<FindAllRecordsResp>> findAllRepairRecords(@RequestBody FindAllRecordsReq req, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findAllRepairRecords------start-" + req);
        }
        Response<Paging<FindAllRecordsResp>> response = new Response<>();
        try {
            Paging<FindAllRecordsResp> pager = new Paging<>(req.getCurPage(), req.getPageSize());
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                req.setEstateId(estateId);
                //校验
                String code = validateFindRecordsReq(req);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                    return response;
                }
                pager.result(maintenanceService.findAllRecords(pager, req));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/findAllRepairRecords----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findAllRepairRecords------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询维保记录详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/26 10:45
     *
     * @param req
     * @return
     */
    @RequiresPermissions("maintenance-findRecord")
    @RequestMapping(value = "v1/findRecord", method = RequestMethod.POST, consumes = "application/json")
    public Response<FindRecordResp> findRecord(@RequestBody FindRecordReq req) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findRecord------start-" + req);
        }
        Response<FindRecordResp> response = new Response<>();
        try {
            //校验
            if (StringUtil.isBlank(req.getRecordId())) {
                response.setCode(MaintenanceCode.RECORD_ID_NULL);
            } else if (null == maintenanceService.findByRecordId(req.getRecordId())) {
                response.setCode(MaintenanceCode.RECORD_IS_DELETE);
            } else {
                response.setData(maintenanceService.findRecordByRecordId(req.getRecordId()));
            }
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/findRecord----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/findRecord------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改维保记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/26 10:45
     *
     * @param req
     * @return
     */
    @RequiresPermissions("maintenance-updateRecord")
    @RequestMapping(value = "v1/updateRecord", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateRecord(@RequestBody MaintenanceReq req) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/updateRecord------start-" + req);
        }
        Response<String> response = new Response<>();
        try {
            //校验
            String code = maintenanceService.updateRecord(req);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/updateRecord----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/updateRecord------end-" + response);
        }
        return response;
    }

    /**
     * 校验
     *
     * @param req
     * @return
     */
    private String validateFindRecordsReq(FindAllRecordsReq req) {
        if (!StringUtil.isBlank(req.getStartTime()) && !WorkUtil.validateTime(req.getStartTime().trim())) {
            return MaintenanceCode.TIME_RULE;
        }
        if (!StringUtil.isBlank(req.getEndTime()) && !WorkUtil.validateTime(req.getEndTime().trim())) {
            return MaintenanceCode.TIME_RULE;
        }
        if (!StringUtil.isBlank(req.getStartTime()) && !StringUtil.isBlank(req.getEndTime())
                && WorkUtil.validateTime(req.getStartTime().trim()) && WorkUtil.validateTime(req.getEndTime().trim())
                && !WorkUtil.validateTimeDistance(req.getStartTime(), req.getEndTime())) {
            return MaintenanceCode.START_LATE_END;
        }
        if (!StringUtil.isEmpty(req.getEquipmentNo())) {
            req.setEquipmentNo(req.getEquipmentNo().trim());
        }
        if (!StringUtil.isBlank(req.getEquipmentNo())
                && !Validate.isCommonString(req.getEquipmentNo(), PatrolConstant.EQUIP_NO_MAX_LENGTH)) {
            return MaintenanceCode.EQUIPMENT_NO_RULE;
        }
        if (!StringUtil.isBlank(req.getEquipmentNo()) && req.getEquipmentNo().contains("_")) {
            req.setEquipmentNo(req.getEquipmentNo().replace("_", "\\_"));
        }
        if (!StringUtil.isEmpty(req.getEquipmentName())) {
            req.setEquipmentName(req.getEquipmentName().trim());
        }
        if (!StringUtil.isBlank(req.getEquipmentName())
                && !Validate.isCommonString(req.getEquipmentName(), PatrolConstant.EQUIP_NAME_MAX_LENGTH)) {
            return MaintenanceCode.EQUIPMENT_NAME_RULE;
        }
        if (!StringUtil.isBlank(req.getEquipmentName()) && req.getEquipmentName().contains("_")) {
            req.setEquipmentName(req.getEquipmentName().replace("_", "\\_"));
        }
        if (!StringUtil.isEmpty(req.getLocation())) {
            req.setLocation(req.getLocation().trim());
        }
        if (!StringUtil.isBlank(req.getLocation())
                && !Validate.isCommonString(req.getLocation(), PatrolConstant.EQUIP_LOCATION_MAX_LENGTH)) {
            return MaintenanceCode.EQUIPMENT_LOCATION_RULE;
        }
        if (!StringUtil.isBlank(req.getLocation()) && req.getLocation().contains("_")) {
            req.setLocation(req.getLocation().replace("_", "\\_"));
        }
        return null;
    }

    /**
     * <p>
     * 导出维保记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/26 10:45
     *
     * @param req
     * @return
     */
    @RequiresPermissions("maintenance-exportRepairRecords")
    @RequestMapping(value = "v1/exportRepairRecords", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<ExportRecordsResp>> exportRepairRecords(@RequestBody ExportRepairRecordsReq req,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/exportRepairRecords------start-" + req);
        }
        Response<List<ExportRecordsResp>> response = new Response<>();
        try {
            if (!StringUtil.isBlank(req.getRecordIds())) {
                //根据多个id导出
                response.setData(maintenanceService.exportbyIds(req.getRecordIds()));
            } else {
                //根据条件导出
                //校验
                FindAllRecordsReq recordsReq = new FindAllRecordsReq();
                recordsReq.setEndTime(req.getEndTime());
                recordsReq.setEquipmentName(req.getEquipmentName());
                recordsReq.setEquipmentNo(req.getEquipmentNo());
                recordsReq.setLocation(req.getLocation());
                recordsReq.setStartTime(req.getStartTime());
                String code = validateFindRecordsReq(recordsReq);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                    return response;
                }
                //校验成功，进行查询
                req.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
                response.setData(maintenanceService.exportbyParams(req));
            }
        } catch (Exception e) {
            LOG.error("----web/maintenance/v1/exportRepairRecords----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/maintenance/v1/exportRepairRecords------end-" + response);
        }
        return response;
    }
}

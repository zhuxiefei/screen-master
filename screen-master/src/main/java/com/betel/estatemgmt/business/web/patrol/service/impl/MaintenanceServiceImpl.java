package com.betel.estatemgmt.business.web.patrol.service.impl;

import com.betel.estatemgmt.business.web.patrol.code.EquipmentCode;
import com.betel.estatemgmt.business.web.patrol.code.MaintenanceCode;
import com.betel.estatemgmt.business.web.patrol.code.PatrolCode;
import com.betel.estatemgmt.business.web.patrol.constant.PatrolConstant;
import com.betel.estatemgmt.business.web.patrol.model.*;
import com.betel.estatemgmt.business.web.patrol.service.MaintenanceService;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.patrol.EquipmentRepairRecordMapper;
import com.betel.estatemgmt.common.mapper.patrol.EquipmentTypeMapper;
import com.betel.estatemgmt.common.mapper.patrol.PatrolEquipmentMapper;
import com.betel.estatemgmt.common.model.patrol.EquipmentRepairRecord;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: MaintenanceServiceImpl <br/>
 * @author: jian.z  <br/>
 * @date: 2018/1/24 10:06 <br/>
 * @Version: 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MaintenanceServiceImpl implements MaintenanceService {

    private static final Logger LOG = LoggerFactory.getLogger(MaintenanceServiceImpl.class);

    @Autowired
    private EquipmentRepairRecordMapper equipmentRepairRecordMapper;
    @Autowired
    private PatrolEquipmentMapper patrolEquipmentMapper;
    @Autowired
    EquipmentTypeMapper typeMapper;

    @Override
    public PatrolEquipment selectByEquipment(PatrolReq patrolReq) {
        PatrolEquipment equipment = patrolEquipmentMapper.selectByEquipment(patrolReq);
        if (null != equipment){
            EquipmentType type = typeMapper.selectByPrimaryKey(equipment.getEquipmentType());
            if (null != type){
                equipment.setEquipmentType(type.getTypeName());
            }
        }
        return equipment;
    }

    @Override
    public String addMaintenance(MaintenanceReq maintenanceReq) throws ParseException {
        String code = check(trim(maintenanceReq));
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            EquipmentRepairRecord err = new EquipmentRepairRecord();
            err.setRecordId(UuidUtil.create());
            err.setEquipmentId(maintenanceReq.getEquipmentId());
            err.setEquipmentOperator(maintenanceReq.getEquipmentOperator());
            err.setOperatorPhone(maintenanceReq.getOperatorPhone());
            err.setRepairDesc(maintenanceReq.getRepairDesc());
            err.setRepairExpense(maintenanceReq.getRepairExpense());
            err.setRepairTime(DateUtil.toDate(maintenanceReq.getRepairTime(), RegexRule.TIME_FORMAT_Y_M_D));
            err.setCreateTime(new Date());
            err.setUpdateTime(new Date());
            String unit = numformatDate(maintenanceReq.getUnit());
            err.setQualityPeriod(maintenanceReq.getQualityPeriod()+","+unit);
            equipmentRepairRecordMapper.insertSelective(err);
            //设备维修次数加1
            PatrolEquipment equipment = patrolEquipmentMapper.selectByPrimaryKey(maintenanceReq.getEquipmentId());
            if (null != equipment){
                PatrolEquipment patrolEquipment = new PatrolEquipment();
                patrolEquipment.setEquipmentId(equipment.getEquipmentId());
                patrolEquipment.setRepairNumber(equipment.getRepairNumber()+1);
                patrolEquipmentMapper.updateByPrimaryKeySelective(patrolEquipment);
            }
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public List<String> findEquipNos(String equipNo,String estateId) {
        return patrolEquipmentMapper.findEquipNos(equipNo,estateId);
    }

    @Override
    public List<EquipmentType> findEquipTypes(String estatId) {
        return typeMapper.findTypes(estatId);
    }

    @Override
    public EquipmentType findByTypeId(String typeId) {
        return typeMapper.selectByPrimaryKey(typeId);
    }

    @Override
    public List<FindAllRecordsResp> findAllRecords(Paging<FindAllRecordsResp> paging, FindAllRecordsReq recordsReq) {
        return equipmentRepairRecordMapper.findAllRecords(paging.getRowBounds(),recordsReq);
    }

    @Override
    public EquipmentRepairRecord findByRecordId(String recordId) {
        return equipmentRepairRecordMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public FindRecordResp findRecordByRecordId(String recordId) {
        FindRecordResp resp = equipmentRepairRecordMapper.findByRecordId(recordId);
        if (null != resp){
            if (!StringUtil.isBlank(resp.getRecordQuality())){
                String[] quality = resp.getRecordQuality().split(",");
                resp.setRecordQuality(quality[0]);
                resp.setRecordUnit(dateformatNum(quality[1]));
            }
            return resp;
        }
        return null;
    }

    @Override
    public String updateRecord(MaintenanceReq req) throws Exception{
        if (StringUtil.isBlank(req.getRecordId())){
            return MaintenanceCode.RECORD_ID_NULL;
        }else if (null == equipmentRepairRecordMapper.selectByPrimaryKey(req.getRecordId())){
            return MaintenanceCode.RECORD_IS_DELETE;
        }
        String code = check(trim(req));
        if (!StringUtil.isBlank(code)) {
            return code;
        }
        //修改
        EquipmentRepairRecord err = new EquipmentRepairRecord();
        err.setRecordId(req.getRecordId());
        err.setEquipmentId(req.getEquipmentId());
        err.setEquipmentOperator(req.getEquipmentOperator());
        err.setOperatorPhone(req.getOperatorPhone());
        err.setRepairDesc(req.getRepairDesc());
        err.setRepairExpense(req.getRepairExpense());
        err.setRepairTime(DateUtil.toDate(req.getRepairTime(), RegexRule.TIME_FORMAT_Y_M_D));
        err.setUpdateTime(new Date());
        String unit = numformatDate(req.getUnit());
        err.setQualityPeriod(req.getQualityPeriod()+","+unit);
        equipmentRepairRecordMapper.updateByPrimaryKeySelective(err);
        return null;
    }

    @Override
    public List<ExportRecordsResp> exportbyIds(String recordIds) {
        String[] ids = recordIds.split(",");
        return formatExportResp(equipmentRepairRecordMapper.exportByIds(ids));
    }

    @Override
    public List<ExportRecordsResp> exportbyParams(ExportRepairRecordsReq repairRecordsReq) {
        return formatExportResp(equipmentRepairRecordMapper.exportByParams(repairRecordsReq));
    }

    private List<ExportRecordsResp> formatExportResp(List<ExportRecordsResp> resps){
        if (resps != null && resps.size() > 0){
            for (ExportRecordsResp resp:
                    resps) {
                if (!StringUtil.isBlank(resp.getRecordQuality())){
                    String[] quality = resp.getRecordQuality().split(",");
                    resp.setRecordQuality(quality[0]);
                    resp.setRecordUnit(dateformatNum(quality[1]));
                }
            }
            return resps;
        }
        return null;
    }

    private String numformatDate(String dateString){
        if (dateString.equals("1")){
            return "天";
        }else if (dateString.equals("2")){
            return "月";
        }else if (dateString.equals("3")){
            return "年";
        }
        return null;
    }

    private String dateformatNum(String dateString){
        if (dateString.equals("天")){
            return "1";
        }else if (dateString.equals("月")){
            return "2";
        }else if (dateString.equals("年")){
            return "3";
        }
        return null;
    }

    MaintenanceReq trim(MaintenanceReq maintenanceReq) {
        if (!StringUtil.isEmpty(maintenanceReq.getEquipmentId())) {
            maintenanceReq.setEquipmentId(maintenanceReq.getEquipmentId().trim());
        }
        if (!StringUtil.isEmpty(maintenanceReq.getEquipmentOperator())) {
            maintenanceReq.setEquipmentOperator(maintenanceReq.getEquipmentOperator().trim());
        }
        if (!StringUtil.isEmpty(maintenanceReq.getOperatorPhone())) {
            maintenanceReq.setOperatorPhone(maintenanceReq.getOperatorPhone().trim());
        }
        if (!StringUtil.isEmpty(maintenanceReq.getRepairDesc())) {
            maintenanceReq.setRepairDesc(maintenanceReq.getRepairDesc().trim());
        }
        if (!StringUtil.isEmpty(maintenanceReq.getRepairExpense())) {
            maintenanceReq.setRepairExpense(maintenanceReq.getRepairExpense().trim());
        }
        if (!StringUtil.isEmpty(maintenanceReq.getRepairTime())) {
            maintenanceReq.setRepairTime(maintenanceReq.getRepairTime().trim());
        }
        if (!StringUtil.isEmpty(maintenanceReq.getQualityPeriod())) {
            maintenanceReq.setQualityPeriod(maintenanceReq.getQualityPeriod().trim());
        }
        return maintenanceReq;
    }

    /**
     * 校验
     * @param maintenanceReq
     * @return
     */
    public String check(MaintenanceReq maintenanceReq) {
        if (StringUtil.isBlank(maintenanceReq.getEquipmentOperator())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else if (!Validate.isCommonString(maintenanceReq.getEquipmentOperator(), Quantity.FIFTEEN)) {
            return PatrolCode.EQUIPMENTOPERATOR_ERROR;
        }
        if (StringUtil.isBlank(maintenanceReq.getOperatorPhone())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else if (!Validate.isCommonString(maintenanceReq.getOperatorPhone(), Quantity.FIFTEEN)) {
            return PatrolCode.OPERATORPHONE_ERROR;
        }
        if (StringUtil.isBlank(maintenanceReq.getRepairDesc())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else if (maintenanceReq.getRepairDesc().length() > Quantity.FIVE_HUNDRED) {
            return EquipmentCode.REPAIRDESC_LENGTH_ERROR;
        }
        if (StringUtil.isBlank(maintenanceReq.getRepairExpense())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else if (!maintenanceReq.getRepairExpense().matches(PatrolConstant.PRICE_RULR)) {
            return EquipmentCode.REPAIREXPENSE_RULE_ERROR;
        }
        if (StringUtil.isBlank(maintenanceReq.getRepairTime())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else if (!maintenanceReq.getRepairTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
            return EquipmentCode.REPAIRTIME_RULE_ERROR;
        }
        if (StringUtil.isBlank(maintenanceReq.getQualityPeriod())){
            return MaintenanceCode.QUALITY_NULL;
        }else if (!maintenanceReq.getQualityPeriod().matches(PatrolConstant.QUALITY_RULE)){
            return MaintenanceCode.QUALITY_RULE;
        }
        if (!StringUtil.isBlank(maintenanceReq.getEquipmentId())
                && null == patrolEquipmentMapper.selectByPrimaryKey(maintenanceReq.getEquipmentId())){
            return MaintenanceCode.EQUIPMENT_IS_DELETE;
        }
        if (!StringUtil.isBlank(maintenanceReq.getEquipmentId())
                && !StringUtil.isBlank(maintenanceReq.getEquipmentNo())){
            PatrolEquipment equipment = patrolEquipmentMapper.selectByPrimaryKey(maintenanceReq.getEquipmentId());
            if (null != equipment && !equipment.getEquipmentNo().equals(maintenanceReq.getEquipmentNo())){
                return MaintenanceCode.EQUIPMENTID_NO_MATCH_ERROR;
            }
        }
        return null;
    }

}

package com.betel.estatemgmt.business.propertyapp.check.service.impl;

import com.alibaba.fastjson.JSON;
import com.betel.estatemgmt.business.propertyapp.check.code.CheckCode;
import com.betel.estatemgmt.business.propertyapp.check.constant.CheckConstant;
import com.betel.estatemgmt.business.propertyapp.check.model.EmployeeResp;
import com.betel.estatemgmt.business.propertyapp.check.model.FindDeviceInfoResp;
import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.propertyapp.check.model.SaveCheckInfoReq;
import com.betel.estatemgmt.business.propertyapp.check.service.CheckService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.patrol.model.MaintenanceReq;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.patrol.*;
import com.betel.estatemgmt.common.mapper.system.SystemPictureMapper;
import com.betel.estatemgmt.common.model.patrol.*;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.DateUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * app设施巡检实现类...
 * </p>
 * ClassName: CheckServiceImpl <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 11:35 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CheckServiceImpl implements CheckService {

    private static final Logger LOG = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Autowired
    PatrolEquipmentMapper patrolEquipmentMapper;

    @Autowired
    SystemPictureMapper pictureMapper;

    @Autowired
    PatrolRecordMapper patrolRecordMapper;

    @Autowired
    PatrolRecordPicMapper patrolRecordPicMapper;
    @Autowired
    EquipmentTypeMapper equipmentTypeMapper;
    @Autowired
    EquipmentRepairRecordMapper equipmentRepairRecordMapper;

    /**
     * 查询设备信息（编号、名称、描述、位置）
     *
     * @param equipmentId
     * @return
     */
    @Override
    public FindDeviceInfoResp findDeviceInfo(String equipmentId,String estateId) {
        PatrolEquipment patrolEquipment = patrolEquipmentMapper.findByEquipmentIdAndEstateId(equipmentId,estateId);
        if (LOG.isInfoEnabled()){
            LOG.info("----------patrolEquipment----------"+patrolEquipment);
        }
        if (null != patrolEquipment) {
            FindDeviceInfoResp findDeviceInfoResp = new FindDeviceInfoResp();
            findDeviceInfoResp.setEquipmentDesc(patrolEquipment.getEquipmentDesc());
            findDeviceInfoResp.setEquipmentLocation(patrolEquipment.getEquipmentLocation());
            findDeviceInfoResp.setEquipmentName(patrolEquipment.getEquipmentName());
            findDeviceInfoResp.setEquipmentNo(patrolEquipment.getEquipmentNo());
            //ADD
            findDeviceInfoResp.setDeadline(DateUtil.toString(patrolEquipment.getDeadline(), "yyyy-MM-dd"));
            findDeviceInfoResp.setEquipmentProducer(patrolEquipment.getEquipmentProducer());
            findDeviceInfoResp.setProducerPhone(patrolEquipment.getProducerPhone());
            findDeviceInfoResp.setEquipmentOperator(patrolEquipment.getEquipmentOperator());
            findDeviceInfoResp.setOperatorPhone(patrolEquipment.getOperatorPhone());
            //迭代九
            findDeviceInfoResp.setTypeName(equipmentTypeMapper.selectByPrimaryKey(patrolEquipment.getEquipmentType()).getTypeName());
            if (null != patrolEquipment.getCheckCycle()) {
                findDeviceInfoResp.setCheckCycle(String.valueOf(patrolEquipment.getCheckCycle()));
            }
            if (null != patrolEquipment.getIsCheck()) {
                findDeviceInfoResp.setIsCheck(patrolEquipment.getIsCheck().toString());
            }
            if (null != patrolEquipment.getEquipmentCreateTime()) {
                findDeviceInfoResp.setEquipmentCreateTime(DateUtil.toString(patrolEquipment.getEquipmentCreateTime(), RegexRule.TIME_FORMAT_Y_M_D));
            }
            if (null != patrolEquipment.getQualityPeriod()) {
                findDeviceInfoResp.setQualityPeriod(patrolEquipment.getQualityPeriod().toString());
            }
            if (null != patrolEquipment.getUnit()) {
                findDeviceInfoResp.setUnit(patrolEquipment.getUnit().toString());
            }
            if (LOG.isInfoEnabled()){
                LOG.info("----------findDeviceInfoResp----------"+findDeviceInfoResp);
            }
            return findDeviceInfoResp;
        }
        return null;
    }

    /**
     * 保存多张图片
     *
     * @param pictures
     */
    @Override
    public void savePictures(List<SystemPicture> pictures) {
        pictureMapper.savePictures(pictures);
    }

    /**
     * 保存巡检记录
     *
     * @param saveCheckInfoReq
     */
    @Override
    public String saveCheckInfo(SaveCheckInfoReq saveCheckInfoReq, String userId) throws ParseException {
        //多次提交唯一标识
        if (StringUtil.isBlank(saveCheckInfoReq.getSerialNo())) {
            return CheckCode.SERIAL_NO_IS_NULL_ERROR;
        } else if (StringUtil.isBlank(saveCheckInfoReq.getEmployeeName())) {
            return CheckCode.EMPLOYEE_NAME_IS_NULL;
        } else {
            PatrolRecord record = new PatrolRecord();
            record.setSerialNo(saveCheckInfoReq.getSerialNo());
            List<PatrolRecord> recordList = patrolRecordMapper.findList(record);
            if (recordList.size() == Quantity.ZERO) {
                //先保存巡检记录到patrol_record
                record = new PatrolRecord();
                record.setEquipmentId(saveCheckInfoReq.getEquipmentId());
                record.setEmployeeId(userId);
                record.setEmployeeName(saveCheckInfoReq.getEmployeeName());
                record.setRecordStatus(saveCheckInfoReq.getRecordStatus());
                record.setRecordDesc(saveCheckInfoReq.getRecordDesc());
                //isDelete:0为未删除；
                record.setIsDelete(Quantity.ZERO);
                record.setSerialNo(saveCheckInfoReq.getSerialNo());
                //Quantity.TWO 已巡检
                record.setIsPatrol(String.valueOf(Quantity.TWO));
                //判断定时任务里是否存在任务
                //去掉待巡检的任务
                String recordId = getRecorId(saveCheckInfoReq);
                if (!StringUtil.isBlank(recordId)) {
                    record.setRecordId(recordId);
                    record.setUpdateTime(new Date());
                    patrolRecordMapper.updateByPrimaryKeySelective(record);
                    //保存图片
                    List<PatrolRecordPic> pics = new ArrayList<>();
                    if (!StringUtil.isBlank(saveCheckInfoReq.getPicIds())) {
                        String[] ids = saveCheckInfoReq.getPicIds().split(",");
                        for (String id : ids) {
                            PatrolRecordPic pic = new PatrolRecordPic();
                            pic.setUpdateTime(new Date());
                            pic.setCreateTime(new Date());
                            pic.setPictureId(id);
                            pic.setRecordId(record.getRecordId());
                            pics.add(pic);
                        }
                        patrolRecordPicMapper.addPics(pics);
                    }
                } else {
                    //无巡检任务
                    if (!StringUtil.isBlank(saveCheckInfoReq.getCreateTime())) {
                        record.setUpdateTime(DateUtil.toDate(saveCheckInfoReq.getCreateTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
                        record.setCreateTime(DateUtil.toDate(saveCheckInfoReq.getCreateTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
                    } else {
                        record.setCreateTime(new Date());
                        record.setUpdateTime(new Date());
                    }
                    //没找到时设置
                    record.setEstateId(saveCheckInfoReq.getEstateId());
                    int flag = patrolRecordMapper.insert(record);
                    //保存图片
                    List<PatrolRecordPic> pics = new ArrayList<>();
                    if (!StringUtil.isBlank(saveCheckInfoReq.getPicIds())) {
                        String[] ids = saveCheckInfoReq.getPicIds().split(",");
                        for (String id : ids) {
                            PatrolRecordPic pic = new PatrolRecordPic();
                            pic.setUpdateTime(new Date());
                            pic.setCreateTime(new Date());
                            pic.setPictureId(id);
                            pic.setRecordId(record.getRecordId());
                            pics.add(pic);
                        }
                        patrolRecordPicMapper.addPics(pics);
                    }
                }
            }
            return StatusCode.SUCCESS;
        }
    }

    /**
     * 获得待巡检任务已被巡检的任务
     *
     * @return
     */
    private String getRecorId(SaveCheckInfoReq saveCheckInfoReq) {
        List<Record> records = patrolRecordMapper.selectCheckList(saveCheckInfoReq.getEstateId());
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getEquipmentId().equals(saveCheckInfoReq.getEquipmentId())) {
                return records.get(i).getRecordId();
            }
        }
        return null;
    }

    @Override
    public List<Record> findAllRecordList(Paging<Record> pager, PatrolReq patrolReq) {
        //待巡检
        if (patrolReq.getTab1().equals(String.valueOf(Quantity.ONE))) {
            return findNotPostList(pager, patrolReq);
        }
        //已巡检
        if (patrolReq.getTab1().equals(String.valueOf(Quantity.TWO))) {
            return patrolRecordMapper.findAllRecordList(pager.getRowBounds(), patrolReq);
        }
        //过期
        if (patrolReq.getTab1().equals(String.valueOf(Quantity.THREE))) {
            return findPostList(pager, patrolReq);
        }
        return null;
    }

    /**
     * 查询未过期的待巡检任务
     *
     * @param pager
     * @param patrolReq
     * @return
     */
    public List<Record> findNotPostList(Paging<Record> pager, PatrolReq patrolReq) {
        return patrolRecordMapper.findNotPostList(pager.getRowBounds(), patrolReq);
    }

    /**
     * 查询过期的待巡检任务
     *
     * @param pager
     * @param patrolReq
     * @return
     */
    public List<Record> findPostList(Paging<Record> pager, PatrolReq patrolReq) {
        return patrolRecordMapper.findPostList(pager.getRowBounds(), patrolReq);
    }

    @Override
    public Record findRecordDetail(PatrolReq patrolReq) {
        Record record = patrolRecordMapper.findRecordDetail(patrolReq);
        if (null != record) {
            String recordId = record.getRecordId();
            List<SystemPicture> systemPictures = patrolRecordPicMapper.findPictureByRecordId(recordId);
            List<String> pictureUrls = new ArrayList<>();
            for (int i = 0; i < systemPictures.size(); i++) {
                if (!StringUtil.isBlank(systemPictures.get(i).getPictureUrl())) {
                    pictureUrls.add(ConfigManager.read(ConfigName.FILE_SERVER) + systemPictures.get(i).getPictureUrl());
                }
            }
            record.setPictureUrls(pictureUrls);
        }
        return record;
    }

    @Override
    public String deleteRecord(PatrolReq patrolReq) {
        patrolReq.setIsDelete(String.valueOf(Quantity.ONE));
        if (StringUtil.isBlank(patrolReq.getRecordIds())) {
            return CheckCode.RECORD_ID_IS_NULL;
        } else if (StringUtil.isBlank(Tool.checkIdIsNull(patrolReq.getRecordIds()))) {
            return CheckCode.RECORD_ID_IS_NULL;
        } else {
            String[] arrIds = Tool.getIdArrOfStringType(patrolReq.getRecordIds());
            patrolReq.setArrIds(arrIds);
            patrolRecordMapper.deleteBatchRecord(patrolReq);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public List<EquipmentType> findTypeList(PatrolReq patrolReq) {
        return equipmentTypeMapper.findTypeList(patrolReq);
    }

    @Override
    public List<MaintenanceResp> findMaintenanceList(MaintenanceReq maintenanceReq) {
        List<MaintenanceResp> maintenanceRespList = equipmentRepairRecordMapper.findMaintenanceList(maintenanceReq);
        for (int i = 0; i < maintenanceRespList.size(); i++) {
            String temp = maintenanceRespList.get(i).getQualityPeriod();
            if (!StringUtil.isBlank(temp)) {
                if (temp.indexOf("月") != Quantity.FU_ZERO) {
                    maintenanceRespList.get(i).setQualityPeriod(temp.replaceAll(",", "个"));
                } else {
                    maintenanceRespList.get(i).setQualityPeriod(temp.replaceAll(",", ""));
                }
            }
        }
        return maintenanceRespList;
    }

    @Override
    public List<PatrolEquipmentResp> findAllEquipmentList(Paging<PatrolEquipmentResp> pager, PatrolReq patrolReq) {
        return patrolEquipmentMapper.findAllEquipmentList(pager.getRowBounds(), patrolReq);
    }

    @Override
    public PatrolEquipment findEquipmentDetail(PatrolReq patrolReq) throws Exception {
        PatrolEquipment patrolEquipment = patrolEquipmentMapper.selectByPrimaryKey(patrolReq.getEquipmentId());
        return patrolEquipment;
    }

    @Override
    public String insertForDate() throws Exception {
        List<PatrolEquipment> pe = findEqpmtListForDate();
        for (int i = 0; i < pe.size(); i++) {
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setRecordId(UuidUtil.create());
            patrolRecord.setEquipmentId(pe.get(i).getEquipmentId());
            patrolRecord.setCreateTime(DateUtil.toDate(DateUtil.toString(new Date(), RegexRule.TIME_FORMAT_Y_M_D) + " 00:00:00", RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
            patrolRecord.setIsDelete(Quantity.ZERO);
            patrolRecord.setIsPatrol(String.valueOf(Quantity.ONE));
            patrolRecord.setSerialNo(UUID.randomUUID().toString() + System.currentTimeMillis());
            patrolRecord.setEstateId(pe.get(i).getEstateId());
            patrolRecordMapper.insertSelective(patrolRecord);
            //推送
            push(patrolRecord);
        }
        return StatusCode.SUCCESS;
    }


    @Override
    public String insertForWeek() throws Exception {
        List<PatrolEquipment> pe = findEqpmtListForWeek();
        for (int i = 0; i < pe.size(); i++) {
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setRecordId(UuidUtil.create());
            patrolRecord.setEquipmentId(pe.get(i).getEquipmentId());
            patrolRecord.setCreateTime(DateUtil.toDate(DateUtil.toString(new Date(), RegexRule.TIME_FORMAT_Y_M_D) + " 00:00:00", RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
            patrolRecord.setIsDelete(Quantity.ZERO);
            patrolRecord.setIsPatrol(String.valueOf(Quantity.ONE));
            patrolRecord.setSerialNo(UUID.randomUUID().toString() + System.currentTimeMillis());
            patrolRecord.setEstateId(pe.get(i).getEstateId());
            patrolRecordMapper.insertSelective(patrolRecord);
            push(patrolRecord);
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public String insertForMonth() throws Exception {
        List<PatrolEquipment> pe = findEqpmtListForMonth();
        for (int i = 0; i < pe.size(); i++) {
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setRecordId(UuidUtil.create());
            patrolRecord.setEquipmentId(pe.get(i).getEquipmentId());
            patrolRecord.setCreateTime(DateUtil.toDate(DateUtil.toString(new Date(), RegexRule.TIME_FORMAT_Y_M_D) + " 00:00:00", RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
            patrolRecord.setIsDelete(Quantity.ZERO);
            patrolRecord.setIsPatrol(String.valueOf(Quantity.ONE));
            patrolRecord.setSerialNo(UUID.randomUUID().toString() + System.currentTimeMillis());
            patrolRecord.setEstateId(pe.get(i).getEstateId());
            patrolRecordMapper.insertSelective(patrolRecord);
            push(patrolRecord);
        }
        return StatusCode.SUCCESS;
    }


    @Override
    public String insertForQuarter() throws Exception {
        List<PatrolEquipment> pe = findEqpmtListForQuarter();
        for (int i = 0; i < pe.size(); i++) {
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setRecordId(UuidUtil.create());
            patrolRecord.setEquipmentId(pe.get(i).getEquipmentId());
            patrolRecord.setCreateTime(DateUtil.toDate(DateUtil.toString(new Date(), RegexRule.TIME_FORMAT_Y_M_D) + " 00:00:00", RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
            patrolRecord.setIsDelete(Quantity.ZERO);
            patrolRecord.setIsPatrol(String.valueOf(Quantity.ONE));
            patrolRecord.setSerialNo(UUID.randomUUID().toString() + System.currentTimeMillis());
            patrolRecord.setEstateId(pe.get(i).getEstateId());
            patrolRecordMapper.insertSelective(patrolRecord);
            push(patrolRecord);
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public String insertForYear() throws Exception {
        List<PatrolEquipment> pe = findEqpmtListForYear();
        for (int i = 0; i < pe.size(); i++) {
            PatrolRecord patrolRecord = new PatrolRecord();
            patrolRecord.setRecordId(UuidUtil.create());
            patrolRecord.setEquipmentId(pe.get(i).getEquipmentId());
            patrolRecord.setCreateTime(DateUtil.toDate(DateUtil.toString(new Date(), RegexRule.TIME_FORMAT_Y_M_D) + " 00:00:00", RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
            patrolRecord.setIsDelete(Quantity.ZERO);
            patrolRecord.setIsPatrol(String.valueOf(Quantity.ONE));
            patrolRecord.setSerialNo(UUID.randomUUID().toString() + System.currentTimeMillis());
            patrolRecord.setEstateId(pe.get(i).getEstateId());
            patrolRecordMapper.insertSelective(patrolRecord);
            push(patrolRecord);
        }
        return StatusCode.SUCCESS;
    }

    /**
     * 查询每日巡检设备信息
     *
     * @return
     */
    public List<PatrolEquipment> findEqpmtListForDate() {
        SaveCheckInfoReq saveCheckInfoReq = new SaveCheckInfoReq();
        saveCheckInfoReq.setCheckCycle(String.valueOf(Quantity.ONE));
        return patrolEquipmentMapper.findEqpmtListByCycle(saveCheckInfoReq);
    }

    /**
     * 查询每周巡检设备信息
     *
     * @return
     */
    public List<PatrolEquipment> findEqpmtListForWeek() {
        SaveCheckInfoReq saveCheckInfoReq = new SaveCheckInfoReq();
        saveCheckInfoReq.setCheckCycle(String.valueOf(Quantity.TWO));
        return patrolEquipmentMapper.findEqpmtListByCycle(saveCheckInfoReq);
    }

    /**
     * 查询每月巡检设备信息
     *
     * @return
     */
    public List<PatrolEquipment> findEqpmtListForMonth() {
        SaveCheckInfoReq saveCheckInfoReq = new SaveCheckInfoReq();
        saveCheckInfoReq.setCheckCycle(String.valueOf(Quantity.THREE));
        return patrolEquipmentMapper.findEqpmtListByCycle(saveCheckInfoReq);
    }

    /**
     * 查询每季度巡检设备信息
     *
     * @return
     */
    public List<PatrolEquipment> findEqpmtListForQuarter() {
        SaveCheckInfoReq saveCheckInfoReq = new SaveCheckInfoReq();
        saveCheckInfoReq.setCheckCycle(String.valueOf(Quantity.FOUR));
        return patrolEquipmentMapper.findEqpmtListByCycle(saveCheckInfoReq);
    }

    /**
     * 查询每年巡检设备信息
     *
     * @return
     */
    public List<PatrolEquipment> findEqpmtListForYear() {
        SaveCheckInfoReq saveCheckInfoReq = new SaveCheckInfoReq();
        saveCheckInfoReq.setCheckCycle(String.valueOf(Quantity.FIVE));
        return patrolEquipmentMapper.findEqpmtListByCycle(saveCheckInfoReq);
    }

    /**
     * 推送
     *
     * @param patrolRecord
     * @throws Exception
     */
    private void push(PatrolRecord patrolRecord) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("privilegeId", 459);
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL) + "/httpclientOA/estatemgmt/v1/findEmp", jsonParam, null);
        List<String> phone = new ArrayList<>();
        if (response != null) {
            if (null != response.getData()) {
                LOG.debug("=======================================" + response.getData().toString());
                Type type = new TypeToken<List<EmployeeResp>>() {
                }.getType();
                List<EmployeeResp> lists = gson.fromJson(response.getData().toString(), type);
                for (int i = 0; i < lists.size(); i++) {
                    phone.add(lists.get(i).getPhoneNum());
                }
            }
        }
        SendMessage send = new SendMessage();
        send.setSendId(patrolRecord.getRecordId());
        send.setSendTitle(CheckConstant.TO_CHECK_TITLE);
        send.setSendNo(CheckConstant.TO_CHECK_SENDNO);
        send.setSendType(CheckConstant.PUSH_SENDTYPE);
        send.setSendContent(CheckConstant.TO_CHECK__SEND_CONTENT);
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        //推送
        if (phone.size() > 0) {
            PropertyPushUtil.pushList(phone, pushInfo, true);
        }
    }

}

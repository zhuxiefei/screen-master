package com.betel.estatemgmt.business.web.patrol.service.impl;

import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.patrol.code.EquipmentCode;
import com.betel.estatemgmt.business.web.patrol.code.PatrolCode;
import com.betel.estatemgmt.business.web.patrol.constant.PatrolConstant;
import com.betel.estatemgmt.business.web.patrol.model.MaintenanceReq;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.business.web.patrol.service.PatrolService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.patrol.*;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.common.mapper.system.SystemFileMapper;
import com.betel.estatemgmt.common.mapper.system.SystemPictureMapper;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.model.patrol.PatrolRecordPicKey;
import com.betel.estatemgmt.common.model.system.SystemFile;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.QRcode.PhotoProDto;
import com.betel.estatemgmt.utils.QRcode.ZXingCodeUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: PatrolServiceImpl <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 10:06 <br/>
 * @Version: 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PatrolServiceImpl implements PatrolService {
    private static final Logger LOG = LoggerFactory.getLogger(PatrolServiceImpl.class);
    @Autowired
    private PatrolEquipmentMapper patrolEquipmentMapper;
    @Autowired
    private PatrolRecordMapper patrolRecordMapper;
    @Autowired
    private PatrolRecordPicMapper patrolRecordPicMapper;
    @Autowired
    private SystemPictureMapper systemPictureMapper;
    @Autowired
    private SystemFileMapper fileMapper;
    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;
    @Autowired
    private EquipmentRepairRecordMapper equipmentRepairRecordMapper;

    @Override
    public List<PatrolEquipmentResp> findAllEquipmentList(Paging<PatrolEquipmentResp> pager, PatrolReq patrolReq) {
        return patrolEquipmentMapper.findAllWebEquipmentList(pager.getRowBounds(), patrolReq);
    }

    @Override
    public PatrolEquipmentResp findEquipmentDetail(PatrolReq patrolReq) throws Exception {
        PatrolEquipment patrolEquipment = patrolEquipmentMapper.selectByPrimaryKey(patrolReq.getEquipmentId());
        if (null == patrolEquipment) {
            return null;
        }
        PatrolEquipmentResp patrolEquipmentResp = new PatrolEquipmentResp();
        patrolEquipmentResp.setEquipmentId(patrolEquipment.getEquipmentId());
        patrolEquipmentResp.setEquipmentNo(patrolEquipment.getEquipmentNo());
        patrolEquipmentResp.setEquipmentType(patrolEquipment.getEquipmentType());
        if (null != patrolEquipment) {
            EquipmentType type = equipmentTypeMapper.selectByPrimaryKey(patrolEquipment.getEquipmentType());
            patrolEquipmentResp.setTypeName(type == null ? null : type.getTypeName());
        }
        patrolEquipmentResp.setEquipmentName(patrolEquipment.getEquipmentName());
        patrolEquipmentResp.setEquipmentLocation(patrolEquipment.getEquipmentLocation());
        patrolEquipmentResp.setEquipmentDesc(patrolEquipment.getEquipmentDesc());
        if (null != patrolEquipment.getIsCheck()) {
            patrolEquipmentResp.setIsCheck(patrolEquipment.getIsCheck().toString());
        }
        if (null != patrolEquipment.getCheckCycle()) {
            patrolEquipmentResp.setCheckCycle(patrolEquipment.getCheckCycle().toString());
        }
        if (null != patrolEquipment.getEquipmentCreateTime()) {
            patrolEquipmentResp.setEquipmentCreateTime(DateUtil.toString(patrolEquipment.getEquipmentCreateTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
        }
        patrolEquipmentResp.setQualityPeriod(patrolEquipment.getQualityPeriod());
        if (null != patrolEquipment.getDeadline()) {
            patrolEquipmentResp.setDeadline(DateUtil.toString(patrolEquipment.getDeadline(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
        }
        if (null != patrolEquipment.getEquipmentProducer()) {
            patrolEquipmentResp.setEquipmentProducer(patrolEquipment.getEquipmentProducer());
        }
        if (null != patrolEquipment.getProducerPhone()) {
            patrolEquipmentResp.setProducerPhone(patrolEquipment.getProducerPhone());
        }
        if (null != patrolEquipment.getEquipmentOperator()) {
            patrolEquipmentResp.setEquipmentOperator(patrolEquipment.getEquipmentOperator());
        }
        if (null != patrolEquipment.getOperatorPhone()) {
            patrolEquipmentResp.setOperatorPhone(patrolEquipment.getOperatorPhone());
        }
        if (null != patrolEquipment.getRepairNumber()) {
            patrolEquipmentResp.setRepairNumber(patrolEquipment.getRepairNumber());
        }
        if (null != patrolEquipment.getUnit()) {
            patrolEquipmentResp.setUnit(patrolEquipment.getUnit());
        }
        if (null != patrolEquipment.getCreateTime()) {
            patrolEquipmentResp.setCreateTime(DateUtil.toString(patrolEquipment.getCreateTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
        }
        if (null != patrolEquipment.getUpdateTime()) {
            patrolEquipmentResp.setUpdateTime(DateUtil.toString(patrolEquipment.getUpdateTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
        }
        return patrolEquipmentResp;
    }


    @Override
    public PatrolEquipment findQrcode(PatrolReq patrolReq) throws Exception {
        PatrolEquipment patrolEquipment = patrolEquipmentMapper.selectByPrimaryKey(patrolReq.getEquipmentId());
        if (null == patrolEquipment) {
            return null;
        }
        patrolEquipment.setEquipmentDesc(null);
        patrolEquipment.setUpdateTime(null);
        patrolEquipment.setCreateTime(null);
        //解析base64的二维码
        if (null != patrolEquipment && !StringUtil.isBlank(patrolEquipment.getEquipmentQRCode())) {
            SystemFile file = fileMapper.selectByPrimaryKey(patrolEquipment.getEquipmentQRCode());
            if (file != null) {
                try {
                    patrolEquipment.setEquipmentQRCode(FileUtil.readTxt(PatrolConstant.PRIVACY_FILE_PATH + file.getFileUrl()));
                } catch (Exception e) {
                    LOG.error("---------没有二维码图片------------", e);
                }
            }
        }
        return patrolEquipment;
    }

    @Override
    public String updateEquipment(PatrolReq patrolReq) throws Exception {
        patrolReq = trimPatrolReq(patrolReq);
        String code = checkUpdateEquipment(patrolReq);
        PatrolEquipment curEqupmt = patrolEquipmentMapper.selectByPrimaryKey(patrolReq.getEquipmentId());
        if (null != code) {
            return code;
        } else if (null == equipmentTypeMapper.selectByPrimaryKey(patrolReq.getEquipmentType())) {
            return EquipmentCode.EQUIPMENT_TYPE_IS_NOT_EXIST;
        } else if (null != patrolEquipmentMapper.selectByEquipment(patrolReq)) {
            return PatrolCode.EQUIPMENT_NO_IS_EXIST;
        } else if (null == curEqupmt) {
            return PatrolCode.EQUIPMENT_IS_NOT_EXIST;
        } else {
            PatrolEquipment patrolEquipment = new PatrolEquipment();
            patrolEquipment.setEquipmentId(patrolReq.getEquipmentId());
            patrolEquipment.setEquipmentNo(patrolReq.getEquipmentNo());
            patrolEquipment.setEquipmentName(patrolReq.getEquipmentName());
            patrolEquipment.setEquipmentLocation(patrolReq.getEquipmentLocation());
            patrolEquipment.setEquipmentDesc(patrolReq.getEquipmentDesc());
            //add
            patrolEquipment.setDeadline(DateUtil.toDate(patrolReq.getDeadline(), "yyyy-MM-dd"));
            patrolEquipment.setEquipmentProducer(patrolReq.getEquipmentProducer());
            patrolEquipment.setProducerPhone(patrolReq.getProducerPhone());
            patrolEquipment.setEquipmentOperator(patrolReq.getEquipmentOperator());
            patrolEquipment.setOperatorPhone(patrolReq.getOperatorPhone());
            //add迭代九
            if (!StringUtil.isBlank(patrolReq.getIsCheck())) {
                patrolEquipment.setIsCheck(Integer.valueOf(patrolReq.getIsCheck()));
            }
            if (!StringUtil.isBlank(patrolReq.getCheckCycle())) {
                patrolEquipment.setCheckCycle(Integer.valueOf(patrolReq.getCheckCycle()));
            }
            if (!StringUtil.isBlank(patrolReq.getEquipmentCreateTime())) {
                patrolEquipment.setEquipmentCreateTime(DateUtil.toDate(patrolReq.getEquipmentCreateTime(), RegexRule.TIME_FORMAT_Y_M_D));
            }
            if (!StringUtil.isBlank(patrolReq.getQualityPeriod())) {
                patrolEquipment.setQualityPeriod(patrolReq.getQualityPeriod());
            }
            if (!StringUtil.isBlank(patrolReq.getUnit())) {
                patrolEquipment.setUnit(Integer.valueOf(patrolReq.getUnit()));
            }
            if (!StringUtil.isBlank(patrolReq.getEquipmentType())) {
                patrolEquipment.setEquipmentType(patrolReq.getEquipmentType());
            }
            patrolEquipment.setUpdateTime(new Date());

            int flag = patrolEquipmentMapper.updateByPrimaryKeySelective(patrolEquipment);
            //删除旧二维码信息
            SystemFile systemFile = fileMapper.selectByPrimaryKey(curEqupmt.getEquipmentQRCode());
            if (null != systemFile) {
                String fullPath = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR) + systemFile.getFileUrl();
                //最后删除临时文件
                try {
                    if (null != fullPath) {
                        //删除物理路径
                        FileUtil.deletefile(fullPath);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("--------------删除物理文件成功标识:" + flag);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("-------删除物理文件失败--------", e);
                }
            }
            fileMapper.deleteByPrimaryKey(patrolEquipment.getEquipmentQRCode());
            String qRcode = createQRcode(patrolEquipment);
            if (StatusCode.SUCCESS.equals(qRcode)) {
                return StatusCode.SUCCESS;
            } else {
                return StatusCode.FAILURE;
            }
        }
    }

    @Override
    public String deleteEquipment(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getEquipmentIds())) {
            return PatrolCode.EQUIPMENT_ID_IS_NULL;
        } else if (StringUtil.isBlank(Tool.checkIdIsNull(patrolReq.getEquipmentIds()))) {
            return PatrolCode.EQUIPMENT_ID_IS_NULL;
        } else {
            String[] idArr = Tool.getIdArrOfStringType(patrolReq.getEquipmentIds());
            List<PatrolEquipment> patrolEquipmentList = patrolEquipmentMapper.findBatchEquipmentByIds(idArr);
            List<String> sysFileIds = new ArrayList<>();
            for (int i = 0; i < patrolEquipmentList.size(); i++) {
                if (!StringUtil.isBlank(patrolEquipmentList.get(i).getEquipmentQRCode())) {
                    sysFileIds.add(patrolEquipmentList.get(i).getEquipmentQRCode());
                }
            }
            if (sysFileIds.size() > 0) {
                List<SystemFile> systemFiles = fileMapper.findBatchQRcodeByFileIds(sysFileIds);
                for (int i = 0; i < systemFiles.size(); i++) {
                    try {
                        String fullPath = ConfigManager.read(ConfigName.PRIVACY_FILE_DIR) + systemFiles.get(i).getFileUrl();
                        boolean flag = FileUtil.deletefile(fullPath);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("--------------删除物理文件成功标识:" + flag);
                        }
                    } catch (Exception e) {
                        LOG.debug("-------------删除物理文件失败--------", e);
                    }
                }
                //删除系统文件信息
                fileMapper.deleteFileList(sysFileIds);
            }
            int deleteRepairRecord = equipmentRepairRecordMapper.deleteByEquipmemtId(idArr);
            int flag = patrolEquipmentMapper.deleteBatchEquipment(idArr);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String addEquipment(PatrolReq patrolReq) throws Exception {
        //判重铺垫
        patrolReq.setEquipmentId(null);
        patrolReq = trimPatrolReq(patrolReq);
        String code = checkAddEquipment(patrolReq);
        if (null != code) {
            return code;
        } else if (null == equipmentTypeMapper.selectByPrimaryKey(patrolReq.getEquipmentType())) {
            return EquipmentCode.EQUIPMENT_TYPE_IS_NOT_EXIST;
        } else if (null != patrolEquipmentMapper.selectByEquipment(patrolReq)) {
            return PatrolCode.EQUIPMENT_NO_IS_EXIST;
        } else {
            PatrolEquipment patrolEquipment = new PatrolEquipment();
            patrolEquipment.setEquipmentId(UuidUtil.create());
            //设备id
            patrolEquipment.setEquipmentType(patrolReq.getEquipmentType());
            patrolEquipment.setEquipmentNo(patrolReq.getEquipmentNo());
            patrolEquipment.setEquipmentName(patrolReq.getEquipmentName());
            patrolEquipment.setEquipmentLocation(patrolReq.getEquipmentLocation());
            if (!StringUtil.isBlank(patrolReq.getIsCheck())) {
                patrolEquipment.setIsCheck(Integer.valueOf(patrolReq.getIsCheck()));
            }
            if (!StringUtil.isBlank(patrolReq.getCheckCycle())) {
                patrolEquipment.setCheckCycle(Integer.valueOf(patrolReq.getCheckCycle()));
            }
            patrolEquipment.setEquipmentDesc(patrolReq.getEquipmentDesc());
            if (!StringUtil.isBlank(patrolReq.getEquipmentCreateTime())) {
                patrolEquipment.setEquipmentCreateTime(DateUtil.toDate(patrolReq.getEquipmentCreateTime(), RegexRule.TIME_FORMAT_Y_M_D));
            }
            if (!StringUtil.isBlank(patrolReq.getQualityPeriod())) {
                patrolEquipment.setQualityPeriod(patrolReq.getQualityPeriod());
            }
            if (!StringUtil.isBlank(patrolReq.getUnit())) {
                patrolEquipment.setUnit(Integer.valueOf(patrolReq.getUnit()));
            }
            patrolEquipment.setCreateTime(new Date());
            patrolEquipment.setUpdateTime(new Date());
            //add
            patrolEquipment.setDeadline(DateUtil.toDate(patrolReq.getDeadline(), "yyyy-MM-dd"));
            patrolEquipment.setEquipmentProducer(patrolReq.getEquipmentProducer());
            patrolEquipment.setProducerPhone(patrolReq.getProducerPhone());
            patrolEquipment.setEquipmentOperator(patrolReq.getEquipmentOperator());
            patrolEquipment.setOperatorPhone(patrolReq.getOperatorPhone());
            patrolEquipment.setEstateId(patrolReq.getEstateId());
            if (LOG.isDebugEnabled()) {
                LOG.debug("生成字符串长度=" + GsonUtil.object2Gson(patrolEquipment).length());
            }
            if (GsonUtil.object2Gson(patrolEquipment).length() > 6000) {
                return PatrolCode.CREATE_QR_TOO_DATE;
            } else {
                int flag = patrolEquipmentMapper.insertSelective(patrolEquipment);
                String qRcode = createQRcode(patrolEquipment);
                if (StatusCode.SUCCESS.equals(qRcode)) {
                    return StatusCode.SUCCESS;
                } else {
                    return StatusCode.FAILURE;
                }
            }
        }
    }

    private String createQRcode(PatrolEquipment patrolEquipment) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("------------------生成二维码--------------start------------");
        }
        patrolEquipment.setEquipmentDesc(null);
        patrolEquipment.setCreateTime(null);
        patrolEquipment.setUpdateTime(null);
        patrolEquipment.setEquipmentQRCode(null);
        EquipmentType equipmentType = equipmentTypeMapper.selectByPrimaryKey(patrolEquipment.getEquipmentType());
        if (null != equipmentType) {
            patrolEquipment.setTypeName(equipmentType.getTypeName());
        }
        //二维码
        PhotoProDto photoProDto = new PhotoProDto();
        photoProDto.setContent(patrolEquipment.getEquipmentId());
        photoProDto.setHeight(250);
        photoProDto.setWidth(250);
        String fullPath = ConfigManager.read(ConfigName.FILE_DIR) + "buildMaterialPic/" + UUID.randomUUID() + ".jpg";
        try {
            BufferedImage bufferedImage = ZXingCodeUtil.buildQRCode(photoProDto);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            Tool.uploadFile(is, fullPath);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------二维码图片服务器地址：" + fullPath);
            }
            String content = "data:image/gif;base64," + ZXingCodeUtil.GetImageStr(fullPath);
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------二维码base64内容-----------" + content);
            }
            String name = FileUtil.uploadFile("/patrolQRcode/" + UUID.randomUUID() + ".txt", content);
            SystemFile file = new SystemFile();
            file.setCreateTime(new Date(System.currentTimeMillis()));
            file.setFileName("patrolQRcode" + UUID.randomUUID());
            file.setFileUrl(name);
            int flag2 = fileMapper.insert(file);
            patrolEquipment.setEquipmentQRCode(file.getFileId());
            patrolEquipment.setEquipmentId(patrolEquipment.getEquipmentId());
            int flag3 = patrolEquipmentMapper.updateByPrimaryKeySelective(patrolEquipment);
            //最后删除临时文件
            try {
                if (null != fullPath) {
                    //删除物理路径
                    boolean flag = FileUtil.deletefile(fullPath);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("--------------删除物理文件成功标识:" + flag);
                    }
                }
            } catch (Exception e) {
                LOG.error("-------删除物理文件失败--------", e);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------------生成二维码-----------end-----------");
            }
        } catch (Exception e) {
            LOG.error("-------创建二维码物理文件失败--------", e);
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public List<Record> findAllRecordList(Paging<Record> pager, PatrolReq patrolReq) {
        return patrolRecordMapper.findAllWebRecordList(pager.getRowBounds(), patrolReq);
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
        patrolReq.setIsDelete(String.valueOf(Quantity.TWO));
        if (StringUtil.isBlank(patrolReq.getRecordIds())) {
            return PatrolCode.RECORD_ID_IS_NULL;
        } else if (StringUtil.isBlank(Tool.checkIdIsNull(patrolReq.getRecordIds()))) {
            return PatrolCode.RECORD_ID_IS_NULL;
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
    public List<MaintenanceResp> findAllMaintenanceList(Paging<MaintenanceResp> pager, PatrolReq patrolReq) {
        List<MaintenanceResp> maintenanceRespList = equipmentRepairRecordMapper.findAllMaintenanceList(pager.getRowBounds(), patrolReq);
        for (int i = 0; i < maintenanceRespList.size(); i++) {
            String temp = maintenanceRespList.get(i).getQualityPeriod();
            if (!StringUtil.isBlank(temp)) {
                if (temp.indexOf("月")!=Quantity.FU_ZERO){
                    maintenanceRespList.get(i).setQualityPeriod(temp.replaceAll(",", "个"));
                }else {
                    maintenanceRespList.get(i).setQualityPeriod(temp.replaceAll(",", ""));
                }
            }
        }
        return maintenanceRespList;
    }

    @Override
    public List<MaintenanceResp> exportList(PatrolReq patrolReq) {
        List<MaintenanceResp> maintenanceRespList = null;
        if (!StringUtil.isBlank(patrolReq.getEquipmentRepairRecordId())) {
            String[] equipmentRepairRecordId = Tool.getIdArrOfStringType(patrolReq.getEquipmentRepairRecordId());
            maintenanceRespList = equipmentRepairRecordMapper.findMaintenanceListById(equipmentRepairRecordId);
        } else {
            MaintenanceReq maintenanceReq = new MaintenanceReq();
            maintenanceReq.setStartTime(patrolReq.getStartTime());
            maintenanceReq.setEndTime(patrolReq.getEndTime());
            maintenanceReq.setEquipmentId(patrolReq.getEquipmentId());
            maintenanceRespList = equipmentRepairRecordMapper.findMaintenanceList(maintenanceReq);
        }
        for (int i = 0; i < maintenanceRespList.size(); i++) {
            String temp = maintenanceRespList.get(i).getQualityPeriod();
            if (!StringUtil.isBlank(temp)) {
                if (temp.indexOf("月")!=Quantity.FU_ZERO){
                    maintenanceRespList.get(i).setQualityPeriod(temp.replaceAll(",", "个"));
                }else {
                    maintenanceRespList.get(i).setQualityPeriod(temp.replaceAll(",", ""));
                }
            }
        }
        return maintenanceRespList;
    }

    @Override
    public PatrolEquipment selectByPrimaryKey(String equipmentId) {
        return patrolEquipmentMapper.selectByPrimaryKey(equipmentId);
    }


    /**
     * 真删除代码，暂时不要删除该代码
     *
     * @param patrolReq
     * @return
     */
    public String deleteTrueRecord(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getRecordIds())) {
            return PatrolCode.RECORD_ID_IS_NULL;
        } else if (StringUtil.isBlank(Tool.checkIdIsNull(patrolReq.getRecordIds()))) {
            return PatrolCode.RECORD_IS_NOT_EXIST;
        } else {
            String[] idArr = Tool.getIdArrOfStringType(patrolReq.getRecordIds());
            //删除图片表
            List<PatrolRecordPicKey> systemPictureList = patrolRecordPicMapper.findSystemPictureByRecordIds(idArr);
            //图片集合
            List<String> pictureIds = new ArrayList<>();
            for (int i = 0; i < systemPictureList.size(); i++) {
                if (null != systemPictureList.get(i).getPictureId()) {
                    pictureIds.add(systemPictureList.get(i).getPictureId());
                }
            }
            if (pictureIds.size() > 0) {
                //批量删除物理文件
                List<SystemPicture> systemPictures = systemPictureMapper.findSysPicByIds(pictureIds);
                for (int i = 0; i < systemPictures.size(); i++) {
                    try {
                        boolean flag = FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + systemPictures.get(i).getPictureUrl());
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("--------------删除物理文件成功标识:" + flag);
                        }
                    } catch (Exception e) {
                        LOG.debug("--------------删除物理文件失败-------------", e);
                    }
                }
                systemPictureMapper.deleteSysPicByIds(pictureIds);
            }
            patrolRecordPicMapper.deleteRecordPictureByRecordIds(idArr);
            patrolRecordMapper.deleteBatchRecord(patrolReq);
            return StatusCode.SUCCESS;
        }
    }

    private PatrolReq trimPatrolReq(PatrolReq patrolReq) {
        if (!StringUtil.isEmpty(patrolReq.getEquipmentNo())) {
            patrolReq.setEquipmentNo(patrolReq.getEquipmentNo().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEquipmentName())) {
            patrolReq.setEquipmentName(patrolReq.getEquipmentName().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEquipmentLocation())) {
            patrolReq.setEquipmentLocation(patrolReq.getEquipmentLocation().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEquipmentDesc())) {
            patrolReq.setEquipmentDesc(patrolReq.getEquipmentDesc().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEquipmentCreateTime())) {
            patrolReq.setEquipmentCreateTime(patrolReq.getEquipmentCreateTime().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getQualityPeriod())) {
            patrolReq.setQualityPeriod(patrolReq.getQualityPeriod().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getDeadline())) {
            patrolReq.setDeadline(patrolReq.getDeadline().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEquipmentProducer())) {
            patrolReq.setEquipmentProducer(patrolReq.getEquipmentProducer());
        }
        if (!StringUtil.isEmpty(patrolReq.getProducerPhone())) {
            patrolReq.setProducerPhone(patrolReq.getProducerPhone());
        }
        if (!StringUtil.isEmpty(patrolReq.getEquipmentOperator())) {
            patrolReq.setEquipmentOperator(patrolReq.getEquipmentOperator());
        }
        if (!StringUtil.isEmpty(patrolReq.getOperatorPhone())) {
            patrolReq.setOperatorPhone(patrolReq.getOperatorPhone());
        }
        return patrolReq;
    }

    private String checkUpdateEquipment(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getEquipmentType())) {
            return EquipmentCode.TYPE_NAME_NULL_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentId())) {
            return PatrolCode.EQUIPMENT_ID_IS_NULL;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentNo())) {
            return PatrolCode.EQUIPMENT_NO_IS_NULL;
        } else if (!patrolReq.getEquipmentNo().matches(RegexRule.SPECIAL_WORD_RULE)) {
            return PatrolCode.EQUIPMENT_NO_IS_NOT_LEGAL;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentName())) {
            return PatrolCode.EQUIPMENT_NAME_IS_NULL;
        } else if (!patrolReq.getEquipmentName().matches(PatrolConstant.EQUIPMENT_NAME_RULE)) {
            return PatrolCode.EQUIPMENT_NAME_IS_NOT_LEGAL;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentLocation())) {
            return PatrolCode.EQUIPMENT_LOCATION_IS_NULL;
        } else if (!patrolReq.getEquipmentLocation().matches(PatrolConstant.EQUIPMENT_LOCATION_RULE)) {
            return PatrolCode.EQUIPMENT_LOCATION_IS_NOT_LEGAL;
        }
        if (!StringUtil.isBlank(patrolReq.getEquipmentDesc())) {
            if (Integer.valueOf(patrolReq.getEquipmentDesc().length()).intValue() > RegexRule.FIVE_THOUSAND) {
                return PatrolCode.EQUIPMENT_DEC_LENGTH_IS_SUPPER_LONG;
            }
        }
        //add
        if (StringUtil.isBlank(patrolReq.getDeadline())) {
            return PatrolCode.DEADLINE_ERROR;
        } else if (!patrolReq.getDeadline().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
            return PatrolCode.DEADLINE_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentProducer())) {
            return PatrolCode.EQUIPMENTPRODUCER_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getProducerPhone())) {
            return PatrolCode.PRODUCERPHONE_ERROR;
        } else if (!Validate.isCommonString(patrolReq.getProducerPhone(), PatrolConstant.QUANTITY_THIRTY)) {
            return PatrolCode.PRODUCERPHONE_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentOperator())) {
            return PatrolCode.EQUIPMENTOPERATOR_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getOperatorPhone())) {
            return PatrolCode.OPERATORPHONE_ERROR;
        } else if (!Validate.isCommonString(patrolReq.getOperatorPhone(), PatrolConstant.QUANTITY_THIRTY)) {
            return PatrolCode.OPERATORPHONE_ERROR;
        }
        //巡检周期
        if (!StringUtil.isBlank(patrolReq.getCheckCycle())) {
            boolean flag1 = String.valueOf(Quantity.ONE).equals(patrolReq.getCheckCycle());
            boolean flag2 = String.valueOf(Quantity.TWO).equals(patrolReq.getCheckCycle());
            boolean flag3 = String.valueOf(Quantity.THREE).equals(patrolReq.getCheckCycle());
            boolean flag4 = String.valueOf(Quantity.FOUR).equals(patrolReq.getCheckCycle());
            boolean flag5 = String.valueOf(Quantity.FIVE).equals(patrolReq.getCheckCycle());
            if (flag1 == false && flag2 == false && flag3 == false && flag4 == false && flag5 == false) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        //是否需要巡检
        if (StringUtil.isBlank(patrolReq.getIsCheck())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            boolean flag1 = String.valueOf(Quantity.ONE).equals(patrolReq.getIsCheck());
            boolean flag2 = String.valueOf(Quantity.TWO).equals(patrolReq.getIsCheck());
            if (flag1 == false && flag2 == false) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        //设备生产日期
        if (StringUtil.isBlank(patrolReq.getEquipmentCreateTime())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            if (!patrolReq.getEquipmentCreateTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
                return PatrolCode.EQUIPMENTCREATETIME_ERROR;
            }
        }
        //保质期
        if (StringUtil.isBlank(patrolReq.getQualityPeriod())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            try {
                int qualityPeriod = Integer.valueOf(patrolReq.getQualityPeriod());
                if (qualityPeriod > Quantity.TEN_HUNDRED) {
                    return PatrolCode.QUALITYPERIOD_MORE_THAN_TEN_HUNDRED;
                }
            } catch (Exception e) {
                return PatrolCode.QUALITYPERIOD_RULE_ERROR;
            }
        }
        //质保期的单位
        if (!StringUtil.isBlank(patrolReq.getUnit())) {
            boolean flag1 = String.valueOf(Quantity.ONE).equals(patrolReq.getUnit());
            boolean flag2 = String.valueOf(Quantity.TWO).equals(patrolReq.getUnit());
            boolean flag3 = String.valueOf(Quantity.THREE).equals(patrolReq.getUnit());
            if (flag1 == false && flag2 == false && flag3 == false) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        return null;
    }

    private String checkAddEquipment(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getEquipmentType())) {
            return EquipmentCode.TYPE_NAME_NULL_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentNo())) {
            return PatrolCode.EQUIPMENT_NO_IS_NULL;
        } else if (!patrolReq.getEquipmentNo().matches(RegexRule.SPECIAL_WORD_RULE)) {
            return PatrolCode.EQUIPMENT_NO_IS_NOT_LEGAL;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentName())) {
            return PatrolCode.EQUIPMENT_NAME_IS_NULL;
        } else if (!patrolReq.getEquipmentName().matches(PatrolConstant.EQUIPMENT_NAME_RULE)) {
            return PatrolCode.EQUIPMENT_NAME_IS_NOT_LEGAL;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentLocation())) {
            return PatrolCode.EQUIPMENT_LOCATION_IS_NULL;
        } else if (!patrolReq.getEquipmentLocation().matches(PatrolConstant.EQUIPMENT_LOCATION_RULE)) {
            return PatrolCode.EQUIPMENT_LOCATION_IS_NOT_LEGAL;
        }
        if (!StringUtil.isBlank(patrolReq.getEquipmentDesc())) {
            if (Integer.valueOf(patrolReq.getEquipmentDesc().length()).intValue() > RegexRule.FIVE_THOUSAND) {
                return PatrolCode.EQUIPMENT_DEC_LENGTH_IS_SUPPER_LONG;
            }
        }
        //add
        if (StringUtil.isBlank(patrolReq.getDeadline())) {
            return PatrolCode.DEADLINE_ERROR;
        } else if (!patrolReq.getDeadline().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
            return PatrolCode.DEADLINE_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentProducer())) {
            return PatrolCode.EQUIPMENTPRODUCER_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getProducerPhone())) {
            return PatrolCode.PRODUCERPHONE_ERROR;
        } else if (!Validate.isCommonString(patrolReq.getProducerPhone(), PatrolConstant.QUANTITY_THIRTY)) {
            return PatrolCode.PRODUCERPHONE_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getEquipmentOperator())) {
            return PatrolCode.EQUIPMENTOPERATOR_ERROR;
        }
        if (StringUtil.isBlank(patrolReq.getOperatorPhone())) {
            return PatrolCode.OPERATORPHONE_ERROR;
        } else if (!Validate.isCommonString(patrolReq.getOperatorPhone(), PatrolConstant.QUANTITY_THIRTY)) {
            return PatrolCode.OPERATORPHONE_ERROR;
        }
        //巡检周期
        if (!StringUtil.isBlank(patrolReq.getCheckCycle())) {
            boolean flag1 = String.valueOf(Quantity.ONE).equals(patrolReq.getCheckCycle());
            boolean flag2 = String.valueOf(Quantity.TWO).equals(patrolReq.getCheckCycle());
            boolean flag3 = String.valueOf(Quantity.THREE).equals(patrolReq.getCheckCycle());
            boolean flag4 = String.valueOf(Quantity.FOUR).equals(patrolReq.getCheckCycle());
            boolean flag5 = String.valueOf(Quantity.FIVE).equals(patrolReq.getCheckCycle());
            if (flag1 == false && flag2 == false && flag3 == false && flag4 == false && flag5 == false) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        //是否需要巡检
        if (StringUtil.isBlank(patrolReq.getIsCheck())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            boolean flag1 = String.valueOf(Quantity.ONE).equals(patrolReq.getIsCheck());
            boolean flag2 = String.valueOf(Quantity.TWO).equals(patrolReq.getIsCheck());
            if (flag1 == false && flag2 == false) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        //设备生产日期
        if (StringUtil.isBlank(patrolReq.getEquipmentCreateTime())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            if (!patrolReq.getEquipmentCreateTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D)) {
                return PatrolCode.EQUIPMENTCREATETIME_ERROR;
            }
        }
        //保质期
        if (StringUtil.isBlank(patrolReq.getQualityPeriod())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            try {
                int qualityPeriod = Integer.valueOf(patrolReq.getQualityPeriod());
                if (qualityPeriod > Quantity.TEN_HUNDRED) {
                    return PatrolCode.QUALITYPERIOD_MORE_THAN_TEN_HUNDRED;
                }
            } catch (Exception e) {
                return PatrolCode.QUALITYPERIOD_RULE_ERROR;
            }

        }
        //质保期的单位
        if (!StringUtil.isBlank(patrolReq.getUnit())) {
            boolean flag1 = String.valueOf(Quantity.ONE).equals(patrolReq.getUnit());
            boolean flag2 = String.valueOf(Quantity.TWO).equals(patrolReq.getUnit());
            boolean flag3 = String.valueOf(Quantity.THREE).equals(patrolReq.getUnit());
            if (flag1 == false && flag2 == false && flag3 == false) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        return null;
    }
}

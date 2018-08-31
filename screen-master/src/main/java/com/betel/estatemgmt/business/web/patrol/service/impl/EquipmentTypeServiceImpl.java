package com.betel.estatemgmt.business.web.patrol.service.impl;

import com.betel.estatemgmt.business.web.patrol.code.EquipmentCode;
import com.betel.estatemgmt.business.web.patrol.code.PatrolCode;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.service.EquipmentTypeService;
import com.betel.estatemgmt.business.web.task.constant.TaskConstant;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.patrol.EquipmentTypeMapper;
import com.betel.estatemgmt.common.mapper.patrol.PatrolEquipmentMapper;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: EquipmentTypeServiceImpl <br/>
 * @author: jian.z  <br/>
 * @date: 2018/1/24 10:06 <br/>
 * @Version: 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

    @Autowired
    private PatrolEquipmentMapper patrolEquipmentMapper;
    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;

    @Override
    public List<EquipmentType> findAllTypeList(Paging<EquipmentType> pager, PatrolReq patrolReq) {
        return equipmentTypeMapper.findAllTypeList(pager.getRowBounds(), patrolReq);
    }

    @Override
    public String updateType(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getTypeId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        } else {
            if (StringUtil.isBlank(patrolReq.getEquipmentType())) {
                return EquipmentCode.TYPE_NAME_NULL_ERROR;
            } else if (!Validate.isCommonString(patrolReq.getEquipmentType().trim(), Quantity.FIFTEEN)) {
                return EquipmentCode.TYPE_NAME_ROLE_ERROR;
            } else {
                List<EquipmentType> equipmentTypeList = equipmentTypeMapper.findTypeList(patrolReq);
                if (equipmentTypeList.size() > Quantity.ZERO) {
                    return EquipmentCode.TYPE_NAME_IS_EXIST_ERROR;
                }
                if (!StringUtil.isBlank(patrolReq.getTypeId())) {
                    EquipmentType equipmentType = equipmentTypeMapper.selectByPrimaryKey(patrolReq.getTypeId());
                    if (null == equipmentType) {
                        return EquipmentCode.EQUIPMENT_TYPE_IS_NOT_EXIST;
                    }
                }
                EquipmentType equipmentType = new EquipmentType();
                equipmentType.setTypeId(patrolReq.getTypeId());
                equipmentType.setTypeName(patrolReq.getEquipmentType().trim());
                equipmentTypeMapper.updateByPrimaryKeySelective(equipmentType);
                return StatusCode.SUCCESS;
            }
        }
    }

    @Override
    public String addType(PatrolReq patrolReq) {
        if (StringUtil.isBlank(patrolReq.getEquipmentType())) {
            return EquipmentCode.TYPE_NAME_NULL_ERROR;
        } else if (!Validate.isCommonString(patrolReq.getEquipmentType().trim(), Quantity.FIFTEEN)) {
            return EquipmentCode.TYPE_NAME_ROLE_ERROR;
        } else {
            List<EquipmentType> equipmentTypeList = equipmentTypeMapper.findTypeList(patrolReq);
            if (equipmentTypeList.size() > Quantity.ZERO) {
                return EquipmentCode.TYPE_NAME_IS_EXIST_ERROR;
            }
            EquipmentType equipmentType = new EquipmentType();
            equipmentType.setTypeId(UuidUtil.create());
            equipmentType.setEstateId(patrolReq.getEstateId());
            equipmentType.setTypeName(patrolReq.getEquipmentType().trim());
            equipmentType.setCreateTime(new Date());
            equipmentTypeMapper.insertSelective(equipmentType);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String deleteType(PatrolReq patrolReq) {
        String code = Tool.checkIdIsNull(patrolReq.getTypeId());
        if (!String.valueOf(Quantity.ONE).equals(code)) {
            return GlobalCode.Global_ID_NULL_ERROR;
        } else {
            String[] typeId = Tool.getIdArrOfStringType(patrolReq.getTypeId().trim());
            List<PatrolEquipment> patrolEquipmentList = equipmentTypeMapper.findEquiptByType(typeId);
            if (patrolEquipmentList.size() > Quantity.ZERO) {
                return PatrolCode.TYPE_HAS_WITH_EQUIPMENT;
            }
            equipmentTypeMapper.deleteType(typeId);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public EquipmentType findType(PatrolReq patrolReq) {
        if (!StringUtil.isBlank(patrolReq.getTypeId())) {
            return equipmentTypeMapper.selectByPrimaryKey(patrolReq.getTypeId());
        }
        return null;
    }
}

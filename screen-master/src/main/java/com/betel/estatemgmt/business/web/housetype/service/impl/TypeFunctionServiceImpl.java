package com.betel.estatemgmt.business.web.housetype.service.impl;

import com.betel.estatemgmt.business.web.housetype.code.HouseTypeCode;
import com.betel.estatemgmt.business.web.housetype.model.AddFunctionResult;
import com.betel.estatemgmt.business.web.housetype.service.TypeFunctionService;
import com.betel.estatemgmt.common.mapper.house.HouseTypeFunctionMapper;
import com.betel.estatemgmt.common.mapper.house.HouseTypeMapper;
import com.betel.estatemgmt.common.mapper.house.HouseTypeMaterialMapper;
import com.betel.estatemgmt.common.model.house.HouseType;
import com.betel.estatemgmt.common.model.house.HouseTypeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 功能区域维护的事务
 * </p>
 * ClassName: TypeFunctionServiceImpl <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/21 8:47 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class TypeFunctionServiceImpl implements TypeFunctionService {

    private static  final Logger LOG = LoggerFactory.getLogger(TypeFunctionServiceImpl.class);

    @Autowired
    private HouseTypeMapper houseTypeMapper;

    @Autowired
    private HouseTypeFunctionMapper houseTypeFunctionMapper;

    @Autowired
    private HouseTypeMaterialMapper houseTypeMaterialMapper;


    @Override
    public void deleteHouseTypeFunction(Long functionId) {
        // 删除功能区域
        houseTypeFunctionMapper.deleteByPrimaryKey(functionId);
        // 删除功能区域其下的建材数据
        houseTypeMaterialMapper.deleteMaterialByFunctionId(functionId);
    }

    @Override
    public String updateHouseTypeFunction(HouseTypeFunction houseTypeFunction) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========TypeFunctionServiceImpl updateHouseTypeFunction start========" +
                    "houseTypeFunction=" + houseTypeFunction);
        }
        String code = HouseTypeCode.FUNCTION_ISREPEAT_ERROR;
        // 判断户型是否被删除
        HouseType houseType = houseTypeMapper.selectByPrimaryKey(houseTypeFunction.getTypeId());
        if(houseType == null){
            if(LOG.isDebugEnabled()){
                LOG.debug("========TypeFunctionServiceImpl updateHouseTypeFunction ========" +
                        "code=" + HouseTypeCode.HOUSETYPE_ISDETELE_ERROR);
            }
            return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
        }
        HouseTypeFunction houseTypeFunction1 = houseTypeFunctionMapper.selectByPrimaryKey(houseTypeFunction.getFunctionId());
        if(houseTypeFunction1 == null){
            // 功能区域被删除
            if(LOG.isDebugEnabled()){
                LOG.debug("========TypeFunctionServiceImpl updateHouseTypeFunction ========" +
                        "code=" + HouseTypeCode.FUNCTION_DELETE_ERROR);
            }
            return HouseTypeCode.FUNCTION_DELETE_ERROR;
        }
        HouseTypeFunction hf = new HouseTypeFunction();
        hf.setFunctionName(houseTypeFunction.getFunctionName());
        hf.setTypeId(houseTypeFunction1.getTypeId());
        HouseTypeFunction houseTypeFunction2 = houseTypeFunctionMapper.selectFunctionIsExistence(hf);
        // 判断功能区域名称是否存在，名称没变可以正常修改
        if(houseTypeFunction2 == null || houseTypeFunction1.getFunctionName().equals(houseTypeFunction.getFunctionName())){
            houseTypeFunctionMapper.updateByPrimaryKeySelective(houseTypeFunction);
            if(LOG.isDebugEnabled()){
                LOG.debug("========TypeFunctionServiceImpl updateHouseTypeFunction ========" +
                        "code=" + code);
            }
            code = null;
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========TypeFunctionServiceImpl updateHouseTypeFunction end========" +
                    "code=" + code);
        }
        return code;
    }

    @Override
    public AddFunctionResult addHouseTypeFunction(HouseTypeFunction houseTypeFunction) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========TypeFunctionServiceImpl addHouseTypeFunction start========" +
                    "houseTypeFunction=" + houseTypeFunction);
        }
        AddFunctionResult addFunctionResult = new AddFunctionResult();
        addFunctionResult.setResultCode(HouseTypeCode.FUNCTION_ISREPEAT_ERROR);
        HouseType houseType = houseTypeMapper.selectByPrimaryKey(houseTypeFunction.getTypeId());
        if(houseType == null){
            // 户型被删除
            addFunctionResult.setResultCode(HouseTypeCode.HOUSETYPE_ISDETELE_ERROR);
            if(LOG.isDebugEnabled()){
                LOG.debug("========TypeFunctionServiceImpl insertHouseTypeFunction ========code=" + HouseTypeCode.HOUSETYPE_ISDETELE_ERROR);
            }
            return addFunctionResult;
        }
        HouseTypeFunction houseTypeFunction1 = houseTypeFunctionMapper.selectFunctionIsExistence(houseTypeFunction);
        if(houseTypeFunction1 == null){
            if(LOG.isDebugEnabled()){
                LOG.debug("========TypeFunctionServiceImpl insertHouseTypeFunction ========" +
                        "houseTypeFunction=" + houseTypeFunction);
            }
            houseTypeFunctionMapper.insertSelective(houseTypeFunction);
            addFunctionResult.setResultCode(null);
            addFunctionResult.setHouseTypeFunction(houseTypeFunction);
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========TypeFunctionServiceImpl addHouseTypeFunction end========" +
                    "AddFunctionResult=" + addFunctionResult);
        }
        return addFunctionResult;
    }
}

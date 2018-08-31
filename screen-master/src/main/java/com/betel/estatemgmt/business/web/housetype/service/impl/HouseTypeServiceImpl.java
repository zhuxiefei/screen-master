package com.betel.estatemgmt.business.web.housetype.service.impl;

import com.betel.estatemgmt.business.web.housetype.code.HouseTypeCode;
import com.betel.estatemgmt.business.web.housetype.model.*;
import com.betel.estatemgmt.business.web.housetype.service.HouseTypeService;
import com.betel.estatemgmt.common.mapper.house.HouseMaterialMapper;
import com.betel.estatemgmt.common.mapper.house.HouseTypeFunctionMapper;
import com.betel.estatemgmt.common.mapper.house.HouseTypeMapper;
import com.betel.estatemgmt.common.mapper.house.HouseTypeMaterialMapper;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * web 户型维护事务类
 * </p>
 * ClassName: HouseTypeServiceImpl <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 8:56 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class HouseTypeServiceImpl implements HouseTypeService {

    private static  final Logger LOG = LoggerFactory.getLogger(HouseTypeServiceImpl.class);

    @Autowired
    private HouseTypeMapper houseTypeMapper;

    @Autowired
    private HouseTypeFunctionMapper houseTypeFunctionMapper;

    @Autowired
    private HouseTypeMaterialMapper houseTypeMaterialMapper;

    @Autowired
    private HouseMaterialMapper houseMaterialMapper;


    @Override
    public String houseTypeAndFunctionJudges(Long typeId, String functionIds) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl houseTypeAndFunctionJudges start========" +
                    "typeId=" + typeId + "functionIds=" + functionIds);
        }
        HouseType houseType = houseTypeMapper.selectByPrimaryKey(typeId);
        if(houseType == null){
            return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
        }
        if(functionIds != null){
            String [] fIds = functionIds.split(",");
            for(int i = 0; i < fIds.length; i++){
                HouseTypeFunction houseTypeFunction = houseTypeFunctionMapper.selectByPrimaryKey(Long.parseLong(fIds[i]));
                if(houseTypeFunction == null){
                    if(fIds.length > 1){
                        return HouseTypeCode.FUNCTIONS_ISDELETE_ERROR;
                    }else{
                        return HouseTypeCode.FUNCTION_DELETE_ERROR;
                    }

                }
            }
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl houseTypeAndFunctionJudges end========code=null");
        }
        return null;
    }

    @Override
    public String houseTypeAndFunctionJudge(Long typeId, Long functionId) {
        HouseType houseType = houseTypeMapper.selectByPrimaryKey(typeId);
        if(houseType == null){
            return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
        }
        if(functionId != null && functionId != 0){
            HouseTypeFunction houseTypeFunction = houseTypeFunctionMapper.selectByPrimaryKey(functionId);
            if(houseTypeFunction == null){
                return HouseTypeCode.FUNCTION_DELETE_ERROR;
            }
        }
        return null;
    }

    @Override
    public String houseTypeAndFunctionIsHasDelete(String typeId, String functionId) {
        if ("0".equalsIgnoreCase(functionId)){//全户型
            HouseType houseType = houseTypeMapper.selectByPrimaryKey(Long.valueOf(typeId));
            if(houseType == null){
                return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
            }
        }else{
            HouseType houseType = houseTypeMapper.selectByPrimaryKey(Long.valueOf(typeId));
            if(houseType == null){
                return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
            }
            HouseTypeFunction curhouseTypeFunction=new HouseTypeFunction();
            curhouseTypeFunction.setFunctionId(Long.valueOf(functionId));

            HouseTypeFunction houseTypeFunction = houseTypeFunctionMapper.selectAllByPrimaryKey(curhouseTypeFunction);
            if(houseTypeFunction == null){
                return HouseTypeCode.FUNCTION_DELETE_ERROR;
            }
        }
        return null;
    }

    @Override
    public List<HouseMaterial> findAllMaterialNotInFunction(Paging<HouseMaterial> pager, NotInFunctionReq notInFunctionReq) {
        if(notInFunctionReq.getFunctionId() == 0){
            // 入参功能区域为0 则设置为null 为sql 入参
            notInFunctionReq.setFunctionId(null);
        }
        List<HouseMaterial> allMaterialNotInFunction = houseTypeMapper.findAllMaterialNotInFunction(pager.getRowBounds(), notInFunctionReq);
        return allMaterialNotInFunction;
    }

    @Override
    public String addHouseTypeMaterial(AddHouseTypeMaterialReq addHouseTypeMaterialReq) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl addHouseTypeMaterial start========" +
                    "addHouseTypeMaterialReq=" + addHouseTypeMaterialReq);
        }
        StringBuffer sb = new StringBuffer();
        List<HouseTypeMaterial> al = new ArrayList<HouseTypeMaterial>();
        String[] split = addHouseTypeMaterialReq.getMaterialIds().split(",");
        // 判断户型是否已被删除
        HouseType houseType = houseTypeMapper.selectByPrimaryKey(addHouseTypeMaterialReq.getTypeId());
        if(houseType == null){
            // 户型已被删除
            if(LOG.isDebugEnabled()){
                LOG.debug("========HouseTypeServiceImpl ========code=" + HouseTypeCode.HOUSETYPE_ISDETELE_ERROR);
            }
            return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
        }
        // 判断功能区域是否已被删除
        // 前台传入为0 时是添加到全户型 只有不为0才做功能区域是否被删除的判断
        if(addHouseTypeMaterialReq.getFunctionId() != 0){
            HouseTypeFunction houseTypeFunction = houseTypeFunctionMapper.selectByPrimaryKey(addHouseTypeMaterialReq.getFunctionId());
            if(houseTypeFunction == null){
                if(LOG.isDebugEnabled()){
                    LOG.debug("========HouseTypeServiceImpl ========code=" + HouseTypeCode.FUNCTION_DELETE_ERROR);
                }
                return HouseTypeCode.FUNCTION_DELETE_ERROR;
            }
        }
        for(int i = 0; i < split.length; i++){
            // 判断建材是否后台另一位管理员删除
            // 查询建材数据
            HouseMaterial houseMaterial = houseMaterialMapper.selectByPrimaryKey(Long.parseLong(split[i]));
            // 判断数据存不存在
            if(houseMaterial == null){
                //已被删除
                return null;
            }
            HouseTypeMaterial houseTypeMaterial = new HouseTypeMaterial();
            houseTypeMaterial.setTypeId(addHouseTypeMaterialReq.getTypeId());
            if(addHouseTypeMaterialReq.getFunctionId() != 0){
                houseTypeMaterial.setFunctionId(addHouseTypeMaterialReq.getFunctionId());
            }
            houseTypeMaterial.setMaterialId(Long.parseLong(split[i]));
            TypeMaterialInfo isExistence = houseTypeMaterialMapper.selectMaterialIsExistence(houseTypeMaterial);
            if(isExistence != null){
                // 已添加
                sb.append(isExistence.getMaterialName()+",");
            }
            // 保存需要添加的户型建材数据
            al.add(houseTypeMaterial);
        }
        if(StringUtil.isEmpty(sb.toString())){
            // 建材都没有添加过，则添加批量添加
            if(LOG.isDebugEnabled()){
                LOG.debug("========HouseTypeServiceImpl insertHouseTypeMaterial ========List<HouseTypeMaterial>=" + al);
            }
            houseTypeMaterialMapper.insertTypeMaterials(al);
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl addHouseTypeMaterial end========");
        }
        return sb.toString();
    }

    @Override
    public void deleteHouseTypeMaterials(String[] tmIds) {
        houseTypeMaterialMapper.deleteHouseTypeMaterials(tmIds);
    }

    @Override
    public List<FunctionMaterialVo> findAllMaterialByTypeFunction(Paging<FunctionMaterialVo> pager,
                                                                  HouseTypeMaterial houseTypeMaterial) {
        List<FunctionMaterialVo> allMaterialByTypeFunction = houseTypeMapper.findAllMaterialByTypeFunction(pager.getRowBounds(), houseTypeMaterial);
        return allMaterialByTypeFunction;
    }

    @Override
    public BasicHouseType findBasicHouseType(Long typeId) {
        // 查询户型基本信息
        BasicHouseType houseTypeBasic = houseTypeMapper.findHouseTypeBasic(typeId);
        // 查询户型下的功能区域
        List<BasicFunction> typeFunctionBasic = houseTypeFunctionMapper.findTypeFunctionBasic(typeId);
        if(typeFunctionBasic.size() != 0){
            houseTypeBasic.setBasicFunctions(typeFunctionBasic);
        }
        return houseTypeBasic;
    }

    @Override
    public String updateHouseType(HouseType houseType) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl updateHouseType start========" +
                    "houseType=" + houseType);
        }
        // 修改户型名称保存前判断户型是否已被删除
        HouseType houseType1 = houseTypeMapper.selectByPrimaryKey(houseType.getTypeId());
        if(houseType1 == null){
            if(LOG.isDebugEnabled()){
                LOG.debug("========HouseTypeServiceImpl updateHouseType ========code=" + HouseTypeCode.HOUSETYPE_ISDETELE_ERROR);
            }
            return HouseTypeCode.HOUSETYPE_ISDETELE_ERROR;
        }
        houseTypeMapper.updateByPrimaryKeySelective(houseType);
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl updateHouseType end========");
        }
        return null;
    }

    @Override
    public String findHouseByTypeName(String[] typeIds) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < typeIds.length; i++){
            List<House> houses = houseTypeMapper.findHouseByHouseType(Long.parseLong(typeIds[i]));
            if(houses.size() != 0){
                // 户型下存在房屋，保存并返回户型名称
                HouseType houseType = houseTypeMapper.selectByPrimaryKey(Long.parseLong(typeIds[i]));
                sb.append(houseType.getTypeName()+",");
            }
        }
        return sb.toString();
    }

    @Override
    public void deleteHouseTypes(String[] typeIds) {
        // 清除户型表数据
        houseTypeMapper.deleteHouseTypes(typeIds);
        // 清除户型功能表数据
        houseTypeFunctionMapper.deleteFunctions(typeIds);
        // 清除户型建材表数据
        houseTypeMaterialMapper.deleteMaterials(typeIds);
    }

    @Override
    public HouseType findHouseTypeByTypeName(String typeName) {
        HouseType houseTypeByTypeName = houseTypeMapper.findHouseTypeByTypeName(typeName);
        return houseTypeByTypeName;
    }

    @Override
    public void addHouseType(HouseType houseType, String [] functionNames) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl addHouseType start========" +
                    "houseType=" + houseType + "functionNames=" + Arrays.toString(functionNames));
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl insertHouseType========" +
                    "houseType=" + houseType);
        }
        houseTypeMapper.insertSelective(houseType);
        List<HouseTypeFunction> houseTypeFunctions = new ArrayList<HouseTypeFunction>();
        if(functionNames != null){
            //批量添加该户型下的功能区域
            for(int i = 0; i < functionNames.length; i++) {
                HouseTypeFunction houseTypeFunction = new HouseTypeFunction();
                houseTypeFunction.setFunctionName(functionNames[i]);
                houseTypeFunction.setTypeId(houseType.getTypeId());
                houseTypeFunctions.add(houseTypeFunction);
            }
            if(LOG.isDebugEnabled()){
                LOG.debug("========HouseTypeServiceImpl insertHouseTypeFunction========" +
                        "houseTypeFunctions=" + houseTypeFunctions);
            }
            houseTypeFunctionMapper.insertFunctions(houseTypeFunctions);
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========HouseTypeServiceImpl addHouseType end========");
        }
    }

    @Override
    public List<HouseTypeVo> findAllHouseType(Paging<HouseTypeVo> pager, PageKeyWord pageKeyWord) {
        List<HouseTypeVo> allHouseType = houseTypeMapper.findAllHouseType(pager.getRowBounds(), pageKeyWord);
        return allHouseType;
    }

    @Override
    public HouseTypeMaterial selectByPrimaryKey(Long tmId) {
        return houseTypeMaterialMapper.selectByPrimaryKey(tmId);
    }
}

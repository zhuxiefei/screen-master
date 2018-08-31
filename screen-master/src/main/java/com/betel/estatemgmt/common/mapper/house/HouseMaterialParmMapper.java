package com.betel.estatemgmt.common.mapper.house;


import com.betel.estatemgmt.common.model.house.HouseMaterialParm;

import java.util.List;
import java.util.Map;

public interface HouseMaterialParmMapper {
    int deleteByPrimaryKey(Long parmId);

    /**
     *  批量删除自定义参数
     * @param parmId
     * @return
     */
    int deleteBulkByPrimaryKeys(Long[] parmId);

    int insert(HouseMaterialParm record);

    int insertSelective(HouseMaterialParm record);

    HouseMaterialParm selectByPrimaryKey(Long parmId);

    int updateByPrimaryKeySelective(HouseMaterialParm record);

    int updateByPrimaryKey(HouseMaterialParm record);

    /**
     * 通过建材主键查询自定义参数列表
     * @param materialId
     * @return
     */
    List<HouseMaterialParm> finAllmaterialParmsBymaterialsId(Long materialId);

    /**
     *  建材id删除自定义参数表信息
     * @param materialId
     * @return
     */
    int deleteByMaterialId(Long materialId);


    /**
     *  通过建材id删除自定义参数表信息
     * @param materialIds
     * @return
     */
    int deleteBulkByMaterialIds(List<Long> materialIds);

    /**
     * 校验自定义参数的重复性（修改时排除本身）
     * @param condition
     * @return
     */
    int selectMaterialCustomParmCount(Map<String, Object> condition);


}
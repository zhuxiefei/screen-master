package com.betel.estatemgmt.common.mapper.house;


import com.betel.estatemgmt.common.model.house.HouseMaterialType;

import java.util.List;
import java.util.Map;

public interface HouseMaterialTypeMapper {
    int deleteByPrimaryKey(String typeName);

    int insert(HouseMaterialType record);

    int insertSelective(HouseMaterialType record);

    HouseMaterialType selectByPrimaryKey(String typeName);

    int updateByPrimaryKeySelective(HouseMaterialType record);

    int updateByPrimaryKey(HouseMaterialType record);
    List<Map<String,Object>> findBuildMaterialType();

    /**
     * 查询类型是否存在
     * @param typeName
     * @return
     */
    int selectMaterialTypeNameIsExcel(String typeName);

    /**
     * 查询所有建材类型名称
     * @return
     */
    List<String> findMaterialTypeNameList();
}
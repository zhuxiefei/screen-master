package com.betel.estatemgmt.common.mapper.cleaning;

import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CleaningTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(CleaningType record);

    int insertSelective(CleaningType record);

    CleaningType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(CleaningType record);

    int updateByPrimaryKey(CleaningType record);

    /**
     根据区域ID查询区域类型
     */
    List<CleaningType> findByAreaId(String areaId);

    /**
     * 根据区域ID和类型名称查询区域类型
     * @param areaId
     * @param typeName
     * @return
     */
    CleaningType findByAreaIdAndTypeName(@Param("areaId") String areaId,@Param("typeName") String typeName);

    /**
     * 批量删除类型
     * @param typeIds
     */
    void deleteByTypeIds(String[] typeIds);
}
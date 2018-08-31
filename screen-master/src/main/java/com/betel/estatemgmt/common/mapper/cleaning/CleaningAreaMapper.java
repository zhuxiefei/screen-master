package com.betel.estatemgmt.common.mapper.cleaning;

import com.betel.estatemgmt.common.model.cleaning.CleaningArea;

import java.util.List;

public interface CleaningAreaMapper {
    int deleteByPrimaryKey(String areaId);

    int insert(CleaningArea record);

    int insertSelective(CleaningArea record);

    CleaningArea selectByPrimaryKey(String areaId);

    int updateByPrimaryKeySelective(CleaningArea record);

    int updateByPrimaryKey(CleaningArea record);

    /**
     * 根据区域名称查询
     * @param name
     * @return
     */
    CleaningArea findByName(String name);

    /**
     * 批量删除区域
     * @param areaIds
     */
    void deleteByAreaIds(String[] areaIds);

    /**
     * 查询所有区域
     * @return
     */
    List<CleaningArea> findAreas(String estateId);
}
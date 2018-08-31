package com.betel.estatemgmt.common.mapper.estate;

import com.betel.estatemgmt.common.model.estate.Estate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EstateMapper {
    int deleteByPrimaryKey(String estateId);

    int insert(Estate record);

    int insertSelective(Estate record);

    Estate selectByPrimaryKey(String estateId);

    int updateByPrimaryKeySelective(Estate record);

    int updateByPrimaryKey(Estate record);

    List<Estate> findByEstateIds(@Param("estateIds") String[] estateIds,
                                 @Param("cityId") String cityId);
}
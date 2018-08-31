package com.betel.estatemgmt.common.mapper.estate;

import com.betel.estatemgmt.common.model.estate.EstateCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EstateCityMapper {
    int deleteByPrimaryKey(String cityId);

    int insert(EstateCity record);

    int insertSelective(EstateCity record);

    EstateCity selectByPrimaryKey(String cityId);

    int updateByPrimaryKeySelective(EstateCity record);

    int updateByPrimaryKey(EstateCity record);

    List<EstateCity> findByEstateIds(@Param("estateIds") String[] estateIds,
                                     @Param("cityName") String cityName);
}
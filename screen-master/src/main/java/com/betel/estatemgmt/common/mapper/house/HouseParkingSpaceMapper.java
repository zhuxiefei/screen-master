package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.common.model.house.HouseParkingSpace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseParkingSpaceMapper {
    int deleteByPrimaryKey(String spaceNum);

    int insert(HouseParkingSpace record);

    int insertSelective(HouseParkingSpace record);

    HouseParkingSpace selectByPrimaryKey(String spaceNum);

    int updateByPrimaryKeySelective(HouseParkingSpace record);

    int updateByPrimaryKey(HouseParkingSpace record);

    void insertList(List<HouseParkingSpace> list);

    Integer findCountsByHouseIdAndType(@Param("houseId") String houseId, @Param("spaceType") Integer spaceType);

    void deleteByHouseId(@Param("houseId") String houseId);

    void deleteByHouseIds(List<String> houseIds);

    List<HouseParkingSpace> findSpacesByHouseId(String houseId);
}
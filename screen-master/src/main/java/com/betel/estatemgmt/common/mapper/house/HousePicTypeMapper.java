package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.house.model.PictureInfo;
import com.betel.estatemgmt.common.model.house.HousePicType;

import java.util.List;

public interface HousePicTypeMapper {
    int deleteByPrimaryKey(String typeName);

    int insert(HousePicType record);

    int insertSelective(HousePicType record);

    HousePicType selectByPrimaryKey(String typeName);

    int updateByPrimaryKeySelective(HousePicType record);

    int updateByPrimaryKey(HousePicType record);

    List<PictureInfo> findPictureType();
}
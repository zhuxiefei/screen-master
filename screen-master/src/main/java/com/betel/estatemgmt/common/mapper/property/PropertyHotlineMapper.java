package com.betel.estatemgmt.common.mapper.property;

import com.betel.estatemgmt.business.userapp.hotline.model.Hotline;
import com.betel.estatemgmt.common.model.property.PropertyHotline;

import java.util.List;

public interface PropertyHotlineMapper {
    int deleteByPrimaryKey(String hotlineId);

    int insert(PropertyHotline record);

    int insertSelective(PropertyHotline record);

    PropertyHotline selectByPrimaryKey(String hotlineId);

    int updateByPrimaryKeySelective(PropertyHotline record);

    int updateByPrimaryKey(PropertyHotline record);

    List<Hotline> findHotlines();
}
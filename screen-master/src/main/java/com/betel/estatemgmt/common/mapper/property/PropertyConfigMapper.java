package com.betel.estatemgmt.common.mapper.property;

import com.betel.estatemgmt.business.web.remind.model.AddRemindDaysReq;
import com.betel.estatemgmt.business.web.remind.model.RemindDays;
import com.betel.estatemgmt.business.web.repair.model.RepairCharge;
import com.betel.estatemgmt.common.model.property.PropertyConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyConfigMapper {
    int deleteByPrimaryKey(String confName);

    int insert(PropertyConfig record);

    int insertSelective(PropertyConfig record);

    PropertyConfig selectByPrimaryKey(PropertyConfig record);

    int updateByPrimaryKeySelective(PropertyConfig record);

    int updateByPrimaryKey(PropertyConfig record);

    void updateRemindDays(AddRemindDaysReq addRemindDaysReq);

    List<RemindDays> findByConfNames(@Param("confNames") String[] confNames,
                                     @Param("estateId") String estateId);

    RepairCharge selectByConfName(@Param("name") String name,
                                  @Param("estateId") String estateId);
}
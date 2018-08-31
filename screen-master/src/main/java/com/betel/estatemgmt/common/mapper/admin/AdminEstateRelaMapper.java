package com.betel.estatemgmt.common.mapper.admin;

import com.betel.estatemgmt.common.model.admin.AdminEstateRela;
import com.betel.estatemgmt.common.model.admin.AdminEstateRelaKey;
import com.betel.estatemgmt.common.model.estate.Estate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminEstateRelaMapper {
    int deleteByPrimaryKey(AdminEstateRelaKey key);

    int insert(AdminEstateRela record);

    int insertSelective(AdminEstateRela record);

    AdminEstateRela selectByPrimaryKey(AdminEstateRelaKey key);

    int updateByPrimaryKeySelective(AdminEstateRela record);

    int updateByPrimaryKey(AdminEstateRela record);

    List<Estate> findByAdminId(String adminId);

    void deleteByAdminIdsAndEstateId(@Param("adminIds") String[] adminIds,
                                     @Param("estateId") String estateId);
}
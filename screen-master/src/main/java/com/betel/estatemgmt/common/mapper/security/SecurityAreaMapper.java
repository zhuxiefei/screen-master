package com.betel.estatemgmt.common.mapper.security;

import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.common.model.security.SecurityArea;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityAreaMapper {
    int deleteByPrimaryKey(String areaId);

    int insert(SecurityArea record);

    int insertSelective(SecurityArea record);

    SecurityArea selectByPrimaryKey(String areaId);

    int updateByPrimaryKeySelective(SecurityArea record);

    int updateByPrimaryKey(SecurityArea record);

    /**
     * 查询巡检区域集合
     *
     * @return
     */
    List<SecurityArea> findAreaList(String estateId);

    /**
     * 通过名称查询(重复判断)
     *
     * @return
     */
    List<SecurityArea> findAreaByName(SecurityReq securityReq);
}
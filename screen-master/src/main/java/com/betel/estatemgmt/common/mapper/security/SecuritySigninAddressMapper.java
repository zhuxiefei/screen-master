package com.betel.estatemgmt.common.mapper.security;

import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.common.model.security.SecurityContent;
import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecuritySigninAddressMapper {
    int deleteByPrimaryKey(String signinId);

    int insert(SecuritySigninAddress record);

    int insertSelective(SecuritySigninAddress record);

    SecuritySigninAddress selectByPrimaryKey(String signinId);

    int updateByPrimaryKeySelective(SecuritySigninAddress record);

    int updateByPrimaryKey(SecuritySigninAddress record);

    /**
     * 查询区域下的签到地点
     *
     * @param securityReq
     * @return
     */
    List<SecuritySigninAddress> findSignList(SecurityReq securityReq);

    /**
     * 通过名称查询
     *
     * @param securityReq
     * @return
     */
    List<SecuritySigninAddress> findSignByName(SecurityReq securityReq);

    /**
     * 通过区域id查询
     *
     * @param securityReq
     * @return
     */
    List<SecuritySigninAddress> findSignByaAreaId(SecurityReq securityReq);

    List<SecuritySigninAddress> findAddressByRecordId(String recordId);
}
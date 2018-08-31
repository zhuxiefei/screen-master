package com.betel.estatemgmt.common.mapper.security;

import com.betel.estatemgmt.business.propertyapp.security.model.SignInfo;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.common.model.security.SecuritySigninNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecuritySigninNumberMapper {
    int deleteByPrimaryKey(String numId);

    int insert(SecuritySigninNumber record);

    int insertSelective(SecuritySigninNumber record);

    SecuritySigninNumber selectByPrimaryKey(String numId);

    int updateByPrimaryKeySelective(SecuritySigninNumber record);

    int updateByPrimaryKey(SecuritySigninNumber record);

    /**
     * 根据recordId查巡逻记录
     *
     * @return
     */
    List<SecuritySigninNumber> findByRecordId(SecurityReq securityReq);

    List<SignInfo> findAllSignList(String recordId);
}
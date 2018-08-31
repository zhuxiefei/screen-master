package com.betel.estatemgmt.common.mapper.security;

import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.common.model.security.SecurityContent;
import com.betel.estatemgmt.common.model.security.SecurityContentAreaSignin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityContentAreaSigninMapper {
    int deleteByPrimaryKey(String contSignAreaId);

    int insert(SecurityContentAreaSignin record);

    int insertSelective(SecurityContentAreaSignin record);

    SecurityContentAreaSignin selectByPrimaryKey(String contSignAreaId);

    int updateByPrimaryKeySelective(SecurityContentAreaSignin record);

    int updateByPrimaryKey(SecurityContentAreaSignin record);

    /**
     * 根据内容id删除
     *
     * @param securityReq
     * @return
     */
    int deleteByContentId(SecurityReq securityReq);

    /**
     * 内容id批量删除
     *
     * @return
     */
    int deleteBatchByContentId(String[] contentId);

    /**
     * 通过签到地址id查询是否巡逻内容
     *
     * @return
     */
    List<SecurityContentAreaSignin> findBySigninId(SecurityReq securityReq);

    /**
     * 通过内容id查询签到地址
     *
     * @return
     */
    List<SecurityContentAreaSignin> findByContentId(SecurityReq securityReq);
}
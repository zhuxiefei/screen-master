package com.betel.estatemgmt.common.mapper.security;


import com.betel.estatemgmt.business.web.security.model.ContextInfo;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.common.model.security.SecurityContent;
import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityContentMapper {
    int deleteByPrimaryKey(String contentId);

    int insert(SecurityContent record);

    int insertSelective(SecurityContent record);

    SecurityContent selectByPrimaryKey(String contentId);

    int updateByPrimaryKeySelective(SecurityContent record);

    int updateByPrimaryKey(SecurityContent record);

    /**
     * 分页查询
     *
     * @param rowBounds
     * @param securityReq
     * @return
     */
    List<SecurityResp> findAllContentList(RowBounds rowBounds, SecurityReq securityReq);

    /**
     * 查询安保内容详情
     *
     * @param securityReq
     * @return
     */
    SecurityResp findContent(SecurityReq securityReq);

    /**
     * 查询安保签到地址
     *
     * @param securityReq
     * @return
     */
    List<SecuritySigninAddress> findSigninByContentId(SecurityReq securityReq);

    /**
     * 主键查询签到地址
     *
     * @param signinId
     * @return
     */
    List<SecuritySigninAddress> findSigninById(String[] signinId);

    /**
     * 批量删除内容
     *
     * @return
     */
    int deleteBatchContent(String[] contentId);

    /**
     * 分页查询内容下的巡逻记录
     *
     * @param rowBounds
     * @param securityReq
     * @return
     */
    List<SecurityResp> findAllRecord(RowBounds rowBounds, SecurityReq securityReq);

    /**
     * 查询所有的巡检内容（不分页）
     *
     * @return
     */
    List<SecurityContent> selectList();

    /**
     * 根据签到地点和区域名称查询巡逻内容
     *
     * @param securityReq
     * @return
     */
    List<ContextInfo> findBySigninAndAreaAndTime(SecurityReq securityReq);

    /**
     * 查询所有巡逻内容信息
     *
     * @return
     */
    List<SecurityContent> selectContent(SecurityReq securityReq);
}
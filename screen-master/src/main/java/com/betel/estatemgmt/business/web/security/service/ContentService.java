package com.betel.estatemgmt.business.web.security.service;


import com.betel.estatemgmt.business.web.security.model.SecurityContentResp;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 安保巡逻区域实现接口
 * </p>
 * ClassName: ContentService <br/>
 * Author:jians.z  <br/>
 * Date:  2018/2/28 <br/>
 * Version: 1.0 <br/>
 */
public interface ContentService {
    /**
     * 查询巡检内容集合
     *
     * @return
     */
    List<SecurityResp> findAllContentList(Paging<SecurityResp> pager, SecurityReq securityReq);

    /**
     * 查询内容详情
     *
     * @param securityReq
     * @return
     */
    SecurityResp findContent(SecurityReq securityReq);

    /**
     * 编辑内容
     *
     * @param securityReq
     * @return
     */
    String updateContent(SecurityReq securityReq);

    /**
     * 新增内容
     *
     * @param securityReq
     * @return
     */
    String addContent(SecurityReq securityReq);

    /**
     * 删除（批量）
     *
     * @param securityReq
     * @return
     */
    String deleteContent(SecurityReq securityReq);

    /**
     * 根据内容id查询巡检记录
     *
     * @param pager
     * @param securityReq
     * @return
     */
    List<SecurityResp> findAllRecord(Paging<SecurityResp> pager, SecurityReq securityReq);

    /**
     * 根据contentId查询内容
     *
     * @param securityReq
     * @return
     */
    SecurityContentResp findRecordContent(SecurityReq securityReq);
}

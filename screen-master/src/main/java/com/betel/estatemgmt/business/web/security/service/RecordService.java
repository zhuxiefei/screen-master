package com.betel.estatemgmt.business.web.security.service;


import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 安保巡逻记录实现接口
 * </p>
 * ClassName: RecordService <br/>
 * Author:jians.z  <br/>
 * Date: 2018/2/12. <br/>
 * Version: 1.0 <br/>
 */
public interface RecordService {
    /**
     * 查询巡检记录集合
     *
     * @return
     */
    List<SecurityResp> findAllRecordList(Paging<SecurityResp> pager, SecurityReq securityReq);

    /**
     * 查询巡逻记录详情
     *
     * @param securityReq
     * @return
     */
    SecurityResp findRecord(SecurityReq securityReq);

    /**
     * 导出
     *
     * @param securityReq
     * @return
     */
    List<SecurityResp> exportList(SecurityReq securityReq);

    /**
     * 定时任务生成安保记录
     */
    void quartzSecurity(long curDate) throws Exception;
}

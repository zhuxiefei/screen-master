package com.betel.estatemgmt.common.mapper.security;

import com.betel.estatemgmt.business.propertyapp.security.model.SecurityInfo;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.business.web.task.model.TaskCountReq;
import com.betel.estatemgmt.common.model.security.SecurityRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(SecurityRecord record);

    int insertSelective(SecurityRecord record);

    SecurityRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(SecurityRecord record);

    int updateByPrimaryKey(SecurityRecord record);

    List<SecurityInfo> findAllSecurityList(RowBounds rowBounds, @Param("status") String status,@Param("estateId") String estateId);

    /**
     * 首页查询待处理的巡逻数
     *
     * @return
     */
    int countUnfinish(String estateId);

    /**
     * 分页查询
     *
     * @param rowBounds
     * @param securityReq
     * @return
     */
    List<SecurityResp> findAllRecordList(RowBounds rowBounds, SecurityReq securityReq);

    /**
     * 查询巡逻记录详情
     *
     * @param securityReq
     * @return
     */
    SecurityResp findRecord(SecurityReq securityReq);

    /**
     * 根据id导出
     *
     * @param recordId
     * @return
     */
    List<SecurityResp> exportListByRecordId(String[] recordId);

    /**
     * 更新搜索条件导出
     *
     * @return
     */
    List<SecurityResp> exportList(SecurityReq securityReq);

    List<SecurityRecord> findRecords(TaskCountReq taskCountReq);
}
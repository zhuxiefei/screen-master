package com.betel.estatemgmt.common.mapper.cleaning;

import com.betel.estatemgmt.business.propertyapp.cleaning.model.CleaningRecordInfo;
import com.betel.estatemgmt.business.propertyapp.cleaning.model.FindAssessmentListReq;
import com.betel.estatemgmt.business.web.cleaning.model.*;
import com.betel.estatemgmt.business.web.task.model.TaskCountReq;
import com.betel.estatemgmt.common.model.cleaning.CleaningRecord;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CleaningRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(CleaningRecord record);

    int insertSelective(CleaningRecord record);

    CleaningRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(CleaningRecord record);

    int updateByPrimaryKey(CleaningRecord record);

    List<CleaningRecordInfo> findAllAssessmentList(RowBounds rowBounds, FindAssessmentListReq findAssessmentListReq);

    /**
     * 分页查询某保洁内容下的考核记录列表
     * @param recordsReq
     * @param rowBounds
     * @return
     */
    List<ContentRecordInfo> findAllContentRecords(FindAllContentRecordsReq recordsReq,RowBounds rowBounds);

    /**
     * 分页查询考核记录列表
     * @param recordsReq
     * @param rowBounds
     * @return
     */
    List<FindAllRecordsResp> findAllRecords(FindAllRecordsReq recordsReq,RowBounds rowBounds);

    /**
     * 查询考核记录详情
     * @param recordId
     * @return
     */
    FindRecordResp findRecord(String recordId);

    /**
     * 根据多Id查询
     * @param recordIds
     * @return
     */
    List<FindAllRecordsResp> findByRecordIds(String[] recordIds);

    /**
     * 根据导出条件查询
     * @param recordsReq
     * @return
     */
    List<FindAllRecordsResp> findByParams(ExportRecordsReq recordsReq);

    List<CleaningRecord> findRecords(TaskCountReq taskCountReq);
}
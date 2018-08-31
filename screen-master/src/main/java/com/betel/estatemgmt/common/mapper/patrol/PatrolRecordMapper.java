package com.betel.estatemgmt.common.mapper.patrol;

import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.business.web.task.model.TaskCountReq;
import com.betel.estatemgmt.common.model.patrol.PatrolRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatrolRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PatrolRecord record);

    int insertSelective(PatrolRecord record);

    PatrolRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PatrolRecord record);

    int updateByPrimaryKey(PatrolRecord record);
    //==================巡检设置=================开始===========

    /**
     * 分页查询巡检记录正常和非正常
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<Record> findAllRecordList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 查询待巡检任务（分页）
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<Record> findNotPostList(RowBounds rowBounds, PatrolReq patrolReq);
    /**
     * 查询待巡检任务（不分页）
     *
     * @return
     */
    List<Record> selectCheckList(@Param("estateId") String estateId);
    /**
     * 查询已过期巡检任务
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<Record> findPostList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 分页查询巡检记录列表
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<Record> findAllWebRecordList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 查询记录详情
     *
     * @param patrolReq
     * @return
     */
    Record findRecordDetail(PatrolReq patrolReq);

    /**
     * 删除巡检记录（批量）
     *
     * @param patrolReq
     * @return
     */
    int deleteBatchRecord(PatrolReq patrolReq);

    //==================巡检设置=================结束===========

//    /**
//     * 今日巡检发现的异常设施数量
//     */
//    List<Record> exceptionEquipmentList(HomeReq homeReq);

    /**
     * 网络缓慢查询唯一标识
     */
    List<PatrolRecord> findList(PatrolRecord record);

    PatrolRecord findNewestByEquipId(String equipId);

    List<PatrolRecord> findRecords(TaskCountReq taskCountReq);

    List<PatrolRecord> findOverRecords(TaskCountReq taskCountReq);
}
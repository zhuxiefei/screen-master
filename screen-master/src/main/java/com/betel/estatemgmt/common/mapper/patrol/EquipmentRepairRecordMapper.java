package com.betel.estatemgmt.common.mapper.patrol;

import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.web.patrol.model.*;
import com.betel.estatemgmt.common.model.patrol.EquipmentRepairRecord;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepairRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(EquipmentRepairRecord record);

    int insertSelective(EquipmentRepairRecord record);

    EquipmentRepairRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(EquipmentRepairRecord record);

    int updateByPrimaryKey(EquipmentRepairRecord record);

    /**
     * 查询维保记录列表分页
     *
     * @param patrolReq
     * @return
     */
    List<MaintenanceResp> findAllMaintenanceList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 查询维保记录不分页（app/web导出）
     *
     * @param maintenanceReq
     * @return
     */
    List<MaintenanceResp> findMaintenanceList(MaintenanceReq maintenanceReq);

    /**
     * 导出查询维保记录（id）
     *
     * @param equipmentRepairRecordId
     * @return
     */
    List<MaintenanceResp> findMaintenanceListById(String[] equipmentRepairRecordId);

    List<FindAllRecordsResp> findAllRecords(RowBounds rowBounds, FindAllRecordsReq req);

    FindRecordResp findByRecordId(String recordId);

    /**
     * 根据设备id删除维保记录
     *
     * @param idArr
     * @return
     */
    int deleteByEquipmemtId(String[] idArr);

    List<ExportRecordsResp> exportByIds(String[] ids);

    List<ExportRecordsResp> exportByParams(ExportRepairRecordsReq repairRecordsReq);
}
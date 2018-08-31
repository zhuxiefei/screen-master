package com.betel.estatemgmt.business.web.patrol.service;

import com.betel.estatemgmt.business.web.patrol.model.*;
import com.betel.estatemgmt.common.model.patrol.EquipmentRepairRecord;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.text.ParseException;
import java.util.List;

/**
 * Created by zhangjian on 2018/1/24.
 */
public interface MaintenanceService {
    /**
     * 查询设备
     *
     * @param patrolReq
     * @return
     */
    PatrolEquipment selectByEquipment(PatrolReq patrolReq);

    /**
     * 添加维保记录
     *
     * @param maintenanceReq
     * @return
     */
    String addMaintenance(MaintenanceReq maintenanceReq) throws ParseException;

    List<String> findEquipNos(String equipNo,String estateId);

    List<EquipmentType> findEquipTypes(String estatId);

    EquipmentType findByTypeId(String typeId);

    List<FindAllRecordsResp> findAllRecords(Paging<FindAllRecordsResp> paging, FindAllRecordsReq recordsReq);

    EquipmentRepairRecord findByRecordId(String recordId);

    FindRecordResp findRecordByRecordId(String recordId);

    String updateRecord(MaintenanceReq req) throws Exception;

    List<ExportRecordsResp> exportbyIds(String recordIds);

    List<ExportRecordsResp> exportbyParams(ExportRepairRecordsReq repairRecordsReq);
}

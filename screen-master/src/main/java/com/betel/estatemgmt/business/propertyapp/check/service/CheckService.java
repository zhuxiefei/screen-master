package com.betel.estatemgmt.business.propertyapp.check.service;

import com.betel.estatemgmt.business.propertyapp.check.model.FindDeviceInfoResp;
import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.propertyapp.check.model.SaveCheckInfoReq;
import com.betel.estatemgmt.business.web.patrol.model.MaintenanceReq;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * app设施巡检业务类...
 * </p>
 * ClassName: CheckService <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 11:35 <br/>
 * Version: 1.0 <br/>
 */
public interface CheckService {

    FindDeviceInfoResp findDeviceInfo(String equipmentId,String estateId);

    void savePictures(List<SystemPicture> pictures);

    String saveCheckInfo(SaveCheckInfoReq saveCheckInfoReq, String userId) throws ParseException;

    /**
     * 查询巡检记录正常和非正常（分页）
     *
     * @param pager
     * @param patrolReq
     * @return
     */
    List<Record> findAllRecordList(Paging<Record> pager, PatrolReq patrolReq);

    /**
     * 查询记录详情
     *
     * @param patrolReq
     * @return
     */
    Record findRecordDetail(PatrolReq patrolReq);

    /**
     * 删除设施巡检记录
     *
     * @param patrolReq
     * @return
     */
    String deleteRecord(PatrolReq patrolReq);

    /**
     * 查询巡检设备设置列表（分页）
     *
     * @param pager
     * @param patrolReq
     * @return
     */
    List<PatrolEquipmentResp> findAllEquipmentList(Paging<PatrolEquipmentResp> pager, PatrolReq patrolReq);

    /**
     * 查询巡检设备
     *
     * @param patrolReq
     * @return
     * @throws Exception
     */
    PatrolEquipment findEquipmentDetail(PatrolReq patrolReq) throws Exception;

    /**
     * 定时任务每天上午9点生产任务
     *
     * @return
     */
    String insertForDate() throws Exception;

    /**
     * 定时任务每周上午9点生产任务
     *
     * @return
     */
    String insertForWeek() throws Exception;

    /**
     * 定时任务每月上午9点生产任务
     *
     * @return
     */
    String insertForMonth() throws Exception;

    /**
     * 定时任务每季度1号上午9点生产任务
     *
     * @return
     */
    String insertForQuarter() throws Exception;

    /**
     * 定时任务每月上午9点生产任务
     *
     * @return
     */
    String insertForYear() throws Exception;

    /**
     * 定时任务每季度上午9点生产任务
     *
     * @return
     */
    List<PatrolEquipment> findEqpmtListForQuarter();

    /**
     * 查询设备类型不分页
     *
     * @return
     */
    List<EquipmentType> findTypeList(PatrolReq patrolReq);

    /**
     * 查询维保记录不分页
     *
     * @param maintenanceReq
     * @return
     */
    List<MaintenanceResp> findMaintenanceList(MaintenanceReq maintenanceReq);

}

package com.betel.estatemgmt.business.web.patrol.service;

import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: PatrolService <br/>
 * @author: jian.z  <br/>
 * @date: 2017/12/4 10:00 <br/>
 * @Version: 1.0
 */
public interface PatrolService {

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
    PatrolEquipmentResp findEquipmentDetail(PatrolReq patrolReq) throws Exception;

    /**
     * 查询二维码
     *
     * @param patrolReq
     * @return
     * @throws Exception
     */
    PatrolEquipment findQrcode(PatrolReq patrolReq) throws Exception;

    /**
     * 更新巡检设备
     *
     * @param patrolReq
     * @return
     */
    String updateEquipment(PatrolReq patrolReq) throws Exception;

    /**
     * 删除巡检设备
     *
     * @param patrolReq
     * @return
     */
    String deleteEquipment(PatrolReq patrolReq);


    /**
     * 新增巡检设备信息设置
     *
     * @param patrolReq
     * @return
     * @throws Exception
     */
    String addEquipment(PatrolReq patrolReq) throws Exception;


    /**
     * 查询巡检设备设置列表（分页）
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
     * 查询设备类型不分页
     *
     * @return
     */
    List<EquipmentType> findTypeList(PatrolReq patrolReq);

    /**
     * 查询维保记录分页
     *
     * @param pager
     * @param patrolReq
     * @return
     */
    List<MaintenanceResp> findAllMaintenanceList(Paging<MaintenanceResp> pager, PatrolReq patrolReq);

    /**
     * 导出查询
     *
     * @param patrolReq
     * @return
     */
    List<MaintenanceResp> exportList(PatrolReq patrolReq);

    /**
     * 根据id查询设备
     *
     * @param equipmentId
     * @return
     */
    PatrolEquipment selectByPrimaryKey(String equipmentId);

}

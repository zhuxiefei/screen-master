package com.betel.estatemgmt.common.mapper.patrol;

import com.betel.estatemgmt.business.propertyapp.check.model.SaveCheckInfoReq;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatrolEquipmentMapper {
    int deleteByPrimaryKey(String equipmentId);

    int insert(PatrolEquipment record);

    int insertSelective(PatrolEquipment record);

    PatrolEquipment selectByPrimaryKey(String equipmentId);

    int updateByPrimaryKeySelective(PatrolEquipment record);

    int updateByPrimaryKeyWithBLOBs(PatrolEquipment record);

    int updateByPrimaryKey(PatrolEquipment record);

    /**
     * 分页查询
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<PatrolEquipmentResp> findAllEquipmentList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 分页查询
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<PatrolEquipmentResp> findAllWebEquipmentList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 批量删除
     *
     * @param idArr
     * @return
     */
    int deleteBatchEquipment(String[] idArr);

    /**
     * 根据设备编号等属性信息查询巡检设备
     *
     * @param patrolReq
     * @return
     */
    PatrolEquipment selectByEquipment(PatrolReq patrolReq);

    /**
     * 批量查询查询巡检设备内容集合
     *
     * @param equipmentIds
     * @return
     */
    List<PatrolEquipment> findBatchEquipmentByIds(String[] equipmentIds);

    /**
     * 查询设备总数
     *
     * @return
     */
    Integer queryAllEquipmentNum();

    /**
     * 查询当天已完成的设备数
     *
     * @return
     */
    List<String> findTodayFinishEquips();


    /**
     * 查询设备信息
     *
     * @param saveCheckInfoReq 周期
     * @return
     */
    List<PatrolEquipment> findEqpmtListByCycle(SaveCheckInfoReq saveCheckInfoReq);

    List<String> findEquipNos(@Param("equipNo") String equipNo, @Param("estateId") String estateId);

    PatrolEquipment findByEquipmentIdAndEstateId(@Param("equipmentId") String equipmentId,
                                                       @Param("estateId") String estateId);
}
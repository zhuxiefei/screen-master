package com.betel.estatemgmt.common.mapper.patrol;

import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(EquipmentType record);

    int insertSelective(EquipmentType record);

    EquipmentType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(EquipmentType record);

    int updateByPrimaryKey(EquipmentType record);

    /**
     * 分页查询
     *
     * @param rowBounds
     * @param patrolReq
     * @return
     */
    List<EquipmentType> findAllTypeList(RowBounds rowBounds, PatrolReq patrolReq);

    /**
     * 批量删除
     *
     * @param arrId
     * @return
     */
    int deleteType(String[] arrId);

    /**
     * 查询设备类型
     *
     * @param patrolReq
     * @return
     */
    List<EquipmentType> findTypeList(PatrolReq patrolReq);

    /**
     * 查询所有类型
     *
     * @return
     */
    List<EquipmentType> findTypes(String estatId);

    /**
     * 查询设备类型
     *
     * @param typeID
     * @return
     */
    List<PatrolEquipment> findEquiptByType(String[] typeID);
}
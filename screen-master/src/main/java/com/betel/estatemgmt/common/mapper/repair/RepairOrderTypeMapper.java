package com.betel.estatemgmt.common.mapper.repair;

import com.betel.estatemgmt.business.web.repair.model.TypeInfo;
import com.betel.estatemgmt.common.model.repair.RepairOrderType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairOrderTypeMapper {
    int deleteByPrimaryKey(Long typeId);

    int insert(RepairOrderType record);

    int insertSelective(RepairOrderType record);

    RepairOrderType selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(RepairOrderType record);

    int updateByPrimaryKey(RepairOrderType record);

    /**
     * 根据类型 查询全部报修类型
     * Create By ZhouYe ON 2017/9/15 9:40
     */
    List<RepairOrderType> findAllRepairOrderType(@Param("typeType") String typeType,@Param("estateType")Integer estateType);

    List<TypeInfo> selectByArea(@Param("area") Integer area,@Param("estateType")Integer estateType);
}
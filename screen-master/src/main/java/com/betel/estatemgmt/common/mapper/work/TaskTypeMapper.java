package com.betel.estatemgmt.common.mapper.work;

import com.betel.estatemgmt.common.model.work.TaskType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(TaskType record);

    int insertSelective(TaskType record);

    TaskType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(TaskType record);

    int updateByPrimaryKey(TaskType record);

    TaskType findByTypeName(@Param("typeName") String typeName,@Param("estateId") String estateId);

    void deleteByTypeIdArray(String[] typeIds);

    List<TaskType> findAllTaskTypes(RowBounds rowBounds, @Param("estateId") String estateId);

    List<TaskType> findTaskTypes(@Param("estateId") String estateId);
}
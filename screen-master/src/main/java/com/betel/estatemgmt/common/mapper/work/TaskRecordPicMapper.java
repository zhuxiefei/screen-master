package com.betel.estatemgmt.common.mapper.work;

import com.betel.estatemgmt.common.model.work.TaskRecordPic;
import com.betel.estatemgmt.common.model.work.TaskRecordPicKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRecordPicMapper {
    int deleteByPrimaryKey(TaskRecordPicKey key);

    int insert(TaskRecordPic record);

    int insertSelective(TaskRecordPic record);

    TaskRecordPic selectByPrimaryKey(TaskRecordPicKey key);

    int updateByPrimaryKeySelective(TaskRecordPic record);

    int updateByPrimaryKey(TaskRecordPic record);

    List<String> findPicUrlByRecordId(String recordId);

    List<String> findPicIdByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void insertList(List<TaskRecordPic> pics);
}
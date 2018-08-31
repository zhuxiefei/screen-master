package com.betel.estatemgmt.common.mapper.patrol;

import com.betel.estatemgmt.common.model.patrol.PatrolRecordPic;
import com.betel.estatemgmt.common.model.patrol.PatrolRecordPicKey;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatrolRecordPicMapper {
    int deleteByPrimaryKey(PatrolRecordPicKey key);

    int insert(PatrolRecordPic record);

    int insertSelective(PatrolRecordPic record);

    PatrolRecordPic selectByPrimaryKey(PatrolRecordPicKey key);

    int updateByPrimaryKeySelective(PatrolRecordPic record);

    int updateByPrimaryKey(PatrolRecordPic record);

    /**
     * 查询巡检图片
     *
     * @param recordId
     * @return
     */
    List<SystemPicture> findPictureByRecordId(String recordId);

    /**
     * 删除巡检记录与图片关系表
     *
     * @param RecordIds
     * @return
     */
    int deleteRecordPictureByRecordIds(String[] RecordIds);

    /**
     * 根据记录id查询图片集合
     *
     * @param RecordIds
     * @return
     */
    List<PatrolRecordPicKey> findSystemPictureByRecordIds(String[] RecordIds);

    void addPics(List<PatrolRecordPic> pics);
}
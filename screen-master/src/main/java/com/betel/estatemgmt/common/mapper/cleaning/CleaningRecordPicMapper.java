package com.betel.estatemgmt.common.mapper.cleaning;

import com.betel.estatemgmt.common.model.cleaning.CleaningRecordPic;
import com.betel.estatemgmt.common.model.cleaning.CleaningRecordPicKey;
import com.betel.estatemgmt.common.model.system.SystemPicture;

import java.util.List;

public interface CleaningRecordPicMapper {
    int deleteByPrimaryKey(CleaningRecordPicKey key);

    int insert(CleaningRecordPic record);

    int insertSelective(CleaningRecordPic record);

    CleaningRecordPic selectByPrimaryKey(CleaningRecordPicKey key);

    int updateByPrimaryKeySelective(CleaningRecordPic record);

    int updateByPrimaryKey(CleaningRecordPic record);

    void addPics(List<CleaningRecordPic> pics);

    List<SystemPicture> findPictureByRecordId(String recordId);
}
package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.HousePicture;
import com.betel.estatemgmt.business.web.house.model.PicReq;
import com.betel.estatemgmt.business.web.house.model.PictureInfo;
import com.betel.estatemgmt.business.web.house.model.UploadPictureReq;
import com.betel.estatemgmt.common.model.house.HousePic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HousePicMapper {
    int deleteByPrimaryKey(Long hpId);

    int insert(HousePic record);

    int insertSelective(HousePic record);

    HousePic selectByPrimaryKey(Long hpId);

    int updateByPrimaryKeySelective(HousePic record);

    int updateByPrimaryKey(HousePic record);

    void updateHousePic(PicReq deletePicReq);

    List<HousePicture> selectHousePicByHouseId(String houseId);

    HousePicture selectHouseCadPicByhousePicId(Long hpId);

    HousePic findHousePicByHouseIdAndPictureType(UploadPictureReq uploadPictureReq);

    PictureInfo findByHouseIdAndTypeName(@Param("houseId") String houseId, @Param("typeName") String typeName);

    void deleteByHouseIds(String[] array);

    List<HousePic> selectByHouseId(String houseId);

    void insertPicList(List<HousePic> pics);

    void updatePicList(List<HousePic> pics);
}
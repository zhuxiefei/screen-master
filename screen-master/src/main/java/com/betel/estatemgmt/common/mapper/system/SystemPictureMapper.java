package com.betel.estatemgmt.common.mapper.system;

import com.betel.estatemgmt.common.model.system.SystemPicture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemPictureMapper {
    int deleteByPrimaryKey(String pictureId);

    int insert(SystemPicture record);

    int insertSelective(SystemPicture record);

    SystemPicture selectByPrimaryKey(String pictureId);

    int updateByPrimaryKeySelective(SystemPicture record);

    int updateByPrimaryKey(SystemPicture record);

    /**
     * 删除巡检图片
     *
     * @param pictureIds
     * @return
     */
    int deleteSysPicByIds(List<String> pictureIds);

    void savePictures(List<SystemPicture> pictures);

    /**
     * 根据图片id查询图片
     *
     * @param pictureIds
     * @return
     */
    List<SystemPicture> findSysPicByIds(List<String> pictureIds);
}
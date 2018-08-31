package com.betel.estatemgmt.common.mapper.system;

import com.betel.estatemgmt.business.web.ad.model.Pic;
import com.betel.estatemgmt.common.model.system.Picture;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureMapper {
    int deleteByPrimaryKey(String pictureId);

    /**
     * 批量删除图片
     * @param pictureId
     * @return
     */
    int deleteBulkByPrimaryKeys(List<String> pictureId);

    int insert(Picture record);

    Long insertSelective(Picture record);

    Picture selectByPrimaryKey(String pictureId);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);

    Pic selectByPictureId(String pictureId);

    Picture selectByPicId(String pictureId);

    void deletePicList(List<String> list);
    /**
     * <p>
     * 批量保存图片
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/2 12:47
     *
     */
    void addActivityPics(List<Picture> pictures);
}
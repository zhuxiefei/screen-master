package com.betel.estatemgmt.common.mapper.repair;

import com.betel.estatemgmt.common.model.repair.RepairOrderPic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairOrderPicMapper {
    int deleteByPrimaryKey(Long opId);

    int insert(RepairOrderPic record);

    int insertSelective(RepairOrderPic record);

    RepairOrderPic selectByPrimaryKey(Long opId);

    int updateByPrimaryKeySelective(RepairOrderPic record);

    int updateByPrimaryKey(RepairOrderPic record);

    List<RepairOrderPic> findByOrderNo(String orderNo);

    void deletePicList(List<Long> list);

    /**
     * 批量添加报修相关图片
     * Create By ZhouYe ON 2017/9/15 15:05
     */
    void addRepairPics(List<RepairOrderPic> list);

    /**
     *  查询保修的相关图片
     * Create By ZhouYe ON 2017/9/15 17:21
     */
    List<String> findPictureUrls(String orderNo);
}
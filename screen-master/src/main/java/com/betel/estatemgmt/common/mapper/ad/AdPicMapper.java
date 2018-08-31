package com.betel.estatemgmt.common.mapper.ad;

import com.betel.estatemgmt.common.model.ad.AdPic;

import java.util.List;

public interface AdPicMapper {
    int deleteByPrimaryKey(Long apId);

    int insert(AdPic record);

    Long insertSelective(AdPic record);

    AdPic selectByPrimaryKey(Long apId);

    int updateByPrimaryKeySelective(AdPic record);

    int updateByPrimaryKey(AdPic record);

    List<AdPic> selectByAdId(Long adId);

    void insertList(List<AdPic> list);

    void deleteList(List<Long> list);
}
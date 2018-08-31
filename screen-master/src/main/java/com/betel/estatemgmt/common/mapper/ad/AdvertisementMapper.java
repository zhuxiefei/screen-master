package com.betel.estatemgmt.common.mapper.ad;


import com.betel.estatemgmt.business.userapp.index.model.AdInfor;
import com.betel.estatemgmt.business.userapp.index.model.SystemAd;
import com.betel.estatemgmt.business.web.ad.model.AdDetail;
import com.betel.estatemgmt.business.web.ad.model.AdInfo;
import com.betel.estatemgmt.business.web.ad.model.AdPage;
import com.betel.estatemgmt.common.model.ad.Advertisement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Long adId);

    int insert(Advertisement record);

    Long insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Long adId);

    Long updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

    List<AdInfor> findAllAdInfor(RowBounds rowBounds);

    SystemAd selAd(Long adId);

    List<AdInfo> findAllAds(RowBounds rowBounds, AdPage page);

    int deleteByAdIds(Long[] array);

    AdDetail selectByAdId(Long adId);

}
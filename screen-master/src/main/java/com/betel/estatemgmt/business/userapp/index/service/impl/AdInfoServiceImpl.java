package com.betel.estatemgmt.business.userapp.index.service.impl;

import com.betel.estatemgmt.business.userapp.index.model.AdInfor;
import com.betel.estatemgmt.business.userapp.index.model.SystemAd;
import com.betel.estatemgmt.business.userapp.index.service.AdInfoService;
import com.betel.estatemgmt.common.mapper.ad.AdvertisementMapper;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 查询广告业务接口实现类
 * </p>
 * ClassName: AdInfoServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/15 14:25 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AdInfoServiceImpl implements AdInfoService{

    @Autowired
    AdvertisementMapper advertisementMapper;


    @Override
    public List<AdInfor> findAllAds(Paging<AdInfor> paging) {
        return advertisementMapper.findAllAdInfor(paging.getRowBounds());
    }

    @Override
    public SystemAd selAd(Long adId) {
        return advertisementMapper.selAd(adId);
    }
}

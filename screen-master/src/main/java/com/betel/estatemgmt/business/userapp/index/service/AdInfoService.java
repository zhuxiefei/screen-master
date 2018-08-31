package com.betel.estatemgmt.business.userapp.index.service;

import com.betel.estatemgmt.business.userapp.index.model.AdInfor;
import com.betel.estatemgmt.business.userapp.index.model.SystemAd;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 查询广告业务接口
 * </p>
 * Author: zhouye  <br/>
 * Date:  2017/5/15 14:15 <br/>
 * Version: 1.0 <br/>
 */
public interface AdInfoService {

     List<AdInfor> findAllAds(Paging<AdInfor> paging);

     SystemAd selAd(Long adId);
}

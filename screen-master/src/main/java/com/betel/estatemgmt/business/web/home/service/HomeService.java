package com.betel.estatemgmt.business.web.home.service;

import com.betel.estatemgmt.business.web.home.model.HomePage;

/**
 * Created by zhangjian on 2017/8/7.
 */
public interface HomeService {
    /**
     * 查询快速代办入口
     *
     * @return
     */
    HomePage findHomePageNew();
}

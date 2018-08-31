package com.betel.estatemgmt.business.propertyapp.home.service;

import com.betel.estatemgmt.business.propertyapp.home.model.HomeReq;
import com.betel.estatemgmt.business.propertyapp.home.model.HomeResp;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;

import java.util.List;

/**
 * Created by zhangjian on 2018/1/3.
 */
public interface AppHomeService {
    /**
     * 只有指派权限
     */
    HomeResp statisticsAssign(HomeReq homeReq);
    /**
     * 只有指派权限，处理权限
     */
    HomeResp statisticsAssignDispose(HomeReq homeReq);
    /**
     * 只有处理权限
     */
    HomeResp statisticsnDispose(HomeReq homeReq);
    /**
     *  查询装修申请数
     */
    List<DecoApplyRecord> findApplyOnline(String estateId);
    /**
     * 查询待巡检数
     */
    int pollingToPatrol(String estateId);

    /**
     * 待巡逻数
     * @return
     */
    int findUnFinishSecurity(String estateId);
}

package com.betel.estatemgmt.business.propertyapp.home.service.impl;

import com.betel.estatemgmt.business.propertyapp.home.constant.HomeConstant;
import com.betel.estatemgmt.business.propertyapp.home.model.HomeReq;
import com.betel.estatemgmt.business.propertyapp.home.model.HomeResp;
import com.betel.estatemgmt.business.propertyapp.home.service.AppHomeService;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.common.mapper.decoration.DecoApplyRecordMapper;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.patrol.PatrolRecordMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderPicMapper;
import com.betel.estatemgmt.common.mapper.security.SecurityRecordMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangjian on 2018/1/3.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppHomeServiceImpl implements AppHomeService {
    @Autowired
    RepairOrderMapper repairOrderMapper;

    @Autowired
    RepairOrderPicMapper repairOrderPicMapper;

    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    DecoApplyRecordMapper decoApplyRecordMapper;

    @Autowired
    private PatrolRecordMapper patrolRecordMapper;

    @Autowired
    SecurityRecordMapper recordMapper;

    @Autowired
    EstateMapper estateMapper;

    @Override
    public HomeResp statisticsAssign(HomeReq homeReq) {
        homeReq.setStartTime(getTime().get("startTime"));
        homeReq.setEndTime(getTime().get("endTime"));
        //可查看今日新增报修单数量
        Integer todayNewAdd = queryTodayReapirNum(homeReq);
        //查看未指派报修单数量
        Integer allUnassign = queryAllUnassignNum();
        //查看今日已指派报修单数量
        Integer todayAssign = queryAllassignNum(homeReq);
        //今日已完成维修的报修单数量
        Integer finish = finishRepairNum(homeReq);
        HomeResp homeResp = new HomeResp();
        homeResp.setTodayNewAdd(String.valueOf(todayNewAdd));
        homeResp.setAllUnassign(String.valueOf(allUnassign));
        homeResp.setTodayAssign(String.valueOf(todayAssign));
        homeResp.setFinish(String.valueOf(finish));
        return homeResp;
    }

    @Override
    public HomeResp statisticsAssignDispose(HomeReq homeReq) {
        homeReq.setStartTime(getTime().get("startTime"));
        homeReq.setEndTime(getTime().get("endTime"));
        //可查看今日新增报修单数量
        Integer todayNewAdd = queryTodayReapirNum(homeReq);
        //查看未指派报修单数量
        Integer allUnassign = queryAllUnassignNum();
        //查看今日已指派报修单数量
        Integer todayAssign = queryAllassignNum(homeReq);
        //指派给我前去维修的报修单数量
        Integer assignMe = assignMeNumNow(homeReq);
        //今日已完成维修的报修单数量
        Integer finish = finishRepairNum(homeReq);
        HomeResp homeResp = new HomeResp();
        homeResp.setTodayNewAdd(String.valueOf(todayNewAdd));
        homeResp.setAllUnassign(String.valueOf(allUnassign));
        homeResp.setTodayAssign(String.valueOf(todayAssign));
        homeResp.setAssignMe(String.valueOf(assignMe));
        homeResp.setFinish(String.valueOf(finish));
        return homeResp;
    }

    @Override
    public HomeResp statisticsnDispose(HomeReq homeReq) {
        homeReq.setStartTime(getTime().get("startTime"));
        homeReq.setEndTime(getTime().get("endTime"));
        //指派给我前去维修的报修单数量
        Integer assignMe = assignMeNumNow(homeReq);
        //今日已完成维修的报修单数量(报修处理)
        Integer disposeFinish = finishRepairNumOfMySelf(homeReq);
        HomeResp homeResp = new HomeResp();
        homeResp.setAssignMe(String.valueOf(assignMe));
        homeResp.setDisposeFinish(String.valueOf(disposeFinish));
        return homeResp;
    }


    @Override
    public List<DecoApplyRecord> findApplyOnline(String estateId) {
        //判断estateId属于哪类楼盘
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if (null != estate){
            List<DecoApplyRecord> list = null;
            if (HomeConstant.PROPERTY_ESTATE_FLAG.equals(estate.getEstateType())){
              //小区楼盘
                list = decoApplyRecordMapper.findPropertyApplyOnline(estateId);
            }
            if (HomeConstant.BUSINESS_ESTATE_FLAG.equals(estate.getEstateType())){
                //商用楼盘
                list = decoApplyRecordMapper.findBusinessApplyOnline(estateId);
            }
            return list;
        }
        return null;
    }

    @Override
    public int pollingToPatrol(String estateId) {
        List<Record> records = patrolRecordMapper.selectCheckList(estateId);
        List<Record> recordList=new ArrayList<>();
        for (int i=0;i<records.size();i++){
            if (!StringUtil.isBlank(records.get(i).getEquipmentNo())){
                recordList.add(records.get(i));
            }
        }
        return recordList.size();
    }

    @Override
    public int findUnFinishSecurity(String estateId) {
        return recordMapper.countUnfinish(estateId);
    }


    /**
     * 今日新增报修单（待维修）数量
     *
     * @param homeReq
     * @return
     */
    protected Integer queryTodayReapirNum(HomeReq homeReq) {

        return repairOrderMapper.queryTodayReapirNum(homeReq);
    }

    /**
     * 所有未指派维修总数量,不限于今日
     *
     * @return
     */
    protected Integer queryAllUnassignNum() {
        return repairOrderMapper.queryAllUnassignNum();
    }

    /**
     * 今日已指派报修单数量
     *
     * @param homeReq
     * @return
     */
    protected Integer queryAllassignNum(HomeReq homeReq) {
        homeReq.setStartTime(getTime().get("startTime"));
        homeReq.setEndTime(getTime().get("endTime"));
        return repairOrderMapper.queryAllassignNum(homeReq);
    }

    /**
     * 今日已完成维修总数量
     * （报修指派模块）
     *
     * @param homeReq
     * @return
     */
    protected Integer finishRepairNum(HomeReq homeReq) {
        homeReq.setStartTime(getTime().get("startTime"));
        homeReq.setEndTime(getTime().get("endTime"));
        return repairOrderMapper.finishRepairNum(homeReq);
    }

    /**
     * 今日已完成维修总数量
     * （报修处理模块）
     *
     * @param homeReq
     * @return
     */
    public Integer finishRepairNumOfMySelf(HomeReq homeReq) {
        homeReq.setStartTime(getTime().get("startTime"));
        homeReq.setEndTime(getTime().get("endTime"));
        return repairOrderMapper.finishRepairNumOfMySelf(homeReq);
    }

    /**
     * 查询所有指派给我前去维修的报修数量
     * 已接单
     *
     * @param homeReq
     * @return
     */
    public Integer assignMeNumNow(HomeReq homeReq) {
        Integer assignMe = repairOrderMapper.assignMeNumNow(homeReq.getUserId());
        return assignMe;
    }


    /**
     * 获取当日时间区间
     *
     * @return
     */
    private Map<String, String> getTime() {
        Map<String, String> map = new HashMap<String, String>();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curTime = dateFormat.format(now);
        String startTime = curTime + " 00:00:00";
        String endTime = curTime + " 23:59:59";
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }


}

package com.betel.estatemgmt.business.smarthome.service;

import com.betel.estatemgmt.business.smarthome.model.BuildingResp;
import com.betel.estatemgmt.business.smarthome.model.HouseInfo;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.common.model.system.Notice;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8/008.
 */
public interface SmartHomeService {

    HouseInfo findByHouseId(String houseId);

    List<Member> findMembers(String houseId);

    void insertNotice(Notice notice);

    List<BuildingResp> findBuildings(String estateId);
}

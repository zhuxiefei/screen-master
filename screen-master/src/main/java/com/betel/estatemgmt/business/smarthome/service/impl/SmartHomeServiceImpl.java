package com.betel.estatemgmt.business.smarthome.service.impl;

import com.betel.estatemgmt.business.smarthome.model.BuildingResp;
import com.betel.estatemgmt.business.smarthome.model.HouseInfo;
import com.betel.estatemgmt.business.smarthome.service.SmartHomeService;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.system.Notice;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/8/008.
 */
@Service("SmartHomeService")
@Transactional(rollbackFor = Exception.class)
public class SmartHomeServiceImpl implements SmartHomeService {

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    HouseOwnerMapper ownerMapper;

    @Autowired
    HouseMemberMapper memberMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    BuildingMapper buildingMapper;

    @Autowired
    BuildingUnitMapper unitMapper;

    @Override
    public HouseInfo findByHouseId(String houseId) {
        HouseInfo info = new HouseInfo();
        House houseByHouseId = houseMapper.findHouseByHouseId(houseId);
        if (null != houseByHouseId) {
            info.setHouseName(houseByHouseId.getHouseNum());
        }
        return info;
    }

    @Override
    public List<Member> findMembers(String houseId) {
        List<Member> memList = new ArrayList<>();
        //查询户主
        Member member = ownerMapper.findMemberByHouseId(houseId);
        if (null != member){
            memList.add(member);
        }
        //查询成员
        List<Member> members = memberMapper.findMemberByHouseId(houseId);
        if (null != members && members.size() > 0){
            for (Member m:
                    members) {
                memList.add(m);
            }
        }
        return memList;
    }

    @Override
    public void insertNotice(Notice notice) {
        noticeMapper.insertSelective(notice);
    }

    @Override
    public List<BuildingResp> findBuildings(String estateId) {
        return buildingMapper.findBuildings(estateId);
    }
}

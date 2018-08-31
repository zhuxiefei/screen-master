package com.betel.estatemgmt.business.web.house.service.impl;

import com.betel.estatemgmt.business.web.ad.model.Pic;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.house.code.HouseCode;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.house.constant.OfficeHouseConstant;
import com.betel.estatemgmt.business.web.house.model.officeHouse.*;
import com.betel.estatemgmt.business.web.house.service.OfficeHouseService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.mapper.decoration.DecoApplyRecordMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.lease.RentMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderPicMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderReplyMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.lease.Rent;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.repair.RepairOrderPic;
import com.betel.estatemgmt.common.model.repair.RepairOrderReply;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.Validate;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */
@Service("OfficeHouseService")
@Transactional(rollbackFor = Exception.class)
public class OfficeHouseServiceImpl implements OfficeHouseService {

    @Autowired
    BuildingMapper buildingMapper;

    @Autowired
    BuildingUnitMapper unitMapper;

    @Autowired
    OfficeHouseMapper officeHouseMapper;

    @Autowired
    RepairOrderMapper orderMapper;

    @Autowired
    PictureMapper pictureMapper;

    @Autowired
    RepairOrderReplyMapper replyMapper;

    @Autowired
    RepairOrderPicMapper orderPicMapper;

    @Autowired
    DecoApplyRecordMapper applyMapper;

    @Autowired
    RentMapper rentMapper;

    @Override
    public String addOfficeHouse(AddOfficeReq addOfficeReq) {
        String code = validateAddOffice(addOfficeReq);
        if (!StringUtil.isBlank(code)) {
            return code;
        }
        //将数据封装到house对象中
        OfficeHouse house = new OfficeHouse();
        house.setHouseId(UuidUtil.create());
        if (StringUtil.isBlank(addOfficeReq.getBuildingId())) {
            house.setBuildingId(null);
        } else {
            house.setBuildingId(Long.parseLong(addOfficeReq.getBuildingId()));
        }
        if (StringUtil.isBlank(addOfficeReq.getUnitId())) {
            house.setUnitId(null);
        } else {
            house.setUnitId(Long.parseLong(addOfficeReq.getUnitId()));
        }
        house.setCreateTime(new Date(System.currentTimeMillis()));
        house.setHouseNum(addOfficeReq.getHouseNum());
        house.setInterFloorArea(Double.parseDouble(addOfficeReq.getInterFloorArea()));
        house.setHouseStatus(Integer.parseInt(addOfficeReq.getHouseStatus()));
        if (!StringUtil.isBlank(addOfficeReq.getDisplayOrder())) {
            house.setDisplayOrder(Integer.parseInt(addOfficeReq.getDisplayOrder()));
        }
        house.setEstateId(addOfficeReq.getEstateId());
        officeHouseMapper.insertSelective(house);
        return null;
    }

    @Override
    public String updateOfficeHouse(UpdateOfficeReq officeReq) {
        String code = validateUpdateOffice(officeReq);
        if (!StringUtil.isBlank(code)) {
            return code;
        }
        //将数据封装到house对象中
        OfficeHouse house = new OfficeHouse();
        house.setHouseId(officeReq.getHouseId());
        if (StringUtil.isBlank(officeReq.getBuildingId())) {
            house.setBuildingId(null);
        } else {
            house.setBuildingId(Long.parseLong(officeReq.getBuildingId()));
        }
        if (StringUtil.isBlank(officeReq.getUnitId())) {
            house.setUnitId(null);
        } else {
            house.setUnitId(Long.parseLong(officeReq.getUnitId()));
        }
        house.setHouseNum(officeReq.getHouseNum());
        house.setInterFloorArea(Double.parseDouble(officeReq.getInterFloorArea()));
        if (!StringUtil.isBlank(officeReq.getDisplayOrder())) {
            house.setDisplayOrder(Integer.parseInt(officeReq.getDisplayOrder()));
        }
        if (!StringUtil.isBlank(officeReq.getHouseStatus())) {
            house.setHouseStatus(Integer.parseInt(officeReq.getHouseStatus()));
        }
        officeHouseMapper.updateByPrimaryKeySelective(house);
        return null;
    }

    @Override
    public List<FindAllOfficeResp> findAllOffices(Paging<FindAllOfficeResp> paging, FindAllOfficesReq officesReq) {
        List<FindAllOfficeResp> list = officeHouseMapper.findAllOffices(paging.getRowBounds(), officesReq);
        if (null != list && list.size() > 0) {
            for (FindAllOfficeResp resp :
                    list) {
                //若是出租状态，查询出租合同时间
                if (OfficeHouseConstant.RENT_STATUS.equals(resp.getHouseStatus())){
                    Rent rent = rentMapper.findNowRent(resp.getHouseId());
                    if (rent != null){
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        resp.setRentTime(format.format(rent.getStartTime())+"~"+format.format(rent.getEndTime()));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void deleteOffices(String houseIds) {
        if (!StringUtil.isBlank(houseIds)){
            String[] houseIdArray = houseIds.split(",");
            //删除维修单列表
            List<String> repairList = new ArrayList<>();
            //删除维修评价单列表
            List<Long> replyList = new ArrayList<>();
            //删除维修图片列表
            List<Long> repairPicList = new ArrayList<>();
            //删除图片列表
            List<String> pictureList = new ArrayList<>();
            for (int i = 0; i < houseIdArray.length; i++) {
                //查询所有维修单
                List<RepairOrder> orders = orderMapper.findByHouseId(houseIdArray[i]);
                if (orders != null && orders.size() > 0) {
                    for (RepairOrder order
                            : orders) {
                        repairList.add(order.getOrderNo());
                        if (order.getOrderPic() != null) {
                            pictureList.add(order.getOrderPic());
                            //删除物理路径
                            Pic picture = pictureMapper.selectByPictureId(order.getOrderPic());
                            FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + picture.getPictureUrl());
                        }
                        //查询评价
                        List<RepairOrderReply> replies = replyMapper.findByOrderNo(order.getOrderNo());
                        if (replies != null && replies.size() > 0) {
                            for (RepairOrderReply reply :
                                    replies) {
                                replyList.add(reply.getReplyId());
                            }
                        }
                        //查询图片
                        List<RepairOrderPic> pics = orderPicMapper.findByOrderNo(order.getOrderNo());
                        if (pics != null && pics.size() > 0) {
                            for (RepairOrderPic pic :
                                    pics) {
                                repairPicList.add(pic.getOpId());
                                pictureList.add(pic.getPictureId());
                                //删除物理路径
                                Pic picture = pictureMapper.selectByPictureId(pic.getPictureId());
                                FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + picture.getPictureUrl());
                            }
                        }
                    }
                }
            }
            //删除维修单
            if (repairList.size() != 0) {
                orderMapper.deleteOrderList(repairList);
            }
            //删除维修评价
            if (replyList.size() != 0) {
                replyMapper.deleteReplyList(replyList);
            }
            //删除维修图片
            if (repairPicList.size() != 0) {
                orderPicMapper.deletePicList(repairPicList);
            }
            //删装修申请
            applyMapper.deleteByHouseIds(houseIdArray);
            //删除房屋
            officeHouseMapper.deleteOffices(houseIdArray);
            //删除租赁信息
            rentMapper.deleteByHouseIds(houseIdArray);
        }
    }

    @Override
    public void updateRentStatus() {
        List<OfficeHouse> houses = officeHouseMapper.findHouses();
        if (null != houses && houses.size() > 0){
            for (OfficeHouse house:
                    houses) {
                //空置、招商--》出租
                if (house.getHouseStatus().equals(OfficeHouseConstant.BUSINESS_STATUS)
                        || house.getHouseStatus().equals(OfficeHouseConstant.EMPTY_STATUS)){
                    //查询当前时间下是否到出租时间
                    Rent rent = rentMapper.findNowRent(house.getHouseId());
                    if (null != rent){
                        house.setHouseStatus(OfficeHouseConstant.RENT_STATUS);
                        officeHouseMapper.updateByPrimaryKeySelective(house);
                    }
                }
            }
        }
    }

    @Override
    public void updateBusinessStatus() {
        List<OfficeHouse> houses = officeHouseMapper.findHouses();
        if (null != houses && houses.size() > 0){
            for (OfficeHouse house:
                 houses) {
                //出租--》招商
                if (house.getHouseStatus().equals(OfficeHouseConstant.RENT_STATUS)){
                    //查询当前时间下是否到退租时间
                    Rent rent = rentMapper.findOutRent(house.getHouseId());
                    if (null != rent){
                        house.setHouseStatus(OfficeHouseConstant.BUSINESS_STATUS);
                        officeHouseMapper.updateByPrimaryKeySelective(house);
                    }
                }
            }
        }
    }

    @Override
    public OfficeHouse findByHouseId(String houseId) {
        return officeHouseMapper.selectByPrimaryKey(houseId);
    }

    @Override
    public FindOfficeHouseResp findOfficeHouse(String houseId) {
        return officeHouseMapper.findOfficeHouse(houseId);
    }

    /**
     * 校验修改房屋入参
     *
     * @param officeReq
     * @return
     */
    private String validateUpdateOffice(UpdateOfficeReq officeReq) {
        //定义code码
        String code = null;
        if (!StringUtil.isBlank(officeReq.getBuildingId())
                && buildingMapper.selectByPrimaryKey(Long.parseLong(officeReq.getBuildingId())) == null) {
            //楼宇不存在
            code = HouseCode.BUILDING_NOT_EXIST;
        } else if (!StringUtil.isBlank(officeReq.getUnitId())
                && unitMapper.selectByPrimaryKey(Long.parseLong(officeReq.getUnitId())) == null) {
            //单元不存在
            code = HouseCode.UNIT_IS_NULL;
        } else if (!StringUtil.isBlank(officeReq.getUnitId())
                && StringUtil.isBlank(officeReq.getBuildingId())) {
            //有单元无楼宇
            code = HouseCode.BUILDING_IS_NULL;
        } else if (!StringUtil.isBlank(officeReq.getUnitId()) && !StringUtil.isBlank(officeReq.getBuildingId())
                && unitMapper.selectByPrimaryKey(Long.parseLong(officeReq.getUnitId())) != null
                && !unitMapper.selectByPrimaryKey(Long.parseLong(officeReq.getUnitId())).getBuildingId().equals(Long.parseLong(officeReq.getBuildingId()))) {
            //单元楼宇不匹配
            code = HouseCode.UNIT_BUILDING_MATCH;
        } else if (StringUtil.isEmpty(officeReq.getHouseNum())) {
            //房号为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (StringUtil.isEmpty(officeReq.getHouseNum().trim())) {
            //房号去空格为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (!officeReq.getHouseNum().trim().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
            //房号格式不符合
            code = HouseCode.HOUSE_NUM_RULE;
        } else if (officeHouseMapper.selectByUpdateHouseReq(officeReq) != null) {
            //该房屋已存在
            code = HouseCode.HOUSE_IS_EXIST;
        } else if (StringUtil.isBlank(officeReq.getInterFloorArea())) {
            //建筑面积为空
            code = HouseCode.FLOORAREA_IS_NULL;
        } else if (!officeReq.getInterFloorArea().trim().matches(HouseDataValidation.AREA_RULE)) {
            //建筑面积格式错误
            code = HouseCode.FLOORAREA_RULE_ERROR;
        } else if (!StringUtil.isBlank(officeReq.getDisplayOrder())
                && !Validate.isNum(officeReq.getDisplayOrder(), HouseDataValidation.ORDER_MAX_LENGTH)) {
            //排序字段错误
            code = HouseCode.ORDER_RULE;
        }
        return code;
    }

    /**
     * 校验新增房屋入参
     *
     * @param addOfficeReq
     * @return
     */
    private String validateAddOffice(AddOfficeReq addOfficeReq) {
        //定义code码
        String code = null;
        if (!StringUtil.isBlank(addOfficeReq.getBuildingId())
                && buildingMapper.selectByPrimaryKey(Long.parseLong(addOfficeReq.getBuildingId())) == null) {
            //楼宇不存在
            code = HouseCode.BUILDING_NOT_EXIST;
        } else if (!StringUtil.isBlank(addOfficeReq.getUnitId())
                && unitMapper.selectByPrimaryKey(Long.parseLong(addOfficeReq.getUnitId())) == null) {
            //单元不存在
            code = HouseCode.UNIT_IS_NULL;
        } else if (!StringUtil.isBlank(addOfficeReq.getUnitId())
                && StringUtil.isBlank(addOfficeReq.getBuildingId())) {
            //有单元无楼宇
            code = HouseCode.BUILDING_IS_NULL;
        } else if (!StringUtil.isBlank(addOfficeReq.getUnitId()) && !StringUtil.isBlank(addOfficeReq.getBuildingId())
                && unitMapper.selectByPrimaryKey(Long.parseLong(addOfficeReq.getUnitId())) != null
                && !unitMapper.selectByPrimaryKey(Long.parseLong(addOfficeReq.getUnitId())).getBuildingId().equals(Long.parseLong(addOfficeReq.getBuildingId()))) {
            //单元楼宇不匹配
            code = HouseCode.UNIT_BUILDING_MATCH;
        } else if (StringUtil.isEmpty(addOfficeReq.getHouseNum())) {
            //房号为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (StringUtil.isEmpty(addOfficeReq.getHouseNum().trim())) {
            //房号去空格为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (!addOfficeReq.getHouseNum().trim().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
            //房号格式不符合
            code = HouseCode.HOUSE_NUM_RULE;
        } else if (officeHouseMapper.selectByHouseReq(addOfficeReq) != null) {
            //该房屋已存在
            code = HouseCode.HOUSE_IS_EXIST;
        } else if (StringUtil.isBlank(addOfficeReq.getInterFloorArea())) {
            //建筑面积为空
            code = HouseCode.FLOORAREA_IS_NULL;
        } else if (!addOfficeReq.getInterFloorArea().trim().matches(HouseDataValidation.AREA_RULE)) {
            //建筑面积格式错误
            code = HouseCode.FLOORAREA_RULE_ERROR;
        } else if (!StringUtil.isBlank(addOfficeReq.getDisplayOrder())
                && !Validate.isNum(addOfficeReq.getDisplayOrder(), HouseDataValidation.ORDER_MAX_LENGTH)) {
            //排序字段错误
            code = HouseCode.ORDER_RULE;
        }
        return code;
    }
}

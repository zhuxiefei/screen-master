package com.betel.estatemgmt.business.web.house.service;

import com.betel.estatemgmt.business.web.ad.model.Pic;
import com.betel.estatemgmt.business.web.house.model.*;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 房屋信息维护实现接口
 * </p>
 * ClassName: HouseService <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 12:29 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseService {
    List<HouseInfo> findAllHouse(Paging<HouseInfo> pager, HousePageReq housePageReq);

    Building findByBuildingId(Long buildingId);

    BuildingUnit findByUnitId(Long unitId);

    HouseType findByTypeId(Long typeId);

    House findByMultipleParams(AddHouseReq addHouseReq);

    void addHouse(House house, UpdateHouseReq houseReq);

    Building findByBuildingName(String buildingName,String estateId);

    List<BuildingUnit> findByUnitNameAndBuildingId(String unitName, Long buildingId);

    HouseType findByTypeName(String typeName);

    House findByHouseId(String houseId);

    HouseInformation findHouseInformationByHouseId(String houseId);

    void deleteHouseByHouseIds(String houseIds,HttpServletRequest request) throws Exception;

    String updateHouse(House house, UpdateHouseReq req);

    List<PictureInfo> findPictureType();

    String addPicture(Picture picture);

    HousePic findHousePicByHouseIdAndPictureType(UploadPictureReq uploadPic);

    void updateHousePicture(HousePic housePic);

    void insertHousePicture(HousePic housePicture);

    HousePic findHousePicByHpId(PicReq deletePicReq);

    void updateHousePic(PicReq deletePicReq);

    void deleteHousePic(PicReq deletePicReq);

    Pic findPicById(String fileId);

    PictureInfo findByHouseIdAndTypeName(String houseId, String typeName);

    List<HouseTypeResp> findHouseTypes();

    void addHouseList(List<House> houseList);

    House findByHouseReq(UpdateHouseReq houseReq);

    void updateDeliverTime(UpdateDeliverTimeReq req);

    HousePicType findByHouseTypeName(String typeName);

    void insertPicture(Picture picture);

    void insertHousePicList(List<HousePic> pics);

    void updateHousePicList(List<HousePic> pics);

    HouseParkingSpace findBySpaceNum(String spaceNum);

    /**
     * 通过userId删除户主和成员
     *
     * @param userIds
     */
    void deleteOwnerMemberByUserId(String[] userIds, HttpServletRequest request) throws Exception;

    /**
     * 根据userId查询房屋信息
     *
     * @param userId
     * @return
     */
    List<House> findHouseByUserId(String userId);
}

package com.betel.estatemgmt.business.userapp.house.service;

import com.betel.estatemgmt.business.userapp.house.model.HousePicture;
import com.betel.estatemgmt.business.userapp.house.model.ShareURL;
import com.betel.estatemgmt.business.userapp.house.model.UserHouseAuth;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.system.Picture;

import java.util.List;

/**
 * <p>
 * 我的家service
 * </p>
 * ClassName: HouseService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/16 12:57 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseService {

    /**
     * <p>
     * 查询所有单元和楼宇为空的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 13:54
     * return List<House>
     */
    List<House> selecteHouseVillas(String estateId);

    /**
     * <p>
     * 查询所有的楼宇
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 14:23
     * return List<Building>
     */
    List<Building> selecteAllBuilding(String estateId);

    /**
     * <p>
     * 根据楼宇Id查询单元
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 14:35
     * return response
     */
    List<BuildingUnit> selecteUnitByBuildId(Long buildId);

    /**
     * <p>
     * 根据楼宇Id查询单元为空的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 14:35
     * return response
     */
    List<House> selecteUnitByBuildIdUnitNull(Long buildId,String estateId);

    /**
     * <p>
     * 根据单元Id查询房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 14:35
     * return response
     */
    List<House> selecteHouseByUnitId(Long unitId,String estateId);

    /**
     * <p>
     * 根据房屋id查询房屋的图纸
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 14:25
     * return response
     */
    List<HousePicture> findHousePicByHouseId(String houseId);

    /**
     * <p>
     * 根据房屋图纸id查询房屋图纸url和cad图纸的url
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 15:40
     * return response
     */
    HousePicture findHousePictureByHousePicID(Long housepicId);

    /**
     * <p>
     * 根据id查询房屋楼宇、单元和房屋号
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 16:52
     * return response
     */
    UserHouseAuth findHouseBuildUnitByHouseId(String houseId);

    /**
     * <p>
     * 插入图片分享表和图片表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 10:12
     * return response
     */
    void addShareAndPic(HousePictureShare housePicShare, Picture picture);

    /**
     * <p>
     * 查询图片记录，删除过期记录
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 11:19
     * return response
     */
    void deleteSharePicture();

    /**
     * <p>
     * 根据主键查询分享记录
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 11:56
     * return response
     */
    ShareURL selectShareByShareId(String id);

    /**
     * <p>
     * 根据房屋号查询房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/7/6 14:57
     * return response
     */
    House findHouseByHouseId(String houseId);

    List<OfficeHouse> selecteHouseByUnitIdOffice(String id);

}

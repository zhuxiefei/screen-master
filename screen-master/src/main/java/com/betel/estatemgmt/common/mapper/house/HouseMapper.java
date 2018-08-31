package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.HouseInfoApp;
import com.betel.estatemgmt.business.userapp.house.model.UserHouseAuth;
import com.betel.estatemgmt.business.web.house.model.*;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusInfo;
import com.betel.estatemgmt.business.web.screen.model.HouseData;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseMapper {
    int deleteByPrimaryKey(String houseId);

    int insert(House record);

    int insertSelective(House record);

    House selectByPrimaryKey(String houseId);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);

    List<HouseInfo> findAllHouse(RowBounds rowBounds, HousePageReq housePageReq);

    List<House> selectByBuildingId(Long buildingId);

    House selectByMultipleParams(AddHouseReq addHouseReq);

    HouseInformation selectHouseInformationByHouseId(String houseId);

    int updateUnitId(String houseId);

    List<House> selectByUnitId(Long unitId);

    List<House> selectAllHouse(String estateId);

    List<House> selecteHouseVillas(String estateId);

    List<House> selectHouseByBuildIdUnitNull(Long buildId);

    UserHouseAuth findHouseBuildUnitByHouseId(String houseId);

    /**
     * <p>
     * 根据用户ID作为户主ID 查询房屋信息
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/23 10:25
     *
     * @param userId 用户ID
     * @return List<House> 房屋信息集合
     */
    List<House> selectHouseOwnerByuserId(String userId);

    /**
     * <p>
     * 通过房屋ID，查询房屋信息
     * </p>
     * Author: zhanglei,zhangjian <br/>
     * Date: 2017/6/24 17:42
     *
     * @param houseId
     * @return House
     */
    House findHouseByHouseId(String houseId);

    int insertHouseList(List<House> houseList);

    House selectByHouseReq(UpdateHouseReq houseReq);

    void deleteByHouseIds(String[] array);

    void updateDeliverTime(@Param("houseIds") String[] houseIds, @Param("time") String time);

    List<com.betel.estatemgmt.business.web.repair.model.HouseInfo> selectByIds(@Param("buildingId") String buildingId, @Param("unitId") String unitId);

    /**
     * 根据收费项Id查询房屋列表
     *
     * @param itemId
     * @return
     */
    List<House> selectByItemId(Long itemId);

    /**
     * 编辑房屋
     *
     * @param house
     */
    void updateHouse(House house);

    /**
     * <p>
     * 根据房屋id，查询房屋信息
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/14 20:39
     */
    HouseInfoApp findHouseInfo(String houseId);
    //    ==================房屋状态管理=====================开始==================

    /**
     * 查询房屋状态
     *
     * @param buildingId
     * @return
     */
    List<HouseStatusInfo> findHouseStatus(@Param("buildingId") Long buildingId);

    /**
     * 第一次加载时查询别墅信息
     *
     * @return
     */
    List<HouseStatusInfo> findVillasByFist();

    /**
     * 通过单元id,楼宇id查询信息
     *
     * @return
     */
    List<HouseStatusInfo> findHouseByUnitId(BuildingUnit buildingUnit);

    /**
     * 查看房屋状态详情
     *
     * @param houseId
     * @return
     */
    HouseStatusInfo findHouseDetail(String houseId);

//    ==================房屋状态管理=====================结束==================

    HouseData findHouseData();
}
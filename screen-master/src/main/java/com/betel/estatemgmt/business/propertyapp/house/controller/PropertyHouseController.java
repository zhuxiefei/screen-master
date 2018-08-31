package com.betel.estatemgmt.business.propertyapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.controller.AppHouseContrller;
import com.betel.estatemgmt.business.userapp.house.model.AllHouse;
import com.betel.estatemgmt.business.userapp.house.model.Builds;
import com.betel.estatemgmt.business.userapp.house.model.UnitOne;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: PropertyHouseController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/31 11:28 <br/>
 * Version: 1.0 <br/>
 */
@Controller
@RequestMapping("propertyApp/house")
public class PropertyHouseController {

    private static final Logger LOG = LoggerFactory.getLogger(AppHouseContrller.class);

    @Autowired
    HouseService houseService;

    /**
     * <p>
     * 查询所有的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 13:02
     * return response
     */
    @RequestMapping(value = "v1/findAllHouse", method = RequestMethod.GET)
    @ResponseBody
    public Response<AllHouse> findAllHouse(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-----propertyApp/house/v1/findAllHouse---------start");
        }
        Response<AllHouse> response = new Response<>();
        try{
            //房屋分类模型
            AllHouse allHouse = new AllHouse();
            //所有的楼宇
            List<Builds> builds = new ArrayList<>();
            List<Building> buildings;
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            buildings = houseService.selecteAllBuilding(estateId);
            //每个楼宇里分类单元和房屋
            for (Building building : buildings) {
                Builds build = new Builds();
                build.setBuildingName(building.getBuildingName());
                List<UnitOne> unitOnes = new ArrayList<>();
                //楼宇下的单元
                List<BuildingUnit> buildingUnits;
                buildingUnits = houseService.selecteUnitByBuildId(building.getBuildingId());
                //每个单元里分类房屋
                for (BuildingUnit buildingUnit : buildingUnits) {
                    UnitOne unitone = new UnitOne();
                    unitone.setUnitName(buildingUnit.getUnitName());
                    unitone.setHouses(houseService.selecteHouseByUnitId(buildingUnit.getUnitId(),AESUtil.decrypt(request.getHeader("estateId"))));
                    unitOnes.add(unitone);
                }
                build.setUnits(unitOnes);
                //楼宇下单元为空的房屋
                List<House> houses = houseService.selecteUnitByBuildIdUnitNull(building.getBuildingId(),AESUtil.decrypt(request.getHeader("estateId")));
                build.setLittltvilla(houses);
                builds.add(build);
            }
            allHouse.setBuilds(builds);
            allHouse.setVilla(houseService.selecteHouseVillas(AESUtil.decrypt(request.getHeader("estateId"))));
            response.setData(allHouse);
        }catch (Exception e){
            LOG.error("-----propertyApp/house/v1/findAllHouse---------error",e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-------propertyApp/house/v1/findAllHouse----------" + response.toString());
        }
        return response;
    }
}

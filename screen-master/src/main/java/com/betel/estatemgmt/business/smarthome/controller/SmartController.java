package com.betel.estatemgmt.business.smarthome.controller;

import com.betel.estatemgmt.business.smarthome.model.BuildingResp;
import com.betel.estatemgmt.business.smarthome.model.HouseInfo;
import com.betel.estatemgmt.business.smarthome.service.SmartHomeService;
import com.betel.estatemgmt.business.userapp.house.model.Member;
import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2018/4/4/004.
 */
@RestController
@RequestMapping("httpclientProperty/device")
public class SmartController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SmartController.class);

    @Autowired
    BuildingService buildingService;

    @Autowired
    SmartHomeService smartHomeService;

    /**
     * <p>
     * 查询所有楼宇
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 13:39
     *
     * @param
     * @return response
     */
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingResp>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findBuildingList start========");
        }
        Response<List<BuildingResp>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //查询所有楼宇
            List<BuildingResp> buildingInfoList = smartHomeService.findBuildings(estateId);
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========httpclientHome/device/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查所有单元
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 16:29
     *
     * @param building
     * @return response
     */
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findUnitList start========buildingId" + building.getBuildingId());
        }
        Response<List<UnitInfo>> response = new Response<>();
        try {
            //判断楼宇id是否为空
            if (null != building.getBuildingId()) {
                //根据楼宇id查楼宇 不为空则查所有单元
                Building currentBuilding = buildingService.selectByPrimaryKey(building.getBuildingId());
                if (currentBuilding != null) {
                    //查所有单元
                    List<UnitInfo> unitInfoList = buildingService.findUnitList(building.getBuildingId());
                    response.setData(unitInfoList);
                }else {//当前楼宇已被删除
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }
            }
        } catch (Exception e) {
            LOG.error("========httpclientHome/device/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findUnitList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询房屋信息
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 13:39
     *
     * @param
     * @return response
     */
    @RequestMapping(value = "v1/findHouseInfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<HouseInfo> findHouseInfo(@RequestBody House house) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findHouseInfo start========");
        }
        Response<HouseInfo> response = new Response<>();
        try {
            response.setData(smartHomeService.findByHouseId(house.getHouseId()));
        } catch (Exception e) {
            LOG.error("========httpclientHome/device/v1/findHouseInfo error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findHouseInfo end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询房屋户主成员
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 13:39
     *
     * @param
     * @return response
     */
    @RequestMapping(value = "v1/findHouseMember", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<Member>> findHouseMember(@RequestBody House house) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findHouseMember start========");
        }
        Response<List<Member>> response = new Response<>();
        try {
            response.setData(smartHomeService.findMembers(house.getHouseId()));
        } catch (Exception e) {
            LOG.error("========httpclientHome/device/v1/findHouseMember error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/findHouseMember end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 插入系统通知
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 13:39
     *
     * @param
     * @return response
     */
    @RequestMapping(value = "v1/insertNotice", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> findHouseMember(@RequestBody Notice notice) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/insertNotice start========");
        }
        Response<String> response = new Response<>();
        try {
            smartHomeService.insertNotice(notice);
        } catch (Exception e) {
            LOG.error("========httpclientHome/device/v1/insertNotice error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========httpclientHome/device/v1/insertNotice end========response" + response);
        }
        return response;
    }
}

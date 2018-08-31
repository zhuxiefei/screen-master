package com.betel.estatemgmt.business.propertyapp.lease.controller;

import com.betel.estatemgmt.business.propertyapp.lease.code.LeaseCode;
import com.betel.estatemgmt.business.propertyapp.lease.constant.LeaseConstant;
import com.betel.estatemgmt.business.propertyapp.lease.model.*;
import com.betel.estatemgmt.business.propertyapp.lease.service.LeaseService;
import com.betel.estatemgmt.business.userapp.house.model.AllHouse;
import com.betel.estatemgmt.business.userapp.house.model.Builds;
import com.betel.estatemgmt.business.userapp.house.model.UnitOne;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.lease.code.WebLeaseCode;
import com.betel.estatemgmt.business.web.lease.controller.WebLeaseController;
import com.betel.estatemgmt.business.web.lease.model.TimeConflict;
import com.betel.estatemgmt.business.web.lease.service.WebLeaseService;
import com.betel.estatemgmt.business.web.repair.model.FindHouseReq;
import com.betel.estatemgmt.business.web.repair.model.HouseInfo;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.common.Page;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 房屋租赁
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/7 17:28 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/lease")
public class LeaseController {

    private static final Logger LOG = LoggerFactory.getLogger(LeaseController.class);

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private WebLeaseService webLeaseService;

    @Autowired
    private RepairService repairService;

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
    public Response<AllHouseOffice> findAllHouse(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/house/v1/findAllHouse---------start");
        }
        Response<AllHouseOffice> response = new Response<>();
        //房屋分类模型
        AllHouseOffice allHouse = new AllHouseOffice();
        //所有的楼宇
        List<OfficeBuilds> builds = new ArrayList<>();
        List<Building> buildings;
        String estateId = "";
        try {
            estateId = AESUtil.decrypt(request.getHeader("estateId"));
            buildings = houseService.selecteAllBuilding(estateId);
        } catch (Exception e) {
            LOG.error("-----app/house/v1/findAllHouse---------error" + e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        allHouse.setVilla(houseService.selecteHouseVillas(estateId));
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------buildings-------" + buildings);
        }
        //每个楼宇里分类单元和房屋
        for (Building building : buildings) {
            OfficeBuilds build = new OfficeBuilds();
            build.setBuildingName(building.getBuildingName());
            List<OfficeUnitOne> unitOnes = new ArrayList<>();
            //楼宇下的单元
            List<BuildingUnit> buildingUnits;
            try {
                buildingUnits = houseService.selecteUnitByBuildId(building.getBuildingId());
            } catch (Exception e) {
                LOG.error("-----app/house/v1/findAllHouse---------error", e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------buildingUnits----------" + buildingUnits);
            }
            //设置楼宇下单元为空的房屋
            List<House> houses = houseService.selecteUnitByBuildIdUnitNull(building.getBuildingId(),estateId);
            build.setLittltvilla(houses);
            //每个单元里分类房屋
            for (BuildingUnit buildingUnit : buildingUnits) {
                OfficeUnitOne unitone = new OfficeUnitOne();
                unitone.setUnitName(buildingUnit.getUnitName());
                try {
                    // 设置房屋
                    unitone.setHouses(houseService.selecteHouseByUnitIdOffice(buildingUnit.getUnitId()+""));
                } catch (Exception e) {
                    LOG.error("-----app/house/v1/findAllHouse---------error", e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
                unitOnes.add(unitone);
            }
            build.setUnits(unitOnes);
            builds.add(build);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------builds----------" + builds);
        }
        allHouse.setBuilds(builds);
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------allHouse----------" + allHouse);
        }
        response.setData(allHouse);
        if (LOG.isInfoEnabled()) {
            LOG.info("-------app/house/v1/findAllHouse----------" + response.toString());
        }
        return response;
    }

    @RequestMapping(value = "v1/findAreaByHouseId", method = RequestMethod.GET)
    public Response findAreaByHouseId(String houseId){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findAreaByHouseId---------start houseId = " + houseId);
        }
        Response response = new Response();
        try {
            response.setData(leaseService.findAreaByHouseId(houseId));
        }catch (Exception e){
            LOG.error("userApp/lease/v1/findAreaByHouseId---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findAreaByHouseId---------end" +response);
        }
        return response;
    }


    /**
     * <p>
     * 租赁详情
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/8 15:05
     */
    @RequestMapping(value = "v1/findLeaseInfo", method = RequestMethod.GET)
    public Response findLeaseInfo(String rentId){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findLeaseInfo---------start houseId = " + rentId);
        }
        Response response = new Response();
        LeaseInfoResp leaseInfo;
        if(rentId != null){
            try {
                leaseInfo = leaseService.findLeaseInfo(rentId);
            }catch (Exception e){
                LOG.error("userApp/lease/v1/findLeaseInfo---------------error---",e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
            response.setData(leaseInfo);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findLeaseInfo---------end" +response);
        }
        return response;
    }



    /**
     * <p>
     * 租赁列表
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/8 13:45
     */
    @RequestMapping(value = "v1/findAllLease", method = RequestMethod.GET)
    public Response findAllLease(String houseNumber, String houseStatus, String endTime , Page page){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findAllLease---------start" );
        }
        Response response = new Response();
        try {
            FindAllLeaseReq req = new FindAllLeaseReq();
            Paging<FindAllLeaseResp> pager = new Paging<>(page.getCurPage(), page.getPageSize());
            List<FindAllLeaseResp> allLease = new ArrayList<>();
            if(StringUtils.isEmpty(houseNumber)){
                if(!StringUtils.isEmpty(houseStatus)){
                    req.setHouseStatus(Integer.parseInt(houseStatus));
                }
                if(!StringUtils.isEmpty(endTime)){
                    req.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
                }
                // 组合筛选
                    allLease = leaseService.findAllLease(pager, req);
            }else {
                // 房号搜索
                req.setHouseNumber(houseNumber);
                if(!req.getHouseNumber().matches(LeaseConstant.LEASE_FORMAT)){
                    response.setCode(LeaseCode.HOUSENUMBER_ERROR);
                    return response;
                }
                allLease = leaseService.findAllLease(pager, req);
            }
            pager.result(allLease);
            response.setData(pager);
        }catch (Exception e){
            LOG.error("userApp/lease/v1/findAllLease---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findAllLease---------end" + response);
        }
        return response;
    }


    /**
     * <p>
     * 获取房屋数量
     * </p>
     *
     * @return response
     * @Author: geyf <br/>
     * @date: 2018/5/8 11:39
     */
    @RequestMapping(value = "v1/findHouseNumber", method = RequestMethod.GET)
    public Response<HouseNumResp> findHouseNumber(){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findHouseNumber---------start");
        }
        Response response = new Response();
        try {
            HouseNumResp houseNumber = leaseService.findHouseNumber();
            response.setData(houseNumber);
        }catch (Exception e){
            LOG.error("userApp/lease/v1/findHouseNumber---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findHouseNumber---------end" + response);
        }
        return response;
    }



    /**
     * <p>
     * 新增租赁
     * </p>
     *
     * @param: req
     * @return Response
     * @Author: geyf <br/>
     * @date: 2018/5/8 11:09
     */
    @RequestMapping(value = "v1/saveLeaseInfo", method = RequestMethod.POST)
    public Response saveLeaseInfo(@RequestBody @Valid SaveLeaseReq req, BindingResult bindingResult){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/saveLeaseInfo---------start" + req);
        }
        Response response = new Response();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = " 23:59:59";

        // 校验基本参数
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }

        String resultCode = WebLeaseController.paramJudgeSpace(req);
        if(resultCode != null){
            response.setCode(resultCode);
            return response;
        }


        try {
            // 校验保留两位小数
            if(!(req.getRentUnitPrice()+"").matches(LeaseConstant.PRICE_FORMAT)){
                response.setCode(LeaseCode.RENTUNITPRICE_ERROR);
                return response;
            }
            if(!(req.getPropertyUnitPrice()+"").matches(LeaseConstant.PRICE_FORMAT)){
                response.setCode(LeaseCode.PROPERTYUNITPRICE_ERROR);
                return response;
            }

            if(sdf.parse(req.getEndTimeStr()).getTime() <= System.currentTimeMillis()){
                response.setCode(LeaseCode.ENDTIME_ERROR);
                return response;
            }
            req.setSignTime(sdf.parse(req.getSignTimeStr()));
            req.setStartTime(sdf.parse(req.getStartTimeStr()));
            req.setEndTime(sdfTime.parse(req.getEndTimeStr() + time));
        } catch (ParseException e) {
            LOG.error("userApp/lease/v1/saveLeaseInfo---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        // 计租时间必须大于等于合同签订时间
        if(req.getStartTime().getTime() < req.getSignTime().getTime()){
            response.setCode(LeaseCode.STARTTIME_ERROR);
            return response;
        }

        try {

            if(webLeaseService.houseIsDelete(req.getAddress())){
                // 房屋被删除
                response.setCode(WebLeaseCode.HOUSE_DELETE);
                return response;
            }

            TimeConflict timeConflict = new TimeConflict();
            timeConflict.setHouseId(req.getAddress());
            timeConflict.setStartTime(req.getStartTime());
            timeConflict.setEndTime(req.getEndTime());
            boolean flag = webLeaseService.isLeaseTimeConflict(timeConflict);
            if(flag){
                response.setCode(WebLeaseCode.TIME_CONFLICT);
                return response;
            }

            leaseService.saveRentInfo(req);
        }catch (Exception e){
            LOG.error("userApp/lease/v1/saveLeaseInfo---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/saveLeaseInfo---------end" + response);
        }
        return response;
    }

    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========userApp/lease/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            //查询所有楼宇
            List<BuildingInfo> buildingInfoList = buildingService.findBuildingList(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========userApp/lease/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========userApp/lease/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========userApp/lease/v1/findUnitList start========building" + building);
        }
        Response<List<UnitInfo>> response = new Response<>();
        try {
            if (null != building.getBuildingId()) {
                //根据楼宇id查楼宇 不为空则查所有单元
                Building currentBuilding = buildingService.selectByPrimaryKey(building.getBuildingId());
                if (currentBuilding == null) {
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }else {
                    //查所有单元
                    List<UnitInfo> unitInfoList = buildingService.findUnitList(building.getBuildingId());
                    response.setData(unitInfoList);
                }
            }
        } catch (Exception e) {
            LOG.error("========userApp/lease/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========userApp/lease/v1/findUnitList end========response" + response);
        }
        return response;
    }

    @RequestMapping(value = "v1/findHouses", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseInfo>> findHouses(@RequestBody FindHouseReq houseReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========userApp/lease/v1/findHouses start========houseReq" + houseReq);
        }
        Response<List<HouseInfo>> response = new Response<>();
        try {
            String buildingId = houseReq.getBuildingId();
            String unitId = houseReq.getUnitId();
            if (!StringUtil.isBlank(buildingId) && !"0".equals(buildingId)
                    && buildingService.selectByPrimaryKey(Long.parseLong(buildingId)) == null) {
                response.setCode(BuildingCode.BUILDING_DELETED);
                return response;
            }
            if (!StringUtil.isBlank(unitId) && !"0".equals(unitId)
                    && buildingService.selectBuildingUnitByUnitId(Long.parseLong(unitId)) == null) {
                response.setCode(BuildingCode.UNIT_DELETED);
                return response;
            }
            response.setData(repairService.findByIds(buildingId, unitId,AESUtil.decrypt(request.getHeader("estateId"))));
        } catch (Exception e) {
            LOG.error("========userApp/lease/v1/findHouses error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========userApp/lease/v1/findHouses end========response" + response);
        }
        return response;
    }

}
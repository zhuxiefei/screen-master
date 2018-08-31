package com.betel.estatemgmt.business.web.house.controller;

import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.house.code.HouseCode;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.house.model.HouseInfo;
import com.betel.estatemgmt.business.web.house.model.officeHouse.*;
import com.betel.estatemgmt.business.web.house.service.HouseService;
import com.betel.estatemgmt.business.web.house.service.OfficeHouseService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
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
 * Created by Administrator on 2018/5/8/008.
 */
@RestController
@RequestMapping("/web/officeHouse")
public class OfficeHouseController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeHouseController.class);

    @Autowired
    OfficeHouseService officeHouseService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    HouseService houseService;

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
    @RequiresPermissions(value = "officeHouseManage-findBuildingList")
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //查询所有楼宇
            response.setData(buildingService.findBuildingList(estateId));
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findBuildingList end========response" + response);
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
    @RequiresPermissions(value = "officeHouseManage-findUnitList")
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findUnitList start========buildingId" + building.getBuildingId());
        }
        Response<List<UnitInfo>> response = new Response<>();
        try {
            //判断楼宇id是否为空
            if (null != building.getBuildingId()) {
                //根据楼宇id查楼宇 不为空则查所有单元
                Building currentBuilding = buildingService.selectByPrimaryKey(building.getBuildingId());
                if (currentBuilding != null) {
                    //查所有单元
                    response.setData(buildingService.findUnitList(building.getBuildingId()));
                } else {
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }
            }
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findUnitList end========response" + response);
        }
        return response;
    }

    /**
     * 添加房屋
     *
     * @param officeReq
     * @param request
     * @return
     */
    @RequiresPermissions(value = "officeHouseManage-addOfficeHouse")
    @RequestMapping(value = "v1/addOfficeHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addOfficeHouse(@RequestBody AddOfficeReq officeReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/addOfficeHouse start========officeReq" + officeReq);
        }
        Response<String> response = new Response<>();
        try {
            officeReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            String code = officeHouseService.addOfficeHouse(officeReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/addOfficeHouse error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/addOfficeHouse end========response" + response);
        }
        return response;
    }

    /**
     * 查看房屋详情
     *
     * @param officeReq
     * @return
     */
    @RequiresPermissions(value = "officeHouseManage-findOfficeHouse")
    @RequestMapping(value = "v1/findOfficeHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<FindOfficeHouseResp> findOfficeHouse(@RequestBody FindOfficeHouseReq officeReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findOfficeHouse start========officeReq" + officeReq);
        }
        Response<FindOfficeHouseResp> response = new Response<>();
        try {
            //判断房屋是否存在
            if (null == officeHouseService.findByHouseId(officeReq.getHouseId())){
                response.setCode(HouseCode.HOUSE_IS_DELETE);
            }else {
                response.setData(officeHouseService.findOfficeHouse(officeReq.getHouseId()));
            }
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/findOfficeHouse error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findOfficeHouse end========response" + response);
        }
        return response;
    }

    /**
     * 编辑房屋
     *
     * @param officeReq
     * @return
     */
    @RequiresPermissions(value = "officeHouseManage-updateOfficeHouse")
    @RequestMapping(value = "v1/updateOfficeHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateOfficeHouse(@RequestBody UpdateOfficeReq officeReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/updateOfficeHouse start========officeReq" + officeReq);
        }
        Response<String> response = new Response<>();
        try {
            officeReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            String code = officeHouseService.updateOfficeHouse(officeReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/updateOfficeHouse error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/updateOfficeHouse end========response" + response);
        }
        return response;
    }

    /**
     * 查看房屋列表
     *
     * @param officeReq
     * @return
     */
    @RequiresPermissions(value = "officeHouseManage-findAllOffices")
    @RequestMapping(value = "v1/findAllOffices", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<FindAllOfficeResp>> findAllOffices(@RequestBody FindAllOfficesReq officeReq,
                                                              HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findAllOffices start========officeReq" + officeReq);
        }
        Response<Paging<FindAllOfficeResp>> response = new Response<>();
        try {
            officeReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            Paging<FindAllOfficeResp> pager = new Paging<>(officeReq.getCurPage(), officeReq.getPageSize());
            String code = validateOfficeReq(officeReq);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }else {
                pager.result(officeHouseService.findAllOffices(pager,officeReq));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/findAllOffices error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/findAllOffices end========response" + response);
        }
        return response;
    }

    /**
     * 批量删除房屋
     *
     * @param officeReq
     * @return
     */
    @RequiresPermissions(value = "officeHouseManage-deleteOffices")
    @RequestMapping(value = "v1/deleteOffices", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<FindAllOfficeResp>> deleteOffices(@RequestBody DeleteOfficesReq officeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/deleteOffices start========officeReq" + officeReq);
        }
        Response<Paging<FindAllOfficeResp>> response = new Response<>();
        try {
            officeHouseService.deleteOffices(officeReq.getHouseIds());
        } catch (Exception e) {
            LOG.error("========web/officeHouse/v1/deleteOffices error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/officeHouse/v1/deleteOffices end========response" + response);
        }
        return response;
    }

    /**
     * 校验分页入参
     *
     * @param officesReq
     * @return
     */
    private String validateOfficeReq(FindAllOfficesReq officesReq) {
        if (!StringUtil.isEmpty(officesReq.getHouseNum())) {
            officesReq.setHouseNum(officesReq.getHouseNum().trim());
        }
        if (!StringUtil.isBlank(officesReq.getHouseNum())) {
            if (!officesReq.getHouseNum().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
                return HouseCode.HOUSE_NUM_RULE;
            }
        }
        //判断楼宇单元是否存在
        if (officesReq.getBuildingId() != null
                && houseService.findByBuildingId(Long.parseLong(officesReq.getBuildingId())) == null) {
            return HouseCode.BUILDING_NOT_EXIST;
        } else if (officesReq.getUnitId() != null
                && houseService.findByUnitId(Long.parseLong(officesReq.getUnitId())) == null) {
            return HouseCode.UNIT_IS_NULL;
        } else {
            if (!StringUtil.isBlank(officesReq.getHouseNum()) && officesReq.getHouseNum().contains("_")) {
                officesReq.setHouseNum(officesReq.getHouseNum().replace("_", "\\_"));
            }
        }
        return null;
    }
}

package com.betel.estatemgmt.business.web.housestatus.controller;

import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.housestatus.code.HouseStatusCode;
import com.betel.estatemgmt.business.web.housestatus.constant.HouseStatusConstant;
import com.betel.estatemgmt.business.web.housestatus.model.*;
import com.betel.estatemgmt.business.web.housestatus.service.HouseStatusService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.utils.StringUtil;
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
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusController <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/13 17:29 <br/>
 * @version: 1.0
 */
@RestController
@RequestMapping("web/houseStatus")
public class HouseStatusController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(HouseStatusController.class);
    @Autowired
    private HouseStatusService houseStatusService;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private BuildingUnitMapper buildingUnitMapper;
    @Autowired
    private HouseMapper houseMapper;

    /**
     * 查询房屋状态列表
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-findHouseStatus")
    @RequestMapping(value = "v1/findHouseStatus", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<BuildingInfo> findHouseStatus(@RequestBody HouseStatusReq houseStatusReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseStatus-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<BuildingInfo> response = new Response<BuildingInfo>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(houseStatusReq.getIsOthers())){
                if (StringUtil.isBlank(houseStatusReq.getBuildingId())) {
                    //楼宇id判读是否为空
                    response.setCode(BuildingCode.BUILDING_ID_NULL);
                } else if (!HouseStatusConstant.MINUS_ONE.equals(houseStatusReq.getBuildingId())&& null == buildingMapper.selectByPrimaryKey(Long.valueOf(houseStatusReq.getBuildingId().trim()))) {
                    //楼宇是否存在
                    response.setCode(BuildingCode.BUILDING_DELETED);
                } else if (!StringUtil.isBlank(houseStatusReq.getUnitId())) {
                    //单元id存在的时候，判断单元是否存在
                    BuildingUnit buildingUnit = buildingUnitMapper.selectByPrimaryKey(Long.valueOf((houseStatusReq.getUnitId().trim())));
                    if (null == buildingUnit) {
                        response.setCode(BuildingCode.UNIT_DELETED);
                    } else {
                        BuildingInfo buildingInfo = houseStatusService.findHouseStatus(houseStatusReq,estateId);
                        response.setData(buildingInfo);
                    }
                } else {
                    BuildingInfo buildingInfo = houseStatusService.findHouseStatus(houseStatusReq,estateId);
                    response.setData(buildingInfo);
                }
            }else {
                BuildingInfo buildingInfo = houseStatusService.findHouseStatus(houseStatusReq,estateId);
                response.setData(buildingInfo);
            }
        } catch (Exception e) {
            LOG.error("--------findHouseStatus--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseStatus--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询房屋状态详情
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-findHouseDetail")
    @RequestMapping(value = "v1/findHouseDetail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<HouseStatusInfo> findHouseDetail(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseDetail-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<HouseStatusInfo> response = new Response<HouseStatusInfo>();
        try {
            if (StringUtil.isBlank(houseStatusReq.getHouseId())) {
                response.setCode(HouseStatusCode.HOUSE_ID_IS_NULL);
            } else {
                HouseStatusInfo houseStatusInfo = houseStatusService.findHouseDetail(houseStatusReq);
                if (null == houseStatusInfo) {
                    response.setCode(HouseStatusCode.HOUSE_IS_NOT_EXIST);
                } else if (null == houseMapper.selectByPrimaryKey(houseStatusReq.getHouseId().trim())) {
                    response.setCode(HouseStatusCode.HOUSE_IS_NOT_EXIST);
                } else {
                    response.setData(houseStatusInfo);
                }

            }
        } catch (Exception e) {
            LOG.error("--------findHouseDetail--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseDetail--------end,response=" + response);
        }
        return response;
    }

    /**
     * 修改房屋状态详情
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-updateHouseStatus")
    @RequestMapping(value = "v1/updateHouseStatus", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseStatusTenant>> updateHouseStatus(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/updateHouseStatus-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<List<HouseStatusTenant>> response = new Response<List<HouseStatusTenant>>();
        try {
            if (StringUtil.isBlank(houseStatusReq.getHouseId())) {
                response.setCode(HouseStatusCode.HOUSE_ID_IS_NULL);
            } else {
                response.setCode(houseStatusService.updateHouseStatus(houseStatusReq));
            }
        } catch (Exception e) {
            LOG.error("--------updateHouseStatus--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/updateHouseStatus--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询房屋户主信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-findHouseStatusOwner")
    @RequestMapping(value = "v1/findHouseStatusOwner", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseStatusOwner>> findHouseStatusOwner(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseDetail-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<List<HouseStatusOwner>> response = new Response<List<HouseStatusOwner>>();
        try {
            if (StringUtil.isBlank(houseStatusReq.getHouseId())) {
                response.setCode(HouseStatusCode.HOUSE_ID_IS_NULL);
            } else {
                List<HouseStatusOwner> houseOwnerList = houseStatusService.findHouseStatusOwner(houseStatusReq.getHouseId().trim());
                response.setData(houseOwnerList);
            }
        } catch (Exception e) {
            LOG.error("--------findHouseDetail--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseDetail--------end,response=" + response);
        }
        return response;
    }

    /**
     * 添加房屋户主信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-addHouseStatusOwner")
    @RequestMapping(value = "v1/addHouseStatusOwner", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addHouseStatusOwner(@RequestBody HouseStatusReq houseStatusReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/addHouseStatusOwner-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setCode(houseStatusService.addHouseStatusOwner(houseStatusReq,request));
        } catch (Exception e) {
            LOG.error("--------addHouseStatusOwner--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/addHouseStatusOwner--------end,response=" + response);
        }
        return response;
    }

    /**
     * 添加房屋成员信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-addHouseStatusMember")
    @RequestMapping(value = "v1/addHouseStatusMember", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addHouseMember(@RequestBody HouseStatusReq houseStatusReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/addHouseStatusMember-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setCode(houseStatusService.addHouseStatusMember(houseStatusReq,request));
        } catch (Exception e) {
            LOG.error("--------addHouseStatusMember--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/addHouseStatusMember--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询房屋成员信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-findHouseMember")
    @RequestMapping(value = "v1/findHouseMember", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseStatusMember>> findHouseMember(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseMember-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<List<HouseStatusMember>> response = new Response<List<HouseStatusMember>>();
        try {
            if (StringUtil.isBlank(houseStatusReq.getHouseId())) {
                response.setCode(HouseStatusCode.HOUSE_ID_IS_NULL);
            } else {
                List<HouseStatusMember> houseStatusMembers = houseStatusService.findHouseStatusMember(houseStatusReq.getHouseId().trim());
                response.setData(houseStatusMembers);
            }
        } catch (Exception e) {
            LOG.error("--------findHouseMember--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseMember--------end,response=" + response);
        }
        return response;
    }

    /**
     * 添加房屋租户信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-addHouseTenant")
    @RequestMapping(value = "v1/addHouseTenant", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addHouseTenant(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/addHouseTenant-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setCode(houseStatusService.addHouseStatusTenant(houseStatusReq));
        } catch (Exception e) {
            LOG.error("--------addHouseTenant--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/addHouseTenant--------end,response=" + response);
        }
        return response;
    }

    /**
     * 删除房屋租户信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-deleteHouseStatusTenant")
    @RequestMapping(value = "v1/deleteHouseStatusTenant", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteHouseStatusTenant(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/deleteHouseStatusTenant-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setData(houseStatusService.deleteHouseStatusTenant(houseStatusReq));
        } catch (Exception e) {
            LOG.error("--------deleteHouseStatusTenant--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/deleteHouseStatusTenant--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询房屋租户信息
     *
     * @param houseStatusReq
     * @return
     */
@RequiresPermissions(value = "houseStatus-findHouseTenant")
    @RequestMapping(value = "v1/findHouseTenant", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseStatusTenant>> findHouseTenant(@RequestBody HouseStatusReq houseStatusReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseTenant-------start,houseStatusReq=" + houseStatusReq);
        }
        Response<List<HouseStatusTenant>> response = new Response<List<HouseStatusTenant>>();
        try {
            if (StringUtil.isBlank(houseStatusReq.getHouseId())) {
                response.setCode(HouseStatusCode.HOUSE_ID_IS_NULL);
            } else {
                response.setData(houseStatusService.findHouseStatusTenant(houseStatusReq));
            }
        } catch (Exception e) {
            LOG.error("--------findHouseTenant--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/houseStatus/v1/findHouseTenant--------end,response=" + response);
        }
        return response;
    }
}

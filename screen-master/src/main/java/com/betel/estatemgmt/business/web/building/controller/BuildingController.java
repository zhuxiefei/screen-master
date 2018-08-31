package com.betel.estatemgmt.business.web.building.controller;

import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.constant.BuildingDataValidation;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.Unit;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 楼宇信息维护业务
 * </p>
 * ClassName: AdminController <br/>
 * Author: Zhang Li  <br/>
 * Date: 2017/5/17 15:28 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/building")
public class BuildingController extends BaseController {

    @Autowired
    private BuildingService buildingService;

    private static final Logger LOG = LoggerFactory.getLogger(BuildingController.class);

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
@RequiresPermissions(value = "buildingManage-findBuildingList")
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //查询所有楼宇
            List<BuildingInfo> buildingInfoList = buildingService.findBuildingList(estateId);
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========web/building/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除楼宇
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 16:29
     *
     * @param building
     * @return response
     */
@RequiresPermissions(value = "buildingManage-deleteBuilding")
    @RequestMapping(value = "v1/deleteBuilding", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> deleteBuilding(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/building/v1/deleteBuilding start========buildingId=" + building.getBuildingId());
        }
        Response<Object> response = new Response<>();
        try {
            if (null != building.getBuildingId()) {
                //查房屋表
                List<House> houseList = buildingService.selectByBuildingId(building.getBuildingId());
                //是否有房屋 若没有删除
                if (houseList.size() == 0) {
                    buildingService.deleteBuilding(building.getBuildingId());
                } else {                         //当前楼宇下存在房屋
                    response.setCode(BuildingCode.BUILDING_HOUSE_EXIST);
                }
            } else {
                response.setCode(BuildingCode.BUILDING_ID_NULL);
            }
        } catch (Exception e) {
            LOG.error("========web/building/v1/deleteBuilding error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/deleteBuilding end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改楼宇
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 17:29
     *
     * @param buildingInfo
     * @return response
     */
@RequiresPermissions(value = "buildingManage-updateBuilding")
    @RequestMapping(value = "v1/updateBuilding", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> updateBuilding(@RequestBody BuildingInfo buildingInfo,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/updateBuilding start========buildingInfo" + buildingInfo);
        }
        Response<Object> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //获取code
            String code = checkUpdateInfo(buildingInfo,estateId);
            if (!StringUtil.isEmpty(code)) {
                response.setCode(code);
            } else {//根据主键查询楼宇 不为空更新
                Building currentBuilding = buildingService.selectByPrimaryKey(buildingInfo.getBuildingId());
                if (currentBuilding != null) {
                    //code为空 更新数据库信息
                    Building building = new Building();
                    building.setBuildingId(buildingInfo.getBuildingId());
                    building.setBuildingName(buildingInfo.getBuildingName());
                    building.setBuildingDesc(buildingInfo.getBuildingDesc());
                    if (!StringUtil.isBlank(buildingInfo.getDisplayOrder())){
                        building.setDisplayOrder(Integer.parseInt(buildingInfo.getDisplayOrder()));
                    }
                    buildingService.updateBuilding(building);
                } else {//当前楼宇已被删除
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }
            }
        } catch (Exception e) {
            LOG.error("web/building/v1/updateBuilding error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/updateBuilding end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验修改楼宇信息
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 10:52
     *
     * @param buildingInfo
     * @return code
     */
    private String checkUpdateInfo(BuildingInfo buildingInfo,String estateId) {
        String code = "";

        if (null == buildingInfo.getBuildingId()) {
            //楼宇Id为空
            code = BuildingCode.BUILDING_ID_NULL;
        } else if (StringUtil.isEmpty(buildingInfo.getBuildingName())) {
            //楼宇名称为空
            code = BuildingCode.BUILDING_NAME_NULL;
        } else if (!buildingInfo.getBuildingName().trim().matches(BuildingDataValidation.BUILDING_NAME_FORMAT)) {
            //楼宇名称格式错误
            code = BuildingCode.BUILDING_FORMAT_ERROR;
        } else if (buildingInfo.getBuildingDesc().length() > BuildingDataValidation.BUILDING_DESC_SIZE) {
            //楼宇描述格式错误
            code = BuildingCode.BUILDINGDESC_FORMAT_ERROR;
        } else if (!StringUtil.isBlank(buildingInfo.getDisplayOrder())
                && !buildingInfo.getDisplayOrder().matches(BuildingDataValidation.DISPLAY_RULE)){
            //排序字段错误
            code = BuildingCode.DISPLAY_WRONG;
        } else {
            code = checkBuilding(buildingInfo, code,estateId);
        }
        return code;
    }

    /**
     * <p>
     * 新增、修改楼宇，楼宇名称是否存在校验
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/22 13:29
     *
     * @param buildingInfo code
     * @return response
     */
    String checkBuilding(BuildingInfo buildingInfo, String code,String estateId) {
        try {
            Building building = buildingService.selectByBuildingName(buildingInfo.getBuildingName(),estateId);
            if (null != building) {
                if (!building.getBuildingId().equals(buildingInfo.getBuildingId())) {
                    //楼宇名称已存在
                    code = BuildingCode.BUILDING_NAME_EXIST;
                }
            }

        } catch (Exception e) {
            code = StatusCode.FAILURE;
        }
        return code;
    }

    /**
     * <p>
     * 校验新增楼宇信息
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 10:52
     *
     * @param buildingInfo
     * @return code
     */
    private String checkAddInfo(BuildingInfo buildingInfo,String estateId) {
        String code = "";
        if (StringUtil.isEmpty(buildingInfo.getBuildingName())) {
            //楼宇名称为空
            code = BuildingCode.BUILDING_NAME_NULL;
        } else if (!buildingInfo.getBuildingName().trim().matches(BuildingDataValidation.BUILDING_NAME_FORMAT)) {
            //楼宇名称格式错误
            code = BuildingCode.BUILDING_FORMAT_ERROR;
        } else if (!StringUtil.isEmpty(buildingInfo.getBuildingDesc()) && buildingInfo.getBuildingDesc().length() > BuildingDataValidation.BUILDING_DESC_SIZE) {
            //楼宇描述格式错误
            code = BuildingCode.BUILDINGDESC_FORMAT_ERROR;
        } else if (!StringUtil.isBlank(buildingInfo.getDisplayOrder())
                && !buildingInfo.getDisplayOrder().matches(BuildingDataValidation.DISPLAY_RULE)){
            //排序字段错误
            code = BuildingCode.DISPLAY_WRONG;
        } else {
            code = checkBuilding(buildingInfo, code,estateId);
        }
        return code;
    }

    /**
     * <p>
     * 新增楼宇信息
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 13:52
     *
     * @param buildingInfo
     * @return response
     */
@RequiresPermissions(value = "buildingManage-addBuilding")
    @RequestMapping(value = "v1/addBuilding", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> addBuilding(@RequestBody BuildingInfo buildingInfo,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/addBuilding start========buildingInfo" + buildingInfo);
        }
        Response<Object> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            String code = checkAddInfo(buildingInfo,estateId);
            if (!StringUtil.isEmpty(code)) {
                response.setCode(code);
            } else {
                //将数据添加到数据库
                Building building = new Building();
                building.setBuildingName(buildingInfo.getBuildingName());
                building.setBuildingDesc(buildingInfo.getBuildingDesc());
                building.setCreateTime(new Date(System.currentTimeMillis()));
                building.setEstateId(estateId);
                if (!StringUtil.isBlank(buildingInfo.getDisplayOrder())){
                    building.setDisplayOrder(Integer.parseInt(buildingInfo.getDisplayOrder()));
                }
                buildingService.addBuilding(building);
            }
        } catch (Exception e) {
            LOG.error("========web/building/v1/addBuilding error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/addBuilding end========response" + response);
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
@RequiresPermissions(value = "buildingManage-findUnitList")
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/findUnitList start========buildingId" + building.getBuildingId());
        }
        Response<List<UnitInfo>> response = new Response<>();
        try {                   //判断楼宇id是否为空
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
            LOG.error("========web/building/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/findUnitList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除单元
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/20 16:29
     *
     * @param buildingUnit
     * @return response
     */
@RequiresPermissions(value = "buildingManage-deleteUnit")
    @RequestMapping(value = "v1/deleteUnit", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> deleteUnit(@RequestBody BuildingUnit buildingUnit) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/deleteUnit start======== buildingUnit" + buildingUnit.getUnitId());
        }
        Response<Object> response = new Response<>();
        try {
            if (null == buildingUnit.getUnitId()) {
                response.setCode(BuildingCode.UNIT_ID_NULL);
            } else {
                //根据楼宇id查楼宇 不为空则删除单元
                Building currentBuilding = buildingService.selectByPrimaryKey(buildingUnit.getBuildingId());
                if (currentBuilding != null) {
                    //查房屋表
                    List<House> houseList = buildingService.selectByUnitId(buildingUnit.getUnitId());
                    //若无房屋则可删除单元------xyx
                    if (houseList.size() == 0) {
                        buildingService.deleteUnit(buildingUnit.getUnitId());
                    }else {
                        response.setCode(BuildingCode.UNIT_HOUSE_EXIST);
                    }
                } else {//当前楼宇已被删除
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }
            }
        } catch (Exception e) {
            LOG.error("========web/building/v1/deleteUnit error!======== ", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/deleteUnit end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验修改单元信息
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 10:52
     *
     * @param unit
     * @return code
     */
    private String checkUpdateUnit(Unit unit) {
        String code = "";
        //单元编号为空
        if (null == unit.getUnitId()) {
            code = BuildingCode.UNIT_ID_NULL;
        } else if (StringUtil.isEmpty(unit.getUnitName())) {
            //单元名称为空
            code = BuildingCode.UNIT_NAME_NULL;
        } else if (!unit.getUnitName().trim().matches(BuildingDataValidation.UNIT_NAME_FORMAT)) {
            //单元名称格式错误
            code = BuildingCode.UNIT_FORMAT_ERROR;
        } else if (!StringUtil.isBlank(unit.getDisplayOrder())
                && !unit.getDisplayOrder().matches(BuildingDataValidation.DISPLAY_RULE)){
            //排序字段错误
            code = BuildingCode.DISPLAY_WRONG;
        } else {
            try {                      //根据单元id查楼宇id
                BuildingUnit bunit = buildingService.selectBuildingIdByUnitId(unit.getUnitId());
                if (bunit != null) {
                    List<BuildingUnit> buildingUnit = buildingService.selectByUnitNameAndBuildingId(unit.getUnitName(), bunit.getBuildingId());
                    if (null != buildingUnit && buildingUnit.size() != 0) {
                        if (!buildingUnit.get(0).getUnitId().equals(unit.getUnitId())) {
                            //当前楼宇下单元名称已存在
                            code = BuildingCode.UNIT_NAME_EXIST;
                        }
                    }
                }
            } catch (Exception e) {
                code = StatusCode.FAILURE;
            }
        }
        return code;
    }

    /**
     * <p>
     * 修改单元
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 11:29
     *
     * @param unit
     * @return response
     */
@RequiresPermissions(value = "buildingManage-updateUnit")
    @RequestMapping(value = "v1/updateUnit", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> updateUnit(@RequestBody Unit unit) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/updateUnit start========unit" + unit);
        }
        Response<Object> response = new Response<>();
        try {
            //获取code
            String code = checkUpdateUnit(unit);
            if (!StringUtil.isEmpty(code)) {
                response.setCode(code);
            } else {
                //根据楼宇id查楼宇 不为空则修改单元
                Building building = buildingService.selectBuildingByBuildingId(unit.getBuildingId());
                if (building != null) {
                    //根据unitId查当前楼宇下单元 不为空更新
                    BuildingUnit currentUnit = buildingService.selectBuildingUnitByUnitId(unit.getUnitId());
                    if (currentUnit != null) {
                        //code为空 更新数据库信息
                        BuildingUnit buildingUnit = new BuildingUnit();
                        buildingUnit.setBuildingId(unit.getBuildingId());
                        buildingUnit.setUnitName(unit.getUnitName());
                        buildingUnit.setUnitId(unit.getUnitId());
                        if (!StringUtil.isBlank(unit.getDisplayOrder())){
                            buildingUnit.setDisplayOrder(Integer.parseInt(unit.getDisplayOrder()));
                        }
                        buildingService.updateUnit(buildingUnit);
                    } else {//当前楼宇下单元已被删除
                        response.setCode(BuildingCode.UNIT_DELETED);
                    }
                } else {//当前楼宇已被删除
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }
            }
        } catch (Exception e) {
            LOG.error("========web/building/v1/updateUnit error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/updateUnit end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验新增单元信息
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 15:29
     *
     * @param unit
     * @return code
     */
    private String checkAddUnit(Unit unit) {
        String code = "";
        if (null == unit.getBuildingId()) {
            //楼宇id为空
            code = BuildingCode.BUILDING_ID_NULL;
        } else if (StringUtil.isEmpty(unit.getUnitName())) {
            //单元名称为空
            code = BuildingCode.UNIT_NAME_NULL;
        } else if (!unit.getUnitName().trim().matches(BuildingDataValidation.UNIT_NAME_FORMAT)) {
            //单元名称格式错误
            code = BuildingCode.UNIT_FORMAT_ERROR;
        } else if (!StringUtil.isBlank(unit.getDisplayOrder())
                && !unit.getDisplayOrder().matches(BuildingDataValidation.DISPLAY_RULE)){
            //排序字段错误
            code = BuildingCode.DISPLAY_WRONG;
        } else {
            try {
                List<BuildingUnit> buildingUnit = buildingService.selectByUnitNameAndBuildingId(unit.getUnitName(), unit.getBuildingId());
                if (buildingUnit != null && buildingUnit.size() != 0) {
                    //当前楼宇下单元名称已存在
                    code = BuildingCode.UNIT_NAME_EXIST;
                }
            } catch (Exception e) {
                code = StatusCode.FAILURE;
            }
        }
        return code;
    }

    /**
     * <p>
     * 新增单元
     * </p>
     * Author: Zhang Li <br/>
     * Date: 2017/6/21 15:29
     *
     * @param unit
     * @return response
     */
@RequiresPermissions(value = "buildingManage-addUnit")
    @RequestMapping(value = "v1/addUnit", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> addUnit(@RequestBody Unit unit) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/addUnit start========unit" + unit);
        }
        Response<Object> response = new Response<>();
        try {
            String code = checkAddUnit(unit);
            if (!StringUtil.isEmpty(code)) {
                response.setCode(code);
            } else {
                //根据buildingid查楼宇 不为空则添加单元
                Building currentBuilding = buildingService.selectByPrimaryKey(unit.getBuildingId());
                if (currentBuilding != null) {
                    //将数据添加到数据库
                    BuildingUnit buildingUnit = new BuildingUnit();
                    buildingUnit.setBuildingId(unit.getBuildingId());
                    buildingUnit.setUnitName(unit.getUnitName());
                    buildingUnit.setCreateTime(new Date(System.currentTimeMillis()));
                    if (!StringUtil.isBlank(unit.getDisplayOrder())){
                        buildingUnit.setDisplayOrder(Integer.parseInt(unit.getDisplayOrder()));
                    }
                    buildingService.addUnit(buildingUnit);
                } else {//当前楼宇已被删除
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }
            }
        } catch (Exception e) {
            LOG.error("========web/building/v1/addUnit error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/building/v1/addUnit end========response" + response);
        }
        return response;
    }
}

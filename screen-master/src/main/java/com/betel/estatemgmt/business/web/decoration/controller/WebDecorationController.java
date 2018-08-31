package com.betel.estatemgmt.business.web.decoration.controller;

import com.betel.estatemgmt.business.propertyapp.decoration.model.DoDecorationReq;
import com.betel.estatemgmt.business.propertyapp.decoration.model.QueryDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.code.DecorationCode;
import com.betel.estatemgmt.business.userapp.decoration.constant.DecorationConstant;
import com.betel.estatemgmt.business.userapp.decoration.model.AddDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.service.DecorationService;
import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.repair.model.FindHouseReq;
import com.betel.estatemgmt.business.web.repair.model.HouseInfo;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 管理后台-装修申请管理
 * </p>
 * ClassName: WebDecorationController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/25 11:26 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/decoration")
public class WebDecorationController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(WebDecorationController.class);

    @Autowired
    DecorationService decorationService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    RepairService repairService;

    /**
     * <p>
     * 物业后台装修申请列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/23 11:54
     * @return
     */
    @RequiresPermissions(value = "decoration-decorationList")
    @RequestMapping(value = "v1/decorationList",method = RequestMethod.POST)
    public Response decorationList(@RequestBody QueryDecorationReq queryReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/decoration/v1/decorationList--------------start=====" );
        }
        Response response = new Response();
        Paging<DecoApplyRecord> pager = new Paging<DecoApplyRecord>(queryReq.getCurPage(), queryReq.getPageSize());
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            queryReq.setEstateId(estateId);
            if(!StringUtil.isBlank(queryReq.getStartTime())){
                queryReq.setStartTime(queryReq.getStartTime()+" 00:00:00");
            }
            if(!StringUtil.isBlank(queryReq.getEndTime())){
                queryReq.setEndTime(queryReq.getEndTime()+" 23:59:59");
            }
            if(!StringUtil.isBlank(queryReq.getBuildingId()) && buildingService.selectByPrimaryKey(Long.parseLong(queryReq.getBuildingId())) == null){
                response.setCode(DecorationCode.BUILDING_IS_DELETE);
            }else if (!StringUtil.isBlank(queryReq.getUnitId()) && buildingService.selectBuildingUnitByUnitId(Long.parseLong(queryReq.getUnitId())) == null) {
                response.setCode(DecorationCode.UNIT_IS_DELETE);
            }else{
                List<DecoApplyRecord> list = decorationService.findAllDecorationList(queryReq,pager);
                pager.result(list);
                response.setData(pager);
            }
        }catch (Exception e){
            LOG.error("---web/decoration/v1/decorationList--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---web/decoration/v1/decorationList--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 装修申请详情
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 9:24
     * @param
     * @return
     */
    @RequiresPermissions(value = "decoration-decorationInfo")
    @RequestMapping(value = "v1/decorationInfo",method = RequestMethod.POST)
    public Response decorationInfo(@RequestBody AddDecorationReq addDecorationReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/decoration/v1/decorationInfo--------------start=====" );
        }
        Response response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            DecoApplyRecord decorationInfo = decorationService.findDecorationInfo(addDecorationReq.getRecordId(),estateId);
            response.setData(decorationInfo);
        }catch (Exception e){
            LOG.error("---web/decoration/v1/decorationInfo--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---web/decoration/v1/decorationInfo--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 物业人员处理装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 15:08
     * @return
     */
    @RequiresPermissions(value = "decoration-doDecoration")
    @RequestMapping(value = "v1/doDecoration",method = RequestMethod.POST)
    public Response doDecoration(@RequestBody @Valid DoDecorationReq doDecorationReq,
                                 BindingResult bindingResult,
                                 @RequestHeader("userId") String userId,
                                 HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/decoration/v1/doDecoration--------------start=======");
        }
        Response response = new Response();
        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        try {
            //判断申请单的状态是否为未处理
            DecoApplyRecord decoApplyRecord = decorationService.findDecoration(doDecorationReq.getRecordId());
            if(null != decoApplyRecord){
                //已处理的、已取消的不能再处理
                if(!DecorationConstant.DECORATION_STATUS_SOLVING.equals(decoApplyRecord.getStatus())){
                    if(DecorationConstant.DECORATION_STATUS_REFUSE.equals(doDecorationReq.getStatus())||
                            DecorationConstant.DECORATION_STATUS_AGREE.equals(doDecorationReq.getStatus())){
                        response.setCode(DecorationCode.STATUS_IS_CHANGED);
                    }else {
                        decorationService.doDecoration(doDecorationReq,userId,"web",request);
                    }
                }else {
                    decorationService.doDecoration(doDecorationReq,userId,"web",request);
                }
            }
        }catch (Exception e){
            LOG.error("---web/decoration/v1/doDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---web/decoration/v1/doDecoration--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 物业人员添加装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 15:08
     * @param addDecorationReq
     * @param bindingResult
     * @return
     */
    @RequiresPermissions(value = "decoration-addDecoration")
    @RequestMapping(value = "v1/addDecoration",method = RequestMethod.POST)
    public Response addDecoration(@RequestBody @Valid AddDecorationReq addDecorationReq, BindingResult bindingResult,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/decoration/v1/addDecoration--------------start====addDecorationReq===" + addDecorationReq);
        }
        Response response = new Response();
        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        try {
            //判断房屋是否被删除
            boolean isDeleted = decorationService.houseIsDeleted(addDecorationReq.getHouseId(),AESUtil.decrypt(request.getHeader("estateId")));
            if(isDeleted){
                response.setCode(DecorationCode.HOUSE_IS_DELETED);
            }else {
                addDecorationReq.setStartTime(addDecorationReq.getStartTime()+" 00:00:00");
                decorationService.addDecoration(addDecorationReq);
            }
        }catch (Exception e){
            LOG.error("---web/decoration/v1/addDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---web/decoration/v1/addDecoration--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 打印装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 15:08
     * @param addDecorationReq
     * @return
     */
    @RequiresPermissions(value = "decoration-printDecoration")
    @RequestMapping(value = "v1/printDecoration",method = RequestMethod.POST)
    public Response printDecoration(@RequestBody AddDecorationReq addDecorationReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/decoration/v1/printDecoration--------------start=======");
        }
        Response response = new Response();
        try {
            DecoApplyRecord decoApplyRecord = new DecoApplyRecord();
            decoApplyRecord.setRecordId(addDecorationReq.getRecordId());
            decoApplyRecord.setPrintStatus(DecorationConstant.PRINTED_STATUS);
            decorationService.updateDecoration(decoApplyRecord);
        }catch (Exception e){
            LOG.error("---web/decoration/v1/printDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---web/decoration/v1/printDecoration--------------end"+response);
        }
        return response;
    }

    @RequiresPermissions(value = "decoration-findBuildingList")
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/decoration/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            //查询所有楼宇
            List<BuildingInfo> buildingInfoList = buildingService.findBuildingList(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========web/decoration/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/decoration/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    @RequiresPermissions(value = "decoration-findUnitList")
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/decoration/v1/findUnitList start========building" + building);
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
            LOG.error("========web/decoration/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/decoration/v1/findUnitList end========response" + response);
        }
        return response;
    }

    @RequiresPermissions(value = "decoration-findHouses")
    @RequestMapping(value = "v1/findHouses", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseInfo>> findHouses(@RequestBody FindHouseReq houseReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/decoration/v1/findHouses start========houseReq" + houseReq);
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
            LOG.error("========web/decoration/v1/findHouses error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/decoration/v1/findHouses end========response" + response);
        }
        return response;
    }
}

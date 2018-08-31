package com.betel.estatemgmt.business.web.lease.controller;

import com.betel.estatemgmt.business.propertyapp.lease.code.LeaseCode;
import com.betel.estatemgmt.business.propertyapp.lease.constant.LeaseConstant;
import com.betel.estatemgmt.business.propertyapp.lease.model.SaveLeaseReq;
import com.betel.estatemgmt.business.propertyapp.lease.service.LeaseService;
import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.lease.code.WebLeaseCode;
import com.betel.estatemgmt.business.web.lease.constant.WebLeaseConstant;
import com.betel.estatemgmt.business.web.lease.model.*;
import com.betel.estatemgmt.business.web.lease.service.WebLeaseService;
import com.betel.estatemgmt.business.web.repair.model.FindHouseReq;
import com.betel.estatemgmt.business.web.repair.model.HouseInfo;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.lease.Rent;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:51 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/lease")
public class WebLeaseController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(WebLeaseController.class);

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private WebLeaseService webLeaseService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private RepairService repairService;


    /**
     * <p>
     * 获取房屋的面积
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/14 10:33
     */
    @RequiresPermissions(value = "lease-findAreaByHouseId")
    @RequestMapping(value = "v1/findAreaByHouseId", method = RequestMethod.POST)
    public Response findAreaByHouseId(@RequestBody OfficeHouse officeHouse){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----userApp/lease/v1/findAreaByHouseId---------start houseId = " + officeHouse);
        }
        Response response = new Response();
        try {
            response.setData(leaseService.findAreaByHouseId(officeHouse.getHouseId()));
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



    @RequiresPermissions(value = "lease-modifyLease")
    @RequestMapping(value = "v1/modifyLease", method = RequestMethod.POST)
    public Response modifyLease(@RequestBody @Valid SaveLeaseReq req, BindingResult bindingResult){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/modifyLease---------start" + req);
        }
        Response response = new Response();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 校验基本参数
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }

        String resultCode = paramJudgeSpace(req);
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
            req.setEndTime(sdf.parse(req.getEndTimeStr()));
        } catch (ParseException e) {
            LOG.error("web/lease/v1/saveLeaseInfo---------------error---",e);
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
            timeConflict.setRentId(req.getRentId());
            boolean flag = webLeaseService.isLeaseTimeConflictModify(timeConflict);
            if(flag){
                response.setCode(WebLeaseCode.TIME_CONFLICT);
                return response;
            }
            webLeaseService.modifyLease(req);
        }catch (Exception e){
            LOG.error("web/lease/v1/modifyLease---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/modifyLease---------end", response);
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
     * @date: 2018/5/8 18:53
     */
    @RequiresPermissions(value = "lease-findLeaseInfo")
    @RequestMapping(value = "v1/findLeaseInfo", method = RequestMethod.POST)
    public Response findLeaseInfo(@RequestBody Rent rent){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/findLeaseInfo---------start" + rent);
        }
        Response response = new Response();
        try {
            WebLeaseInfo webLeaseInfo = webLeaseService.findWebLeaseInfo(rent.getRentId());
            response.setData(webLeaseInfo);
        }catch (Exception e){
            LOG.error("web/lease/v1/findLeaseInfo---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/findLeaseInfo---------end", response);
        }
        return response;
    }



    /**
     * <p>
     * 获取下载地址
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/8 18:33
     */
//    @RequiresPermissions(value = "lease-downloadPDF")
    @RequestMapping(value = "v1/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadPDF(String rentId, HttpServletRequest request){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/downloadPDF---------start" + rentId);
        }
        DownloadPDFResp pdfUrl = new DownloadPDFResp();
        ModelAndView mav = new ModelAndView("redirect:../../../web/document/v1/download");
        if(!StringUtils.isEmpty(rentId)){
            try {
                pdfUrl = webLeaseService.findPDFUrl(rentId);
                mav.addObject("fileName", pdfUrl.getName() + ".pdf");
                mav.addObject("fileUrl", pdfUrl.getURL());
                return mav;
            }catch (Exception e){
                LOG.error("web/lease/v1/downloadPDF---------------error---",e);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/downloadPDF---------end");
        }
        return mav;
    }



    /**
     * <p>
     * 上传合同
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/8 18:05
     */
    @RequiresPermissions(value = "lease-uploadContractPDF")
    @RequestMapping(value = "v1/uploadContractPDF", method = RequestMethod.POST)
    public Response uploadContractPDF(@RequestParam ("file")MultipartFile file,String rentId){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/uploadContractPDF---------start" + file);
        }
        Response response = new Response();
        String fileName = file.getOriginalFilename();
        // 原文件名称不加后缀
        String name = fileName.substring(0, fileName.indexOf("."));
        String mediaType = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(name.length() > 50){
            response.setCode(WebLeaseCode.FILENAME_FORMAT);
            return response;
        }
        if(!mediaType.toLowerCase().equals("pdf")){
            response.setCode(WebLeaseCode.FILETYPE_FORMAT);
            return response;
        }
        if(file.getSize() > WebLeaseConstant.PDF_FILE_SIZE){
            response.setCode(WebLeaseCode.FILE_SIZE_ERROR);
            return response;
        }
        try {
            String newFilename = FileUtil.uploadFile(file, ConfigManager.read("file.dir") + "lease/");
            webLeaseService.savePDF(rentId, name, newFilename);
        } catch (Exception e) {
            LOG.error("web/lease/v1/uploadContractPDF---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/uploadContractPDF---------end");
        }
        return response;
    }


    /**
     * <p>
     * web租赁列表
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/8 18:03
     */
    @RequiresPermissions(value = "lease-findAllLeaseList")
    @RequestMapping(value = "v1/findAllLeaseList", method = RequestMethod.POST)
    public Response findAllLeaseList(@RequestBody @Valid WebFindAllLeaseReq req,  BindingResult bindingResult){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/findAllLeaseList---------start" + req);
        }
        Response response = new Response();

        if(!StringUtils.isEmpty(req.getStartTimeStr())){
            SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
            try {
                req.setStartTime(sdfStart.parse(req.getStartTimeStr()));
            } catch (ParseException e) {
                LOG.error("web/lease/v1/findAllLeaseList---------------error---",e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
        }

        if(!StringUtils.isEmpty(req.getEndTimeStr())){
            SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                req.setEndTime(sdfEnd.parse(req.getEndTimeStr() + " 23:59:59"));
            } catch (ParseException e) {
                LOG.error("web/lease/v1/findAllLeaseList---------------error---",e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
        }

        // 校验基本参数
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        Paging<WebLeaseList> pager = new Paging<>(req.getCurPage(), req.getPageSize());
        // 合同是否失效 0全部 1未失效 2失效
        // 是否上传合同 3是全部0是未上传1是已上传
        try {
            List<WebLeaseList> all = webLeaseService.findAll(pager,req);
            pager.result(all);
            response.setData(pager);
        }catch (Exception e){
            LOG.error("web/lease/v1/findAllLeaseList---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/findAllLeaseList---------end" + response);
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
    @RequiresPermissions(value = "lease-saveLeaseInfo")
    @RequestMapping(value = "v1/saveLeaseInfo", method = RequestMethod.POST)
    public Response saveLeaseInfo(@RequestBody @Valid SaveLeaseReq req, BindingResult bindingResult){
        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/saveLeaseInfo---------start" + req);
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

        String resultCode = paramJudgeSpace(req);
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
            LOG.error("web/lease/v1/saveLeaseInfo---------------error---",e);
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
            LOG.error("web/lease/v1/saveLeaseInfo---------------error---",e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("-----web/lease/v1/saveLeaseInfo---------end" + response);
        }
        return response;
    }

    @RequiresPermissions(value = "lease-findBuildingList")
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/lease/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            //查询所有楼宇
            List<BuildingInfo> buildingInfoList = buildingService.findBuildingList(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========web/lease/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/lease/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    @RequiresPermissions(value = "lease-findUnitList")
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/lease/v1/findUnitList start========building" + building);
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
            LOG.error("========web/lease/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/lease/v1/findUnitList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询对应房屋号
     * </p>
     *
     * @param:
     * @return
     * @throws
     * @Author: geyf <br/>
     * @date: 2018/5/11 11:48
     */
    @RequiresPermissions(value = "lease-findHouses")
    @RequestMapping(value = "v1/findHouses", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseInfo>> findHouses(@RequestBody FindHouseReq houseReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/lease/v1/findHouses start========houseReq" + houseReq);
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
            LOG.error("========web/lease/v1/findHouses error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/lease/v1/findHouses end========response" + response);
        }
        return response;
    }

    public static String paramJudgeSpace(SaveLeaseReq req){
        req.setCompanyName(req.getCompanyName().trim());
        req.setTenant(req.getTenant().trim());
        req.setTenantPhone(req.getTenantPhone().trim());
        req.setPropertyCompany(req.getPropertyCompany().trim());
        req.setPropertyPhone(req.getPropertyPhone().trim());
        // 校验参数正则
        if(!req.getCompanyName().matches(WebLeaseConstant.COMPANYNAME_FORMAT)){
            return LeaseCode.COMPANYNAME_FORMAT_ERROR;
        }
        if(!req.getTenant().matches(WebLeaseConstant.TENANT_FORMAT)){
            return LeaseCode.TENANT_FORMAT_ERROR;
        }
        if(!req.getTenantPhone().matches(WebLeaseConstant.TENANTPHONE_FORMAT)){
            return LeaseCode.TENANTPHONE_FORMAT_ERROR;
        }
        if(!req.getPropertyCompany().matches(WebLeaseConstant.PROPERTYCOMPANY_FORMAT)){
            return LeaseCode.PROPERTYCOMPANY_FORMAT_ERROR;
        }
        if(!req.getPropertyPhone().matches(WebLeaseConstant.PROPERTYPHONE_FORMAT)){
            return LeaseCode.TENANTPHONE_FORMAT_ERROR;
        }
        return null;
    }

}
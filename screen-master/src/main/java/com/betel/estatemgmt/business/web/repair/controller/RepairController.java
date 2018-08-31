package com.betel.estatemgmt.business.web.repair.controller;

import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.remind.constant.RemindDataValidation;
import com.betel.estatemgmt.business.web.repair.code.RepairCode;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.repair.model.*;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 维修管理接口
 * </p>
 * ClassName: RepairController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 16:11 <br/>
 * Version: 1.0 <br/>
 */
@RestController("webRepairController")
@RequestMapping("web/repair")
public class RepairController extends BaseController {
    @Autowired
    private RepairService repairService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    EstateMapper estateMapper;

    private static final Logger LOG = LoggerFactory.getLogger(RepairController.class);

    private static String estateType_house = "1";

    /**
     * <p>
     * 分页查询维修单列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 16:22
     * @param repairPageReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findAllOrders")
    @RequestMapping(value = "v1/findAllOrders", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<OrderInfo>> findAllOrders(@RequestBody RepairPageReq repairPageReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findAllOrders start========repairPageReq=" + repairPageReq);
        }
        Response<Paging<OrderInfo>> response = new Response<>();
        if(repairPageReq.getBuildingId() != null && buildingService.selectByPrimaryKey(repairPageReq.getBuildingId()) == null){
            response.setCode(RepairCode.BUILDING_IS_DELETE);
        }else if (repairPageReq.getUnitId() != null && buildingService.selectBuildingUnitByUnitId(repairPageReq.getUnitId()) == null){
            response.setCode(RepairCode.UNIT_IS_DELETE);
        }else if (!StringUtil.isBlank(repairPageReq.getUserName())
                && !repairPageReq.getUserName().trim().matches(RepairDataValidation.USERNAME_RULE)){
            response.setCode(RepairCode.USERNAME_RULE_ERROR);
        }else if (!StringUtil.isBlank(repairPageReq.getHouseNum())
                && !repairPageReq.getHouseNum().trim().matches(RepairDataValidation.HOUSENUM_RULE)){
            response.setCode(RepairCode.HOUSENUM_RULE_ERROR);
        }else if (!StringUtil.isBlank(repairPageReq.getStartTime()) && !validateTime(repairPageReq.getStartTime().trim())){
            response.setCode(RepairCode.TIMR_RULE_ERROR);
        }else if (!StringUtil.isBlank(repairPageReq.getEndTime()) && !validateTime(repairPageReq.getEndTime().trim())){
            response.setCode(RepairCode.TIMR_RULE_ERROR);
        }else if (!StringUtil.isBlank(repairPageReq.getStartTime()) && !StringUtil.isBlank(repairPageReq.getEndTime())
                && validateTime(repairPageReq.getStartTime().trim()) && validateTime(repairPageReq.getEndTime().trim())
                && !validateTimeDistance(repairPageReq.getStartTime(),repairPageReq.getEndTime())){
            response.setCode(RepairCode.STARTTIME_LATE_ENDTIME);
        }else if (!StringUtil.isBlank(repairPageReq.getOrderNo())
                && !repairPageReq.getOrderNo().trim().matches(RepairDataValidation.ORDERNO_RULE)){
            response.setCode(RepairCode.REPAIRID_RULE_ERROR);
        } else {
            Paging<OrderInfo> pager = new Paging<>(repairPageReq.getCurPage(), repairPageReq.getPageSize());
            //前后去空格
            if (!StringUtil.isEmpty(repairPageReq.getStartTime())){
                repairPageReq.setStartTime(repairPageReq.getStartTime().trim());
            }
            if (!StringUtil.isEmpty(repairPageReq.getHouseNum())){
                repairPageReq.setHouseNum(repairPageReq.getHouseNum().trim());
            }
            if (!StringUtil.isEmpty(repairPageReq.getUserName())){
                repairPageReq.setUserName(repairPageReq.getUserName().trim());
            }
            if (!StringUtil.isEmpty(repairPageReq.getEndTime())){
                repairPageReq.setEndTime(repairPageReq.getEndTime().trim());
            }
            if (!StringUtil.isEmpty(repairPageReq.getOrderNo())){
                repairPageReq.setOrderNo(repairPageReq.getOrderNo().trim());
            }
            if(!StringUtil.isBlank(repairPageReq.getHouseNum()) && repairPageReq.getHouseNum().contains("_")){
                repairPageReq.setHouseNum(repairPageReq.getHouseNum().replace("_","\\_"));
            }
            if(!StringUtil.isBlank(repairPageReq.getUserName()) && repairPageReq.getUserName().contains("_")){
                repairPageReq.setUserName(repairPageReq.getUserName().replace("_","\\_"));
            }
            if(!StringUtil.isBlank(repairPageReq.getOrderNo()) && repairPageReq.getOrderNo().contains("_")){
                repairPageReq.setOrderNo(repairPageReq.getOrderNo().replace("_","\\_"));
            }
            try {
                String estateId = AESUtil.decrypt(request.getHeader("estateId"));
                repairPageReq.setEstateId(estateId);
                //查询
                List<OrderInfo> orderList = repairService.findAllOrders(pager,repairPageReq);
                pager.result(orderList);
                response.setData(pager);
            } catch (Exception e) {
                LOG.error("========web/repairManage/v1/findAllOrders error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findAllOrders end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询所有楼宇
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 14:15
     * @param
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findBuildingList")
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            //查询所有楼宇
            List<BuildingInfo> buildingInfoList = buildingService.findBuildingList(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查所有单元
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 14:22
     * @param building
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findUnitList")
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findUnitList start========building" + building);
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
            LOG.error("========web/repairManage/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findUnitList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询确认单图片
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 9:18
     * @param picReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findOrderPic")
    @RequestMapping(value = "v1/findOrderPic", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<OrderPic> findOrderPic(@RequestBody FindPicReq picReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findOrderPic start========picReq" + picReq);
        }
        Response<OrderPic> response = new Response<>();
        try {
            if (StringUtil.isBlank(picReq.getOrderNo())){
                response.setCode(RepairCode.REPAIRID_NULL_ERROR);
            }else if (repairService.findByOrderNo(picReq.getOrderNo()) == null){
                response.setCode(RepairCode.REPAIR_NOT_EXIST);
            }else{
                OrderPic pic = repairService.findPicByOrderNo(picReq.getOrderNo());
                if (pic != null){
                    pic.setPicUrl(RepairDataValidation.FILE_SERVER_PATH+pic.getPicUrl());
                }
                response.setData(pic);
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/findOrderPic error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findOrderPic end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 新增维修单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 14:02
     * @param orderReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-addOrder")
    @RequestMapping(value = "v1/addOrder", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addOrder(@RequestBody AddOrderReq orderReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/addOrder start========orderReq" + orderReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            orderReq.setEstateId(estateId);
            //校验
            String code = validateAddRepair(orderReq,request);
            if (code != null){
                response.setCode(code);
            }else {
                //前后去空格
                trimAddReq(orderReq);
                //添加
                repairService.addRepairOrder(orderReq,request);
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/addOrder error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/addOrder end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询维修单类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 15:26
     * @param typeReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findChildOrderTypes")
    @RequestMapping(value = "v1/findChildOrderTypes", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<TypeInfo>> findChildOrderTypes(@RequestBody FindOrderTypeReq typeReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findChildOrderTypes start========typeReq" + typeReq);
        }
        Response<List<TypeInfo>> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //查询楼盘标识
            Estate estate = repairService.findByEstateId(estateId);
            if (!typeReq.getOrderArea().equals(RepairDataValidation.ORDER_AREA_PERSONAL)
                    && !typeReq.getOrderArea().equals(RepairDataValidation.ORDER_AREA_PUBLIC)){
                response.setData(null);
            }else {
                response.setData(repairService.findByArea(typeReq.getOrderArea(),Integer.parseInt(estate.getEstateType())));
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/findChildOrderTypes error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findChildOrderTypes end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 根据楼宇单元查询房屋
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 15:27
     * @param houseReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findHouses")
    @RequestMapping(value = "v1/findHouses", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseInfo>> findHouses(@RequestBody FindHouseReq houseReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findHouses start========houseReq" + houseReq);
        }
        Response<List<HouseInfo>> response = new Response<>();
        try {
            response.setData(repairService.findByIds(houseReq.getBuildingId(),houseReq.getUnitId(),AESUtil.decrypt(request.getHeader("estateId"))));
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/findHouses error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findHouses end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询维修单详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 15:57
     * @param orderReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findOrder")
    @RequestMapping(value = "v1/findOrder", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<OrderDetail> findOrder(@RequestBody FindOrderReq orderReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findOrder start========orderReq" + orderReq);
        }
        Response<OrderDetail> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(orderReq.getOrderNo())){
                response.setCode(RepairCode.REPAIRID_NULL_ERROR);
            }else{
                RepairOrder order = repairService.findByOrderNo(orderReq.getOrderNo());
                if (order == null){
                    response.setCode(RepairCode.REPAIR_NOT_EXIST);
                }else if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_CANCEL)){
                    response.setCode(RepairCode.REPAIR_IS_CANCEL);
                }else if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_END)){
                    response.setCode(RepairCode.REPAIR_IS_DONE);
                }else {
                    response.setData(repairService.findDetailByOrderNo(order.getOrderNo(),estateId));
                }
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/findOrder error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findOrder end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑维修单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 16:33
     * @param orderReq
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-updateOrder")
    @RequestMapping(value = "v1/updateOrder", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateOrder(@RequestBody UpdateOrderReq orderReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/updateOrder start========orderReq" + orderReq);
        }
        Response<String> response = new Response<>();
        try {
            //校验
            String code = validateUpdateOrder(orderReq,request);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }else {
                //去空格
                trimUpdateReq(orderReq);
                //指派维修单
                repairService.updateOrder(orderReq,request);
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/updateOrder error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/updateOrder end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑收费标准
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 19:01
     * @param req 编辑收费标准入参
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-updateStandard")
    @RequestMapping(value = "v1/updateStandard", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateStandard(@RequestBody UpdateStandardReq req,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/updateStandard start========UpdateStandardReq" + req);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (!StringUtil.isBlank(req.getConfName()) && req.getConfName().equals(RepairDataValidation.REPAIR_CHARGE_STANDARD)){
                if (!StringUtil.isBlank(req.getRepairChargeStandard())
                        && req.getRepairChargeStandard().length() > RepairDataValidation.STANDARD_MAX_LENGTH){
                    response.setCode(RepairCode.REPAIRSTANDARD_SIZE_ERROR);
                }else {
                    //修改收费标准
                    req.setEstateId(AESUtil.decrypt(estateId));
                    repairService.updateCharge(req);
                }
            }else {
                response.setCode(StatusCode.FAILURE);
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/updateStandard error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/updateStandard end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 上传报修确认单图
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 19:14
     * @param picReq
     * @param multipartfile
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-uploadOrderPic")
    @RequestMapping(value = "v1/uploadOrderPic", method = RequestMethod.POST)
    public Response<String> uploadOrderPic(UploadOrderPicReq picReq, MultipartFile multipartfile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/uploadOrderPic start========multipartFile" + multipartfile);
        }
        Response<String> response = new Response<>();
        if (null == multipartfile) {
            response.setCode(RepairCode.FILE_NULL_ERROR);
        } else if (StringUtil.isBlank(picReq.getOrderNo())){
            response.setCode(RepairCode.REPAIRID_NULL_ERROR);
        } else {
            RepairOrder order = repairService.findByOrderNo(picReq.getOrderNo());
            if (order == null){
                response.setCode(RepairCode.REPAIR_NOT_EXIST);
            }else if (order.getOrderStatus().equals(RepairDataValidation.ORDER_FINISH_STATUS)){
                response.setCode(RepairCode.REPAIR_IS_DONE);
            }else if (order.getOrderStatus().equals(RepairDataValidation.ORDER_QUXIAO_STATUS)){
                response.setCode(RepairCode.REPAIR_IS_CANCEL);
            }else {
                //获取原始文件名（包括类型）
                String picName = multipartfile.getOriginalFilename();
                if (LOG.isDebugEnabled()){
                    LOG.debug("========originalFilename=========" + picName);
                }
                //获取文件类型
                String picType = picName.substring(picName.lastIndexOf(".") + 1);
                if (LOG.isDebugEnabled()){
                    LOG.debug("========originalFileType=========" + picType);
                }
                //判断文件格式和大小
                if (picName.length() > RepairDataValidation.PICNAME_MAX_LENGTH){
                    response.setCode(RepairCode.FILENAME_SIZE_ERROR);
                } else if(multipartfile.getSize() > RepairDataValidation.PIC_MAX_SIZE){
                    response.setCode(RepairCode.FILE_SIZE_ERROR);
                } else if(!picType.toLowerCase().matches(RepairDataValidation.PIC_TYPE_RULE)){
                    response.setCode(RepairCode.FILE_RULE_ERROR);
                } else {
                    //设置图片保存路径
                    String path = ConfigManager.read(ConfigName.FILE_DIR) + "repair/";
                    String name = "";
                    try {
                        //上传后的新文件名
                        name = FileUtil.uploadFile(multipartfile, path);
                        if (LOG.isDebugEnabled()){
                            LOG.debug("========newFilename=========" + name);
                        }
                    } catch (NoSuchAlgorithmException | IOException e) {
                        LOG.error("========web/repairManage/v1/uploadOrderPic error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                    //将数据写入Picture对象中
                    Picture picture = new Picture();
                    picture.setCreateTime(new Date(System.currentTimeMillis()));
                    picture.setPictureName(picName);
                    picture.setPictureUrl("repair/" + name);
                    try {
                        repairService.uploadPic(picture,picReq);
                    } catch (Exception e) {
                        LOG.error("========web/repairManage/v1/uploadOrderPic error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/uploadOrderPic end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询维修收费标准
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/19 18:50
     * @param req
     * @return response
     *
     * */
    @RequiresPermissions(value = "repairManage-findRepairCharge")
    @RequestMapping(value = "v1/findRepairCharge", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<RepairCharge> findRepairCharge(@RequestBody FindRepairChargeReq req,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findRepairCharge start========FindRepairChargeReq" + req);
        }
        Response<RepairCharge> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (!StringUtil.isBlank(req.getConfName()) && req.getConfName().equals(RemindDataValidation.REPAIR_CHARGE_STANDARD)){
                response.setData(repairService.findByConfName(req.getConfName(),AESUtil.decrypt(estateId)));
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/findRepairCharge error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/findRepairCharge end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 打印维修单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/20 13:28
     * @param orderReq
     * @return response
     * */
    @RequiresPermissions(value = "repairManage-printOrder")
    @RequestMapping(value = "v1/printOrder", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<OrderInfo> printOrder(@RequestBody FindOrderReq orderReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/printOrder start========orderReq" + orderReq);
        }
        Response<OrderInfo> response = new Response<>();
        try {
            if (StringUtil.isBlank(orderReq.getOrderNo())){
                response.setCode(RepairCode.REPAIRID_NULL_ERROR);
            } else if (repairService.findByOrderNo(orderReq.getOrderNo()) == null){
                response.setCode(RepairCode.REPAIR_NOT_EXIST);
            } else if (repairService.findByOrderNo(orderReq.getOrderNo()).getOrderStatus().equals(RepairDataValidation.ORDER_FINISH_STATUS)){
                response.setCode(RepairCode.REPAIR_IS_DONE);
            } else {
                response.setData(repairService.findInfoByOrderNo(orderReq.getOrderNo(),AESUtil.decrypt(request.getHeader("estateId"))));
            }
        } catch (Exception e) {
            LOG.error("========web/repairManage/v1/printOrder error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/printOrder end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 上传富文本编辑图片
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/21 10:41
     * @param picReq
     * @param multipartfile
     * @return response
     *
     * */
    @RequiresPermissions(value = "common")
    @RequestMapping(value = "v1/uploadEditPic", method = RequestMethod.POST)
    public Response<OrderPic> uploadStandardPic(UploadEditPicReq picReq, MultipartFile multipartfile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/uploadEditPic start========multipartFile" + multipartfile);
        }
        Response<OrderPic> response = new Response<>();
        if (null == multipartfile) {
            response.setCode(RepairCode.FILE_NULL_ERROR);
        } else{
            //获取原始文件名（包括类型）
            String picName = multipartfile.getOriginalFilename();
            if (LOG.isDebugEnabled()){
                LOG.debug("========originalFilename=========" + picName);
            }
            //获取文件类型
            String picType = picName.substring(picName.lastIndexOf(".") + 1);
            if (LOG.isDebugEnabled()){
                LOG.debug("========originalFileType=========" + picType);
            }
            //判断文件格式和大小
            if (picName.length() > RepairDataValidation.PICNAME_MAX_LENGTH){
                response.setCode(RepairCode.FILENAME_SIZE_ERROR);
            } else if(multipartfile.getSize() > RepairDataValidation.PIC_MAX_SIZE){
                response.setCode(RepairCode.FILE_SIZE_ERROR);
            } else if(!picType.toLowerCase().matches(RepairDataValidation.PIC_TYPE_RULE)){
                response.setCode(RepairCode.FILE_RULE_ERROR);
            } else {
                //设置图片保存路径
                String path = ConfigManager.read(ConfigName.FILE_DIR) + "richText/";
                String name = "";
                try {
                    //上传后的新文件名
                    name = FileUtil.uploadFile(multipartfile, path);
                    if (LOG.isDebugEnabled()){
                        LOG.debug("========newFilename=========" + name);
                    }
                } catch (NoSuchAlgorithmException | IOException e) {
                    LOG.error("========web/repairManage/v1/uploadEditPic error!=========", e);
                    response.setCode(StatusCode.FAILURE);
                }
                //将数据写入Picture对象中
                Picture picture = new Picture();
                picture.setCreateTime(new Date(System.currentTimeMillis()));
                picture.setPictureName(picName);
                picture.setPictureUrl("richText/" + name);
                try {
                    //上传图片
                    response.setData(repairService.uploadEditPic(picture,picReq));
                } catch (Exception e) {
                    LOG.error("========web/repairManage/v1/uploadEditPic error!=========", e);
                    response.setCode(StatusCode.FAILURE);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/repairManage/v1/uploadStandardPic end========response" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 取消报修单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/23 12:15
     *
     * @param orderReq
     * @return
     */
    @RequiresPermissions(value = "repairManage-cancelRepair")
    @RequestMapping(value = "v1/cancelRepair", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> cancelRepair(@RequestBody FindOrderReq orderReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/repair/v1/cancelRepair----------start----" + orderReq);
        }
        Response<String> response = new Response();
        //获取用户ID
        String userId = request.getHeader("userId");
        if (LOG.isInfoEnabled()){
            LOG.info("==========  web/repair/v1/cancelRepair  userId================="+userId);
        }
        if(StringUtil.isBlank(userId)){
            //用户没有登录
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        try {
            String code = repairService.cancelOrder(orderReq.getOrderNo(), AESUtil.decrypt(userId),request);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("---web/repair/v1/cancelRepair----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/repair/v1/cancelRepair----------end----"+response);
        }
        return response;
    }

    /**
     *  <p>
     * 分页搜索查询所有操作记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 15:48
     *
     * @param recordReq
     * @return
     */
    @RequiresPermissions(value = "repairManage-findAllRecords")
    @RequestMapping(value = "v1/findAllRecords", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<FindAllRecordResp>> findAllRecords(@RequestBody FindAllRecordReq recordReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/repair/v1/findAllRecords----------start----" + recordReq);
        }
        Response<Paging<FindAllRecordResp>> response = new Response();
        Paging<FindAllRecordResp> pager = new Paging<>(recordReq.getCurPage(), recordReq.getPageSize());
        try {
            //校验入参
            String code = validateRecord(recordReq);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }else {
                //查询
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                recordReq.setEstateId(AESUtil.decrypt(estateId));
                List<FindAllRecordResp> list = repairService.findAllRecords(pager,recordReq);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("---web/repair/v1/findAllRecords----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/repair/v1/findAllRecords----------end----"+response);
        }
        return response;
    }


    /**
     *  <p>
     * 条件查询指派人员
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 15:48
     *
     * @param executorReq
     * @return
     */
    @RequiresPermissions(value = "repairManage-findExecutors")
    @RequestMapping(value = "v1/findExecutors", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<FindExecutorResp>> findExecutors(@RequestBody FindExecutorReq executorReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/repair/v1/findExecutors----------start----" + executorReq);
        }
        Response<List<FindExecutorResp>> response = new Response();
        try {
            //查询
            response = repairService.findExecutors(executorReq,request);
        } catch (Exception e) {
            LOG.error("---web/repair/v1/findExecutors----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---web/repair/v1/findExecutors----------end----"+response);
        }
        return response;
    }

    /**
     * <p>
     * 查询部门列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/11/10 16:21
     */
    @RequiresPermissions(value = "repairManage-findDeptLists")
    @RequestMapping(value = "v1/findDeptLists", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<DeptResp>> findDeptLists(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/repair/v1/findDeptLists-------start");
        }
        Response<List<DeptResp>> response = new Response<>();
        try {
            response = repairService.findDeptList(request);
        } catch (Exception e) {
            LOG.error("--------web/repair/v1/findDeptLists---------error-----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/repair/v1/findDeptLists-------end---response=" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 查询子部门列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/1/5 8:47
     *
     * @param deptReq
     * @return
     */
    @RequiresPermissions(value = "repairManage-findPositions")
    @RequestMapping(value = "v1/findPositions", method = RequestMethod.POST)
    public Response<List<Department>> findPosition(@RequestBody DeptReq deptReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/repair/v1/findPositions-------start,deptReq="+deptReq);
        }
        Response<List<Department>> response = new Response<>();
        try {
            response = repairService.findPositions(deptReq,request);
        } catch (Exception e) {
            LOG.error("--------web/repair/v1/findPositions--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/repair/v1/findPositions-------end,response=" + response);
        }
        return response;
    }

    /**
     * 校验操作记录入参
     * @param req
     * @return
     */
    private String validateRecord(FindAllRecordReq req){
        //去空格
        if (!StringUtil.isEmpty(req.getOrderNo())){
            req.setOrderNo(req.getOrderNo().trim());
        }
        //校验
        if (!StringUtil.isBlank(req.getStartTime()) && !validateTime(req.getStartTime().trim())){
            return RepairCode.TIMR_RULE_ERROR;
        }else if (!StringUtil.isBlank(req.getEndTime()) && !validateTime(req.getEndTime().trim())){
            return RepairCode.TIMR_RULE_ERROR;
        }else if (!StringUtil.isBlank(req.getStartTime()) && !StringUtil.isBlank(req.getEndTime())
                && validateTime(req.getStartTime().trim()) && validateTime(req.getEndTime().trim())
                && !validateTimeDistance(req.getStartTime(),req.getEndTime())){
            return RepairCode.STARTTIME_LATE_ENDTIME;
        }else if (!StringUtil.isBlank(req.getOrderNo())
                && !req.getOrderNo().trim().matches(RepairDataValidation.ORDERNO_RULE)){
            return RepairCode.REPAIRID_RULE_ERROR;
        }
        return null;
    }

    /**
     * <p>
     * 校验编辑维修单入参
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 16:43
     * @param orderReq
     * @return response
     *
     * */
    private String validateUpdateOrder(UpdateOrderReq orderReq,HttpServletRequest request) throws Exception{
        String code = null;
        if (StringUtil.isBlank(orderReq.getOrderNo())){
            code = RepairCode.REPAIRID_NULL_ERROR;
        }else{
            RepairOrder order = repairService.findByOrderNo(orderReq.getOrderNo());
            if (order == null){
                code = RepairCode.REPAIR_NOT_EXIST;
            }else if (order.getOrderArea().equals(RepairDataValidation.ORDER_AREA_PERSONAL)
                    && !repairService.findByHouseId(order.getHouseId(),AESUtil.decrypt(request.getHeader("estateId")))){
                code = RepairCode.HOUSE_NOT_EXIST;
            }else if (StringUtil.isBlank(orderReq.getOperatorId())){
                code = RepairCode.OPERATOR_NULL_ERROR;
            }else if (null == repairService.validateOperatorExist(orderReq.getOperatorId(),request)){
                code = RepairCode.OPERATOR_IS_DELETE;
            }else if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_CANCEL)){
                code = RepairCode.REPAIR_IS_CANCEL;
            }else if (order.getOrderStatus().equals(RepairConstant.ORDER_STATUS_END)){
                code = RepairCode.REPAIR_IS_DONE;
            }
        }
        return code;
    }

    /**
     * <p>
     * 编辑维修单入参去空格
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 16:47
     * @param orderReq
     * @return UpdateOrderReq
     *
     * */
    private UpdateOrderReq trimUpdateReq(UpdateOrderReq orderReq){
        if (!StringUtil.isEmpty(orderReq.getOperatorPhone())){
            orderReq.setOperatorPhone(orderReq.getOperatorPhone().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOrderNo())){
            orderReq.setOrderNo(orderReq.getOrderNo().trim());
        }
        return orderReq;
    }

    /**
     * <p>
     * 校验新增维修单信息
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 14:02
     * @param orderReq 新增入参
     * @return code码
     *
     * */
    private String validateAddRepair(AddOrderReq orderReq,HttpServletRequest request) throws Exception{
        String estateId = AESUtil.decrypt(request.getHeader("estateId"));
        String code = null;
        if (orderReq.getOrderArea() == 1 && StringUtil.isBlank(orderReq.getHouseId())){
            code = RepairCode.HOUSEID_NULL_ERROR;
        }else if ((orderReq.getHouseId()!= null)
                && !repairService.findByHouseId(orderReq.getHouseId(),estateId)){
            code = RepairCode.HOUSE_NOT_EXIST;
        }else if (StringUtil.isBlank(orderReq.getOrderContact())){
            code = RepairCode.ORDERCONTACT_NULL_ERROR;
        }else if (!orderReq.getOrderContact().trim().matches(RepairDataValidation.USERNAME_RULE)){
            code = RepairCode.ORDERCONTACT_RULE_ERROR;
        }else if (StringUtil.isBlank(orderReq.getContactPhone())){
            code = RepairCode.CONTACTPHONE_NULL_ERROR;
        }else if (!orderReq.getContactPhone().trim().matches(RepairDataValidation.PHONE_RULE)){
            code = RepairCode.CONTACTPHONE_RULE_ERROR;
        }else if (orderReq.getOrderDesc() != null && orderReq.getOrderDesc().trim().length() > RepairDataValidation.REPAIRDESC_MAX_LENGTH){
            code = RepairCode.REPAIRDESC_SIZE_ERROR;
        }else if (StringUtil.isBlank(orderReq.getOperatorId())){
            code = RepairCode.OPERATOR_NULL_ERROR;
        }else if (null == repairService.validateOperatorExist(orderReq.getOperatorId(),request)){
            code = RepairCode.OPERATOR_IS_DELETE;
        }else if (orderReq.getOrderArea() == 0){
            code = RepairCode.REPAIRAREA_NULL_ERROR;
        }else if (orderReq.getOrderType() == 0){
            code = RepairCode.REPAIRTYPE_NULL_ERROR;
        }else if (!StringUtil.isBlank(orderReq.getAppointTime()) && !validateTime(orderReq.getAppointTime())){
            code = RepairCode.APPOINTTIME_RULE_ERROR;
        }
        return code;
    }

    /**
     * <p>
     * 前后去空格
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/18 11:35
     *
     * */
    private AddOrderReq trimAddReq(AddOrderReq orderReq){
        if (!StringUtil.isEmpty(orderReq.getContactPhone())){
            orderReq.setContactPhone(orderReq.getContactPhone().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOperatorPhone())){
            orderReq.setOperatorPhone(orderReq.getOperatorPhone().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOrderContact())){
            orderReq.setOrderContact(orderReq.getOrderContact().trim());
        }
        if (!StringUtil.isEmpty(orderReq.getOrderDesc())){
            orderReq.setOrderDesc(orderReq.getOrderDesc().trim());
        }
        return orderReq;
    }

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    private boolean validateTime(String time){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }


    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    private boolean validateTimeDistance(String startTime,String endTime){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = format.parse(startTime).getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2){
                flag = false;
            }
        } catch (ParseException e) {}
        return flag;
    }
}

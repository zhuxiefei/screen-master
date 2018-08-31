package com.betel.estatemgmt.business.userapp.repair.controller;

import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.repair.code.AppRepairCode;
import com.betel.estatemgmt.business.userapp.repair.code.GoodsCode;
import com.betel.estatemgmt.business.userapp.repair.constant.RepairConstant;
import com.betel.estatemgmt.business.userapp.repair.model.AppRepairOrder;
import com.betel.estatemgmt.business.userapp.repair.model.CancelRepairReq;
import com.betel.estatemgmt.business.userapp.repair.model.RepairPage;
import com.betel.estatemgmt.business.userapp.repair.service.RepairService;
import com.betel.estatemgmt.business.userapp.user.code.UserCode;
import com.betel.estatemgmt.business.userapp.user.constant.UserConstant;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.model.repair.RepairOrderReply;
import com.betel.estatemgmt.common.model.repair.RepairOrderType;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.Validate;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户报修模块接口
 * </p>
 * ClassName: RepairController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:16 <br/>
 * Version: 1.0 <br/>
 */
@Controller
@RequestMapping(value = "userApp/repair")
public class RepairController {
    private static final Logger LOG = LoggerFactory.getLogger(RepairController.class);

    @Autowired
    RepairService repairService;

    /**
     * 上传图片
     * Create By ZhouYe ON 2017/9/15 15:47
     */
    @ResponseBody
    @RequestMapping(value = "v1/uploadImage", method = RequestMethod.POST)
    public Response uploadImage(@RequestParam(value = "pictureFile") List<MultipartFile> pictureFile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/uploadImage----------start----");
        }
        Response response = new Response();
        if (pictureFile.size() < 1) {
            response.setCode(GoodsCode.IMAGE_NULL);
            //验证图片的数量
        } else if (pictureFile.size() > RepairConstant.NINE) {
            response.setCode(GoodsCode.IAMGE_OUTOFNUM);
        } else {
            //设置图片的集合
            List<Picture> pictures = new ArrayList<>();
            for (MultipartFile picFile : pictureFile) {
                Picture picture = new Picture();
                //获得文件名(包含类型)
                String imagname = picFile.getOriginalFilename();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("---pictures--imagname------------" + imagname);
                }
                //获取文件的类型
                String picType = imagname.substring(imagname.lastIndexOf(".") + 1);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("---pictures--picType------------" + picType);
                }
                //图片名长度验证
                if (imagname.length() > UserConstant.IMAGE_NAME_LENGTH) {
                    response.setCode(UserCode.IMAGE_NAME_LENGTH_OUT);
                } else if (!Validate.isImage(picType)) {
                    //图片类型错误
                    response.setCode(GoodsCode.IMAGE_FORMAT_WRONG);
                } else if (picFile.getSize() > HouseConstant.IMAGE_LENGTH_MAX) {
                    //图片过大
                    response.setCode(GoodsCode.IMAGE_SIZE_LARGE);
                } else {
                    //设置图片保存路径
                    String name;
                    try {
                        //返回上传后成功的图片名
                        name = FileUtil.uploadFile(picFile, RepairConstant.FILE_DIR_PATH);
                    } catch (Exception e) {
                        response.setCode(StatusCode.FAILURE);
                        LOG.error("----------app/repair/v1/uploadImage========  error!", e);
                        return response;
                    }
                    //封装
                    picture.setPictureName(imagname);
                    picture.setCreateTime(new Date());
                    picture.setPictureUrl("repair/" + name);
                    picture.setPictureId(UuidUtil.create());
                    pictures.add(picture);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("---pictures--------------" + pictures);
            }
            //保存图片至数据库
            try {
                repairService.addPicture(pictures);
            } catch (Exception e) {
                response.setCode(StatusCode.FAILURE);
                LOG.error("----------app/repair/v1/uploadImage========  error!" + e);
                return response;
            }
            StringBuffer goodsPicIds = new StringBuffer();
            int j = pictures.size();
            for (int i = 0; i < j; i++) {
                if (i == 0) {
                    goodsPicIds.append(pictures.get(i).getPictureId());
                } else {
                    goodsPicIds.append("," + pictures.get(i).getPictureId());
                }
            }
            //返回图片的id组成的字符串
            response.setData(goodsPicIds.toString());
            if (LOG.isInfoEnabled()) {
                LOG.info("---app/repair/v1/uploadImage----------end----" + response);
            }
        }
        return response;
    }

    /**
     * 添加报修记录
     * Create By ZhouYe ON 2017/9/15 15:47
     */
    @ResponseBody
    @RequestMapping(value = "v1/addRepair", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response addRepair(@RequestBody AppRepairOrder appRepairOrder, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/addRepair----------start----" + appRepairOrder);
        }
        Response response = new Response();
        //验证维修订单参数
        String code = validateRepairOrder(appRepairOrder);
        if (StatusCode.SUCCESS.equals(code)) {
            //参数合法执行添加方法
            try {
                String estateId = request.getHeader("estateId");
                //如果为空 默认为锋尚
                if(null == estateId){
                    estateId = AESUtil.encrypt("1");
                }
                appRepairOrder.setEstateId(AESUtil.decrypt(estateId));
                //获取userID，并解密
                String userId = request.getHeader("userId");
                userId = AESUtil.decrypt(userId);
                appRepairOrder.setUser(userId);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--appRepairOrder---------" + appRepairOrder);
                }
                //添加订单，判断
                String addCode = repairService.addRepair(appRepairOrder,request);
                response.setCode(addCode);
            } catch (Exception e) {
                LOG.error("---app/repair/v1/addRepair----------error----", e);
                response.setCode(StatusCode.FAILURE);
            }
        } else {
            response.setCode(code);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/addRepair----------end----" + response);
        }
        return response;
    }


    /**
     * <p>
     * 报修催单
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 10:04
     */
    @ResponseBody
    @RequestMapping(value = "v1/urgeRepair", method = RequestMethod.GET)
    public Response<String> urgeRepair(String orderNo,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/urgeRepair----------start---orderNo-" + orderNo);
        }
        Response<String> response = new Response();
        try {
            if (StringUtil.isBlank(orderNo)) {
                response.setCode(AppRepairCode.ORDER_NO_NULL);
            } else {
                String code = repairService.urgeRepair(orderNo,request);
                if (!StringUtil.isBlank(code)){
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("---app/repair/v1/urgeRepair----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/urgeRepair-----------end---orderNo-" + orderNo);
        }
        return response;
    }

    /**
     * 报修订单参数验证
     * Create By ZhouYe ON 2017/9/15 10:45
     */
    private String validateRepairOrder(AppRepairOrder appRepairOrder) {
        if (StringUtil.isBlank(appRepairOrder.getOrderContact())) {
            //报修联系人不为空
            return AppRepairCode.CONTACT_NULL;
        } else if (StringUtil.isBlank((appRepairOrder.getContactPhone()))) {
            //报修联系人号码不为空
            return AppRepairCode.CONTACT_PHONE_NULL;
        } else if (!appRepairOrder.getOrderContact().trim().matches(RepairConstant.NAME_RULE)) {
            //报修联系人姓名格式校验
            return AppRepairCode.CONTACT_FORMAT_WRONG;
        } else if (!Validate.isMobile(appRepairOrder.getContactPhone().trim())) {
            //报修联系人号码格式校验
            return AppRepairCode.CONTACT_PHONE_FORMAT_WRONG;
        } else if (StringUtil.isBlank(appRepairOrder.getOrderArea().toString())) {
            //报修区域校验
            return AppRepairCode.ORDER_AREA_NULL;
        } else if (StringUtil.isBlank(appRepairOrder.getOrderType())) {
            //报修类型不为空
            return AppRepairCode.ORDER_TYPE_NULL;
        } else if (!StringUtil.isBlank(appRepairOrder.getOrderDesc()) && appRepairOrder.getOrderDesc().trim().length() > RepairConstant.ORDER_DES_LENGTH) {
            //报修描述过长
            return AppRepairCode.ORDER_DES;
        } else if (!StringUtil.isBlank(appRepairOrder.getAppointTime()) && !validateTimeDistance(new Date(), appRepairOrder.getAppointTime())) {
            //预约维修时间早于当前时间
            return AppRepairCode.APPOINTTIME_EARLY_NOW;
        }
        return StatusCode.SUCCESS;
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
    private boolean validateTimeDistance(Date startTime, String endTime) {
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = startTime.getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2) {
                flag = false;
            }
        } catch (ParseException e) {
        }
        return flag;
    }

    /**
     * <p>
     * 查询维修类型
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/15 9:28
     */
    @ResponseBody
    @RequestMapping(value = "v1/findOrderType", method = RequestMethod.GET)
    public Response findOrderType(String typeType, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findOrderType----------start----");
        }
        Response response = new Response();
        List<RepairOrderType> repairOrderTypes;
        try {
            String estateId = request.getHeader("estateId");
            //如果为空 默认为锋尚
            if(null == estateId){
                estateId = AESUtil.encrypt("1");
            }
            //查询楼盘标识
            Estate estate = repairService.findByEstateId(AESUtil.decrypt(estateId));
            //查询全部报修类型
            repairOrderTypes = repairService.findAllRepairOrderType(typeType,Integer.parseInt(estate.getEstateType()));
            if (LOG.isDebugEnabled()) {
                LOG.debug("---repairOrderTypes----" + repairOrderTypes);
            }
            response.setData(repairOrderTypes);
        } catch (Exception e) {
            LOG.error("---app/repair/v1/findOrderType----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findOrderType----------end----" + response);
        }
        return response;
    }

    /**
     * 查询维修收费标准
     * Create By ZhouYe ON 2017/9/15 16:08
     */
    @ResponseBody
    @RequestMapping(value = "v1/findFeeScale", method = RequestMethod.GET)
    public Response findFeeScale(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findFeeScale----------start----");
        }
        Response response = new Response();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            //查询全部报修类型
            String feeScale = repairService.findFeeScale(estateId);
            if (LOG.isDebugEnabled()) {
                LOG.debug("---FeeScale----" + feeScale);
            }
            response.setData(feeScale);
        } catch (Exception e) {
            LOG.error("---app/repair/v1/findFeeScale----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findFeeScale----------end----" + response);
        }
        return response;
    }

    /**
     * 条件查询维修单
     * Create By ZhouYe ON 2017/9/15 16:08
     */
    @ResponseBody
    @RequestMapping(value = "v1/findAllRepair", method = RequestMethod.GET)
    public Response findAllRepair(RepairPage repairPage, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findAllRepair----------start----" + repairPage);
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        try {
            String estateId = request.getHeader("estateId");
            //如果为空 默认为锋尚
            if(null == estateId){
                estateId = AESUtil.encrypt("1");
            }
            repairPage.setEstateId(AESUtil.decrypt(estateId));
            userId = AESUtil.decrypt(userId);
            repairPage.setUserId(userId);
            //查询维修单
            List<AppRepairOrder> appRepairOrders = repairService.findAllRepair(repairPage);
            if (LOG.isDebugEnabled()) {
                LOG.debug("---appRepairOrders----" + appRepairOrders);
            }
            repairPage.result(appRepairOrders);
            response.setData(repairPage);
        } catch (Exception e) {
            LOG.error("---app/repair/v1/findAllRepair----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findAllRepair----------end----" + response);
        }
        return response;
    }

    /**
     * 查询维修单的详情
     * Create By ZhouYe ON 2017/9/18 10:24
     */
    @ResponseBody
    @RequestMapping(value = "v1/findRepair", method = RequestMethod.GET)
    public Response findRepair(String orderNo) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findRepair----------start----" + orderNo);
        }
        Response response = new Response();
        //判断orderNo是否为空
        if (null == orderNo) {
            response.setCode(AppRepairCode.ORDER_NO_NULL);
        } else {
            try {
                //根据订单id查询订单记录
                AppRepairOrder appRepairOrder = repairService.findRepair(orderNo);
                if (null == appRepairOrder){
                    response.setCode(AppRepairCode.REPAIR_NULL);
                }
                response.setData(appRepairOrder);
            } catch (Exception e) {
                LOG.error("---app/repair/v1/findRepair----------error----", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/findRepair----------end----" + response);
        }
        return response;
    }

    /**
     * 报修评论
     * Create By ZhouYe ON 2017/9/18 10:44
     */
    @ResponseBody
    @RequestMapping(value = "v1/addRepairReply", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response addRepairReply(@RequestBody RepairOrderReply repairOrderReply) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/addRepairReply----------start----" + repairOrderReply);
        }
        Response response = new Response();
        //验证参数的合法性
        if (repairOrderReply.getOrderNo() == null) {
            response.setCode(AppRepairCode.ORDER_NO_NULL);
        } else if (repairOrderReply.getServiceOnTime() == null) {
            response.setCode(AppRepairCode.ORDER_REPLY_TIME);
        } else if (repairOrderReply.getServiceAttitude() == null) {
            response.setCode(AppRepairCode.ORDER_REPLY_ATTITUDE);
        } else if (repairOrderReply.getServiceQuality() == null) {
            response.setCode(AppRepairCode.ORDER_REPLY_QUALITY);
        } else if (repairOrderReply.getServiceDesc() != null && repairOrderReply.getServiceDesc().trim().length() > RepairConstant.ORDER_DES_LENGTH) {
            response.setCode(AppRepairCode.ORDER_REPLY_DES);
        } else {
            try {
                //保存评价
                response.setCode(repairService.addRepairReply(repairOrderReply));
            } catch (Exception e) {
                LOG.error("---app/repair/v1/addRepairReply----------error----", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/addRepairReply----------end----" + repairOrderReply);
        }
        return response;
    }

    /**
     *  <p>
     * 用户取消报修单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/23 12:15
     *
     * @param repairReq
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "v1/cancelRepair", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> cancelRepair(@RequestBody CancelRepairReq repairReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/cancelRepair----------start----" + repairReq);
        }
        Response<String> response = new Response();
        try {
            String code = repairService.cancelRepair(repairReq,request);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("---app/repair/v1/cancelRepair----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/repair/v1/cancelRepair----------end----"+response);
        }
        return response;
    }

    /**
     * <p>
     * 删除已完成、已取消的维修单
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/23 11:49
     * @param repairReq
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "v1/deleteRepair",method = RequestMethod.POST)
    public Response deleteRepair(@RequestBody CancelRepairReq repairReq){
        if(LOG.isInfoEnabled()){
            LOG.info("---app/repair/v1/deleteRepair--------------start===repairReq="+repairReq);
        }
        Response response = new Response();
        try {
            String code = repairService.deleteRepair(repairReq.getOrderNo());
            if(!StringUtil.isBlank(code)){
                response.setCode(code);
            }
        }catch (Exception e){
            LOG.error("===app/repair/v1/deleteRepair====error",e);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/repair/v1/deleteRepair--------------end"+response);
        }
        return response;
    }
}

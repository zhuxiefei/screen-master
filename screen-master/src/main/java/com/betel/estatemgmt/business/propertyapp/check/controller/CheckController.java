package com.betel.estatemgmt.business.propertyapp.check.controller;

import com.betel.estatemgmt.business.propertyapp.check.code.CheckCode;
import com.betel.estatemgmt.business.propertyapp.check.constant.CheckConstant;
import com.betel.estatemgmt.business.propertyapp.check.model.FindDeviceInfoReq;
import com.betel.estatemgmt.business.propertyapp.check.model.FindDeviceInfoResp;
import com.betel.estatemgmt.business.propertyapp.check.model.MaintenanceResp;
import com.betel.estatemgmt.business.propertyapp.check.model.SaveCheckInfoReq;
import com.betel.estatemgmt.business.propertyapp.check.service.CheckService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.patrol.model.MaintenanceReq;
import com.betel.estatemgmt.business.web.patrol.model.PatrolEquipmentResp;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.model.Record;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.model.patrol.PatrolEquipment;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * App-设施巡检接口
 * </p>
 * ClassName: CheckController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 13:52 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/check")
public class CheckController {

    private static final Logger LOG = LoggerFactory.getLogger(CheckController.class);

    @Autowired
    CheckService checkService;

    /**
     * <p>
     * 查询设备信息
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/5 14:21
     *
     * @param findDeviceInfoReq
     * @return
     */
    @RequestMapping(value = "v1/findDeviceInfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> findDeviceInfo(@RequestBody FindDeviceInfoReq findDeviceInfoReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/check/v1/findDeviceInfo start==========equipmentId=" + findDeviceInfoReq.getEquipmentId());
        }
        Response<Object> response = new Response<>();
        try{
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            FindDeviceInfoResp findDeviceInfoResp = checkService.findDeviceInfo(findDeviceInfoReq.getEquipmentId(),
                    AESUtil.decrypt(estateId));
            if (null == findDeviceInfoResp) {
                response.setCode(CheckCode.DEVICE_NOT_EXIT);
            } else {
                response.setData(findDeviceInfoResp);
            }
        }catch (Exception e){
            LOG.error("--------app/check/v1/findDeviceInfo---------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/check/v1/findDeviceInfo end==========response="+response);
        }
        return response;
    }

    /**
     * 查询巡检设备内容设置列表（分页）
     * Author: jians.z <br/>
     *
     * @return
     */
    @RequestMapping(value = "v1/findAllEquipmentList")
    public Response<Paging<PatrolEquipmentResp>> findAllEquipmentList(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findAllEquipmentList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<PatrolEquipmentResp>> response = new Response<Paging<PatrolEquipmentResp>>();
        try {
            if (!StringUtil.isEmpty(patrolReq.getKeyWord())) {
                patrolReq.setKeyWord(patrolReq.getKeyWord().trim());
                //校验他的合法性待做
                if (!StringUtil.isBlank(patrolReq.getKeyWord()) && !patrolReq.getKeyWord().matches(RegexRule.SPECIAL_WORD_RULE)) {
                    response.setCode(CheckCode.KEYWORD_RULE_ERROR);
                    return response;
                }
                if (!StringUtil.isBlank(patrolReq.getKeyWord()) && patrolReq.getKeyWord().contains("_")) {
                    patrolReq.setEquipmentNo(patrolReq.getKeyWord().replace("_", "\\_"));
                }
            }
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else{
                patrolReq.setEstateId(AESUtil.decrypt(estateId));
                Paging<PatrolEquipmentResp> pager = new Paging<PatrolEquipmentResp>(patrolReq.getCurPage(), patrolReq.getPageSize());
                List<PatrolEquipmentResp> list = checkService.findAllEquipmentList(pager, patrolReq);
                pager.result(list);
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("--------app/check/v1/findAllEquipmentList---------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findAllEquipmentList-------end,response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 提交巡检信息
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/5 17:19
     *
     * @param saveCheckInfoReq
     * @return
     */
    @RequestMapping(value = "v1/saveCheckInfo", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> saveCheckInfo(@RequestBody SaveCheckInfoReq saveCheckInfoReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/check/v1/saveCheckInfo start===========");
        }
        Response<Object> response = new Response<>();
        //获取用户ID
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
        } else {
            try {
                if (saveCheckInfoReq.getRecordDesc() != null && saveCheckInfoReq.getRecordDesc().length() > CheckConstant.DESC_MAX_NUM) {
                    response.setCode(CheckCode.DESC_OUT_SIZE);
                } else {
                    saveCheckInfoReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
                    response.setCode(checkService.saveCheckInfo(saveCheckInfoReq, AESUtil.decrypt(userId)));
                }
            } catch (Exception e) {
                LOG.error("======saveCheckInfo===error======", e);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/check/v1/saveCheckInfo end==========");
        }
        return response;
    }


    /**
     * <p>
     * 巡检上传图片（支持多张）
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/5 17:30
     *
     * @param pictureFile
     * @return
     */
    @RequestMapping(value = "v1/uploadPic", method = RequestMethod.POST)
    public Response<Object> uploadPic(@RequestParam(value = "pictureFile") List<MultipartFile> pictureFile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/check/v1/uploadPic start===========");
        }
        Response<Object> response = new Response<>();
        if (pictureFile.size() < 1) {
            response.setCode(CheckCode.IMAGE_NULL);
            //验证图片的数量
        } else if (pictureFile.size() > CheckConstant.IMAGE_MAX_NUM) {
            response.setCode(CheckCode.IMAGE_OUT_OF_NUM);
        } else {
            List<SystemPicture> pictures = new ArrayList<>();
            for (MultipartFile picFile : pictureFile) {
                SystemPicture picture = new SystemPicture();
                String image_name = picFile.getOriginalFilename();
                String picType = image_name.substring(image_name.lastIndexOf(".") + 1);
                //图片名长度验证
                if (image_name.length() > CheckConstant.IMAGE_NAME_LENGTH) {
                    response.setCode(CheckCode.IMAGE_NAME_LENGTH_OUT);
                } else if (!Validate.isImage(picType)) {
                    //图片类型错误
                    response.setCode(CheckCode.IMAGE_FORMAT_WRONG);
                } else if (picFile.getSize() > CheckConstant.IMAGE_LENGTH_MAX) {
                    //图片过大
                    response.setCode(CheckCode.IMAGE_OUT_SIZE);
                } else {
                    //设置图片保存路径
                    String name;
                    try {
                        //返回上传后成功的图片名
                        name = FileUtil.uploadFile(picFile, ConfigManager.read(ConfigName.FILE_DIR) + "check/");
                    } catch (Exception e) {
                        response.setCode(StatusCode.FAILURE);
                        LOG.error("----------app/check/v1/uploadPic---------  error!", e);
                        return response;
                    }
                    //封装
                    picture.setPictureName(image_name);
                    picture.setCreateTime(new Date());
                    picture.setPictureUrl("check/" + name);
                    picture.setPictureId(UuidUtil.create());
                    pictures.add(picture);
                }
            }
            checkService.savePictures(pictures);
            StringBuffer picIds = new StringBuffer();
            int j = pictures.size();
            for (int i = 0; i < j; i++) {
                if (i == 0) {
                    picIds.append(pictures.get(i).getPictureId());
                } else {
                    picIds.append("," + pictures.get(i).getPictureId());
                }
            }
            //返回图片的id组成的字符串
            response.setData(picIds.toString());
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/uploadPic end--------");
        }
        return response;
    }


    /**
     * 查询设施巡检记录详情
     * <p>
     * Author: jians.z <br/>
     *
     * @param patrolReq
     * @return
     */
    @RequestMapping(value = "v1/findRecordDetail", consumes = "application/json;charset=UTF-8")
    public Response<Record> findRecordDetail(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findRecordDetail-------start,patrolReq=" + patrolReq);
        }
        Response<Record> response = new Response<Record>();
        try {
            if (StringUtil.isBlank(patrolReq.getRecordId())) {
                response.setCode(CheckCode.RECORD_ID_IS_NULL);
            } else {
                Record record = checkService.findRecordDetail(patrolReq);
                if (null == record) {
                    response.setCode(CheckCode.RECORD_IS_NOT_EXIST);
                } else {
                    response.setData(record);
                }
            }
        } catch (Exception e) {
            LOG.error("--------app/check/v1/findRecordDetail--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findRecordDetail--------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询设施巡检记录列表
     * Author: jians.z <br/>
     *
     * @param patrolReq
     * @return
     */
    @RequestMapping(value = "v1/findAllRecordList")
    public Response<Paging<Record>> findAllRecordList(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findAllRecordList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<Record>> response = new Response<>();
        try {
            Paging<Record> pager = new Paging<Record>(patrolReq.getCurPage(), patrolReq.getPageSize());
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else{
                if (userId == null) {
                    response.setCode(StatusCode.UNAUTHORIZED);
                } else {
                    patrolReq.setEmployeeId(AESUtil.decrypt(userId));
                    patrolReq.setEstateId(AESUtil.decrypt(estateId));
                    List<Record> list = checkService.findAllRecordList(pager, patrolReq);
                    pager.result(list);
                    response.setData(pager);
                }
            }
        } catch (Exception e) {
            LOG.error("---------app/check/v1/findAllRecordList--------报错--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findAllRecordList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 删除设施巡检记录（批量）
     * Author: jians.z <br/>
     *
     * @param patrolReq
     * @return
     */
    @RequestMapping(value = "v1/deleteRecord", consumes = "application/json;charset=UTF-8")
    public Response<String> deleteRecord(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/deleteRecord-------start,deptReq=" + patrolReq);
        }
        Response<String> response = new Response<String>();
        try {
            response.setCode(checkService.deleteRecord(patrolReq));
        } catch (Exception e) {
            LOG.error("--------app/check/v1/deleteRecord---------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/deleteRecord--------end,response=" + response);
        }
        return response;
    }


    /**
     * 查询巡检设备内容设置详情
     * Author: jians.z <br/>
     *
     * @param patrolReq
     * @return
     */
    @RequestMapping(value = "v1/findEquipmentDetail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<PatrolEquipment> findEquipmentDetail(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findEquipmentDetail-------start,patrolReq=" + patrolReq);
        }
        Response<PatrolEquipment> response = new Response<PatrolEquipment>();
        try {
            if (StringUtil.isBlank(patrolReq.getEquipmentId())) {
                response.setCode(CheckCode.EQUIPMENT_ID_IS_NULL);
            } else {
                PatrolEquipment patrolEquipment = checkService.findEquipmentDetail(patrolReq);
                if (null == patrolEquipment) {
                    response.setCode(CheckCode.DEVICE_NOT_EXIT);
                } else {
                    response.setData(patrolEquipment);
                }
            }
        } catch (Exception e) {
            LOG.error("--------app/check/v1/findEquipmentDetail--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findEquipmentDetail--------end,response=" + response);
        }
        return response;
    }


    /**
     * 查询设备类型列表（不分页）
     *
     * @param patrolReq
     * @return
     */
    @RequestMapping(value = "v1/findTypeList", method = RequestMethod.POST)
    public Response<List<EquipmentType>> findTypeList(@RequestBody PatrolReq patrolReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findTypeList-------start,patrolReq=" + patrolReq);
        }
        Response<List<EquipmentType>> response = new Response<List<EquipmentType>>();
        try {
            //去空格再校验
            patrolReq.setEquipmentType(null);
            patrolReq.setEstateId(AESUtil.decrypt(request.getHeader("estateId")));
            List<EquipmentType> list = checkService.findTypeList(patrolReq);
            response.setData(list);

        } catch (Exception e) {
            LOG.error("--------app/check/v1/findTypeList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findTypeList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 查询维保记录列表（不分页）
     *
     * @param maintenanceReq
     * @return
     */
    @RequestMapping(value = "v1/findMaintenanceList", method = RequestMethod.POST)
    public Response<List<MaintenanceResp>> findMaintenanceList(@RequestBody MaintenanceReq maintenanceReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findTypeList-------start,patrolReq=" + maintenanceReq);
        }
        Response<List<MaintenanceResp>> response = new Response<List<MaintenanceResp>>();
        try {
            //去空格再校验
            if (StringUtil.isBlank(maintenanceReq.getEquipmentId())) {
                response.setCode(GlobalCode.Global_ID_NULL_ERROR);
            } else {
                maintenanceReq=setStartTimeEndTime(maintenanceReq);
                List<MaintenanceResp> list = checkService.findMaintenanceList(maintenanceReq);
                response.setData(list);
            }
        } catch (Exception e) {
            LOG.error("-------app/check/v1/findTypeList--------errpr--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------app/check/v1/findTypeList-------end,response=" + response);
        }
        return response;
    }

    /**
     * 重新设置开始时间和结束时间（兼容之前app参数传值）
     * @param maintenanceReq
     * @return
     */
    private MaintenanceReq setStartTimeEndTime(MaintenanceReq maintenanceReq){
        if (!StringUtil.isBlank(maintenanceReq.getStartTime())){
            try {
                String date=TimeUtils.splitStrDate(maintenanceReq.getStartTime());
                maintenanceReq.setStartTime(date+" 00:00:00");
            }catch (Exception e){
                LOG.error("findMaintenanceList.setStartTimeEndTime.error",e);
            }
        }
        if (!StringUtil.isBlank(maintenanceReq.getEndTime())){
            try {
                String date=TimeUtils.splitStrDate(maintenanceReq.getEndTime());
                maintenanceReq.setEndTime(date+" 23:59:59");
            }catch (Exception e){
                LOG.error("findMaintenanceList.setStartTimeEndTime.error",e);
            }
        }
        return maintenanceReq;
    }
}


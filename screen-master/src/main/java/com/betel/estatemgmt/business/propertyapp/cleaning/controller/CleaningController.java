package com.betel.estatemgmt.business.propertyapp.cleaning.controller;

import com.betel.estatemgmt.business.propertyapp.cleaning.code.CleaningCode;
import com.betel.estatemgmt.business.propertyapp.cleaning.constant.CleaningConstant;
import com.betel.estatemgmt.business.propertyapp.cleaning.model.*;
import com.betel.estatemgmt.business.propertyapp.cleaning.service.CleaningService;
import com.betel.estatemgmt.business.web.cleaning.model.UpdateAreaReq;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.Validate;
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

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * app保洁控制层
 * </p>
 * ClassName: CleaningController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/2/28 10:41 <br/>
 * Version: 1.0 <br/>
 */
@RestController("AppCleaningController")
@RequestMapping("propertyApp/cleaning")
public class CleaningController {

    private static final Logger LOG = LoggerFactory.getLogger(CleaningController.class);

    @Autowired
    CleaningService cleaningService;

    /**
     * <p>
     * 分页查询保洁内容列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/2/28 10:54
     * @param findCleaningListReq
     * @return
     */
    @RequestMapping(value = "v1/findCleaningList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<CleaningInfo>> findCleaningList(@RequestBody FindCleaningListReq findCleaningListReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/findCleaningList start==========findCleaningListReq=" + findCleaningListReq);
        }
        Response<Paging<CleaningInfo>> response = new Response<>();
        Paging<CleaningInfo> pager = new Paging<CleaningInfo>(findCleaningListReq.getCurPage(), findCleaningListReq.getPageSize());
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            findCleaningListReq.setEstateId(AESUtil.decrypt(estateId));
            List<CleaningInfo> infos = cleaningService.findAllCleaningList(findCleaningListReq,pager);
            pager.result(infos);
            response.setData(pager);
        } catch (Exception e) {
            LOG.error("---propertyApp/cleaning/v1/findCleaningList----------error----", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/findCleaningList end==========");
        }
        return response;
    }

    /**
     * <p>
     * 上传图片，支持多张
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/2/28 13:47
     * @param pictureFile
     * @return
     */
    @RequestMapping(value = "v1/uploadPic", method = RequestMethod.POST)
    public Response<Object> uploadPic(@RequestParam(value = "pictureFile") List<MultipartFile> pictureFile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/uploadPic start===========");
        }
        Response<Object> response = new Response<>();
        if (pictureFile.size() < 1) {
            response.setCode(CleaningCode.IMAGE_NULL);
            //验证图片的数量
        } else if (pictureFile.size() > CleaningConstant.IMAGE_MAX_NUM) {
            response.setCode(CleaningCode.IMAGE_OUT_OF_NUM);
        } else {
            List<SystemPicture> pictures = new ArrayList<>();
            for (MultipartFile picFile : pictureFile) {
                SystemPicture picture = new SystemPicture();
                String image_name = picFile.getOriginalFilename();
                String picType = image_name.substring(image_name.lastIndexOf(".") + 1);
                //图片名长度验证
                if (image_name.length() > CleaningConstant.IMAGE_NAME_LENGTH) {
                    response.setCode(CleaningCode.IMAGE_NAME_LENGTH_OUT);
                } else if (!Validate.isImage(picType)) {
                    //图片类型错误
                    response.setCode(CleaningCode.IMAGE_FORMAT_WRONG);
                } else if (picFile.getSize() > CleaningConstant.IMAGE_LENGTH_MAX) {
                    //图片过大
                    response.setCode(CleaningCode.IMAGE_OUT_SIZE);
                } else {
                    //设置图片保存路径
                    String name;
                    try {
                        //返回上传后成功的图片名
                        name = FileUtil.uploadFile(picFile, ConfigManager.read(ConfigName.FILE_DIR) + "cleaning/");
                    } catch (Exception e) {
                        response.setCode(StatusCode.FAILURE);
                        LOG.error("----------propertyApp/cleaning/v1/uploadPic---------  error!", e);
                        return response;
                    }
                    //封装
                    picture.setPictureName(image_name);
                    picture.setCreateTime(new Date());
                    picture.setPictureUrl("cleaning/" + name);
                    picture.setPictureId(UuidUtil.create());
                    pictures.add(picture);
                }
            }
            cleaningService.savePictures(pictures);
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
            LOG.info("========propertyApp/cleaning/v1/uploadPic end==========");
        }
        return response;
    }

    /**
     * <p>
     * 提交考核
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/2/28 13:55
     * @param addReq
     * @return
     */
    @RequestMapping(value = "v1/addAssessment", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> addAssessment(@RequestBody AddAssessmentReq addReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/addAssessment start==========addReq=" + addReq);
        }
        Response<Object> response = new Response<>();
        String userId = request.getHeader("userId");
        if (userId == null) {
            //用户没有登录
            response.setCode(StatusCode.UNAUTHORIZED);
        } else {
            try {
                String estateId = request.getHeader("estateId");
                if (StringUtil.isBlank(estateId)){
                    estateId = AESUtil.encrypt("1");
                }
                cleaningService.addAssessment(addReq, AESUtil.decrypt(userId), AESUtil.decrypt(estateId));
            }catch (Exception e){
                LOG.error("========propertyApp/cleaning/v1/addAssessment error==========",e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/addAssessment end==========");
        }
        return response;
    }

    /**
     * <p>
     * 查询考核记录列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/2/28 15:15
     * @param findAssessmentListReq
     * @return
     */
    @RequestMapping(value = "v1/findAssessmentList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<CleaningRecordInfo>> findAssessmentList(@RequestBody FindAssessmentListReq findAssessmentListReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/findAssessmentList start=====findAssessmentListReq======"+findAssessmentListReq);
        }
        Response<Paging<CleaningRecordInfo>> response = new Response<>();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            findAssessmentListReq.setEstateId(AESUtil.decrypt(estateId));
            Paging<CleaningRecordInfo> pager = new Paging<CleaningRecordInfo>(findAssessmentListReq.getCurPage(), findAssessmentListReq.getPageSize());
            List<CleaningRecordInfo> records = cleaningService.findAllAssessmentList(pager,findAssessmentListReq,request);
            pager.result(records);
            response.setData(pager);
        }catch(Exception e){
            LOG.error("propertyApp/cleaning/v1/findAreaList error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========propertyApp/cleaning/v1/findAssessmentList end==========");
        }
        return response;
    }

    /**
     *  <p>
     * 查询保洁区域列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequestMapping(value = "/v1/findAreaList", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningArea>> findAreaList(HttpServletRequest request){
        if(LOG.isInfoEnabled()){
            LOG.info("======== propertyApp/cleaning/v1/findAreaList start ========");
        }
        Response<List<CleaningArea>> response = new Response();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            response.setData(cleaningService.findAllAreas(AESUtil.decrypt(estateId)));
        }catch(Exception e){
            LOG.error("propertyApp/cleaning/v1/findAreaList error!", e);
            response.setCode(FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("======== propertyApp/cleaning/v1/findAreaList end ========response=" + response);
        }
        return response;
    }

    /**
     *  <p>
     * 查询保洁类型列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequestMapping(value = "/v1/findTypeList", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningType>> findTypeList(@RequestBody UpdateAreaReq areaReq){
        if(LOG.isInfoEnabled()){
            LOG.info("======== propertyApp/cleaning/v1/findTypeList start ========");
        }
        Response<List<CleaningType>> response = new Response();
        try {
            response.setData(cleaningService.findTypes(areaReq.getAreaId()));
        }catch(Exception e){
            LOG.error("propertyApp/cleaning/v1/findTypeList error!", e);
            response.setCode(FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("======== propertyApp/cleaning/v1/findTypeList end ========response=" + response);
        }
        return response;
    }
}

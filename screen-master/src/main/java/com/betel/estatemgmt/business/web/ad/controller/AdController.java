package com.betel.estatemgmt.business.web.ad.controller;

import com.betel.estatemgmt.business.web.ad.code.AdCode;
import com.betel.estatemgmt.business.web.ad.constant.AdDataValidation;
import com.betel.estatemgmt.business.web.ad.model.*;
import com.betel.estatemgmt.business.web.ad.service.AdService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.login.service.WebLoginService;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.ad.Advertisement;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.shiro.ActiveUser;
import com.betel.estatemgmt.shiro.ActiveUserInfo;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台广告业务接口
 * </p>
 * ClassName: AdController <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 15:06 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/ad")
public class AdController extends BaseController {
    @Autowired
    private AdService adService;

    @Autowired
    private WebLoginService webLoginService;

    private static final Logger LOG = LoggerFactory.getLogger(AdController.class);

    /**
     * <p>
     * 新增广告
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param adInfo 新增广告入参
     * @return response
     */
    @RequiresPermissions(value = "activity-addAd")
    @RequestMapping(value = "v1/addAd", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addAd(@RequestBody AddAdInfo adInfo) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/addAd start========adInfo=" + adInfo);
        }
        Response<String> response = new Response<>();
        //获取userId
        String userId = null;
        try {
            userId = ActiveUserInfo.getActiveUserId();
        } catch (Exception e) {
            LOG.error("========/web/ad/v1/addAd error!=========", e);
            response.setCode(AdCode.USER_ID_NULL);
        }
        //校验数据
        if (StringUtil.isEmpty(adInfo.getAdContent().trim())) {
            response.setCode(AdCode.AD_CONTENT_NULL);
        }else if (AdDataValidation.AD_CONTENT_SIZE < adInfo.getAdContent().length()) {
            response.setCode(AdCode.AD_CONTENT_SIZE);
        } else {
            try {
                //将相关字段设置到Advertisement对象中
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Advertisement advertisement = new Advertisement();
                advertisement.setAuthorId(userId);
                advertisement.setCreateTime(new Date(System.currentTimeMillis()));
                advertisement.setAdContent(adInfo.getAdContent().trim());
                advertisement.setAdTitle(format.format(new Date()));
                //插入广告
                adService.addAd(advertisement, adInfo);
            } catch (Exception e) {
                LOG.error("========/web/ad/v1/addAd error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/addAd end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询广告列表（分页）
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param page 分页入参
     * @return response
     */
    @RequiresPermissions(value = "activity-findAllAds")
    @RequestMapping(value = "v1/findAllAds", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<AdInfo>> findAllAds(@RequestBody AdPage page) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/ad/v1/findAllAds start========page=" + page);
        }
        Response<Paging<AdInfo>> response = new Response<>();
        Paging<AdInfo> pager = new Paging<>(page.getCurPage(), page.getPageSize());
        try {
            //查询广告列表
            List<AdInfo> list;
            //去空格再校验
            String code = checkParm(trim(page));
            if (null == code) {
                if (!StringUtil.isBlank(page.getAdTitle()) && page.getAdTitle().contains("_")) {
                    page.setAdTitle(page.getAdTitle().replace("_", "\\_"));
                }
                if (!StringUtil.isBlank(page.getUserName()) && page.getUserName().contains("_")) {
                    page.setUserName(page.getUserName().replace("_", "\\_"));
                }
                list = adService.findAllAds(pager, page);
                pager.result(list);
                response.setData(pager);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========web/ad/v1/findAllAds error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/ad/v1/findAllAds end========response" + response);
        }
        return response;
    }

    private AdPage trim(AdPage page) {
        if (!StringUtil.isEmpty(page.getAdTitle())) {
            page.setAdTitle(page.getAdTitle().trim());
        }
        if (!StringUtil.isEmpty(page.getUserName())) {
            page.setUserName(page.getUserName().trim());

        }
        if (!StringUtil.isEmpty(page.getStartTime())) {
            page.setStartTime(page.getStartTime().trim());
        }
        if (!StringUtil.isEmpty(page.getEndTime())) {
            page.setEndTime(page.getEndTime().trim());
        }
        return page;
    }

    private String checkParm(AdPage page) {
        if (!StringUtil.isBlank(page.getAdTitle())) {
            if (!page.getAdTitle().matches(AdDataValidation.AD_TITLE_RULE)) {
                return GlobalCode.TITLE_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(page.getUserName())) {
            if (!page.getUserName().matches(RegexRule.USERNAME_RULE)) {
                return GlobalCode.NICK_NAME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(page.getStartTime())) {
            if (!page.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(page.getEndTime())) {
            if (!page.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(page.getStartTime(), page.getEndTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }
        return null;
    }


    /**
     * <p>
     * 删除广告（支持批量）
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param adIds 广告编号数组
     * @return response
     */
    @RequiresPermissions(value = "activity-deleteAd")
    @RequestMapping(value = "v1/deleteAd", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteAd(@RequestBody DeleteAdId adIds) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/ad/v1/deleteAd start========adIds=" + adIds);
        }
        Response<String> response = new Response<>();
        if (StringUtil.isEmpty(adIds.getAdIds())) {
            response.setCode(AdCode.ADID_ARRAY_NULL);
        } else {
            try {
                //删除广告，系统广告图片，图片物理路径
                adService.deleteAdByAdIds(adIds.getAdIds());
            } catch (Exception e) {
                LOG.error("========web/ad/v1/deleteAd error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/ad/v1/deleteAd end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改广告
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param adInfo 修改广告入参
     * @return response
     */
    @RequiresPermissions(value = "activity-updateAd")
    @RequestMapping(value = "v1/updateAd", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateAd(@RequestBody UpdateAdInfo adInfo) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/updateAd start========adInfo=" + adInfo);
        }
        Response<String> response = new Response<>();
        //获取userId
        String userId = null;
        try {
            userId = ActiveUserInfo.getActiveUserId();
        } catch (Exception e) {
            LOG.error("========/web/ad/v1/updateAd error!=========", e);
            response.setCode(AdCode.USER_ID_NULL);
        }
        //校验数据
        if (null == adInfo.getAdId()) {
            response.setCode(AdCode.AD_ID_NULL);
        } else if (null == adService.findByPrimaryKey(adInfo.getAdId())) {
            response.setCode(AdCode.AD_IS_DELETE);
        } else if (StringUtil.isEmpty(adInfo.getAdContent().trim())) {
            response.setCode(AdCode.AD_CONTENT_NULL);
        } else if (AdDataValidation.AD_CONTENT_SIZE < adInfo.getAdContent().length()) {
            response.setCode(AdCode.AD_CONTENT_SIZE);
        } else {
            try {
                //将相关字段设置到Advertisement对象中
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Advertisement advertisement = new Advertisement();
                advertisement.setLastModifyId(userId);
                advertisement.setUpdateTime(new Date(System.currentTimeMillis()));
                advertisement.setAdContent(adInfo.getAdContent().trim());
                advertisement.setAdId(adInfo.getAdId());
                advertisement.setAdTitle(format.format(new Date()));
                //修改数据
                adService.updateAd(advertisement, adInfo);
            } catch (Exception e) {/*-|Code-Review|Administrator|c3|?*/
                LOG.error("========/web/ad/v1/updateAd error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/updateAd end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查看广告详情
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param info 广告编号
     * @return response
     */
    @RequiresPermissions(value = "activity-findAd")
    @RequestMapping(value = "v1/findAd", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<AdDetail> findAd(@RequestBody FindAdInfo info) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/findAd start========info=" + info);
        }
        Response<AdDetail> response = new Response<>();
        //校验数据
        if (null == info.getAdId()) {
            response.setCode(AdCode.AD_ID_NULL);
        } else if (null == adService.findByPrimaryKey(info.getAdId())) {
            response.setCode(AdCode.AD_IS_DELETE);
        } else {
            try {
                AdDetail detail = adService.findByAdId(info.getAdId());
                response.setData(detail);
            } catch (Exception e) {
                LOG.error("========/web/ad/v1/setAdTop error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/findAd end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 上传图片
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param multipartFile 上传文件
     * @return response
     */
    //@RequiresPermissions(value = "activity-uploadPic")
    @RequestMapping(value = "v1/uploadPic", method = RequestMethod.POST)
    public Response<Pic> findAd(MultipartFile multipartFile) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/uploadPic start========multipartFile=" + multipartFile);
        }
        Response<Pic> response = new Response<>();
        try {
            //由于estateId无法从请求头部获取，则在接口简单鉴权
            Subject subject = SecurityUtils.getSubject();
            String tokenContent = (String)subject.getSession().getAttribute("tokenContent");
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            // 鉴权
            if(activeUser != null) {
                // 鉴权
                boolean isExist = webLoginService.checkWebToken(tokenContent,activeUser.getUserId());
                LOG.info("======================isExist=============="+isExist);
                if (null == tokenContent || null == activeUser.getUserId() || !isExist) {
                    response.setCode(StatusCode.UNAUTHORIZED);
                    return response;
                }
            }
            if (null == multipartFile) {
                response.setCode(AdCode.AD_FILE_NULL);
            } else {
                //获取原始文件名（包括类型）
                String picName = multipartFile.getOriginalFilename();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========originalFilename=========" + picName);
                }
                //获取文件类型
                String picType = picName.substring(picName.lastIndexOf(".") + 1);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========originalFileType=========" + picType);
                }
                //判断文件格式和大小
                if (picName.length() > AdDataValidation.PIC_NAME_SIZE) {
                    response.setCode(AdCode.PICTURE_NAME_MAX);
                } else if (multipartFile.getSize() > AdDataValidation.UPLOAD_PIC_SIZE) {
                    response.setCode(AdCode.AD_FILE_SIZE);
                } else if (!picType.toLowerCase().matches(AdDataValidation.PIC_TYPE_RULE)) {
                    response.setCode(AdCode.AD_FILE_RULE);
                } else if (picType.toLowerCase().matches(AdDataValidation.PIC_TYPE_RULE)
                        && multipartFile.getSize() <= AdDataValidation.UPLOAD_PIC_SIZE) {
                    //设置图片保存路径
                    String path = ConfigManager.read(ConfigName.FILE_DIR) + "ad/";
                    String name = "";
                    try {
                        //上传后的新文件名
                        name = FileUtil.uploadFile(multipartFile, path);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========newFilename=========" + name);
                        }
                    } catch (NoSuchAlgorithmException | IOException e) {
                        LOG.error("========/web/ad/v1/uploadPic error!=========", e);
                        response.setCode(AdCode.READ_FILE_FAILUER);
                    }
                    //将数据写入Picture对象中
                    Picture picture = new Picture();
                    picture.setCreateTime(new Date(System.currentTimeMillis()));
                    picture.setPictureName(picName);
                    picture.setPictureUrl("ad/" + name);
                    try {
                        String a = adService.addPicture(picture);
                        if (a != null) {
                            Pic pic = adService.findByPictureId(a);
                            //拼接下载路径得到全路径返回
                            String picUrl = pic.getPictureUrl();
                            pic.setPictureUrl(ConfigManager.read(ConfigName.FILE_SERVER) + picUrl);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("========response picture=======" + picture);
                            }
                            response.setData(pic);
                        }
                    } catch (Exception e) {
                        LOG.error("========/web/ad/v1/uploadPic error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("========/web/ad/v1/uploadPic error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/ad/v1/findAd end========response" + response);
        }
        return response;
    }
}

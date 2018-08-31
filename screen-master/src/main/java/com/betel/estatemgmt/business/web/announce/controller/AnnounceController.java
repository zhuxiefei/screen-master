package com.betel.estatemgmt.business.web.announce.controller;

import com.betel.estatemgmt.business.web.ad.code.AdCode;
import com.betel.estatemgmt.business.web.announce.code.AnnounceCode;
import com.betel.estatemgmt.business.web.announce.constant.AnnounceDataValidation;
import com.betel.estatemgmt.business.web.announce.model.*;
import com.betel.estatemgmt.business.web.announce.service.AnnounceService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.announce.Announce;
import com.betel.estatemgmt.shiro.ActiveUserInfo;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 公告业务接口
 * </p>
 * ClassName: AnnounceController <br/>
 * Author: xiayanxin/jians.z <br/>
 * Date: 2017/5/15 18:39 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/announce")
public class AnnounceController extends BaseController {

    @Autowired
    private AnnounceService announceService;

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceController.class);

    /**
     * <p>
     * 新增公告
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param announce 新增公告入参
     * @return response
     */
    @RequiresPermissions(value = "notice-addAnnounce")
    @RequestMapping(value = "v1/addAnnounce", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addAnnounce(@RequestBody Announce announce) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/announce/v1/addAnnounce start========announce=" + announce);
        }
        Response<String> response = new Response<>();
        //获取userId
        String userId = null;
        try {
            userId = ActiveUserInfo.getActiveUserId();
        } catch (Exception e) {
            LOG.error("========web/announce/v1/addAnnounce error!=========", e);
            response.setCode(AnnounceCode.USER_ID_NULL);
        }
        //将相关字段设置到Announce对象中
        Announce announce1 = new Announce();/*-?|Code-Review|Administrator|c8|?*/
        announce1.setAuthorId(userId);
        announce1.setCreateTime(new Date(System.currentTimeMillis()));
        announce1.setAnnounceStatus(AnnounceDataValidation.STATUS_NOT_SEND);
        announce1.setIsTop(AnnounceDataValidation.NOT_TOP_STATUS);
        if (StringUtil.isEmpty(announce.getAnnounceTitle().trim())) {
            response.setCode(AnnounceCode.ANNOUNCE_TITLE_NULL);
        } else if (StringUtil.isEmpty(announce.getAnnounceContent().trim())) {
            response.setCode(AnnounceCode.ANNOUNCE_CONTENT_NULL);
        } else if (!announce.getAnnounceTitle().matches(AnnounceDataValidation.ANNOUNCE_TITLE_RULE)) {
            response.setCode(AnnounceCode.ANNOUNCE_TITLE_RULE);
        } else if (AnnounceDataValidation.ANNOUNCE_CONTENT_SIZE < announce.getAnnounceContent().length()) {
            response.setCode(AnnounceCode.ANNOUNCE_CONTENT_SIZE);
        } else {
            try {
                //向公告表中插入一条数据
                announce1.setAnnounceTitle(announce.getAnnounceTitle().trim());
                announce1.setAnnounceContent(announce.getAnnounceContent().trim());
                announceService.addAnnounce(announce1);
            } catch (Exception e) {
                LOG.error("========web/announce/v1/addAnnounce error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/announce/v1/addAnnounce end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询公告列表（支持分页）
     * </p>
     * Author: xiayanxin <br/>
     * Author: jians.z 2018-1-9 <br/>
     * Date: 2017/5/15 17:14
     *
     * @param page 分页入参
     * @return response
     */
    @RequiresPermissions(value = "notice-findAllAnnounce")
    @RequestMapping(value = "v1/findAllAnnounce", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<AnnouncePoint>> findAllAnnounce(@RequestBody AnnouncePage page) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/findAllAnnounce start========page=" + page);
        }
        Response<Paging<AnnouncePoint>> response = new Response<>();
        //判断查询标题长度限制不超过60的所有字符，不能包含\，<，>，’，”和%
        Paging<AnnouncePoint> pager = new Paging<>(page.getCurPage(), page.getPageSize());
        try {
            String code = check(trim(page));
            if (null == code) {
                if (!StringUtil.isBlank(page.getAnnounceTitle()) && page.getAnnounceTitle().contains("_")) {
                    page.setAnnounceTitle(page.getAnnounceTitle().replace("_", "\\_"));
                }
                if (!StringUtil.isBlank(page.getUserName()) && page.getUserName().contains("_")) {
                    page.setUserName(page.getUserName().replace("_", "\\_"));
                }
                //查询公告列表
                List<AnnouncePoint> list = announceService.findAllAnnounce(pager, page);
                pager.result(list);
                response.setData(pager);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========web/announce/v1/findAllAnnounce error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/findAllAnnounce end========response" + response);
        }
        return response;
    }


    private AnnouncePage trim(AnnouncePage page) {
        if (!StringUtil.isEmpty(page.getAnnounceTitle())) {
            page.setAnnounceTitle(page.getAnnounceTitle().trim());
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
        if (!StringUtil.isEmpty(page.getIsTop())) {
            page.setIsTop(page.getIsTop().trim());
        }
        if (!StringUtil.isEmpty(page.getAnnounceStatus())) {
            page.setAnnounceStatus(page.getAnnounceStatus().trim());
        }
        return page;
    }

    private String check(AnnouncePage page) {
        if (!StringUtil.isBlank(page.getAnnounceTitle())) {
            if (!page.getAnnounceTitle().matches(RegexRule.TITLE_RULE)) {
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
        if (!StringUtil.isBlank(page.getIsTop())) {
            boolean flag1 = String.valueOf(Quantity.ZERO).equals(page.getIsTop());
            boolean flag2 = String.valueOf(Quantity.ONE).equals(page.getIsTop());
            if (flag1 == false && flag2 == false) {
                return AdCode.TOP_STATUS_RULE;
            }
        }
        return null;
    }

    /**
     * <p>
     * 删除公告
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param announceId 广告编号数组
     * @return response
     */
    @RequiresPermissions(value = "notice-deleteAnnounce")
    @RequestMapping(value = "v1/deleteAnnounce", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteAnnounce(@RequestBody AnnounceId announceId) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/deleteAnnounce start========announceId=" + announceId);
        }
        Response<String> response = new Response<>();
        if (StringUtil.isEmpty(announceId.getAnnounceIds())) {
            response.setCode(AnnounceCode.ANNOUNCEID_ARRAY_NULL);
        } else {
            try {
                String announceIds = announceId.getAnnounceIds();
                //删除公告
                announceService.deleteAnnounce(announceIds);
            } catch (Exception e) {
                LOG.error("========web/announce/v1/deleteAnnounce error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/deleteAnnounce end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询公告详情
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param announce 公告编号
     * @return response
     */
    @RequiresPermissions(value = "notice-findAnnounce")
    @RequestMapping(value = "v1/findAnnounce", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<AnnounceInfo> findAnnounce(@RequestBody Announce announce) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/findAnnounce start========announce=" + announce);
        }
        Response<AnnounceInfo> response = new Response<>();
        if (announce.getAnnounceId() == null) {
            response.setCode(AnnounceCode.ANNOUNCE_ID_NULL);
        } else {
            try {
                Announce an = announceService.findByAnnounceId(announce.getAnnounceId());
                if (null == an || (null != an && AnnounceDataValidation.ANNOUNCE_DELETE_STATUS.equals(an.getAnnounceStatus()))) {
                    response.setCode(AnnounceCode.ANNOUNCE_IS_DELETE);
                } else {
                    //查询公告详情
                    AnnounceInfo info = announceService.findAnnounce(announce.getAnnounceId());
                    response.setData(info);
                }
            } catch (Exception e) {
                LOG.error("========web/announce/v1/findAnnounce error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/findAnnounce end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改公告
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param announce 修改公告入参
     * @return response
     */
    @RequiresPermissions(value = "notice-updateAnnounce")
    @RequestMapping(value = "v1/updateAnnounce", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateAnnounce(@RequestBody Announce announce) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/updateAnnounce start========announce=" + announce);
        }
        Response<String> response = new Response<>();
        //获取userId
        String userId = null;
        try {
            userId = ActiveUserInfo.getActiveUserId();
        } catch (Exception e) {
            LOG.error("========web/announce/v1/updateAnnounce error!=========", e);
            response.setCode(AnnounceCode.USER_ID_NULL);
        }
        //将相关字段设置到Announce对象中
        Announce announce1 = new Announce();/*-?|Code-Review|Administrator|c10|?*/
        announce1.setLastModifyId(userId);
        announce1.setAuthorId(userId);
        announce1.setUpdateTime(new Date(System.currentTimeMillis()));
        if (announce.getAnnounceId() == null) {
            response.setCode(AnnounceCode.ANNOUNCE_ID_NULL);
        } else if (StringUtil.isEmpty(announce.getAnnounceTitle().trim())) {
            response.setCode(AnnounceCode.ANNOUNCE_TITLE_NULL);
        } else if (StringUtil.isEmpty(announce.getAnnounceContent().trim())) {
            response.setCode(AnnounceCode.ANNOUNCE_CONTENT_NULL);
        } else if (!announce.getAnnounceTitle().matches(AnnounceDataValidation.ANNOUNCE_TITLE_RULE)) {
            response.setCode(AnnounceCode.ANNOUNCE_TITLE_RULE);
        } else if (AnnounceDataValidation.ANNOUNCE_CONTENT_SIZE < announce.getAnnounceContent().length()) {
            response.setCode(AnnounceCode.ANNOUNCE_CONTENT_SIZE);
        } else {
            try {
                Announce an = announceService.findByAnnounceId(announce.getAnnounceId());
                if (null == an || (null != an && AnnounceDataValidation.ANNOUNCE_DELETE_STATUS.equals(an.getAnnounceStatus()))) {
                    response.setCode(AnnounceCode.ANNOUNCE_IS_DELETE);
                } else {
                    //修改公告
                    announce1.setAnnounceId(announce.getAnnounceId());
                    announce1.setAnnounceContent(announce.getAnnounceContent().trim());
                    announce1.setAnnounceTitle(announce.getAnnounceTitle().trim());
                    announceService.updateAnnounce(announce1);
                }
            } catch (Exception e) {
                LOG.error("========web/announce/v1/updateAnnounce error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/announce/v1/updateAnnounce end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 设置公告置顶
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 17:14
     *
     * @param topReq
     * @return response
     */
    @RequiresPermissions(value = "notice-setAnnounceTop")
    @RequestMapping(value = "v1/setAnnounceTop", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> setAnnounceTop(@RequestBody AnnounceTopReq topReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/announce/v1/setAnnounceTop start========topReq=" + topReq);
        }
        Response<String> response = new Response<>();
        //获取userId
        String userId = null;
        try {
            userId = ActiveUserInfo.getActiveUserId();
        } catch (Exception e) {
            LOG.error("========/web/announce/v1/setAnnounceTop error!=========", e);
            response.setCode(AnnounceCode.USER_ID_NULL);
        }
        //将数据封装到Advertisement对象中
        Announce announce = new Announce();
        announce.setAnnounceId(topReq.getAnnounceId());
        announce.setUpdateTime(new Date(System.currentTimeMillis()));
        announce.setLastModifyId(userId);
        List<Announce> list = announceService.findTopAnnounces();
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======findTopAnnounces=======list=" + list);
        }
        Announce aa = announceService.findByAnnounceId(topReq.getAnnounceId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======findByAnnounceId=======Announce=" + aa);
        }
        //校验数据
        if (null == topReq.getAnnounceId()) {
            response.setCode(AnnounceCode.ANNOUNCE_ID_NULL);
        } else if (null == topReq.getStatus()) {
            response.setCode(AnnounceCode.TOP_STATUS_NULL);
        } else if (null == aa || (null != aa && AnnounceDataValidation.ANNOUNCE_DELETE_STATUS.equals(aa.getAnnounceStatus()))) {
            response.setCode(AnnounceCode.ANNOUNCE_IS_DELETE);
        } else if (!(topReq.getStatus().equals(AnnounceDataValidation.TOP_STATUS) || topReq.getStatus().equals(AnnounceDataValidation.NOT_TOP_STATUS))) {
            response.setCode(AnnounceCode.TOP_STATUS_RULE);
        } else if (topReq.getStatus().equals(AnnounceDataValidation.TOP_STATUS)) {
            if (aa != null) {
                if (aa.getIsTop().equals(AnnounceDataValidation.TOP_STATUS)) {
                    response.setCode(AnnounceCode.ANNOUNCE_IS_TOP);
                } else {
                    announce.setIsTop(AnnounceDataValidation.TOP_STATUS);
                    announce.setTopTime(new Date(System.currentTimeMillis()));
                    try {
                        announceService.setAnnounceTop(announce);
                    } catch (Exception e) {
                        LOG.error("========/web/announce/v1/setAnnounceTop error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                }
            }
        } else if (topReq.getStatus().equals(AnnounceDataValidation.NOT_TOP_STATUS)) {
            //若是取消置顶,则判断置顶是否存在,并且判断该是否已经取消置顶
            if (list == null) {
                response.setCode(AnnounceCode.NO_TOP_ANNOUNCE);
            } else if (aa != null) {
                if (aa.getIsTop().equals(AnnounceDataValidation.NOT_TOP_STATUS)) {
                    response.setCode(AnnounceCode.ANNOUNCE_NOT_TOP);
                } else {
                    announce.setIsTop(AnnounceDataValidation.NOT_TOP_STATUS);
                    announce.setTopTime(null);
                    try {
                        announceService.setAnnounceTop(announce);
                    } catch (Exception e) {
                        LOG.error("========/web/announce/v1/setAnnounceTop error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========/web/announce/v1/setAnnounceTop end========response=" + response);
        }
        return response;
    }
}

package com.betel.estatemgmt.business.web.cleaning.controller;

import com.betel.estatemgmt.business.web.cleaning.code.CleaningCode;
import com.betel.estatemgmt.business.web.cleaning.constant.CleaningConstant;
import com.betel.estatemgmt.business.web.cleaning.model.*;
import com.betel.estatemgmt.business.web.cleaning.service.CleaningService;
import com.betel.estatemgmt.business.web.task.util.WorkUtil;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Validate;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 9:25 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/cleaning")
public class CleaningController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CleaningController.class);

    @Autowired
    CleaningService cleaningService;

    /**
     * <p>
     * 添加区域
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 9:31
     *
     * @param addAreaReq
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-addArea")
    @RequestMapping(value = "/v1/addArea", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addArea(@RequestBody AddAreaReq addAreaReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/addArea start ========");
        }
        Response<String> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            addAreaReq.setEstateId(estateId);
            String code = cleaningService.addArea(addAreaReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/addArea error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/addArea end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 批量删除保洁区域
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @param deleteArea
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-deleteArea")
    @RequestMapping(value = "/v1/deleteArea", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteArea(@RequestBody DeleteAreaReq deleteArea) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/deleteArea start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.deleteArea(deleteArea);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/deleteArea error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/deleteArea end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 9:31
     *
     * @param addTypeReq
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-addType")
    @RequestMapping(value = "/v1/addType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addType(@RequestBody AddTypeReq addTypeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/addType start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.addType(addTypeReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/addType error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/addType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 批量删除保洁类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @param deleteTypeReq
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-deleteType")
    @RequestMapping(value = "/v1/deleteType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteType(@RequestBody DeleteTypeReq deleteTypeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/deleteType start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.deleteType(deleteTypeReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/deleteType error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/deleteType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑保洁区域
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @param areaReq
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-updateArea")
    @RequestMapping(value = "/v1/updateArea", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateArea(@RequestBody UpdateAreaReq areaReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/updateArea start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.updateArea(areaReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/updateArea error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/updateArea end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑保洁类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @param typeReq
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-updateType")
    @RequestMapping(value = "/v1/updateType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateType(@RequestBody UpdateTypeReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/updateType start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.updateType(typeReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/updateType error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/updateType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁区域列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-findAreaList")
    @RequestMapping(value = "/v1/findAreaList", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningArea>> findAreaList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAreaList start ========");
        }
        Response<List<CleaningArea>> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            response.setData(cleaningService.findAllAreas(estateId));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findAreaList error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAreaList end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁类型列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-findTypeList")
    @RequestMapping(value = "/v1/findTypeList", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningType>> findTypeList(@RequestBody UpdateAreaReq areaReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findTypeList start ========");
        }
        Response<List<CleaningType>> response = new Response();
        try {
            response.setData(cleaningService.findTypes(areaReq.getAreaId()));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findTypeList error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findTypeList end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁区域详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-findArea")
    @RequestMapping(value = "/v1/findArea", method = RequestMethod.POST, consumes = "application/json")
    public Response<CleaningArea> findArea(@RequestBody UpdateAreaReq areaReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findArea start ========");
        }
        Response<CleaningArea> response = new Response();
        try {
            CleaningArea area = cleaningService.findArea(areaReq.getAreaId());
            if (null == area) {
                response.setCode(CleaningCode.AREA_IS_DELETE);
            } else {
                response.setData(area);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findArea error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findArea end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁类型详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningArea-findType")
    @RequestMapping(value = "/v1/findType", method = RequestMethod.POST, consumes = "application/json")
    public Response<CleaningType> findType(@RequestBody UpdateTypeReq typeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findType start ========");
        }
        Response<CleaningType> response = new Response();
        try {
            CleaningType type = cleaningService.findType(typeReq.getTypeId());
            if (null == type) {
                response.setCode(CleaningCode.TYPE_IS_DELETE);
            } else {
                response.setData(type);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findType error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加保洁内容
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-addContent")
    @RequestMapping(value = "/v1/addContent", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addContent(@RequestBody AddContentReq contentReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/addContent start ========");
        }
        Response<String> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            contentReq.setEstateId(estateId);
            String code = cleaningService.addContent(contentReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/addContent error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/addContent end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁区域列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-findAreas")
    @RequestMapping(value = "/v1/findAreas", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningArea>> findAreas(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAreas start ========");
        }
        Response<List<CleaningArea>> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            response.setData(cleaningService.findAllAreas(estateId));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findAreas error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAreas end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁类型列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-findTypes")
    @RequestMapping(value = "/v1/findTypes", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningType>> findAreas(@RequestBody UpdateAreaReq areaReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findTypes start ========");
        }
        Response<List<CleaningType>> response = new Response();
        try {
            response.setData(cleaningService.findTypes(areaReq.getAreaId()));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findTypes error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findTypes end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页搜索查询保洁内容列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-findAllContent")
    @RequestMapping(value = "/v1/findAllContent", method = RequestMethod.POST, consumes = "application/json")
    public Response<Paging<FindAllContentResp>> findAllContent(@RequestBody FindAllContentReq contentReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAllContent start ========");
        }
        Response<Paging<FindAllContentResp>> response = new Response();
        Paging<FindAllContentResp> pager = new Paging<>(contentReq.getCurPage(), contentReq.getPageSize());
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            contentReq.setEstateId(estateId);
            String code = validateFindContentReq(contentReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            } else {
                pager.result(cleaningService.findAllContent(contentReq, pager));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findAllContent error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAllContent end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查看保洁内容详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-findContent")
    @RequestMapping(value = "/v1/findContent", method = RequestMethod.POST, consumes = "application/json")
    public Response<FindContentResp> findContent(@RequestBody FindContentReq contentReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findContent start ========");
        }
        Response<FindContentResp> response = new Response();
        try {
            if (cleaningService.findByContentId(contentReq.getContentId()) == null) {
                response.setCode(CleaningCode.CONTENT_IS_DELETE);
            } else {
                response.setData(cleaningService.findContent(contentReq.getContentId()));
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findContent error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findContent end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑保洁内容
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-updateContent")
    @RequestMapping(value = "/v1/updateContent", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateContent(@RequestBody UpdateContentReq contentReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/updateContent start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.updateContent(contentReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/updateContent error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/updateContent end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 批量删除保洁内容
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-deleteContent")
    @RequestMapping(value = "/v1/deleteContent", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteContent(@RequestBody UpdateContentReq contentReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/deleteContent start ========");
        }
        Response<String> response = new Response();
        try {
            String code = cleaningService.deleteContent(contentReq.getContentId());
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/deleteContent error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/deleteContent end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查看该保洁内容下的考核记录列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningContent-findAllContentRecords")
    @RequestMapping(value = "/v1/findAllContentRecords", method = RequestMethod.POST, consumes = "application/json")
    public Response<Paging<ContentRecordInfo>> findAllContentRecords(@RequestBody FindAllContentRecordsReq recordsReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAllContentRecords start ========");
        }
        Response<Paging<ContentRecordInfo>> response = new Response();
        Paging<ContentRecordInfo> pager = new Paging<>(recordsReq.getCurPage(), recordsReq.getPageSize());
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)) {
                estateId = AESUtil.encrypt("1");
            }
            recordsReq.setEstateId(AESUtil.decrypt(estateId));
            String code = validateRecordsReq(recordsReq);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            } else {
                pager.result(cleaningService.findContentRecords(recordsReq, pager, request));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findAllContentRecords error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAllContentRecords end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁区域列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningRecord-findCleaningAreas")
    @RequestMapping(value = "/v1/findCleaningAreas", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningArea>> findCleaningAreas(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findCleaningAreas start ========");
        }
        Response<List<CleaningArea>> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            response.setData(cleaningService.findAllAreas(estateId));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findCleaningAreas error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findCleaningAreas end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询保洁类型列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningRecord-findCleaningTypes")
    @RequestMapping(value = "/v1/findCleaningTypes", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<CleaningType>> findCleaningTypes(@RequestBody UpdateAreaReq areaReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findCleaningTypes start ========");
        }
        Response<List<CleaningType>> response = new Response();
        try {
            response.setData(cleaningService.findTypes(areaReq.getAreaId()));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findCleaningTypes error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findCleaningTypes end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分页搜索查询考核记录列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningRecord-findAllRecords")
    @RequestMapping(value = "/v1/findAllRecords", method = RequestMethod.POST, consumes = "application/json")
    public Response<Paging<FindAllRecordsResp>> findAllRecords(@RequestBody FindAllRecordsReq recordsReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAllRecords start ========");
        }
        Response<Paging<FindAllRecordsResp>> response = new Response();
        Paging<FindAllRecordsResp> pager = new Paging<>(recordsReq.getCurPage(), recordsReq.getPageSize());
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            recordsReq.setEstateId(estateId);
            //校验入参
            FindAllContentReq req = new FindAllContentReq();
            req.setContentNo(recordsReq.getContentNo());
            req.setLocation(recordsReq.getLocation());
            req.setStartTime(recordsReq.getStartTime());
            req.setEndTime(recordsReq.getEndTime());
            String code = validateFindContentReq(req);
            if (!StringUtil.isBlank(code)) {
                response.setCode(code);
            } else {
                pager.result(cleaningService.findAllRecords(recordsReq, pager));
                response.setData(pager);
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findAllRecords error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findAllRecords end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查看考核记录详情
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningRecord-findRecord")
    @RequestMapping(value = "/v1/findRecord", method = RequestMethod.POST, consumes = "application/json")
    public Response<FindRecordResp> findRecord(@RequestBody FindRecordReq recordReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findRecord start ========");
        }
        Response<FindRecordResp> response = new Response();
        try {
            response.setData(cleaningService.findRecord(recordReq.getRecordId(), request));
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/findRecord error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/findRecord end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 导出考核记录
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2018/2/28 10:23
     *
     * @return
     */
    @RequiresPermissions(value = "cleaningRecord-exportRecords")
    @RequestMapping(value = "/v1/exportRecords", method = RequestMethod.POST, consumes = "application/json")
    public Response<List<FindAllRecordsResp>> exportRecords(@RequestBody ExportRecordsReq req, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/exportRecords start ========");
        }
        Response<List<FindAllRecordsResp>> response = new Response();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            req.setEstateId(estateId);
            if (!StringUtil.isBlank(req.getRecordIds())) {
                //根据id查询
                response.setData(cleaningService.exportByIds(req.getRecordIds()));
            } else {
                //根据搜索条件查询
                //校验入参
                FindAllContentReq contentReq = new FindAllContentReq();
                contentReq.setContentNo(req.getContentNo());
                contentReq.setLocation(req.getLocation());
                contentReq.setStartTime(req.getStartTime());
                contentReq.setEndTime(req.getEndTime());
                String code = validateFindContentReq(contentReq);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                } else {
                    response.setData(cleaningService.exportByParams(req));
                }
            }
        } catch (Exception e) {
            LOG.error("web/cleaning/v1/exportRecords error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/cleaning/v1/exportRecords end ========response=" + response);
        }
        return response;
    }

    private String validateFindContentReq(FindAllContentReq contentReq) {
        //去空格
        if (!StringUtil.isEmpty(contentReq.getLocation())) {
            contentReq.setLocation(contentReq.getLocation());
        }
        if (!StringUtil.isEmpty(contentReq.getContentNo())) {
            contentReq.setContentNo(contentReq.getContentNo());
        }
        //校验
        if (!StringUtil.isBlank(contentReq.getContentNo())
                && !Validate.isCommonString(contentReq.getContentNo(), CleaningConstant.CONTENT_NO_LENGTH)) {
            return CleaningCode.CONTENT_NO_ERROR;
        } else if (!StringUtil.isBlank(contentReq.getLocation())
                && !Validate.isCommonString(contentReq.getLocation(), CleaningConstant.AREA_NAME_LENGTH)) {
            return CleaningCode.LOCATION_ERROR;
        } else if (!StringUtil.isBlank(contentReq.getStartTime()) && !WorkUtil.validateTime(contentReq.getStartTime())) {
            return CleaningCode.TIME_ERROR;
        }
        if (!StringUtil.isBlank(contentReq.getEndTime()) && !WorkUtil.validateTime(contentReq.getEndTime().trim())) {
            return CleaningCode.TIME_ERROR;
        }
        if (!StringUtil.isBlank(contentReq.getStartTime()) && !StringUtil.isBlank(contentReq.getEndTime())
                && WorkUtil.validateTime(contentReq.getStartTime().trim()) && WorkUtil.validateTime(contentReq.getEndTime().trim())
                && !WorkUtil.validateTimeDistance(contentReq.getStartTime(), contentReq.getEndTime())) {
            return CleaningCode.START_LATE_END;
        }
        return null;
    }

    private String validateRecordsReq(FindAllContentRecordsReq recordsReq) {
        if (StringUtil.isBlank(recordsReq.getContentId())) {
            return CleaningCode.CONTENT_ID_NULL;
        } else if (cleaningService.findByContentId(recordsReq.getContentId()) == null) {
            return CleaningCode.CONTENT_IS_DELETE;
        } else if (!StringUtil.isBlank(recordsReq.getStartTime()) && !WorkUtil.validateTime(recordsReq.getStartTime())) {
            return CleaningCode.TIME_ERROR;
        }
        if (!StringUtil.isBlank(recordsReq.getEndTime()) && !WorkUtil.validateTime(recordsReq.getEndTime().trim())) {
            return CleaningCode.TIME_ERROR;
        }
        if (!StringUtil.isBlank(recordsReq.getStartTime()) && !StringUtil.isBlank(recordsReq.getEndTime())
                && WorkUtil.validateTime(recordsReq.getStartTime().trim()) && WorkUtil.validateTime(recordsReq.getEndTime().trim())
                && !WorkUtil.validateTimeDistance(recordsReq.getStartTime(), recordsReq.getEndTime())) {
            return CleaningCode.START_LATE_END;
        }
        return null;
    }
}

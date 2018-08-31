package com.betel.estatemgmt.business.web.housetype.controller;

import com.betel.estatemgmt.business.web.housetype.code.HouseTypeCode;
import com.betel.estatemgmt.business.web.housetype.constant.HouseTypeConstant;
import com.betel.estatemgmt.business.web.housetype.model.*;
import com.betel.estatemgmt.business.web.housetype.service.HouseTypeService;
import com.betel.estatemgmt.business.web.housetype.service.TypeFunctionService;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialUpdate;
import com.betel.estatemgmt.business.web.material.service.BuildMaterialService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.house.HouseType;
import com.betel.estatemgmt.common.model.house.HouseTypeFunction;
import com.betel.estatemgmt.common.model.house.HouseTypeMaterial;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.RegexRule;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.TimeUtils;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.betel.estatemgmt.common.StatusCode.FAILURE;

/**
 * <p>
 * 户型维护接口
 * </p>
 * ClassName: HouseTypeController <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 8:53 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/houseType")
public class HouseTypeController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(HouseTypeController.class);

    @Autowired
    private HouseTypeService houseTypeService;

    @Autowired
    private TypeFunctionService typeFunctionService;
    @Autowired
    private BuildMaterialService buildMaterialService;

    /**
     * <p>
     * 关键词分页查询户型列表接口
     * </p>
     * Author: geyf/jians.z <br/>
     * Date: 2017/6/20 11:19
     *
     * @param pageKeyWord 关键词
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-findAllHouseType")
    @RequestMapping(value = "/v1/findAllHouseType", method = RequestMethod.POST, consumes = "application/json")
    public Response findAllHouseType(@RequestBody PageKeyWord pageKeyWord) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findAllHouseType start ========pageKeyWord=" + pageKeyWord);
        }
        Response response = new Response();
        Paging<HouseTypeVo> pager = new Paging<HouseTypeVo>(pageKeyWord.getCurPage(), pageKeyWord.getPageSize());
        try {
            String code = check(trim(pageKeyWord));
            if (null == code) {
                if (!StringUtil.isBlank(pageKeyWord.getTypeName()) && pageKeyWord.getTypeName().contains("_")) {
                    pageKeyWord.setTypeName(pageKeyWord.getTypeName().replace("_", "\\_"));
                }
                List<HouseTypeVo> allHouseType = houseTypeService.findAllHouseType(pager, pageKeyWord);
                pager.result(allHouseType);
                response.setData(pager);
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("findAllHouseType error!", e);
            response.setCode(FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findAllHouseType end ========response=" + response);
        }
        return response;
    }

    private PageKeyWord trim(PageKeyWord pageKeyWord) {
        if (!StringUtil.isEmpty(pageKeyWord.getTypeName())) {
            pageKeyWord.setTypeName(pageKeyWord.getTypeName().trim());
        }
        if (!StringUtil.isEmpty(pageKeyWord.getStartTime())) {
            pageKeyWord.setStartTime(pageKeyWord.getStartTime().trim());
        }
        if (!StringUtil.isEmpty(pageKeyWord.getEndTime())) {
            pageKeyWord.setEndTime(pageKeyWord.getEndTime().trim());
        }
        return pageKeyWord;
    }

    private String check(PageKeyWord pageKeyWord) {
        if (!StringUtil.isBlank(pageKeyWord.getTypeName())) {
            if (!pageKeyWord.getTypeName().matches(HouseTypeConstant.HOUSETYPE_FORMAT)) {
                return HouseTypeCode.HOUSETYPE_FORMAT_ERROR;
            }
        }
        if (!StringUtil.isBlank(pageKeyWord.getStartTime())) {
            if (!pageKeyWord.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(pageKeyWord.getEndTime())) {
            if (!pageKeyWord.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(pageKeyWord.getStartTime(), pageKeyWord.getEndTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }

        return null;
    }

    /**
     * <p>
     * 新增户型接口
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 13:57
     *
     * @param addHouseTypeReq 新增户型入参信息
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-addHouseType")
    @RequestMapping(value = "/v1/addHouseType", method = RequestMethod.POST, consumes = "application/json")
    public Response addHouseType(@RequestBody AddHouseTypeReq addHouseTypeReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/addHouseType start ========addHouseTypeReq=" + addHouseTypeReq);
        }
        Response response = new Response();
        String code = null;
        String[] arrFunctionNames = null;
        if (!StringUtil.isEmpty(addHouseTypeReq.getFunctionNames())) {
            arrFunctionNames = addHouseTypeReq.getFunctionNames().split(",");
        }
        //前后去空格
        if (addHouseTypeReq.getTypeName() != null) {
            addHouseTypeReq.setTypeName(addHouseTypeReq.getTypeName().trim());
        }
        if (StringUtil.isEmpty(addHouseTypeReq.getTypeName())) {
            // 户型名称为空
            code = HouseTypeCode.HOUSETYPE_ISNULL_ERROR;
            response.setCode(code);
        } else if (arrFunctionNames != null) {
            //名称格式校验
            code = houseTypeJudge(addHouseTypeReq.getTypeName(),
                    arrFunctionNames,
                    null);
            if (StringUtil.isEmpty(code)) {
                //校验成功
                try {
                    HouseType houseTypeByTypeName = houseTypeService.findHouseTypeByTypeName(addHouseTypeReq.getTypeName());
                    // 判断户型名称是否存在
                    if (houseTypeByTypeName != null) {
                        code = HouseTypeCode.HOUSETYPE_ISREPEAT_ERROR;
                        response.setCode(code);
                    } else {
                        // 敏感词过滤

                        HouseType houseType = new HouseType();
                        houseType.setTypeName(addHouseTypeReq.getTypeName());
                        houseType.setCreateTime(new Date());
                        houseTypeService.addHouseType(houseType, arrFunctionNames);
                    }
                } catch (Exception e) {
                    LOG.error("addHouseType error!", e);
                    response.setCode(FAILURE);
                }
            } else {
                // code 不为空则校验失败
                response.setCode(code);
            }

        } else if (addHouseTypeReq.getFunctionNames() == null) {
            //名称格式校验
            code = houseTypeJudge(addHouseTypeReq.getTypeName(),
                    null,
                    null);
            if (StringUtil.isEmpty(code)) {
                //校验成功
                try {
                    HouseType houseTypeByTypeName = houseTypeService.findHouseTypeByTypeName(addHouseTypeReq.getTypeName());
                    // 判断户型名称是否存在
                    if (houseTypeByTypeName != null) {
                        code = HouseTypeCode.HOUSETYPE_ISREPEAT_ERROR;
                        response.setCode(code);
                    } else {
                        HouseType houseType = new HouseType();
                        houseType.setTypeName(addHouseTypeReq.getTypeName());
                        houseType.setCreateTime(new Date());
                        houseTypeService.addHouseType(houseType, null);
                    }
                } catch (Exception e) {
                    LOG.error("addHouseType error!", e);
                    response.setCode(FAILURE);
                }
            } else {
                // code 不为空则校验失败
                response.setCode(code);
            }
        } else {
            //功能区域为空字符串
            response.setCode(HouseTypeCode.FUNCTION_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/addHouseType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除户型（可以批量）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 17:05
     *
     * @param typeIds 户型ID集合
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-deteleHouseType")
    @RequestMapping(value = "/v1/deteleHouseType", method = RequestMethod.POST, consumes = "application/json")
    public Response deteleHouseType(@RequestBody TypeIds typeIds) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/deteleHouseType start ========typeIds=" + typeIds);
        }
        Response response = new Response();
        if (!StringUtil.isEmpty(typeIds.getTypeIds())) {
            String[] split = typeIds.getTypeIds().split(",");
            String houseTypeNames = houseTypeService.findHouseByTypeName(split);
            if (StringUtil.isEmpty(houseTypeNames)) {
                // 户型可以删除
                try {
                    houseTypeService.deleteHouseTypes(split);
                } catch (Exception e) {
                    LOG.error("deteleHouseType error!", e);
                    response.setCode(FAILURE);
                }
            } else {
                // 户型下存在房屋，返回户型
                response.setCode(HouseTypeCode.HOUSETYPE_EXISTENCEHOUSE_ERROR);
                response.setData(houseTypeNames.substring(0, houseTypeNames.length() - 1));
//                response.setMessage(houseTypeNames.substring(0, houseTypeNames.length()-1));
            }
        } else {
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/deteleHouseType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改户型名称
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 17:47
     *
     * @param houseType 修改后的户型
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-updateHouseTypeName")
    @RequestMapping(value = "/v1/updateHouseTypeName", method = RequestMethod.POST, consumes = "application/json")
    public Response updateHouseTypeName(@RequestBody HouseType houseType) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/updateHouseTypeName start ========houseType=" + houseType);
        }
        Response response = new Response();
        houseType.setTypeName(houseType.getTypeName().trim());
        if (!StringUtil.isEmpty(houseType.getTypeName())) {
            String code = houseTypeJudge(houseType.getTypeName(), null, null);
            if (StringUtil.isEmpty(code)) {
                try {
                    HouseType houseType1 = houseTypeService.findHouseTypeByTypeName(houseType.getTypeName());
                    if (houseType1 == null || houseType1.getTypeId().equals(houseType.getTypeId())) {
                        // 修改后的名称不存在或者名称没有变，则可以修改
                        String resultCode = houseTypeService.updateHouseType(houseType);
                        if (resultCode != null) {
                            // 户型被删除
                            response.setCode(resultCode);
                        }
                    } else {
                        // 户型名称已存在
                        response.setCode(HouseTypeCode.HOUSETYPE_ISREPEAT_ERROR);
                    }
                } catch (Exception e) {
                    LOG.error("updateHouseTypeName error!", e);
                    response.setCode(FAILURE);
                }
            } else {
                // 户型名称校验错误
                response.setCode(code);
            }
        } else if (StringUtil.isEmpty(houseType.getTypeId() + "")) {
            // 户型ID为空
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        } else {
            // 户型名称为空
            response.setCode(HouseTypeCode.HOUSETYPE_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/updateHouseTypeName end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 新增户型下的功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 9:29
     *
     * @param houseTypeFunction 新增的户型
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-addFunction")
    @RequestMapping(value = "/v1/addFunction", method = RequestMethod.POST, consumes = "application/json")
    public Response addFunction(@RequestBody HouseTypeFunction houseTypeFunction) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/addFunction start ========houseTypeFunction=" + houseTypeFunction);
        }
        Response response = new Response();
        houseTypeFunction.setFunctionName(houseTypeFunction.getFunctionName().trim());
        if (!StringUtil.isEmpty(houseTypeFunction.getFunctionName())) {
            // 功能区域名称不为空
            String code = houseTypeJudge(null, null, houseTypeFunction.getFunctionName());
            if (StringUtil.isEmpty(code)) {
                // 功能区域名称校验成功
                // 判断功能区域是否重复
                try {
                    AddFunctionResult addFunctionResult = typeFunctionService.addHouseTypeFunction(houseTypeFunction);
                    if (addFunctionResult.getResultCode() != null) {
                        response.setCode(addFunctionResult.getResultCode());
                    } else {
                        response.setData(addFunctionResult.getHouseTypeFunction());
                    }
                } catch (Exception e) {
                    LOG.error("addFunction error!", e);
                    response.setCode(FAILURE);
                }
            } else {
                response.setCode(code);
            }
        } else if (houseTypeFunction.getTypeId() == null) {
            // 户型ID为空
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        } else {
            // 功能区域名称为空
            response.setCode(HouseTypeCode.FUNCTION_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/addFunction end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 修改户型功能区域名称
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 10:02
     *
     * @param houseTypeFunction 修改的功能区域
     * @return
     */
@RequiresPermissions(value = "houseTypeManage-updateFunctionName")
    @RequestMapping(value = "/v1/updateFunctionName", method = RequestMethod.POST, consumes = "application/json")
    public Response updateFunctionName(@RequestBody HouseTypeFunction houseTypeFunction) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/updateFunctionName start ========houseTypeFunction=" + houseTypeFunction);
        }
        Response response = new Response();
        houseTypeFunction.setFunctionName(houseTypeFunction.getFunctionName().trim());
        if (!StringUtil.isEmpty(houseTypeFunction.getFunctionName())) {
            String code = houseTypeJudge(null, null, houseTypeFunction.getFunctionName());
            if (StringUtil.isEmpty(code)) {
                // 功能区域名称校验成功
                try {
                    String resultCode = typeFunctionService.updateHouseTypeFunction(houseTypeFunction);
                    if (resultCode != null) {
                        // 修改失败，返回相应返回码
                        response.setCode(resultCode);
                    }
                } catch (Exception e) {
                    LOG.error("updateFunctionName error!", e);
                    response.setCode(FAILURE);
                }
            } else {
                response.setCode(code);
            }
        } else if (houseTypeFunction.getFunctionId() == null) {
            response.setCode(HouseTypeCode.FUNCTION_ID_ISNULL_ERROR);
        } else {
            // 功能区域名称为空
            response.setCode(HouseTypeCode.FUNCTION_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/updateFunctionName end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 10:55
     *
     * @param houseTypeFunction 功能区域ID
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-deteleFunction")
    @RequestMapping(value = "/v1/deteleFunction", method = RequestMethod.POST, consumes = "application/json")
    public Response deteleFunction(@RequestBody HouseTypeFunction houseTypeFunction) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/deteleFunction start ========houseTypeFunction=" + houseTypeFunction);
        }
        Response response = new Response();
        if (houseTypeFunction.getFunctionId() != null) {
            // 删除功能区域时候，先判断户型有没有被删除
            String code = houseTypeService.houseTypeAndFunctionJudge(houseTypeFunction.getTypeId(), null);
            if (!StringUtil.isEmpty(code)) {
                response.setCode(code);
            } else {
                try {
                    typeFunctionService.deleteHouseTypeFunction(houseTypeFunction.getFunctionId());
                } catch (Exception e) {
                    LOG.error("deteleFunction error!", e);
                    response.setCode(FAILURE);
                }
            }
        } else {
            // 功能区域ID为空
            response.setCode(HouseTypeCode.FUNCTION_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/deteleFunction end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询户型详情
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 11:28
     *
     * @param houseType 户型ID
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-findHouseType")
    @RequestMapping(value = "/v1/findHouseType", method = RequestMethod.POST, consumes = "application/json")
    public Response findHouseType(@RequestBody HouseType houseType) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findHouseType start ========houseType=" + houseType);
        }
        Response response = new Response();
        // 判断户型ID是否为空
        if (houseType.getTypeId() != null) {
            try {
                BasicHouseType basicHouseType = houseTypeService.findBasicHouseType(houseType.getTypeId());
                if (basicHouseType == null) {
                    // 户型已被删除
                    response.setCode(HouseTypeCode.HOUSETYPE_ISDETELE_ERROR);
                } else {
                    response.setData(basicHouseType);
                }
            } catch (Exception e) {
                LOG.error("findHouseType error!", e);
                response.setCode(FAILURE);
            }

        } else {
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findHouseType end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 根据户型功能区域查询建材列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 11:43
     *
     * @param houseTypeMaterialPage
     * @return
     */
@RequiresPermissions(value = "houseTypeManage-findAllFunctionMaterial")
    @RequestMapping(value = "/v1/findAllFunctionMaterial", method = RequestMethod.POST, consumes = "application/json")
    public Response findAllFunctionMaterial(@RequestBody HouseTypeMaterialPage houseTypeMaterialPage) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findAllFunctionMaterial start ========houseTypeMaterialPage=" + houseTypeMaterialPage);
        }
        Response response = new Response();
        if (houseTypeMaterialPage.getTypeId() != null) {
            Paging<FunctionMaterialVo> pager = new Paging<FunctionMaterialVo>(houseTypeMaterialPage.getCurPage(),
                    houseTypeMaterialPage.getPageSize());
            HouseTypeMaterial houseTypeMaterial = new HouseTypeMaterial();
            houseTypeMaterial.setTypeId(houseTypeMaterialPage.getTypeId());
            if (houseTypeMaterialPage.getFunctionId() == null) {
                // 设置查询所有参数
                houseTypeMaterial.setFunctionId(0L);
            } else if (houseTypeMaterialPage.getFunctionId() == 0) {
                // 设置查询全户型参数
            } else {
                houseTypeMaterial.setFunctionId(houseTypeMaterialPage.getFunctionId());
            }
            // 按功能区域查询
            try {
                String code = houseTypeService.houseTypeAndFunctionJudge(houseTypeMaterialPage.getTypeId(),
                        houseTypeMaterialPage.getFunctionId());
                if (code == null) {
                    List<FunctionMaterialVo> allMaterialByTypeFunction = houseTypeService.findAllMaterialByTypeFunction(pager, houseTypeMaterial);
                    // 到这里为查询成功
                    pager.result(allMaterialByTypeFunction);
                    response.setData(pager);
                } else {
                    // 功能区域 或户型被删除
                    response.setCode(code);
                }
            } catch (Exception e) {
                LOG.error("findAllFunctionMaterial error!", e);
                response.setCode(FAILURE);
            }

        } else {
            // 户型ID为空.
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findAllFunctionMaterial end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除户型建材（支持批量）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 14:36
     *
     * @param tmIdsReq 传入需删除的户型建材字符串集合
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-deteleHouseTypeMaterial")
    @RequestMapping(value = "/v1/deteleHouseTypeMaterial", method = RequestMethod.POST, consumes = "application/json")
    public Response deteleHouseTypeMaterial(@RequestBody TmIdsReq tmIdsReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/deteleHouseTypeMaterial start ========tmIdsReq=" + tmIdsReq);
        }
        Response response = new Response();
        String code = houseTypeService.houseTypeAndFunctionJudges(tmIdsReq.getTypeId(), tmIdsReq.getFunctionIds());
        if (!StringUtil.isEmpty(code)) {
            response.setCode(code);
        } else if (!StringUtil.isEmpty(tmIdsReq.getTmIds())) {
            try {
                houseTypeService.deleteHouseTypeMaterials(tmIdsReq.getTmIds().split(","));
            } catch (Exception e) {
                LOG.error("deteleHouseTypeMaterial error!", e);
                response.setCode(FAILURE);
            }
        } else {
            // 户型建材ID为空
            response.setCode(HouseTypeCode.HOUSETYPEMATERIAL_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/deteleHouseTypeMaterial end ========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加建材(支持批量)
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 15:03
     *
     * @param addHouseTypeMaterialReq 包含建材ID集合和功能区域ID
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-addHouseTypeMaterial")
    @RequestMapping(value = "/v1/addHouseTypeMaterial", method = RequestMethod.POST, consumes = "application/json")
    public Response addHouseTypeMaterial(@RequestBody AddHouseTypeMaterialReq addHouseTypeMaterialReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/addHouseTypeMaterial start ========addHouseTypeMaterialReq=" + addHouseTypeMaterialReq);
        }
        Response response = new Response();
        if (!StringUtil.isEmpty(addHouseTypeMaterialReq.getMaterialIds())
                && addHouseTypeMaterialReq.getTypeId() != null) {
            try {
                String resultMessage = houseTypeService.addHouseTypeMaterial(addHouseTypeMaterialReq);
                if (resultMessage == null) {
                    // 建材已被删除
                    String[] ids = addHouseTypeMaterialReq.getMaterialIds().split(",");
                    if (ids.length == 1) {
                        response.setCode(HouseTypeCode.MATERIAL_ISDELETE_ERROR);
                    } else {
                        response.setCode(HouseTypeCode.MATERIALS_ISDELETE_ERROR);
                    }
                } else if (resultMessage.equals(HouseTypeCode.HOUSETYPE_ISDETELE_ERROR) ||
                        resultMessage.equals(HouseTypeCode.FUNCTION_DELETE_ERROR)) {
                    // 户型或功能区域已被删除
                    response.setCode(resultMessage);
                } else if ("".equals(resultMessage)) {

                } else {
                    response.setCode(HouseTypeCode.MATERIAL_ISADD_ERROR);
                    // 添加失败，返回具体的已添加的建材名称
                    response.setData(resultMessage.substring(0, resultMessage.length() - 1));
//                    response.setMessage(resultMessage.substring(0, resultMessage.length()-1));
                }
            } catch (Exception e) {
                LOG.error("addHouseTypeMaterial error!", e);
                response.setCode(FAILURE);
            }
        } else if (addHouseTypeMaterialReq.getTypeId() == null) {
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        } else {
            response.setCode(HouseTypeCode.MATERIAL_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/addHouseTypeMaterial end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 分页查询某户型下能添加的建材
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/22 9:10
     *
     * @param notInFunctionReq 户型ID 功能区域ID 建材类型
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-findAllNoFunctionMaterial")
    @RequestMapping(value = "/v1/findAllNoFunctionMaterial", method = RequestMethod.POST, consumes = "application/json")
    public Response findAllNoFunctionMaterial(@RequestBody NotInFunctionReq notInFunctionReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findAllNoFunctionMaterial start ========notInFunctionReq=" + notInFunctionReq);
        }
        Response response = new Response();
        if (notInFunctionReq.getTypeId() != null) {
            Paging<HouseMaterial> pager = new Paging<HouseMaterial>(notInFunctionReq.getCurPage(),
                    notInFunctionReq.getPageSize());
            try {
                // 校验户型或功能区域是否被删除
                String code = houseTypeService.houseTypeAndFunctionJudge(notInFunctionReq.getTypeId(), notInFunctionReq.getFunctionId());
                if (code == null) {
                    //成功
                    List<HouseMaterial> allMaterialNotInFunction = houseTypeService.findAllMaterialNotInFunction(pager, notInFunctionReq);
                    pager.result(allMaterialNotInFunction);
                    response.setData(pager);
                } else {
                    // 被删除返回错误码
                    response.setCode(code);
                }
            } catch (Exception e) {
                LOG.error("findAllNoFunctionMaterial error!", e);
                response.setCode(FAILURE);
            }
        } else {
            // 户型ID 为空
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findAllNoFunctionMaterial end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询建材详情时判断户型是否删除
     * </p>
     * Author: 张建 <br/>
     * Date: 2017/6/22 9:10
     *
     * @param houseAndMaterial
     * @return response
     */
@RequiresPermissions(value = "houseTypeManage-findHouseAndMaterial")
    @RequestMapping(value = "/v1/findHouseAndMaterial", method = RequestMethod.POST, consumes = "application/json")
    public Response findHouseAndMaterial(@RequestBody HouseAndMaterial houseAndMaterial) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findHouseAndMaterial start ========houseAndMaterial=" + houseAndMaterial);
        }
        Response response = new Response();
        //校验户型id  和功能id  戶型建材id
        if (StringUtil.isBlank(houseAndMaterial.getTypeId())) {
            response.setCode(HouseTypeCode.HOUSETYPE_ID_ISNULL_ERROR);
            return response;
        }
        if ("".equals(houseAndMaterial.getFunctionId())) {
            response.setCode(HouseTypeCode.FUNCTION_ID_ISNULL_ERROR);
            return response;
        }
        //戶型建材id
        if (null == houseAndMaterial.getTypeId() && "".equals(houseAndMaterial.getTmId())) {
            response.setCode(HouseTypeCode.HOUSETYPEMATERIAL_ID_ISNULL_ERROR);
            return response;
        }


        //如果户型被删除时
        //如果户型功能区域被删除
        String code = houseTypeService.houseTypeAndFunctionIsHasDelete(houseAndMaterial.getTypeId(), houseAndMaterial.getFunctionId());
        if (null != code && !"".equals(code)) {
            response.setCode(code);
            return response;
        }

//        查詢戶型建材表判斷是否被刪除
        try {
//
            HouseTypeMaterial houseTypeMaterial = houseTypeService.selectByPrimaryKey(Long.valueOf(houseAndMaterial.getTmId().trim()));
            if (null == houseTypeMaterial) {
                response.setCode(HouseTypeCode.MATERIAL_ISDELETE_ERROR);
                return response;
            } else {
                Long materialId = houseTypeMaterial.getMaterialId();
                if (null == materialId) {
                    response.setCode(HouseTypeCode.MATERIAL_ID_ISNULL_ERROR);
                    return response;
                }
                HouseMaterial houseMaterial = new HouseMaterial();
                houseMaterial.setMaterialId(materialId);
                BuildMaterialUpdate hMaterial = buildMaterialService.findBuildMaterial(houseMaterial);
                if (null == hMaterial) {
                    response.setCode(HouseTypeCode.MATERIAL_ISDELETE_ERROR);
                    return response;
                }
                response.setData(hMaterial);

            }
        } catch (Exception e) {
            LOG.error("=========web/material/v1/findHouseAndMaterial error!=========", e);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("======== web/houseType/v1/findHouseAndMaterial end ========response=" + response);
        }
        return response;
    }


    /**
     * <p>
     * 户型名称,功能区域集合或单个功能区域名称格式的校验通用方法
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 14:03
     *
     * @param typeName      户型名称 为null则不校验该参数
     * @param functionNames 功能区域集合 为null则不校验该参数
     * @param functionName  功能区域名称 为null则不校验该参数
     * @return String 返回错误码    空：校验成功
     */
    private String houseTypeJudge(String typeName, String[] functionNames, String functionName) {
        String code = null;
        // 校验户型名称
        if (typeName != null) {
            // 户型名称长度错误
            if (typeName.length() > HouseTypeConstant.HOUSETYPE_LENGTH) {
                code = HouseTypeCode.HOUSETYPE_TOLONG_ERROR;
                return code;
            }
            // 户型名称格式错误
            if (!typeName.matches(HouseTypeConstant.HOUSETYPE_FORMAT)) {
                code = HouseTypeCode.HOUSETYPE_FORMAT_ERROR;
                return code;
            }
        }
        // 新增户型的时候，校验功能区域集合
        if (functionNames != null) {
            Set<String> set = new HashSet<String>();
            for (int i = 0; i < functionNames.length; i++) {
                // 功能区域名称不能为空字符串或空格
                if (functionNames[i].trim().length() == 0) {
                    code = HouseTypeCode.FUNCTION_ISNULL_ERROR;
                    return code;
                }
                // 功能区域名称长度错误
                if (functionNames[i].length() > HouseTypeConstant.FUNCTIONNAME_LENGTH) {
                    code = HouseTypeCode.FUNCTION_TOLONG_ERROR;
                    return code;
                }
                // 功能区域名称格式错误
                if (!functionNames[i].matches(HouseTypeConstant.HOUSETYPE_FORMAT)) {
                    code = HouseTypeCode.FUNCTION_FORMAT_ERROR;
                    return code;
                }
                // 功能区域名称不能为 所有和全户型
                if (functionNames[i].equals(HouseTypeConstant.ALL) || functionNames[i].equals(HouseTypeConstant.HOUSETYPE_ALL)) {
                    code = HouseTypeCode.FUNCTIONNAME_SENSITIVE_WORDS_ERROR;
                    return code;
                }
                set.add(functionNames[i]);
            }
            // 校验户型名称是否重复
            if (set.size() != functionNames.length) {
                code = HouseTypeCode.FUNCTION_ISREPEAT_ERROR;
                return code;
            }
        }
        // 校验单个功能区域
        if (functionName != null) {
            // 功能区域名称长度错误
            if (functionName.length() > HouseTypeConstant.FUNCTIONNAME_LENGTH) {
                code = HouseTypeCode.FUNCTION_TOLONG_ERROR;
                return code;
            }
            // 功能区域名称格式错误
            if (!functionName.matches(HouseTypeConstant.HOUSETYPE_FORMAT)) {
                code = HouseTypeCode.FUNCTION_FORMAT_ERROR;
                return code;
            }
            // 功能区域名称不能为 所有和全户型
            if (functionName.equals(HouseTypeConstant.ALL) || functionName.equals(HouseTypeConstant.HOUSETYPE_ALL)) {
                code = HouseTypeCode.FUNCTIONNAME_SENSITIVE_WORDS_ERROR;
                return code;
            }
        }
        return code;
    }

}

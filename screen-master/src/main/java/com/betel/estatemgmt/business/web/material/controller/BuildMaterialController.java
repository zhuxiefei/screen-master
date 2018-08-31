package com.betel.estatemgmt.business.web.material.controller;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.material.code.BuildMaterialCode;
import com.betel.estatemgmt.business.web.material.constant.BuildMaterialConstant;
import com.betel.estatemgmt.business.web.material.constant.BuildMaterialValidation;
import com.betel.estatemgmt.business.web.material.controller.util.MaterialUtil;
import com.betel.estatemgmt.business.web.material.model.*;
import com.betel.estatemgmt.business.web.material.service.BuildMaterialService;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.house.HouseMaterialParm;
import com.betel.estatemgmt.common.model.system.Picture;
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
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 关于家装建材操作的接口
 * </p>
 * ClassName: BuildMaterialController <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/material")
public class BuildMaterialController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BuildMaterialController.class);
    @Autowired
    private BuildMaterialService buildMaterialService;


    /**
     * <p>
     * 查询建材类型
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    @RequiresPermissions(value = "materialManage-findBuildMaterialType")
    @RequestMapping(value = "v1/findBuildMaterialType", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> findBuildMaterialType() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/ad/v1/findBuildMaterialType start========");
        }
        Response<Object> response = new Response<Object>();
        //调用service
        try {
            List<Map<String, Object>> list = buildMaterialService.findBuildMaterialType();
            if (LOG.isDebugEnabled()) {
                LOG.debug("query material list of list" + list);
            }
            response.setData(list);
        } catch (Exception e) {
            LOG.error("========web/material/v1/findBuildMaterialType error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========web/material/v1/findBuildMaterialType end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询建材列表（分页）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @param buildMaterialReq 分页入参
     * @return response
     */
    @RequiresPermissions(value = "materialManage-findAllBuildMaterial")
    @RequestMapping(value = "v1/findAllBuildMaterial", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<BuildMaterialInfo>> findAllBuildMaterial(@RequestBody BuildMaterialReq buildMaterialReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/material/v1/findAllBuildMaterial start========buildMaterialReq=" + buildMaterialReq);
        }
        Response<Paging<BuildMaterialInfo>> response = new Response<Paging<BuildMaterialInfo>>();
        Paging<BuildMaterialInfo> pager = new Paging<BuildMaterialInfo>(buildMaterialReq.getCurPage(), buildMaterialReq.getPageSize());
        try {
            String code=check(trim(buildMaterialReq));
            if (null==code){
                if (!StringUtil.isBlank(buildMaterialReq.getMaterialName()) && buildMaterialReq.getMaterialName().contains("_")) {
                    buildMaterialReq.setMaterialName(buildMaterialReq.getMaterialName().replace("_", "\\_"));
                }
                if (!StringUtil.isBlank(buildMaterialReq.getMaterialBrand()) && buildMaterialReq.getMaterialBrand().contains("_")) {
                    buildMaterialReq.setMaterialBrand(buildMaterialReq.getMaterialBrand().replace("_", "\\_"));
                }
                if (!StringUtil.isBlank(buildMaterialReq.getMaterialModel()) && buildMaterialReq.getMaterialModel().contains("_")) {
                    buildMaterialReq.setMaterialModel(buildMaterialReq.getMaterialModel().replace("_", "\\_"));
                }
                if (!StringUtil.isBlank(buildMaterialReq.getMaterialType()) && buildMaterialReq.getMaterialType().contains("_")) {
                    buildMaterialReq.setMaterialType(buildMaterialReq.getMaterialType().replace("_", "\\_"));
                }
                List<BuildMaterialInfo> mapList = buildMaterialService.findAllBuildMaterial(pager, buildMaterialReq);
                pager.result(mapList);
                response.setData(pager);
            }else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========web/material/v1/findAllBuildMaterial error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========web/material/v1/findAllBuildMaterial end========response" + response);
        }
        return response;
    }

    private BuildMaterialReq trim(BuildMaterialReq buildMaterialReq) {
        if (!StringUtil.isEmpty(buildMaterialReq.getMaterialName())) {
            buildMaterialReq.setMaterialName(buildMaterialReq.getMaterialName().trim());
        }
        if (!StringUtil.isEmpty(buildMaterialReq.getMaterialBrand())) {
            buildMaterialReq.setMaterialBrand(buildMaterialReq.getMaterialBrand().trim());
        }
        if (!StringUtil.isEmpty(buildMaterialReq.getMaterialModel())) {
            buildMaterialReq.setMaterialModel(buildMaterialReq.getMaterialModel().trim());
        }
        if (!StringUtil.isEmpty(buildMaterialReq.getMaterialType())) {
            buildMaterialReq.setMaterialType(buildMaterialReq.getMaterialType().trim());
        }
        if (!StringUtil.isEmpty(buildMaterialReq.getStartTime())) {
            buildMaterialReq.setStartTime(buildMaterialReq.getStartTime().trim());
        }
        if (!StringUtil.isEmpty(buildMaterialReq.getEndTime())) {
            buildMaterialReq.setEndTime(buildMaterialReq.getEndTime().trim());
        }
        return buildMaterialReq;
    }

    private String check(BuildMaterialReq buildMaterialReq) {
        if (!StringUtil.isBlank(buildMaterialReq.getMaterialName())) {
            if (!buildMaterialReq.getMaterialName().matches(BuildMaterialValidation.MATERIAL_NAME_RULE)) {
                return BuildMaterialCode.MATERIAL_NAME_RULE_NOT_TRUE;
            }
        }
        if (!StringUtil.isBlank(buildMaterialReq.getMaterialBrand())) {
            if (!buildMaterialReq.getMaterialBrand().matches(BuildMaterialValidation.MATERIAL_NAME_RULE)) {
                return BuildMaterialCode.MATERIALBRAND_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(buildMaterialReq.getMaterialModel())) {
            if (!buildMaterialReq.getMaterialModel().matches(BuildMaterialValidation.MATERIAL_NAME_RULE)) {
                return BuildMaterialCode.MATERIALMODEL_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(buildMaterialReq.getMaterialType())) {
            if (!buildMaterialReq.getMaterialType().matches(BuildMaterialValidation.MATERIAL_NAME_RULE)) {
                return BuildMaterialCode.MATERIALTYPE_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(buildMaterialReq.getStartTime())) {
            if (!buildMaterialReq.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(buildMaterialReq.getEndTime())) {
            if (!buildMaterialReq.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(buildMaterialReq.getStartTime(), buildMaterialReq.getEndTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }
        return null;

    }


    /**
     * <p>
     * 删除用户，可批量删除(旧的)
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    @RequiresPermissions(value = "materialManage-deleteBuildMaterialOld")
    @RequestMapping(value = "v1/deleteBuildMaterialOld", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> deleteBuildMaterialOld(@RequestBody BuildMaterialReq buildMaterialReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/material/v1/deleteBuildMaterialOld start========MaterialIds:=" + buildMaterialReq.getMaterialIds());
        }
        Response<Object> response = new Response<Object>();
        try {
            //如果MaterialIds不为空
            if (!"".equals(buildMaterialReq.getMaterialIds()) && null != buildMaterialReq.getMaterialIds()) {
                //获取userIds
                String[] materialIds = buildMaterialReq.getMaterialIds().split(",");
                for (String materialId : materialIds) {
                    //更新缓存前获取材料名称
                    HouseMaterial houseMaterial = buildMaterialService.selectByPrimaryKey(Long.valueOf(materialId));
                    //删除材料信息
                    int flag = buildMaterialService.deleteBuildMaterial(materialId);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("delet number of material=" + flag);
                    }
                    if (flag > 0) {
                        //更新缓存
                        MaterialUtil.materialNameList.remove(houseMaterial.getMaterialName());
                    }
                }
            } else {
                response.setCode(BuildMaterialCode.MATERIAL_IDS_ISNULL);
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("=========web/material/v1/deleteBuildMaterial error!=========", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/material/v1/deleteBuildMaterial end!=========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除用户，可批量删除
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    @RequiresPermissions(value = "materialManage-deleteBuildMaterial")
    @RequestMapping(value = "v1/deleteBuildMaterial", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> deleteBuildMaterial(@RequestBody BuildMaterialReq buildMaterialReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/material/v1/deleteBuildMaterial start========MaterialIds:=" + buildMaterialReq.getMaterialIds());
        }
        Response<Object> response = new Response<Object>();
        try {
            //如果MaterialIds不为空
            if (!"".equals(buildMaterialReq.getMaterialIds()) && null != buildMaterialReq.getMaterialIds()) {
                List<Long> ids = new ArrayList<>();
                //获取userIds
                String[] materialIds = buildMaterialReq.getMaterialIds().split(",");
                for (String materialId : materialIds) {
                    if (!StringUtil.isBlank(materialId)) {
                        ids.add(Long.valueOf(materialId.trim()));
                    } else {
                        response.setCode(BuildMaterialCode.MATERIAL_IDS_ISNULL);
                        return response;
                    }
                }
                int flag = 0;
                try {
                    flag = buildMaterialService.deleteBulkBuildMaterial(ids);
                } catch (Exception e1) {
                    response.setCode(StatusCode.FAILURE);
                    LOG.error("=========web/material/v1/deleteBulkBuildMaterial error!=========", e1);
                }
                if (flag > 0) {
                    LOG.info("======web/material/v1/Controller==>>>>>>deleteBuildMaterial end=======SUCCESS");
                } else if (flag == BuildMaterialConstant.FLAG_MATERIAL_NOT_EXIST) {
                    response.setCode(BuildMaterialCode.MATERIAL_HAS_BEEN_DELETE);
                } else {
                    response.setCode(BuildMaterialCode.MATERIAL_IDS_ISNULL);
                    return response;
                }
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("=========web/material/v1/deleteBulkBuildMaterial error!=========", e);
        }
        return response;
    }


    /**
     * <p>
     * 添加建材
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    @RequiresPermissions(value = "materialManage-addBuildMaterial")
    @RequestMapping(value = "v1/addBuildMaterial", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> addBuildMaterial(@RequestBody BuildMaterialAddUpdateReq buildMaterialAddUpdateReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/material/v1/addBuildMaterial start========BuildMaterialAddUpdateReq:=" + buildMaterialAddUpdateReq);
        }
        Response<Object> response = new Response<Object>();
        //添加建材信息
        //1.校验参数的合法性
        String code = checkBuildMaterialReqIslegal(buildMaterialAddUpdateReq, 1);
        //2.如果code为null说明是合法，插入数据库；否则不合法
        if (null != code && !"".equals(code)) {
            response.setCode(code);
            return response;
        }
        //执行新增操作
        try {
            //====================  1.新增建材表
            HouseMaterial houseMaterial = getHouseMaterial(buildMaterialAddUpdateReq, 1);
            buildMaterialService.insertSelective(houseMaterial);
            //======================2.新增自定义参数
            int flag = insertMaterialCustomParms(buildMaterialAddUpdateReq, 1, houseMaterial.getMaterialId());
            if (LOG.isDebugEnabled()) {
                LOG.debug("insert number of material=" + flag);
            }
            if (flag > 0) {
                MaterialUtil.materialNameList.add(houseMaterial.getMaterialName().trim());//新增内存
                LOG.error("=========web/material/v1/insertSelectiveCustomParm success!=========");
            } else {
                LOG.error("=========web/material/v1/insertSelectiveCustomParm false!=========");
            }

        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("=========web/material/v1/addBuildMaterial error!=========", e);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/material/v1/addBuildMaterial end!=========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询建材详情
     * </p>
     *
     * @param houseMaterial 建材对象
     *                      Author: zhangjian <br/>
     *                      Date: 2017/6/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "materialManage-findBuildMaterial")
    @RequestMapping(value = "v1/findBuildMaterial", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> findBuildMaterial(@RequestBody HouseMaterial houseMaterial) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/material/v1/findBuildMaterial start========findBuildMaterial:=" + houseMaterial);
        }
        Response<Object> response = new Response<Object>();
        if (null != houseMaterial && null != houseMaterial.getMaterialId() && 0L != houseMaterial.getMaterialId()) {
            /**
             * 第一种方法
             */
            BuildMaterialUpdate hMaterial = buildMaterialService.findBuildMaterial(houseMaterial);
            if (null == hMaterial) {
                response.setCode(BuildMaterialCode.MATERIAL_HAS_BEEN_DELETE);
                return response;
            }
            response.setData(hMaterial);
        } else {
            response.setCode(BuildMaterialCode.MATERIAL_IDS_ISNULL);
        }
        return response;
    }


    /**
     * <p>
     * 修改建材详情
     * </p>
     *
     * @param buildMaterialAddUpdateReq 新增与修改对象
     *                                  Author: zhangjian <br/>
     *                                  Date: 2017/6/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "materialManage-updateBuildMaterial")
    @RequestMapping(value = "v1/updateBuildMaterial", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> updateBuildMaterial(@RequestBody BuildMaterialAddUpdateReq buildMaterialAddUpdateReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/material/v1/updateBuildMaterial start========updateBuildMaterial:=" + buildMaterialAddUpdateReq);
        }
        Response<Object> response = new Response<Object>();
        //添加建材信息
        //1.校验参数的合法性
        String code = checkBuildMaterialReqIslegal(buildMaterialAddUpdateReq, 2);
        //2.如果code为null说明是合法，插入数据库；否则不合法
        if (null != code && !"".equals(code)) {
            response.setCode(code);
            return response;
        }
        //执行修改操作
        Long materialId = 0L;
        int flag = insertMaterialCustomParms(buildMaterialAddUpdateReq, 2, materialId);


        //  3、更新建材信息
        /**
         * 组合对象
         */
        HouseMaterial houseMaterial = getHouseMaterial(buildMaterialAddUpdateReq, 2);
        int isUpdateTrue = buildMaterialService.updateByPrimaryKey(houseMaterial);
        if (LOG.isDebugEnabled()) {
            LOG.debug("update number of material=" + isUpdateTrue);
        }
        //更新内存
        if (isUpdateTrue > 0) {
            MaterialUtil.materialNameList.remove(buildMaterialAddUpdateReq.getMaterialName());
            MaterialUtil.materialNameList.add(houseMaterial.getMaterialName());
        }


        if (LOG.isInfoEnabled()) {
            LOG.info("=========web/material/v1/addBuildMaterial end!=========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 插入建材自定义参数
     * </p>
     *
     * @param materialId                建材id
     * @param buildMaterialAddUpdateReq *@param operation  1 新增  2  修改
     *                                  Author: zhangjian <br/>
     *                                  Date: 2017/6/21 13:44
     * @return response
     */
    public int insertMaterialCustomParms(BuildMaterialAddUpdateReq buildMaterialAddUpdateReq, int operation, Long materialId) {
        int flag = -1;
        if (operation == BuildMaterialConstant.ACTION_TYPE) {
            // 1、先删除自定义参数表信息
            materialId = new Long(buildMaterialAddUpdateReq.getMaterialId()).longValue();
            buildMaterialService.deleteByMaterialId(materialId);
        }
        /**
         * =======================================自定义参数============开始============================================
         * 处理一下数据
         */
        //   2、插入新的自定义参数信息
        HouseMaterialParm houseMaterialParm = new HouseMaterialParm();
        houseMaterialParm.setMaterialId(materialId);
        String customParmNamesStr = buildMaterialAddUpdateReq.getParmNames();
        String customParmContentsStr = buildMaterialAddUpdateReq.getParmContents();
        /**
         * 重新定义无空格集合组装集合
         */
        List<String> customParanNamesNewList = new ArrayList<>();
        List<String> customParanContentsNewList = new ArrayList<>();

        if (!StringUtil.isBlank(customParmNamesStr)) {
            String[] customParmNames = customParmNamesStr.trim().split(",");

            for (int i = 0; i < customParmNames.length; i++) {
                if (!StringUtil.isBlank(customParmNames[i])) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("自定义参数名称=" + customParmNames[i]);
                    }
                    customParanNamesNewList.add(customParmNames[i].trim());
                }
            }

        }

        if (!StringUtil.isBlank(customParmContentsStr)) {
            String[] parmContents = customParmContentsStr.trim().split(",");
            for (int i = 0; i < parmContents.length; i++) {
                if (!StringUtil.isBlank(parmContents[i])) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("自定义参数内容=" + parmContents[i]);
                    }
                    customParanContentsNewList.add(parmContents[i].trim());
                }
            }
        }


        /**
         * 组装数据
         */
        for (int i = 0; i < customParanNamesNewList.size(); i++) {
            houseMaterialParm.setParmName(customParanNamesNewList.get(i));
            houseMaterialParm.setParmContent(customParanContentsNewList.get(i));
            /**
             * 插入自定义参数
             */
            flag = buildMaterialService.insertSelectiveCustomParm(houseMaterialParm);
            if (LOG.isDebugEnabled()) {
                LOG.debug("插入自定义参数的数量" + flag);
            }
        }
        return flag;
/**
 * =======================================自定义参数============结束============================================
 */
    }


    /**
     * <p>
     * 上传图片
     * </p>
     *
     * @param pictureUrl 文件流
     *                   Author: zhangjian <br/>
     *                   Date: 2017/6/21 13:44
     * @return response
     */
    @RequiresPermissions(value = "materialManage-uploadBuildMaterialPicture")
    @RequestMapping(value = "v1/uploadBuildMaterialPicture", method = RequestMethod.POST)
    public Response<BuildMaterialPicInfo> uploadBuildMaterialPicture(MultipartFile pictureUrl) {
        if (LOG.isInfoEnabled()) {
            LOG.info("==========  user/web/v1/uploadBuildMaterialPicture====start:");
        }
        Response<BuildMaterialPicInfo> response = new Response<>();

        //头像文件判空
        if (null == pictureUrl) {
            response.setCode(BuildMaterialCode.MATERIAL_PICTURE_EMPTY);
            return response;
        }
        //文件类型限制
        String[] allowedType = {"image/bmp", "image/jpeg", "image/png", "image/jpg"};
        boolean allowed = Arrays.asList(allowedType).contains(pictureUrl.getContentType());
        if (!allowed) {
            response.setCode(BuildMaterialCode.MATERIAL_PICTURE_FORMAT_ERROR);
            return response;
        }
        // 图片大小限制
        if (pictureUrl.getSize() > BuildMaterialValidation.MATERIAL_PIC_LENGTH_MAX_RULE) {
            response.setCode(BuildMaterialCode.MATERIAL_PICTURE_SIZE_OVER_LIMITE);
            return response;
        }
        try {
            //上传文件(url)
            String newFileName = FileUtil.uploadFile(pictureUrl, BuildMaterialConstant.FILE_DIR_PATH + "buildMaterialPic/");
            if (LOG.isDebugEnabled()) {
                LOG.debug("上传图片名称====" + newFileName);
            }
            String imgUrl = "buildMaterialPic/" + newFileName;
            if (LOG.isDebugEnabled()) {
                LOG.debug("上传图片url====" + imgUrl);
            }
            //创建图片对象
            Picture picture = new Picture();
            if (!StringUtil.isBlank(newFileName) && newFileName.length() > BuildMaterialCode.PIC_NAME_SIZE) {
                response.setCode(BuildMaterialCode.PICTURE_NAME_MAX);
                return response;
            }
            picture.setPictureName(newFileName);
            picture.setPictureUrl(imgUrl);
            picture.setCreateTime(new Date());
            //将建材图片插入数据库  增加图片自动增加pictureId(主键)
            buildMaterialService.addBuildMaterilPicture(picture);
            if (LOG.isDebugEnabled()) {
                LOG.debug("上传图片成功返回主键===" + picture.getPictureId());
            }
            //创建返回实体
            BuildMaterialPicInfo buildMaterialPicInfo = new BuildMaterialPicInfo();
            buildMaterialPicInfo.setPictureId(picture.getPictureId());
            buildMaterialPicInfo.setPictureUrl(ConfigManager.read(ConfigName.FILE_SERVER) + imgUrl);
            if (LOG.isDebugEnabled()) {
                LOG.debug("上传图片成功返回前端全局url===" + ConfigManager.read(ConfigName.FILE_SERVER) + imgUrl);
            }
            //操作成功
            response.setCode(StatusCode.SUCCESS);
            response.setData(buildMaterialPicInfo);

        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
        }
        return response;
    }


    /**
     * <p>
     * 建材校验新增和修改参数是否合法
     * </p>
     *
     * @param buildMaterialAddUpdateReq 新增与修改参数对象
     * @param operation                 1:新增   2：修改
     *                                  Author: zhangjian <br/>
     *                                  Date: 2017/6/21 13:44
     * @return response
     */
    public String checkBuildMaterialReqIslegal(BuildMaterialAddUpdateReq buildMaterialAddUpdateReq, int operation) {
        String code = "";
        HouseMaterial houseMaterial = new HouseMaterial();
        //校验建材id是否为空（这里暂时不用）
        if (operation == BuildMaterialConstant.ACTION_TYPE) {
            String materialId = buildMaterialAddUpdateReq.getMaterialId();
            if (StringUtil.isBlank(materialId)) {
                return code = BuildMaterialCode.MATERIAL_IDS_ISNULL;
            } else {
                houseMaterial.setMaterialId(new Long(materialId.trim()));
            }

        }
        //校验建材类型是否为空，不为空表示数据库存在
        if (StringUtil.isBlank(buildMaterialAddUpdateReq.getMaterialType())) {
            return code = BuildMaterialCode.MATERIAL_TYPE_ISNULL;
        }

        //校验建材名称是否为空
        String materialName = buildMaterialAddUpdateReq.getMaterialName();
        if (StringUtil.isBlank(materialName)) {
            return code = BuildMaterialCode.MATERIAL_NAME_ISNULL;
        }
        materialName = materialName.trim();//去空格
        //校验建材名称格式是否合法
        if (!materialName.matches(BuildMaterialValidation.MATERIAL_NAME_RULE)) {
            return code = BuildMaterialCode.MATERIAL_NAME_RULE_NOT_TRUE;
        }
        houseMaterial.setMaterialName(materialName);
        int count = buildMaterialService.checkBuildMaterialIsExist(houseMaterial);
        if (count > 0) {
            return code = BuildMaterialCode.MATERIAL_NAME_EXIST;
        }
        String materialBrand = buildMaterialAddUpdateReq.getMaterialBrand();
        if (!StringUtil.isBlank(materialBrand)) {
            materialBrand = materialBrand.trim();//前后去空格
            int materialBrandLength = materialBrand.length();
            if (materialBrandLength > BuildMaterialConstant.LENGTH_VALUE) {
                return code = BuildMaterialCode.MATERIAL_BRAND_DESCRIP_WORD_NUM_MORE_MOST;
            }
        }

        //校验型号
        String materialModel = buildMaterialAddUpdateReq.getMaterialModel();
        if (!StringUtil.isBlank(materialModel)) {
            materialModel = materialModel.trim();//前后去空格
            int materialModelLength = materialModel.length();
            if (materialModelLength > BuildMaterialConstant.LENGTH_VALUE) {
                return code = BuildMaterialCode.MATERIAL_MODEL_DESCRIP_WORD_NUM_MORE_MOST;
            }
        }
        //校验规格
        String materialSpecification = buildMaterialAddUpdateReq.getMaterialSpecification();
        if (!StringUtil.isBlank(materialSpecification)) {
            materialSpecification = materialSpecification.trim();//前后去空格
            int materialSpecificationLength = materialSpecification.length();
            if (materialSpecificationLength > BuildMaterialConstant.LENGTH_VALUE) {
                return code = BuildMaterialCode.MATERIAL_SPECIFICATION_DESCRIP_WORD_NUM_MORE_MOST;
            }
        }
        //校验产地
        String materialOrigin = buildMaterialAddUpdateReq.getMaterialOrigin();
        if (!StringUtil.isBlank(materialOrigin)) {
            materialOrigin = materialOrigin.trim();//前后去空格
            int materialOriginLength = materialOrigin.length();
            if (materialOriginLength > BuildMaterialConstant.LENGTH_VALUE) {
                return code = BuildMaterialCode.MATERIAL_ORIGIN_DESCRIP_WORD_NUM_MORE_MOST;
            }
        }

        //校验生产日期
        String materialProduceDate = buildMaterialAddUpdateReq.getMaterialProduceDate();
        if (!StringUtil.isBlank(materialProduceDate)) {
            materialProduceDate = materialProduceDate.trim();//前后去空格
            Boolean flag = materialProduceDate.matches(BuildMaterialValidation.MATERIAL_PRODUCE_DATE_RULE);
            if (flag == false) {
                return code = BuildMaterialCode.MATERIAL_PRODUCE_DATE_RULE_ILLEGAL;
            }
        }

        //校验截止日期
        String materialWarrantyDate = buildMaterialAddUpdateReq.getMaterialWarrantyDate();
        if (!StringUtil.isBlank(materialWarrantyDate)) {
            materialWarrantyDate = materialWarrantyDate.trim();
            Boolean flag = materialWarrantyDate.matches(BuildMaterialValidation.MATERIAL_WARRANTY_DATE_RULE_RULE);
            if (flag == false) {
                return code = BuildMaterialCode.MATERIAL_WARRANTY_DATE_RULE_ILLEGAL;
            }
        }
        /**
         * ======================================开始校验自定义参数===========================
         */
        String customParanNames = buildMaterialAddUpdateReq.getParmNames();
        String customParanContents = buildMaterialAddUpdateReq.getParmContents();

        //新增时
        //第一种情况：客户没自定义参数是null
        if (StringUtil.isBlank(customParanNames) && StringUtil.isBlank(customParanContents)) {
            LOG.info("=========web/material/v1/addBuildMaterial====客户没有打开input输入框=====customParanNames==>" + customParanNames);
            LOG.info("=========web/material/v1/addBuildMaterial====客户没有打开input输入框=====customParanContents==>" + customParanContents);
            return code = "";//结束校验
        } else if (StringUtil.isBlank(customParanNames) == true && StringUtil.isBlank(customParanContents) == false) {
            return code = "xxxxx";
        } else if (StringUtil.isBlank(customParanNames) == false && StringUtil.isBlank(customParanContents) == true) {
            return code = "xxxxx";
        } else {
            customParanNames = customParanNames.trim();
            customParanContents = customParanContents.trim();
        }


        /**
         * 校验思路：
         * 1.前端自定义参数名称前后去空格
         * 2.截取变成数组，便利数组得到每一个参数名称，再去空格，如果空，返回参数名称为空
         * 3.如果不为空，校验合法性
         * 4.如果不为空，校验是否重复
         * 5.当新增时只和页面参数校验，不查数据库；
         * 6.当修改的时候先校验页面，再校验自定义参数表
         *
         *
         */
        //当都不为空的时候
        String[] customParmNamesArray = customParanNames.split(",");
        String[] customPrmContentArray = customParanContents.split(",");
        //重复定义集合接收
        //第二种情况：客户自定义参数时没填是为""
        List<String> customParanNamesNewList = new ArrayList<>();
        List<String> customParanContentsNewList = new ArrayList<>();


        //==============================校验自定义名称与返回新的无空格集合===========开始============================================
        for (int i = 0; i < customParmNamesArray.length; i++) {
            String paraName = customParmNamesArray[i];//获得值后判断是否为空
            //not null
            if (StringUtil.isBlank(paraName)) {
                return code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_NAME_ISNULL;
            }
            //not null
            if (!StringUtil.isBlank(paraName)) {
                paraName = paraName.trim();
                if (!paraName.matches(BuildMaterialValidation.MATERIAL_CUSTOM_PARAM_NAME_RULE)) {
                    return code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_NAME_ILLEGAL;
                }
            }
            /**
             * 集合接收名称集合
             *
             */
            customParanNamesNewList.add(paraName);
        }

        /**
         * 校验自定义参数名称是否重复(可以在次优化代码)
         * 1.自身校验   2.页面原有参数校验
         */

        code = myselfCheckMaterialCustomParmNamesIsRepeat(customParanNamesNewList);
        if (null != code && !"".equals(code)) {
            return code;
        }
        for (int i = 0; i < customParanNamesNewList.size(); i++) {
            code = checkMaterialParamNameIsExist(customParanNamesNewList.get(i), materialOldParamNameInit());
            if (null != code && !"".equals(code)) {
                return code;
            }
        }
        //==============================校验自定义名称与返回新的无空格集合=========结束============================================
        //==============================校验自定义内容与返回新的无空格集合============================================
        for (int i = 0; i < customPrmContentArray.length; i++) {
            String parmContent = customPrmContentArray[i];//获得值后判断是否为空
            //not null
            if (StringUtil.isBlank(parmContent)) {
                return code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_CONTENT_ISNULL;
            }
            //not null
            if (!StringUtil.isBlank(parmContent)) {
                parmContent = parmContent.trim();
                if (parmContent.length() > BuildMaterialValidation.MATERIAL_CUSTOM_PARAM_CONTENT_RULE) {
                    return code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_CONTENT_WORD_NUM_MORE_MOST;
                }
            }
            /**
             * 接收内容
             *
             */
            customParanContentsNewList.add(parmContent);
        }
        return code;
    }

    /**
     * <p>
     * 自定义参数自身校验
     * </p>
     *
     * @param customParanNamesList Author: zhangjian <br/>
     *                             Date: 2017/6/21 13:44
     * @return response
     */
    public String myselfCheckMaterialCustomParmNamesIsRepeat(List<String> customParanNamesList) {
        Set<String> set = new HashSet<String>();
        String code = "";
        if (null == customParanNamesList) {
            return code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_NAME_ISNULL;
        }
        for (String string : customParanNamesList) {
            set.add(string);
        }
        if (set.size() != customParanNamesList.size()) {
            code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_NAME_EXIST;
        }
        return code;
    }


    /**
     * <p>
     * 校验自定义参数是否存在
     * </p>
     *
     * @param curName
     * @param materialOldParams Author: zhangjian <br/>
     *                          Date: 2017/6/21 13:44
     * @return response
     */
    public String checkMaterialParamNameIsExist(String curName, List<String> materialOldParams) {
        String code = "";
        if (null != materialOldParams && materialOldParams.size() > 0) {
            for (int i = 0; i < materialOldParams.size(); i++) {
                if (materialOldParams.get(i).equals(curName)) {
                    return code = BuildMaterialCode.MATERIAL_CUSTOM_PARAM_NAME_EXIST;
                }
            }
        }
        return code;
    }


    /**
     * <p>
     * 初始化建材参数列表
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/21 13:44
     *
     * @return response
     */
    public static List<String> materialOldParamNameInit() {
        List<String> materialOldParamNameInit = new ArrayList<String>();
        materialOldParamNameInit.add("类别");
        materialOldParamNameInit.add("材料名称");
        materialOldParamNameInit.add("图片");
        materialOldParamNameInit.add("品牌");
        materialOldParamNameInit.add("型号");
        materialOldParamNameInit.add("规格");
        materialOldParamNameInit.add("产地");
        materialOldParamNameInit.add("生产日期");
        materialOldParamNameInit.add("截止日期");
        return materialOldParamNameInit;
    }


    /**
     * <p>
     * 组装对象HouseMaterial
     * </p>
     *
     * @param object
     * @param operate Author: zhangjian <br/>
     *                Date: 2017/6/21 13:44
     * @return response
     */
    public static HouseMaterial getHouseMaterial(Object object, int operate) {
        HouseMaterial houseMaterial = new HouseMaterial();
        if (null == object) {
            return null;
        }
        if (object instanceof BuildMaterialAddUpdateReq) {
            BuildMaterialAddUpdateReq buildMaterialAddUpdateReq = (BuildMaterialAddUpdateReq) object;
            if (operate == BuildMaterialConstant.ACTION_TYPE) {
                houseMaterial.setMaterialId(new Long(buildMaterialAddUpdateReq.getMaterialId()).longValue());
            }
            if (operate == 1) {
                houseMaterial.setCreateTime(new Date());
            }
            houseMaterial.setMaterialName(buildMaterialAddUpdateReq.getMaterialName());
            houseMaterial.setMaterialType(buildMaterialAddUpdateReq.getMaterialType());
            houseMaterial.setMaterialPic(buildMaterialAddUpdateReq.getMaterialPicId());
            houseMaterial.setMaterialBrand(buildMaterialAddUpdateReq.getMaterialBrand());
            houseMaterial.setMaterialModel(buildMaterialAddUpdateReq.getMaterialModel());
            houseMaterial.setMaterialSpecification(buildMaterialAddUpdateReq.getMaterialSpecification());
            houseMaterial.setMaterialOrigin(buildMaterialAddUpdateReq.getMaterialOrigin());

            try {
                String materialProductDate = buildMaterialAddUpdateReq.getMaterialProduceDate();
                if (!StringUtil.isBlank(materialProductDate)) {
                    materialProductDate = materialProductDate.trim();
                    houseMaterial.setMaterialProduceDate(new SimpleDateFormat("yyyy-MM-dd").parse(materialProductDate));
                }
                String materialWarrantyDate = buildMaterialAddUpdateReq.getMaterialWarrantyDate();
                if (!StringUtil.isBlank(materialWarrantyDate)) {
                    materialWarrantyDate = materialWarrantyDate.trim();
                    houseMaterial.setMaterialWarrantyDate(new SimpleDateFormat("yyyy-MM-dd").parse(materialWarrantyDate));
                }
            } catch (ParseException e) {
                LOG.error("========web/user/v1/updateMaterial error========", e);
            }
        }
        return houseMaterial;
    }

}

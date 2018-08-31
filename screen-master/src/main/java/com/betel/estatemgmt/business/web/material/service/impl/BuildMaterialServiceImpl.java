package com.betel.estatemgmt.business.web.material.service.impl;


import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.material.controller.util.MaterialUtil;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialInfo;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialReq;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialUpdate;
import com.betel.estatemgmt.business.web.material.service.BuildMaterialService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.mapper.house.HouseMaterialMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMaterialParmMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMaterialTypeMapper;
import com.betel.estatemgmt.common.mapper.house.HouseTypeMaterialMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.house.HouseMaterialParm;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 关于家装建材操作的接口实现类
 * </p>
 * ClassName: BuildMaterialServiceImpl <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class BuildMaterialServiceImpl implements BuildMaterialService {
    private static final Logger LOG = LoggerFactory.getLogger(BuildMaterialServiceImpl.class);

    @Autowired
    private HouseMaterialTypeMapper houseMaterialTypeMapper;
    @Autowired
    private HouseMaterialMapper houseMaterialMapper;
    @Autowired
    private HouseMaterialParmMapper houseMaterialParmMapper;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private HouseTypeMaterialMapper houseTypeMaterialMapper;

    @Override
    public List<Map<String, Object>> findBuildMaterialType() {
        return houseMaterialTypeMapper.findBuildMaterialType();
    }

    @Override
    public List<BuildMaterialInfo> findAllBuildMaterial(Paging<BuildMaterialInfo> pager, BuildMaterialReq buildMaterialReq) {
        return houseMaterialMapper.findAllBuildMaterial(pager.getRowBounds(), buildMaterialReq);
    }

    @Override
    public int deleteBuildMaterial(String materialId) {
        int isDelBMaterial = -1;
        int isDelParam = -1;
        int isDelPic = -1;
        if (!StringUtil.isEmpty(materialId)) {
            Long id = Long.valueOf(materialId);
            // 第一步删除自定参数表
            isDelParam = houseMaterialParmMapper.deleteByMaterialId(id);
            if (isDelParam > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========deleCustomParam===SUCCESS");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========deleCustomParam===false");
                }
            }

            //第二步删除图片表
            //查询出图片id
            HouseMaterial buildMaterial = houseMaterialMapper.selectByPrimaryKey(id);
            if (LOG.isDebugEnabled()) {
                LOG.debug("============BuildMaterial=" + buildMaterial);
            }
            if (null != buildMaterial) {
                isDelPic = pictureMapper.deleteByPrimaryKey(buildMaterial.getMaterialPic());
            }
            if (isDelPic > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========delePic===SUCCESS");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========delePic===false");
                }
            }
            //第三步 删除建材户型关系表（根据建材id关联）
            houseTypeMaterialMapper.deleteHouseTypeMaterialsByMaterialId(id);
            //第四步删除建材主表
            isDelBMaterial = houseMaterialMapper.deleteByPrimaryKey(id);
            if (isDelBMaterial > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========deleBMaterial===SUCCESS");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========deleBMaterial===false");
                }
            }

            return isDelBMaterial;
        }
        return isDelBMaterial;
    }

    @Override
    public int deleteBulkBuildMaterial(List<Long> materialIds) throws Exception {
        int isDelBMaterial = -1;
        int isDelParam = -1;
        int isDelPic = -1;
        //执行删除之前必须要判断材料是否存在，如果存在组装图片id集合，若不存在直接返回code
        List<HouseMaterial> houseMaterials = houseMaterialMapper.selectBulkByPrimaryKeys(materialIds);  //1.查看图片id集合
        if (LOG.isDebugEnabled()) {
            LOG.debug("========建材对象=" + houseMaterials);
        }
        List<String> picIds = new ArrayList<>();
        if (null != houseMaterials && houseMaterials.size() > 0) {
            for (HouseMaterial hm : houseMaterials) {
                if (null == hm.getMaterialPic()) {
                    picIds.add("");
                } else {
                    picIds.add(hm.getMaterialPic());
                }

            }
        } else {
            return -2;
        }
        //==================第一步：删除自定参数表====================
        isDelParam = houseMaterialParmMapper.deleteBulkByMaterialIds(materialIds);
        if (isDelParam > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("========第一步：删除自定参数表===>>>>>>SUCCESS");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("========第一步：删除自定参数表===>>>>>>false");
            }
        }

        //=================第二步：删除图片表=======================
//        isDelPic = pictureMapper.deleteBulkByPrimaryKeys(picIds);  //2.执行删除图片
//        if (isDelPic > 0) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("========第二步：删除图片表===>>>>>>SUCCESS");
//            }
//        } else {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("========第二步：删除图片表===>>>>>>false");
//            }
//        }

        //================第三步：删除建材户型关系表（根据建材id关联）===
        int isDelHtm = houseTypeMaterialMapper.deleteBulkHouseTypeMaterialsByMaterialIds(materialIds);
        if (isDelPic > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("========第三步：删除建材户型关系表（根据建材id关联===>>>>>SUCCESS");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("========第三步：删除建材户型关系表（根据建材id关联===>>>>>false");
            }
        }
        //================第四步删除建材主表==============================
        isDelBMaterial = houseMaterialMapper.deleteBulkByPrimaryKeys(materialIds);
        if (isDelBMaterial > 0) {
            //更新缓存
            for (HouseMaterial hm : houseMaterials) {
                MaterialUtil.materialNameList.remove(hm.getMaterialName());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========第四步删除建材主表更新缓存===>>>>>>SUCCESS");
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("========第四步删除建材主表===>>>>>>SUCCESS");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("========第四步删除建材主表===>>>>>false");
            }
        }
        return isDelBMaterial;
    }


    @Override
    public List<HouseMaterial> findBuildMaterialNames() {
        return houseMaterialMapper.findBuildMaterialNames();
    }

    //    public int checkBuildMaterialIsExist(String materialName){
//        return houseMaterialMapper.checkBuildMaterialIsExist(materialName);
//    }
    @Override
    public int checkBuildMaterialIsExist(HouseMaterial houseMaterial) {
        return houseMaterialMapper.checkBuildMaterialIsExist(houseMaterial);
    }

    @Override
    public int selectMaterialCustomParmCount(String materialId, String curName, Long parmId) {
        Map<String, Object> condition = new HashMap<>();
        if (!StringUtil.isEmpty(materialId.trim())) {
            Integer.valueOf(materialId.trim());
            condition.put("materialId", materialId);
        }
        condition.put("parmName", curName);
        condition.put("parmId", parmId);
        return houseMaterialParmMapper.selectMaterialCustomParmCount(condition);
    }

    @Override
    public int insertSelective(HouseMaterial houseMaterial) {
        //先插入建材表
        int materialId = houseMaterialMapper.insertSelective(houseMaterial);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========insertSelectivHouseMateriale===SUCCESS" + materialId);
        }
        return materialId;
    }

    @Override
    public int insert(HouseMaterial record) {
        return houseMaterialMapper.insert(record);
    }

    @Override
    public int bulkImportHouseMaterial(List<HouseMaterial> houseMaterials) {
        return houseMaterialMapper.bulkImportHouseMaterial(houseMaterials);
    }

    @Override
    public int insertSelectiveCustomParm(HouseMaterialParm houseMaterialParm) {
        int flag = -1;
        flag = houseMaterialParmMapper.insertSelective(houseMaterialParm);
        if (flag > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("========insertHouseSelectivMaterialParm===SUCCESS" + flag);
            }
        }

        return flag;
    }

    @Override
    public Long addBuildMaterilPicture(Picture picture) {
        return pictureMapper.insertSelective(picture);
    }

    @Override
    public int selectMaterialTypeNameIsExcel(String typeName) {
        return houseMaterialTypeMapper.selectMaterialTypeNameIsExcel(typeName);
    }

    @Override
    public BuildMaterialUpdate findBuildMaterial(HouseMaterial record) {
        BuildMaterialUpdate buildMaterialUpdate = new BuildMaterialUpdate();
        //先查询建材信息
        HouseMaterial houseMaterial = houseMaterialMapper.selectByPrimaryKey(record.getMaterialId());
        if (null == houseMaterial) {
            return null;
        }
        /**
         * 组合修改对象参数信息
         *这里是可以优化的
         */
        buildMaterialUpdate.setMaterialId(houseMaterial.getMaterialId());
        if (null == houseMaterial.getMaterialType()) {
            buildMaterialUpdate.setMaterialType("");
        } else {
            buildMaterialUpdate.setMaterialType(houseMaterial.getMaterialType());
        }
        if (null == houseMaterial.getMaterialName()) {
            buildMaterialUpdate.setMaterialName("");
        } else {
            buildMaterialUpdate.setMaterialName(houseMaterial.getMaterialName());
        }

        if (null == houseMaterial.getMaterialBrand()) {
            buildMaterialUpdate.setMaterialBrand("");
        } else {
            buildMaterialUpdate.setMaterialBrand(houseMaterial.getMaterialBrand());
        }

        if (null == houseMaterial.getMaterialModel()) {
            buildMaterialUpdate.setMaterialModel("");
        } else {
            buildMaterialUpdate.setMaterialModel(houseMaterial.getMaterialModel());
        }

        if (null == houseMaterial.getMaterialSpecification()) {
            buildMaterialUpdate.setMaterialSpecification("");
        } else {
            buildMaterialUpdate.setMaterialSpecification(houseMaterial.getMaterialSpecification());
        }

        if (null == houseMaterial.getMaterialOrigin()) {
            buildMaterialUpdate.setMaterialOrigin("");
        } else {
            buildMaterialUpdate.setMaterialOrigin(houseMaterial.getMaterialOrigin());
        }

        try {
            if (null != houseMaterial.getMaterialProduceDate()) {
                buildMaterialUpdate.setMaterialProduceDate(new SimpleDateFormat("yyyy-MM-dd").format(houseMaterial.getMaterialProduceDate()));
            } else {
                buildMaterialUpdate.setMaterialProduceDate("");
            }
            if (null != houseMaterial.getMaterialWarrantyDate()) {
                buildMaterialUpdate.setMaterialWarrantyDate(new SimpleDateFormat("yyyy-MM-dd").format(houseMaterial.getMaterialWarrantyDate()));
            } else {
                buildMaterialUpdate.setMaterialWarrantyDate("");
            }

        } catch (Exception e) {
            LOG.error("========web/user/v1/updateMaterial error========", e);
        }
        String materialPicId = houseMaterial.getMaterialPic();
        /**
         * 再查询图片信息
         *
         */
        if (null != materialPicId && !"".equals(materialPicId)) {
            Picture picture = pictureMapper.selectByPrimaryKey(materialPicId);
            if (null != picture) {
                String picUrl = picture.getPictureUrl();
                String materialFullPicUrl = ConfigManager.read(ConfigName.FILE_SERVER) + picUrl;
                buildMaterialUpdate.setMaterialPicId(picture.getPictureId());
                buildMaterialUpdate.setMaterialPicUrl(materialFullPicUrl);
            } else {
                buildMaterialUpdate.setMaterialPicId("");
                buildMaterialUpdate.setMaterialPicUrl("");
            }
        } else {
            buildMaterialUpdate.setMaterialPicId("");
            buildMaterialUpdate.setMaterialPicUrl("");
        }
        /**
         * 再查询自定义参数信息
         *
         */
        List<HouseMaterialParm> houseMaterialParms = houseMaterialParmMapper.finAllmaterialParmsBymaterialsId(record.getMaterialId());
        buildMaterialUpdate.setHouseMaterialParms(houseMaterialParms);
        return buildMaterialUpdate;
    }

    @Override
    public int deleteByMaterialId(Long materialId) {
        return houseMaterialParmMapper.deleteByMaterialId(materialId);
    }

    @Override
    public int updateByPrimaryKey(HouseMaterial record) {

        return houseMaterialMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<String> findMaterialTypeNameList() {
        return houseMaterialTypeMapper.findMaterialTypeNameList();
    }

    /**
     * 查询所有建材名称
     *
     * @return
     */
    @Override
    public List<String> findMaterialNameList() {
        return houseMaterialMapper.findMaterialNameList();
    }

    @Override
    public HouseMaterial selectByPrimaryKey(Long materialId) {
        return houseMaterialMapper.selectByPrimaryKey(materialId);
    }

   /* @Override
    public List<Map<String, Object>> goToUpdateMaterialPage(Map<String, Object> condition) {
        return houseMaterialMapper.goToUpdateMaterialPage(condition);
    }*/
}

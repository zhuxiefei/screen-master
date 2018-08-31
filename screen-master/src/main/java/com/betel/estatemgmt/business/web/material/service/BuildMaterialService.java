package com.betel.estatemgmt.business.web.material.service;

import com.betel.estatemgmt.business.web.material.model.BuildMaterialInfo;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialReq;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialUpdate;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.house.HouseMaterialParm;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 关于家装建材操作的接口
 * </p>
 * ClassName: BuildMaterialService <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public interface BuildMaterialService {
    /**
     * 查询所有建材类型名称集合
     * @return
     */
    List<Map<String,Object>> findBuildMaterialType();
    /**
     * 分页查询建材列表
     * @param buildMaterialReq  关键字，材料名称，页码，每页几条
     * @return
     */
    List<BuildMaterialInfo> findAllBuildMaterial(Paging<BuildMaterialInfo> pager, BuildMaterialReq buildMaterialReq);

    /**
     * 删除支持家装建材（批量删除）
     * @param materialId
     */
    int deleteBuildMaterial(String materialId);

    /**
     * 批量删除家装建材（批量删除）
     * @param materialIds
     * @return
     */
    int deleteBulkBuildMaterial(List<Long> materialIds) throws Exception;


    /**
     * 查询所有的建材名称集合
     * @return
     */
    List<HouseMaterial> findBuildMaterialNames();

    /**
     * 检测建材名称是否重复
     * @param houseMaterial
     * @return
     */
//    int checkBuildMaterialIsExist(String materialName);
    int checkBuildMaterialIsExist(HouseMaterial houseMaterial);

    /**
     * 查询自定义参数数量
     * @param materialId
     * @param curName
     * @return
     */
    int selectMaterialCustomParmCount(String materialId, String curName, Long parmId);

    /**
     * 插入建材表数据
     * @param houseMaterial
     * @return
     */
    int insertSelective(HouseMaterial houseMaterial);

    /**
     * 插入导入数据
     * @param record
     * @return
     */
    int insert(HouseMaterial record);

    /**
     * 批量导入
     * @param houseMaterials
     * @return
     */
    int bulkImportHouseMaterial(List<HouseMaterial> houseMaterials);


    int insertSelectiveCustomParm(HouseMaterialParm houseMaterialParm);

    /**
     * 添加图片
     * Author:Zhang Li
     * Date: 2017/5/19 15:26
     * @return 结果
     */

    Long addBuildMaterilPicture(Picture picture);

    /**
     * 查询类型名称是否存在
     * @param typeName
     * @return
     */
    int selectMaterialTypeNameIsExcel(String typeName);

    /**
     * 修改查询信息
     * @param record
     * @return
     */
    BuildMaterialUpdate findBuildMaterial(HouseMaterial record);

    /**
     *  建材id删除自定义参数表信息
     * @param materialId
     * @return
     */
    int deleteByMaterialId(Long materialId);

    /**
     * 更新建材信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(HouseMaterial record);

    /**
     * 查询所有建材类型名称
     * @return
     */
    List<String> findMaterialTypeNameList();

    /**
     * 查询所有建材名称
     * @return
     */
    List<String> findMaterialNameList();

    /**
     * 查询建材
     * @param materialId
     * @return
     */
    HouseMaterial selectByPrimaryKey(Long materialId) ;

    /**
     * 查询建材详情
     * @param condition
     * @return
     */
   /* List<Map<String,Object>>  goToUpdateMaterialPage(Map<String,Object> condition);*/

}

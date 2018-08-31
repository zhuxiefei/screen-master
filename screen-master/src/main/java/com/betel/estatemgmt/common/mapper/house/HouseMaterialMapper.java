package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.Materials;
import com.betel.estatemgmt.business.userapp.house.model.Page;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialInfo;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialReq;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface HouseMaterialMapper {
    int deleteByPrimaryKey(Long materialId);

    /**
     * 批量删除
     * @param materialIds
     * @return
     */
    int deleteBulkByPrimaryKeys(List<Long> materialIds);

    int insert(HouseMaterial record);

    /**
     * 批量插入建材信息
     * @param houseMaterials
     * @return
     */
    int bulkImportHouseMaterial(List<HouseMaterial> houseMaterials);

    int insertSelective(HouseMaterial houseMaterial);


    HouseMaterial selectByPrimaryKey(Long materialId);

    /**
     * 批量查询建材
     * @param materialIds
     * @return
     */
    List<HouseMaterial> selectBulkByPrimaryKeys(List<Long> materialIds);

    int updateByPrimaryKeySelective(HouseMaterial record);

    int updateByPrimaryKey(HouseMaterial record);
    /**
     * <p>
     *查询房屋材料列表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/30 10:32
     *return response
     */
    List<Materials> findAllMaterialByHouseId(RowBounds rowBounds, Page page);

    Materials findMaterialBymaterialId(Long materialId);



    /**
     * 分页查询建材列表
     * @param buildMaterialReq  关键字，材料名称，页码，每页几条
     * @return
     */
    List<BuildMaterialInfo> findAllBuildMaterial(RowBounds rowBounds, BuildMaterialReq buildMaterialReq);

    /**
     * 家装建材名称集合（校验名称是否存在使用）
     * @return
     */
    List<HouseMaterial> findBuildMaterialNames();

    /**
     * 判断建材名称查询是否重复（校验名称是否存在使用）
     * @param houseMaterial
     * @return
     */
//    int checkBuildMaterialIsExist(String materialName);
    int checkBuildMaterialIsExist(HouseMaterial houseMaterial);

    /**
     * 修改查询信息
     * @param record
     * @return
     */
    List<Map<String,Object>> goToUpdateMaterialPage(HouseMaterial record);


    /**
     * 查询所有建材名称
     * @return
     */
    List<String> findMaterialNameList();

    /**
     * 查询建材详情
     * @param condition
     * @return
     */
    List<Map<String,Object>>  goToUpdateMaterialPage(Map<String, Object> condition);

}
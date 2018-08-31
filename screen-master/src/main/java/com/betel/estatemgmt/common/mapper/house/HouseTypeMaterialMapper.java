package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.housetype.model.TypeMaterialInfo;
import com.betel.estatemgmt.common.model.house.HouseTypeMaterial;

import java.util.List;

public interface HouseTypeMaterialMapper {


    /**
     * <p>
     * 单个或批量添加户型建材
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 15:48
     *
     * @param al 功能区域ID和建材ID集合
     */
    void insertTypeMaterials(List<HouseTypeMaterial> al);

    /**
     * <p>
     * 根据功能区域ID,和建材ID查询是否已添加
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 15:18
     */
    TypeMaterialInfo selectMaterialIsExistence(HouseTypeMaterial houseTypeMaterial);

    /**
     * <p>
     * 单个或批量删除户型建材
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 14:42
     *
     * @param tmIds 户型建材ID数组
     */
    void deleteHouseTypeMaterials(String[] tmIds);

    /**
     * <p>
     * 根据功能区域ID清除数据
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 10:48
     *
     * @param functionId 功能区域ID
     */
    void deleteMaterialByFunctionId(Long functionId);

    /**
     * <p>
     * 删除户型建材数据（可以批量）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 16:27
     *
     * @param typeIds 户型数组
     */
    void deleteMaterials(String[] typeIds);

    /**
     * 删除建材户型关系数据（可以批量）
     * </p>
     * Author: 张建 <br/>
     * Date: 2017/6/20 16:27
     *
     * @param materialIds 建材id数组
     * @return
     */
    int deleteHouseTypeMaterialsByMaterialsIds(String[] materialIds);
    /**
     * 删除建材户型关系数据（但删除）
     * </p>
     * Author: 张建 <br/>
     * Date: 2017/6/20 16:27
     *
     * @param materialId 建材id
     * @return
     */
    int deleteHouseTypeMaterialsByMaterialId(Long materialId);

    /**
     * 删除建材户型关系数据（批量删除）
     * </p>
     * Author: 张建 <br/>
     * Date: 2017/6/20 16:27
     *
     * @param materialId 建材id
     * @return
     */
    int deleteBulkHouseTypeMaterialsByMaterialIds(List<Long> materialId);

    int deleteByPrimaryKey(Long tmId);

    int insert(HouseTypeMaterial record);

    int insertSelective(HouseTypeMaterial record);

    HouseTypeMaterial selectByPrimaryKey(Long tmId);

    int updateByPrimaryKeySelective(HouseTypeMaterial record);

    int updateByPrimaryKey(HouseTypeMaterial record);
}
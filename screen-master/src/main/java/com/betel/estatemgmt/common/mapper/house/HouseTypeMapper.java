package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.house.model.HouseTypeResp;
import com.betel.estatemgmt.business.web.housetype.model.*;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.house.HouseType;
import com.betel.estatemgmt.common.model.house.HouseTypeMaterial;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HouseTypeMapper {


    /**
     * <p>
     * 查询户型功能区域下可以添加的建材（去除已添加的）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/22 10:07
     *
     * @param notInFunctionReq 户型ID 功能区域ID 建材类型
     * @return List<HouseMaterial> 建材集合
     */
    List<HouseMaterial> findAllMaterialNotInFunction(RowBounds rowBounds, NotInFunctionReq notInFunctionReq);



    /**
     * <p>
     * 根据功能区域查询建材列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 13:31
     *
     * @param houseTypeMaterial 包含户型ID和功能区域ID
     * @return List<FunctionMaterialVo> 返回的建材集合
     */
    List<FunctionMaterialVo> findAllMaterialByTypeFunction(RowBounds rowBounds, HouseTypeMaterial houseTypeMaterial);


    /**
     * <p>
     * 根据户型ID查询户型信息
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 11:16
     *
     * @param typeId 户型ID
     * @return BasicHouseType 户型的基本信息
     */
    BasicHouseType findHouseTypeBasic(Long typeId);



    /**
     * <p>
     * 根据户型ID查询户型下是否存在房屋
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 16:35
     *
     * @param typeId 户型ID
     * @return List<House> 为空则不存在房屋
     */
    List<House> findHouseByHouseType(Long typeId);


    /**
     * <p>
     * 删除户型（可以批量）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 16:15
     *
     * @param typeIds 户型数组
     */
    void deleteHouseTypes(String[] typeIds);


    /**
     * <p>
     * 根据户型名称查询是否存在
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 15:18
     *
     * @param typeName 户型名称
     * @return HouseType 户型信息
     */
    HouseType findHouseTypeByTypeName(String typeName);

    /**
     * <p>
     * 关键词查询户型列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 11:02
     *
     * @param pageKeyWord 关键词
     * @return List<HouseTypeVo> 户型信息的集合
     */
    List<HouseTypeVo> findAllHouseType(RowBounds rowBounds, PageKeyWord pageKeyWord);


    int deleteByPrimaryKey(Long typeId);

    int insert(HouseType record);

    int insertSelective(HouseType record);

    HouseType selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(HouseType record);

    int updateByPrimaryKey(HouseType record);

    List<HouseTypeResp> selectHouseTypeResp();
}
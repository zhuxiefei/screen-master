package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.web.housetype.model.BasicFunction;
import com.betel.estatemgmt.common.model.house.HouseTypeFunction;

import java.util.List;

public interface HouseTypeFunctionMapper {

    /**
     * <p>
     * 根据户型ID查询其下的功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 11:19
     *
     * @param typeId 户型ID
     * @return List<BasicFunction> 功能区域集合
     */
    List<BasicFunction> findTypeFunctionBasic(Long typeId);


    /**
     * <p>
     * 判断功能区域是否存在
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 8:52
     *
     * @param houseTypeFunction 需要判断的功能区域名称和对应的户型ID
     * @return HouseTypeFunction 返回null： 不存在
     *                           不为null:  存在
     */
    HouseTypeFunction selectFunctionIsExistence(HouseTypeFunction houseTypeFunction);

    /**
     * <p>
     * 删除户型下的功能区域（可以批量）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 16:23
     *
     * @param typeIds 户型数组
     */
    void deleteFunctions(String[] typeIds);


    /**
     * <p>
     * 根据户型 批量添加功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 13:38
     *
     * @param houseTypeFunctions 功能区域集合
     */
    void insertFunctions(List<HouseTypeFunction> houseTypeFunctions);


    int deleteByPrimaryKey(Long functionId);

    int insert(HouseTypeFunction record);

    int insertSelective(HouseTypeFunction record);

    HouseTypeFunction selectByPrimaryKey(Long functionId);
    /**
     * <p>
     * 根据户型 批量添加功能区域
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/20 13:38
     *
     * @param houseTypeFunction 功能区域
     */
    HouseTypeFunction selectAllByPrimaryKey(HouseTypeFunction houseTypeFunction);


    int updateByPrimaryKeySelective(HouseTypeFunction record);

    int updateByPrimaryKey(HouseTypeFunction record);

    List<HouseTypeFunction> findHouseTypeFunctionByHouseId(String houseId);
}
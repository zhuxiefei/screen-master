package com.betel.estatemgmt.business.web.housetype.service;

import com.betel.estatemgmt.business.web.housetype.model.*;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.house.HouseType;
import com.betel.estatemgmt.common.model.house.HouseTypeMaterial;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * web 户型维护事物接口
 * </p>
 * ClassName: HouseTypeService <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 8:55 <br/>
 * Version: 1.0 <br/>
 */
public interface HouseTypeService {

    public String houseTypeAndFunctionJudges(Long typeId, String functionIds);

    /**
     * <p>
     * 判断户型或功能区域是否被删除
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/29 9:59
     *
     * @param typeId functionId 户型ID 功能区域ID
     * @return String 为空则校验成功 否则返回失败码
     */
    public String houseTypeAndFunctionJudge(Long typeId, Long functionId);

    /**
     * <p>
     * 判断户型或功能区域是否被删除
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/6/29 9:59
     *
     * @param typeId functionId 户型ID 功能区域ID
     * @return String 为空则校验成功 否则返回失败码
     */
    public String houseTypeAndFunctionIsHasDelete(String typeId, String functionId);


    /**
     * <p>
     * 查询户型功能区域下可以添加的建材（去除已添加的）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/22 10:20
     *
     * @param notInFunctionReq 户型ID 功能区域ID 建材类型
     * @return List<HouseMaterial> 建材集合
     */
    public List<HouseMaterial> findAllMaterialNotInFunction(Paging<HouseMaterial> pager, NotInFunctionReq notInFunctionReq);


    /**
     * <p>
     * 单个或批量添加户型建材
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 15:11
     *
     * @param addHouseTypeMaterialReq 功能区域ID和建材ID集合
     * @return String 返回"" 则添加成功
     *                返回null 则添加失败 建材已被删除错误
     *                返回G0020 和 G0021均为失败
     *                其他 则添加失败，返回的是已被添加过的建材名称字符串集合
     *
     */
    public String addHouseTypeMaterial(AddHouseTypeMaterialReq addHouseTypeMaterialReq);

    /**
     * <p>
     * 单个或批量删除户型建材
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 14:44
     *
     * @param tmIds 户型建材ID数组
     */
    public void deleteHouseTypeMaterials(String[] tmIds);

    /**
     * <p>
     * 查询功能区域下的建材列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 13:42
     *
     * @param houseTypeMaterial 包含了户型ID和功能区域ID
     *                          功能区域ID为空时，查询全户型的建材
     * @return List<FunctionMaterialVo> 建材集合
     *
     */
    public List<FunctionMaterialVo> findAllMaterialByTypeFunction(Paging<FunctionMaterialVo> pager, HouseTypeMaterial houseTypeMaterial);


    /**
     * <p>
     * 查询户型详情，包括其下的功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 11:23
     *
     * @param typeId 户型ID
     * @return BasicHouseType 户型的基本信息
     */
    public BasicHouseType findBasicHouseType(Long typeId);

    /**
     * <p>
     * 修改户型名称
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 17:43
     *
     * @param houseType 修改后的户型
     * @return String 返回为空 修改成功
     *                不为空 则返回的是错误码
     */
    public String updateHouseType(HouseType houseType);

    /**
     * <p>
     * 根据户型ID查询户型下是否存在房屋
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 16:47
     *
     * @param typeIds 户型数组
     * @return String 为空则：  可以删除
     *                不为空则：存在房屋的户型集合（以","分隔的字符串）
     */
    public String findHouseByTypeName(String[] typeIds);

    /**
     * <p>
     * 删除户型（可以批量）
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 16:41
     *
     * @param typeIds 户型数组
     */
    public void deleteHouseTypes(String[] typeIds);

    /**
     * <p>
     * 判断户型名称是否存在
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 15:27
     *
     * @param typeName 户型名称
     * @return HouseType 为空则不存在
     *                   不为空则存在
     */
    public HouseType findHouseTypeByTypeName(String typeName);

    /**
     * <p>
     * 新增户型
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 13:41
     *
     * @param houseType 新增的户型信息
     * @param functionNames 需添加的功能区域名称
     */
    public void addHouseType(HouseType houseType, String[] functionNames);


    /**
     * <p>
     * 关键词查询户型列表
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/20 11:06
     *
     * @param pageKeyWord 关键词
     * @return List<HouseTypeVo> 户型信息的集合
     */
    List<HouseTypeVo> findAllHouseType(Paging<HouseTypeVo> pager, PageKeyWord pageKeyWord);

    HouseTypeMaterial selectByPrimaryKey(Long tmId);

}

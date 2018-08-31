package com.betel.estatemgmt.business.web.housetype.service;

import com.betel.estatemgmt.business.web.housetype.model.AddFunctionResult;
import com.betel.estatemgmt.common.model.house.HouseTypeFunction;

/**
 * <p>
 * 户型下的功能区域维护事务接口
 * </p>
 * ClassName: TypeFunctionService <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/21 8:47 <br/>
 * Version: 1.0 <br/>
 */
public interface TypeFunctionService {



    /**
     * <p>
     * 删除功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 10:51
     *
     * @param functionId 功能区域ID
     */
    public void deleteHouseTypeFunction(Long functionId);

    /**
     * <p>
     * 修改功能区域名称
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 10:06
     *
     * @param houseTypeFunction
     * @return String 返回null ： 户型下不存在相同的功能区域名称，正常修改成功
     *                返回不为null　：　存在相同的，不能修改。并返回值为ｃｏｄｅ码
     */
    public String updateHouseTypeFunction(HouseTypeFunction houseTypeFunction);


    /**
     * <p>
     * 新增某户型下的功能区域
     * </p>
     * Author: geyf <br/>
     * Date: 2017/6/21 8:44
     *
     * @param houseTypeFunction 需新增的功能区域
     * @return
     */
    public AddFunctionResult addHouseTypeFunction(HouseTypeFunction houseTypeFunction);
}

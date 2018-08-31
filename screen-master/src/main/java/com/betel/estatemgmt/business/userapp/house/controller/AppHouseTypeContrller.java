package com.betel.estatemgmt.business.userapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.service.AppHouseTypeService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseTypeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * app房屋户型控制层
 * </p>
 * ClassName: AppHouseTypeContrller <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/23 13:09 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/houseType")
public class AppHouseTypeContrller {
    private static final Logger LOG = LoggerFactory.getLogger(AppHouseTypeContrller.class);

    @Autowired
    AppHouseTypeService appHouseTypeService;

    @Autowired
    HouseService houseService;
    /**
     * <p>
     * 查询户型的功能区域
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 13:17
     *return response
     */
    @RequestMapping(value = "v1/findAllHouseTypeFunction",method = RequestMethod.GET)
    public Response findMaterial(House house){
        if(LOG.isInfoEnabled()){
            LOG.info("app/houseType/v1/findAllHouseTypeFunction---------------start---"+house.getHouseId());
        }
        Response response = new Response();
        if(house.getHouseId()==null){
            response.setCode(HouseCode.HOUSEIDNULL);
        }else {
            House houses = houseService.findHouseByHouseId(house.getHouseId());
            if(houses == null){
                response.setCode(HouseCode.HOUSEDELETE);
            }else {
                //设置所有和全户型的材料
                List<HouseTypeFunction> housetypeFunctions = new ArrayList<>();
                HouseTypeFunction housetypeFunction1 = new  HouseTypeFunction();
                housetypeFunction1.setFunctionId(HouseConstant.FUNCTION_ALL_ID);
                housetypeFunction1.setFunctionName(HouseConstant.FUNCTION_ALL);
                housetypeFunctions.add(housetypeFunction1);
                HouseTypeFunction housetypeFunction2 = new  HouseTypeFunction();
                housetypeFunction2.setFunctionId(HouseConstant.FUNCTIONTYPE_ALL_ID);
                housetypeFunction2.setFunctionName(HouseConstant.FUNCTIONTYPE_ALL);
                housetypeFunctions.add(housetypeFunction2);
                List<HouseTypeFunction> housetypeFunction;
                try{
                    housetypeFunction = appHouseTypeService.findHouseTypeFunctionByHouseId(house.getHouseId());
                    if(LOG.isDebugEnabled()){
                        LOG.debug("----------housetypeFunction-------------"+housetypeFunction);
                    }
                }catch (Exception e){
                    LOG.error("app/houseType/v1/findAllHouseTypeFunction---------------error---",e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
                housetypeFunctions.addAll(housetypeFunction);
                if(LOG.isDebugEnabled()){
                    LOG.debug("-------housetypeFunctions------------"+housetypeFunctions);
                }
                response.setData(housetypeFunctions);
            }
        }
        if(LOG.isInfoEnabled()){
            LOG.info("app/houseType/v1/findAllHouseTypeFunction---------------end---"+response);
        }
        return response;
    }
}

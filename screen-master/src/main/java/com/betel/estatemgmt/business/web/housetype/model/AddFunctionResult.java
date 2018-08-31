package com.betel.estatemgmt.business.web.housetype.model;

import com.betel.estatemgmt.common.model.house.HouseTypeFunction;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AddFunctionResult <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/27 14:59 <br/>
 * Version: 1.0 <br/>
 */
public class AddFunctionResult {

    private HouseTypeFunction houseTypeFunction;

    private String resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public HouseTypeFunction getHouseTypeFunction() {

        return houseTypeFunction;
    }

    public void setHouseTypeFunction(HouseTypeFunction houseTypeFunction) {
        this.houseTypeFunction = houseTypeFunction;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddFunctionResult{");
        sb.append("houseTypeFunction=").append(houseTypeFunction);
        sb.append(", resultCode='").append(resultCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

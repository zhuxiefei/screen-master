package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 关于户型建材参数对象
 * </p>
 * ClassName: BuildMaterialInfo <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class HouseAndMaterial {
    private String tmId;
    private String typeId;
    private String functionId;

    public String getTmId() {
        return tmId;
    }

    public void setTmId(String tmId) {
        this.tmId = tmId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
}

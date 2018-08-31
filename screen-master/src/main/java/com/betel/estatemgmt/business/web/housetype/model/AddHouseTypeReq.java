package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AddHouseTypeReq <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 13:55 <br/>
 * Version: 1.0 <br/>
 */
public class AddHouseTypeReq {

    private String typeName;

    private String functionNames;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFunctionNames() {
        return functionNames;
    }

    public void setFunctionNames(String functionNames) {
        this.functionNames = functionNames;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddHouseTypeReq{");
        sb.append("typeName='").append(typeName).append('\'');
        sb.append(", functionNames='").append(functionNames).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

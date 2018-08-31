package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: BasicFunction <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 9:45 <br/>
 * Version: 1.0 <br/>
 */
public class BasicFunction {

    private Long functionId;

    private String functionName;

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicFunction{");
        sb.append("functionId=").append(functionId);
        sb.append(", functionName='").append(functionName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

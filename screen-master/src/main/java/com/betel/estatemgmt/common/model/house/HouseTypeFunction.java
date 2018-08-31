package com.betel.estatemgmt.common.model.house;

public class HouseTypeFunction {
    private Long functionId;

    private String functionName;

    private Long typeId;

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
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseTypeFunction{");
        sb.append("functionId=").append(functionId);
        sb.append(", functionName='").append(functionName).append('\'');
        sb.append(", typeId=").append(typeId);
        sb.append('}');
        return sb.toString();
    }
}
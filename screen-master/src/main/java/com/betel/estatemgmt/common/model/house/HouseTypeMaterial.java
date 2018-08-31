package com.betel.estatemgmt.common.model.house;

public class HouseTypeMaterial {
    private Long tmId;

    private Long typeId;

    private Long functionId;

    private Long materialId;

    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseTypeMaterial{");
        sb.append("functionId=").append(functionId);
        sb.append(", tmId=").append(tmId);
        sb.append(", typeId=").append(typeId);
        sb.append(", materialId=").append(materialId);
        sb.append('}');
        return sb.toString();
    }
}
package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: TypeMaterialInfo <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/21 15:33 <br/>
 * Version: 1.0 <br/>
 */
public class TypeMaterialInfo {

    private Long tmId;
    private Long typeId;
    private Long functionId;
    private Long materialId;
    private String materialName;

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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypeMaterialInfo{");
        sb.append("tmId=").append(tmId);
        sb.append(", typeId=").append(typeId);
        sb.append(", functionId=").append(functionId);
        sb.append(", materialId=").append(materialId);
        sb.append(", materialName='").append(materialName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

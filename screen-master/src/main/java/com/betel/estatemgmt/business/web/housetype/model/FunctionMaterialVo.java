package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: FunctionMaterialVo <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 9:47 <br/>
 * Version: 1.0 <br/>
 */
public class FunctionMaterialVo {

    private Long tmId;

    private Long materialId;

    private String materialName;

    private String materialType;

    private String materialBrand;

    private String materialModel;

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

    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialBrand() {
        return materialBrand;
    }

    public void setMaterialBrand(String materialBrand) {
        this.materialBrand = materialBrand;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FunctionMaterialVo{");
        sb.append("tmId=").append(tmId);
        sb.append(", materialId=").append(materialId);
        sb.append(", materialName='").append(materialName).append('\'');
        sb.append(", materialType='").append(materialType).append('\'');
        sb.append(", materialBrand='").append(materialBrand).append('\'');
        sb.append(", materialModel='").append(materialModel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

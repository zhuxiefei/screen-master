package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: AddHouseTypeMaterialReq <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/21 15:05 <br/>
 * Version: 1.0 <br/>
 */
public class AddHouseTypeMaterialReq {

    private Long typeId;

    private String materialIds;

    private Long functionId;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getMaterialIds() {

        return materialIds;
    }

    public void setMaterialIds(String materialIds) {
        this.materialIds = materialIds;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddHouseTypeMaterialReq{");
        sb.append("typeId=").append(typeId);
        sb.append(", materialIds='").append(materialIds).append('\'');
        sb.append(", functionId=").append(functionId);
        sb.append('}');
        return sb.toString();
    }
}

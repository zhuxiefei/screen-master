package com.betel.estatemgmt.business.web.housetype.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: NotInFunctionReq <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/22 10:06 <br/>
 * Version: 1.0 <br/>
 */
public class NotInFunctionReq extends Page {

    private Long typeId;

    private Long functionId;

    private String materialType;

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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NotInFunctionReq{");
        sb.append("typeId=").append(typeId);
        sb.append(", functionId=").append(functionId);
        sb.append(", materialType='").append(materialType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

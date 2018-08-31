package com.betel.estatemgmt.business.web.housetype.model;

import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: HouseTypeMaterialPage <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/21 11:47 <br/>
 * Version: 1.0 <br/>
 */
public class HouseTypeMaterialPage extends Page {

    private Long typeId;

    private Long functionId;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseTypeMaterialPage{");
        sb.append("typeId=").append(typeId);
        sb.append(", functionId=").append(functionId);
        sb.append('}');
        return sb.toString();
    }
}

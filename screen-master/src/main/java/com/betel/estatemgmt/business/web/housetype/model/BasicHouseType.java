package com.betel.estatemgmt.business.web.housetype.model;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: BasicHouseType <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 9:43 <br/>
 * Version: 1.0 <br/>
 */
public class BasicHouseType {

    private Long typeId;

    private String typeName;

    private List<BasicFunction> BasicFunctions;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<BasicFunction> getBasicFunctions() {
        return BasicFunctions;
    }

    public void setBasicFunctions(List<BasicFunction> basicFunctions) {
        BasicFunctions = basicFunctions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicHouseType{");
        sb.append("typeId=").append(typeId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", BasicFunctions=").append(BasicFunctions);
        sb.append('}');
        return sb.toString();
    }
}

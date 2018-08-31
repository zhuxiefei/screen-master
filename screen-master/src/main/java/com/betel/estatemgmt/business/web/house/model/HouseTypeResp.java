package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 户型回参
 * </p>
 * ClassName: HouseTypeResp <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/29 14:44 <br/>
 * Version: 1.0 <br/>
 */
public class HouseTypeResp {
    private Long typeId;
    private String typeName;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseTypeResp{");
        sb.append("typeId=").append(typeId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

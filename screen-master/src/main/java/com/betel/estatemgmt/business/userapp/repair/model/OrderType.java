package com.betel.estatemgmt.business.userapp.repair.model;

/**
 * <p>
 * 报修类型model
 * </p>
 * ClassName: OrderType <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 17:23 <br/>
 * Version: 1.0 <br/>
 */
public class OrderType {
    /*
    类别Id
     */
    private String typeId;
    /*
    父级id
     */
    private String parentId;
    /*
    类别名称
     */
    private String typeName;
    /*
    类型描述
     */
    private String typeDesc;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}

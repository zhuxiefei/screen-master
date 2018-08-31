package com.betel.estatemgmt.common.model.repair;

public class RepairOrderType {
    private Long typeId;

    private Long parentId;

    private int typeType;

    private String typeName;

    private String typeDesc;

    private Integer estateType;

    public Integer getEstateType() {
        return estateType;
    }

    public void setEstateType(Integer estateType) {
        this.estateType = estateType;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getTypeType() {
        return typeType;
    }

    public void setTypeType(int typeType) {
        this.typeType = typeType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc == null ? null : typeDesc.trim();
    }

    @Override
    public String toString() {
        return "RepairOrderType{" +
                "typeId=" + typeId +
                ", parentId=" + parentId +
                ", typeType=" + typeType +
                ", typeName='" + typeName + '\'' +
                ", typeDesc='" + typeDesc + '\'' +
                ", estateType=" + estateType +
                '}';
    }
}
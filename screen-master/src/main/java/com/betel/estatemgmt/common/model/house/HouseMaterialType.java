package com.betel.estatemgmt.common.model.house;

public class HouseMaterialType {
    private String typeName;

    private String typeDesc;

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
        final StringBuilder sb = new StringBuilder("HouseMaterialType{");
        sb.append("typeDesc='").append(typeDesc).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
package com.betel.estatemgmt.common.model.house;

public class HouseMaterialParm {
    private Long parmId;

    private String parmName;

    private String parmContent;

    private Long materialId;

    public Long getParmId() {
        return parmId;
    }

    public void setParmId(Long parmId) {
        this.parmId = parmId;
    }

    public String getParmName() {
        return parmName;
    }

    public void setParmName(String parmName) {
        this.parmName = parmName == null ? null : parmName.trim();
    }

    public String getParmContent() {
        return parmContent;
    }

    public void setParmContent(String parmContent) {
        this.parmContent = parmContent == null ? null : parmContent.trim();
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseMaterialParm{");
        sb.append("materialId=").append(materialId);
        sb.append(", parmId=").append(parmId);
        sb.append(", parmName='").append(parmName).append('\'');
        sb.append(", parmContent='").append(parmContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
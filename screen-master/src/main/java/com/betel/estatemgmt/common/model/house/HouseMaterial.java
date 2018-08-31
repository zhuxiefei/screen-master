package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class HouseMaterial {
    private Long materialId;

    private String materialName;

    private String materialPic;

    private String materialBrand;

    private String materialModel;

    private String materialSpecification;

    private String materialOrigin;

    private Date materialProduceDate;

    private Date materialWarrantyDate;

    private String materialType;

    private Date createTime;

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
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getMaterialPic() {
        return materialPic;
    }

    public void setMaterialPic(String materialPic) {
        this.materialPic = materialPic;
    }

    public String getMaterialBrand() {
        return materialBrand;
    }

    public void setMaterialBrand(String materialBrand) {
        this.materialBrand = materialBrand == null ? null : materialBrand.trim();
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel == null ? null : materialModel.trim();
    }

    public String getMaterialSpecification() {
        return materialSpecification;
    }

    public void setMaterialSpecification(String materialSpecification) {
        this.materialSpecification = materialSpecification == null ? null : materialSpecification.trim();
    }

    public String getMaterialOrigin() {
        return materialOrigin;
    }

    public void setMaterialOrigin(String materialOrigin) {
        this.materialOrigin = materialOrigin == null ? null : materialOrigin.trim();
    }

    public Date getMaterialProduceDate() {
        return materialProduceDate;
    }

    public void setMaterialProduceDate(Date materialProduceDate) {
        this.materialProduceDate = materialProduceDate;
    }

    public Date getMaterialWarrantyDate() {
        return materialWarrantyDate;
    }

    public void setMaterialWarrantyDate(Date materialWarrantyDate) {
        this.materialWarrantyDate = materialWarrantyDate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType == null ? null : materialType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseMaterial{");
        sb.append("createTime=").append(createTime);
        sb.append(", materialId=").append(materialId);
        sb.append(", materialName='").append(materialName).append('\'');
        sb.append(", materialPic=").append(materialPic);
        sb.append(", materialBrand='").append(materialBrand).append('\'');
        sb.append(", materialModel='").append(materialModel).append('\'');
        sb.append(", materialSpecification='").append(materialSpecification).append('\'');
        sb.append(", materialOrigin='").append(materialOrigin).append('\'');
        sb.append(", materialProduceDate=").append(materialProduceDate);
        sb.append(", materialWarrantyDate=").append(materialWarrantyDate);
        sb.append(", materialType='").append(materialType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
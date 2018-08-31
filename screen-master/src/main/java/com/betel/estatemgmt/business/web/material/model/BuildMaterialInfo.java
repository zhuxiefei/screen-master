package com.betel.estatemgmt.business.web.material.model;

import java.util.Date;

/**
 * <p>
 * 关于家装建材的展示页面对象
 * </p>
 * ClassName: BuildMaterialInfo <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialInfo {

    private Long materialId;
    private String materialType;
    private String materialName;
    private String materialBrand;
    private String materialModel;
    private String materialSpecification;
    private Date createTime;

    @Override
    public String toString() {
        return "HouseMaterialInfo{" +
                "materialId=" + materialId +
                ", materialName='" + materialName + '\'' +
                ", materialBrand='" + materialBrand + '\'' +
                ", materialModel='" + materialModel + '\'' +
                ", materialSpecification='" + materialSpecification + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialBrand() {
        return materialBrand;
    }

    public void setMaterialBrand(String materialBrand) {
        this.materialBrand = materialBrand;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public String getMaterialSpecification() {
        return materialSpecification;
    }

    public void setMaterialSpecification(String materialSpecification) {
        this.materialSpecification = materialSpecification;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BuildMaterialInfo(Long materialId, String materialName, String materialBrand, String materialModel, String materialSpecification, Date createTime) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialBrand = materialBrand;
        this.materialModel = materialModel;
        this.materialSpecification = materialSpecification;
        this.createTime = createTime;
    }

    public BuildMaterialInfo() {
        super();
    }


}

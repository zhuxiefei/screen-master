package com.betel.estatemgmt.business.web.material.model;

import com.betel.estatemgmt.common.model.house.HouseMaterialParm;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 关于家装建材修改展示操作的参数对象
 * </p>
 * ClassName: BuildMaterialUpdate <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialUpdate {
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

    public String getMaterialOrigin() {
        return materialOrigin;
    }

    public void setMaterialOrigin(String materialOrigin) {
        this.materialOrigin = materialOrigin;
    }

    public String getMaterialProduceDate() {
        return materialProduceDate;
    }

    public void setMaterialProduceDate(String materialProduceDate) {
        this.materialProduceDate = materialProduceDate;
    }

    public String getMaterialWarrantyDate() {
        return materialWarrantyDate;
    }

    public void setMaterialWarrantyDate(String materialWarrantyDate) {
        this.materialWarrantyDate = materialWarrantyDate;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<HouseMaterialParm> getHouseMaterialParms() {
        return houseMaterialParms;
    }

    public void setHouseMaterialParms(List<HouseMaterialParm> houseMaterialParms) {
        this.houseMaterialParms = houseMaterialParms;
    }

    private Long materialId;

    private String materialName;

    private String materialPicId;

    private String materialPicUrl;

    public String getMaterialPicId() {
        return materialPicId;
    }

    public void setMaterialPicId(String materialPicId) {
        this.materialPicId = materialPicId;
    }

    public String getMaterialPicUrl() {
        return materialPicUrl;
    }

    public void setMaterialPicUrl(String materialPicUrl) {
        this.materialPicUrl = materialPicUrl;
    }

    private String materialBrand;

    private String materialModel;

    private String materialSpecification;

    private String materialOrigin;

    private String materialProduceDate;

    private String materialWarrantyDate;

    private String materialType;

    private Date createTime;

    private List<HouseMaterialParm> houseMaterialParms;


}

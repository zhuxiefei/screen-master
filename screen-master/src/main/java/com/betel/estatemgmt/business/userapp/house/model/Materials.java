package com.betel.estatemgmt.business.userapp.house.model;

import com.betel.estatemgmt.common.model.house.HouseMaterialParm;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: Materials <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/23 10:19 <br/>
 * Version: 1.0 <br/>
 */
public class Materials {
    //材料id
    private Long materialId;
    //材料名称
    private String materialName;
    //材料图片
    private String materialPic;
    //材料品牌
    private String materialBrand;
    //建材型号
    private String materialModel;
    //材料规格
    private String materialSpecification;
    //材料产地
    private String materialOrigin;
    //生产日期
    private String materialProduceDate;
    //质保日期
    private String materialWarrantyDate;
    //其他参数
    private List<HouseMaterialParm>  materialparms;

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

    public List<HouseMaterialParm> getMaterialparms() {
        return materialparms;
    }

    public void setMaterialparms(List<HouseMaterialParm> materialparms) {
        this.materialparms = materialparms;
    }

    @Override
    public String toString() {
        return "Materials{" +
                "materialparms=" + materialparms +
                ", materialWarrantyDate='" + materialWarrantyDate + '\'' +
                ", materialProduceDate='" + materialProduceDate + '\'' +
                ", materialOrigin='" + materialOrigin + '\'' +
                ", materialSpecification='" + materialSpecification + '\'' +
                ", materialModel='" + materialModel + '\'' +
                ", materialBrand='" + materialBrand + '\'' +
                ", materialPic='" + materialPic + '\'' +
                ", materialName='" + materialName + '\'' +
                ", materialId=" + materialId +
                '}';
    }

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
        this.materialBrand = materialBrand;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
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
}

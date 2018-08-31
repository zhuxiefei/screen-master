package com.betel.estatemgmt.business.web.material.model;

/**
 * <p>
 * 关于家装建材操作的新增和修改参数对象
 * </p>
 * ClassName: BuildMaterialAddUpdateReq <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialAddUpdateReq {


    //材料id，修改需要
    private String materialId;
    //材料类别名称
    private String materialType;
    //材料名称
    private String materialName;
    //材料图片id
    private String materialPicId;
    //材料品牌
    private String materialBrand;
    //材料型号
    private String materialModel;
    //规格
    private String materialSpecification;
    //材料产地
    private String materialOrigin;
    //生产日期
    private String materialProduceDate;
    //截止日期（保质期）
    private String materialWarrantyDate;

    public String getParmNames() {
        return parmNames;
    }

    public void setParmNames(String parmNames) {
        this.parmNames = parmNames;
    }

    public String getParmContents() {
        return parmContents;
    }

    public void setParmContents(String parmContents) {
        this.parmContents = parmContents;
    }

    //材料自定义参数集合
    private String parmNames;
    //材料自定义参数内容
    private String parmContents;


    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialPicId() {
        return materialPicId;
    }

    public void setMaterialPicId(String materialPicId) {
        this.materialPicId = materialPicId;
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


}

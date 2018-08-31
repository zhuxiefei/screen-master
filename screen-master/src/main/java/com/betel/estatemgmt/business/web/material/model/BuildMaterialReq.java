package com.betel.estatemgmt.business.web.material.model;


/**
 * <p>
 * 关于家装建材查询操作的参数对象
 * </p>
 * ClassName: BuildMaterialReq <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialReq {
    /**
     * 关键字：材料名称
     */
    private String keyWord;

    /**
     * 材料类型
     */
    private String materialType;
    private String materialName;
    private String materialBrand;
    private String materialModel;
    private String startTime;
    private String endTime;

    /**
     * 批量删除id集合
     */
    private String materialIds;
    /**
     * 分页数据
     */
    private int curPage = 1;

    private int pageSize = 10;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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

    public String getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(String materialIds) {
        this.materialIds = materialIds;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}

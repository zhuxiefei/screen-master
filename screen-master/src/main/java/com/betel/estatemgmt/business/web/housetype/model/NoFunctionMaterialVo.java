package com.betel.estatemgmt.business.web.housetype.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: NoFunctionMaterialVo <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 9:51 <br/>
 * Version: 1.0 <br/>
 */
public class NoFunctionMaterialVo {

    private Long materialId;

    private String materialBrand;

    private String materialModel;

    private Date createTime;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
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

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoFunctionMaterialVo{");
        sb.append("materialId=").append(materialId);
        sb.append(", materialBrand='").append(materialBrand).append('\'');
        sb.append(", materialModel='").append(materialModel).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

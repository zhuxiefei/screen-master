package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 导入房屋图纸失败信息回参
 * </p>
 * ClassName: PicImportMsg <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/23 12:04 <br/>
 * Version: 1.0 <br/>
 */
public class PicImportMsg {

    private String buildingName;

    private String unitName;

    private String houseNum;

    private String housePicType;

    private String pictureName;

    private String cadName;

    private String failureMsg;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getHousePicType() {
        return housePicType;
    }

    public void setHousePicType(String housePicType) {
        this.housePicType = housePicType;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getCadName() {
        return cadName;
    }

    public void setCadName(String cadName) {
        this.cadName = cadName;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PicImportMsg{");
        sb.append("buildingName='").append(buildingName).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", housePicType='").append(housePicType).append('\'');
        sb.append(", pictureName='").append(pictureName).append('\'');
        sb.append(", cadName='").append(cadName).append('\'');
        sb.append(", failureMsg='").append(failureMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

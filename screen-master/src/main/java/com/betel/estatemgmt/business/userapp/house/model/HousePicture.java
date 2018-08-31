package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * 房屋图纸
 * </p>
 * ClassName: HousePicture <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/23 15:00 <br/>
 * Version: 1.0 <br/>
 */
public class HousePicture {
    //图纸id
    private Long hpId;

    //房屋id
    private String houseId;

    //图纸类型
    private String pictureType;

    //图纸Url
    private String pictureUrl;

    //CADURl
    private String cadUrl;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getCadUrl() {
        return cadUrl;
    }

    public void setCadUrl(String cadUrl) {
        this.cadUrl = cadUrl;
    }

    @Override
    public String toString() {
        return "HousePicture{" +
                "hpId=" + hpId +
                ", pictureType='" + pictureType + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }

    public Long getHpId() {
        return hpId;
    }

    public void setHpId(Long hpId) {
        this.hpId = hpId;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}

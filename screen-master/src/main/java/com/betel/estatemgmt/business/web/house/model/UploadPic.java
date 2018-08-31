package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 上传房屋图纸、CAD文件接口入参类
 * </p>
 * ClassName: uploadPic <br/>
 * Author: zhangli  <br/>
 * Date: 2017/6/23 10:18 <br/>
 * Version: 1.0 <br/>
 */
public class UploadPic {

    /**
     * 房屋图纸编号
     */
    private Long hpId;

    /**
     * 图片url
     */
    private String pictureUrl;

    /**
     * cad文件名
     */
    private String pictureName;

    public Long getHpId() {
        return hpId;
    }

    public void setHpId(Long hpId) {
        this.hpId = hpId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadPic{");
        sb.append("hpId=").append(hpId);
        sb.append(", pictureUrl='").append(pictureUrl).append('\'');
        sb.append(", pictureName='").append(pictureName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

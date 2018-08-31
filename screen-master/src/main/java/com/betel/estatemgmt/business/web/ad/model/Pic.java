package com.betel.estatemgmt.business.web.ad.model;

/**
 * <p>
 * 上传图片返回值类
 * </p>
 * ClassName: AdPage <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 17:19 <br/>
 * Version: 1.0 <br/>
 */
public class Pic {
    private String pictureId;

    private String pictureUrl;

    private String pictureName;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}

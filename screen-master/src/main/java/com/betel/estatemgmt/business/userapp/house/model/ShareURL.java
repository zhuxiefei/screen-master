package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * 分享图片的model
 * </p>
 * ClassName: ShareURL <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/24 10:51 <br/>
 * Version: 1.0 <br/>
 */
public class ShareURL {
    /**
     * 分享的链接图片的id
     */
   private String pictureId;

    /**
     * 分享的链接图片名
     */

    private String pictureName;

    /**
     * 文件的链接
     */
    private String pictureUrl;
    /**
     * 分享的链接
     */
    private String url;
    /**
     * 密码
     */
    private String randomPassWord;

    /**
     *失效时间
     */
    private String expireTime;

    public ShareURL() {
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
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

    public ShareURL(String url, String randomPassWord) {
        this.url = url;
        this.randomPassWord = randomPassWord;
    }

    public ShareURL(String expireTime,String pictureName, String pictureUrl, String randomPassWord) {
        this.expireTime = expireTime;
        this.pictureName = pictureName;
        this.pictureUrl = pictureUrl;
        this.randomPassWord = randomPassWord;
    }

    @Override
    public String toString() {
        return "ShareURL{" +
                "pictureId=" + pictureId +
                ", pictureName='" + pictureName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", url='" + url + '\'' +
                ", randomPassWord='" + randomPassWord + '\'' +
                ", expireTime='" + expireTime + '\'' +
                '}';
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRandomPassWord() {
        return randomPassWord;
    }
    public void setRandomPassWord(String randomPassWord) {
        this.randomPassWord = randomPassWord;
    }
}

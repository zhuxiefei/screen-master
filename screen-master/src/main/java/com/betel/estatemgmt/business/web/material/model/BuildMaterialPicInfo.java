package com.betel.estatemgmt.business.web.material.model;

/**
 * <p>
 * 关于家装建材操作的头像对象
 * </p>
 * ClassName: BuildMaterialPicInfo <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialPicInfo {
    private String pictureId;

    private String pictureUrl;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BuildMaterialPicInfo [pictureId=");
        builder.append(pictureId);
        builder.append(",pictureUrl=");
        builder.append(pictureUrl);
        builder.append("]");
        return builder.toString();
    }
}

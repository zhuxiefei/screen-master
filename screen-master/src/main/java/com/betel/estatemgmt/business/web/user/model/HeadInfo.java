package com.betel.estatemgmt.business.web.user.model;

/**
 * 头像信息
 * ClassName: HeadInfo
 * Author: Zhang li
 * Date: 2017/5/15 13:20
 * Version: 1.0
 */
public class HeadInfo {
    private Long pictureId;

    private String pictureUrl;

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("HeadInfo [pictureId=");
        builder.append(pictureId);
        builder.append(",pictureUrl=");
        builder.append(pictureUrl);
        builder.append("]");
        return builder.toString();
    }
}

package com.betel.estatemgmt.common.model.house;

import java.util.Date;

/**
 * <p>
 * 分享图片的
 * </p>
 * ClassName: HousePictureShare <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/24 9:37 <br/>
 * Version: 1.0 <br/>
 */
public class HousePictureShare {
    private Long shareId;

    private String shareUrl;

    private String shareKey;

    private Date expireTime;

    private String shareUser;

    private Date createTime;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public Date getDatetime() {
        return expireTime;
    }

    public void setDatetime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HousePictureShare{");
        sb.append("createTime=").append(createTime);
        sb.append(", shareId=").append(shareId);
        sb.append(", shareUrl=").append(shareUrl);
        sb.append(", shareKey='").append(shareKey).append('\'');
        sb.append(", expireTime=").append(expireTime);
        sb.append(", shareUser='").append(shareUser).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

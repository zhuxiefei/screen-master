package com.betel.estatemgmt.business.web.ad.model;

import java.util.Date;

/**
 * <p>
 * 广告列表实体类
 * </p>
 * ClassName: AdInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 15:36 <br/>
 * Version: 1.0 <br/>
 */
public class AdInfo {
    private Long adId;

    private String userName;

    private String adTitle;

    private Date createTime;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public Date getCreateTime() {
        if(createTime == null){
            return null;
        }
        return (Date)createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdInfo{");
        sb.append("adId=").append(adId);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", adTitle='").append(adTitle).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

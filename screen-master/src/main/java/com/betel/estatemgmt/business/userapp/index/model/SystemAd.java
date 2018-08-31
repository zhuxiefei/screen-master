package com.betel.estatemgmt.business.userapp.index.model;

/**
 * <p>
 * 广告详情类
 * </p>
 * ClassName: SystemAd <br/>
 * Author: zhouye  <br/>
 * Date:  2017/5/15 14:02 <br/>
 * Version: 1.0 <br/>
 */
public class SystemAd {
    //广告标题
    private String adTitle;
    //广告内容
    private String adContent;
    /*
    广告创建时间
   */
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SystemAd{" +
                "adTitle='" + adTitle + '\'' +
                ", adContent='" + adContent + '\'' +
                '}';
    }
    public SystemAd( ) {
    }

    public SystemAd(String adTitle, String adContent) {
        this.adTitle = adTitle;
        this.adContent = adContent;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }
}

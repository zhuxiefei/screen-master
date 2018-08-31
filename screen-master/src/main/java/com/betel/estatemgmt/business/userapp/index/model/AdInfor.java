package com.betel.estatemgmt.business.userapp.index.model;

/**
 * <p>
 * 广告信息类
 * </p>
 * ClassName: zhouye <br/>
 * Author: Cui.xx  <br/>
 * Date:  2017/5/15 14:00 <br/>
 * Version: 1.0 <br/>
 */
public class AdInfor {
    /*
    广告id
     */
    private Long adId;
    /*
    广告标题
     */
    private  String adTitle;
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

    public AdInfor() {
        super();
    }

    public AdInfor(Long adId, String adTitle, String pictureUrl) {
        this.adId = adId;
        this.adTitle = adTitle;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public Long getAdId() {
        return adId;
    }

    public String getAdTitle() {
        return adTitle;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdInfor{");
        sb.append("adId=").append(adId);
        sb.append(", adTitle='").append(adTitle).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

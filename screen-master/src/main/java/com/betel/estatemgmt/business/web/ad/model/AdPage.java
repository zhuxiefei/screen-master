package com.betel.estatemgmt.business.web.ad.model;


import com.betel.estatemgmt.common.Page;

/**
 * <p>
 * 广告分页入餐
 * </p>
 * ClassName: AdPage <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/16 17:19 <br/>
 * Version: 1.0 <br/>
 */
public class AdPage extends Page{

    private String adTitle;
    private String userName;
    private String startTime;
    private String endTime;

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdPage{");
        sb.append("adTitle='").append(adTitle).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

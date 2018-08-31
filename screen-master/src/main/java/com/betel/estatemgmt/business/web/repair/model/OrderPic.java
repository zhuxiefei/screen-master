package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 查询确认单图片回参
 * </p>
 * ClassName: OrderPic <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:00 <br/>
 * Version: 1.0 <br/>
 */
public class OrderPic {
    private String picId;

    private String picUrl;

    private String description;

    private Integer serviceOnTime;

    private Integer serviceAttitude;

    private Integer serviceQuality;

    private String serviceDesc;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getServiceOnTime() {
        return serviceOnTime;
    }

    public void setServiceOnTime(Integer serviceOnTime) {
        this.serviceOnTime = serviceOnTime;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Integer getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Integer serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    @Override
    public String toString() {
        return "OrderPic{" +
                "picId='" + picId + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", description='" + description + '\'' +
                ", serviceOnTime=" + serviceOnTime +
                ", serviceAttitude=" + serviceAttitude +
                ", serviceQuality=" + serviceQuality +
                ", serviceDesc='" + serviceDesc + '\'' +
                '}';
    }
}

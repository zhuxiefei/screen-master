package com.betel.estatemgmt.business.web.ad.model;

/**
 * <p>
 * 设置广告置顶状态入参
 * </p>
 * ClassName: AdTopInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 13:40 <br/>
 * Version: 1.0 <br/>
 */
public class AdTopInfo {

    private Long adId;

    private Integer status;//1代表置顶，0代表不置顶

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

package com.betel.estatemgmt.business.web.announce.model;

/**
 * <p>
 * 设置公告置顶入参
 * </p>
 * ClassName: AnnounceTopReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/10/17 10:45 <br/>
 * Version: 1.0 <br/>
 */
public class AnnounceTopReq {

    private Long announceId;

    private Integer status;//1代表置顶，0代表不置顶

    public Long getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(Long announceId) {
        this.announceId = announceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnnounceTopReq{");
        sb.append("announceId=").append(announceId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.common.model.property;

import java.util.Date;

public class PropertyHotline {
    private String hotlineId;

    private String hotlineName;

    private String hotline;

    private Date createTime;

    private Date updateTime;

    public String getHotlineId() {
        return hotlineId;
    }

    public void setHotlineId(String hotlineId) {
        this.hotlineId = hotlineId == null ? null : hotlineId.trim();
    }

    public String getHotlineName() {
        return hotlineName;
    }

    public void setHotlineName(String hotlineName) {
        this.hotlineName = hotlineName == null ? null : hotlineName.trim();
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline == null ? null : hotline.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
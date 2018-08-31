package com.betel.estatemgmt.business.web.announce.model;

import java.util.Date;

/**
 * <p>
 * 公告列表
 * </p>
 * ClassName: AnnounceInfo <br/>
 * Author: xiayanxin  <br/>
 * Date: 2017/5/15 18:39 <br/>
 * Version: 1.0 <br/>
 */
public class AnnouncePoint {
    private Long announceId;

    private String announceTitle;

    private String userName;

    private Integer announceStatus;

    private Date createTime;

    private Integer isTop;

    public Long getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(Long announceId) {
        this.announceId = announceId;
    }

    public String getAnnounceTitle() {
        return announceTitle;
    }

    public void setAnnounceTitle(String announceTitle) {
        this.announceTitle = announceTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAnnounceStatus() {
        return announceStatus;
    }

    public void setAnnounceStatus(Integer announceStatus) {
        this.announceStatus = announceStatus;
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

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnnouncePoint{");
        sb.append("announceId=").append(announceId);
        sb.append(", announceTitle='").append(announceTitle).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", announceStatus=").append(announceStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", isTop=").append(isTop);
        sb.append('}');
        return sb.toString();
    }
}

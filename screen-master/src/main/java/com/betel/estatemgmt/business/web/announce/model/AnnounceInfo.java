package com.betel.estatemgmt.business.web.announce.model;

import java.util.Date;

/**
 * <p>
 * 公告详情
 * </p>
 * ClassName: AnnounceInfo <br/>
 * Author: xiayanxin  <br/>
 * Date: 2017/5/15 18:39 <br/>
 * Version: 1.0 <br/>
 */
public class AnnounceInfo {
    private Long announceId;

    private String announceTitle;

    private String announceContent;

    private Date createTime;

    public AnnounceInfo(){}

    public AnnounceInfo(Long announceId, String announceTitle, String announceContent, Date createTime) {
        this.announceId = announceId;
        this.announceTitle = announceTitle;
        this.announceContent = announceContent;
        if(createTime == null){
            this.createTime = null;
        }else{
            this.createTime = (Date)createTime.clone();
        }
    }

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

    public String getAnnounceContent() {
        return announceContent;
    }

    public void setAnnounceContent(String announceContent) {
        this.announceContent = announceContent;
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
}

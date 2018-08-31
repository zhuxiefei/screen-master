package com.betel.estatemgmt.business.userapp.index.model;

/**
 * <p>
 * 公告详情类
 * </p>
 * ClassName: SystemAnnounce <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/15 14:33 <br/>
 * Version: 1.0 <br/>
 */
public class SystemAnnounce {
   /*
   公告Id
    */
    private Long announceId;
    /*
    公告标题
     */
    private String announceTitle;
    /*
    公告内容
     */
    private String announceContent;
    /*
    公告创建时间
     */
    private String  createTime;

    /**
     * 是否置顶
     */
    private Integer isTop;

    public SystemAnnounce() {

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemAnnounce{");
        sb.append("announceId=").append(announceId);
        sb.append(", announceTitle='").append(announceTitle).append('\'');
        sb.append(", announceContent='").append(announceContent).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", isTop=").append(isTop);
        sb.append('}');
        return sb.toString();
    }
}

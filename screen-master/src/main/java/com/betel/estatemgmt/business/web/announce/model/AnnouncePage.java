package com.betel.estatemgmt.business.web.announce.model;

/**
 * <p>
 * 公告分页入餐
 * </p>
 * ClassName: AnnouncePage <br/>
 * Author: xiayanxin  <br/>
 * Date: 2017/5/16 08:37 <br/>
 * Version: 1.0
 */
public class AnnouncePage {


    private String announceTitle;
    private String userName;
    private String startTime;
    private String endTime;


    private String isTop;
    private String announceStatus;

    private int curPage = 1;

    private int pageSize = 10;

    @Override
    public String toString() {
        return "AnnouncePage{" +
                "announceTitle='" + announceTitle + '\'' +
                ", userName='" + userName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", announceStatus='" + announceStatus + '\'' +
                ", curPage=" + curPage +
                ", pageSize=" + pageSize +
                '}';
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
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

    public String getAnnounceStatus() {
        return announceStatus;
    }

    public void setAnnounceStatus(String announceStatus) {
        this.announceStatus = announceStatus;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

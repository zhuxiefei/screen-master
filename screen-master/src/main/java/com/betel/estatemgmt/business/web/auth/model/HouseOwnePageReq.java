package com.betel.estatemgmt.business.web.auth.model;

/**
 * <p>
 * 户主认证列表入参
 * </p>
 * ClassName: HouseOwnePageReq <br/>
 * Author: zhanglei  <br/>
 * Date: 2017/6/20 9:26 <br/>
 * Version: 1.0 <br/>
 */
public class HouseOwnePageReq {

    private String authStatus;

    private Integer curPage;

    private Integer pageSize;

    /*
    审核状态
     */
    private String reviewStatus;
    /*
     初始时间
     */
    private String startTime;
    /*
    结束时间
     */
    private String endTime;
    /*
    房屋信息
     */
    private String houseInfo;
    /*
    申请人信息
     */
    private String applicantName;

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
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

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseOwnePageReq{");
        sb.append("authStatus=").append(authStatus);
        sb.append(", curPage=").append(curPage);
        sb.append(", pageSize=").append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}

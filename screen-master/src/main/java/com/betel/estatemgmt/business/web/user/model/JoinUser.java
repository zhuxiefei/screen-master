package com.betel.estatemgmt.business.web.user.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: JoinUser <br/>
 * Author: geyf  <br/>
 * Date: 2017/8/10 14:39 <br/>
 * Version: 1.0 <br/>
 */
public class JoinUser {

    private String acctName;

    private String activityTitle;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JoinUser{");
        sb.append("acctName='").append(acctName).append('\'');
        sb.append(", activityTitle='").append(activityTitle).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

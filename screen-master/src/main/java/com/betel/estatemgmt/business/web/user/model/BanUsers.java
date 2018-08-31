package com.betel.estatemgmt.business.web.user.model;

/**
 * <p>
 * 用户被禁言信息
 * </p>
 * ClassName: Users <br/>
 * Author: Zhang Li  <br/>
 * Date: 2017/8/7 15:28 <br/>
 * Version: 1.0 <br/>
 */
public class BanUsers {

    private String userId;

    private String acctName;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Users{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", acctName='").append(acctName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

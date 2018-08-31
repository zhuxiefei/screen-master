package com.betel.estatemgmt.business.web.user.model;
import com.betel.estatemgmt.common.Page;

/**
 * 用户分页
 * ClassName: UserPage
 * Author: Zhang li
 * Date: 2017/5/15 14:08
 * Version: 1.0
 */
public class UserPage extends Page {

    private String userId;

    private String userName;

    private String userStatus;

    private String acctName;

    private String createTime;

    private String keyWord;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAcctName() { return acctName; }

    public void setAcctName(String acctName) { this.acctName = acctName; }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserPage [userId=");
        builder.append(userId);
        builder.append(",userName=");
        builder.append(userName);
        builder.append(",acctName=");
        builder.append(acctName);
        builder.append(",userStatus=");
        builder.append(userStatus);
        builder.append(",createTime=");
        builder.append(createTime);
        builder.append(",keyWord=");
        builder.append(keyWord);
        builder.append("]");
        return builder.toString();
    }

}

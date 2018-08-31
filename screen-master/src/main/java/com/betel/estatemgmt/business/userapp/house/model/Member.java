package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: Member <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/20 9:45 <br/>
 * Version: 1.0 <br/>
 */
public class Member {
    /**
     * 成员id
     */
    private String hmId;
    /**
     * 真实姓名
     */
    private String userName;
    /**
     * 电话号码
     */
    private String userAccount;
    /**
     * 成员类型 1:户主，2：成员
     */
    private int type;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private String userid;

    @Override
    public String toString() {
        return "Member{" +
                "hmId=" + hmId +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", type=" + type +
                '}';
    }

    public String getHmId() {
        return hmId;
    }

    public void setHmId(String hmId) {
        this.hmId = hmId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

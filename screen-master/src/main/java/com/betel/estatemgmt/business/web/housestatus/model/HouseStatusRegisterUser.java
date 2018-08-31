package com.betel.estatemgmt.business.web.housestatus.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseStatusRegisterUser <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/17 15:03 <br/>
 * @version: 1.0
 */
public class    HouseStatusRegisterUser {
    /**
     * 成员注册账号usseId
     */
    private String memberUserId;
    /**
     * 成员姓名
     */
    private String memberRealName;
    /**
     * 成员手机号
     */
    private String phoneNum;
    /**
     * 成员密码
     */
    private String memberPwd;
    /**
     * 成员昵称
     */
    private String memberNickName;

    @Override
    public String toString() {
        return "HouseStatusRegisterUser{" +
                "memberUserId='" + memberUserId + '\'' +
                ", memberRealName='" + memberRealName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", memberNickName='" + memberNickName + '\'' +
                '}';
    }

    public String getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public String getMemberRealName() {
        return memberRealName;
    }

    public void setMemberRealName(String memberRealName) {
        this.memberRealName = memberRealName;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName;
    }
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}

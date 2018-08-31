package com.betel.estatemgmt.business.userapp.user.model;

/**
 * <p>
 * 用于用户修改密码时，封装手机号，新旧密码
 * </p>
 * ClassName: ValiInfo <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/17 9:27 <br/>
 * Version: 1.0 <br/>
 */
public class ValiInfo {
    //旧的密码
    private String oldPassword;
    //新的密码
    private String newPassword;
    //账户号（号码）
    private String phoneNum;
    //发送短信的Mark
    private String mark;
    //短信验证码
    private  String smsCode;
    //鉴定码，用于修改手机号
    private  String identificationId;

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getOldPasswod() {
        return oldPassword;
    }

    public void setOldPasswod(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "ValiInfo{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", mark='" + mark + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", identificationId='" + identificationId + '\'' +
                '}';
    }
}

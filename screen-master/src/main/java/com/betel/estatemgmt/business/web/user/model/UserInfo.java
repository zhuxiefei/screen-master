package com.betel.estatemgmt.business.web.user.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UserInfo <br/>
 * Author: Zhang Li  <br/>
 * Date: 2017/5/16 10:05 <br/>
 * Version: 1.0 <br/>
 */
public class UserInfo {
    /*
    用户Id
     */
    private String userId;
    /*
    用户昵称
     */
    private String userName;
    /*
    图像的id
     */
    private Long pictureId;
    /*
    图像路径
     */
    private String pictureUrl;
    /*
    用户性别
     */
    private String userGender;
    /*
    用户账号（号码）
     */
    private String acctName;

    @Override
    public String toString() {
        return "UserInfo{" +
                "acctName='" + acctName + '\'' +
                ", userGender=" + userGender +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", pictureId=" + pictureId +
                ", userName='" + userName + '\'' +
                '}';
    }

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

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }
}

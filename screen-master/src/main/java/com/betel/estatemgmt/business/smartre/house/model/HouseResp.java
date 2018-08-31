package com.betel.estatemgmt.business.smartre.house.model;

/**
 * Created by zhangjian on 2018/9/1.
 */
public class HouseResp {
    private String houseId;
    private String houseNum;
    /**
     * 类型 1；户主，2.成员认证
     */
    private int authType;

    @Override
    public String toString() {
        return "HouseResp{" +
                "houseId='" + houseId + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", authType=" + authType +
                '}';
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }
}

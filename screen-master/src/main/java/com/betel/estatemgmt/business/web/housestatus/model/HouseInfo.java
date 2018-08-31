package com.betel.estatemgmt.business.web.housestatus.model;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: HouseInfo <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/14 11:22 <br/>
 * @version: 1.0
 */
public class HouseInfo {
    private String houseId;
    private String houseNum;
    private String houseStatus;

    @Override
    public String toString() {
        return "HouseInfo{" +
                "houseId='" + houseId + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", houseStatus='" + houseStatus + '\'' +
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

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }
}

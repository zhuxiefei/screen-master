package com.betel.estatemgmt.business.userapp.house.model;

/**
 * <p>
 * $DES$
 * </p>
 * ClassName: $CLASSNAME$ <br/>
 * Author: zhouye  <br/>
 * Date: $DATA$ 20:34$ <br/>
 * Version: $VERSION$ <br/>
 */
public class HouseInfoApp {
    /**
     * 房屋id
     */
    String houseId;
    /**
     * 房屋名称
     */
    String houseName;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}

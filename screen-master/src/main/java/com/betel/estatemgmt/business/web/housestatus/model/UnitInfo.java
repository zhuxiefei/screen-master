package com.betel.estatemgmt.business.web.housestatus.model;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @className: UnitInfo <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/14 11:15 <br/>
 * @version: 1.0
 */
public class UnitInfo {
    private String unitId;
    private String unitName;
    private List<HouseStatusInfo> houseList;

    @Override
    public String toString() {
        return "UnitInfo{" +
                "unitId='" + unitId + '\'' +
                ", unitName='" + unitName + '\'' +
                ", houseList=" + houseList +
                '}';
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<HouseStatusInfo> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseStatusInfo> houseList) {
        this.houseList = houseList;
    }
}

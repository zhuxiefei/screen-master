package com.betel.estatemgmt.business.propertyapp.lease.model;

import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/12 12:04 <br/>
 * Version: 1.0 <br/>
 */
public class OfficeUnitOne {

    //单元名称
    private String unitName;
    //单元下的房屋
    private List<OfficeHouse> houses;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<OfficeHouse> getHouses() {
        return houses;
    }

    public void setHouses(List<OfficeHouse> houses) {
        this.houses = houses;
    }

    @Override
    public String toString() {
        return "OfficeUnitOne{" +
                "unitName='" + unitName + '\'' +
                ", houses=" + houses +
                '}';
    }
}
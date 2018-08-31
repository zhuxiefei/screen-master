package com.betel.estatemgmt.business.userapp.house.model;

import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UnitOne <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/20 9:30 <br/>
 * Version: 1.0 <br/>
 */
public class UnitOne {
    //单元名称
    private String unitName;
    //单元下的房屋
    private List<House> houses;

    private List<OfficeHouse> officeHouses;

    public List<OfficeHouse> getOfficeHouses() {
        return officeHouses;
    }

    public void setOfficeHouses(List<OfficeHouse> officeHouses) {
        this.officeHouses = officeHouses;
    }

    @Override
    public String toString() {
        return "UnitOne{" +
                "unitName='" + unitName + '\'' +
                ", houses=" + houses +
                '}';
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }
}

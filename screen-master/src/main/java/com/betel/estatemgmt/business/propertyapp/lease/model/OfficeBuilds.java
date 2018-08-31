package com.betel.estatemgmt.business.propertyapp.lease.model;

import com.betel.estatemgmt.business.userapp.house.model.UnitOne;
import com.betel.estatemgmt.common.model.house.House;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/12 12:03 <br/>
 * Version: 1.0 <br/>
 */
public class OfficeBuilds {

    //楼宇名称
    private String buildingName;
    //楼宇的单元集合
    private List<OfficeUnitOne> units;
    //楼宇下的房屋
    private List<House> littltvilla;


    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<OfficeUnitOne> getUnits() {
        return units;
    }

    public void setUnits(List<OfficeUnitOne> units) {
        this.units = units;
    }

    public List<House> getLittltvilla() {
        return littltvilla;
    }

    public void setLittltvilla(List<House> littltvilla) {
        this.littltvilla = littltvilla;
    }

    @Override
    public String toString() {
        return "OfficeBuilds{" +
                "buildingName='" + buildingName + '\'' +
                ", units=" + units +
                ", littltvilla=" + littltvilla +
                '}';
    }
}
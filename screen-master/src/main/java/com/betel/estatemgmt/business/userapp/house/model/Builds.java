package com.betel.estatemgmt.business.userapp.house.model;

import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: builds <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/20 9:28 <br/>
 * Version: 1.0 <br/>
 */
public class Builds {
    //楼宇名称
    private String buildingName;
    //楼宇的单元集合
    private List<UnitOne> units;
    //楼宇下的房屋
    private List<House> littltvilla;

    @Override
    public String toString() {
        return "builds{" +
                "buildingName='" + buildingName + '\'' +
                ", units=" + units +
                ", Littltvilla=" + littltvilla +
                '}';
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<UnitOne> getUnits() {
        return units;
    }

    public void setUnits(List<UnitOne> units) {
        this.units = units;
    }

    public List<House> getLittltvilla() {
        return littltvilla;
    }

    public void setLittltvilla(List<House> littltvilla) {
        this.littltvilla = littltvilla;
    }
}

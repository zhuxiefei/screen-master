package com.betel.estatemgmt.business.propertyapp.lease.model;

import com.betel.estatemgmt.business.userapp.house.model.Builds;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/11 19:53 <br/>
 * Version: 1.0 <br/>
 */
public class AllHouseOffice {

    //楼宇集合
    private List<OfficeBuilds> builds;
    //别墅
    private List<House> villa;

    public List<OfficeBuilds> getBuilds() {
        return builds;
    }

    public void setBuilds(List<OfficeBuilds> builds) {
        this.builds = builds;
    }

    public List<House> getVilla() {
        return villa;
    }

    public void setVilla(List<House> villa) {
        this.villa = villa;
    }

    @Override
    public String toString() {
        return "AllHouseOffice{" +
                "builds=" + builds +
                ", villa=" + villa +
                '}';
    }
}
package com.betel.estatemgmt.business.userapp.house.model;

import com.betel.estatemgmt.common.model.house.House;

import java.util.List;

/**
 * <p>
 * 全部房屋
 * </p>
 * ClassName: AllHouse <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/20 9:21 <br/>
 * Version: 1.0 <br/>
 */
public class AllHouse {
    //楼宇集合
    private List<Builds> builds;
    //别墅
    private List<House> villa;

    @Override
    public String toString() {
        return "AllHouse{" +
                "Builds=" + builds +
                ", Littltvilla=" + villa +
                '}';
    }

    public List<Builds> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Builds> builds) {
        this.builds = builds;
    }

    public List<House> getVilla() {
        return villa;
    }

    public void setVilla(List<House> villa) {
        this.villa = villa;
    }
}

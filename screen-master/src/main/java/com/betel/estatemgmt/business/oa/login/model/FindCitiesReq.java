package com.betel.estatemgmt.business.oa.login.model;

/**
 * Created by Administrator on 2018/5/7/007.
 */
public class FindCitiesReq{

    private String estateIds;

    private String cityName;

    private String cityId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEstateIds() {
        return estateIds;
    }

    public void setEstateIds(String estateIds) {
        this.estateIds = estateIds;
    }

    @Override
    public String toString() {
        return "FindCitiesReq{" +
                "estateIds='" + estateIds + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityId='" + cityId + '\'' +
                '}';
    }
}

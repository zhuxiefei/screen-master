package com.betel.estatemgmt.business.web.house.model;

import java.util.List;

/**
 * <p>
 * 添加房屋入参
 * </p>
 * ClassName: AddHouseReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:36 <br/>
 * Version: 1.0 <br/>
 */
public class AddHouseReq {
    private Long buildingId;

    private Long unitId;

    private String houseNum;

    private Long typeId;

    private String floorArea;

    private String interFloorArea;

    private String buyPark;

    private List<String> carSeatNumBuy;

    private List<String> plateNumBuy;

    private String rentalPark;

    private List<String> carSeatNumRent;

    private List<String> plateNumRent;

    private String estateId;

    public AddHouseReq(){}

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getBuyPark() {
        return buyPark;
    }

    public void setBuyPark(String buyPark) {
        this.buyPark = buyPark;
    }

    public List<String> getCarSeatNumBuy() {
        return carSeatNumBuy;
    }

    public void setCarSeatNumBuy(List<String> carSeatNumBuy) {
        this.carSeatNumBuy = carSeatNumBuy;
    }

    public List<String> getPlateNumBuy() {
        return plateNumBuy;
    }

    public void setPlateNumBuy(List<String> plateNumBuy) {
        this.plateNumBuy = plateNumBuy;
    }

    public String getRentalPark() {
        return rentalPark;
    }

    public void setRentalPark(String rentalPark) {
        this.rentalPark = rentalPark;
    }

    public List<String> getCarSeatNumRent() {
        return carSeatNumRent;
    }

    public void setCarSeatNumRent(List<String> carSeatNumRent) {
        this.carSeatNumRent = carSeatNumRent;
    }

    public List<String> getPlateNumRent() {
        return plateNumRent;
    }

    public void setPlateNumRent(List<String> plateNumRent) {
        this.plateNumRent = plateNumRent;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(String interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    @Override
    public String toString() {
        return "AddHouseReq{" +
                "buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", houseNum='" + houseNum + '\'' +
                ", typeId=" + typeId +
                ", floorArea='" + floorArea + '\'' +
                ", interFloorArea='" + interFloorArea + '\'' +
                ", buyPark='" + buyPark + '\'' +
                ", carSeatNumBuy=" + carSeatNumBuy +
                ", plateNumBuy=" + plateNumBuy +
                ", rentalPark='" + rentalPark + '\'' +
                ", carSeatNumRent=" + carSeatNumRent +
                ", plateNumRent=" + plateNumRent +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

package com.betel.estatemgmt.business.web.house.model;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: UpdateHouseReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:44 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateHouseReq {
    private String houseId;

    private String buildingId;

    private String unitId;

    private String houseNum;

    private String typeId;

    private String floorArea;

    private String interFloorArea;

    private String buyPark;

    private String carSeatNumBuy;

    private String plateNumBuy;

    private String rentalPark;

    private String carSeatNumRent;

    private String plateNumRent;

    private String displayOrder;

    private String estateId;

    public String getHouseId() {
        return houseId;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public UpdateHouseReq(){};

    public String getBuyPark() {
        return buyPark;
    }

    public void setBuyPark(String buyPark) {
        this.buyPark = buyPark;
    }

    public String getRentalPark() {
        return rentalPark;
    }

    public void setRentalPark(String rentalPark) {
        this.rentalPark = rentalPark;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
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

    public String getCarSeatNumBuy() {
        return carSeatNumBuy;
    }

    public void setCarSeatNumBuy(String carSeatNumBuy) {
        this.carSeatNumBuy = carSeatNumBuy;
    }

    public String getPlateNumBuy() {
        return plateNumBuy;
    }

    public void setPlateNumBuy(String plateNumBuy) {
        this.plateNumBuy = plateNumBuy;
    }

    public String getCarSeatNumRent() {
        return carSeatNumRent;
    }

    public void setCarSeatNumRent(String carSeatNumRent) {
        this.carSeatNumRent = carSeatNumRent;
    }

    public String getPlateNumRent() {
        return plateNumRent;
    }

    public void setPlateNumRent(String plateNumRent) {
        this.plateNumRent = plateNumRent;
    }

    @Override
    public String toString() {
        return "UpdateHouseReq{" +
                "houseId=" + houseId +
                ", buildingId='" + buildingId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", typeId='" + typeId + '\'' +
                ", floorArea='" + floorArea + '\'' +
                ", interFloorArea='" + interFloorArea + '\'' +
                ", buyPark='" + buyPark + '\'' +
                ", carSeatNumBuy='" + carSeatNumBuy + '\'' +
                ", plateNumBuy='" + plateNumBuy + '\'' +
                ", rentalPark='" + rentalPark + '\'' +
                ", carSeatNumRent='" + carSeatNumRent + '\'' +
                ", plateNumRent='" + plateNumRent + '\'' +
                ", displayOrder='" + displayOrder + '\'' +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}

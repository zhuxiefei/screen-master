package com.betel.estatemgmt.business.web.house.model;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 房屋详情回参
 * </p>
 * ClassName: HouseInformation <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:01 <br/>
 * Version: 1.0 <br/>
 */
public class HouseInformation {
    private String houseId;

    private String houseNum;

    private String buildingName;

    private Long buildingId;

    private String unitName;

    private Long unitId;

    private String typeName;

    private Long typeId;

    private String houseDesc;

    private Double floorArea;

    private Double interFloorArea;

    private Date deliverTime;

    private String displayOrder;

    private Integer houseStatus;

    private Integer petStatus;

    private Integer buyPark;

    private List<String> carSeatNumBuy;

    private List<String> plateNumBuy;

    private Integer rentalPark;

    private List<String> carSeatNumRent;

    private List<String> plateNumRent;

    private List<MemberInfo> memberInfoList;

    private List<DecorationApply> decorationApply;



    public Integer getBuyPark() {
        return buyPark;
    }

    public void setBuyPark(Integer buyPark) {
        this.buyPark = buyPark;
    }

    public Integer getRentalPark() {
        return rentalPark;
    }

    public void setRentalPark(Integer rentalPark) {
        this.rentalPark = rentalPark;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getHouseDesc() {
        return houseDesc;
    }

    public void setHouseDesc(String houseDesc) {
        this.houseDesc = houseDesc;
    }

    public Double getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
    }

    public Double getInterFloorArea() {
        return interFloorArea;
    }

    public void setInterFloorArea(Double interFloorArea) {
        this.interFloorArea = interFloorArea;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Integer getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(Integer petStatus) {
        this.petStatus = petStatus;
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

    public List<MemberInfo> getMemberInfoList() {
        return memberInfoList;
    }

    public void setMemberInfoList(List<MemberInfo> memberInfoList) {
        this.memberInfoList = memberInfoList;
    }

    public List<DecorationApply> getDecorationApply() {
        return decorationApply;
    }

    public void setDecorationApply(List<DecorationApply> decorationApply) {
        this.decorationApply = decorationApply;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseInformation{");
        sb.append("houseId=").append(houseId);
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", buildingName='").append(buildingName).append('\'');
        sb.append(", buildingId=").append(buildingId);
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", unitId=").append(unitId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", typeId=").append(typeId);
        sb.append(", houseDesc='").append(houseDesc).append('\'');
        sb.append(", floorArea=").append(floorArea);
        sb.append(", interFloorArea=").append(interFloorArea);
        sb.append(", deliverTime=").append(deliverTime);
        sb.append(", displayOrder='").append(displayOrder).append('\'');
        sb.append(", houseStatus=").append(houseStatus);
        sb.append(", petStatus=").append(petStatus);
        sb.append(", buyPark='").append(buyPark).append('\'');
        sb.append(", carSeatNumBuy=").append(carSeatNumBuy);
        sb.append(", plateNumBuy=").append(plateNumBuy);
        sb.append(", rentalPark='").append(rentalPark).append('\'');
        sb.append(", carSeatNumRent=").append(carSeatNumRent);
        sb.append(", plateNumRent=").append(plateNumRent);
        sb.append(", memberInfoList=").append(memberInfoList);
        sb.append(", decorationApply=").append(decorationApply);
        sb.append('}');
        return sb.toString();
    }
}

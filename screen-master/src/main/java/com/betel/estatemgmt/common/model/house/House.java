package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class House {
    private String houseId;

    private String houseNum;

    private String houseDesc;

    private Long buildingId;

    private Long unitId;

    private Long typeId;

    private Date createTime;

    private Double floorArea;

    private Double interFloorArea;

    private Date deliverTime;

    private Integer houseStatus;

    private Integer petStatus;

    private Integer displayOrder;

    private String estateId;

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
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
        this.houseNum = houseNum == null ? null : houseNum.trim();
    }

    public String getHouseDesc() {
        return houseDesc;
    }

    public void setHouseDesc(String houseDesc) {
        this.houseDesc = houseDesc == null ? null : houseDesc.trim();
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "House{" +
                "houseId=" + houseId +
                ", houseNum='" + houseNum + '\'' +
                ", houseDesc='" + houseDesc + '\'' +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", typeId=" + typeId +
                ", createTime=" + createTime +
                ", floorArea=" + floorArea +
                ", interFloorArea=" + interFloorArea +
                ", deliverTime=" + deliverTime +
                ", houseStatus=" + houseStatus +
                ", petStatus=" + petStatus +
                ", displayOrder=" + displayOrder +
                ", estateId='" + estateId + '\'' +
                '}';
    }
}
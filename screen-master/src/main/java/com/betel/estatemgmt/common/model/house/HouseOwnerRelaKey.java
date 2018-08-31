package com.betel.estatemgmt.common.model.house;

public class HouseOwnerRelaKey {
    private String ownerId;

    private String houseId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
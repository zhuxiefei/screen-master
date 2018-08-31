package com.betel.estatemgmt.common.model.admin;

public class AdminEstateRelaKey {
    private String adminId;

    private String estateId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId == null ? null : estateId.trim();
    }
}
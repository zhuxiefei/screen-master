package com.betel.estatemgmt.business.web.login.model;

/**
 * Created by Administrator on 2018/5/7/007.
 */
public class FindSystemsReq {

    private String estateId;

    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    @Override
    public String toString() {
        return "FindSystemsReq{" +
                "estateId='" + estateId + '\'' +
                ", adminId='" + adminId + '\'' +
                '}';
    }
}

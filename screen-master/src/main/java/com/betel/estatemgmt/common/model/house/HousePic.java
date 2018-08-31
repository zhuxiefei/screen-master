package com.betel.estatemgmt.common.model.house;

import java.util.Date;

public class HousePic {
    private Long hpId;

    private String houseId;

    private String pictureId;

    private String cadId;

    private String pictureType;

    private Date createTime;

    public Long getHpId() {
        return hpId;
    }

    public void setHpId(Long hpId) {
        this.hpId = hpId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getCadId() {
        return cadId;
    }

    public void setCadId(String cadId) {
        this.cadId = cadId;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType == null ? null : pictureType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HousePic{");
        sb.append("cadId=").append(cadId);
        sb.append(", hpId=").append(hpId);
        sb.append(", houseId=").append(houseId);
        sb.append(", pictureId=").append(pictureId);
        sb.append(", pictureType='").append(pictureType).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
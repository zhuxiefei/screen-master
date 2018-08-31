package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 图纸信息回参
 * </p>
 * ClassName: PictureInfo <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:03 <br/>
 * Version: 1.0 <br/>
 */
public class PictureInfo {
    private Long hpId;

    private String typeName;

    private String pictureId;

    private String pictureUrl;

    private String cadId;

    private String cadName;

    public Long getHpId() {
        return hpId;
    }

    public void setHpId(Long hpId) {
        this.hpId = hpId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCadId() {
        return cadId;
    }

    public void setCadId(String cadId) {
        this.cadId = cadId;
    }

    public String getCadName() {
        return cadName;
    }

    public void setCadName(String cadName) {
        this.cadName = cadName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PictureInfo{");
        sb.append("cadId=").append(cadId);
        sb.append(", hpId=").append(hpId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", pictureId=").append(pictureId);
        sb.append(", pictureUrl='").append(pictureUrl).append('\'');
        sb.append(", cadName='").append(cadName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

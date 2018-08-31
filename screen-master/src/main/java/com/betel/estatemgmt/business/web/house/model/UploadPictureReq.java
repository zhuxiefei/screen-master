package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 上传图片入参
 * </p>
 * ClassName: UploadPictureReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:46 <br/>
 * Version: 1.0 <br/>
 */
public class UploadPictureReq {
    private String houseId;

    private String pictureType;

    private Integer pictureFlag;//0代表预览图片  1代表CAD文件

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public Integer getPictureFlag() {
        return pictureFlag;
    }

    public void setPictureFlag(Integer pictureFlag) {
        this.pictureFlag = pictureFlag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadPictureReq{");
        sb.append("houseId=").append(houseId);
        sb.append(", pictureType='").append(pictureType).append('\'');
        sb.append(", pictureFlag=").append(pictureFlag);
        sb.append('}');
        return sb.toString();
    }
}

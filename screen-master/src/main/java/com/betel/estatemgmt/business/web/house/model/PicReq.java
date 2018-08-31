package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 删除房屋图纸、CAD文件接口入参类
 * </p>
 * ClassName: PicReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/6/21 8:55 <br/>
 * Version: 1.0 <br/>
 */
public class PicReq {

    /**
     * 房屋图片关系主键id
     */
    private Long hpId;
    /**
     * 文件标识：0代表预览图片;1代表CAD文件
     */
    private Integer pictureFlag;

    public Long getHpId() {
        return hpId;
    }

    public void setHpId(Long hpId) {
        this.hpId = hpId;
    }

    public Integer getPictureFlag() {
        return pictureFlag;
    }

    public void setPictureFlag(Integer pictureFlag) {
        this.pictureFlag = pictureFlag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PicReq{");
        sb.append("hpId=").append(hpId);
        sb.append(", pictureFlag=").append(pictureFlag);
        sb.append('}');
        return sb.toString();
    }
}

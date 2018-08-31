package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 下载删除图片入参
 * </p>
 * ClassName: DownloadPictureReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:48 <br/>
 * Version: 1.0 <br/>
 */
public class DownloadPictureReq {
    private Long pictureId;

    private Integer pictureFlag;//0代表预览图片  1代表CAD文件

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getPictureFlag() {
        return pictureFlag;
    }

    public void setPictureFlag(Integer pictureFlag) {
        this.pictureFlag = pictureFlag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DownloadPictureReq{");
        sb.append("pictureFlag=").append(pictureFlag);
        sb.append(", pictureId=").append(pictureId);
        sb.append('}');
        return sb.toString();
    }
}

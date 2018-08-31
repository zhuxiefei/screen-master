package com.betel.estatemgmt.business.web.repair.model;

/**
 * <p>
 * 上传富文本编辑图片
 * </p>
 * ClassName: UploadEditPicReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/23 9:37 <br/>
 * Version: 1.0 <br/>
 */
public class UploadEditPicReq {

    private String picFlag;

    private String picRelation;

    public String getPicFlag() {
        return picFlag;
    }

    public void setPicFlag(String picFlag) {
        this.picFlag = picFlag;
    }

    public String getPicRelation() {
        return picRelation;
    }

    public void setPicRelation(String picRelation) {
        this.picRelation = picRelation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateEditPicReq{");
        sb.append("picFlag='").append(picFlag).append('\'');
        sb.append(", picRelation='").append(picRelation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

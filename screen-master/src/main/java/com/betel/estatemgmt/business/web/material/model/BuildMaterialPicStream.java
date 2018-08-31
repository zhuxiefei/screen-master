package com.betel.estatemgmt.business.web.material.model;

import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * <p>
 * 关于家装建材导入操作的图片流对象
 * </p>
 * ClassName: BuildMaterialPicStream <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialPicStream {
    private Long picId;
    private String picName;
    private Long picSize;
    private InputStream picInputStream;
    private ZipEntry ze;

    public ZipEntry getZe() {
        return ze;
    }

    public void setZe(ZipEntry ze) {
        this.ze = ze;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Long getPicSize() {
        return picSize;
    }

    public void setPicSize(Long picSize) {
        this.picSize = picSize;
    }

    public InputStream getPicInputStream() {
        return picInputStream;
    }

    public void setPicInputStream(InputStream picInputStream) {
        this.picInputStream = picInputStream;
    }
}

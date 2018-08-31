package com.betel.estatemgmt.utils.QRcode;

import java.io.File;


/**
 * <p>
 * 二维码信息
 * </p>
 *
 * @className: PhotoProDto <br/>
 * @author: jian.z  <br/>
 * @date: 2017/11/30 17:24 <br/>
 * @version: 1.0
 */
public class PhotoProDto {
    /**
     * 二维码宽度
     */
    private int width;
    /**
     * 二维码高度
     */
    private int height;
    /**
     * 二维码内容（包括链接）
     */
    private String content;
    /**
     * 是否添加loge
     */
    private boolean hasLogo;
    /**
     * logo图片
     */
    private File logoFile;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHasLogo() {
        return hasLogo;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public File getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

}

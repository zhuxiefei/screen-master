package com.betel.estatemgmt.business.web.material.model;

import java.io.InputStream;

/**
 * <p>
 * 关于家装建材批量导入操作的文件流对象
 * </p>
 * ClassName: BuildMaterialFileStream <br/>
 * Author: zhangjian <br/>
 * Date: 2017/6/21 14:21 <br/>
 * Version: 1.0 <br/>
 */
public class BuildMaterialFileStream {
    private String fileName;
    private Long fileSize;
    private InputStream in;
    private boolean isFile;//true  是文件，false  是路径

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean isFile) {
        this.isFile = isFile;
    }

}

package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 文件路径回参
 * </p>
 * ClassName: FileUrl <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/7/4 16:42 <br/>
 * Version: 1.0 <br/>
 */
public class FileUrl {
    private String fileUrl;

    private String fileName;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileUrl{");
        sb.append("fileUrl='").append(fileUrl).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.betel.estatemgmt.business.userapp.document.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DocUrl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/10 14:10 <br/>
 * Version: 1.0 <br/>
 */
public class DocUrl {

    private String fileUrl;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocUrl{");
        sb.append("fileUrl='").append(fileUrl).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

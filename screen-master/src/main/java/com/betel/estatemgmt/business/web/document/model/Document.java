package com.betel.estatemgmt.business.web.document.model;

import java.util.Date;

/**
 * <p>
 * 据资料类别查询所有资料回参
 * </p>
 * ClassName: Document <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 15:49 <br/>
 * Version: 1.0 <br/>
 */
public class Document {

    private String docId;

    private String docName;

    private String adminName;

    private Date createTime;

    //0表示能下载 1表示不能下载
    private String isDownload;

    public String getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(String isDownload) {
        this.isDownload = isDownload;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("docId='").append(docId).append('\'');
        sb.append(", docName='").append(docName).append('\'');
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", isDownload=").append(isDownload);
        sb.append('}');
        return sb.toString();
    }
}

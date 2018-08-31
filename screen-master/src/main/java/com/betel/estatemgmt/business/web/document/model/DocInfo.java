package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 查询资料详情回参
 * </p>
 * ClassName: DocInfo <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 16:03 <br/>
 * Version: 1.0 <br/>
 */
public class DocInfo {

    private String docId;

    private String docName;

    private String typeName;

    private String docContent;

    //0表示能下载 1表示不能下载
    private Integer isDownload;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public Integer getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(Integer isDownload) {
        this.isDownload = isDownload;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocInfo{");
        sb.append("docId='").append(docId).append('\'');
        sb.append(", docName='").append(docName).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", docContent='").append(docContent).append('\'');
        sb.append(", isDownload=").append(isDownload);
        sb.append('}');
        return sb.toString();
    }
}

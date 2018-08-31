package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 编辑资料入参
 * </p>
 * ClassName: UpdateDocReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 17:35 <br/>
 * Version: 1.0 <br/>
 */
public class UpdateDocReq {

    private String docId;

    private String docName;

    private String typeId;

    private String docContent;

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateDocReq{");
        sb.append("docId='").append(docId).append('\'');
        sb.append(", docName='").append(docName).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", docContent='").append(docContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

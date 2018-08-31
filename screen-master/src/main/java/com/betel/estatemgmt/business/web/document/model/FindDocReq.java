package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 查询资料详情入参
 * </p>
 * ClassName: FindDocReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 15:59 <br/>
 * Version: 1.0 <br/>
 */
public class FindDocReq {

    private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindDocReq{");
        sb.append("docId='").append(docId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

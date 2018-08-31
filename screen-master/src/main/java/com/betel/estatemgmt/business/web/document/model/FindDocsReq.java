package com.betel.estatemgmt.business.web.document.model;

/**
 * <p>
 * 根据资料类别查询所有资料入参
 * </p>
 * ClassName: FindDocsReq <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/9 15:39 <br/>
 * Version: 1.0 <br/>
 */
public class FindDocsReq {

    private String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindDocsReq{");
        sb.append("typeId='").append(typeId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

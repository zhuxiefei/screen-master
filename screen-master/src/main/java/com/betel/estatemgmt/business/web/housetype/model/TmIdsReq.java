package com.betel.estatemgmt.business.web.housetype.model;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: TmIdsReq <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/21 14:37 <br/>
 * Version: 1.0 <br/>
 */
public class TmIdsReq {

    private Long typeId;

    public String getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }

    private String functionIds;

    private String tmIds;

    public String getTmIds() {
        return tmIds;
    }

    public void setTmIds(String tmIds) {

        this.tmIds = tmIds;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TmIdsReq{");
        sb.append("tmIds='").append(tmIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

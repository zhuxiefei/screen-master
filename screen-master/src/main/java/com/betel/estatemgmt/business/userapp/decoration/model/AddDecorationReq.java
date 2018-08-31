package com.betel.estatemgmt.business.userapp.decoration.model;

import com.betel.estatemgmt.business.userapp.decoration.code.DecorationCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <p>
 * 装修申请时的入参
 * </p>
 * ClassName: AddDecorationReq <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/24 10:29 <br/>
 * Version: 1.0 <br/>
 */
public class AddDecorationReq {

    private String recordId;

    @NotNull(message = DecorationCode.HOUSEID_NULL)
    private String houseId;

    @NotNull(message = DecorationCode.DECORATION_COMPANY_NULL)
    private String decorationCompany;

    @NotNull(message = DecorationCode.START_TIME_NULL)
    private String startTime;

    private String endTime;

    @NotNull(message = DecorationCode.DECORATION_CYCLE_NULL)
    private Integer decorationCycle;

    @Size(max = 500, message = DecorationCode.DECORATION_DESC_FORMAT)
    private String decorationDesc;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getDecorationCompany() {
        return decorationCompany;
    }

    public void setDecorationCompany(String decorationCompany) {
        this.decorationCompany = decorationCompany;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDecorationCycle() {
        return decorationCycle;
    }

    public void setDecorationCycle(Integer decorationCycle) {
        this.decorationCycle = decorationCycle;
    }

    public String getDecorationDesc() {
        return decorationDesc;
    }

    public void setDecorationDesc(String decorationDesc) {
        this.decorationDesc = decorationDesc;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddDecorationReq{");
        sb.append("recordId='").append(recordId).append('\'');
        sb.append(", houseId=").append(houseId);
        sb.append(", decorationCompany='").append(decorationCompany).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", decorationCycle=").append(decorationCycle);
        sb.append(", decorationDesc='").append(decorationDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

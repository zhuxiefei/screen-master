package com.betel.estatemgmt.business.web.house.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: DecorationApply <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/24 16:11 <br/>
 * Version: 1.0 <br/>
 */
public class DecorationApply {

    private String decorationCompany;

    private Integer decorationCycle;

    private Date createTime;

    private Date startTime;

    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDecorationCompany() {
        return decorationCompany;
    }

    public void setDecorationCompany(String decorationCompany) {
        this.decorationCompany = decorationCompany;
    }

    public Integer getDecorationCycle() {
        return decorationCycle;
    }

    public void setDecorationCycle(Integer decorationCycle) {
        this.decorationCycle = decorationCycle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DecorationApply{");
        sb.append("decorationCompany='").append(decorationCompany).append('\'');
        sb.append(", decorationCycle=").append(decorationCycle);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }
}

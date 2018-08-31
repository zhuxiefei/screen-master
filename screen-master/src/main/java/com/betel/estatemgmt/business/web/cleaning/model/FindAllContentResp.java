package com.betel.estatemgmt.business.web.cleaning.model;

import java.util.Date;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: FindAllContentResp <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 15:10 <br/>
 * Version: 1.0 <br/>
 */
public class FindAllContentResp {

    private String contentId;

    private String contentNo;

    private String areaName;

    private String typeName;

    private String location;

    private Integer cycle;

    private Date createTime;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FindAllContentResp{");
        sb.append("contentId='").append(contentId).append('\'');
        sb.append(", contentNo='").append(contentNo).append('\'');
        sb.append(", areaName='").append(areaName).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", cycle=").append(cycle);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

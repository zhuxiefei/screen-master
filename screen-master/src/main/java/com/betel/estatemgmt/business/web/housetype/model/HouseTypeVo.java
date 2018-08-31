package com.betel.estatemgmt.business.web.housetype.model;

import java.util.Date;

/**
 * <p>
 * 文件说明
 * </p>
 * ClassName: HouseTypeVo <br/>
 * Author: geyf  <br/>
 * Date: 2017/6/20 9:10 <br/>
 * Version: 1.0 <br/>
 */
public class HouseTypeVo {

    private Long typeId;

    private String typeName;

    private Date createTime;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return null;
        }
        return (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = null;
        } else {
            this.createTime = (Date) createTime.clone();
        }

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HouseTypeVo{");
        sb.append("typeId=").append(typeId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

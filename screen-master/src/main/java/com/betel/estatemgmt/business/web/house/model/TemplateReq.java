package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: TemplateReq <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/29 11:18 <br/>
 * Version: 1.0 <br/>
 */
public class TemplateReq {
    private Integer templateFlag;

    public Integer getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Integer templateFlag) {
        this.templateFlag = templateFlag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TemplateReq{");
        sb.append("templateFlag=").append(templateFlag);
        sb.append('}');
        return sb.toString();
    }
}

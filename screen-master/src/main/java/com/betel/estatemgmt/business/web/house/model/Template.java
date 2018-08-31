package com.betel.estatemgmt.business.web.house.model;

/**
 * <p>
 * 下载模板回参
 * </p>
 * ClassName: Template <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/29 11:16 <br/>
 * Version: 1.0 <br/>
 */
public class Template {
    private String templateUrl;

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Template{");
        sb.append("templateUrl='").append(templateUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

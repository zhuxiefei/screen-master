package com.betel.estatemgmt.business.userapp.hotline.model;

/**
 * <p>
 * 查询热线电话回参
 * </p>
 * ClassName: Hotline <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/10 16:47 <br/>
 * Version: 1.0 <br/>
 */
public class Hotline {

    private String hotlineName;

    private String hotline;

    public String getHotlineName() {
        return hotlineName;
    }

    public void setHotlineName(String hotlineName) {
        this.hotlineName = hotlineName;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hotline{");
        sb.append("hotlineName='").append(hotlineName).append('\'');
        sb.append(", hotline='").append(hotline).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

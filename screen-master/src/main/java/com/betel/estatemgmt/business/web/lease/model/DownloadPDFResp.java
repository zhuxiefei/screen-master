package com.betel.estatemgmt.business.web.lease.model;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 18:33 <br/>
 * Version: 1.0 <br/>
 */
public class DownloadPDFResp {

    private String URL;

    private String name;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DownloadPDFResp{" +
                "URL='" + URL + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
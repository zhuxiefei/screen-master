/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package com.betel.estatemgmt.business.web.config.model;

/**
 * <p>
 * 配置详情
 * </p>
 * ClassName: ConfigInfo <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/16 8:59 <br/>
 * Version: 1.0 <br/>
 */
public class ConfigInfo {

    /**
     * token失效时间
     */
    private int tokenExpireTime;

    public int getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(int tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigInfo{");
        sb.append("tokenExpireTime=").append(tokenExpireTime);
        sb.append('}');
        return sb.toString();
    }
}

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
 * 配置名称常量
 * </p>
 * ClassName: ConfigName <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/15 16:57 <br/>
 * Version: 1.0 <br/>
 */
public interface ConfigName {

    /**
     * Openfire服务器IP
     */
    String OPENFIRE_SERVER = "openfire.server";
    /**
     * Openfire域
     */
    String OPENFIRE_DOMAIN = "openfire.domain";
    /**
     * Openfire服务器用户名
     */
    String OPENFIRE_USER = "openfire.user";
    /**
     * Openfire服务器用户密码
     */
    String OPENFIRE_PASSWORD = "openfire.password";
    /**
     * Openfire客户端连接端口
     */
    String OPENFIRE_CLIENT_PORT = "openfire.client.port";
    /**
     * Openfire服务器连接端口
     */
    String OPENFIRE_SERVER_PORT = "openfire.server.port";

    /**
     * token失效时间，单位：小时
     */
    String TOKEN_EXPIRE_TIME = "token.expire.time";

    /**
     * redis服务器IP
     */
    String REDIS_HOST = "redis.host";

    /**
     * redis密码
     */
    String REDIS_PASSWORD = "redis.password";

    /**
     * redis端口
     */
    String REDIS_PORT = "redis.port";

    /**
     * 短信验证码失效时间，单位：分钟
     */
    String SMS_EXPIRE_TIME = "sms.expire.time";

    /**
     * 文件服务器地址
     */
    String FILE_SERVER = "file.server";

    /**
     * 文件服务器存储物理文件夹（根目录）
     */
    String FILE_DIR = "file.dir";

    /**
     * 隐私文件服务器存储物理文件夹（根目录）
     */
    String PRIVACY_FILE_DIR = "privacy.file.dir";

    /**
     * 用户App极光推送appKey
     */
    String USER_APP_KEY = "user.jpush.appkey";

    /**
     * 用户App极光推送master secret
     */
    String USER_MASTERSECRET = "user.jpush.master.secret";

    /**
     * 物管App极光推送appKey
     */
    String PROPERTY_APP_KEY = "property.jpush.appkey";

    /**
     * 物管App极光推送master secret
     */
    String PROPERTY_MASTERSECRET = "property.jpush.master.secret";

    /**
     * 系统交付时间
     */
    String SYSTEM_DELIVER_TIME = "system.deliver.time";

    /**
     * 调用OA接口的URL
     */
    String OA_PROJECT_URL = "oa.project.url";

    /**
     * 极光环境:false为开发，true为生产
     */
    String JPUSH_ENVIRONMENT = "jpush.environment";

    /**
     * 调用SMARTRE接口的URL
     */
    String SMARTRE_PROJECT_URL = "smartre.project.url";

    /**
     * 调用HOME接口的URL
     */
    String HOME_PROJECT_URL = "home.project.url";
}

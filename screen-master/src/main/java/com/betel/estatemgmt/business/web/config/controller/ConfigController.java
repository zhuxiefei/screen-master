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

package com.betel.estatemgmt.business.web.config.controller;

import com.betel.estatemgmt.business.web.config.code.ConfigCode;
import com.betel.estatemgmt.business.web.config.model.ConfigInfo;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 配置管理接口
 * </p>
 * ClassName: ConfigController <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/15 13:51 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/config")
public class ConfigController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    /**
     * <p>
     * 查询所有配置
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/16 11:45
     *
     * @return 当前系统所有配置项
     */
    @RequiresPermissions(value = "config-findAllConfig")
    @RequestMapping(value = "v1/findAllConfig", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<ConfigInfo> findAllConfig() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/config/v1/findAllConfig start========");
        }

        Response<ConfigInfo> response = new Response<>();
        try {
            ConfigInfo configInfo = new ConfigInfo();
            //读取当前系统的token失效时间配置
            configInfo.setTokenExpireTime(Integer.parseInt(ConfigManager.read(ConfigName.TOKEN_EXPIRE_TIME)));
            response.setData(configInfo);
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("========web/config/v1/findAllConfig error========", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/config/v1/findAllConfig end==========");
        }
        return response;
    }

    /**
     * <p>
     * 系统配置修改（全部配置）
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/16 11:46
     *
     * @param configInfo 系统配置信息
     * @return 执行结果
     */
    @RequiresPermissions(value = "config-updateConfig")
    @RequestMapping(value = "v1/updateConfig", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateConfig(@RequestBody ConfigInfo configInfo) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/config/v1/updateConfig start========" + configInfo);
        }
        Response<String> response = new Response<>();
        try {
            //修改token失效时间
            //使用int型做入参，不用校验空的情况
            int tokenExpireTime = configInfo.getTokenExpireTime();
            //校验token失效时间范围
            if (tokenExpireTime < 1 || tokenExpireTime > 2160) {
                response.setCode(ConfigCode.TOKEN_OUT_OF_RANGE);
                return response;
            } else {
                ConfigManager.update(ConfigName.TOKEN_EXPIRE_TIME, String.valueOf(tokenExpireTime));
            }
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("========web/config/v1/updateConfig error========", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/config/v1/updateConfig end==========");
        }
        return response;
    }
}

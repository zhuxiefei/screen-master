package com.betel.estatemgmt.business.smartre.configuration.controller;

import com.betel.estatemgmt.business.web.config.model.ConfigInfo;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
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
 * ClassName: ConfigurationController <br/>
 * Author: jians.z  <br/>
 * Date: 2018/2/1 13:51 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("httpclientProperty/config")
public class ConfigurationController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationController.class);

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
    @RequestMapping(value = "v1/updateConfig", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateConfig(@RequestBody ConfigInfo configInfo) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/config/v1/updateConfig start========" + configInfo);
        }
        Response<String> response = new Response<>();
        try {
            int tokenExpireTime = configInfo.getTokenExpireTime();
            ConfigManager.update(ConfigName.TOKEN_EXPIRE_TIME, String.valueOf(tokenExpireTime));
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

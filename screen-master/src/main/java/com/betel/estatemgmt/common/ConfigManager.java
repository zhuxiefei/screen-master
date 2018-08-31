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

package com.betel.estatemgmt.common;

import com.betel.estatemgmt.business.web.config.service.ConfigService;
import com.betel.estatemgmt.common.model.config.Config;
import com.betel.estatemgmt.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配置管理器
 * </p>
 * ClassName: ReadConfig <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/15 14:20 <br/>
 * Version: 1.0 <br/>
 */
public class ConfigManager {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigManager.class);

    private static HashMap<String, String> conf = new HashMap<>();

    /**
     * <p>
     * 初始化加载系统配置
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/15 15:39
     */
    public static void initConf() {
        if (LOG.isInfoEnabled()) {
            LOG.info("============loading config...=============");
        }
        try {
            List<Config> confList = BeanFactory.getBean(ConfigService.class).findAll();
            for (Config systemConfig : confList) {
                conf.put(systemConfig.getConfName(), systemConfig.getConfValue());
            }
            //debug打印配置信息
            if (LOG.isDebugEnabled()) {
                for (Map.Entry<String, String> entry : conf.entrySet()) {
                    LOG.debug("系统配置：" + entry.getKey() + ":" + entry.getValue());
                }
            }
            if (LOG.isInfoEnabled()) {
                LOG.info("============load success!============");
            }
        } catch (Exception e) {
            LOG.error("============load failed!============", e);
        }
    }

    /**
     * <p>
     * 读取配置
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 11:22
     *
     * @param confName 配置名称，配置常量统一从com.betel.smartre.business.web.config.model.ConfigName中获取
     * @return 配置值
     */
    public static String read(String confName) {
        String confValue = null;
        try {
            // 先从map中读取配置，若配置不存在，则从数据库中重新读取
            confValue = conf.get(confName);
            if (StringUtil.isEmpty(confValue)) {
                confValue = BeanFactory.getBean(ConfigService.class).findOne(confName).getConfValue();
                // 若数据库中该配置不为空，则将该值重新put到map中
                if (!StringUtil.isEmpty(confValue)) {
                    conf.put(confName, confValue);
                }
            }
        } catch (Exception e) {
            LOG.error("read config: [" + confName + "] error", e);
        }
        return confValue;
    }

    /**
     * <p>
     * 更新配置
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 11:24
     *
     * @param confName  配置名称
     * @param confValue 配置值
     */
    public static void update(String confName, String confValue) {
        try {
            conf.put(confName, confValue);
            BeanFactory.getBean(ConfigService.class).updateConfigByKey(confName, confValue);
        } catch (Exception e) {
            LOG.error("update config: [" + confName + "] error", e);
        }
    }

}

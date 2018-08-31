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

package com.betel.estatemgmt.business.web.config.service;

import com.betel.estatemgmt.common.model.config.Config;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ConfigService <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/15 13:19 <br/>
 * Version: 1.0 <br/>
 */
public interface ConfigService {

    boolean updateConfig(Config config);

    boolean updateConfigByKey(String confName, String confValue);

    List<Config> findAll();

    Config findOne(String confName);
}

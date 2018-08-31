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

package com.betel.estatemgmt.business.web.config.service.impl;

import com.betel.estatemgmt.business.web.config.service.ConfigService;
import com.betel.estatemgmt.common.mapper.config.ConfigMapper;
import com.betel.estatemgmt.common.model.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ConfigServiceImpl <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/15 13:19 <br/>
 * Version: 1.0 <br/>
 */
@Service("ConfigService")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public boolean updateConfig(Config config) {
        int i = configMapper.updateByPrimaryKeySelective(config);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateConfigByKey(String confName, String confValue) {
        Config config = new Config(confName, confValue);
        int i = configMapper.updateByPrimaryKeySelective(config);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<Config> findAll() {
        return configMapper.findAll();
    }

    @Override
    public Config findOne(String confName) {
        return configMapper.selectByPrimaryKey(confName);
    }
}

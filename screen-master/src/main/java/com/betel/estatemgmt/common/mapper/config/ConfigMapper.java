package com.betel.estatemgmt.common.mapper.config;

import com.betel.estatemgmt.common.model.config.Config;

import java.util.List;

public interface ConfigMapper {

    Config selectByPrimaryKey(String confName);

    int updateByPrimaryKeySelective(Config record);

    List<Config> findAll();

}
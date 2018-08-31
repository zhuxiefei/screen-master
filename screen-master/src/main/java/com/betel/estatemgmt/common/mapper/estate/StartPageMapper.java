package com.betel.estatemgmt.common.mapper.estate;

import com.betel.estatemgmt.common.model.estate.StartPage;

import java.util.List;

public interface StartPageMapper {
    int deleteByPrimaryKey(String id);

    int insert(StartPage record);

    int insertSelective(StartPage record);

    StartPage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StartPage record);

    int updateByPrimaryKey(StartPage record);

    List<String> findStartPage();
}
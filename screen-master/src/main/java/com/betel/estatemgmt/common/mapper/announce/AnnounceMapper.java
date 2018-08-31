package com.betel.estatemgmt.common.mapper.announce;


import com.betel.estatemgmt.business.userapp.index.model.SystemAnnounce;
import com.betel.estatemgmt.business.web.announce.model.AnnounceInfo;
import com.betel.estatemgmt.business.web.announce.model.AnnouncePage;
import com.betel.estatemgmt.business.web.announce.model.AnnouncePoint;
import com.betel.estatemgmt.business.web.announce.model.AppAnnounceInfo;
import com.betel.estatemgmt.common.model.announce.Announce;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;


public interface AnnounceMapper {
    int deleteByPrimaryKey(Long announceId);

    int insert(Announce record);

    Long insertSelective(Announce record);

    AnnounceInfo findByPrimaryKey(Long announceId);

    int updateByPrimaryKeySelective(Announce record);

    int updateByPrimaryKeyWithBLOBs(Announce record);

    int updateByPrimaryKey(Announce record);


    SystemAnnounce selAnnounce(Long announceId);

    List<SystemAnnounce> findAllAnnounces(RowBounds rowBounds);

    Integer announceTotal();

    int deleteByAnnounceIds(Long[] array);

    List<AnnouncePoint> findAllAnnounce(RowBounds rowBounds, AnnouncePage page);

    AppAnnounceInfo selectByAnnounceId(Long announceId);

    List<Announce> findTopAnnounces();

    Announce findByAnnounceId(@Param("announceId") Long announceId);

    void setAnnounceTop(Announce announce);

    Announce findLastBetelTip(String date);
}
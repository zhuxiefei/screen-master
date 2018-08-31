package com.betel.estatemgmt.common.mapper.decoration;

import com.betel.estatemgmt.business.propertyapp.decoration.model.QueryDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.model.AddDecorationReq;
import com.betel.estatemgmt.business.web.house.model.DecorationApply;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DecoApplyRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(DecoApplyRecord record);

    int insertSelective(DecoApplyRecord record);

    DecoApplyRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(DecoApplyRecord record);

    int updateByPrimaryKey(DecoApplyRecord record);

    List<DecoApplyRecord> findMyDecorationList(@Param("userId") String userId, @Param("estateId")String estateId);

    DecoApplyRecord findDecorationInfo(String recordId);

    void submitDecorationAgain(AddDecorationReq addDecorationReq);

    List<DecoApplyRecord> findAllDecorationList(QueryDecorationReq queryReq, RowBounds rowBounds);

    List<DecorationApply> findByHouseId(String houseId);

    String findOwnerByRecordId(String recordId);

    List<String> findMembersByRecordId(String recordId);

    List<DecoApplyRecord> findPropertyApplyOnline(String estateId);

    List<DecoApplyRecord> findBusinessApplyOnline(String estateId);

    List<DecoApplyRecord> findAllOfficeDecorationList(QueryDecorationReq queryReq, RowBounds rowBounds);

    DecoApplyRecord findOfficeDecorationInfo(String recordId);

    void deleteByHouseIds(String[] houseIds);
}
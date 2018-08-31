package com.betel.estatemgmt.common.mapper.repair;

import com.betel.estatemgmt.common.model.repair.RepairOrderReply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairOrderReplyMapper {
    int deleteByPrimaryKey(Long replyId);

    int insert(RepairOrderReply record);

    int insertSelective(RepairOrderReply record);

    RepairOrderReply selectByPrimaryKey(Long replyId);

    int updateByPrimaryKeySelective(RepairOrderReply record);

    int updateByPrimaryKey(RepairOrderReply record);

    List<RepairOrderReply> findByOrderNo(String orderNo);

    void deleteReplyList(List<Long> list);
}
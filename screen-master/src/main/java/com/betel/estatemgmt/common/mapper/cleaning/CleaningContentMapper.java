package com.betel.estatemgmt.common.mapper.cleaning;

import com.betel.estatemgmt.business.propertyapp.cleaning.model.CleaningInfo;
import com.betel.estatemgmt.business.propertyapp.cleaning.model.FindCleaningListReq;
import com.betel.estatemgmt.business.web.cleaning.model.FindAllContentReq;
import com.betel.estatemgmt.business.web.cleaning.model.FindAllContentResp;
import com.betel.estatemgmt.business.web.cleaning.model.FindContentResp;
import com.betel.estatemgmt.common.model.cleaning.CleaningContent;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CleaningContentMapper {
    int deleteByPrimaryKey(String contentId);

    int insert(CleaningContent record);

    int insertSelective(CleaningContent record);

    CleaningContent selectByPrimaryKey(String contentId);

    int updateByPrimaryKeySelective(CleaningContent record);

    int updateByPrimaryKey(CleaningContent record);

    /**
     * 根据类型id查询保洁内容
     * @param typeId
     * @return
     */
    List<CleaningContent> findByTypeId(String typeId);

    List<CleaningInfo> findAllCleaningList(FindCleaningListReq findCleaningListReq, RowBounds rowBounds);

    List<FindAllContentResp> findAllContent(FindAllContentReq contentReq,RowBounds rowBounds);

    FindContentResp findByContentId(String contentId);

    /**
     * 批量删除保洁内容
     * @param contentIds
     */
    void deleteByContentIds(String[] contentIds);
}
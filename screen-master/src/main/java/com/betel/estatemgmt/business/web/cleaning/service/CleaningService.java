package com.betel.estatemgmt.business.web.cleaning.service;

import com.betel.estatemgmt.business.web.cleaning.model.*;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningContent;
import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 9:22 <br/>
 * Version: 1.0 <br/>
 */
public interface CleaningService {

    String addArea(AddAreaReq addAreaReq);

    String deleteArea(DeleteAreaReq areaReq);

    String addType(AddTypeReq addTypeReq);

    String deleteType(DeleteTypeReq deleteTypeReq);

    String updateArea(UpdateAreaReq areaReq);

    String updateType(UpdateTypeReq typeReq);

    List<CleaningArea> findAllAreas(String estateId);

    List<CleaningType> findTypes(String areaId);

    String addContent(AddContentReq contentReq);

    List<FindAllContentResp> findAllContent(FindAllContentReq contentReq, Paging<FindAllContentResp> paging);

    CleaningContent findByContentId(String contentId);

    FindContentResp findContent(String contentId);

    CleaningArea findArea(String areaId);

    CleaningType findType(String typeId);

    String updateContent(UpdateContentReq contentReq);

    String deleteContent(String contentId);

    List<ContentRecordInfo> findContentRecords(FindAllContentRecordsReq recordsReq
            , Paging<ContentRecordInfo> paging, HttpServletRequest request);

    List<FindAllRecordsResp> findAllRecords(FindAllRecordsReq recordsReq,Paging<FindAllRecordsResp> paging);

    FindRecordResp findRecord(String recordId, HttpServletRequest request);

    List<FindAllRecordsResp> exportByIds(String recordIds);

    List<FindAllRecordsResp> exportByParams(ExportRecordsReq recordsReq);
}

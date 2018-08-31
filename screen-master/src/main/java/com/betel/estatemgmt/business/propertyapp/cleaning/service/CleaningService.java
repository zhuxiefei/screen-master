package com.betel.estatemgmt.business.propertyapp.cleaning.service;

import com.betel.estatemgmt.business.propertyapp.cleaning.model.*;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * app保洁业务类...
 * </p>
 * ClassName: CleaningService <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 11:35 <br/>
 * Version: 1.0 <br/>
 */
public interface CleaningService {

    List<CleaningInfo> findAllCleaningList(FindCleaningListReq findCleaningListReq, Paging<CleaningInfo> pager);

    void savePictures(List<SystemPicture> pictures);

    void addAssessment(AddAssessmentReq addReq,String userId,String estateId);

    List<CleaningRecordInfo> findAllAssessmentList(Paging<CleaningRecordInfo> pager, FindAssessmentListReq findAssessmentListReq,HttpServletRequest request);

    List<CleaningArea> findAllAreas(String estateId);

    List<CleaningType> findTypes(String areaId);
}

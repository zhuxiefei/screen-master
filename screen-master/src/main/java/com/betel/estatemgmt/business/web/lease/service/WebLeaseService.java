package com.betel.estatemgmt.business.web.lease.service;

import com.betel.estatemgmt.business.propertyapp.lease.model.SaveLeaseReq;
import com.betel.estatemgmt.business.web.lease.model.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:51 <br/>
 * Version: 1.0 <br/>
 */
public interface WebLeaseService {

    boolean houseIsDelete(String houseId);

    void modifyLease(SaveLeaseReq req);

    WebLeaseInfo findWebLeaseInfo(String rentId);

    DownloadPDFResp findPDFUrl(String rentId);

    void savePDF(String rentId , String filename ,String newFilename);

    List<WebLeaseList> findAll(Paging<WebLeaseList> pager , WebFindAllLeaseReq req);

    boolean isLeaseTimeConflict(TimeConflict timeConflict);

    boolean isLeaseTimeConflictModify(TimeConflict timeConflict);
}
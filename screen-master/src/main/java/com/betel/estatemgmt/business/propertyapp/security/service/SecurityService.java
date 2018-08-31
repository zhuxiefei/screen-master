package com.betel.estatemgmt.business.propertyapp.security.service;

import com.betel.estatemgmt.business.propertyapp.security.model.SecurityInfo;
import com.betel.estatemgmt.business.propertyapp.security.model.SignInfo;
import com.betel.estatemgmt.business.propertyapp.security.model.SignReq;
import com.betel.estatemgmt.common.model.security.SecurityRecord;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * app安保巡逻业务类...
 * </p>
 * ClassName: RecordService <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 11:35 <br/>
 * Version: 1.0 <br/>
 */
public interface SecurityService {

    List<SecurityInfo> findAllSecurityList(Paging<SecurityInfo> pager, String status,String estateId);

    List<SignInfo> findAllSignList(String recordId);

    void sign(SignReq signReq, String userId, HttpServletRequest request)throws Exception;

    void finish(String recordId, String userId, HttpServletRequest request)throws Exception;

    SecurityRecord findSecurityRecordById(String recordId);
}

package com.betel.estatemgmt.business.web.screen.service;

import com.betel.estatemgmt.business.web.screen.model.FindDataResp;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ScreenService <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:24 <br/>
 * Version: 1.0 <br/>
 */
public interface ScreenService {

    FindDataResp findData(HttpServletRequest request) throws Exception;
}

package com.betel.estatemgmt.business.web.security.service;

/**
 * Created by zhangjian on 2018/2/27.
 */

import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.common.model.security.SecurityArea;
import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;

import java.util.List;

/**
 * <p>
 * 安保巡逻区域实现接口
 * </p>
 * ClassName: AreaService <br/>
 * Author:jians.z  <br/>
 * Date:  2018/2/27 <br/>
 * Version: 1.0 <br/>
 */
public interface AreaService {
    /**
     * 查询巡检区域集合
     *
     * @return
     */
    List<SecurityArea> findAreaList(String estateId);

    /**
     * 查询巡检区域集合
     *
     * @return
     */
    List<SecuritySigninAddress> findSignList(SecurityReq securityReq);

    /**
     * 新增安保区域
     *
     * @param securityReq
     * @return
     */
    String addArea(SecurityReq securityReq);

    /**
     * 新增安保区域
     *
     * @param securityReq
     * @return
     */
    String addSignin(SecurityReq securityReq);

    /**
     * 更新区域下
     *
     * @return
     */
    String updateArea(SecurityReq securityReq);

    /**
     * 更新区域下的签到地点
     *
     * @return
     */
    String updateSignin(SecurityReq securityReq);

    /**
     * 查询安保区域
     *
     * @param securityReq
     * @return
     */
    SecurityArea findArea(SecurityReq securityReq);

    /**
     * 查询安保区域下签到地址
     *
     * @param securityReq
     * @return
     */
    SecuritySigninAddress findSignin(SecurityReq securityReq);

    /**
     * 删除区域
     *
     * @param securityReq
     * @return
     */
    String deleteArea(SecurityReq securityReq);

    /**
     * 删除签到地址
     *
     * @param securityReq
     * @return
     */
    String deleteSignin(SecurityReq securityReq);

}

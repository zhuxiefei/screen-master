package com.betel.estatemgmt.business.userapp.index.controller;

import com.betel.estatemgmt.business.userapp.index.code.IndexCode;
import com.betel.estatemgmt.business.userapp.index.model.AdInfor;
import com.betel.estatemgmt.business.userapp.index.model.SystemAd;
import com.betel.estatemgmt.business.userapp.index.model.SystemAnnounce;
import com.betel.estatemgmt.business.userapp.index.service.AdInfoService;
import com.betel.estatemgmt.business.userapp.index.service.AnnounceService;
import com.betel.estatemgmt.business.userapp.user.constant.UserConstant;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Page;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.ad.Advertisement;
import com.betel.estatemgmt.common.model.announce.Announce;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * app首页显示
 * </p>
 * ClassName: IndexController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/15 17:16 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/user")
public class IndexController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    AdInfoService adInfoService;
    @Autowired
    AnnounceService announceService;
    /**
     * <p>
     * 分页查询小区工作日志列表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/15 15:10
     * return response
     */
    @RequestMapping(value = "v1/findAds",method = RequestMethod.GET)
    public Response<Paging<AdInfor>> findAds(Page page) {
        if(LOG.isInfoEnabled()){
            LOG.info("========/app/user/v1/findAds  start:");
        }
        Response<Paging<AdInfor>> response = new Response<>();
        Paging<AdInfor> pager = new Paging<>(page.getCurPage(), page.getPageSize());
        try {
            //查询工作日志列表
            pager.result(adInfoService.findAllAds(pager));
            response.setData(pager);
        } catch (Exception e) {
            LOG.error("=================/app/user/v1/findAds",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("========/app/user/v1/findAds  end:"+response);
        }
        return response;
    }

    /**
     * <p>
     * 查询广告详情
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/15 15:15
     * return response
     */
    @RequestMapping(value = "v1/findAd",method = RequestMethod.GET)
    public Response<SystemAd> findAd(Advertisement advertisement) {
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/user/v1/findAd  start====+advertisementId"+advertisement.getAdId());
        }
        Response<SystemAd> response = new Response();
        SystemAd systemAd;
        if(null == advertisement.getAdId()){
            response.setCode(IndexCode.NULLID);
        }else{
            try {
                systemAd = adInfoService.selAd(advertisement.getAdId());
            }catch (Exception e){
                response.setCode(StatusCode.FAILURE);
                LOG.error("========/app/user/v1/findAd",e);
                return response;
            }
            //判断查询结果是否为空
            if (systemAd == null) {
                response.setCode(IndexCode.AD_IS_DELETE);
            } else {
                systemAd.setCreateTime(systemAd.getCreateTime().substring(0,11));
                response.setData(systemAd);
            }
        }
        if(LOG.isInfoEnabled()) {
            LOG.info("========/app/user/v1/findAd  end:"+response);
        }
        return response;
    }
    /**
    * <p>
    * 查询公告详情
    * </p>
    * Author: zhouye <br/>
   * Date: 2017/5/15 18:47
    *return response
    */
    @RequestMapping(value = "v1/findAnnounce",method = RequestMethod.GET)
    public Response<SystemAnnounce> findAnnounce(Announce announce) {
        if(LOG.isInfoEnabled()){
            LOG.info("========/app/user/v1/findAnnounce  start=========announce："+announce.getAnnounceId());
        }
        Response<SystemAnnounce> response = new Response();
        if(null== announce.getAnnounceId()) {
            response.setCode(IndexCode.NULLID);
        }else{
            SystemAnnounce systemAnnounce = new SystemAnnounce();
            try {
                 systemAnnounce = announceService.findAnnounce(announce.getAnnounceId());
            }catch (Exception e){
                response.setCode(StatusCode.FAILURE);
                LOG.error("========/app/user/v1/findAnnounce  :",e);
           }
            //判断查询结果是否为空
           if (systemAnnounce == null) {
               response.setCode(IndexCode.ANNOUNCE_ID_DELETE);
           } else {
               response.setCode(StatusCode.SUCCESS);
               //修改时间格式
               String creatTime=systemAnnounce.getCreateTime();
               creatTime=creatTime.substring(0,10);
               systemAnnounce.setCreateTime(creatTime);
               response.setData(systemAnnounce);
           }
       }
        if(LOG.isInfoEnabled()){
            LOG.info("========/app/user/v1/findAnnounce  end:"+response);
        }
       return response;
    }
    /**
     * <p>
     * 分页查询公告
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/15 19:35
     *return response
     */
    @RequestMapping(value="v1/findAllAnnounce",method = RequestMethod.GET)
    public Response<Paging<SystemAnnounce>> findAllAnnounce(Page page) {
        if(LOG.isInfoEnabled()){
            LOG.info("========/app/user/v1/findAnnounceAll  start=======page="+page.toString());
        }
        Response<Paging<SystemAnnounce>> response = new Response();
        Paging<SystemAnnounce> paging=new Paging(page.getCurPage(),page.getPageSize());
        List<SystemAnnounce> systemAnnounces;
        try {
            //查询总数量
            systemAnnounces = announceService.findAllAnnounce(paging);
        }catch (Exception e){
            response.setCode(StatusCode.FAILURE);
            LOG.error("========/app/user/v1/findAnnounceAll error!=======",e);
            return response;
        }
        //判断查询结果是否为空
        if (systemAnnounces.size()>=1) {
            //修改所有公告的创建时间类型
            List<SystemAnnounce> sya=new ArrayList<>();
            for (SystemAnnounce s:systemAnnounces) {
                String createTime=s.getCreateTime().substring(0,10);
                s.setCreateTime(createTime);
                sya.add(s);
            }
            //分页操作
            paging.result(sya);
        }
        response.setData(paging);
        if(LOG.isInfoEnabled()){
            LOG.info("========/app/user/v1/findAnnounceAll  end======= response"+response);
        }
        return response;
    }
}

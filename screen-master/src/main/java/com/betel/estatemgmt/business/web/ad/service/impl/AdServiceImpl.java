package com.betel.estatemgmt.business.web.ad.service.impl;

import com.betel.estatemgmt.business.web.ad.constant.AdDataValidation;
import com.betel.estatemgmt.business.web.ad.model.*;
import com.betel.estatemgmt.business.web.ad.service.AdService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.ad.AdPicMapper;
import com.betel.estatemgmt.common.mapper.ad.AdvertisementMapper;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.ad.AdPic;
import com.betel.estatemgmt.common.model.ad.Advertisement;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 广告相关操作接口的实现类
 * </p>
 * ClassName: AdServiceImpl <br/>
 * Author: XYX  <br/>
 * Date: 2017/5/16 16:37 <br/>
 * Version: 1.0
 */
@Service("AdService")
@Transactional
public class AdServiceImpl implements AdService {

    private static final Logger LOG = LoggerFactory.getLogger(AdServiceImpl.class);

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Autowired
    private AdPicMapper adPicMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public void addAd(Advertisement advertisement, AddAdInfo adInfo) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl addAd start========advertisement=" + advertisement + "  adInfo=" + adInfo);
        }
        advertisementMapper.insertSelective(advertisement);
        //返回主键
        Long adId = advertisement.getAdId();
        if (LOG.isDebugEnabled()) {
            LOG.debug("========after addAd========advertisement=" + advertisement);
        }
        //判断广告内容图片是否存在
        if (null != adInfo.getContentPicId()) {
            String[] picIdArray = adInfo.getContentPicId().split(",");
            if (LOG.isDebugEnabled()) {
                LOG.debug("========addAd========contentPicId=" + picIdArray);
            }
            //用于接收内容图片编号
            String picId;
            //将相关数据封装到AdPic对象中
            List<AdPic> list = new ArrayList<>();
            for (String picIdStr : picIdArray) {
                AdPic adPic = new AdPic();
                picId = picIdStr;
                adPic.setAdId(adId);
                adPic.setCreateTime(new Date(System.currentTimeMillis()));
                adPic.setPictureId(picId);
                list.add(adPic);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("========before addAdPic========List<AdPic>=" + list);
            }
            if (list.size() != 0){
                adPicMapper.insertList(list);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl addAd end========");
        }
        //推送系统消息
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSendContent(advertisement.getAdTitle());
        sendMessage.setSendId(adId.toString());
        sendMessage.setSendNo(AdDataValidation.SEND_NO);
        sendMessage.setSendType(AdDataValidation.SEND_TYPE);
        sendMessage.setSendTitle("小区工作日志");
        if(LOG.isDebugEnabled()){
            LOG.debug("========pushAnnounce========sendMessage=" +sendMessage);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(sendMessage);
        if(LOG.isDebugEnabled()){
            LOG.debug("========object2Gson sendMessage========" + pushInfo);
        }
        //消息组件发送，发送给所有人
        MsgPushUtils.pushAll(pushInfo);
    }

    @Override
    public Advertisement findByPrimaryKey(Long adId) {
        return advertisementMapper.selectByPrimaryKey(adId);
    }

    @Override
    public List<AdInfo> findAllAds(Paging<AdInfo> pager, AdPage page) {
        List<AdInfo> list = advertisementMapper.findAllAds(pager.getRowBounds(), page);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl findAllAds end========List<AdInfo>="+list);
        }
        return list;
    }

    @Override
    public String deleteAdByAdIds(String adIds) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl deleteAdByAdIds start========adIds="+adIds);
        }
        String[] stringArray = adIds.split(",");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteAdByAdIds========stringArray="+stringArray);
        }
        Long[] longArray = new Long[stringArray.length];
        //删除图片列表
        List<String> picList = new ArrayList<>();
        //删除广告图片的关系表列表
        List<Long> adList = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            //将String数组转为Long数组
            longArray[i] = Long.parseLong(stringArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========foreach stringArray========longArray[i]=" + longArray[i]);
            }
            //根据广告Id查询广告
            Advertisement ad = advertisementMapper.selectByPrimaryKey(longArray[i]);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========foreach stringArray========ad=" + ad);
            }
            if (ad != null) {
                //查询该广告的图片关系表
                List<AdPic> list = adPicMapper.selectByAdId(ad.getAdId());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========foreach stringArray========List<AdPic>=" + list);
                }
                if (list != null && list.size() > 0) {
                    for (AdPic aList : list) {
                        Pic pic = pictureMapper.selectByPictureId(aList.getPictureId());
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========foreach Lisy<AdPic>========pic=" + pic);
                        }
                        if (pic != null) {
                            //删除图片物理路径
                            FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR) + pic.getPictureUrl());
                        }
                        //删除图片列表添加数据
                        picList.add(aList.getPictureId());
                    }
                    //删除广告图片的关系表列表添加数据
                    adList.add(ad.getAdId());
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteAdByAdIds========picList="+picList);
        }
        //删除图片
        if (picList.size() != 0){
            pictureMapper.deletePicList(picList);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteAdByAdIds========adList="+adList.toString());
        }
        //删除广告图片的关系表
        if (adList.size() != 0){
            adPicMapper.deleteList(adList);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deleteAdByAdIds========adIdArray="+longArray.toString());
        }
        //删除广告
        advertisementMapper.deleteByAdIds(longArray);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl deleteAdByAdIds end========");
        }
        return null;
    }

    @Override
    public void updateAd(Advertisement advertisement, UpdateAdInfo adInfo) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl updateAd start========advertisement=" + advertisement + "  adInfo=" + adInfo);
        }
        advertisementMapper.updateByPrimaryKeySelective(advertisement);
        //判断广告内容图片是否存在
        if (!StringUtil.isEmpty(adInfo.getContentPicId())) {
            String[] picIdArray = adInfo.getContentPicId().split(",");
            if (LOG.isDebugEnabled()) {
                LOG.debug("========updateAd========picIdArray=" + picIdArray);
            }
            //用于接收内容图片编号
            String picId;
            List<AdPic> list = new ArrayList<>();
            //将修改后的内容图片编号保存到关系表中
            for (String picIdStr : picIdArray) {/*-?|Code-Review|Administrator|c2|*/
                picId = picIdStr;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========foreach picIdArray========picId=" + picId);
                }
                //将相关数据封装到AdPic对象中
                AdPic adPic = new AdPic();
                adPic.setAdId(adInfo.getAdId());
                adPic.setCreateTime(new Date(System.currentTimeMillis()));
                adPic.setPictureId(picId);
                list.add(adPic);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("========updateAd========List<AdPic>=" + list);
            }
            if (list.size() != 0){
                adPicMapper.insertList(list);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl updateAd end========");
        }
    }

    @Override
    public AdDetail findByAdId(Long adId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========AdServiceImpl findByAdId start=======adId=" + adId);
        }
        AdDetail detail = advertisementMapper.selectByAdId(adId);
        return detail;
    }

    @Override
    public String addPicture(Picture picture) {
        if (LOG.isDebugEnabled()){
            LOG.debug("========AdServiceImpl addPicture start=======picture=" + picture);
        }
        pictureMapper.insertSelective(picture);
        if (LOG.isDebugEnabled()){
            LOG.debug("========AdServiceImpl addPicture end=======picture=" + picture);
        }
        //返回主键
        return picture.getPictureId();
    }

    @Override
    public Pic findByPictureId(String pictureId) {
        return pictureMapper.selectByPictureId(pictureId);
    }
}

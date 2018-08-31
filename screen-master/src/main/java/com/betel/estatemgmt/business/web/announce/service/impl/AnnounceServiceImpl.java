package com.betel.estatemgmt.business.web.announce.service.impl;

import com.betel.estatemgmt.business.web.announce.code.AnnounceCode;
import com.betel.estatemgmt.business.web.announce.constant.AnnounceDataValidation;
import com.betel.estatemgmt.business.web.announce.model.AnnounceInfo;
import com.betel.estatemgmt.business.web.announce.model.AnnouncePage;
import com.betel.estatemgmt.business.web.announce.model.AnnouncePoint;
import com.betel.estatemgmt.business.web.announce.model.AppAnnounceInfo;
import com.betel.estatemgmt.business.web.announce.service.AnnounceService;
import com.betel.estatemgmt.common.mapper.announce.AnnounceMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.model.announce.Announce;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 公告相关操作接口的实现类
 * </p>
 * ClassName: AnnounceServiceImpl <br/>
 * Author:   <br/>
 * Date: 2017/5/15 16:22 <br/>
 * Version: 1.0
 */
@Service("AnnounceService")
@Transactional
public class AnnounceServiceImpl implements AnnounceService {

    @Autowired
    private AnnounceMapper announceMapper;

    @Autowired
    private RepairOrderMapper orderMapper;

    private static final Logger LOG = LoggerFactory.getLogger(AnnounceServiceImpl.class);

    @Override
    public Long addAnnounce(Announce announce) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl addAnnounce start========announce="+announce);
        }
        announceMapper.insertSelective(announce);
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl addAnnounce end========");
        }
        Long announceId = announce.getAnnounceId();
        AppAnnounceInfo info = announceMapper.selectByAnnounceId(announceId);
        info.setSendType(AnnounceDataValidation.SEND_TYPE);
        info.setSendNo(AnnounceDataValidation.SEND_NO);
        info.setSendTitle("全站公告");
        if(LOG.isDebugEnabled()){
            LOG.debug("========pushAnnounce========AppAnnounceInfo=" +info);
        }
        //转json
        String pushInfo = GsonUtil.object2Gson(info);
        if(LOG.isDebugEnabled()){
            LOG.debug("========object2Gson AppAnnounceInfo========pushInfo=" + pushInfo);
        }
        //消息组件发送，发送给所有人
        MsgPushUtils.pushAll(pushInfo);
        //修改发送状态
        Announce announce1 = new Announce();
        announce1.setAnnounceId(announceId);
        announce1.setAnnounceStatus(AnnounceDataValidation.STATUS_IS_SEND);
        if(LOG.isDebugEnabled()){
            LOG.debug("========updateAnnounce========announce1=" +announce1);
        }
        announceMapper.updateByPrimaryKeySelective(announce1);
        return null;
    }

    @Override
    public List<AnnouncePoint> findAllAnnounce(Paging<AnnouncePoint> pager, AnnouncePage page) {
        List<AnnouncePoint> list = announceMapper.findAllAnnounce(pager.getRowBounds(), page);
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl findAllAnnounce end========List<AnnouncePoint>=" +list);
        }
        return list;
    }


    @Override
    public String deleteAnnounce(String announceIds) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl deleteAnnounce start========announceIds=" +announceIds);
        }
        String[] stringArray = announceIds.split(",");/*-?|Code-Review|Administrator|c12|*/
        if (LOG.isDebugEnabled()){
            LOG.debug("=========stringArray==========" + stringArray);
        }
        Long[] longArray = new Long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longArray[i] = Long.parseLong(stringArray[i]);
        }/*-|Code-Review|Administrator|c12|?*/
        if(LOG.isDebugEnabled()){
            LOG.debug("========deleteAnnounce========announceIdsArray=" +longArray);
        }
        announceMapper.deleteByAnnounceIds(longArray);
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl deleteAnnounce end========");
        }
       return null;
    }

    @Override
    public AnnounceInfo findAnnounce(Long announceId) {
        if (LOG.isDebugEnabled()){
            LOG.debug("==========AnnounceServiceImpl findAnnounce start==========announceId=" + announceId);
        }
        AnnounceInfo info = announceMapper.findByPrimaryKey(announceId);
        if (LOG.isDebugEnabled()){
            LOG.debug("==========AnnounceServiceImpl findAnnounce end==========announceInfo=" + info);
        }
        return info;
    }

    @Override
    public String updateAnnounce(Announce announce) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl updateAnnounce start========announce=" +announce);
        }
        announceMapper.updateByPrimaryKeySelective(announce);
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl updateAnnounce end========");
        }
        return null;
    }

    @Override
    public String pushAnnounce(Long announceId) {
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl pushAnnounce start========announceId=" +announceId);
        }
        //查询该公告
        Announce an = announceMapper.findByAnnounceId(announceId);
        if (null == an || (null != an && AnnounceDataValidation.ANNOUNCE_DELETE_STATUS.equals(an.getAnnounceStatus()))){
            return AnnounceCode.ANNOUNCE_IS_DELETE;
        }else {
            AppAnnounceInfo info = announceMapper.selectByAnnounceId(announceId);
            info.setSendType(AnnounceDataValidation.SEND_TYPE);
            info.setSendNo(AnnounceDataValidation.SEND_NO);
            info.setSendTitle("全站公告");
            if(LOG.isDebugEnabled()){
                LOG.debug("========pushAnnounce========AppAnnounceInfo=" +info);
            }
            //转json
            String pushInfo = GsonUtil.object2Gson(info);
            if(LOG.isDebugEnabled()){
                LOG.debug("========object2Gson AppAnnounceInfo========pushInfo=" + pushInfo);
            }
            //消息组件发送，发送给所有人
            MsgPushUtils.pushAll(pushInfo);
            //修改发送状态
            Announce announce = new Announce();
            announce.setAnnounceId(announceId);
            announce.setAnnounceStatus(AnnounceDataValidation.STATUS_IS_SEND);
            if(LOG.isDebugEnabled()){
                LOG.debug("========updateAnnounce========announce=" +announce);
            }
            announceMapper.updateByPrimaryKeySelective(announce);
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl pushAnnounce end========");
        }
        return null;
    }

    @Override
    public List<Announce> findTopAnnounces() {
        return announceMapper.findTopAnnounces();
    }

    @Override
    public Announce findByAnnounceId(Long id) {
        return announceMapper.findByAnnounceId(id);
    }

    @Override
    public void setAnnounceTop(Announce announce) {
        announceMapper.setAnnounceTop(announce);
    }

    @Override
    public void createBetelTip() {
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl createBetelTip start========");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(getLastFirstDay());
        //查询当月未完成的报修单
        Long unFinish = orderMapper.countOrders(date,1);
        if(LOG.isDebugEnabled()){
            LOG.debug("========createBetelTip unFinish========"+unFinish);
        }
        //查询当月已完成的报修单
        Long finish = orderMapper.countOrders(date,2);
        if(LOG.isDebugEnabled()){
            LOG.debug("========createBetelTip finish========"+finish);
        }
        //公告内容
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
        //2017年10月1号-2017年10月31号：本月报修XX单，已完成XX单，剩余XX单未完成
        String content = sdf2.format(getLastFirstDay())+"-"+sdf2.format(getLastEndDay())+"：\n本月报修"
                +(unFinish+finish)+"单，已完成"+finish+"单，剩余"+unFinish+"单未完成";
        if(LOG.isDebugEnabled()){
            LOG.debug("========createBetelTip content========"+content);
        }
        //创建生成对象
        Announce a = new Announce();
        a.setAnnounceContent(content);
        a.setCreateTime(new Date(System.currentTimeMillis()));
        a.setAnnounceStatus(1);
        a.setAnnounceTitle(AnnounceDataValidation.BETEL_TIP);
        a.setAuthorId("1");
        a.setIsTop(AnnounceDataValidation.NOT_TOP_STATUS);
        a.setTopTime(null);
        announceMapper.insertSelective(a);
        if(LOG.isDebugEnabled()){
            LOG.debug("========AnnounceServiceImpl createBetelTip end========");
        }
    }

    /**
     * 获取前一个月的第一天
     * @return
     */
    public static Date getLastFirstDay(){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        return calendar1.getTime();
    }

    /**
     * 获取前一个月的最后一天
     * @return
     */
    public static Date getLastEndDay(){
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        return calendar2.getTime();
    }
}

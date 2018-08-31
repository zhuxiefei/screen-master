/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package com.betel.estatemgmt.system.quartz;

import com.betel.estatemgmt.business.propertyapp.check.service.CheckService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.business.web.announce.service.AnnounceService;
import com.betel.estatemgmt.business.web.house.service.OfficeHouseService;
import com.betel.estatemgmt.business.web.remind.service.RemindService;
import com.betel.estatemgmt.business.web.security.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 系统定时任务
 * </p>
 * ClassName: SystemQuartz <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/18 13:53 <br/>
 * Version: 1.0 <br/>
 */
@Component
public class SystemQuartz {

    private static final Logger LOG = LoggerFactory.getLogger(SystemQuartz.class);

    @Autowired
    private HouseService houseService;
    @Autowired
    private RemindService remindService;
    @Autowired
    private AnnounceService announceService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private AppPayService appPayService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private OfficeHouseService officeHouseService;

    //    @Scheduled(cron="0/5 * *  * * ? ")
    public void hello() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz [Hello,world!]========");
        }
    }

    //    @Scheduled(cron="0/10 * *  * * ? ")
    public void hello2() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz [Hello,world!2222]========");
        }
    }

    /**
     * <p>
     * 每天定时查询数据库中分享图片记录，删除过期记录
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 11:15
     * return response
     */
   // @Scheduled(cron = "0 0 4 * * ?")
    public void deleteSharePicture() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz deleteSharePicture start========");
        }
        //同步redis中帖子的浏览测试到数据库
        try {
            houseService.deleteSharePicture();
        } catch (Exception e) {
            LOG.error("deleteSharePicture error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz deleteSharePicture end========");
        }
    }

    /**
     * <p>
     * 每天上午9点发送缴费提醒短信和通知
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/19 9:55
     */
    //@Scheduled(cron = "0 0 9 * * ?")
    public void payUpRemind() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz payUpRemind start========");
        }
        try {
            //发送短信和通知
            remindService.payMentRemind();
        } catch (Exception e) {
            LOG.error("payUpRemind error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz payUpRemind end========");
        }
    }

    /**
     * <p>
     * 每天上午10点发送催缴提醒短信和通知
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/19 9:55
     */
    //@Scheduled(cron = "0 0 10 * * ?")
    public void overDueRemind() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz overDueRemind start========");
        }
        try {
            //发送短信和通知
            remindService.overDueRemind();
        } catch (Exception e) {
            LOG.error("overDueRemind error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz overDueRemind end========");
        }
    }

    /**
     * <p>
     * 每月一号上午11点发送缴费通知短信和通知
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = "0 0 11 1 * ?")
    public void payUpNotice() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz payUpNotice start========");
        }
        try {
            //发送短信和通知
            remindService.payUpNotice();
        } catch (Exception e) {
            LOG.error("payUpNotice error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz payUpNotice end========");
        }
    }

    /**
     * <p>
     * 每月一号零点生成贝特小贴
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = "0 0 0 1 * ?")
    public void createBetelTip() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz createBetelTip start========");
        }
        try {
            announceService.createBetelTip();
        } catch (Exception e) {
            LOG.error("createBetelTip error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz createBetelTip end========");
        }
    }

    /**
     * <p>
     * 每日凌晨生成巡检任务
     * </p>
     * Author: jians.z <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = " 0 0 0 * * ?")
    public void insertForDate() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForDate start========");
        }
        try {
            checkService.insertForDate();
        } catch (Exception e) {
            LOG.error("insetForDate error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForDate end========");
        }
    }

    /**
     * <p>
     * 每周凌晨生成巡检任务
     * </p>
     * Author: jians.z  <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = "0 0 0 ? * MON")
    public void insertForWeek() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForWeek start========");
        }
        try {
            checkService.insertForWeek();
        } catch (Exception e) {
            LOG.error("insetForWeek error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForWeek end========");
        }
    }

    /**
     * <p>
     * 每月1日凌晨生成巡检任务
     * </p>
     * Author: jians.z <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = "0 0 0 1 * ?")
    public void insertForMonth() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForMonth start========");
        }
        try {
            checkService.insertForMonth();
        } catch (Exception e) {
            LOG.error("insetForMonth error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForMonth end========");
        }
    }

    /**
     * <p>
     * 每季度1日凌晨生成巡检任务
     * </p>
     * Author: jians.z <br/>
     * Date: 2017/9/19 9:55
     */
    //@Scheduled(cron = "0 0 0 1 1/3 ?")
    public void insertForQuarter() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForQuarter start========");
        }
        try {
            checkService.insertForQuarter();
        } catch (Exception e) {
            LOG.error("insetForQuarter error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForQuarter end========");
        }
    }

    /**
     * <p>
     * 每年1月1日凌晨生成巡检任务
     * </p>
     * Author: jians.z  <br/>
     * Date: 2017/9/19 9:55
     */
    //@Scheduled(cron = "0 0 0 1 1 *")
    public void insertForYear() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForYear start========");
        }
        try {
            checkService.insertForYear();
        } catch (Exception e) {
            LOG.error("insetForQuarter error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz insetForYear end========");
        }
    }

    /**
     * <p>
     * 每日凌晨执行所要收据
     * </p>
     * Author: jians.z <br/>
     * Date: 2017/9/19 9:55
     */
    //@Scheduled(cron = " 0 0 0 * * ?")
    public void updateDemandStatus() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz updateDemandStatus start========");
        }
        try {
            appPayService.updateDemandStatusNew();
        } catch (Exception e) {
            LOG.error("updateDemandStatus error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz updateDemandStatus end========");
        }
    }

    /**
     * <p>
     * 定时生成待巡逻任务
     * </p>
     * Author: jians.z <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = " 0 0/1 * * * ?")
    public void createSecurity() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz createSecurity start========");
        }
        try {
            Date date=new Date();
            recordService.quartzSecurity(date.getTime());
        } catch (Exception e) {
            LOG.error("quartzSecurity error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz createSecurity end========");
        }
    }

    /**
     * <p>
     * 每日凌晨修改房屋状态(出租--》招商)
     * </p>
     * Author: xiayx <br/>
     * Date: 2017/9/19 9:55
     */
   // @Scheduled(cron = " 0 59 23 * * ?")
    public void updateBusinessStatus() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz updateOfficeHouseStatus start========");
        }
        try {
            officeHouseService.updateBusinessStatus();
        } catch (Exception e) {
            LOG.error("updateOfficeHouseStatus error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz updateOfficeHouseStatus end========");
        }
    }

    /**
     * <p>
     * 每日凌晨修改房屋状态(招商--》出租)
     * </p>
     * Author: xiayx <br/>
     * Date: 2017/9/19 9:55
     */
  //  @Scheduled(cron = " 0 0 0 * * ?")
    public void updateRentStatus() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz updateOfficeHouseStatus start========");
        }
        try {
            officeHouseService.updateRentStatus();
        } catch (Exception e) {
            LOG.error("updateOfficeHouseStatus error", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========system quartz updateOfficeHouseStatus end========");
        }
    }
}

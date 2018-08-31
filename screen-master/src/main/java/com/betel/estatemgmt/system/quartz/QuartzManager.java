package com.betel.estatemgmt.system.quartz;

import com.betel.estatemgmt.system.quartz.jobclass.ConfirmReceiptDemand;
import com.betel.estatemgmt.system.quartz.jobclass.DeleteClass;
import com.betel.estatemgmt.system.quartz.model.JobModel;
import com.betel.estatemgmt.utils.DateUtil;
import com.betel.estatemgmt.utils.Quantity;
import com.betel.estatemgmt.utils.RegexRule;
import com.betel.estatemgmt.utils.TimeUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * 定时任务管理器
 * </p>
 * ClassName: QuartzManager <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/11 8:54 <br/>
 * Version: 1.0 <br/>
 */
public class QuartzManager {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(QuartzManager.class);
    private static SchedulerFactory schedulerFactory;
    private static Scheduler scheduler;
    private static QuartzManager quartzManager;

    private QuartzManager() {
        if (LOG.isInfoEnabled()) {
            LOG.info("-------------Quartz--------inits--start----");
        }
        schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-------------Quartz--------inits--end----");
        }
    }

    /**
     * <p>
     * 实例化quartzManager
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/21 16:14
     *
     * @return
     * @throws Exception
     */
    public static void getInstence() {
        if (quartzManager == null) {
            quartzManager = new QuartzManager();
        }
    }

    /**
     * <p>
     * 停止定时任务线程
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/21 15:43
     *
     * @throws SchedulerException
     */
    public static void shutDown() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    /**
     * <p>
     * 添加任务
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/11 9:22
     * return response
     */
    public static void addJob(JobModel jobModel) throws SchedulerException {
        if (LOG.isInfoEnabled()) {
            LOG.info("-------------Quartz----------addJob----" + jobModel);
        }
        JobDetail jobDetail = new JobDetail(jobModel.getJobName(), Scheduler.DEFAULT_GROUP, DeleteClass.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("flowNo", jobModel.getDataMap().get("flowNo"));
        jobDetail.setJobDataMap(jobDataMap);
        SimpleTrigger simpleTrigger = new SimpleTrigger(jobModel.getJobName(),
                Scheduler.DEFAULT_GROUP);
        simpleTrigger.setStartTime(new Date(System.currentTimeMillis() + 1000 * 300));
        simpleTrigger.setRepeatInterval(1000);
        simpleTrigger.setRepeatCount(0);
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * <p>
     * 添加任务：索要收据计时--->确认收货
     * </p>
     * Author: jian.z <br/>
     * Date: 2017/9/11 9:22
     * return response
     */
    public static void askJob(JobModel jobModel) throws SchedulerException, ParseException {
        if (LOG.isInfoEnabled()) {
            LOG.info("-------------Quartz----------askJob----" + jobModel);
        }
        JobDetail jobDetail = new JobDetail(jobModel.getJobName(), Scheduler.DEFAULT_GROUP, ConfirmReceiptDemand.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("billNo", jobModel.getDataMap().get("billNo"));
        jobDetail.setJobDataMap(jobDataMap);
        SimpleTrigger simpleTrigger = new SimpleTrigger(jobModel.getJobName(),
                Scheduler.DEFAULT_GROUP);
        simpleTrigger.setStartTime(DateUtil.toDate(TimeUtils.getFetureDate(Quantity.FIFTEEN), RegexRule.TIME_FARMAT_Y_M_D_H_M_S));
        simpleTrigger.setRepeatInterval(1000);
        simpleTrigger.setRepeatCount(0);
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * <p>
     * Describer this method...
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/21 15:44
     *
     * @return
     * @throws Exception
     */
    public static void removeJob(String jobName, String triggerName)
            throws SchedulerException {
        scheduler.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);//停止触发器
        scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);//移除触发器
        scheduler.deleteJob(jobName, Scheduler.DEFAULT_GROUP);//删除任务
    }
   /* private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "BetelJob";
    private static String TRIGGER_GROUP_NAME = "BetelGroup";



    *//**添加一个定时任务，使用默认的任务组名，触发器名，触发器组名*//*
    public static void addJob(String jobName,Job job,String cronExpression)
            throws SchedulerException, ParseException {
        addJob(jobName,null,jobName,null,job,cronExpression);
    }

    *//**
     * 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param job     任务
     * @param cronExpression    时间设置，参考quartz说明文档
     *//*
    public static void addJob(String jobName,String jobGroupName,
                              String triggerName,String triggerGroupName,Job job,String cronExpression)
            throws SchedulerException, ParseException{
        if(StringUtils.isBlank(jobGroupName)){
            jobGroupName = JOB_GROUP_NAME;
        }
        if(StringUtils.isBlank(triggerGroupName)){
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Scheduler sched = sf.getScheduler();
        JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类
        CronTrigger  trigger = new CronTrigger(jobName,triggerGroupName,cronExpression);//触发器名,触发器组,cron表达式
        sched.scheduleJob(jobDetail,trigger);
        //启动
        if(!sched.isShutdown()){
            sched.start();
        }
    }

    *//**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *//*
    public static void modifyJobTime(String jobName,String cronExpression)
            throws SchedulerException, ParseException{
        modifyJobTime(jobName, null, cronExpression);
    }

    *//**
     * 修改一个任务的触发时间
     *//*
    public static void modifyJobTime(String triggerName,String triggerGroupName,
                                     String cronExpression)throws SchedulerException, ParseException{
        if(StringUtils.isBlank(triggerGroupName)){
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Scheduler sched = sf.getScheduler();
        Trigger trigger = sched.getTrigger(triggerName,triggerGroupName);
        if(trigger != null){
            CronTrigger ct = (CronTrigger)trigger;
            //修改时间
            ct.setCronExpression(cronExpression);
            //重启触发器
            sched.resumeTrigger(triggerName,triggerGroupName);
        }
    }

    *//**移除一个任务和触发器(使用默认的任务组名，触发器名，触发器组名)*//*
    public static void removeJob(String jobName,String triggerName)
            throws SchedulerException{
        removeJob(jobName, null, triggerName, null);
    }

    *//**移除一个任务和触发器 *//*
    public static void removeJob(String jobName,String jobGroupName,
                                 String triggerName,String triggerGroupName)
            throws SchedulerException{
        if(StringUtils.isBlank(jobGroupName)){
            jobGroupName = JOB_GROUP_NAME;
        }
        if(StringUtils.isBlank(triggerGroupName)){
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Scheduler sched = sf.getScheduler();
        sched.pauseTrigger(triggerName,triggerGroupName);//停止触发器
        sched.unscheduleJob(triggerName,triggerGroupName);//移除触发器
        sched.deleteJob(jobName,jobGroupName);//删除任务
    }*/


}

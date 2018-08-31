package com.betel.estatemgmt.system.quartz.jobclass;

import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.common.mapper.expense.ExpenseFlowMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.system.quartz.QuartzManager;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * <p>
 * 流水单删除
 * </p>
 * ClassName: DeleteClass <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/11 9:50 <br/>
 * Version: 1.0 <br/>
 */
@Component
public class DeleteClass implements Job,Serializable{
    private static final Logger LOG = LoggerFactory.getLogger(DeleteClass.class);
    @Autowired
    ExpenseFlowMapper expenseFlowMapper;
    @Autowired
    AppPayService  appPayService;

    public static DeleteClass deleteClass;

    @PostConstruct
    public void init() {
        deleteClass = this;
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String flowNo = (String) jobDetail.getJobDataMap().get("flowNo");
        ExpenseFlow expenseFlow = deleteClass.expenseFlowMapper.selectByPrimaryKey(flowNo);
        //五分钟还未支付，删除支付订单
        if(expenseFlow!=null&&expenseFlow.getFlowStatus()!=3){
            deleteClass.appPayService.cancelPay(flowNo);
        }
        if (LOG.isInfoEnabled()){
            LOG.info("------cancel----expenseBill-----"+flowNo);
        }
        //移除定时任务
        try {
            QuartzManager.removeJob(flowNo,flowNo);
        } catch (SchedulerException e) {
            LOG.error("--quartz-------removeJob---",e);
        }
    }
}

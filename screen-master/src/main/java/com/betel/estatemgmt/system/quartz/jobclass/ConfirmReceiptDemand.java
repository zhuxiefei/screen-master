package com.betel.estatemgmt.system.quartz.jobclass;

import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.system.quartz.QuartzManager;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Created by zhangjian on 2018/1/5.
 */
@Component
public class ConfirmReceiptDemand implements Job, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmReceiptDemand.class);
    public static ConfirmReceiptDemand confirmReceiptDemand;

    @Autowired
    AppPayService appPayService;

    @PostConstruct
    public void init() {
        confirmReceiptDemand = this;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String billNo = (String) jobDetail.getJobDataMap().get("billNo");
        //15日确认收货
        String code=confirmReceiptDemand.appPayService.updateDemandStatus(billNo);
        if (LOG.isDebugEnabled()){
            LOG.debug("------quartz------确认收货返回业务状态码--code="+code);
        }
        //移除定时任务
        try {
            QuartzManager.removeJob(billNo,billNo);
        } catch (SchedulerException e) {
            LOG.error("--quartz-------updateBillDemandStatus---",e);
        }
    }
}

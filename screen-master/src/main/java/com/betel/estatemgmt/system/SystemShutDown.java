package com.betel.estatemgmt.system;

import com.betel.estatemgmt.system.quartz.QuartzManager;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * <p>
 * spring容器销毁时，执行的方法
 * </p>
 * ClassName: SystemShutDowm <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/21 14:41 <br/>
 * Version: 1.0 <br/>
 */
@Component
public class SystemShutDown implements ApplicationListener<ContextClosedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(SystemInit.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if(event.getApplicationContext().getParent() == null) {
            try {
                QuartzManager.shutDown();
                if (LOG.isInfoEnabled()) {
                    LOG.info("-------QuartzManager-----close----");
                }
            } catch (SchedulerException e) {
                LOG.error("-------spring--context---close----", e);
            }
        }
    }
}

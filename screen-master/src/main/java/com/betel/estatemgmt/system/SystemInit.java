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

package com.betel.estatemgmt.system;

import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.system.quartz.QuartzManager;
import com.betel.estatemgmt.utils.alipay.Alipay;
import com.betel.estatemgmt.utils.wechatpay.WeChatPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 系统初始化加载
 * </p>
 * ClassName: SystemInit <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/10 16:58 <br/>
 * Version: 1.0 <br/>
 */
@Component
public class SystemInit implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInit.class);

    /**
     * 启动状态位
     */
    private volatile static boolean isStart = false;

    private static void setIsStart(boolean isStart) {
        SystemInit.isStart = isStart;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!isStart) {
            if (LOG.isInfoEnabled()) {
                LOG.info("============init system...=============");
            }
            ConfigManager.initConf();
            MsgPushUtils.getInstance();
            PropertyPushUtil.getInstance();
            Alipay.getInstance();
            WeChatPay.getInstence();
            QuartzManager.getInstence();
            //状态位复位
            SystemInit.setIsStart(true);
        }
    }
}

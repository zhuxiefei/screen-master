package com.betel.estatemgmt.common.msgpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.utils.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 * 用户App极光推送
 * </p>
 * ClassName: MsgPushUtils <br/>
 * Author: zhouye  <br/>
 * Date: 2017/8/2 15:47 <br/>
 * Version: 1.0 <br/>
 */
public class MsgPushUtils {
    private static final Logger LOG = LoggerFactory.getLogger(MsgPushUtils.class);
    private static  JPushClient jpushClient;
    private static boolean flag = false;
    /**
     * <p>
     * 初始化极光服务环境
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/8 9:37
     */
    private MsgPushUtils(){
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang init start...=========");
        }
        String appKey = ConfigManager.read((ConfigName.USER_APP_KEY));
        String masterSecret = ConfigManager.read((ConfigName.USER_MASTERSECRET));
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang -- appKey========="+appKey);
            LOG.info("============jiguang -- masterSecret1========="+masterSecret);
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("======read config...");
            LOG.debug("masterSecret:" + masterSecret);
            LOG.debug("appKey:" + appKey);
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        jpushClient = new  JPushClient(masterSecret, appKey, null, clientConfig);

        String jpushEnvironment = ConfigManager.read((ConfigName.JPUSH_ENVIRONMENT));
        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang -- environment...========="+jpushEnvironment);
        }
        if (jpushEnvironment.equals("true")){
            flag = true;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("============jiguang init end...=========");
        }
    }

    /**
     * 单例对象，在系统启动时创建
     */
    private static MsgPushUtils instance = new MsgPushUtils();

    /**
     * <p>
     * 单例构造函数(饿汉)
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/8 9:41
     *
     * @return 单例对象
     */
    public static MsgPushUtils getInstance(){
        return instance;
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObjectAndroidIosAliasAlert(String alias, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent(alert)
                        .build()
                )
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }


    /**
     * <p>
     * 指定对象推送 ios
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/08 17:19
     *return PushPayload
     */
    public static PushPayload buildPushObjectIosAliasAlert(String alias, String alert) {
        SendMessage message = GsonUtil.gson2Object(alert, SendMessage.class);
        IosAlert iosAlert = IosAlert.newBuilder()
                .setTitleAndBody(message.getSendTitle(), "", message.getSendContent())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setContentAvailable(true)
                                .setAlert(iosAlert)
                                //.incrBadge(1)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("userinfo", alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * <p>
     * 群推
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/2 17:19
     *return response
     */
    public static PushPayload buildPushObjectAndroidIosListaliasAlert(List<String> alias, String alert) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setMsgContent(alert)
                        .build()
                )
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * <p>
     * 群推 ios
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/08 17:19
     *return PushPayload
     */
    public static PushPayload buildPushObjectIosListaliasAlert(List<String> alias, String alert) {
        SendMessage message = GsonUtil.gson2Object(alert, SendMessage.class);
        IosAlert iosAlert = IosAlert.newBuilder()
                .setTitleAndBody(message.getSendTitle(), "", message.getSendContent())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setContentAvailable(true)
                                .setAlert(iosAlert)
                                //.incrBadge(1)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("userinfo", alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * <p>
     * 群推 ios
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/08 17:19
     *return PushPayload
     */
    public static PushPayload buildPushObjectIosAll(String alert) {
        SendMessage message = GsonUtil.gson2Object(alert, SendMessage.class);
        IosAlert iosAlert = IosAlert.newBuilder()
                .setTitleAndBody(message.getSendTitle(), "", message.getSendContent())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setContentAvailable(true)
                                .setAlert(iosAlert)
                                //.incrBadge(1)
                                .setBadge(1)
                                .setSound("default")
                                .addExtra("userinfo", alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(flag)
                        .setTimeToLive(86400*10)
                        .build())
                .build();
    }

    /**
     * 极光推送方法(采用java SDK)
     *
     * @param alias 对象别名
     * @param alert  推送内容
     * @return PushResult
     */
    public static void push(String alias, String alert) {
        LOG.info("----------jiguang--obect--------"+alias);
        PushPayload payload = buildPushObjectAndroidIosAliasAlert(alias, alert);
        PushPayload payloadIos = buildPushObjectIosAliasAlert(alias, alert);
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            LOG.error("-----jiguang--------");
        }
        try{
            jpushClient.sendPush(payloadIos);
        }catch (Exception e) {
            LOG.error("-----jiguang--------");
        }
    }

    /**
     * <p>
     * 推送给所有人
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/8/2 16:24
     *return response
     */
    public static void pushAll(String alert) {
        PushPayload payload =  PushPayload.messageAll(alert);
        PushPayload payloadIos =  buildPushObjectIosAll(alert);
        try {
            jpushClient.sendPush(payloadIos);
        } catch (Exception e) {
            LOG.error("-----jiguang------pushAll--");
        }
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            LOG.error("-----jiguang------pushAll--");
        }
    }

    /**
     *
     * @param alias 对象别名集合
     * @param alert  推送内容
     * Author: zhouye <br/>
     * Date: 2017/8/2 16:27
     */
    public static void pushList(List<String> alias, String alert){
        PushPayload payload = buildPushObjectAndroidIosListaliasAlert(alias,alert);
        PushPayload payloadIos = buildPushObjectIosListaliasAlert(alias,alert);
        try {
            jpushClient.sendPush(payloadIos);
        } catch (Exception e) {
            LOG.error("-----jiguang-----pushList---");
        }
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e) {
            LOG.error("-----jiguang-----pushList---");
        }
    }
}

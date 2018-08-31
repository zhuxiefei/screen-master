package com.betel.estatemgmt.utils;


import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 短信工具
 * </p>
 * ClassName: BaiduSmsUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/10/16 14:08 <br/>
 * Version: 1.0
 */
public class BaiduSmsUtil {
    /**
     * 日志对象
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaiduSmsUtil.class);

    private static final String MSGSMS_SERVER_URL = "http://sms.bj.baidubce.com";

    private static final String MSGSMS_ACCESSKEY = "1fa6594764f44d0fae8f0a25f2986dd9";

    private static final String MSGSMS_SECRETKEY = "f1b9aa862037474a93d119306324d39c";

    private static final String MSGPUS_INVOKE_ID = "uHKqPOf0-PhMI-htfb";

    /**
     * 短信发送成功
     */
    private static final int SMS_SEND_SUCCESS = 1;

    /**
     * 短信发送失败
     */
    private static final int SMS_SEND_FAILURE = 2;

    /**
     * 短信发送过于频繁
     */
    private static final int SMS_SEND_TOO_FREQUENT = 3;

    /**
     * <p>
     * 检验验证码是否正确
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 11:38
     *
     * @param phone        手机号
     * @param mark         验证码类型标记
     * @param validateCode 验证码
     * @return 验证码是否正确，true：是，false：否
     */
    public static boolean checkValidateCode(String phone, String mark, String validateCode) {
        //校验入参不可为空
        if (StringUtil.isBlank(phone) || StringUtil.isBlank(mark) || StringUtil.isBlank(validateCode)) {
            return false;
        }
        // redis的key
        String redisKey = mark + phone;
        //获取验证码详情
        String smsCodeInfo = RedisManager.get(redisKey);
        if (StringUtil.isEmpty(smsCodeInfo)) {
            //如果取不到，说明验证码失效或不存在
            return false;
        }
        //获取原有验证码
        JSONObject jsStr = JSONObject.fromObject(smsCodeInfo);
        String code = jsStr.getString("validateCode");

        //返回验证码比较结果
        if (validateCode.equals(code)) {
            //RedisManager.delete(redisKey);
        } else {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * 发送短信验证码
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 10:28
     *
     * @param phone 接受短信手机号
     * @param mark  验证码类型标记，"login"：登录验证码，"register"：注册验证码，"common"：通用验证码
     * @return 发送结果，1：发送成功，2：发送失败，3：发送过于频繁（1分钟以内）
     */
    public static int sendValidateCode(String phone, String mark) {
        int result = SMS_SEND_SUCCESS;
        //从配置中读取短信失效时间，单位：分钟
        int smsExpireTime = Integer.parseInt(ConfigManager.read(ConfigName.SMS_EXPIRE_TIME));
        //生成随机数
        String code = getCode();
        //构造短信（需要符合短信平台的模板）
        String[] phones = new String[]{phone};
        String[] params = new String[]{code, ConfigManager.read(ConfigName.SMS_EXPIRE_TIME)};

        // redis的key
        String redisKey = mark + phone;
        String smsCodeInfo = RedisManager.get(redisKey);

        if (StringUtil.isBlank(smsCodeInfo)) {
            //发送验证码
            if (!sendSms(BaiduSmsCode.COMMON_VALIDATE_TEMPLATE,phones,params)) {
                return SMS_SEND_FAILURE;
            }
            // 存储redis
            JSONObject jsonValue = new JSONObject();
            // 短信验证码
            jsonValue.put("validateCode", code);
            // 发送时间
            jsonValue.put("createTime", System.currentTimeMillis());
            RedisManager.add(redisKey, jsonValue.toString(), smsExpireTime * 60);
        } else {
            //获取原有验证码
            JSONObject jsStr = JSONObject.fromObject(smsCodeInfo);
            Long createTime = jsStr.getLong("createTime");
            Long nowTime = System.currentTimeMillis();
            // 如果时间差超过一分钟可再次发送
            if ((nowTime - createTime) > 60 * 1000) {
                //发送验证码
                if (!sendSms(BaiduSmsCode.COMMON_VALIDATE_TEMPLATE,phones,params)) {
                    return SMS_SEND_FAILURE;
                }
                //删除原有验证码
                RedisManager.delete(redisKey);
                // 存储redis
                JSONObject jsonValue = new JSONObject();
                // 短信验证码
                jsonValue.put("validateCode", code);
                // 发送时间
                jsonValue.put("createTime", System.currentTimeMillis());
                RedisManager.add(redisKey, jsonValue.toString(), smsExpireTime * 60);
            } else {
                result = SMS_SEND_TOO_FREQUENT;
            }
        }
        return result;
    }

    /**
     * <p>
     * 生成6位随机数字验证码
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/17 9:53
     *
     * @return 6位随机数
     */
    private static String getCode() {
        return StringUtil.getRandomStr(6, StringUtil.DEFAULT_RANDOM_NUM);
    }

    /**
     * <p>
     * 发送短信
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/10/16 17:05
     *
     * @param templateCode 短信模板ID
     * @param phoneNumbers  手机号数组
     * @param parms        参数列表
     * @return true为成功，false为失败
     */
    public static boolean sendSms(String templateCode, String[] phoneNumbers, String[] parms) {
     /*   // ak、sk等config
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setEndpoint(MSGSMS_SERVER_URL);
        config.setCredentials(new DefaultBceCredentials(MSGSMS_ACCESSKEY, MSGSMS_SECRETKEY));
        // 实例化发送客户端
        SmsClient smsClient = new SmsClient(config);
        // 定义请求参数

        Map<String, String> vars = new HashMap<>();
        // 若模板内容为：您的验证码是\${code},在\${time}分钟内输入有效
        for (int i = 0; i < parms.length; i++) {
            vars.put("PARM" + (i + 1), parms[i]);
        }

        // 发送短信，开启线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        //实例化请求对象
        for (String phoneNumber : phoneNumbers) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    SendMessageV2Request request = new SendMessageV2Request();
                    request.withInvokeId(MSGPUS_INVOKE_ID).withPhoneNumber(phoneNumber).withTemplateCode(templateCode).withContentVar(vars);

                    // 发送请求
                    SendMessageV2Response response = smsClient.sendMessage(request);
                    if (null == response || !response.isSuccess()) {
                        LOG.error("Send Sms to " + phoneNumber + ",response=" + response);
                    } else {
                        LOG.info("Send Sms to " + phoneNumber + ", result=" + (response != null && response.isSuccess()));
                    }
                }
            });
        }

        // 关闭线程池
        threadPoolExecutor.shutdown();
*/
        // 现阶段不关注发送的结果，默认发送成功
        return true;
    }
}

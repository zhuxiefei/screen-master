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

package com.betel.estatemgmt.utils;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 短信验证码发送工具
 * </p>
 * ClassName: MsgUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/16 17:39 <br/>
 * Version: 1.0 <br/>
 */
public class SmsUtil {

    private static final String SIGN = "四合院社区";

    private static final String NAME = "jsbt";

    private static final String PASSWORD = "C6AF62307288E70DFC0D19B1665B";

    private static final String URL = "http://web.1xinxi.cn/asmx/smsservice.aspx?";

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
     * 验证码类型：登录
     */
    public static final String CODE_MARK_LOGIN = "login";

    /**
     * 验证码类型：注册
     */
    public static final String CODE_MARK_REGISTER = "register";

    /**
     * 验证码类型：通用
     */
    public static final String CODE_MARK_COMMON = "common";

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
        StringBuilder sb = new StringBuilder();
        sb.append("尊敬的用户，您的验证码是：");
        sb.append(code);
        sb.append("，");
        sb.append(smsExpireTime);
        sb.append("分钟内有效。请勿向任何人提供您收到的短信验证码！");

        // redis的key
        String redisKey = mark + phone;
        String smsCodeInfo = RedisManager.get(redisKey);

        if (StringUtil.isBlank(smsCodeInfo)) {
            //发送验证码
            if (!sendSms(phone, sb.toString())) {
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
                if (!sendSms(phone, sb.toString())) {
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
     * Date: 2017/5/16 18:25
     *
     * @param phone   接收人手机号
     * @param content 发送内容
     * @return 如：0,2017051619150826848796149,0,1,0,提交成功
     */
//    private static boolean sendSms(String phone, String content) {
//        String result = "";
//        BufferedReader in = null;
//        try {
//            // 创建StringBuffer对象用来操作字符串
//            StringBuilder sb = new StringBuilder(URL);
//            // 向StringBuffer追加用户名
//            sb.append("name=" + NAME);
//            // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
//            sb.append("&pwd=" + PASSWORD);
//            // 向StringBuffer追加手机号码
//            sb.append("&mobile=" + phone);
//            // 向StringBuffer追加消息内容转URL标准码
//            sb.append("&content=" + URLEncoder.encode(content, "UTF-8"));
//            // 追加发送时间，可为空，为空为及时发送
//            sb.append("&stime=");
//            // 加签名
//            sb.append("&sign=" + URLEncoder.encode(SIGN, "UTF-8"));
//            // type为固定值pt extno为扩展码，必须为数字 可为空
//            sb.append("&type=pt&extno=");
//            // 创建url对象
//            URL url = new URL(sb.toString());
//            // 打开url连接
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            // 设置url请求方式 ‘get’ 或者 ‘post’
//            connection.setRequestMethod("POST");
//            // 发送
//            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//            // 返回发送结果
//            result = in.readLine();
//        } catch (IOException e) {
//            // ignore
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//        boolean isSuccess = false;
//        if (!StringUtil.isBlank(result)) {
//            result = result.substring(0, result.indexOf(","));
//            if ("0".equals(result)) {
//                isSuccess = true;
//            }
//        }
//        return isSuccess;
//    }
    /**
     * <p>
     * 公共的发送短信
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/5/16 18:25
     *
     * @param phone   接收人手机号
     * @param content 发送内容
     * @return 如：0,2017051619150826848796149,0,1,0,提交成功
     */
    public static boolean sendMessage(String phone, String content){
        return  sendSms(phone,content);
    }

    private static boolean sendSms(String phone, String content) {
        try {
            String url="http://sdk2.yd10086.cn:8888/sms.aspx";
            HttpPost httppost=new HttpPost(url);
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userid","2738"));
            params.add(new BasicNameValuePair("account","siheyuan"));
            params.add(new BasicNameValuePair("password","123456"));
            params.add(new BasicNameValuePair("mobile",phone));
            params.add(new BasicNameValuePair("content",content+"【四合院社区】"));
            params.add(new BasicNameValuePair("sendTime",""));
            params.add(new BasicNameValuePair("action","send"));
            params.add(new BasicNameValuePair("extno",""));
            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response=new DefaultHttpClient().execute(httppost);
            if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                String result= EntityUtils.toString(response.getEntity());
            }
        }catch (Exception e){
            // ignore
        }
        return true;
    }
}

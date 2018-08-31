package com.betel.estatemgmt.utils.wechatpay;

import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.utils.IpAddrUtil;
import com.betel.estatemgmt.utils.StringUtil;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: WeChatPay <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/15 8:59 <br/>
 * Version: 1.0 <br/>
 */
public class WeChatPay {
    private static final Logger LOG = LoggerFactory.getLogger(WeChatPay.class);
    //回调路径
    private static  String notify_url;

    private static WeChatPay weChatPay;

    //私有话构造器
    private WeChatPay(){
        if (LOG.isInfoEnabled()) {
            LOG.info("============WeChatPay=====init=========start");
        }
        notify_url = ConfigManager.read("pay.return.url")+"wechatpayForSure";
        if (LOG.isInfoEnabled()) {
            LOG.info("============WeChatPay=====notify_url========="+notify_url);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("============WeChatPay=====init=========end=====");
        }
    }
    private static Logger logger = LoggerFactory.getLogger(WeChatPay.class);

    //实例化构造器
    public static void getInstence(){
        if(weChatPay==null){
            weChatPay = new WeChatPay();
        }
    }
    /**
     * <p>
     * 微信统一下单
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/28 10:25
     *
     * @param flowNo 订单号
     * @param money  支付金额
     *
     * @return Map
     * @throws IOException
     * @throws JDOMException
     */
    public static Map wxPrePay(String flowNo,BigDecimal money,String startTime,String expireTime) throws IOException, JDOMException {
        //微信支付的金钱单位是分
        String price = money.multiply(new BigDecimal("100")).toString();
        price = price.substring(0,price.indexOf("."));
        //设置回调地址-获取当前的地址拼接回调地址
        SortedMap<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appid", ConfigUtil.APPID);
        parameters.put("mch_id", ConfigUtil.MCH_ID);
        parameters.put("nonce_str", PayCommonUtil.createNoncestr());
        parameters.put("body", "费用缴纳");
        parameters.put("out_trade_no",flowNo); //订单id
        parameters.put("fee_type", "CNY");
        parameters.put("total_fee", price);
        parameters.put("spbill_create_ip", IpAddrUtil.getIpAddress());
        parameters.put("notify_url", notify_url);
        parameters.put("trade_type", "APP");
        parameters.put("time_start",startTime);
        parameters.put("time_expire",expireTime);
        //设置签名
        String sign = PayCommonUtil.createSign("UTF-8",parameters);
        parameters.put("sign", sign);
        //封装请求参数结束
        String requestXML = PayCommonUtil.getRequestXml(parameters);
        if(logger.isInfoEnabled()) {
            logger.info("-------requestXML-------:" + requestXML);
        }
        //调用统一下单接口
        String result = PayCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        if(logger.isInfoEnabled()) {
            logger.info("-------weixinPay---------result:" + result);
        }
        if(StringUtil.isBlank(result)){
            return null;
        }
        //**统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay**//*
        Map<String, String> map = XMLUtil.doXMLParse(result);
        String prepayId = map.get("prepay_id");
        if(StringUtil.isBlank(prepayId)){
            return null;
        }
        //前端进行接口请求，就不需要进行处理在签名封装了
        SortedMap<String, String> parameterMap = new TreeMap<String, String>();
        parameterMap.put("appid", ConfigUtil.APPID);
        parameterMap.put("partnerid", ConfigUtil.MCH_ID);
        parameterMap.put("prepayid", prepayId);
        parameterMap.put("package", "Sign=WXPay");
        parameterMap.put("noncestr", map.get("nonce_str"));
        //本来生成的时间戳是13位，但是请求必须是10位，所以截取了一下
        parameterMap.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0,10));
        String signForPay = PayCommonUtil.createSign("UTF-8",parameterMap);
        parameterMap.put("sign", signForPay);
        if(logger.isInfoEnabled()) {
            logger.info("-------weixinPay---------orderInfo:" + parameterMap);
        }
        return parameterMap;
    }
}

package com.betel.estatemgmt.utils.alipay;

/*import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;*/
import com.betel.estatemgmt.common.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*import static com.alipay.api.AlipayConstants.CHARSET_UTF8;
import static com.alipay.api.AlipayConstants.FORMAT_JSON;*/

/**
 * <p>
 * 支付宝工具类
 * </p>
 * ClassName: Alipay <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/13 11:04 <br/>
 * Version: 1.0 <br/>
 */
public class Alipay {

    private static final Logger LOG = LoggerFactory.getLogger(Alipay.class);
    //private static AlipayClient alipayClient;
    private static String returnURL;

    //创建单例对象
    private static Alipay alipay;

    /**
     * <p>
     * 私有构造器
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/14 13:51
     *return response
     */

    private Alipay(){
        if (LOG.isInfoEnabled()) {
            LOG.info("============alipay init start...=========");
        }
        String appPrivateKey = AliPayConfig.PRIVATE_KEY;
        String alipayPublicKey = AliPayConfig.PUBLIC_KEY;
        String appId = AliPayConfig.APP_ID;
        String serverUrl = AliPayConfig.SERVER_URL;
        returnURL = ConfigManager.read("pay.return.url")+"alipayForSure";
        if (LOG.isInfoEnabled()) {
            LOG.info("============alipay=====returnURL========="+returnURL);
        }
       // alipayClient = new DefaultAlipayClient(serverUrl, appId, appPrivateKey, FORMAT_JSON, CHARSET_UTF8, alipayPublicKey, "RSA2");
        if (LOG.isInfoEnabled()) {
            LOG.info("============alipay init end...=========");
        }
    }


    /**
     * <p>
     *  返回单例对象
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/14 13:47
     *return Alipay
     */
    public static Alipay getInstance(){
        if(alipay==null){
             alipay = new Alipay();
        }
        return alipay;
    }

    /**
     * <p>
     * 支付，生成订单，返回orderString，
     * </p>
     * @param outTradeNo 订单号
     * @param totalAmount  支付金额
     * @param productCode  商品编号
     * Author: zhouye <br/>
     * Date: 2017/9/14 13:52
     *return String
     */
    public static String pay(String outTradeNo,String totalAmount,String productCode){
        /*AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("费用缴纳");
        model.setSubject("费用缴纳");
        //订单id
        model.setOutTradeNo(outTradeNo);
        //逾期时间，关闭订单 5分钟
        model.setTimeoutExpress("5m");
        model.setTotalAmount(totalAmount);
        //产品编号
        model.setProductCode(productCode);
        if(LOG.isInfoEnabled()){
            LOG.info("---pay -----------request------"+model);
        }
        request.setBizModel(model);
        request.setNotifyUrl(returnURL);
        String orderInfo = null;
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            //就是orderString 可以直接给客户端请求，无需再做处理。
            orderInfo = response.getBody();
            return orderInfo;
        } catch (AlipayApiException e) {
            LOG.error("--------Alipay--------createOrder---error",e);
        }
        return orderInfo;*/
        return null;
    }

    /**
     * <p>
     * 官网的demo
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/9/14 14:04
     *return response
     */
  /*  public static void main(String[] args) {
       //实例化客户端
        AlipayClient AlipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, "APP_PRIVATE_KEY", FORMAT_JSON, CHARSET_UTF8, "ALIPAY_PUBLIC_KEY", "RSA2");
      //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
      //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo("outtradeno");
        model.setTimeoutExpress("15m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }*/
}

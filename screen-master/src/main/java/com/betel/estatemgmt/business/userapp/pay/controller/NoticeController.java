package com.betel.estatemgmt.business.userapp.pay.controller;

import com.betel.estatemgmt.business.userapp.pay.constant.PayConstant;
import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.alipay.AliPayConfig;
import com.betel.estatemgmt.utils.wechatpay.PayCommonUtil;
import com.betel.estatemgmt.utils.wechatpay.XMLUtil;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 支付异步回调接口
 * </p>
 * ClassName: NoticeController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/19 14:19 <br/>
 * Version: 1.0 <br/>
 */
@Controller
public class NoticeController {
    private static final Logger LOG = LoggerFactory.getLogger(NoticeController.class);
    @Autowired
    AppPayService appPayService;

    @RequestMapping(value = "alipayForSure")
    public void alipayForSure(HttpServletRequest request,HttpServletResponse response){
        if(LOG.isInfoEnabled()){
            LOG.info("----------------alipayForSure-----start------");
        }
        PrintWriter out = null;
        try {
             out = response.getWriter();
        } catch (IOException e) {
            LOG.error("-----alipayForSure----------",e);
        }
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        if(LOG.isInfoEnabled()){
            LOG.info("----------------alipayForSure------requestParams----"+requestParams);
        }
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            if(LOG.isInfoEnabled()){
                LOG.info("----------------alipayForSure-----"+name+":"+valueStr);
            }
            //乱码解决，这段代码在出现乱码时使用。
            params.put(name, valueStr);
        }
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        //验签操作
        boolean flag;
        try {
            flag = false;
            //AlipaySignature.rsaCheckV1(params, AliPayConfig.APP_PUBLIC_KEY, "UTF-8","RSA2");
            if(LOG.isInfoEnabled()){
                LOG.info("----------------alipayForSure------flag----"+flag);
            }
            if(flag){
                String code = params.get("trade_status");
                if(PayConstant.TRADE_SUCCESS.equals(code)){
                    String flowNo =  params.get("out_trade_no");
                    ExpenseFlow expenseFlow = appPayService.findExpenseFlowByFlowNo(flowNo);
                    if(expenseFlow!=null&&expenseFlow.getFlowStatus()!=PayConstant.FLOW_SUSSES){
                        appPayService.surePay(flowNo);
                        //发送短信和通知
                        sendMessage(flowNo);
                        if(LOG.isDebugEnabled()){
                            LOG.debug("----------------alipayForSure-----surePay--success----"+flowNo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("----alipayForSure------",e);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("----------------alipayForSure-----end------");
        }
        out.print("success");
    }

    @RequestMapping(value = "wechatpayForSure")
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(LOG.isInfoEnabled()){
            LOG.info("----------------wechatpayForSure-----start------");
        }
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //解析xml成map
        Map<String, String> m;
        m = XMLUtil.doXMLParse(sb.toString());
        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            if(LOG.isInfoEnabled()){
                LOG.info("----------------wechatpayForSure-----"+parameter+":"+v);
            }
            packageParams.put(parameter, v);
        }
        //判断签名是否正确
        String resXml = "";
        if(PayCommonUtil.isTenpaySign(PayConstant.UTF, packageParams)) {
            if(PayConstant.SUCCESS.equals((String)packageParams.get(PayConstant.RESULT_CODE))){
                // 这里是支付成功
                //商户订单号
                String outTradeNo = (String)packageParams.get("out_trade_no");
                //查询订单 根据订单号查询订单
                ExpenseFlow expenseFlow = appPayService.findExpenseFlowByFlowNo(outTradeNo);
                if(expenseFlow!=null&&expenseFlow.getFlowStatus()!=PayConstant.FLOW_SUSSES) {
                    appPayService.surePay(outTradeNo);
                    sendMessage(outTradeNo);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("----------------wechatpayForSure-----surePay--success----" + outTradeNo);
                    }
                    resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
                }
            }else {
                LOG.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
        } else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
            LOG.info("通知签名验证失败");
        }
        if(LOG.isInfoEnabled()){
            LOG.info("----------------wechatpayForSure-----end------"+resXml);
        }
        //处理业务
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    /**
     * 发送缴费成功短信和通知
     * @param flowNo
     */
    private void sendMessage(String flowNo) throws Exception {
        ExpenseFlow smsExpense = appPayService.findUserPhoneByFlowNo(flowNo);
        List<Expenses> expensesList = appPayService.findByFlowNo(flowNo);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (expensesList!=null && expensesList.size()>0){
            for (Expenses ex:
                    expensesList) {
                //确定项目类型
                String itemType="";
                if(ExpenseStaticStatus.ITEM_TYPE_ONE.equals(ex.getItemType())){itemType="物业费";}
                if(ExpenseStaticStatus.ITEM_TYPE_TWO.equals(ex.getItemType())){itemType="公摊水电费";}
                if(ExpenseStaticStatus.ITEM_TYPE_THREE.equals(ex.getItemType())){itemType="购买停车费";}
                if(ExpenseStaticStatus.ITEM_TYPE_FOUR.equals(ex.getItemType())){itemType="租赁停车费";}
                if(ExpenseStaticStatus.ITEM_TYPE_FIVE.equals(ex.getItemType())){itemType="能耗费";}
                String sms = "尊敬的业主"+ex.getHouseMaster()+"，您于"+sdf.format(new Date())+"缴纳"
                        + itemType+",共合计人民币"+ex.getTotalAmount()+"元。感谢您的配合。";
                String[] phones = new String[]{smsExpense.getBillPayer()};
                String[] params = new String[]{ex.getHouseMaster(),sdf.format(new Date()),
                        itemType,ex.getTotalAmount()};
                BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_SUCCESS_TEMPLATE,phones,params);
                if (!StringUtil.isBlank(ex.getPhone())){
                    //通知
                    UserAccount account = appPayService.findByAcctName(ex.getPhone());
                    if (account!=null){
                        //创建系统通知对象，将通知存到数据库
                        Notice notice = new Notice();
                        notice.setNoticeStatus(1);
                        notice.setNoticeType(PayConstant.SEND_NO);
                        notice.setCreateTime(new Date(System.currentTimeMillis()));
                        notice.setNoticeUserId(account.getUserId());
                        notice.setNoticeContent(sms);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========before addNotice========notice=" + notice);
                        }
                        Long noticeId = appPayService.insertNotice(notice);
                        //返回主键
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========after addNotice========notice=" + notice);
                        }
                        SendMessage send = new SendMessage();
                        send.setSendId(noticeId.toString());
                        send.setSendTitle("缴费成功通知");
                        send.setSendNo(PayConstant.SEND_NO.toString());
                        send.setSendType(PayConstant.SEND_TYPE);
                        send.setSendContent(sms);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========SendMessage========" + send);
                        }
                        //转json
                        String pushInfo = GsonUtil.object2Gson(send);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========SendMessage to json========" + pushInfo);
                        }
                        //推送
                        MsgPushUtils.push(account.getAcctName(), pushInfo);
                    }
                }
            }
        }
    }
}

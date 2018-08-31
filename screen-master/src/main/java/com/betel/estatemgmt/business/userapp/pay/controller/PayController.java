package com.betel.estatemgmt.business.userapp.pay.controller;

import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.userapp.house.service.AppMemberService;
import com.betel.estatemgmt.business.userapp.pay.code.AppPayCode;
import com.betel.estatemgmt.business.userapp.pay.constant.PayConstant;
import com.betel.estatemgmt.business.userapp.pay.model.*;
import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.alipay.Alipay;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: PayController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/14 14:09 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/payment")
public class PayController {
    private static final Logger LOG = LoggerFactory.getLogger(Alipay.class);

    @Autowired
    AppMemberService appMemberService;

    @Autowired
    AppAuthService appAuthService;

    @Autowired
    AppPayService appPayService;

    /**
     * 条件查询缴费列表
     * Create By ZhouYe ON 2017/9/18 13:24
     */
    @RequestMapping(value = "v1/findAllPayments", method = RequestMethod.GET)
    public Response findAllPayments(PayPage payPage, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/findAllPayments------start" + payPage);
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        try {
            userId = AESUtil.decrypt(userId);
        } catch (Exception e) {
            LOG.error("------app/payment/v1/findAllPayments------error--", e);
        }
        //判断expenseType和itemType是否为空
        if (payPage.getExpenseType() == null) {
            response.setCode(AppPayCode.EXPENSETYPE_NULL);
        } else if (payPage.getHouseId() != null) {
            //判断房屋是否存在和是否是该房屋的成员
            try {
                String code = validateHouseAndMember(userId, payPage.getHouseId());
                if (!code.equals(StatusCode.SUCCESS)) {
                    response.setCode(code);
                    if (LOG.isInfoEnabled()) {
                        LOG.info("------app/payment/v1/findAllPayments------end" + response);
                    }
                    return response;
                }
            } catch (Exception e) {
                LOG.error("------app/payment/v1/findAllPayments------eeror--", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        //获取认证的房屋,
        StringBuffer houseIds = new StringBuffer();
        List<AuthHouse> lists = null;
        try {
            lists = appAuthService.findHouseAuth(userId, HouseConstant.TWO);
        } catch (Exception e) {
            response.setCode(StatusCode.FAILURE);
            LOG.error("----app/auth/v1/findAllPayments------error-", e);
        }
        //查询列表结果处理
        for (AuthHouse list : lists) {
            //筛选认证成功过的房屋
            houseIds.append(list.getHouseId() + ",");
        }
        if (houseIds.length() == 0) {
            response.setCode(AppPayCode.NOT_HOUSE_MEMBER);
            if (LOG.isInfoEnabled()) {
                LOG.info("------app/payment/v1/findAllPayments------end" + response);
            }
            return response;
        }
        String houseIdes = "";
        if (lists.size() > 0) {
            houseIdes = houseIds.substring(0, houseIds.lastIndexOf(","));
        }
        //将房屋的id赋值到payPage
        if (StringUtils.isBlank(payPage.getHouseId())) {
            payPage.setHouseId(houseIdes);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("------payPage---------" + payPage);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("------houseIds---------" + houseIdes);
        }
        if (response.getCode().equals(StatusCode.SUCCESS)) {
            AllPayList allPayList = null;
            try {
                allPayList = appPayService.findAllPayList(payPage, houseIdes);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------allPayList---------" + allPayList);
                }
            } catch (Exception e) {
                LOG.error("----app/auth/v1/findAllPayments-------", e);
                response.setCode(StatusCode.FAILURE);
            }
            response.setData(allPayList);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/findAllPayments------end" + response);
        }
        return response;
    }

    /**
     * 判断房屋是否存在和是否是该房屋的成员
     * Create By ZhouYe ON 2017/9/18 14:23
     */
    private String validateHouseAndMember(String userId, String houseId) throws Exception {
        //根据房屋id查询房屋
        House house = appAuthService.findHouseByHouseId(houseId);
        if (house == null) {
            return AppPayCode.HOUSE_DELETE;
        }
        //查询是否进行成员认证
        List<AuthHouse> lists = null;
        lists = appAuthService.findHouseAuth(userId, HouseConstant.TWO);
        boolean flag = false;
        for (AuthHouse list : lists) {
            if (list.getHouseId().equals(houseId.toString())) {
                flag = true;
            }
        }
        if (flag == false) {
            return AppPayCode.NOT_HOUSE_MEMBER;
        }
        return StatusCode.SUCCESS;
    }

    /**
     * <p>
     * 支付
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/23 10:58
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "v1/pay")
    public Response pay(@RequestBody PayModel payModel, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/pay------start------------" + payModel);
        }
        Response response = new Response();
        if (StringUtil.isBlank(payModel.getBillNo())) {
            response.setCode(AppPayCode.BILL_NO_NULL);
        } else if (null == payModel.getPayType()) {
            response.setCode(AppPayCode.BILL_NO_NULL);
        } else {
            try {
                String userId = request.getHeader("userId");
                userId = AESUtil.decrypt(userId);
                List<ExpenseBill> lists = appPayService.findExpenseBillByBillNos(payModel.getBillNo());
                if (lists.size() == 0) {
                    response.setCode(AppPayCode.BILL_DELETE);
                    if (LOG.isInfoEnabled()) {
                        LOG.info("------app/payment/v1/pay------end------------" + response);
                    }
                    return response;
                }
                //判断是否是房屋的成员
                for (ExpenseBill list : lists) {
                    if (list.getHouseId() != null) {
                        String code = validateHouseAndMember(userId, list.getHouseId().toString());
                        if (!code.equals(StatusCode.SUCCESS)) {
                            response.setCode(code);
                            if (LOG.isInfoEnabled()) {
                                LOG.info("------app/payment/v1/pay------end------------" + response);
                            }
                            return response;
                        }
                    }
                }
                payModel.setUserId(userId);
                //支付
                appPayService.pay(payModel, response);
            } catch (Exception e) {
                LOG.error("------app/payment/v1/pay------error------------", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/pay------end------------" + response);
        }
        return response;
    }

    /**
     * 查询订单详情
     * Create By ZhouYe ON 2017/9/18 17:36
     */
    @RequestMapping(value = "v1/findPayments", method = RequestMethod.GET)
    public Response findPayments(String billNo, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/findPayments------start------------" + billNo);
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        //查询账单的状态
        try {
            userId = AESUtil.decrypt(userId);
            ExpenseBill expenseBill = appPayService.findExpenses(billNo);
            if (LOG.isDebugEnabled()) {
                LOG.info("------expenseBill------------------" + expenseBill);
            }
            if (expenseBill != null) {
                String code = validateHouseAndMember(userId, expenseBill.getHouseId().toString());
                if (StatusCode.SUCCESS.equals(code)) {
                    Payment payment = appPayService.findPayments(billNo);
                    response.setData(payment);
                } else {
                    response.setCode(code);
                }
                //判断是否支付
                if (expenseBill.getBillStatus() == PayConstant.TWO) {
                    //判断是否删除
                    if (expenseBill.getIsDelete() == PayConstant.BILL_DELETE) {
                        response.setCode(AppPayCode.BILL_DELETE);
                    }
                } else {
                    response.setCode(AppPayCode.BILL_NOT_PAY);
                }
            } else {
                response.setCode(AppPayCode.BILL_DELETE);
            }
        } catch (Exception e) {
            LOG.error("------app/payment/v1/findPayments------error", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/findPayments------end--" + response);
        }
        return response;
    }

    /**
     * 查询收费标准
     * Create By ZhouYe ON 2017/9/19 8:54
     */
    @RequestMapping(value = "v1/findFeeScale", method = RequestMethod.GET)
    public Response findFeeScale(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/findFeeScale------start------------");
        }
        Response response = new Response();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            //查询收费标准
            String feeScale = appPayService.findFeeScale(AESUtil.decrypt(estateId));
            if (LOG.isDebugEnabled()) {
                LOG.info("------feeScale------------------" + feeScale);
            }
            response.setData(feeScale);
        } catch (Exception e) {
            LOG.error("------app/payment/v1/findFeeScale---------error", e);
            response.setCode(StatusCode.FAILURE);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/findFeeScale------start------------" + response);
        }
        return response;
    }

    /**
     * 删除账单记录/删除预交费账单
     * Create By ZhouYe ON 2017/9/19 9:20
     */
    @RequestMapping(value = "v1/deletePayment", method = RequestMethod.POST)
    public Response deletePayment(@RequestBody DeleteBillReq billReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/deletePayment------start-----------billReq：-" + billReq);
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        try {
            userId = AESUtil.decrypt(userId);
            ExpenseBill expenseBill = appPayService.findExpenses(billReq.getBillNo());
            if (LOG.isDebugEnabled()) {
                LOG.info("------expenseBill------------------" + expenseBill);
            }
            if (billReq.getType().equals(1) && expenseBill != null && !expenseBill.getBillStatus().equals(PayConstant.ALIPAY_STATUS)) {
                //验证是否已缴费
                response.setCode(AppPayCode.BILL_NOT_PAY);
            } else if (billReq.getType().equals(2) && expenseBill != null && System.currentTimeMillis() >= expenseBill.getStartTime().getTime()){
                //验证预交费账单是否过了预交费时间
                response.setCode(AppPayCode.BILL_OVER_PREPAY);
            } else if (expenseBill != null) {
                //验证是否是房屋成员
                String code = validateHouseAndMember(userId, expenseBill.getHouseId().toString());
                if (LOG.isDebugEnabled()) {
                    LOG.info("------code------------------" + code);
                }
                if (code.equals(StatusCode.SUCCESS) && expenseBill.getIsDelete() == 1) {
                    appPayService.deletePayment(billReq);
                } else {
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("------app/payment/v1/deletePayment--error", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/deletePayment------end------------" + response);
        }
        return response;
    }

    /**
     * <p>
     * 索要票据
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 11:45
     */
    @RequestMapping(value = "v1/demandBill", method = RequestMethod.GET)
    public Response demandBill(String billNo, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/demandBill------start-----------billNo：-" + billNo);
        }
        Response response = new Response();
        String userId = request.getHeader("userId");
        try {
            userId = AESUtil.decrypt(userId);
            ExpenseBill expenseBill = appPayService.findExpenses(billNo);
            if (LOG.isDebugEnabled()) {
                LOG.debug("------expenseBill------------------" + expenseBill);
            }
            //验证是否已缴费
            if (expenseBill != null && !expenseBill.getBillStatus().equals(PayConstant.ALIPAY_STATUS)) {
                response.setCode(AppPayCode.BILL_NOT_PAY);
            } else if (expenseBill != null && !expenseBill.getIsDelete().equals(PayConstant.TWO)) {
                //验证是否是房屋成员
                String code = validateHouseAndMember(userId, expenseBill.getHouseId().toString());
                if (LOG.isDebugEnabled()) {
                    LOG.info("------code-------isHouse-----------" + code);
                }
                if (code.equals(StatusCode.SUCCESS) && expenseBill.getIsDelete() == 1) {
                    if (expenseBill.getDemandBillStatus() == PayConstant.TWO || expenseBill.getDemandBillStatus() == PayConstant.THREE) {
                        response.setCode(AppPayCode.BILL_COMMAND);
                    } else {
                        appPayService.demandBill(expenseBill.getBillNo());
                    }
                } else {
                    response.setCode(code);
                }
            } else {
                response.setCode(AppPayCode.BILL_DELETE);
            }
        } catch (Exception e) {
            LOG.error("------app/payment/v1/demandBill--error", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("------app/payment/v1/demandBill------end------------" + response);
        }
        return response;
    }


    /**
     * <p>
     * 确认收到收据
     * </p>
     * Author: jians.z <br/>
     * Date: 2018/1/5 12:56
     *
     * @param payList
     * @return response
     */
    @RequestMapping(value = "v1/updateDemand", consumes = "application/json;charset=UTF-8",method = RequestMethod.POST)
    public Response<String> updateDemand(@RequestBody PayList payList, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/payment/v1/updateDemand start========demandReq=" + payList.getBillNo());
        }
        Response<String> response = new Response<>();
        try {
            String userId = request.getHeader("userId");
            userId = AESUtil.decrypt(userId);
            ExpenseBill expenseBill = appPayService.findExpenses(payList.getBillNo());
            //验证是否是房屋成员
            String code = validateHouseAndMember(userId, expenseBill.getHouseId().toString());
            if (code.equals(StatusCode.SUCCESS)) {
                response.setCode(appPayService.updateDemandStatus(payList.getBillNo()));
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========app/payment/v1/updateDemand error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/payment/v1/updateDemand end========response" + response);
        }
        return response;
    }
}

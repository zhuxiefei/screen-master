package com.betel.estatemgmt.business.userapp.pay.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.pay.code.AppPayCode;
import com.betel.estatemgmt.business.userapp.pay.constant.PayConstant;
import com.betel.estatemgmt.business.userapp.pay.model.*;
import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.business.userapp.pay.util.PayUntil;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.expenses.constant.ExpensesValidation;
import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.business.web.expenses.service.ExpensesService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseFlowMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.mapper.property.PropertyConfigMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.property.PropertyConfig;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.system.quartz.QuartzManager;
import com.betel.estatemgmt.system.quartz.model.JobModel;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.alipay.Alipay;
import com.betel.estatemgmt.utils.wechatpay.WeChatPay;
import org.jdom.JDOMException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 缴费Services实现类
 * </p>
 * ClassName: AppPayServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/9/18 14:20 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AppPayServiceImpl implements AppPayService {
    private static final Logger LOG = LoggerFactory.getLogger(AppPayServiceImpl.class);
    @Autowired
    ExpenseBillMapper expenseBillMapper;

    @Autowired
    ExpenseItemMapper expenseItemMapper;

    @Autowired
    PropertyConfigMapper propertyConfigMapper;

//    @Autowired
//    UserAccountMapper userAccountMapper;

    @Autowired
    ExpenseFlowMapper expenseFlowMapper;

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private UserService userService;

    /**
     * 条件查询缴费列表
     * Create By ZhouYe ON 2017/9/18 14:58
     */
    @Override
    public AllPayList findAllPayList(PayPage payPage, String ids) throws ParseException {
        AllPayList allPayList = new AllPayList();
        payPage.setHouseIds(payPage.getHouseId().split(","));
        //获取缴费列表
        List<PayList> payList = expenseBillMapper.findAllPayList(payPage.getRowBounds(), payPage);
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------payList----------" + payList);
        }
        if (payPage.getExpenseType() == 1 || payPage.getExpenseType() == 3 || payPage.getExpenseType() == 4) {
            //计算滞纳金
            for (PayList pay : payList) {
                //获取滞纳金率
                ExpenseItem expenseItem = expenseItemMapper.selectByPrimaryKey(pay.getItemId());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("-------expenseItem----------" + expenseItem);
                }
                if (expenseItem != null) {
                    //计算滞纳金
                    if (System.currentTimeMillis() > pay.getDeadLine().getTime()) {
                        int days = daysBetweenAnd(pay.getDeadLine(), new Date());
                        double sum = days * Double.valueOf(String.valueOf(pay.getBillTotelAmount())) * expenseItem.getOverdueFine();
                        sum = sum / 100;
                        //滞纳金
                        pay.setOverdueFine(BigDecimal.valueOf(sum).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------allPayList----------" + allPayList);
            }
            //获取总金额 total
            BigDecimal total = new BigDecimal("0.00");
            if (!StringUtil.isBlank(ids)) {
                List<PayList> payLists = expenseBillMapper.findPayListByHouseIds(payPage);
                for (PayList pay : payLists) {
                    total = total.add(pay.getBillTotelAmount());
                    //滞纳金
                    if (System.currentTimeMillis() > pay.getDeadLine().getTime()) {
                        ExpenseItem expenseItem = expenseItemMapper.selectByPrimaryKey(pay.getItemId());
                        if (expenseItem != null) {
                            int days = daysBetweenAnd(pay.getDeadLine(), new Date());
                            double sum = (days * Double.valueOf(String.valueOf(pay.getBillTotelAmount())) * expenseItem.getOverdueFine());
                            sum = sum / 100;
                            total = total.add(BigDecimal.valueOf(sum).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------total----------" + total);
            }
            allPayList.setTotal(total);
        }
        payPage.result(payList);
        allPayList.setPage(payPage);
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------allPayList----------" + allPayList);
        }
        return allPayList;
    }

    private static final String TYPEONE = "物业费";
    private static final String TYPETWO = "公摊水电费";
    private static final String TYPETHREE = "购买停车费";
    private static final String TYPEFOUR = "租赁停车费";
    private static final String TYPEFIVE = "能耗费";

    /**
     * 查询缴费账单详情
     * Create By ZhouYe ON 2017/9/18 18:29
     */
    @Override
    public Payment findPayments(String billNo) throws ParseException {
        Payment payment = expenseBillMapper.findPayments(billNo);
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------payment----------" + payment);
        }
        if (payment != null) {
            String itemType = "";
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE) == payment.getItemType()) {
                itemType = TYPEONE;
            }
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO) == payment.getItemType()) {
                itemType = TYPETWO;
            }
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE) == payment.getItemType()) {
                itemType = TYPETHREE;
            }
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR) == payment.getItemType()) {
                itemType = TYPEFOUR;
            }
            if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE) == payment.getItemType()) {
                itemType = TYPEFIVE;
            }
            payment.setPayDetail(itemType + ":" + payment.getTotalAmount());
            //判断滞纳金是否为空，为空就不进行运算
            if (payment.getOverdueFine() != null) {
                payment.setTotalAmount(payment.getOverdueFine().add(payment.getTotalAmount()));
            } else {
                payment.setTotalAmount(payment.getTotalAmount());
            }
            //支付时间格式处理
            if (!StringUtil.isBlank(payment.getPayTime())) {
                String payTime = payment.getPayTime();
                payment.setPayTime(payTime.substring(0, payTime.lastIndexOf(".")));
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------payment----------" + payment);
        }
        return payment;
    }

    /**
     * 查询缴费账单状态
     * Create By ZhouYe ON 2017/9/18 19:28
     */
    @Override
    public ExpenseBill findExpenses(String billNo) {
        return expenseBillMapper.selectByPrimaryKey(billNo);
    }

    /**
     * <p>
     * 查询缴费账单
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/23 11:03
     *
     * @param billNo
     * @return
     * @throws Exception
     */
    @Override
    public List<ExpenseBill> findExpenseBillByBillNos(String billNo) {
        String[] strs = billNo.split(",");
        return expenseBillMapper.findExpenseBill(strs);
    }

    /**
     * 查询物业费收费标准
     * Create By ZhouYe ON 2017/9/19 9:11
     */
    @Override
    public String findFeeScale(String estateId) {
        PropertyConfig config = new PropertyConfig();
        config.setEstateId(estateId);
        config.setConfName(PayConstant.CHARGESTANDARD);
        PropertyConfig pro = propertyConfigMapper.selectByPrimaryKey(config);
        if (pro != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("--propertyConfig----repairfee-----" + pro);
            }
            return pro.getConfValue();
        }
        return null;
    }

    /**
     * 支付service
     * Create By ZhouYe ON 2017/9/19 9:41
     */
    @Override
    public void pay(PayModel payModel, Response response) throws ParseException, IOException, JDOMException, SchedulerException {
        synchronized (this) {
            if (payModel.getPayType().equals(PayConstant.ALIPAY)) {
                payByAliPay(payModel, response);
            } else if (payModel.getPayType().equals(PayConstant.WEIXINPAY)) {
                payWeiXin(payModel, response);
            } else {
                expensesService.payWebCourse(payModel.getBillNo(), response);
            }
        }
    }

    /**
     * <p>
     * 支付成功
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 14:34
     *
     * @param flowNo 流水的id
     * @return
     * @throws Exception
     */
    @Override
    public void surePay(String flowNo) {
        expenseBillMapper.surePay(flowNo);
        expenseFlowMapper.surePay(flowNo,new Date());
    }

    /**
     * <p>
     * 删除交易订单
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/25 9:23
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public void deletePayment(DeleteBillReq req) {
        if (req.getType().equals(1)){
            expenseBillMapper.deletePayment(req.getBillNo());
        }else if (req.getType().equals(2)){
            expenseBillMapper.deleteByPrimaryKey(req.getBillNo());
        }
    }

    /**
     * <p>
     * 根据flowNo查询流水详情
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/25 9:23
     *
     * @return
     * @throws Exception
     */
    @Override
    public ExpenseFlow findUserPhoneByFlowNo(String flowNo) throws Exception {
        ExpenseFlow expenseFlow = expenseFlowMapper.selectByPrimaryKey(flowNo);
        if (expenseFlow != null) {
//            UserAccount userAccount = userAccountMapper.selectByUserId(expenseFlow.getBillPayer());
            //jian.z 2018/1/21
            UserAccount userAccount = userService.findByUserId(expenseFlow.getBillPayer(),null);
            if (userAccount != null) {
                expenseFlow.setBillPayer(userAccount.getAcctName());
            }
        }
        return expenseFlow;
    }

    /**
     * <p>
     * 定时取消订单
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/25 10:14
     *
     * @return
     * @throws Exception
     */
    @Override
    public void cancelPay(String flowNo) {
        //删除流水
        expenseFlowMapper.deleteByPrimaryKey(flowNo);
        //重置账单
        expenseBillMapper.cancelPay(flowNo);
    }

    /**
     * <p>
     * 查询流水信息
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/27 19:28
     *
     * @return
     * @throws Exception
     */
    @Override
    public ExpenseFlow findExpenseFlowByFlowNo(String flow) {
        return expenseFlowMapper.selectByPrimaryKey(flow);
    }

    @Override
    public List<Expenses> findByFlowNo(String flowNo) {
        return expenseBillMapper.findByFlowNo(flowNo);
    }

    /**
     * <p>
     * 索要票据
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/11/10 11:50
     */
    @Override
    public void demandBill(String billNo) {
        ExpenseBill expenseBill = new ExpenseBill();
        expenseBill.setBillNo(billNo);
        expenseBill.setDemandBillStatus(PayConstant.TWO);
        expenseBill.setDemandBillTime(new Date());
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("----定时任务---索要收据计时开始----------" + billNo);
//        }
//        JobModel jobModel = new JobModel();
//        jobModel.setJobName(billNo);
//        Map map = new HashMap<>();
//        map.put("billNo", billNo);
//        jobModel.setDataMap(map);
//        try {
//            QuartzManager.askJob(jobModel);
//        } catch (Exception e) {
//            LOG.error("索要票据定时任务QuartzManager报错", e);
//        }
        expenseBillMapper.updateByPrimaryKeySelective(expenseBill);
    }

    @Override
    public UserAccount findByAcctName(String acctName) throws Exception {
        //jians.z 2018-1-21
        return userService.findByAccountName(acctName,null);
    }

    @Override
    public Long insertNotice(Notice notice) {
        noticeMapper.insertSelective(notice);
        return notice.getNoticeId();
    }

    /**
     * <p>
     * 微信支付
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 11:45
     *
     * @param payModel
     * @param response
     * @return
     * @throws Exception
     */
    private void payWeiXin(PayModel payModel, Response response) throws ParseException, IOException, JDOMException, SchedulerException {
        //查询List<ExpenseBill>  进行账单状态的判断
        List<ExpenseBill> expenseBills = expenseBillMapper.findExpenseBill(payModel.getBillNo().split(","));
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------expenseBills----------" + expenseBills);
        }
        String code = validateBill(expenseBills);
        if (code.equals(StatusCode.SUCCESS)) {
            //生成流水账单
            String flowNo = PayUntil.getFlowNo();
            ExpenseFlow expenseFlow = new ExpenseFlow();
            expenseFlow.setFlowNo(flowNo);
            expenseFlow.setChargeWay(PayConstant.WEIXINPAY);
            expenseFlow.setIsOnline(PayConstant.ON_LINE);
            expenseFlow.setCreateTime(new Date());
            expenseFlow.setBillPayer(payModel.getUserId());
            expenseFlow.setFlowStatus(PayConstant.PAY_STATUS);
            BigDecimal totalAmount = totalAmount(expenseBills);
            expenseFlow.setTotalAmount(totalAmount);
            //保存流水账单
            expenseFlowMapper.insertSelective(expenseFlow);
            //设置账单的流水号
            expenseBillMapper.updateFlowNoByBillNo(payModel.getBillNo().split(","), flowNo);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------expenseFlow----------" + expenseFlow);
            }
            JobModel jobModel = new JobModel();
            jobModel.setJobName(flowNo);
            Map map = new HashMap<>();
            map.put("flowNo", flowNo);
            jobModel.setDataMap(map);
            QuartzManager.addJob(jobModel);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            //设置微信预订单的失效时间
            Date now = new Date();
            String startTime = simpleDateFormat.format(now);
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            long time1 = cal.getTimeInMillis();
            String expireTime = simpleDateFormat.format(new Date(time1 + 1000 * 300));
            Map orderInfo = WeChatPay.wxPrePay(flowNo, totalAmount, startTime, expireTime);
            response.setData(orderInfo);
        } else {
            response.setCode(code);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------response----------" + response);
        }
    }

    /**
     * 支付宝支付
     * Create By ZhouYe ON 2017/9/19 9:45
     */
    private void payByAliPay(PayModel payModel, Response response) throws ParseException, SchedulerException {
        //查询List<ExpenseBill>  进行账单账单的判断
        List<ExpenseBill> expenseBills = expenseBillMapper.findExpenseBill(payModel.getBillNo().split(","));
        String code = validateBill(expenseBills);
        if (code.equals(StatusCode.SUCCESS)) {
            //生成流水账单
            String flowNo = PayUntil.getFlowNo();
            String produceCode = PayUntil.getProductCode();
            ExpenseFlow expenseFlow = new ExpenseFlow();
            expenseFlow.setFlowNo(flowNo);
            expenseFlow.setChargeWay(PayConstant.ALIPAY);
            expenseFlow.setIsOnline(PayConstant.ON_LINE);
            expenseFlow.setBillPayer(payModel.getUserId());
            expenseFlow.setCreateTime(new Date());
            expenseFlow.setFlowStatus(PayConstant.PAY_STATUS);
            BigDecimal totalAmount = totalAmount(expenseBills);
            expenseFlow.setTotalAmount(totalAmount);
            //保存流水账单
            expenseFlowMapper.insertSelective(expenseFlow);
            //定时任务
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------expenseFlow----------" + expenseFlow);
            }
            //设置账单的流水号
            expenseBillMapper.updateFlowNoByBillNo(payModel.getBillNo().split(","), flowNo);
            //添加定时任务
            JobModel jobModel = new JobModel();
            jobModel.setJobName(flowNo);
            Map map = new HashMap<>();
            map.put("flowNo", flowNo);
            jobModel.setDataMap(map);
            QuartzManager.addJob(jobModel);
            //生成预订单
            String orderInfo = Alipay.pay(flowNo, totalAmount.toString(), produceCode);
            response.setData(orderInfo);
        } else {
            response.setCode(code);
        }
    }

    /**
     * <p>
     * 计算代缴总金额
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 11:02
     *
     * @param expenseBills 账单集合
     * @return
     * @throws Exception
     */
    private BigDecimal totalAmount(List<ExpenseBill> expenseBills) throws ParseException {
        BigDecimal totalAmount = new BigDecimal("0.00");
        for (ExpenseBill expenseBill : expenseBills) {
            //获取滞纳金率
            totalAmount = totalAmount.add(expenseBill.getBillAmount());
            //计算滞纳金
            if (System.currentTimeMillis() > expenseBill.getDeadLine().getTime()) {
                ExpenseItem expenseItem = expenseItemMapper.selectByPrimaryKey(expenseBill.getItemId());
                if (expenseItem != null) {
                    int days = daysBetweenAnd(expenseBill.getDeadLine(), new Date());
                    double sum = days * Double.valueOf(expenseBill.getBillAmount().toString()) * expenseItem.getOverdueFine();
                    sum = sum / 100;
                    BigDecimal overdueFine = BigDecimal.valueOf(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
                    expenseBill.setOverdueFine(overdueFine);
                    expenseBillMapper.saveOverdueFine(expenseBill);
                    //代收总额
                    totalAmount = overdueFine.add(totalAmount);
                }
            } else {
                expenseBill.setOverdueFine(BigDecimal.ZERO);
                expenseBillMapper.saveOverdueFine(expenseBill);
            }
        }
        return totalAmount;
    }

    /**
     * <p>
     * 计算两个时间的差 单位 天,忽略 时分秒  天数差距
     * </p>
     * Author:ZhouYe<br/>
     * Date: 2017/9/19 11:09
     *
     * @param smdate bdate
     * @return
     * @throws Exception
     */
    private int daysBetweenAnd(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s1 = format.format(smdate);
        String s2 = format.format(bdate);
        smdate = format.parse(s1);
        bdate = format.parse(s2);
        Long time = (bdate.getTime() - smdate.getTime()) / (1000 * 3600 * 24);
        int day = Integer.valueOf(String.valueOf(time)) - 1;
        return day > 0 ? day : 0;
    }

    /**
     * 判断账单是否已支付或者待支付
     * Create By ZhouYe ON 2017/9/19 10:08
     */
    private String validateBill(List<ExpenseBill> expenseBills) {
        for (ExpenseBill expenseBill : expenseBills) {
            //判断账单是否已缴费
            if (expenseBill.getBillStatus() == 2) {
                return AppPayCode.BILL_IS_PAY;
            }
            //判断是否绑定流水
            ExpenseFlow expenseFlow = expenseFlowMapper.selectByPrimaryKey(expenseBill.getFlowNo());
            //判断流水
            if (expenseFlow != null) {
                return AppPayCode.BILL_WAIT_PAY;
            }
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public String updateDemandStatus(String billNo) {
        if (StringUtil.isBlank(billNo)) {
            return AppPayCode.BILL_NO_NULL;
        } else {
            ExpenseBill bill = expenseBillMapper.selectByPrimaryKey(billNo);
            if (bill != null & bill.getIsDelete() != PayConstant.BILL_DELETE) {
                if (bill.getBillStatus().equals(Integer.parseInt(ExpenseStaticStatus.TYPE_ONE))) {
                    return AppPayCode.BILL_NOT_PAY;
                } else if (bill.getBillStatus().equals(Integer.parseInt(ExpenseStaticStatus.TYPE_TWO)) && bill.getDemandBillStatus().equals(ExpensesValidation.BILL_IS_NOT_SUO_YAO)) {
                    return AppPayCode.BILL_NOT_SEND;
                } else if (bill.getBillStatus().equals(Integer.parseInt(ExpenseStaticStatus.TYPE_TWO)) && bill.getDemandBillStatus().equals(ExpensesValidation.BILL_IS_YI_SUO_YAO)) {
                    //未点击送货上门时不走此程序
                    ExpenseBill expenseBill = new ExpenseBill();
                    expenseBill.setBillNo(billNo);
                    expenseBill.setDemandBillStatus(ExpensesValidation.BILL_IS_DEMAND);
                    expenseBillMapper.updateByPrimaryKeySelective(expenseBill);
                    return StatusCode.SUCCESS;
                }
            } else {
                return AppPayCode.BILL_DELETE;
            }
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public ExpenseBill findByBilNo(String billNo) {
        return expenseBillMapper.selectByPrimaryKey(billNo);
    }

    @Override
    public String updateDemandStatusNew() throws ParseException {
        List<ExpenseBill> billList = expenseBillMapper.findList();
        for (int i=0;i<billList.size();i++){
            ExpenseBill expenseBill=billList.get(i);
            Date demandBillTime=expenseBill.getDemandBillTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(demandBillTime);
            // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
            calendar.add(Calendar.DATE, 15);
            Date date_one = calendar.getTime();
            String out = sdf.format(date_one)+" 00:00:00";
            Date date_two=sdf.parse(out);
            long datedate_three=date_two.getTime();
            if (datedate_three<=System.currentTimeMillis()){
                //执行更新操作
                expenseBill.setDemandBillStatus(ExpensesValidation.BILL_IS_DEMAND);
                expenseBillMapper.updateByPrimaryKeySelective(expenseBill);
            }
        }
        return StatusCode.SUCCESS;
    }


}

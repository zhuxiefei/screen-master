package com.betel.estatemgmt.business.web.expenses.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.userapp.pay.model.PayModel;
import com.betel.estatemgmt.business.userapp.pay.service.AppPayService;
import com.betel.estatemgmt.business.web.expenses.code.ExpensesCode;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.expenses.constant.ExpensesValidation;
import com.betel.estatemgmt.business.web.expenses.model.*;
import com.betel.estatemgmt.business.web.expenses.service.ExpensesService;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.remind.constant.RemindDataValidation;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseFlowMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemBuildingRelaMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemMapper;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.expense.ExpenseItemBuildingRela;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseOwner;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.shiro.ActiveUser;
import com.betel.estatemgmt.system.quartz.QuartzManager;
import com.betel.estatemgmt.system.quartz.model.JobModel;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 费用查询接口实现类
 * </p>
 * ClassName: ExpensesServiceImpl <br/>
 * Author:zhangjain <br/>
 * Date: 2017/9/18  <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class ExpensesServiceImpl implements ExpensesService {
    private static final Logger LOG = LoggerFactory.getLogger(ExpensesServiceImpl.class);
    @Autowired
    private ExpenseBillMapper expenseBillMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private BuildingUnitMapper buildingUnitMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private ExpenseFlowMapper expenseFlowMapper;
    //    @Autowired
//    private UserAccountMapper accountMapper;
    @Autowired
    private ExpenseItemMapper itemMapper;
    @Autowired
    private ExpenseItemBuildingRelaMapper relaMapper;
    @Autowired
    private HouseParkingSpaceMapper spaceMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private HouseOwnerMapper houseOwnerMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<Expenses> findAllExpenses(Paging<Expenses> pager, ExpensesReq expensesReq) {
        List<Expenses> expensesList = expenseBillMapper.findAllExpenses(pager.getRowBounds(), expensesReq);
        //滞纳金再没有滞纳金和欠缴费时默认为空字符串
        for (int i = 0; i < expensesList.size(); i++) {
            String overduefinePrice = expensesList.get(i).getOverduefinePrice();
            if ("".equals(overduefinePrice) || null == overduefinePrice) {
                expensesList.get(i).setOverduefinePrice("");
            }
            //户主
            if (StringUtil.isBlank(expensesList.get(i).getHouseMaster())) {
                expensesList.get(i).setHouseMaster("");
            }
            //手机号
            if (StringUtil.isBlank(expensesList.get(i).getPhone())) {
                expensesList.get(i).setPhone("");
            }
            if (!StringUtil.isBlank(expensesList.get(i).getHouseInfo())) {
                expensesList.get(i).setHouseInfo(makeHouseNo(expensesList.get(i).getHouseInfo()));
            }
            //服务器时间
            expensesList.get(i).setServerTime(new Date());
        }
        return expensesList;
    }

    @Override
    public int sendReminderMessage(ExpensesReq expensesReq, HttpServletRequest request) throws Exception {
        //查询数据库获得催缴信息:1.房屋信息，户主，手机号
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        List<Expenses> expensesList = expenseBillMapper.findExpensesByBillNos(billNos);
        //短信发送和系统通知
        pushReminderMessage(expensesList, 1,request);
        //增加催缴次数
        if (expensesList != null && expensesList.size() > 0) {
            for (Expenses e :
                    expensesList) {
                ExpenseBill bill = new ExpenseBill();
                bill.setBillNo(e.getBillNo());
                bill.setUrgeCount(e.getUrgeCount() + 1);
                bill.setLastUrgeTime(new Date(System.currentTimeMillis()));
                expenseBillMapper.updateByPrimaryKeySelective(bill);
            }
        }
        return expensesList.size();
    }

    private List<ExpenseBill> findExpenseBill(String[] billNos) {
        return expenseBillMapper.findExpenseBillByArraybillNo(billNos);
    }

    @Override
    public String checkExpenseBillisOver(ExpensesReq expensesReq) {
        String code = "";
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        List<ExpenseBill> expenseBillList = expenseBillMapper.findExpenseBillByArraybillNo(billNos);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < expenseBillList.size(); i++) {
            String flowNo = expenseBillList.get(i).getFlowNo();
            set.add(flowNo);
            //如果账单的流水号为空，说明过期
            if (StringUtil.isBlank(flowNo)) {
                code = ExpensesCode.BILL_EXPIRED;//过期
                return code;
            }
        }
        if (expenseBillList.size() != 1 && set.size() != 1) {
            code = ExpensesCode.BILL_STATUS_NOT_UNIQUE;
            return code;
        }
        return code;
    }

    private int updateBill(ExpensesReq expensesReq, HttpServletRequest request) throws Exception {
        PayBillReq payBillReq = new PayBillReq();
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        payBillReq.setBillNos(billNos);
        payBillReq.setChargeWay(expensesReq.getChargeWays());
        int flag = expenseBillMapper.updateBillFlow(payBillReq);
        if (LOG.isDebugEnabled()) {
            LOG.debug("------------------支付成功，更新缴费流水表，设置缴费状态为3（已支付）--------------------" + flag);
        }
        List<Expenses> expensesList = expenseBillMapper.findPayDetail(billNos);
        //设置滞纳金
        countOverdueFinePric(expensesList);
        int flag2 = expenseBillMapper.updateBillStatus(payBillReq);
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------------------支付成功，更新账单的状态为2（已支付）---------------------------" + flag2);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------------------支付成功，发送短信以及系统通知---------------------------");
        }
        List<Expenses> expensesList1 = expenseBillMapper.findPayDetail(billNos);
        //查询数据库获得催缴信息:1.房屋信息，户主，手机号
        pushReminderMessage(expensesList1, 2,request);
        return flag2;
    }

    @Override
    public String

    checkExpenseBillisHasBeenBoundStreamNumber(ExpensesReq expensesReq) {
        String code = "";
        PayBillReq payBillReq = new PayBillReq();
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        payBillReq.setBillNos(billNos);
        List<ExpenseBill> expenseBillList = expenseBillMapper.findExpenseBillAndFlowNoIsExist(payBillReq);
        for (int i = 0; i < expenseBillList.size(); i++) {
            String flowNo = expenseBillList.get(i).getFlowNo();
            Integer billStatus = expenseBillList.get(i).getBillStatus();
            //如果账单的流水号为空，说明过期
            if (!StringUtil.isBlank(flowNo) && 1 == billStatus) {
                code = ExpensesCode.BILL_LOCKING;//已被锁定
                return code;
            } else if (!StringUtil.isBlank(flowNo) && 2 == billStatus) {
                code = ExpensesCode.CHOOSE_BILL_HAS_ALL_PAY;
                return code;
            }
        }
        return code;
    }

    @Override
    public int updateFlowOfBill(PayBillReq payBillReq) {
        return expenseBillMapper.insertBillFlowNoByBillNos(payBillReq);
    }

    @Override
    public int insertFlowInfo(ExpenseFlow expenseFlow) {
        return expenseFlowMapper.insertSelective(expenseFlow);
    }

    @Autowired
    private AppPayService appPayService;

    @Override
    public Response payWebUnderLine(ExpensesReq expensesReq, Response response) {
        PayModel payModel = new PayModel();
        payModel.setBillNo(expensesReq.getBillNos());
        payModel.setPayType(0);
        try {
            appPayService.pay(payModel, response);
        } catch (Exception e) {
            LOG.error("web端支付一次异常");
        }
        return response;
    }

    private static Object o = new Object();

    @Override
    public Response payUnderWebLine(ExpensesReq expensesReq, Response response, Integer actionType, HttpServletRequest request) throws Exception {
        if (1 == actionType) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------------web端支付生成订单-------------------开始----------------------------");
            }
            PayModel payModel = new PayModel();
            payModel.setBillNo(expensesReq.getBillNos());
            payModel.setPayType(0);
            synchronized (o) {
                appPayService.pay(payModel, response);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------------web端支付生成订单-------------------结束---------------------------");
            }
            //这里报错，事务自动回滚，清除脏数据
        } else if (ExpenseStaticStatus.ACTION_VALUE == actionType) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------------web端支付-------------------开始---------------------------");
            }
            synchronized (o) {
                int flag = updateBill(expensesReq,request);
                Map<String, Integer> result = new HashMap<>();
                result.put("count", flag);
                response.setData(result);
            }
            //这里报错，抛出异常，事务自动回滚，清除脏数据
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------------web端支付-------------------结束---------------------------");
            }
        }
        //这里报错，全部回滚
        return response;
    }

    @Override
    public void payWebCourse(String billNos, Response response) throws ParseException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------生成订单----------随机生成缴费流水号---------开始-----------------------：");
        }
        String radomFlow = RandomId.getLocalTrmSeqNum(ExpensesCode.PROPERTY_FEE);  //生成订单号
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----------生成订单----------随机生成缴费流水号---------结束------------------------流水号为：" + radomFlow);
        }
        if (StringUtil.isBlank(radomFlow)) {
            response.setCode(ExpensesCode.CREATE_FLOW_FAILURE);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------生成订单--------------随机生成缴费流水号，插入到账单表里---------开始----------------：");
            }
            PayBillReq payBillReq = new PayBillReq();
            payBillReq.setFlowNo(radomFlow);
            payBillReq.setBillNos(Tool.getIdArrOfStringType(billNos));
            int flag = updateFlowOfBill(payBillReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------生成订单-----------随机生成缴费流水号，插入到账单表里---------结束------------更新账单表数量：" + flag);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------生成订单------查询数据库获得缴费订单详情对象---------开始---------------------------");
            }
            ExpensesReq expensesReq = new ExpensesReq();
            expensesReq.setBillNos(billNos);
            Expenses expenses = findPayDetail(expensesReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------生成订单-------查询数据库获得缴费订单详情对象---------结束-----------------------expenses----" + expenses);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------生成订单---------目前订单处于绑定缴费流水状态，其他人不得支付------开始-----------");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------生成订单----------------获得缴费流水对象--------------------开始-----------");
            }
            ExpenseFlow expenseFlow = new ExpenseFlow();//组合流水表对象，插入数据库
            expenseFlow.setFlowNo(radomFlow);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------生成订单----------------缴费流水号id-------radomFlow-----------" + radomFlow);
            }
            expenseFlow.setTotalAmount(BigDecimal.valueOf(Double.valueOf(expenses.getTotalAmount())));
            if (BigDecimal.valueOf(Double.valueOf(expenses.getTotalAmount())) == null) {
                expenseFlow.setTotalAmount(BigDecimal.valueOf(0));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------生成订单---------------缴费流总金额-------totalAmount-----------" + expenses.getTotalAmount());
            }
            expenseFlow.setFlowStatus(2);
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------生成订单---------------缴费状态-------FlowStatus：2----待支付-------");
            }

            Subject subject = SecurityUtils.getSubject();
            //jianz.z-------------------------------2018-1-21
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------生成订单---------------鉴权获取登录人信息-------------" + activeUser);
            }
            if (null == activeUser) {
                activeUser = new ActiveUser();
                activeUser.setUserId("1");
            }
            expenseFlow.setBillPayer(activeUser.getUserId());
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------生成订单---------------缴费管理员id-----------------" + activeUser.getUserId());
            }
            expenseFlow.setCreateTime(new Date());
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------生成订单--------------为了数据库不报错，支付方式标识此时设置为：0，缴费总金额如果为空，也是0,其他字段同理-----------------");
            }
            expenseFlow.setChargeWay(0);

            if (LOG.isDebugEnabled()) {
                LOG.debug("------生成订单---------获得缴费流水对象------expenseFlow-----------" + expenseFlow);
            }
            expenseFlow.setIsOnline(ExpensesValidation.NOT_ON_LINE);
            expenseFlow.setPayTime(new Date());
            int flag2 = insertFlowInfo(expenseFlow);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------生成订单---------------------获得缴费流水对象------------------结束------------------插入缴费流水表对象的个数：" + flag2);
            }
            //
            if (LOG.isDebugEnabled()) {
                LOG.debug("----定时任务---expenseFlow----------" + radomFlow);
            }
            JobModel jobModel = new JobModel();
            jobModel.setJobName(radomFlow);
            Map map = new HashMap<>();
            map.put("flowNo", radomFlow);
            jobModel.setDataMap(map);
            try {
                QuartzManager.addJob(jobModel);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            response.setData(expenses);
        }
    }

    @Override
    public List<Expenses> findExpensesByBillNos(String[] billNos) {
        List<Expenses> expensesList = expenseBillMapper.findExpensesByBillNos(billNos);
        return expensesList;
    }

    @Override
    public List<ExpenseItem> findByItemType(Integer itemType) {
        return itemMapper.findByItemType(itemType);
    }

    @Override
    public ExpenseItemBuildingRela findByItemIdAndBuildingId(Long itemId, Long buildingId) {
        return relaMapper.findByItemIdAndBuildingId(itemId, buildingId);
    }

    @Override
    public List<FindItemCycleResp> findItemCycle(House house, List<ExpenseItem> items) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("============web findItemCycle start==============house=" + house + ",List<ExpenseItem>=" + items);
        }
        List<FindItemCycleResp> respList = new ArrayList<>();
        Integer buyCounts = spaceMapper.findCountsByHouseIdAndType(house.getHouseId(), HouseDataValidation.BUY_PARKING_SPACE);
        Integer rentCounts = spaceMapper.findCountsByHouseIdAndType(house.getHouseId(), HouseDataValidation.RENT_PARKING_SPACE);
        if (items != null && items.size() > 0) {
            for (ExpenseItem item :
                    items) {
                //物业费
                if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE) == item.getItemType()) {
                    respList.add(countCycle(item));
                }
                //租赁停车费
                if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR) == item.getItemType() && rentCounts != 0) {
                    respList.add(countCycle(item));
                }
                //购买停车费
                if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE) == item.getItemType() && buyCounts != 0) {
                    respList.add(countCycle(item));
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("============web findItemCycle end==============List<FindItemCycleResp>=" + respList);
        }
        return respList;
    }

    /**
     * 计算周期
     *
     * @param item
     * @return
     */
    private static FindItemCycleResp countCycle(ExpenseItem item) {
        FindItemCycleResp resp = new FindItemCycleResp();
        resp.setItemId(item.getItemId());
        Integer cycle = item.getItemCycle();
        Integer multiple = ExpensesValidation.MAX_MONTHS / cycle;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= multiple; i++) {
            list.add(cycle * i);
        }
        resp.setMonthList(list);
        resp.setItemType(item.getItemType());
        return resp;
    }

    @Override
    public ExpenseItem findByItemId(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public Double countTotalAmount(Double floorArea, BigDecimal itemPrice, Integer months, Integer itemType, Integer buyCounts, Integer rentCounts) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("============web countTotalAmount end==============floorArea=" + floorArea + ",itemPrice=" + itemPrice
                    + ",months=" + months + ",itemType=" + itemType + ",buyCounts=" + buyCounts + ",rentCounts=" + rentCounts);
        }
        Double total = 0.0;
        //判断是物业费还是停车费
        if (itemType == Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE)) {
            total = itemPrice.doubleValue() * floorArea * months;
        } else if (itemType == Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE)) {
            total = itemPrice.doubleValue() * months * buyCounts;
        } else if (itemType == Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR)) {
            total = itemPrice.doubleValue() * months * rentCounts;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("============web countTotalAmount end==============total=" + total);
        }
        return total;
    }

    @Override
    public List<String> prePay(List<ExpenseItem> items, String[] months,
                               House house, Integer buyCounts,
                               Integer rentCounts, Integer flag,HttpServletRequest request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("================web prepay start=================items=" + items + ",months=" + months + ",house=" + house
                    + ",buyCounts=" + buyCounts + ",rentCounts=" + rentCounts);
        }
        //创建返回主键列表
        List<String> list = new ArrayList<>();
        synchronized (this) {
            for (int i = 0; i < items.size(); i++) {
                Long itemId = items.get(i).getItemId();
                //判断生成几次账单
                Integer itemCycle = items.get(i).getItemCycle();
                Integer times = Integer.parseInt(months[i]) / itemCycle;
                //根据房屋和收费项查询最新的账单
                ExpenseBill newestBill = expenseBillMapper.findNewestByHouseIdAndItemId(house.getHouseId(), itemId);
                if (newestBill != null) {
                    //若账单不为空，则根据账单的结束时间继续生成账单
                    Date endTime = newestBill.getEndTime();
                    //生成账单
                    List<String> list1 = createBills(times, items.get(i), house,
                            getAfterDay(endTime), buyCounts, rentCounts, flag,request);
                    if (list1 != null && list1.size() > 0) {
                        for (String a :
                                list1) {
                            list.add(a);
                        }
                    }
                } else {
                    //若账单为空，表示第一次生成账单
                    //根据当前时间和开始计费时间判断预缴账单的开始时间
                    Integer cycle = items.get(i).getItemCycle();
                    Date time = items.get(i).getStartTime();
                    Date startTime;
                    Integer monthBetween = getMonthDiff(new Date(), time);
                    if (monthBetween < 0) {
                        startTime = time;
                    } else {
                        startTime = currentCycleStartTime(time, (monthBetween / cycle + 1) * cycle);
                    }
                    List<String> list2 = createBills(times, items.get(i), house, startTime, buyCounts, rentCounts, flag,request);
                    if (list2 != null && list2.size() > 0) {
                        for (String a :
                                list2) {
                            list.add(a);
                        }
                    }
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("================web prepay end=================payList=" + list);
        }
        return list;
    }

    /**
     * <p>
     * 获取两个日期相差的月数
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/30 11:41
     *
     * @param d1 较大的日期
     * @param d2 较小的日期
     * @return 如果d1>d2返回 月数差 否则返回-1
     */
    private int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return -1;
        }
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    @Override
    public Integer findSpacesByHouseIdAndType(String houseId, Integer spaceType) {
        return spaceMapper.findCountsByHouseIdAndType(houseId, spaceType);
    }

    @Override
    public List<PrePayPageResp> findByBillNos(String[] billNos, Paging<PrePayPageResp> pager) {
        List<PrePayPageResp> list = expenseBillMapper.findAllByBillNos(billNos, pager.getRowBounds());
        return list;
    }

    @Override
    public List<ExpenseItemBuildingRela> findRelasByItemId(Long itemId) {
        return relaMapper.findByItemId(itemId);
    }

    @Override
    public ExpenseBill findByBilNo(String billNo) {
        return expenseBillMapper.selectByPrimaryKey(billNo);
    }

    @Override
    public void updateDemandStatus(ExpenseBill expenseBill) {
        expenseBillMapper.updateByPrimaryKeySelective(expenseBill);
    }

    /**
     * 批量生成账单
     *
     * @param times      生成次数
     * @param item       收费项
     * @param house      房屋
     * @param buyCounts  购买车位数
     * @param rentCounts 租赁车位数
     * @param startTime  第一次开始生成账单的时间
     * @param flag       1代表app预缴  2代表web预缴
     * @return 批量插入返回主键
     */
    private List<String> createBills(Integer times, ExpenseItem item,
                                     House house, Date startTime, Integer buyCounts,
                                     Integer rentCounts, Integer flag,HttpServletRequest request) throws Exception {
        //创建批量插入对象
        List<ExpenseBill> list = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            //获取当前账单周期的起始时间和结束时间
            Date cycleStartTime = currentCycleStartTime(startTime, i * item.getItemCycle());
            Date cycleEndTime = currentCycleEndTime(currentCycleStartTime(startTime, (i + 1) * item.getItemCycle()));
            //定义金额
            String billAmount = null;
            if (item.getItemType().equals(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE))) {
                //物业费=每平米每个月物业费价格*房屋建筑面积*计费周期
                billAmount = BillAmountUtil.countPropertyAmount(item.getItemPrice().doubleValue(), house.getFloorArea(), item.getItemCycle());
            } else if (item.getItemType().equals(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE))) {
                //购买停车费=每平米每个月停车费价格*计费周期*车位数
                billAmount = BillAmountUtil.countSpaceAmount(item.getItemPrice().doubleValue(), item.getItemCycle(), buyCounts);
            } else if (item.getItemType().equals(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR))) {
                //租赁停车费=每平米每个月停车费价格*计费周期*车位数
                billAmount = BillAmountUtil.countSpaceAmount(item.getItemPrice().doubleValue(), item.getItemCycle(), rentCounts);
            }
            //创建账单对象
            ExpenseBill bill = new ExpenseBill();
            bill.setBillNo(getLocalTrmSeqNum("ZD"));
            bill.setItemType(item.getItemType());
            bill.setHouseId(house.getHouseId());
            bill.setCreateTime(new Date(System.currentTimeMillis()));
            bill.setItemId(item.getItemId());
            bill.setBillStatus(RemindDataValidation.BILL_NOTPAY_STATUS);
            bill.setIsPrint(0);
            bill.setIsDelete(1);
            bill.setUrgeCount(0);
            bill.setDemandBillStatus(1);
            HouseOwner houseOwner = houseOwnerMapper.selectByHouseId(house.getHouseId());
            if (houseOwner != null) {
                if (!StringUtil.isBlank(houseOwner.getUserId())) {
                    //jianz.z 2018-1-21
                    UserAccount userAccount=userService.findByUserId(houseOwner.getUserId(),request);
                    if (null!=userAccount){
                        bill.setHouseOwnerPhone(userAccount.getAcctName());
                    }
                } else {
                    bill.setHouseOwnerPhone(houseOwner.getPhoneNum());
                }
                bill.setHouseOwnerName(houseOwner.getRealName());
            }
            bill.setBillAmount(new BigDecimal(billAmount));
            bill.setStartTime(DateUtil.toDate(DateUtil.toString(cycleStartTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
            bill.setEndTime(DateUtil.toDate(DateUtil.toString(cycleEndTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
            bill.setDeadLine(DateUtil.toDate(DateUtil.toString(countDeadLine(cycleStartTime, item.getChargeCycle()), "yyyy-MM-dd"), "yyyy-MM-dd"));
            //判断该账单是否已经生成过
            List<ExpenseBill> bills = expenseBillMapper.findByExpenseBill(bill);
            if (bill.getBillAmount().doubleValue() != 0 && (bills == null || (bills != null && bills.size() == 0))) {
                list.add(bill);
            }
            if (flag == 2 && !StringUtil.isBlank(billAmount)) {
                //计算计费周期
                String billCycle = DateUtil.toString(bill.getStartTime(), "yyyy-MM-dd") + "~" + DateUtil.toString(bill.getEndTime(), "yyyy-MM-dd");
                String typeName = null;
                if (item.getItemType().equals(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE))) {
                    typeName = "物业费";
                } else if (item.getItemType().equals(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE))) {
                    typeName = "购买停车费";
                } else if (item.getItemType().equals(Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR))) {
                    typeName = "租赁停车费";
                }
                //将费用转换为普通计数法
                BigDecimal a = new BigDecimal(billAmount);
                billAmount = a.toPlainString();
                String content = "尊敬的" + makeHouseNo(house.getHouseId()) + "业主" + bill.getHouseOwnerName()
                        + "\n您" + billCycle + "的" + typeName + "共计人民币" + billAmount + "元，请尽快缴费";
                String[] params = new String[]{makeHouseNo(house.getHouseId()),bill.getHouseOwnerName(),
                        billCycle,typeName,billAmount};
                //发短信和通知
                //判断是否存在户主账号
                if (houseOwner != null && !StringUtil.isBlank(houseOwner.getUserId())) {
                    //发通知
                    //创建系统通知对象，将通知存到数据库
                    Notice notice = new Notice();
                    notice.setNoticeStatus(1);
                    notice.setNoticeType(Integer.parseInt(RemindDataValidation.PAYMENT_NOTICE_SENDNO));
                    notice.setCreateTime(new Date(System.currentTimeMillis()));
                    notice.setNoticeUserId(houseOwner.getUserId());
                    notice.setNoticeContent(content);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========before addNotice========notice=" + notice);
                    }
                    noticeMapper.insertSelective(notice);
                    //返回主键
                    Long noticeId = notice.getNoticeId();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========after addNotice========notice=" + notice);
                    }
                    SendMessage send = new SendMessage();
                    send.setSendId(noticeId.toString());
                    send.setSendTitle("缴费通知");
                    send.setSendNo(RemindDataValidation.PAYMENT_NOTICE_SENDNO);
                    send.setSendType(RemindDataValidation.SENDTYPE);
                    send.setSendContent(content);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========SendMessage========" + send);
                    }
                    //转json
                    String pushInfo = GsonUtil.object2Gson(send);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========SendMessage to json========" + pushInfo);
                    }
                    //查询用户账号 jian.z 2018-1-21
                    UserAccount account = userService.findByUserId(houseOwner.getUserId(),request);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========findAccountByUserId========" + account);
                    }
                    if (account != null) {
                        //推送
                        MsgPushUtils.push(account.getAcctName(), pushInfo);
                        //发短信
                        String[] phones = new String[]{account.getAcctName()};
                        BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE,phones,params);
                    }
                }
                //若户主账号不存在，则给户主预留手机号发短信
                if (houseOwner != null && StringUtil.isBlank(houseOwner.getUserId())) {
                    //发短信
                    String[] phones = new String[]{houseOwner.getPhoneNum()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE,phones,params);
                }
            }
        }
        List<String> billNos = new ArrayList<>();
        //批量插入账单
        if (list.size() > 0) {
            expenseBillMapper.insertList(list);
            for (ExpenseBill bill :
                    list) {
                billNos.add(bill.getBillNo());
            }
        }
        return billNos;
    }

    //生成房屋信息
    public String makeHouseNo(String houseId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RemindServiceImpl makeHouseNo start=========houseId=" + houseId);
        }
        House house = houseMapper.selectByPrimaryKey(houseId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========makeHouseNo findHouseById=========house=" + house);
        }
        StringBuffer houseNo = new StringBuffer();
        if (house.getBuildingId() != null) {
            Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
            if (building != null) {
                houseNo.append(building.getBuildingName());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========makeHouseNo houseNoAppendBuildingId=========houseNo=" + houseNo);
        }
        if (house.getUnitId() != null) {
            BuildingUnit unit = buildingUnitMapper.selectByPrimaryKey(house.getUnitId());
            if (unit != null) {
                houseNo.append(unit.getUnitName());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========makeHouseNo houseNoAppendUnitId=========houseNo=" + houseNo);
        }
        houseNo.append(house.getHouseNum());
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RemindServiceImpl makeHouseNo end=========houseNo" + houseNo);
        }
        return houseNo.toString();
    }

    /**
     * 计算生成账单的到期日
     *
     * @param currentCycleStartTime 当前账单周期的起始时间
     * @param chargeCycle           缴费周期
     * @return
     */
    private Date countDeadLine(Date currentCycleStartTime, Integer chargeCycle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentCycleStartTime);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + chargeCycle - 1);
        return calendar.getTime();
    }

    /**
     * 获取当前周期的开始时间
     *
     * @param startTime 第一次生成账单的起始时间
     * @param itemCycle 周期月
     * @return
     */
    private Date currentCycleStartTime(Date startTime, Integer itemCycle) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======currentCycleStartTime start========itemCycle=" + itemCycle + ",startTime=" + startTime);
        }
        //获取当前周期的第一天
        Calendar cl = Calendar.getInstance();
        cl.setTime(startTime);
        cl.add(Calendar.MONTH, itemCycle);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======currentCycleStartTime end========date=" + cl.getTime());
        }
        return cl.getTime();
    }

    /**
     * 获取当前周期的截止时间
     *
     * @param nextStartTime 下个周期生成账单的起始时间
     * @return
     */
    private Date currentCycleEndTime(Date nextStartTime) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======currentCycleEndTime start========");
        }
        Calendar firstDay = Calendar.getInstance();
        firstDay.setTime(nextStartTime);
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        firstDay.set(Calendar.HOUR_OF_DAY, 23);//时
        firstDay.set(Calendar.MINUTE, 59);//分
        firstDay.set(Calendar.SECOND, 59);//秒
        firstDay.set(Calendar.MILLISECOND, 0);//毫秒
        firstDay.add(Calendar.DATE, -1);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======currentCycleEndTime end========date=" + firstDay.getTime());
        }
        return firstDay.getTime();
    }

    /**
     * 获取后一天的日期
     *
     * @param date
     * @return
     */
    public static Date getAfterDay(Date date) {
        //使用set方法直接进行设置
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int day = c1.get(Calendar.DATE);
        c1.set(Calendar.DATE, day + 1);
        return c1.getTime();
    }

    /**
     * 获取当前时间的下月一号
     *
     * @return
     */
    public static Date getNextMonthTime() {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY, 0);//时
        c1.set(Calendar.MINUTE, 0);//分
        c1.set(Calendar.SECOND, 0);//秒
        c1.set(Calendar.MILLISECOND, 0);//毫秒
        c1.add(Calendar.MONTH, 1);
        return c1.getTime();
    }

    /**
     * <p>
     * 标识+YYYYMMDDHHMMSS+6位自增长码(20位) 
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/11 9:37
     *
     * @param flag 标识
     * @return String 自增序列号
     */
    private Integer sequence = RemindDataValidation.ADDNO_SEQUENCE;
    private Integer length = RemindDataValidation.ADDNO_LENGTH;

    private synchronized String getLocalTrmSeqNum(String flag) {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String s = Integer.toString(sequence);
        return flag + dateTime + IdUtil.addLeftZero(s, length);
    }

    /**
     * 推送通知
     *
     * @param expensesList 参数
     * @param action       1 短信催缴   2  缴费成功短信
     */
    private void pushReminderMessage(List<Expenses> expensesList, int action, HttpServletRequest request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----------------web/expenses/v1/pushReminderMessage------start-------expensesList的数量-------", expensesList.size());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /**
         * 获得系统通知对象集合
         */
        List<Notice> notices = new ArrayList<>();
        List<String> systemInfo = new ArrayList<>();
        for (int i = 0; i < expensesList.size(); i++) {
            if (!StringUtil.isBlank(expensesList.get(i).getPhone())
                    && userService.findByAccountName(expensesList.get(i).getPhone(),request) != null) {
                //确定项目类型 jian.z 2018-1-21
                String itemType = "";
                if (ExpenseStaticStatus.ITEM_TYPE_ONE.equals(expensesList.get(i).getItemType())) {
                    itemType = "物业费";
                }
                if (ExpenseStaticStatus.ITEM_TYPE_TWO.equals(expensesList.get(i).getItemType())) {
                    itemType = "公摊水电费";
                }
                if (ExpenseStaticStatus.ITEM_TYPE_THREE.equals(expensesList.get(i).getItemType())) {
                    itemType = "购买停车费";
                }
                if (ExpenseStaticStatus.ITEM_TYPE_FOUR.equals(expensesList.get(i).getItemType())) {
                    itemType = "租赁停车费";
                }
                if (ExpenseStaticStatus.ITEM_TYPE_FIVE.equals(expensesList.get(i).getItemType())) {
                    itemType = "能耗费";
                }
                String content = "";
                String[] params = null;
                String[] phones = new String[]{expensesList.get(i).getPhone().toString()};
                if (1 == action) {
                    if (!StringUtil.isBlank(expensesList.get(i).getHouseInfo())) {
                        content = "尊敬的" + expensesList.get(i).getHouseInfo() + "业主" + expensesList.get(i).getHouseMaster()
                                + "\n截止" + DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd")
                                + "，您欠缴的" + itemType + "共计人民币" + expensesList.get(i).getTotalAmount() + "元（未包含滞纳金），请尽快缴费，感谢您的支持";
                        params = new String[]{expensesList.get(i).getHouseInfo(),expensesList.get(i).getHouseMaster(),
                                DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd"),itemType,
                                expensesList.get(i).getTotalAmount()};
                        BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE_TWO,phones,params);
                    } else {
                        content = "尊敬的业主" + expensesList.get(i).getHouseMaster()
                                + "\n截止" + DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd")
                                + "，您欠缴的" + itemType + "共计人民币" + expensesList.get(i).getTotalAmount() + "元（未包含滞纳金），请尽快缴费，感谢您的支持";
                        params = new String[]{"",expensesList.get(i).getHouseMaster(),
                                DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd"),itemType,
                                expensesList.get(i).getTotalAmount()};
                        BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE_TWO,phones,params);
                    }
                } else if (2 == action) {
                    content = "尊敬的业主" + expensesList.get(i).getHouseMaster() + "，您于" + sdf.format(new Date()) + "缴纳"
                            + itemType + ",共合计人民币" + expensesList.get(i).getTotalAmount() + "元。感谢您的配合。";
                    params = new String[]{expensesList.get(i).getHouseMaster(),sdf.format(new Date()),
                            itemType,expensesList.get(i).getTotalAmount()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_SUCCESS_TEMPLATE,phones,params);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------------------------------------短信催缴发送-------------");
                }
                Notice notice = new Notice();
                //jians.z 2018-1-21
                notice.setNoticeUserId(userService.findByAccountName(expensesList.get(i).getPhone(),request).getUserId());
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeContent(content);
                if (1 == action) {
                    notice.setNoticeType(Integer.parseInt(ExpensesCode.EXPENSES_SENDNO));
                } else if (2 == action) {
                    notice.setNoticeType(Integer.parseInt(ExpensesCode.EXPENSES_SUCCESS_SENDNO));
                }
                notice.setNoticeStatus(1);
                notices.add(notice);
                systemInfo.add(content);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========web/expenses/v1/=====inert notice table count===========", notices.size());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========web/expenses/v1/=====inert notice table count===========", notices.size());
        }
        //不等于空时插入系统通知表，即不需要通知用户
        if (notices.size() > 0) {
            int flag = noticeMapper.insertAuditNotice(notices);
            for (int i = 0; i < notices.size(); i++) {
                //返回主键
                Long noticeId = notices.get(i).getNoticeId();
                //建立推送模型
                SendMessage sendMessage = new SendMessage();
                sendMessage.setSendType(ExpensesCode.SENDTYPE);
                if (1 == action) {
                    sendMessage.setSendNo(ExpensesCode.EXPENSES_SENDNO);
                    sendMessage.setSendTitle("催缴提醒");
                } else if (2 == action) {
                    sendMessage.setSendNo(ExpensesCode.EXPENSES_SUCCESS_SENDNO);
                    sendMessage.setSendTitle("缴费成功通知");
                }
                sendMessage.setSendContent(systemInfo.get(i));
                sendMessage.setSendId(noticeId.toString());
                if (LOG.isDebugEnabled()) {
                    {
                        LOG.debug("-------系统通知----sendMessage：" + sendMessage);
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("-------系统通知----noticeId：" + noticeId);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------------------------------------系统通知，催缴发送-------------");
                }
                //转json
                String pushInfo = GsonUtil.object2Gson(sendMessage);
                //再推送消息给用户
                if (null != expensesList.get(i).getPhone()) {
                    //再推送消息给用户
                    MsgPushUtils.push(expensesList.get(i).getPhone(), pushInfo);
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========web/expenses/v1/pushReminderMessage============end=====");
        }
    }

    /**
     * 校验前端参数
     *
     * @param expensesReq
     * @return
     */
    @Override
    public String checkParamter(ExpensesReq expensesReq) {
        String code = "";
        //房号
        if (!StringUtil.isBlank(expensesReq.getHouseNum())) {
            if (!expensesReq.getHouseNum().trim().matches(ExpensesValidation.HOUSE_NUM_RULE)) {
                code = ExpensesCode.HOUSE_NUM_IS_NOT_LEGAL;
                return code;
            }
        }
        //楼宇id
        if (StringUtil.isBlank(expensesReq.getBuildingId())) {
        } else {
            //楼宇是否存在
            try {
                Long id = Long.valueOf(expensesReq.getBuildingId().trim());
                Building building = buildingMapper.selectByPrimaryKey(id);
                if (null == building) {
                    code = ExpensesCode.BUILDING_IS_NOT_EXIST;
                    return code;
                }
            } catch (Exception e) {
                code = ExpensesCode.BUILDING_IS_NOT_EXIST;
                return code;
            }

        }

        //单元id
        if (StringUtil.isBlank(expensesReq.getUnitId())) {
        } else {
            //单元是否存在
            try {
                Long id = Long.valueOf(expensesReq.getUnitId().trim());
                BuildingUnit buildingUnit = buildingUnitMapper.selectByPrimaryKey(id);
                if (null == buildingUnit) {
                    code = ExpensesCode.UNIT_IS_NOT_EXIST;
                    return code;
                }
            } catch (Exception e) {
                code = ExpensesCode.UNIT_IS_NOT_EXIST;
                return code;
            }

        }
        //户主姓名
        if (!StringUtil.isBlank(expensesReq.getHouseMaster())) {
            if (!expensesReq.getHouseMaster().trim().matches(ExpensesValidation.HOUSE_NAME_RULE)) {
                code = ExpensesCode.HOUSE_MASTER_IS_NOT_LEGAL;
                return code;
            }
        }
        //出账开始时间和结束时间规则和大小比较
        if (!StringUtil.isBlank(expensesReq.getExportBillStartTime())) {
            if (!expensesReq.getExportBillStartTime().trim().matches(ExpensesValidation.START_END_TIME_RULE)) {
                code = ExpensesCode.EXPORT_BILL_START_TIME_IS_NOT_LEGAL;
                return code;
            }
        }
        if (!StringUtil.isBlank(expensesReq.getExportBillEndTime())) {
            if (!expensesReq.getExportBillEndTime().trim().matches(ExpensesValidation.START_END_TIME_RULE)) {
                code = ExpensesCode.EXPORT_BILL_END_TIME_IS_NOT_LEGAL;
                return code;
            }
        }
        if (TimeUtils.compareDate(expensesReq.getExportBillStartTime(), expensesReq.getExportBillEndTime(), ExpenseStaticStatus.TIME_TYPE)) {
            code = ExpensesCode.EXPORT_BILL_START_TIME_IS_MORE_THAN_END_TIME;
            return code;
        }
        //项目类型
        if (!StringUtil.isBlank(expensesReq.getItemType())) {
            boolean flag1 = ExpenseStaticStatus.ITEM_TYPE_ONE.equals(expensesReq.getItemType().trim());
            boolean flag2 = ExpenseStaticStatus.ITEM_TYPE_TWO.equals(expensesReq.getItemType().trim());
            boolean flag3 = ExpenseStaticStatus.ITEM_TYPE_THREE.equals(expensesReq.getItemType().trim());
            boolean flag4 = ExpenseStaticStatus.ITEM_TYPE_FOUR.equals(expensesReq.getItemType().trim());
            boolean flag5 = ExpenseStaticStatus.ITEM_TYPE_FIVE.equals(expensesReq.getItemType().trim());
            if (flag1 == false && flag2 == false && flag3 == false && flag4 == false && flag5 == false) {
                code = ExpensesCode.ITEM_TYPE_IS_NOT_EXIST;
                return code;
            }
        }
        //账单状态
        if (!StringUtil.isBlank(expensesReq.getBillStatus())) {
            boolean flag1 = ExpenseStaticStatus.TYPE_ONE.equals(expensesReq.getBillStatus().trim());
            boolean flag2 = ExpenseStaticStatus.TYPE_TWO.equals(expensesReq.getBillStatus().trim());
            boolean flag3 = ExpenseStaticStatus.TYPE_THREE.equals(expensesReq.getBillStatus().trim());
            boolean flag4 = ExpenseStaticStatus.TYPE_FOUR.equals(expensesReq.getBillStatus().trim());
            if (flag1 == false && flag2 == false && flag3 == false && flag4 == false) {
                code = ExpensesCode.BILL_STATUS_IS_NOT_EXIST;
                return code;
            }
        }
        //add 收据状态
        if (!StringUtil.isBlank(expensesReq.getDemandBillStatus())) {
            boolean flag1 = ExpenseStaticStatus.DEMANDBILLSTATUS_ONE.equals(expensesReq.getDemandBillStatus().trim());
            boolean flag2 = ExpenseStaticStatus.DEMANDBILLSTATUS_TWO.equals(expensesReq.getDemandBillStatus().trim());
            boolean flag3 = ExpenseStaticStatus.DEMANDBILLSTATUS_THREE.equals(expensesReq.getDemandBillStatus().trim());
            if (flag1 == false && flag2 == false && flag3 == false) {
                code = ExpensesCode.DEMANDBILLSTATUS_RULE_ERROR;
                return code;
            }
        } else {

        }
        return code;
    }

    @Override
    public String testFindPayDetailParamter(ExpensesReq expensesReq) {
        String code = "";
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        List<Expenses> expensesList = expenseBillMapper.findPayDetail(billNos);
        Set<String> set = new HashSet<>();
        //是否是同一房屋
        for (int i = 0; i < expensesList.size(); i++) {
            String houseInfo = expensesList.get(i).getHouseInfo();
            set.add(houseInfo);
        }
        if (expensesList.size() == set.size() && expensesList.size() != 1) {
            code = ExpensesCode.CHOOSE_BILL_IS_NOT_THE_SAME_AS_BILL_UNDER_THE_SAME_HOUSE;
        }
        //是否全部欠缴费
        Set<String> set1 = new HashSet<>();
        for (int i = 0; i < expensesList.size(); i++) {
            String billStatus = expensesList.get(i).getBillStatus();
            set1.add(billStatus);
        }
        if (expensesList.size() == set1.size() && expensesList.size() != 1) {
            code = ExpensesCode.CHOOSE_BILL_HAS_PART_OF_PAYMENT_BILL;
        }
        return code;
    }

    @Override
    public Expenses findPayDetail(ExpensesReq expensesReq) throws ParseException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------------选择多个账单生成欠缴费单明细------------开始------------------------------");
        }
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        List<Expenses> expensesList = expenseBillMapper.findPayDetail(billNos);
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------------查询已缴费详情----------------expensesList-------------" + expensesList);
        }
        //总金额
        double totalMenory = 0.00;
        //滞纳金总额
        double totalOverdueFinePrice = 0.00;
        //组合前端对象
        Expenses expenses = new Expenses();
        //房屋信息
        expenses.setHouseInfo(expensesList.get(0).getHouseInfo());
        if (LOG.isDebugEnabled()) {
            LOG.debug("----选择账单：房屋信息--HouseInfo--" + expensesList.get(0).getHouseInfo());
        }
        //手机号
        expenses.setPhone(expensesList.get(0).getPhone());
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----选择账单：户主手机号-----" + expensesList.get(0).getPhone());
        }
        //户主
        expenses.setHouseMaster(expensesList.get(0).getHouseMaster());
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----选择账单：户主名称-------" + expensesList.get(0).getHouseMaster());
        }
        //查询最新的户主信息----xyx
//        HouseOwnerInfo houseOwnerInfo = expenseBillMapper.findNewOwnerByBillNo(billNos[0]);
//        if (houseOwnerInfo != null){
//            HouseAuth auth = authMapper.selectAuthByUserIdAndHouseId(houseOwnerInfo.getUserId(),houseOwnerInfo.getHouseId());
//            if (auth != null){
//                houseOwnerInfo.setOwnerName(auth.getApplicantName());
//            }
//            //手机号
//            expenses.setPhone(houseOwnerInfo.getPhone());
//            if (LOG.isDebugEnabled()){LOG.debug("-----选择账单：户主手机号-----"+expensesList.get(0).getPhone()); }
//            //户主
//            expenses.setHouseMaster(houseOwnerInfo.getOwnerName());
//            if (LOG.isDebugEnabled()){ LOG.debug("-----选择账单：户主名称-------"+expensesList.get(0).getHouseMaster()); }
//        }
        //组合小集合
        List<PayDetail> payDetailList = new ArrayList<>();
        for (int i = 0; i < expensesList.size(); i++) {
            //创建小集合对象
            PayDetail payDetail = new PayDetail();
            //费用类型
            String itemType = expensesList.get(i).getItemType();
            String itemName = "";
            if ("1".equals(itemType)) {
                itemName = "物业费";
            } else if ("2".equals(itemType)) {
                itemName = "公摊水电费费";
            }
            payDetail.setItemType(itemType);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------------------选择多个账单中第" + (i + 0) + "个账单的项目类型-------------" + itemName);
            }
            payDetail.setItemType(itemType);
            //应收开始时间
            payDetail.setBillingStartTime(expensesList.get(i).getBillingStartTime());
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------选择多个账单中第" + (i + 0) + "个账单的应收开始账单时间-----------" + expensesList.get(i).getBillingStartTime());
            }
            //应收结束时间
            payDetail.setBillingEndTime(expensesList.get(i).getBillingEndTime());
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------选择多个账单中第" + (i + 0) + "个账单的应收结束账单时间-----------" + expensesList.get(i).getBillingEndTime());
            }
            //应收账单
            payDetail.setBillAmount(expensesList.get(i).getBillAmount());
            //改为普通计数法
            BigDecimal a = new BigDecimal(payDetail.getBillAmount());
            payDetail.setBillAmount(a.toPlainString());
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------选择多个账单中第" + (i + 0) + "个账单的物业费--------------" + expensesList.get(i).getBillAmount());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------------------计算滞纳金----------------开始-----------------多个账单多个滞纳金------------");
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String deaLine = expensesList.get(i).getDeadLine();
                Date deadLine = sdf.parse(deaLine);
                //预期天数
                int num = TimeUtils.daysBetween(deadLine, new Date());
                if (num <= 0) {
                    num = 0;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--------------------账单到期时间：" + deaLine + ",今天缴费预期天数：" + num + "--------------------");
                }
                String znjl = expensesList.get(i).getOverdueFine();
                double doubleznjl = Double.valueOf(znjl).doubleValue();
                //滞纳金率
                double znjRate = doubleznjl / 100;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------------账单-----------------------滞纳金率为：" + znjRate);
                }
                /**
                 * 滞纳金=应收账款*逾期天数*滞纳金率
                 */
                double billAmount = 0.00;
                if (!StringUtil.isBlank(expensesList.get(i).getBillAmount())) {
                    //应收账款
                    billAmount = Double.valueOf(expensesList.get(i).getBillAmount());
                }
                //计算滞纳金
                double overdueFinePrice = 0.00;
                overdueFinePrice = billAmount * num * znjRate;
                BigDecimal b = new BigDecimal(overdueFinePrice);
                overdueFinePrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------计算滞纳金-----结束--------选择多个账单中--------某当前账单滞纳金为：" + overdueFinePrice);
                }
                expensesList.get(i).setOverduefinePrice(overdueFinePrice + "");
                if (LOG.isDebugEnabled()) {
                    LOG.debug("-------- 计算滞纳金，多个账单多个滞纳金---------------结束------------");
                }
                //设置滞纳金
                payDetail.setOverduefinePrice(overdueFinePrice + "");
                //改为普通计数法
                BigDecimal v = new BigDecimal(payDetail.getOverduefinePrice());
                payDetail.setOverduefinePrice(v.toPlainString());
                //计算滞纳金总额
                totalOverdueFinePrice = totalOverdueFinePrice + overdueFinePrice;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("------选择多个账单中当前当前中应收金额-----billAmount---" + billAmount);
                }
                //设置合计金额=滞纳金+应收账单
                payDetail.setTotalAmount((billAmount + overdueFinePrice + ""));
                //改为普通计数法
                BigDecimal h = new BigDecimal(String.format("%.2f", Double.parseDouble(payDetail.getTotalAmount())));
                payDetail.setTotalAmount(h.toPlainString());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("--------选择多个账单中当前当前中总金额（滞纳金+应收金额）：-------billAmount-------" + billAmount);
                }
                //全部合计。暂时前端计算
                totalMenory = totalMenory + Double.valueOf(payDetail.getTotalAmount());
                //存储小集合
                payDetailList.add(payDetail);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("----------选择多个账单中第" + (i + 1) + "条账单对象--------------payDetailList---------" + payDetailList);
                }
            } catch (Exception e) {
                LOG.error("------------数字转化出错---------------------", e);
            }
        }
        //组合大集合
        expenses.setPayDetails(payDetailList);
        //设置滞纳金的总金额
        expenses.setTotalOverdueFinePrice(totalOverdueFinePrice + "");
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------------------选择多个账单--------------滞纳金的总金额----------totalOverdueFinePrice---------------" + totalOverdueFinePrice);
        }
        //物业费+滞纳金的+公摊水电费总金额：注意注意金额
        BigDecimal b = new BigDecimal(totalMenory);
        totalMenory = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        expenses.setTotalAmount(totalMenory + "");
        if (LOG.isDebugEnabled()) {
            LOG.debug("-----选择多个账单------(物业费+滞纳金的+公摊水电费总金额)--------总金额----------totalMenory-------" + totalMenory);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("--------选择多个账单生成欠缴费单明细------------结束---------欠缴费明细对象---------------------" + expenses);
        }
        return expenses;
    }


    /**
     * 计算滞纳金
     *
     * @param expensesList
     * @return
     * @throws ParseException
     */
    private void countOverdueFinePric(List<Expenses> expensesList) throws ParseException {
        for (int i = 0; i < expensesList.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String deaLine = expensesList.get(i).getDeadLine();
            Date deadLine = sdf.parse(deaLine);
            //预期天数
            int num = TimeUtils.daysBetween(deadLine, new Date());
            //如果没有逾期默认为0
            if (num <= 0) {
                num = 0;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------------------账单到期时间：" + deaLine + ",今天缴费预期天数：" + num + "--------------------");
            }
            double doubleznjl = 0.00;
            if (!StringUtil.isBlank(expensesList.get(i).getOverdueFine())) {
                doubleznjl = Double.valueOf(expensesList.get(i).getOverdueFine()).doubleValue();
            }
            //滞纳金率
            double znjRate = doubleznjl / 100;
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------------------------账单-----------------------滞纳金率为：" + znjRate);
            }
            double overdueFinePrice = 0.00;
            //计算滞纳金=应收账单金额*逾期天数*滞纳金率
            if (!StringUtil.isBlank(expensesList.get(i).getBillAmount())) {
                overdueFinePrice = Double.valueOf(expensesList.get(i).getBillAmount()) * num * znjRate;
            }
            //插入滞纳金到账单表
            ExpenseBill eb = new ExpenseBill();
            eb.setBillNo(expensesList.get(i).getBillNo());
            eb.setOverdueFine(BigDecimal.valueOf(overdueFinePrice));
            int flag = expenseBillMapper.setOverdueFineOfupdateBill(eb);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------插入数据库滞纳金成功：" + flag + "条-------");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------计算滞纳金------结束------选择多个账单中--------某当前账单滞纳金为：" + overdueFinePrice);
            }
        }
    }

    /**
     * <p>
     * 计算前一天的日期
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/21 11:21
     *
     * @param date
     * @return date
     */
    public static Date getBeforeDay(Date date) {
        //使用set方法直接进行设置
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int day = c1.get(Calendar.DATE);
        c1.set(Calendar.DATE, day - 1);
        return c1.getTime();
    }

}

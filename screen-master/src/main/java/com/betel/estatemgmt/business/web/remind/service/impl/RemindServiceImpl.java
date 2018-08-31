package com.betel.estatemgmt.business.web.remind.service.impl;

import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.remind.constant.RemindDataValidation;
import com.betel.estatemgmt.business.web.remind.model.AddRemindDaysReq;
import com.betel.estatemgmt.business.web.remind.model.OverDue;
import com.betel.estatemgmt.business.web.remind.model.Payment;
import com.betel.estatemgmt.business.web.remind.model.RemindDays;
import com.betel.estatemgmt.business.web.remind.service.RemindService;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemBuildingRelaMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemMapper;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.property.PropertyConfigMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseOwner;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DATE;

/**
 * <p>
 * 设置提醒天数接口实现类
 * </p>
 * ClassName: RemindServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 17:41 <br/>
 * Version: 1.0 <br/>
 */
@Service("RemindService")
@Transactional
public class RemindServiceImpl implements RemindService {

    @Autowired
    private PropertyConfigMapper configMapper;

    @Autowired
    private ExpenseBillMapper expenseBillMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private ExpenseItemMapper itemMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BuildingUnitMapper unitMapper;

    @Autowired
    private ExpenseItemBuildingRelaMapper relaMapper;

    @Autowired
    private HouseParkingSpaceMapper spaceMapper;

    @Autowired
    private HouseOwnerMapper houseOwnerMapper;
    @Autowired
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(RemindServiceImpl.class);

    @Override
    public void addRemindDays(AddRemindDaysReq addRemindDaysReq) {
        configMapper.updateRemindDays(addRemindDaysReq);
    }

    @Override
    public List<RemindDays> findByConfName(String confName,String estateId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RemindServiceImpl findByConfName start========confName=" + confName);
        }
        String[] array = confName.split(",");
        List<RemindDays> list = configMapper.findByConfNames(array,estateId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========RemindServiceImpl findByConfName end========list=" + list);
        }
        return list;
    }

    @Override
    public void payMentRemind() throws Exception {
        //查询支付提醒天数
        List<RemindDays> remindDays = findByConfName(RemindDataValidation.PAYMENT_REMIND_DAYS,"1");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========remindDays=======" + remindDays);
        }
        //出账时间
        Date time = countTime(remindDays.get(0).getConfValue(), 0);
        time = DateUtil.toDate(DateUtil.toString(time, "yyyy-MM-dd"), "yyyy-MM-dd");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========createTime=======" + time);
        }
        //根据出账时间查询数据
        List<Payment> payments = expenseBillMapper.findByCreateTime(time);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=========payMentRemind========payments=" + payments);
        }
        if (payments != null && payments.size() > 0) {
            for (Payment payment :
                    payments) {
                if (!StringUtil.isBlank(payment.getUserId())) {
                    //将金额转换为普通计数法
                    String amount = String.format("%.2f", payment.getBillAmount());
                    BigDecimal a = new BigDecimal(amount);
                    String billAmount = a.toPlainString();
                    //发通知给户主
                    //创建系统通知对象，将通知存到数据库
                    Notice notice = new Notice();
                    notice.setNoticeStatus(1);
                    notice.setNoticeType(Integer.parseInt(RemindDataValidation.PAYMENT_REMIND_SENDNO));
                    notice.setCreateTime(new Date(System.currentTimeMillis()));
                    notice.setNoticeUserId(payment.getUserId());
                    String typeName = null;
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE) == payment.getItemType()) {
                        typeName = "物业费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO) == payment.getItemType()) {
                        typeName = "公摊水电费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE) == payment.getItemType()) {
                        typeName = "购买停车费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR) == payment.getItemType()) {
                        typeName = "租赁停车费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE) == payment.getItemType()) {
                        typeName = "能耗费";
                    }
                    notice.setNoticeContent("尊敬的" + payment.getHouseNo() + "业主" + payment.getHouseOwnerName()
                            + "\n您" + payment.getBillingCycle() + "的" + typeName + "共计人民币" + billAmount + "元，请尽快缴费");
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
                    send.setSendTitle("缴费提醒");
                    send.setSendNo(RemindDataValidation.PAYMENT_REMIND_SENDNO);
                    send.setSendType(RemindDataValidation.SENDTYPE);
                    send.setSendContent("尊敬的" + payment.getHouseNo() + "业主" + payment.getHouseOwnerName()
                            + "\n您" + payment.getBillingCycle() + "的" + typeName + "共计人民币" + billAmount + "元，请尽快缴费");
                    String[] params = new String[]{payment.getHouseNo(),payment.getHouseOwnerName(),
                            payment.getBillingCycle(),typeName,billAmount};
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========SendMessage========" + send);
                    }
                    //转json
                    String pushInfo = GsonUtil.object2Gson(send);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========SendMessage to json========" + pushInfo);
                    }
                    //推送
                    MsgPushUtils.push(payment.getHouseOwnerPhone(), pushInfo);
                    //发短信给户主
                    String[] phones = new String[]{payment.getHouseOwnerPhone()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE,phones,params);
                }
            }
        }
    }

    @Override
    public void overDueRemind() throws Exception {
        //查询超期提醒天数
        List<RemindDays> remindDays = findByConfName(RemindDataValidation.OVERDUE_REMIND_DAYS,"1");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========remindDays=======" + remindDays);
        }
        //到期时间
        Date time = countTime(remindDays.get(0).getConfValue(), 1);
        time = DateUtil.toDate(DateUtil.toString(time, "yyyy-MM-dd"), "yyyy-MM-dd");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========deadLine=======" + time);
        }
        //根据到期时间查询数据
        List<OverDue> overDues = expenseBillMapper.findByDeadLine(time);
        if (LOG.isDebugEnabled()) {
            LOG.debug("========overDues=======" + overDues);
        }
        if (overDues != null && overDues.size() > 0) {
            for (OverDue overDue :
                    overDues) {
                if (!StringUtil.isBlank(overDue.getHouseOwnerPhone())){
                    UserAccount userAccount=userService.findByAccountName(overDue.getHouseOwnerPhone(),null);
                    if (userAccount!=null){
                        overDue.setUserId(userAccount.getUserId());
                    }
                }
                if (!StringUtil.isBlank(overDue.getUserId())) {
                    //发通知给户主
                    //计算总金额
                    double totalAmount = overDue.getBillAmount();
                    //计算结果四舍五入
                    String amount = String.format("%.2f", totalAmount);
                    //将计算结果转换为普通计数法
                    BigDecimal a = new BigDecimal(amount);
                    String billAmount = a.toPlainString();
                    //创建系统通知对象，将通知存到数据库
                    Notice notice = new Notice();
                    notice.setNoticeStatus(1);
                    notice.setNoticeType(Integer.parseInt(RemindDataValidation.OVERDUE_REMIND_SENDNO));
                    notice.setCreateTime(new Date(System.currentTimeMillis()));
                    notice.setNoticeUserId(overDue.getUserId());
                    String typeName = null;
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE) == overDue.getItemType()) {
                        typeName = "物业费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_TWO) == overDue.getItemType()) {
                        typeName = "公摊水电费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE) == overDue.getItemType()) {
                        typeName = "购买停车费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR) == overDue.getItemType()) {
                        typeName = "租赁停车费";
                    }
                    if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FIVE) == overDue.getItemType()) {
                        typeName = "能耗费";
                    }
                    String content;
                    String[] params = null;
                    if (!StringUtil.isBlank(overDue.getHouseNo())) {
                        content = "尊敬的" + overDue.getHouseNo() + "业主" + overDue.getHouseOwnerName()
                                + "\n截止" + DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd")
                                + "，您欠缴的" + typeName + "共计人民币" + billAmount + "元（未包含滞纳金），请尽快缴费，感谢您的支持";
                        params = new String[]{overDue.getHouseNo(),overDue.getHouseOwnerName(),
                                DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd"),typeName,billAmount};
                    } else {
                        content = "尊敬的业主" + overDue.getHouseOwnerName()
                                + "\n截止" + DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd")
                                + "，您欠缴的" + typeName + "共计人民币" + billAmount + "元（未包含滞纳金），请尽快缴费，感谢您的支持";
                        params = new String[]{"",overDue.getHouseOwnerName(),
                                DateUtil.toString(getBeforeDay(new Date()), "yyyy-MM-dd"),typeName,billAmount};
                    }
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
                    send.setSendTitle("缴费提醒");
                    send.setSendNo(RemindDataValidation.OVERDUE_REMIND_SENDNO);
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
                    //推送
                    MsgPushUtils.push(overDue.getHouseOwnerPhone(), pushInfo);
                    //发短信给户主
                    String[] phones = new String[]{overDue.getHouseOwnerPhone()};
                    BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE_TWO,phones,params);
                    //增加催缴次数
                    ExpenseBill bill = new ExpenseBill();
                    bill.setBillNo(overDue.getBillNo());
                    bill.setUrgeCount(overDue.getUrgeCount() + 1);
                    bill.setLastUrgeTime(new Date(System.currentTimeMillis()));
                    expenseBillMapper.updateByPrimaryKeySelective(bill);
                }
            }
        }
    }

    @Override
    public void payUpNotice() throws Exception {
        //查询所有物业/停车费收费项
        List<ExpenseItem> items = itemMapper.findRemindItems();
        if (LOG.isDebugEnabled()) {
            LOG.debug("========findRemindItems========" + items);
        }
        if (items != null && items.size() > 0) {
            for (ExpenseItem item :
                    items) {
                //查询每个收费项对应的房屋
                List<House> houses = houseMapper.selectByItemId(item.getItemId());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========everyItem houses========" + houses);
                }
                //判断是否存在别墅
                List<Long> buildingIds = relaMapper.selectBuildingIdByItemId(item.getItemId());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========everyItem buildingIds========" + buildingIds);
                }
                if (buildingIds.contains(null)) {
                    List<House> villa = relaMapper.findVilla();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========everyItem villa========" + villa);
                    }
                    if (villa != null && villa.size() > 0) {
                        houses.addAll(villa);
                    }
                }
                if (houses != null && houses.size() > 0) {
                    for (House house :
                            houses) {
                        //获取停车位数
                        Integer buyCounts = spaceMapper.findCountsByHouseIdAndType(house.getHouseId(), HouseDataValidation.BUY_PARKING_SPACE);
                        Integer rentCounts = spaceMapper.findCountsByHouseIdAndType(house.getHouseId(), HouseDataValidation.RENT_PARKING_SPACE);
                        long startTime = initStartTime(item.getStartTime()).getTime();
                        long nowTime = System.currentTimeMillis();
                        //计算当前日和开始收费时间的月份差，判断是否需要出账单
                        int monthBetween = getMonthDiff(new Date(), item.getStartTime());
                        //在判断当前时间是否比开始收费时间早，若早，则不生成账单
                        if (nowTime >= startTime && monthBetween % item.getItemCycle() == 0) {
                            //计算账单的计费开始时间
                            Date billStartTime = initStartTime(new Date());
                            //计算账单的计费结束时间
                            Date billEndTime = countEndTime(billStartTime, item.getItemCycle());
                            //计费周期
                            String billCycle = null;
                            //账单金额
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
                            //判断预缴是否已经生成过该账单
                            ExpenseBill bill = new ExpenseBill();
                            bill.setHouseId(house.getHouseId());
                            bill.setItemId(item.getItemId());
                            bill.setStartTime(DateUtil.toDate(DateUtil.toString(billStartTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
                            bill.setEndTime(DateUtil.toDate(DateUtil.toString(billEndTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
                            List<ExpenseBill> bills = expenseBillMapper.findByExpenseBill(bill);
                            //生成账单 
                            if (Double.parseDouble(billAmount) != 0 && (bills == null || (bills != null && bills.size() == 0))) {
                                billCycle = createBill(house, item, new BigDecimal(billAmount),
                                        billStartTime, billEndTime);
                            }
                            HouseOwner houseOwner = houseOwnerMapper.selectByHouseId(house.getHouseId());
                            //发送短信和通知
                            if (!StringUtil.isBlank(billCycle) && houseOwner != null) {
                                String typeName = null;
                                if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_ONE) == item.getItemType()) {
                                    typeName = "物业费";
                                }
                                if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_THREE) == item.getItemType()) {
                                    typeName = "购买停车费";
                                }
                                if (Integer.parseInt(ExpenseStaticStatus.ITEM_TYPE_FOUR) == item.getItemType()) {
                                    typeName = "租赁停车费";
                                }
                                //将费用转换为普通计数法
                                BigDecimal a = new BigDecimal(billAmount);
                                billAmount = a.toPlainString();
                                String content = "尊敬的" + makeHouseNo(house.getHouseId()) + "业主" + houseOwner.getRealName()
                                        + "\n您" + billCycle + "的" + typeName + "共计人民币" + billAmount + "元，请尽快缴费";
                                String[] params = new String[]{makeHouseNo(house.getHouseId()),houseOwner.getRealName(),
                                        billCycle,typeName,billAmount};
                                //判断是否存在户主账号
                                if (!StringUtil.isBlank(houseOwner.getUserId())) {
                                    //发通知
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("==========houseOwner==========" + houseOwner);
                                    }
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
                                    //查询用户账号
                                    //jian.z 2018-01-21
                                    UserAccount account = userService.findByUserId(houseOwner.getUserId(),null);
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
                                if (StringUtil.isBlank(houseOwner.getUserId())) {
                                    //发短信
                                    String[] phones = new String[]{houseOwner.getPhoneNum()};
                                    BaiduSmsUtil.sendSms(BaiduSmsCode.PAY_REMIND_TEMPLATE,phones,params);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 生成账单
     *
     * @param house
     * @param item
     * @param billAmount
     * @param startTime
     * @param endTime
     * @return 计费区间
     * @throws Exception
     */
    private String createBill(House house, ExpenseItem item, BigDecimal billAmount, Date startTime, Date endTime) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======createBill start========house=" + house + ",item=" + item + ",billAmount=" + billAmount + ",startTime=" + startTime);
        }
        ExpenseBill bill = new ExpenseBill();
        bill.setBillNo(getLocalTrmSeqNum("ZD"));
        bill.setBillStatus(RemindDataValidation.BILL_NOTPAY_STATUS);
        bill.setCreateTime(new Date(System.currentTimeMillis()));
        bill.setEndTime(DateUtil.toDate(DateUtil.toString(endTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
        bill.setStartTime(DateUtil.toDate(DateUtil.toString(startTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
        bill.setDeadLine(DateUtil.toDate(DateUtil.toString(countDeadLine(item.getChargeCycle()), "yyyy-MM-dd"), "yyyy-MM-dd"));
        bill.setIsPrint(0);
        bill.setHouseId(house.getHouseId());
        bill.setUrgeCount(0);
        bill.setDemandBillStatus(1);
        HouseOwner houseOwner = houseOwnerMapper.selectByHouseId(house.getHouseId());
        if (houseOwner != null && !StringUtil.isBlank(houseOwner.getUserId())) {
            if (!StringUtil.isBlank(houseOwner.getUserId())) {
                UserAccount userAccount=userService.findByUserId(houseOwner.getUserId(),null);
                if (null!=userAccount){
                    bill.setHouseOwnerPhone(userAccount.getAcctName());
                }
            } else {
                bill.setHouseOwnerPhone(houseOwner.getPhoneNum());
            }
            bill.setHouseOwnerName(houseOwner.getRealName());
        }
        bill.setBillAmount(billAmount);
        bill.setItemId(item.getItemId());
        bill.setIsDelete(1);
        bill.setItemType(item.getItemType());
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======before createBill========bill=" + bill);
        }
        expenseBillMapper.insertSelective(bill);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======createBill success========");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======createBill end========");
        }
        return DateUtil.toString(bill.getStartTime(), "yyyy-MM-dd") + "~" + DateUtil.toString(bill.getEndTime(), "yyyy-MM-dd");
    }

    /**
     * 计算账单结束时间
     *
     * @param startTime
     * @param itemCycle
     * @return
     */
    private Date countEndTime(Date startTime, Integer itemCycle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MONTH, itemCycle);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);//时
        calendar.set(Calendar.MINUTE, 59);//分
        calendar.set(Calendar.SECOND, 59);//秒
        calendar.set(Calendar.MILLISECOND, 0);//毫秒
        calendar.add(DATE, -1);
        return calendar.getTime();
    }

    /**
     * 计算生成账单的到期日
     *
     * @param chargeCycle
     * @return
     */
    private Date countDeadLine(Integer chargeCycle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(DATE, calendar.get(DATE) + chargeCycle - 1);
        return calendar.getTime();
    }

    /**
     * <p>
     * 初始化开始收费时间
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/26 16:57
     */
    private Date initStartTime(Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);//时
        calendar.set(Calendar.MINUTE, 0);//分
        calendar.set(Calendar.SECOND, 0);//秒
        calendar.set(Calendar.MILLISECOND, 0);//毫秒
        return calendar.getTime();
    }

    /**
     * 计算时间
     *
     * @param days
     * @return
     */
    private Date countTime(String days, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(DATE, calendar.get(DATE) - Integer.parseInt(days) - day);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws Exception
     */
    public static int daysBetween(Date smdate, Date bdate) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======daysBetween start========smdate=" + smdate + ",bdate=" + bdate);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        if (LOG.isDebugEnabled()) {
            LOG.debug("=======daysBetween end========days=" + (Integer.parseInt(String.valueOf(betweenDays)) + 1));
        }
        return Integer.parseInt(String.valueOf(betweenDays)) + 1;
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
        int day = c1.get(DATE);
        c1.set(DATE, day - 1);
        return c1.getTime();
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
     * @return 如果d1>d2返回 月数差 否则返回0
     */
    private int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return 0;
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
            BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
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
}

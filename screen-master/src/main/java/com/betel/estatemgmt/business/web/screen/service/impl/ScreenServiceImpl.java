package com.betel.estatemgmt.business.web.screen.service.impl;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.screen.constant.ScreenConstant;
import com.betel.estatemgmt.business.web.screen.model.*;
import com.betel.estatemgmt.business.web.screen.service.ScreenService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseFlowMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemBuildingRelaMapper;
import com.betel.estatemgmt.common.mapper.expense.ExpenseItemMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.patrol.PatrolEquipmentMapper;
import com.betel.estatemgmt.common.mapper.patrol.PatrolRecordMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.mapper.work.TaskRecordMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.common.model.expense.ExpenseFlow;
import com.betel.estatemgmt.common.model.expense.ExpenseItem;
import com.betel.estatemgmt.common.model.patrol.PatrolRecord;
import com.betel.estatemgmt.utils.DateUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: ScreenServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/1/9 9:25 <br/>
 * Version: 1.0 <br/>
 */
@Service("ScreenService")
@Transactional(rollbackFor = Exception.class)
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private ExpenseBillMapper expenseBillMapper;

    @Autowired
    private ExpenseItemMapper itemMapper;

    @Autowired
    private ExpenseFlowMapper flowMapper;

    @Autowired
    private TaskRecordMapper recordMapper;

    @Autowired
    private PatrolRecordMapper patrolRecordMapper;

    @Autowired
    private PatrolEquipmentMapper equipmentMapper;

    @Override
    public FindDataResp findData(HttpServletRequest request) throws Exception {
        FindDataResp resp = new FindDataResp();
        //查询房屋数据
        resp.setHouseData(findHouseData());
        //查询维修数据
        resp.setRepairData(findRepairData());
        //查询账单数据
        resp.setExpenseData(findExpenseData());
        //查询巡检设备数据
        resp.setPatrolData(findPatrolData());
        //查询工作任务数据
        resp.setTaskData(findTaskData(AESUtil.decrypt(request.getHeader("estateId"))));
        return resp;
    }

    /**
     * 计算房屋统计数据
     * @return
     */
    private HouseData findHouseData(){
        HouseData houseData = houseMapper.findHouseData();
        if (null != houseData){
            Integer allHouses = houseData.getEmptyHouses()+houseData.getFullHouses()+houseData.getRentHouses();
            if (allHouses != 0){
                houseData.setEmptyPercentage(countPercentage(houseData.getEmptyHouses(),allHouses));
                houseData.setFullPercentage(countPercentage(houseData.getFullHouses(),allHouses));
                houseData.setRentPercentage(countPercentage(houseData.getRentHouses(),allHouses));
            }else {
                houseData.setEmptyPercentage("100");
                houseData.setFullPercentage("0");
                houseData.setRentPercentage("0");
            }
            return houseData;
        }else {
            HouseData data = new HouseData();
            data.setEmptyPercentage("100");
            data.setFullPercentage("0");
            data.setRentPercentage("0");
            data.setEmptyHouses(0);
            data.setFullHouses(0);
            data.setRentHouses(0);
            return data;
        }
    }

    /**
     * 计算维修统计数据
     * @return
     */
    private RepairData findRepairData(){
        RepairData repairData = repairOrderMapper.findRepairData();
        if (null != repairData){
            if (repairData.getTodayRepairs() != 0){
                repairData.setTodayAcceptPercentage(countPercentage(repairData.getTodayAcceptRepairs(),repairData.getTodayRepairs()));
                repairData.setTodayFinishPercentage(countPercentage(repairData.getTodayFinishRepairs(),repairData.getTodayRepairs()));
                repairData.setTodayWaitPercentage(countPercentage(repairData.getTodayWaitRepairs(),repairData.getTodayRepairs()));
            }else {
                repairData.setTodayAcceptPercentage("0");
                repairData.setTodayFinishPercentage("0");
                repairData.setTodayWaitPercentage("0");
            }
            return repairData;
        }else {
            RepairData data = new RepairData();
            data.setTodayWaitPercentage("0");
            data.setTodayFinishPercentage("0");
            data.setTodayAcceptPercentage("0");
            data.setAllUntreatedRepairs(0);
            data.setTodayAcceptRepairs(0);
            data.setTodayRepairs(0);
            data.setTodayWaitRepairs(0);
            data.setTodayFinishRepairs(0);
            data.setTodayWorkedRepairs(0);
            return data;
        }
    }

    /**
     * 计算缴费统计数据
     * @return
     */
    private ExpenseData findExpenseData() throws Exception{
        ExpenseData data = new ExpenseData();
        //定义变量
        Double allPaidProperty = 0.00;
        Double allUnPaidProperty = 0.00;
        Double allPaidEnergy = 0.00;
        Double allUnPaidEnergy = 0.00;
        Double allPaidPublic = 0.00;
        Double allUnPaidPublic = 0.00;
        Double allPaidSpace = 0.00;
        Double allUnPaidSpace = 0.00;
        Integer paidPropertyHouses = 0;
        Integer unPaidPropertyHouses = 0;
        Integer paidEnergyHouses = 0;
        Integer unPaidEnergyHouses = 0;
        Integer paidPublicHouses = 0;
        Integer unPaidPublicHouses = 0;
        Integer paidSpaceHouses = 0;
        Integer unPaidSpaceHouses = 0;
        DecimalFormat df = new DecimalFormat("######0.00");
        //定义已缴费账单列表
        List<ExpenseBill> bills = new ArrayList<>();
        //定义逾期户数
        Set<String> overHouses = new LinkedHashSet<>();
        //定义停车费未缴费账单列表
        List<ExpenseBill> spaceBills = new ArrayList<>();
        //定义停车费账单对应的所有有停车位的房屋
        List<String> cycleHouseIds = new ArrayList<>();
        //查询所有收费项
        List<ExpenseItem> items = itemMapper.findItems();
        //定义日期入参格式：年月日时分秒
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Set set = new  HashSet();
        if (items != null && items.size()>0){
            //遍历收费项，获取上个周期的所有账单
            for (ExpenseItem item:
                    items) {
                //定义开始收费日期
                Date startTime = null;
                if (item.getItemType().equals(ScreenConstant.PROPERTY_TYPE)
                        || item.getItemType().equals(ScreenConstant.BUY_SPACE_TYPE)
                        || item.getItemType().equals(ScreenConstant.RENT_SPACE_TYPE)) {
                    //物业费/停车费
                    startTime = item.getStartTime();
                }else {
                    //能耗费/公摊水电费
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    startTime = sdf.parse(ScreenConstant.SYSTEM_DELIVER_TIME);
                }
                //已激活
                if (startTime != null){
                    //计算上个周期起始月、结束月
                    Date[] lastCycle = findLastCycle(startTime, item.getItemCycle(),item.getItemType());
                    lastCycle[0] = format.parse(format.format(lastCycle[0]));
                    lastCycle[1] = format.parse(format.format(lastCycle[1]));
                    //停车费收费项
                    if (item.getItemType().equals(ScreenConstant.BUY_SPACE_TYPE)
                            || item.getItemType().equals(ScreenConstant.RENT_SPACE_TYPE)){
                        //查询上个周期该收费项的所有有停车位的房屋
                        List<ExpenseBill> allBills = expenseBillMapper.findByTimeBetween(
                                lastCycle[0], lastCycle[1], item.getItemId(),null);
                        //用于去重复的houseId
                        if (allBills != null && allBills.size()>0){
                            for (ExpenseBill e:
                                    allBills) {
                                if(set.add(e.getHouseId())){
                                    cycleHouseIds.add(e.getHouseId());
                                }
                            }
                        }
                    }
                    //上个周期该收费项的已缴费账单
                    List<ExpenseBill> expenseBills = expenseBillMapper.findByTimeBetween(
                            lastCycle[0], lastCycle[1], item.getItemId(),ScreenConstant.PAY_STATUS);
                    if (expenseBills != null && expenseBills.size()>0){
                        for (ExpenseBill e:
                             expenseBills) {
                            bills.add(e);
                        }
                    }
                    //上个周期该收费项的逾期未缴费户数
                    List<String> overs = expenseBillMapper.findOverHousesByParams(
                            lastCycle[0], lastCycle[1], item.getItemId());
                    if (overs != null && overs.size()>0){
                        for (String o:
                             overs) {
                            overHouses.add(o);
                        }
                    }
                    //查询该周期内该收费项已缴费/未缴费的账单金额
                    Double paidAmount = expenseBillMapper.countAmountByParams(
                            lastCycle[0],lastCycle[1],item.getItemId(),ScreenConstant.PAY_STATUS);
                    Double paidOver = expenseBillMapper.countOverFineByParams(
                            lastCycle[0],lastCycle[1],item.getItemId(),ScreenConstant.PAY_STATUS);
                    Double unPaidAmount = expenseBillMapper.countAmountByParams(
                            lastCycle[0],lastCycle[1],item.getItemId(),ScreenConstant.NOT_PAY_STATUS);
                    Double unPaidOver = expenseBillMapper.countOverFineByParams(
                            lastCycle[0],lastCycle[1],item.getItemId(),ScreenConstant.NOT_PAY_STATUS);
                    //查询该周期内已缴纳/未缴纳用户数量
                    Integer paidHouses = expenseBillMapper.countHouseByParams(
                            lastCycle[0],lastCycle[1],item.getItemId(),ScreenConstant.PAY_STATUS);
                    Integer unPaidHouses = expenseBillMapper.countHouseByParams(
                            lastCycle[0],lastCycle[1],item.getItemId(),ScreenConstant.NOT_PAY_STATUS);
                    //判断结果是否为null
                    if (paidAmount==null){paidAmount=0.00;}
                    if (paidOver==null){paidOver=0.00;}
                    if (unPaidAmount==null){unPaidAmount=0.00;}
                    if (unPaidOver==null){unPaidOver=0.00;}
                    if (paidHouses==null){paidHouses=0;}
                    if (unPaidHouses==null){unPaidHouses=0;}
                    if (item.getItemType().equals(ScreenConstant.PROPERTY_TYPE)){
                        allPaidProperty += paidAmount+paidOver;
                        allUnPaidProperty += unPaidAmount+unPaidOver;
                        paidPropertyHouses += paidHouses;
                        unPaidPropertyHouses += unPaidHouses;
                    }else if (item.getItemType().equals(ScreenConstant.BUY_SPACE_TYPE)
                            || item.getItemType().equals(ScreenConstant.RENT_SPACE_TYPE)){
                        allPaidSpace += paidAmount;
                        allUnPaidSpace += unPaidAmount;
                        //查询该收费项的未缴费账单
                        List<ExpenseBill> unpaidBills = expenseBillMapper.findByTimeBetween(
                                lastCycle[0], lastCycle[1], item.getItemId(),ScreenConstant.NOT_PAY_STATUS);
                        if (unpaidBills != null && unpaidBills.size()>0){
                            for (ExpenseBill e:
                                    unpaidBills) {
                                spaceBills.add(e);
                            }
                        }
                    }else if (item.getItemType().equals(ScreenConstant.ENERGY_TYPE)){
                        allPaidEnergy += paidAmount;
                        allUnPaidEnergy += unPaidAmount;
                        paidEnergyHouses += paidHouses;
                        unPaidEnergyHouses += unPaidHouses;
                    }else if (item.getItemType().equals(ScreenConstant.PUBLIC_TYPE)){
                        allPaidPublic += paidAmount;
                        allUnPaidPublic += unPaidAmount;
                        paidPublicHouses += paidHouses;
                        unPaidPublicHouses += unPaidHouses;
                    }
                }
            }
        }
        //遍历所有已缴费账单，统计线上缴费百分比
        if (bills.size()>0){
            Integer paidCount = 0;
            for (ExpenseBill e:
                 bills) {
                ExpenseFlow flow = flowMapper.selectByPrimaryKey(e.getFlowNo());
                if (flow != null && ScreenConstant.ONLINE_PAY.equals(flow.getIsOnline())){
                    paidCount++;
                }
            }
            data.setOnlinePercentage(countPercentage(paidCount,bills.size()));
        }else {
            //没有已缴费账单
            data.setOnlinePercentage("50");
        }
        //判断逾期户数是否存在
        if (overHouses.size()>0){
            data.setOverDueHouses(overHouses.size());
        }else {
            data.setOverDueHouses(0);
        }
        //查询停车费已缴纳用户数量、未缴纳用户数量
        if (cycleHouseIds != null && cycleHouseIds.size()>0){
            //若有停车位的房屋，则判断是否存在未缴费的停车费用，
            //若存在则查询，若不存在则再判断该周期内的停车费用是否存在，若存在则表示停车费用户都已缴纳，若不存在表示这个周期内没有停车费用的账单
            if (spaceBills.size()>0){
                //查询未缴费账单中的houseId
                Set set1 = new HashSet();
                List<String> houseIds = new ArrayList<>();
                for (ExpenseBill e:
                        spaceBills) {
                    if(set1.add(e.getHouseId())){
                        houseIds.add(e.getHouseId());
                    }
                }
                for (String h:
                        cycleHouseIds) {
                    if (houseIds.contains(h)){
                        unPaidSpaceHouses ++;
                    }
                }
                paidSpaceHouses = cycleHouseIds.size()- unPaidSpaceHouses;
            }else {
                //存在已缴纳的停车费用
                if (allPaidSpace>0){
                    paidSpaceHouses = cycleHouseIds.size();
                    unPaidSpaceHouses = 0;
                }else {
                    paidSpaceHouses = 0;
                    unPaidSpaceHouses = 0;
                }
            }
        }
        data.setAllPaidProperty(df.format(allPaidProperty));
        data.setAllUnPaidProperty(df.format(allUnPaidProperty));
        data.setAllPaidEnergy(df.format(allPaidEnergy));
        data.setAllUnPaidEnergy(df.format(allUnPaidEnergy));
        data.setAllPaidPublic(df.format(allPaidPublic));
        data.setAllUnPaidPublic(df.format(allUnPaidPublic));
        data.setAllPaidSpace(df.format(allPaidSpace));
        data.setAllUnPaidSpace(df.format(allUnPaidSpace));

        data.setPaidPropertyHouses(paidPropertyHouses);
        data.setUnPaidPropertyHouses(unPaidPropertyHouses);
        data.setPaidEnergyHouses(paidEnergyHouses);
        data.setUnPaidEnergyHouses(unPaidEnergyHouses);
        data.setPaidPublicHouses(paidPublicHouses);
        data.setUnPaidPublicHouses(unPaidPublicHouses);
        data.setPaidSpaceHouses(paidSpaceHouses);
        data.setUnPaidSpaceHouses(unPaidSpaceHouses);

        data.setPaidTotal(df.format(allPaidProperty+allPaidEnergy+allPaidPublic+allPaidSpace
                +allUnPaidProperty+allUnPaidEnergy+allUnPaidPublic+allUnPaidSpace));
        data.setUnPaidTotal(df.format(allUnPaidProperty+allUnPaidEnergy+allUnPaidPublic+allUnPaidSpace));
        return data;
    }

    /**
     * 计算巡检设备数据
     * @return
     */
    private PatrolData findPatrolData() throws Exception{
        PatrolData data = new PatrolData();
        //查询今日已完成设备数
        List<String> finishEquips = equipmentMapper.findTodayFinishEquips();
        int normalEquips = 0;
        if (finishEquips != null && finishEquips.size()>0){
            for (String equip:
                    finishEquips) {
                //查询最新的设备状态
                PatrolRecord newestByEquipId = patrolRecordMapper.findNewestByEquipId(equip);
                if (newestByEquipId != null && newestByEquipId.getRecordStatus().equals(1)){
                    normalEquips++;
                }
            }
            data.setTodayFinishEquipments(finishEquips.size());
            data.setTodayNormalEquipments(normalEquips);
            data.setTodayAbnormalEquipments(finishEquips.size()-normalEquips);
            data.setTodayAbnormalPercentage(countPercentage(data.getTodayAbnormalEquipments(),data.getTodayFinishEquipments()));
            data.setTodayNormalPercentage(countPercentage(data.getTodayNormalEquipments(),data.getTodayFinishEquipments()));
        }else {
            data.setTodayFinishEquipments(0);
            data.setTodayNormalEquipments(0);
            data.setTodayAbnormalEquipments(0);
            data.setTodayAbnormalPercentage("0");
            data.setTodayNormalPercentage("100");
        }
        return data;
    }

    /**
     * 计算工作任务数据
     * @return
     */
    private TaskData findTaskData(String estateId) throws Exception{
        TaskData taskData = new TaskData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当天零点
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        Date start = c1.getTime();
        String startTime = format.format(start);

        //查询截至到目前的新增的，已完成，未完成的任务，用于计算百分比
        TodayTaskData data = recordMapper.findTodayTaskData(startTime,format.format(new Date()));
        if (null != data) {
            //查询今日已完成/未完成百分比
            if (data.getTodayTasks() != 0) {
                taskData.setTodayFinishPercentage(countPercentage(data.getTodayFinishTasks(), data.getTodayTasks()));
                taskData.setTodayUnFinishPercentage(countPercentage(data.getTodayUnFinishTasks(), data.getTodayTasks()));
            } else {
                taskData.setTodayFinishPercentage("0");
                taskData.setTodayUnFinishPercentage("100");
            }
        }else {
            //无数据则给默认值
            taskData.setTodayFinishPercentage("0");
            taskData.setTodayUnFinishPercentage("100");
        }

        //定义部门列表
        List<DepTaskData> list = new ArrayList<>();
        //查询所有一级部门
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("estateId",estateId);
        String url = ConfigManager.read(ConfigName.OA_PROJECT_URL) + "httpclientOA/task/v1/findDeptListDiff";
        Response resp = HttpClientUtil.post(url, jsonParam, null);
        if (null != resp){
            Type type = new TypeToken<List<FindDepListResp>>() {
            }.getType();
            List<FindDepListResp> departments = gson.fromJson(resp.getData().toString(), type);
            if (null != departments && departments.size()>0){
                //遍历部门，查询各部门任务总数量、已完成任务总数量、未完成任务总数量
                for (FindDepListResp dep:
                        departments) {
                    DepTaskData depTaskData = recordMapper.findDepTaskData(dep.getDepId().substring(0,4));
                    if (null != depTaskData){
                        if (depTaskData.getAllTasks() != 0){
                            depTaskData.setFinishPercentage(countPercentage(depTaskData.getFinishTasks(),depTaskData.getAllTasks()));
                            depTaskData.setUnFinishPercentage(countPercentage(depTaskData.getUnFinishTasks(),depTaskData.getAllTasks()));
                        }else {
                            depTaskData.setFinishPercentage("0");
                            depTaskData.setUnFinishPercentage("0");
                        }
                        depTaskData.setDepName(dep.getDepartmentName());
                        list.add(depTaskData);
                    }else {
                        //无数据则给默认值
                        DepTaskData depTaskData1 = new DepTaskData();
                        depTaskData1.setDepName(dep.getDepartmentName());
                        depTaskData1.setFinishPercentage("0");
                        depTaskData1.setUnFinishPercentage("0");
                        depTaskData1.setAllTasks(0);
                        depTaskData1.setFinishTasks(0);
                        depTaskData1.setUnFinishTasks(0);
                        list.add(depTaskData1);
                    }
                }
            }
        }
        taskData.setDepTaskData(list);

        //定义任务列表
        List<TodayTaskData> todayList = new ArrayList<>();
        //定义时间点数组
        Date[] dateAttr = getDateAttr();
        for (Date date:
                dateAttr) {
            //判断当前时间跟时间戳，当前时间>=时间戳则查询
            if (!validateTimeDistance1(new Date(),date)){
                TodayTaskData todayTaskData = recordMapper.findTodayTaskData(startTime,format.format(date));
                if (todayTaskData != null){
                    todayList.add(todayTaskData);
                }else {
                    //无数据则给默认值
                    TodayTaskData todayTaskData1 = new TodayTaskData();
                    todayTaskData1.setTodayFinishTasks(0);
                    todayTaskData1.setTodayTasks(0);
                    todayTaskData1.setTodayUnFinishTasks(0);
                    todayTaskData1.setUnfinishTasks(0);
                    todayList.add(todayTaskData1);
                }
            }
        }
        taskData.setTodayTaskDatas(todayList);
        return taskData;
    }

    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    private boolean validateTimeDistance1(Date startTime,Date endTime){
        boolean flag = true;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();
        if (time1 >= time2){
            flag = false;
        }
        return flag;
    }

    /**
     * 获取任务时间戳数组
     * @return
     */
    private Date[] getDateAttr(){
        Date[] dates = new Date[6];
        //获取今天0 9 12 15 18 24
        int[] dateAttr = new int[]{0,9,12,15,18,24};
        for (int i = 0; i < dateAttr.length; i++) {
            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.HOUR_OF_DAY, dateAttr[i]);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            dates[i] = c1.getTime();
        }
        return dates;
    }

    /**
     * 求百分比
     * @param num1 除数
     * @param num2 被除数
     * @return
     */
    private String countPercentage(Integer num1, Integer num2){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后0位
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format((float) num1 / (float) num2 * 100);
    }

    /**
     * 获取收费项当前周期的上一个周期
     * @param startTime 收费项开始收费时间
     * @param itemCycle 收费周期
     * @param itemType 收费类型
     * @return
     * @throws Exception
     */
    private Date[] findLastCycle(Date startTime,Integer itemCycle,Integer itemType) throws Exception{
        Date[] dataAttr = new Date[2];
        //计算当前月和startTime之间的月份差
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(DateUtil.today("yyyy-MM")));
        aft.setTime(sdf.parse(DateUtil.toString(startTime,"yyyy-MM")));
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        int monthBetween = Math.abs(month + result);
        //计算上个周期是第几个周期
        int count = monthBetween/itemCycle;
        //计算上个周期起始时间和结束时间
        Calendar cl = Calendar.getInstance();
        cl.setTime(startTime);
        //判断当前时间和开始收费时间
        if (validateTimeDistance(new Date(),startTime)){
            //若当前时间在开始时间之前，则不查询数据
            dataAttr[0] = new Date();
            dataAttr[1] = new Date();
            return dataAttr;
        }
        if (itemType.equals(ScreenConstant.PROPERTY_TYPE)
                || itemType.equals(ScreenConstant.BUY_SPACE_TYPE)
                || itemType.equals(ScreenConstant.RENT_SPACE_TYPE)) {
            //物业费/停车费
            cl.add(Calendar.MONTH, count*itemCycle);
            dataAttr[0] = cl.getTime();
            cl.add(Calendar.MONTH,itemCycle-1);
            dataAttr[1] = cl.getTime();
        }else {
            //能耗费/公摊水电费
            cl.add(Calendar.MONTH, count*itemCycle-1);
            dataAttr[1] = cl.getTime();
            cl.add(Calendar.MONTH,-(itemCycle-1));
            dataAttr[0] = cl.getTime();
        }

        return dataAttr;
    }

    /**
     * <p>
     * 校验时间差
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:29
     *
     * @param startTime,endTime 入参
     * @return flag
     */
    private boolean validateTimeDistance(Date startTime,Date endTime){
        boolean flag = false;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();
        if (time1 < time2){
            flag = true;
        }
        return flag;
    }
}

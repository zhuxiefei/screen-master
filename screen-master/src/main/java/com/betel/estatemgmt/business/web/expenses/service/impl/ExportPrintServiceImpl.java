package com.betel.estatemgmt.business.web.expenses.service.impl;


import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.business.web.expenses.model.ExpensesReq;
import com.betel.estatemgmt.business.web.expenses.model.Note;
import com.betel.estatemgmt.business.web.expenses.model.PrintNote;
import com.betel.estatemgmt.business.web.expenses.service.ExportPrintService;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  导出打印查询接口实现类
 * </p>
 * ClassName: ExpensesServiceImpl <br/>
 * Author:zhangjain <br/>
 * Date: 2017/9/18  <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class ExportPrintServiceImpl implements ExportPrintService {
    private static final Logger LOG = LoggerFactory.getLogger(ExportPrintServiceImpl.class);
    @Autowired
    private ExpenseBillMapper expenseBillMapper;
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private BuildingUnitMapper buildingUnitMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Override
    public List<Expenses> exportBill(ExpensesReq expensesReq,boolean flag) {
        if (flag==true){
            List<Expenses> expensesList=expenseBillMapper.exportBill(expensesReq);
            if (expensesList.size()==0){
                expensesList.add(creatExpenses());
            }
            if (expensesList!=null && expensesList.size()>0){
                for (Expenses ex:
                     expensesList) {
                    if (!StringUtil.isBlank(ex.getHouseInfo())){
                        ex.setHouseInfo(makeHouseNo(ex.getHouseInfo()));
                    }
                }
            }
            return   expensesList;
        }else{
            String[] billNos= Tool.getIdArrOfStringType(expensesReq.getBillNos());
            //少一个账单被删除的判断
            List<Expenses> expensesList=expenseBillMapper.exportBillById(billNos);
            if (expensesList.size()==0){
                expensesList.add(creatExpenses());
            }
            if (expensesList!=null && expensesList.size()>0){
                for (Expenses ex:
                        expensesList) {
                    if (!StringUtil.isBlank(ex.getHouseInfo())){
                        ex.setHouseInfo(makeHouseNo(ex.getHouseInfo()));
                    }
                }
            }
            return expensesList;
        }
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
     *  创建空的对象
     * @return
     */
    private static Expenses creatExpenses(){
        Expenses expenses=new Expenses();
        expenses.setHouseInfo("");
        expenses.setHouseMaster("");
        expenses.setPhone("");
        expenses.setBillingStartTime("");
        expenses.setBillingEndTime("");
        expenses.setItemType("");
        expenses.setBillAmount("");
        expenses.setOverduefinePrice("");
        expenses.setTotalAmount("");
        expenses.setCreateTime("");
        expenses.setBillStatus("");
        return expenses;
    }

    @Override
    public PrintNote printReceipt(ExpensesReq expensesReq) {
        String[] billNos= Tool.getIdArrOfStringType(expensesReq.getBillNos());
        List<PrintNote> printNoteList=expenseBillMapper.printReceipt(billNos);
        if (LOG.isDebugEnabled()){ LOG.debug("-----------------打印收据查询已缴费详情------xpensesList------"+printNoteList);}
        PrintNote printNote =new PrintNote();
        // -------------------------先组合外围------------------------------
        if(printNoteList.size()<=0){
            return  null;
        }
        printNote.setHouseMaster(printNoteList.get(0).getHouseMaster());
        printNote.setPhone(printNoteList.get(0).getPhone());
        //房屋信息
        String houseInfo = printNoteList.get(0).getHouseInfo();
        if (!StringUtil.isBlank(houseInfo)){
            printNote.setHouseInfo(makeHouseNo(houseInfo));
        }else{
            printNote.setHouseInfo("");
        }
        //“totalAmount”所有的总金额
        double totalAmount=0.00;
        //“receivableTotalAmount(应收总金额),
        double receivableTotalAmount=0.00;
        //“actualTotalAmount”(实收总金额)
        double actualTotalAmount=0.00;
        double totalOverdueFinePrice=0.00;
        //-------------------------在确定内围--------------------------------
        List<Note> notes=new ArrayList<>();
        for (int i=0;i<printNoteList.size();i++){
            //创建一个内部对象
            Note note=new Note();
            //payTime”收费日期：交钱时间
            String payTime=printNoteList.get(i).getPayTime();
            if (StringUtil.isBlank(payTime)){
                note.setPayTime("");
            }else {
                note.setPayTime(payTime);
            }

            //“itemType”收费项目类型)
            String itemType=printNoteList.get(i).getItemType();
            if (StringUtil.isBlank(itemType)){
                note.setItemType("");
            }else{
                note.setItemType(itemType);
            }
            //“billingStartTime应收起始时间)

            String billingStartTime=printNoteList.get(i).getBillingStartTime();
            if (StringUtil.isBlank(billingStartTime)){
                note.setBillingStartTime("");
            }else{
                note.setBillingStartTime(billingStartTime);
            }
            //“billingEndTime”应收截止时间)
            String billingEndTime=printNoteList.get(i).getBillingEndTime();
            if (StringUtil.isBlank(billingEndTime)){
                note.setBillingEndTime("");
            }else{
                note.setBillingEndTime(billingEndTime);
            }

            //“itemPrice”单价)
            String itemPrice=printNoteList.get(i).getItemPrice();
            if (StringUtil.isBlank(itemPrice)){
                note.setItemPrice("");
            }else {
                note.setItemPrice(itemPrice);
            }

            //“itemCycle”数量）
            String itemCype=printNoteList.get(i).getItemCycle();
            if (StringUtil.isBlank(itemCype)){
                note.setItemCycle("");
            }else {
                note.setItemCycle(printNoteList.get(i).getItemCycle());
            }

            //“overdueFinePrice”滞纳金),
            String overdueFinePrice=printNoteList.get(i).getOverduefinePrice();
            if (StringUtil.isBlank(overdueFinePrice)){
                note.setOverdueFinePrice("");
            }else{
                note.setOverdueFinePrice(printNoteList.get(i).getOverduefinePrice());
            }

             // chargeWay”收费方式)
            String chargeWay=printNoteList.get(i).getChargeWay();
            if (StringUtil.isBlank(chargeWay)){
                note.setChargeWay("");
            }else {
                note.setChargeWay(printNoteList.get(i).getChargeWay());
            }

            //“totalbillAmount”应收合计金额：单个账单滞纳金+应收金额	合计)
            String totalBillAmount=printNoteList.get(i).getTotalAmount();
            if (StringUtil.isBlank(totalBillAmount)){
                note.setTotalbillAmount("");
            }else {
                note.setTotalbillAmount(printNoteList.get(i).getTotalAmount());
            }

            //计算单个账单的应收金额 -----xyx
            note.setPrice(printNoteList.get(i).getBillAmount().toString());
            notes.add(note);
            //应收总金额
            if (!StringUtil.isBlank(printNoteList.get(i).getTotalBillAmount())){
                totalAmount=totalAmount+ Double.valueOf(printNoteList.get(i).getTotalAmount());
                receivableTotalAmount=totalAmount;
                actualTotalAmount=totalAmount;

            }

            //滞纳金总金额
            if (!StringUtil.isBlank(printNoteList.get(i).getOverduefinePrice())){
                totalOverdueFinePrice=totalOverdueFinePrice+Double.valueOf(printNoteList.get(i).getOverduefinePrice());
            }
            printNote.setBillNo(printNoteList.get(i).getBillNo());
        }
        printNote.setNotes(notes);
        printNote.setTotalAmount(totalAmount+"");
        printNote.setReceivableTotalAmount(receivableTotalAmount+"");
        printNote.setActualTotalAmount(actualTotalAmount+"");
        printNote.setTotalOverdueFinePrice(totalOverdueFinePrice+"");
        return printNote;

    }

    @Override
    public List<Expenses> findExportExpensesByBillNos(String[] billNos) {
        List<Expenses> expensesList=expenseBillMapper.findExportExpensesByBillNos(billNos);
        return expensesList;
    }
}

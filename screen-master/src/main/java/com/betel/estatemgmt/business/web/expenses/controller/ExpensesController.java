package com.betel.estatemgmt.business.web.expenses.controller;

/**
 * Created by zhangjian on 2017/9/17.
 */

import com.betel.estatemgmt.business.web.expenses.code.ExpensesCode;
import com.betel.estatemgmt.business.web.expenses.constant.ExpenseStaticStatus;
import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.business.web.expenses.model.ExpensesReq;
import com.betel.estatemgmt.business.web.expenses.service.ExpensesService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Tool;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 费用设置接口
 * </p>
 * ClassName: ExpensesController <br/>
 * Author: zhangjian <br/>
 * Date:  2017/9/18<br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/expenses")
public class ExpensesController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ExpensesController.class);
    @Autowired
    private ExpensesService expensesService;


    /**
     * <p>
     * 查询费用列表
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("paymentManage-findAllExpenses")
    @RequestMapping(value = "v1/findAllExpenses", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<Expenses>> findAllExpenses(@RequestBody ExpensesReq expensesReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/findAllExpenses start========expensesReq=" + expensesReq);
        }
        Response<Paging<Expenses>> response = new Response<Paging<Expenses>>();
        try {
            //校验前端参数
            String code = expensesService.checkParamter(expensesReq);
            if (!"".equals(code) && null != code) {
                response.setCode(code);
                return response;
            }
            //去掉房号,客户姓名的前后空格
            if (!StringUtil.isEmpty(expensesReq.getHouseNum())) {
                expensesReq.setHouseNum(expensesReq.getHouseNum().trim());
            }
            if (!StringUtil.isEmpty(expensesReq.getHouseMaster())) {
                expensesReq.setHouseMaster(expensesReq.getHouseMaster().trim());
            }
            if (!StringUtil.isEmpty(expensesReq.getExportBillStartTime())) {
                expensesReq.setExportBillStartTime(expensesReq.getExportBillStartTime().trim() + " 00:00:00");
            }
            if (!StringUtil.isEmpty(expensesReq.getExportBillEndTime())) {
                expensesReq.setExportBillEndTime(expensesReq.getExportBillEndTime().trim() + " 23:59:59");
            }
            if(!StringUtil.isBlank(expensesReq.getHouseNum()) && expensesReq.getHouseNum().contains("_")){
                expensesReq.setHouseNum(expensesReq.getHouseNum().replace("_","\\_"));
            }
            if(!StringUtil.isBlank(expensesReq.getHouseMaster()) && expensesReq.getHouseMaster().contains("_")){
                expensesReq.setHouseMaster(expensesReq.getHouseMaster().replace("_","\\_"));
            }

            Paging<Expenses> pager = new Paging<Expenses>(expensesReq.getCurPage(), expensesReq.getPageSize());
            List<Expenses> list = expensesService.findAllExpenses(pager, expensesReq);
            pager.result(list);
            response.setData(pager);
        } catch (Exception e) {
            LOG.error("========查询费用列表报错========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/findAllExpenses end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 线下支付：生成订单
     * （批量查询代缴费详情）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("paymentManage-findPayDetail")
    @RequestMapping(value = "v1/findPayDetail", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Expenses> findPayDetail(@RequestBody ExpensesReq expensesReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/findPayDetail start========expensesReq=" + expensesReq);
        }
        Response<Expenses> response = new Response<Expenses>();
        try {
            //校验id
            String ids = expensesReq.getBillNos();
            String code = Tool.checkIdIsNull(ids);
            if (null == code || "".equals(code)) {
                response.setCode(ExpensesCode.BILL_ID_IS_NULL);
                return response;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------------------账单是否存在判断----------开始------------");
            }
            String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
            //查询帐单是否删除
            List<Expenses> expensesList = expensesService.findExpensesByBillNos(billNos);
            if (expensesList.size() == 0) {
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            }
            if (billNos.length > 1) {
                if (expensesList.size() < billNos.length) {//批量
                    response.setCode(ExpensesCode.CHOOSE_BILL_HAS_DELETE_PART_OF_ALL_BILL);
                    return response;
                }
            } else if (billNos.length == 1 && expensesList.size() == 0) {//单个
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("-------------------------账单是否存在判断----------结束------------");
            }
            //判断账单是否是同一个房屋的
            code = expensesService.testFindPayDetailParamter(expensesReq);
            if (null != code && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
            //===================判断该选中的账单是否绑定流水号===================================

            code = expensesService.checkExpenseBillisHasBeenBoundStreamNumber(expensesReq);
            if (null != code && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
        } catch (Exception e) {
            LOG.error("----------------------检查选中账单是否已经绑定流水号-----------报错-----------------------", e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        try {
            expensesService.payUnderWebLine(expensesReq, response, 1,request);
        } catch (Exception e) {
            LOG.error("------------------查询代缴费详情（批量）---报错-----------------", e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/findPayDetail end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 线下支付：修改账单缴费状态，缴费方式（批量）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("paymentManage-updateBill")
    @RequestMapping(value = "v1/updateBill", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Expenses> updateBill(@RequestBody ExpensesReq expensesReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/updateBill start========expensesReq=" + expensesReq);
        }
        Response<Expenses> response = new Response<Expenses>();
        if (LOG.isDebugEnabled()) {
            LOG.debug("-------------------------校验账单id--------------------------------" + expensesReq.getBillNos());
        }
        String ids = expensesReq.getBillNos();
        String code = Tool.checkIdIsNull(ids);
        if (null == code || "".equals(code)) {
            response.setCode(ExpensesCode.BILL_ID_IS_NULL);
            return response;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------------------------------校验收费方式合法性---------------------------------------------");
        }
        if (StringUtil.isBlank(expensesReq.getChargeWays())) {
            response.setCode(ExpensesCode.PAYMENT_MENTHOD_SYSTEM_DOES_NOT_EXIST);
            return response;
        } else {
            boolean flag1 = expensesReq.getChargeWays().trim().equals(ExpensesCode.PAYMENT_IN_CASH);
            boolean flag2 = expensesReq.getChargeWays().trim().equals(ExpensesCode.PAYMENT_IN_CARD);
            boolean flag3 = expensesReq.getChargeWays().trim().equals(ExpensesCode.PAYMENT_IN_ALIPAY);
            boolean flag4 = expensesReq.getChargeWays().trim().equals(ExpensesCode.PAYMENT_IN_WEIXIN);
            if (flag1 == false && flag2 == false && flag3 == false && flag4 == false) {
                response.setCode(ExpensesCode.PAYMENT_MENTHOD_SYSTEM_DOES_NOT_EXIST);
                return response;
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("---------------------------账单是否存在判断---------------------开始-----------------------");
        }
        String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
        //查询帐单是否删除
        try {
            List<Expenses> expensesList = expensesService.findExpensesByBillNos(billNos);
            if (expensesList.size() == 0) {
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            }
            if (billNos.length > 1) {
                if (expensesList.size() < billNos.length) {//批量
                    response.setCode(ExpensesCode.CHOOSE_BILL_HAS_DELETE_PART_OF_ALL_BILL);
                    return response;
                }
            } else if (billNos.length == 1 && expensesList.size() == 0) {//单个
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("------------------------账单是否存在判断--------------------------------结束----------------------");
            }
        } catch (Exception e) {
            LOG.error("-------------------------------账单是否存在判断--------报错报错----------", e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("----------------------------判断账单是否是同一个房屋的,是否全部欠缴费------------------------");
            }
            code = expensesService.testFindPayDetailParamter(expensesReq);
            if (null != code && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
        } catch (Exception e) {
            LOG.error("-------------------判断账单是否是同一个房屋的,是否全部欠缴费---------------报错------------------", e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }

        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("-----------------------------------判断流水账单是否已经过期----------------------------------");
            }
            code = expensesService.checkExpenseBillisOver(expensesReq);
            if (null != code && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
        } catch (Exception e) {
            LOG.error("-----------------------------------查询代缴费详情（批量）-----------------------------------" + e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------------支付----------开始-----------------修改缴费流水状态------------------------");
        }
        try {
            expensesService.payUnderWebLine(expensesReq, response, 2,request);
            if (LOG.isDebugEnabled()) {
                LOG.debug("--------------支付---------------结束------------------修改缴费流水状态:3------------更新账单的数量----------" + response.getData());
            }
        } catch (Exception e) {
            LOG.error("------------------查询代缴费详情（批量）-------------报错--------", e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("----------------------------------------------整个缴费过程全部结束----------------------------------------------");
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/updateBill end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 发送催缴短信（支持批量）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("paymentManage-sendReminderMessage")
    @RequestMapping(value = "v1/sendReminderMessage", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> sendReminderMessage(@RequestBody ExpensesReq expensesReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/sendReminderMessage start========expensesReq=" + expensesReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //校验前端参数
            String ids = expensesReq.getBillNos();
            String code = Tool.checkIdIsNull(ids);
            if (null == code || "".equals(code)) {
                response.setCode(ExpensesCode.BILL_ID_IS_NULL);
                return response;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("---------------------------------------判断账单是否存在");
            }
            //查询数据库获得催缴信息:1.房屋信息，户主，手机号
            String[] billNos = Tool.getIdArrOfStringType(expensesReq.getBillNos());
            List<Expenses> expensesList = expensesService.findExpensesByBillNos(billNos);
            //判断账单是否已经缴费----xyx
            if (expensesList != null && expensesList.size() > 0) {
                for (Expenses e :
                        expensesList) {
                    if (ExpenseStaticStatus.TYPE_TWO.equals(e.getBillStatus())) {
                        response.setCode(ExpensesCode.CHOOSE_BILL_HAS_PART_PAY);
                        return response;
                    }
                }
            }
            if (expensesList.size() == 0) {
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            }
            if (billNos.length > 1) {
                if (expensesList.size() < billNos.length) {//批量
                    response.setCode(ExpensesCode.CHOOSE_BILL_HAS_DELETE_PART_OF_ALL_BILL);
                    return response;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("---------------------------------------判断账单的户主是否存在");
                }
                for (int i = 0; i < expensesList.size(); i++) {
                    String houseMaster = expensesList.get(i).getHouseMaster();//户主
                    if (null == houseMaster || "".equals(houseMaster)) {
                        response.setCode(ExpensesCode.HOUSE_MASTER_NOT_EXIST_PART_OF_CHOOSE_BILL);
                        return response;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("---------------------------------------判断账单的是否逾期");
                    }
                    String overTime = expensesList.get(i).getDeadLine();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date overDate = sdf.parse(overTime);
                        String overDateStr = sdf.format(overDate) + " 23:59:59";
                        Date date = sdf.parse(overDateStr);
                        Long tempDate = date.getTime();
                        if (tempDate > System.currentTimeMillis()) {
                            response.setCode(ExpensesCode.CHOOSE_BILL_HAS_OVER_PART_OF_BILL);
                            return response;
                        }

                    } catch (ParseException e) {
                        LOG.error("-------------时间转换异常------", e);
                    }
                }

            } else if (billNos.length == 1 && expensesList.size() == 0) {//单个
                if (LOG.isDebugEnabled()) {
                    LOG.debug("---------------------------------------判断账单的户主是否存在");
                }
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            } else if (billNos.length == 1) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("---------------------------------------判断账单的户主是否存在");
                }
                String houseMaster = expensesList.get(0).getHouseMaster();
                if (null == houseMaster || "".equals(houseMaster)) {
                    response.setCode(ExpensesCode.HOUSE_MASTER_NOT_EXIST_OF_CHOOSE_BILL);
                    return response;
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("---------------------------------------判断账单的是否逾期");
                }
                String overTime = expensesList.get(0).getDeadLine();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date overDate = sdf.parse(overTime);
                    String overDateStr = sdf.format(overDate) + " 23:59:59";
                    Date date = sdf.parse(overDateStr);
                    Long tempDate = date.getTime();
                    if (tempDate > System.currentTimeMillis()) {
                        response.setCode(ExpensesCode.CHOOSE_BILL_HAS_OVER_OF_ALL_BILL);
                        return response;
                    }

                } catch (ParseException e) {
                    LOG.error("-------------时间转换异常------", e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
            }
            for (int i = 0; i < expensesList.size(); i++) {
                String houseMaster = expensesList.get(i).getHouseMaster();//户主
                if (null == houseMaster || "".equals(houseMaster)) {
                    response.setCode(ExpensesCode.HOUSE_MASTER_NOT_EXIST_OF_CHOOSE_BILL);
                    return response;
                }
            }
            int flag = expensesService.sendReminderMessage(expensesReq,request);
            if (LOG.isDebugEnabled()) {
                {
                    LOG.debug("发送催缴对象成功,共发送" + flag + "条短信");
                }
            }
        } catch (Exception e) {
            LOG.error("========发送催缴对象失败报错========", e);
            response.setCode(StatusCode.FAILURE);
        }


        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/sendReminderMessage end========response" + response);
        }
        return response;
    }
}

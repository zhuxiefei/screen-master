package com.betel.estatemgmt.business.web.expenses.controller;

import com.betel.estatemgmt.business.web.expenses.code.ExpensesCode;
import com.betel.estatemgmt.business.web.expenses.model.Expenses;
import com.betel.estatemgmt.business.web.expenses.model.ExpensesReq;
import com.betel.estatemgmt.business.web.expenses.model.PrintNote;
import com.betel.estatemgmt.business.web.expenses.service.ExpensesService;
import com.betel.estatemgmt.business.web.expenses.service.ExportPrintService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Tool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 导出打印账单接口
 * </p>
 * ClassName: ExportPrintBilController <br/>
 * Author: zhangjian <br/>
 * Date:  2017/9/18<br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/expenses")
public class ExportPrintBilController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ExportPrintBilController.class);
    @Autowired
    private ExportPrintService exportPrintService;
    @Autowired
    private ExpensesService expensesService;

    /**
     * <p>
     * 导出费用列表
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("paymentManage-exportBill")
    @RequestMapping(value = "v1/exportBill", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> exportBill(@RequestBody ExpensesReq expensesReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/exportBill start========expensesReq=" + expensesReq);
        }
        Response<Object> response = new Response<Object>();
        try {
            //校验id
            String ids = expensesReq.getBillNos();
            String code = Tool.checkIdIsNull(ids);
            boolean flag = false;
            if (null == code || "".equals(code)) {
                //校验前端参数
                code = expensesService.checkParamter(expensesReq);
                if (!"".equals(code) && null != code) {
                    response.setCode(code);
                    return response;
                }
            }
            if (StringUtil.isBlank(expensesReq.getBillNos())) {
                flag = true;
            }
            List<Expenses> list = exportPrintService.exportBill(expensesReq, flag);
            if (LOG.isDebugEnabled()) {
                LOG.debug("======导出查询费用列表=====" + list);
            }
            response.setData(list);
        } catch (Exception e) {
            LOG.error("========导出查询费用列表报错========", e);
            response.setCode(StatusCode.FAILURE);
        }


        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/exportBill end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 打印已缴费收据（批量）
     * </p>
     * Author: zhangjian <br/>
     * Date: 2017/09/18
     *
     * @return response
     */
    @RequiresPermissions("paymentManage-printReceipt")
    @RequestMapping(value = "v1/printReceipt", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<PrintNote> printReceipt(@RequestBody ExpensesReq expensesReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/printReceipt start========expensesReq=" + expensesReq);
        }
        Response<PrintNote> response = new Response<PrintNote>();
        try {
            //校验id
            String ids = expensesReq.getBillNos();
            String code = Tool.checkIdIsNull(ids);
            if (null == code || "".equals(code)) {
                response.setCode(ExpensesCode.BILL_ID_IS_NULL);
                return response;
            }
            //判断账单是否是同一个房屋的,是否全部是已缴费
            code = expensesService.testFindPayDetailParamter(expensesReq);
            if (null != code && !"".equals(code)) {
                response.setCode(code);
                return response;
            }
            //=======是否全部是已缴费???????????????????


            PrintNote printNote = exportPrintService.printReceipt(expensesReq);
            if (printNote == null) {
                response.setCode(ExpensesCode.BILL_IS_NOT_EXIST);
                return response;
            }
            response.setData(printNote);
        } catch (Exception e) {
            LOG.error("-----------------------------------------------------查询已缴费详情（批量）----------------------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/expenses/v1/printReceipt end========response" + response);
        }
        return response;
    }

}

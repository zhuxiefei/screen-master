package com.betel.estatemgmt.business.web.income.controller;

import com.betel.estatemgmt.business.web.building.code.BuildingCode;
import com.betel.estatemgmt.business.web.building.model.BuildingInfo;
import com.betel.estatemgmt.business.web.building.model.UnitInfo;
import com.betel.estatemgmt.business.web.building.service.BuildingService;
import com.betel.estatemgmt.business.web.income.code.IncomeCode;
import com.betel.estatemgmt.business.web.income.constant.IncomeDataValidation;
import com.betel.estatemgmt.business.web.income.model.ExportReq;
import com.betel.estatemgmt.business.web.income.model.Income;
import com.betel.estatemgmt.business.web.income.model.IncomePageReq;
import com.betel.estatemgmt.business.web.income.service.IncomeService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
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
import java.util.List;

/**
 * <p>
 * 收入明细接口
 * </p>
 * ClassName: IncomeController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 11:56 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/income")
public class IncomeController extends BaseController {
    @Autowired
    private IncomeService incomeService;

    @Autowired
    private BuildingService buildingService;

    private static final Logger LOG = LoggerFactory.getLogger(IncomeController.class);

    /**
     * <p>
     * 分页查询收入明细列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 10:01
     * @param incomePageReq 入参
     * @return reponse 回参
     *
     * */
    @RequiresPermissions(value = "income-findAllIncome")
    @RequestMapping(value = "v1/findAllIncome", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<Income>> findAllIncome(@RequestBody IncomePageReq incomePageReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findAllIncome start========incomePageReq=" + incomePageReq);
        }
        Response<Paging<Income>> response = new Response<>();
        if(incomePageReq.getBuildingId() != null && buildingService.selectByPrimaryKey(incomePageReq.getBuildingId()) == null){
            response.setCode(IncomeCode.BUILDING_IS_DELETE);
        }else if (incomePageReq.getUnitId() != null && buildingService.selectBuildingUnitByUnitId(incomePageReq.getUnitId()) == null){
            response.setCode(IncomeCode.UNIT_IS_DELETE);
        }else if (!StringUtil.isBlank(incomePageReq.getUserName())
                && !incomePageReq.getUserName().trim().matches(IncomeDataValidation.USERNAME_RULE)){
            response.setCode(IncomeCode.USERNAME_RULE_ERROR);
        }else if (!StringUtil.isBlank(incomePageReq.getHouseNum())
                && !incomePageReq.getHouseNum().trim().matches(IncomeDataValidation.HOUSENUM_RULE)){
            response.setCode(IncomeCode.HOUSENUM_RULE_ERROR);
        }else if (!StringUtil.isBlank(incomePageReq.getStartTime()) && !validateTime(incomePageReq.getStartTime().trim())){
            response.setCode(IncomeCode.TIMR_RULE_ERROR);
        }else if (!StringUtil.isBlank(incomePageReq.getEndTime()) && !validateTime(incomePageReq.getEndTime().trim())){
            response.setCode(IncomeCode.TIMR_RULE_ERROR);
        }else if (!StringUtil.isBlank(incomePageReq.getStartTime()) && !StringUtil.isBlank(incomePageReq.getEndTime())
                && validateTime(incomePageReq.getStartTime().trim()) && validateTime(incomePageReq.getEndTime().trim())
                && !validateTimeDistance(incomePageReq.getStartTime(),incomePageReq.getEndTime())){
            response.setCode(IncomeCode.STARTTIME_LATE_ENDTIME);
        }else {
            Paging<Income> pager = new Paging<>(incomePageReq.getCurPage(), incomePageReq.getPageSize());
            //前后去空格
            if (!StringUtil.isEmpty(incomePageReq.getStartTime())){
                incomePageReq.setStartTime(incomePageReq.getStartTime().trim());
            }
            if (!StringUtil.isEmpty(incomePageReq.getHouseNum())){
                incomePageReq.setHouseNum(incomePageReq.getHouseNum().trim());
            }
            if (!StringUtil.isEmpty(incomePageReq.getUserName())){
                incomePageReq.setUserName(incomePageReq.getUserName().trim());
            }
            if (!StringUtil.isEmpty(incomePageReq.getEndTime())){
                incomePageReq.setEndTime(incomePageReq.getEndTime().trim());
            }
            if(!StringUtil.isBlank(incomePageReq.getHouseNum()) && incomePageReq.getHouseNum().contains("_")){
                incomePageReq.setHouseNum(incomePageReq.getHouseNum().replace("_","\\_"));
            }
            if(!StringUtil.isBlank(incomePageReq.getUserName()) && incomePageReq.getUserName().contains("_")){
                incomePageReq.setUserName(incomePageReq.getUserName().replace("_","\\_"));
            }
            try {
                //查询明细
                List<Income> incomeList = incomeService.findAllIncome(pager,incomePageReq);
                pager.result(incomeList);
                response.setData(pager);
            } catch (Exception e) {
                LOG.error("========web/income/v1/findAllIncome error!=========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findAllIncome end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询所有楼宇
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 14:15
     * @param
     * @return response
     *
     * */
    @RequiresPermissions(value = "income-findBuildingList")
    @RequestMapping(value = "v1/findBuildingList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<BuildingInfo>> findBuildingList(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findBuildingList start========");
        }
        Response<List<BuildingInfo>> response = new Response<>();
        try {
            //查询所有楼宇
            List<BuildingInfo> buildingInfoList = buildingService.findBuildingList(AESUtil.decrypt(request.getHeader("estateId")));
            response.setData(buildingInfoList);
        } catch (Exception e) {
            LOG.error("========web/income/v1/findBuildingList error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findBuildingList end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查所有单元
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 14:22
     * @param building
     * @return response
     *
     * */
    @RequiresPermissions(value = "income-findUnitList")
    @RequestMapping(value = "v1/findUnitList", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<UnitInfo>> findUnitList(@RequestBody Building building) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findUnitList start========building" + building);
        }
        Response<List<UnitInfo>> response = new Response<>();
        try {
            if (null != building.getBuildingId()) {
                //根据楼宇id查楼宇 不为空则查所有单元
                Building currentBuilding = buildingService.selectByPrimaryKey(building.getBuildingId());
                if (currentBuilding == null) {
                    response.setCode(BuildingCode.BUILDING_DELETED);
                }else {
                    //查所有单元
                    List<UnitInfo> unitInfoList = buildingService.findUnitList(building.getBuildingId());
                    response.setData(unitInfoList);
                }
            }
        } catch (Exception e) {
            LOG.error("========web/income/v1/findUnitList error========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findUnitList end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 查询导出收入明细列表
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/15 10:01
     * @param incomePageReq 入参
     * @return reponse 回参
     *
     * */
    @RequiresPermissions(value = "income-findExportIncome")
    @RequestMapping(value = "v1/findExportIncome", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<Income>> findExportIncome(@RequestBody ExportReq incomePageReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findExportIncome start========incomePageReq=" + incomePageReq);
        }
        Response<List<Income>> response = new Response<>();
        try {
            //前后去空格
            if (!StringUtil.isEmpty(incomePageReq.getStartTime())){
                incomePageReq.setStartTime(incomePageReq.getStartTime().trim());
            }
            if (!StringUtil.isEmpty(incomePageReq.getHouseNum())){
                incomePageReq.setHouseNum(incomePageReq.getHouseNum().trim());
            }
            if (!StringUtil.isEmpty(incomePageReq.getUserName())){
                incomePageReq.setUserName(incomePageReq.getUserName().trim());
            }
            if (!StringUtil.isEmpty(incomePageReq.getEndTime())){
                incomePageReq.setEndTime(incomePageReq.getEndTime().trim());
            }
            //查询明细
            List<Income> incomeList = incomeService.findExportIncomes(incomePageReq);
            response.setData(incomeList);
        } catch (Exception e) {
            LOG.error("========web/income/v1/findExportIncome error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/income/v1/findExportIncome end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    private boolean validateTime(String time){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
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
    private boolean validateTimeDistance(String startTime,String endTime){
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time1 = format.parse(startTime).getTime();
            long time2 = format.parse(endTime).getTime();
            if (time1 > time2){
                flag = false;
            }
        } catch (ParseException e) {}
        return flag;
    }
}

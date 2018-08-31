package com.betel.estatemgmt.business.web.income.service.impl;

import com.betel.estatemgmt.business.web.income.model.ExportReq;
import com.betel.estatemgmt.business.web.income.model.Income;
import com.betel.estatemgmt.business.web.income.model.IncomePageReq;
import com.betel.estatemgmt.business.web.income.service.IncomeService;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 收入明细接口实现类
 * </p>
 * ClassName: IncomeServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/13 11:50 <br/>
 * Version: 1.0 <br/>
 */
@Service("IncomeService")
@Transactional
public class IncomeServiceImpl implements IncomeService {
    private static final Logger LOG = LoggerFactory.getLogger(IncomeServiceImpl.class);

    @Autowired
    private ExpenseBillMapper expenseBillMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BuildingUnitMapper unitMapper;

    @Override
    public List<Income> findAllIncome(Paging<Income> pager, IncomePageReq incomePageReq) {
        if (LOG.isDebugEnabled()){
            LOG.debug("=========IncomeServiceImpl findAllIncome start=========Paging<Income>=" + pager
                    + ",IncomePageReq=" + incomePageReq);
        }
        List<Income> list = expenseBillMapper.findAllIncome(pager.getRowBounds(),incomePageReq);
        //添加房屋信息
        if (list != null && list.size() > 0){
            for (Income income:
                 list) {
                if (!StringUtil.isBlank(income.getHouseNo())){
                    income.setHouseNo(makeHouseNo(income.getHouseNo()));
                }
            }
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("=========IncomeServiceImpl findAllIncome end=========List<Income>=" + list);
        }
        return list;
    }

    @Override
    public List<Income> findExportIncomes(ExportReq incomePageReq) {
        if (LOG.isDebugEnabled()){
            LOG.debug("=========IncomeServiceImpl findExportIncomes start=========IncomePageReq=" + incomePageReq);
        }
        List<Income> list = expenseBillMapper.findExportIncomes(incomePageReq);
        //添加房屋信息
        if (list != null && list.size() > 0){
            for (Income income:
                    list) {
                if (!StringUtil.isBlank(income.getHouseNo())){
                    income.setHouseNo(makeHouseNo(income.getHouseNo()));
                }
            }
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("=========IncomeServiceImpl findExportIncomes end=========list=" + list);
        }
        return list;
    }

    //生成房屋信息
    private String makeHouseNo(String houseId){
        House house = houseMapper.selectByPrimaryKey(houseId);
        StringBuffer houseNo = new StringBuffer();
        if (house.getBuildingId() != null){
            Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
            if (building != null){
                houseNo.append(building.getBuildingName());
            }
        }
        if (house.getUnitId() != null){
            BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
            if (unit != null){
                houseNo.append(unit.getUnitName());
            }
        }
        houseNo.append(house.getHouseNum());
        return houseNo.toString();
    }
}

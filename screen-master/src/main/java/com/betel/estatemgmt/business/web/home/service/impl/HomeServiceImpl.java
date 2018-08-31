package com.betel.estatemgmt.business.web.home.service.impl;

import com.betel.estatemgmt.business.web.home.constant.HomeValidation;
import com.betel.estatemgmt.business.web.home.model.HomePage;
import com.betel.estatemgmt.business.web.home.service.HomeService;
import com.betel.estatemgmt.business.web.login.model.Menu;
import com.betel.estatemgmt.common.mapper.expense.ExpenseBillMapper;
import com.betel.estatemgmt.common.mapper.repair.RepairOrderMapper;
import com.betel.estatemgmt.common.model.expense.ExpenseBill;
import com.betel.estatemgmt.shiro.ActiveUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjian on 2017/8/7.
 */
@Service
@Transactional
public class HomeServiceImpl implements HomeService {
    private static final Logger LOG = LoggerFactory.getLogger(HomeServiceImpl.class);

    @Autowired
    private RepairOrderMapper repairOrderMapper;
    @Autowired
    private ExpenseBillMapper expenseBillMapper;

    @Override
    public HomePage findHomePageNew() {
        List<String> menuIds = new ArrayList<>();
        HomePage homePage = new HomePage();
        try {
            Subject subject = SecurityUtils.getSubject();
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            if (null != activeUser) {
                List<Menu> menus = activeUser.getMenus();
                for (int i = 0; i < menus.size(); i++) {
                    menuIds.add(menus.get(i).getMenuId());
                }
            }
        } catch (Exception e) {
            LOG.error("========web/首页鉴权查询菜单报错========" + e);

        }
        //查询数据库
        Map<String, Long> homeCount = new HashMap<>();
        try {
            homeCount = repairOrderMapper.findHomePageNew();
        } catch (Exception e) {
            LOG.error("========web/==首页==findHomePageNew====" + e);
        }
        Map<String, Object> taget = new HashMap<>();
        boolean repairAuth = false;
        boolean expenserbill = false;
        for (int i = 0; i < menuIds.size(); i++) {
            if ("241".equals(menuIds.get(i))) {
                repairAuth = true;
                continue;
            }
            if ("203".equals(menuIds.get(i))) {
                expenserbill = true;
                continue;
            }
        }
        // ==================================举报处理====================开始=============================
        Long j = 0L;
        if (true == repairAuth) {
            j = homeCount.get("maintainToAudit");
            homePage.setMaintainToAudit(j + "");
        }
        //收据数
        List<ExpenseBill> sendReceipt = expenseBillMapper.findSendReceipt(String.valueOf(HomeValidation.QUANTITY_TWO));
        if (true == expenserbill) {
            for (int z = 0; z < menuIds.size(); z++) {
                if ("204".equals(menuIds.get(z))) {
                    homePage.setSendReceipt(String.valueOf(sendReceipt.size()));
                    continue;
                }
            }
        }
        // ==================================待维修====================结束=============================
        return homePage;
    }

}

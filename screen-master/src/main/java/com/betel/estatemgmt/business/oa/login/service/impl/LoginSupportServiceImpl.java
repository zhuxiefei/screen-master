package com.betel.estatemgmt.business.oa.login.service.impl;

import com.betel.estatemgmt.business.oa.login.service.LoginSupportService;
import com.betel.estatemgmt.common.mapper.estate.EstateCityMapper;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.estate.EstateCity;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2018/5/7/007.
 */
@Service("LoginSupportService")
@Transactional(rollbackFor = Exception.class)
public class LoginSupportServiceImpl implements LoginSupportService {

    @Autowired
    private EstateCityMapper estateCityMapper;

    @Autowired
    private EstateMapper estateMapper;

    @Override
    public List<EstateCity> findByEstateIds(String estateIds,String cityName) {
        String[] estateIdArray = estateIds.split(",");
        List<EstateCity> list = estateCityMapper.findByEstateIds(estateIdArray,cityName);
        if (null != list && list.size() > 0){
            //去重
            removeDuplicateWithOrder(list);
            return list;
        }
        return null;
    }

    // 删除ArrayList中重复元素，保持顺序
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
    }

    @Override
    public List<Estate> findEstateByEstateIds(String estateIds,String cityId) throws Exception{
        String[] estateIdArray = estateIds.split(",");
        List<Estate> list = estateMapper.findByEstateIds(estateIdArray,cityId);
        if (null != list && list.size() > 0){
            for (Estate e:
                 list) {
                e.setEstateId(AESUtil.encrypt(e.getEstateId()));
            }
        }
        return list;
    }
}

package com.betel.estatemgmt.business.userapp.hotline.service.impl;

import com.betel.estatemgmt.business.userapp.hotline.model.Hotline;
import com.betel.estatemgmt.business.userapp.hotline.service.HotlineService;
import com.betel.estatemgmt.common.mapper.property.PropertyHotlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: HotlineServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/11/10 16:49 <br/>
 * Version: 1.0 <br/>
 */
@Transactional
@Service
public class HotlineServiceImpl implements HotlineService {

    @Autowired
    private PropertyHotlineMapper hotlineMapper;

    @Override
    public List<Hotline> findHotlines() {
        return hotlineMapper.findHotlines();
    }
}

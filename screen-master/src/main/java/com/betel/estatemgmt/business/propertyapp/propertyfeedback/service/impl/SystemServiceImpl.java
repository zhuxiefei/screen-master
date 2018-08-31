package com.betel.estatemgmt.business.propertyapp.propertyfeedback.service.impl;

import com.betel.estatemgmt.business.propertyapp.propertyfeedback.service.SystemService;
import com.betel.estatemgmt.common.mapper.system.FeedbackMapper;
import com.betel.estatemgmt.common.model.system.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.betel.smartre.userfeedback.mapper.admin.TokenMapper;

/**
 * <p>
 * 系统操作业务接口实现类
 * </p>
 * ClassName: SystemServiceImpl <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 15:13 <br/>
 * Version: 1.0 <br/>
 */
@Service("PropertySystemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public int addFeedback(Feedback feedback) {
        return feedbackMapper.insertSelective(feedback);
    }
}

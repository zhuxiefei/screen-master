package com.betel.estatemgmt.business.userapp.userfeedback.service;

import com.betel.estatemgmt.common.model.system.Feedback;

/**
 * <p>
 * 系统操作业务接口
 * </p>
 * ClassName: SystemService <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/17 15:12 <br/>
 * Version: 1.0 <br/>
 */
public interface SystemService {

    /**
     * <p>
     * 保存反馈意见
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 19:08
     *
     * @param feedback 反馈意见
     * @return
     */
    int addFeedback(Feedback feedback);

//    /**
//     * <p>
//     * 删除token信息
//     * </p>
//     * Author: xiayanxin <br/>
//     * Date: 2017/5/15 19:08
//     *
//     * @param userId 用户编号
//     * @return
//     */
//    int deleteTokenByUserId(String userId);
}

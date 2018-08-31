package com.betel.estatemgmt.common.mapper.house;

import com.betel.estatemgmt.business.userapp.house.model.ShareURL;
import com.betel.estatemgmt.common.model.house.HousePictureShare;

import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: HousePicShareMapper <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/24 9:48 <br/>
 * Version: 1.0 <br/>
 */
public interface HousePicShareMapper {
    /**
     * <p>
     *添加分享记录
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 11:36
     *
     */
    void insertHousePicShare(HousePictureShare housePictureShare);
    /**
     * <p>
     * 查询过期记录
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 11:36
     *return response
     */
    List<ShareURL> selectShareOutOfTime();

    /**
     * <p>
     *查询
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 12:04
     *return response
     */
    ShareURL selectShareByShareId(String shareId);
}

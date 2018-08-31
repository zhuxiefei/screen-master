package com.betel.estatemgmt.business.web.ad.service;

import com.betel.estatemgmt.business.web.ad.model.*;
import com.betel.estatemgmt.common.model.ad.Advertisement;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 关于广告相关操作的接口
 * </p>
 * ClassName: AdService <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/5/15 14:21 <br/>
 * Version: 1.0 <br/>
 */
public interface AdService {

    /**
     * <p>
     * 新增广告
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 15:08
     *
     * @param advertisement 广告
     * @return 新增结果
     */
    void addAd(Advertisement advertisement, AddAdInfo adInfo);

    /**
     * <p>
     * 查询广告列表（分页）
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 15:18
     *
     * @param pager 分页
     * @param page 关键词
     * @return 广告列表
     */
    List<AdInfo> findAllAds(Paging<AdInfo> pager, AdPage page);

    /**
     * <p>
     * 根据广告编号删除广告（支持批量）
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 15:20
     *
     * @param adIds 广告编号数组
     * @return 删除结果
     */
    String deleteAdByAdIds(String adIds);

    /**
     * <p>
     * 修改广告
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 15:22
     *
     * @param advertisement 广告
     * @return 修改结果
     */
    void updateAd(Advertisement advertisement, UpdateAdInfo adInfo);

    /**
     * <p>
     * 根据主键查询
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 15:24
     *
     * @param adId 广告编号
     * @return 设置结果
     */
    Advertisement findByPrimaryKey(Long adId);

    /**
     * <p>
     * 根据广告编号查询广告详情
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 16:20
     *
     * @param adId 广告编号
     * @return 广告详情
     */
    AdDetail findByAdId(Long adId);

    /**
     * <p>
     * 添加图片
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 16:20
     *
     * @param picture 图片
     * @return 新增结果
     */
    String addPicture(Picture picture);

    /**
     * <p>
     * 根据图片编号查询
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 16:20
     *
     * @param pictureId 图片编号
     * @return 新增结果
     */
    Pic findByPictureId(String pictureId);
}

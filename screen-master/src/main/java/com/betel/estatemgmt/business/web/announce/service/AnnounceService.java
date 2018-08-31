package com.betel.estatemgmt.business.web.announce.service;

import com.betel.estatemgmt.business.web.announce.model.AnnounceInfo;
import com.betel.estatemgmt.business.web.announce.model.AnnouncePage;
import com.betel.estatemgmt.business.web.announce.model.AnnouncePoint;
import com.betel.estatemgmt.common.model.announce.Announce;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 关于公告相关操作的接口
 * </p>
 * ClassName: AnnounceService <br/>
 * Author: xiayanxin  <br/>
 * Date: 2017/5/15 16:04 <br/>
 * Version: 1.0
 */
public interface AnnounceService {

    /**
     * <p>
     * 新增公告
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 16:11
     *
     * @param announce 公告
     * @return 新增结果
     */
    Long addAnnounce(Announce announce);

    /**
     * <p>
     * 搜索查询公告列表（分页）
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 16:14
     *
     * @param page 关键词
     * @param pager 分页
     * @return 公告列表
     */
    List<AnnouncePoint> findAllAnnounce(Paging<AnnouncePoint> pager, AnnouncePage page);

    /**
     * <p>
     * 删除公告（支持批量）
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/5/15 16:15
     *
     * @param announceIds 公告编号数组
     * @return 删除结果
     */
    String deleteAnnounce(String announceIds);

    /**
     * <p>
     * 查看公告详情
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 16:17
     *
     * @param announceId 公告编号
     * @return 公告详细信息
     */
    AnnounceInfo findAnnounce(Long announceId);

    /**
     * <p>
     * 修改公告
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 16:18
     *
     * @param announce 公告
     * @return 修改结果
     */
    String updateAnnounce(Announce announce);

    /**
     * * <p>
     * 公告全站发送
     * </p>
     * Author: XYX <br/>
     * Date: 2017/5/15 16:18
     *
     * @param announceId 公告编号
     * @return
     */
    String pushAnnounce(Long announceId);

    /**
     * 查询置顶公告
     * @return
     */
    List<Announce> findTopAnnounces();

    /**
     * 根据公告ID查公告
     * @param id
     * @return
     */
    Announce findByAnnounceId(Long id);

    /**
     * 设置公告置顶
     * @param announce
     */
    void setAnnounceTop(Announce announce);

    /**
     * 生成贝特小贴
     */
    void createBetelTip();
}

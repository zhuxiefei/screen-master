package com.betel.estatemgmt.business.userapp.index.service;

import com.betel.estatemgmt.business.userapp.index.model.SystemAnnounce;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 公告业务接口
 * </p>
 * ClassName: AnnounceService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/15 15:30 <br/>
 * Version: 1.0 <br/>
 */
public interface AnnounceService {
/**
 * <p>
 * 搜索选中的公告详情
 * </p>
 * Author: zhouye <br/>
 * Date: 2017/5/15 15:34
 *return SystemAnnounce
 */
    SystemAnnounce findAnnounce(Long announceId);

    /**
     * <p>
     * 分页查询公告列表
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/15 15:36
     *return response
     */
    List<SystemAnnounce> findAllAnnounce(Paging<SystemAnnounce> page);

    /**
     * <p>
     * 查询公告总数
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/15 18:38
     *return response
     */
    Integer announceTotal();
}

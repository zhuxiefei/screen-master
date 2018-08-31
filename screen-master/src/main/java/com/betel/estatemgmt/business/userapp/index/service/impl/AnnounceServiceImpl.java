package com.betel.estatemgmt.business.userapp.index.service.impl;

import com.betel.estatemgmt.business.userapp.index.model.SystemAnnounce;
import com.betel.estatemgmt.business.userapp.index.service.AnnounceService;
import com.betel.estatemgmt.common.mapper.announce.AnnounceMapper;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * AnnounceService实现类
 * </p>
 * ClassName: AnnounceServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/5/15 15:46 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class AnnounceServiceImpl implements AnnounceService{
    @Autowired
    AnnounceMapper announceMapper;
    /**
     * <p>
     * 查询公告信息.
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/17 10:58
     *return response
     */
    @Override
    public SystemAnnounce findAnnounce(Long announceId) {
        return announceMapper.selAnnounce(announceId);
    }

    /**
     * <p>
     * 分页查询所有公告.
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/17 10:58
     *return response
     */
    @Override
    public List<SystemAnnounce> findAllAnnounce(Paging<SystemAnnounce> page) {
        return announceMapper.findAllAnnounces(page.getRowBounds());
    }
    /**
     * <p>
     * 查询公告总数
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/5/17 10:59
     *return response
     */
    @Override
    public Integer announceTotal() {
        return announceMapper.announceTotal();
    }
}

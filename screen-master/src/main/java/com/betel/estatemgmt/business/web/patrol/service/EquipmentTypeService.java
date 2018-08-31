package com.betel.estatemgmt.business.web.patrol.service;

import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * Created by zhangjian on 2018/1/24.
 */
public interface EquipmentTypeService {
    /**
     * 查询设备类型
     *
     * @param pager
     * @param patrolReq
     * @return
     */
    List<EquipmentType> findAllTypeList(Paging<EquipmentType> pager, PatrolReq patrolReq);

    /**
     * 更新
     *
     * @param patrolReq
     * @return
     */
    String updateType(PatrolReq patrolReq);

    /**
     * 新增
     *
     * @param patrolReq
     * @return
     */
    String addType(PatrolReq patrolReq);

    /**
     * 新增
     *
     * @param patrolReq
     * @return
     */
    String deleteType(PatrolReq patrolReq);

    /**
     * 查看
     *
     * @param patrolReq
     * @return
     */
    EquipmentType findType(PatrolReq patrolReq);
}

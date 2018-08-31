package com.betel.estatemgmt.business.userapp.house.service;

import com.betel.estatemgmt.business.userapp.house.model.Materials;
import com.betel.estatemgmt.business.userapp.house.model.Page;
import com.betel.estatemgmt.common.model.house.HouseMaterialParm;
import com.betel.estatemgmt.utils.pagination.model.Paging;

import java.util.List;

/**
 * <p>
 * 建材信息Services
 * </p>
 * ClassName: AppMaterialService <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/22 19:54 <br/>
 * Version: 1.0 <br/>
 */
public interface AppMaterialService {
    /**
     * <p>
     * 分页查询建材
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 19:55
     *return response
     */
    List<Materials> findAllMaterialByHouseId(Paging paging, Page page);

    /**
     * <p>
     * 查询材料详情
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 11:23
     *return response
     */
    Materials findMaterialBymaterialId(Long materialId);

    /**
     * <p>
     * 查询材料自定义参数
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 11:43
     *return response
     */
    List<HouseMaterialParm> finAllmaterialParmsBymaterialsId(Long materialId);
}

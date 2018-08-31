package com.betel.estatemgmt.business.web.patrol.controller;

import com.betel.estatemgmt.business.web.patrol.code.EquipmentCode;
import com.betel.estatemgmt.business.web.patrol.model.PatrolReq;
import com.betel.estatemgmt.business.web.patrol.service.EquipmentTypeService;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.patrol.EquipmentType;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.RegexRule;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.TimeUtils;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 *
 * @ClassName: EquipmentTypeComtroller <br/>
 * @author: jian.z  <br/>
 * @date: 2018/1/24 10:06 <br/>
 * @Version: 1.0
 */
@RestController
@RequestMapping("web/equipment")
public class EquipmentTypeController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(EquipmentTypeController.class);
    @Autowired
    private EquipmentTypeService equipmentTypeMapper;

    /**
     * 查询设施类型列表
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-findAllTypeList")
    @RequestMapping(value = "v1/findAllTypeList", method = RequestMethod.POST)
    public Response<Paging<EquipmentType>> findAllTypeList(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/findAllRecordList-------start,patrolReq=" + patrolReq);
        }
        Response<Paging<EquipmentType>> response = new Response<Paging<EquipmentType>>();
        try {
            Paging<EquipmentType> pager = new Paging<>(patrolReq.getCurPage(), patrolReq.getPageSize());
            //去空格再校验
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                patrolReq.setEstateId(estateId);
                String code = checkParm(trim(patrolReq));
                if (null == code) {
                    List<EquipmentType> list = equipmentTypeMapper.findAllTypeList(pager, patrolReq);
                    pager.result(list);
                    response.setData(pager);
                } else {
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("--------web/equipment/v1/findAllRecordList--------error--------", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("--------web/equipment/v1/findAllTypeList-------end,response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 添加类型
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/5 9:06
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-addType")
    @RequestMapping(value = "v1/addType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> addType(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/addType------start-" + patrolReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                patrolReq.setEstateId(estateId);
                response.setCode(equipmentTypeMapper.addType(patrolReq));
            }
        } catch (Exception e) {
            LOG.error("----web/equipment/v1/addType----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/addType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 修改
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 9:54
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-updateType")
    @RequestMapping(value = "v1/updateType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> updateType(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/updateType------start-" + patrolReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                patrolReq.setEstateId(estateId);
                response.setCode(equipmentTypeMapper.updateType(patrolReq));
            }
        } catch (Exception e) {
            LOG.error("----web/equipment/v1/updateType----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/updateType------end-" + response);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/updateType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除类型（批量）
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 10:02
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-deleteType")
    @RequestMapping(value = "v1/deleteType", method = RequestMethod.POST, consumes = "application/json")
    public Response<String> deleteType(@RequestBody PatrolReq patrolReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/deleteType------start-" + patrolReq);
        }
        Response<String> response = new Response<>();
        try {
            response.setCode(equipmentTypeMapper.deleteType(patrolReq));
            ;
        } catch (Exception e) {
            LOG.error("----web/equipment/v1/deleteType----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/deleteType------end-" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询根据设备名称查询类型列表（不分页）
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/12/21 9:54
     *
     * @param patrolReq
     * @return
     */
    @RequiresPermissions(value = "equipment-findType")
    @RequestMapping(value = "v1/findType", method = RequestMethod.POST, consumes = "application/json")
    public Response<EquipmentType> findType(@RequestBody PatrolReq patrolReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/findType------start-" + patrolReq);
        }
        Response<EquipmentType> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            if (StringUtil.isBlank(estateId)) {
                response.setCode(GlobalCode.ESTATE_ID_IS_NULL);
            } else {
                patrolReq.setEstateId(estateId);
                EquipmentType equipmentType = equipmentTypeMapper.findType(patrolReq);
                if (null == equipmentType) {
                    response.setCode(EquipmentCode.EQUIPMENT_TYPE_IS_NOT_EXIST);
                } else {
                    response.setData(equipmentType);
                }
            }

        } catch (Exception e) {
            LOG.error("----web/equipment/v1/findType----error---", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/findType------end-" + response);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("----------web/equipment/v1/findType------end-" + response);
        }
        return response;
    }


    private PatrolReq trim(PatrolReq patrolReq) {
        if (!StringUtil.isEmpty(patrolReq.getStartTime())) {
            patrolReq.setStartTime(patrolReq.getStartTime().trim());
        }
        if (!StringUtil.isEmpty(patrolReq.getEndTime())) {
            patrolReq.setEndTime(patrolReq.getEndTime().trim());
        }
        return patrolReq;
    }

    private String checkParm(PatrolReq patrolReq) {
        if (!StringUtil.isBlank(patrolReq.getStartTime())) {
            if (!patrolReq.getStartTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.START_TIME_RULE_ERROR;
            }
        }
        if (!StringUtil.isBlank(patrolReq.getEndTime())) {
            if (!patrolReq.getEndTime().matches(RegexRule.START_END_TIME_RULE_Y_M_D_H_M_S)) {
                return GlobalCode.END_TIME_RULE_ERROR;
            }
        }
        //校验startTime和endTime的大小
        if (TimeUtils.compareDate(patrolReq.getStartTime(), patrolReq.getEndTime(), RegexRule.TIME_FARMAT_Y_M_D_H_M_S)) {
            return GlobalCode.START_TIME_THAN_END_TIME_ERROR;
        }
        return null;
    }
}

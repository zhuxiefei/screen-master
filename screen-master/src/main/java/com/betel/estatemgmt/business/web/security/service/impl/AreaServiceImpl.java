package com.betel.estatemgmt.business.web.security.service.impl;

import com.betel.estatemgmt.business.web.security.code.SecurityCode;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.service.AreaService;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.security.SecurityAreaMapper;
import com.betel.estatemgmt.common.mapper.security.SecurityContentAreaSigninMapper;
import com.betel.estatemgmt.common.mapper.security.SecuritySigninAddressMapper;
import com.betel.estatemgmt.common.model.security.SecurityArea;
import com.betel.estatemgmt.common.model.security.SecurityContentAreaSignin;
import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.Quantity;
import com.betel.estatemgmt.utils.RegexRule;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 安保巡逻区域实现接口
 * </p>
 * ClassName: AreaServiceImpl <br/>
 * Author:jians.z  <br/>
 * Date:  2018/2/27 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl implements AreaService {
    private static Logger LOG = LoggerFactory.getLogger(AreaServiceImpl.class);
    @Autowired
    private SecurityAreaMapper securityAreaMapper;
    @Autowired
    private SecuritySigninAddressMapper securitySigninAddressMapper;
    @Autowired
    private SecurityContentAreaSigninMapper securityContentAreaSigninMapper;


    @Override
    public List<SecurityArea> findAreaList(String estateId) {
        return securityAreaMapper.findAreaList(estateId);
    }

    @Override
    public List<SecuritySigninAddress> findSignList(SecurityReq securityReq) {
        return securitySigninAddressMapper.findSignList(securityReq);
    }

    @Override
    public String addArea(SecurityReq securityReq) {
        String code = check(trim(securityReq), Quantity.ONE);
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            List<SecurityArea> securityAreaList = securityAreaMapper.findAreaByName(securityReq);
            if (securityAreaList.size() > Quantity.ZERO) {
                return SecurityCode.AREA_NAME_HAS_EXIST_ERROR;
            }
            SecurityArea ssd = new SecurityArea();
            ssd.setAreaId(UuidUtil.create());
            ssd.setAreaName(securityReq.getAreaName());
            ssd.setCreateTime(new Date());
            ssd.setUpdateTime(new Date());
            ssd.setEstateId(securityReq.getEstateId());
            securityAreaMapper.insert(ssd);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String addSignin(SecurityReq securityReq) {
        String code = check(trim(securityReq), Quantity.TWO);
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            List<SecuritySigninAddress> securitySigninAddressList = securitySigninAddressMapper.findSignByName(securityReq);
            if (securitySigninAddressList.size() > Quantity.ZERO) {
                return SecurityCode.AREA_SIGNIN_ADDRES_HAS_EXIST_ERROR;
            }
            SecurityArea securityArea = securityAreaMapper.selectByPrimaryKey(securityReq.getAreaId());
            if (securityArea == null) {
                return SecurityCode.AREA_IS_NOT_EXIST;
            }
            //区域是否删除判断
            SecuritySigninAddress ssd = new SecuritySigninAddress();
            ssd.setSigninId(UuidUtil.create());
            ssd.setSigninAddress(securityReq.getSigninAddress());
            ssd.setAreaId(securityReq.getAreaId());
            ssd.setCreateTime(new Date());
            ssd.setUpdateTime(new Date());
            securitySigninAddressMapper.insert(ssd);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String updateArea(SecurityReq securityReq) {
        String code = check(trim(securityReq), Quantity.ONE);
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            SecurityArea securityArea = securityAreaMapper.selectByPrimaryKey(securityReq.getAreaId());
            if (securityArea == null) {
                return SecurityCode.AREA_IS_NOT_EXIST;
            }
            List<SecurityArea> securityAreaList = securityAreaMapper.findAreaByName(securityReq);
            if (securityAreaList.size() > 0) {
                return SecurityCode.AREA_NAME_HAS_EXIST_ERROR;
            }
            SecurityArea ssd = new SecurityArea();
            ssd.setAreaId(securityReq.getAreaId());
            ssd.setAreaName(securityReq.getAreaName());
            ssd.setUpdateTime(new Date());
            securityAreaMapper.updateByPrimaryKeySelective(ssd);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public String updateSignin(SecurityReq securityReq) {
        String code = check(trim(securityReq), Quantity.TWO);
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            SecuritySigninAddress securitySigninAddress = securitySigninAddressMapper.selectByPrimaryKey(securityReq.getSigninId());
            if (securitySigninAddress == null) {
                return SecurityCode.SIGNIN_ADDRESS_IS_NOT_EXIST;
            }
            List<SecuritySigninAddress> securitySigninAddressList = securitySigninAddressMapper.findSignByName(securityReq);
            if (securitySigninAddressList.size() > Quantity.ZERO) {
                return SecurityCode.AREA_SIGNIN_ADDRES_HAS_EXIST_ERROR;
            }
            SecuritySigninAddress ssd = new SecuritySigninAddress();
            ssd.setSigninId(securityReq.getSigninId());
            ssd.setSigninAddress(securityReq.getSigninAddress());
            ssd.setUpdateTime(new Date());
            securitySigninAddressMapper.updateByPrimaryKeySelective(ssd);
            return StatusCode.SUCCESS;
        }
    }

    @Override
    public SecurityArea findArea(SecurityReq securityReq) {
        return securityAreaMapper.selectByPrimaryKey(securityReq.getAreaId());
    }

    @Override
    public SecuritySigninAddress findSignin(SecurityReq securityReq) {
        return securitySigninAddressMapper.selectByPrimaryKey(securityReq.getSigninId());
    }

    @Override
    public String deleteArea(SecurityReq securityReq) {
        if (StringUtil.isBlank(securityReq.getAreaId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        } else {
            List<SecuritySigninAddress> securitySigninAddressList = securitySigninAddressMapper.findSignByaAreaId(securityReq);
            if (securitySigninAddressList.size() > Quantity.ZERO) {
                return SecurityCode.SIGINID_ADRESS_UDDER_AREA;
            } else {
                securityAreaMapper.deleteByPrimaryKey(securityReq.getAreaId());
                return StatusCode.SUCCESS;
            }
        }
    }

    @Override
    public String deleteSignin(SecurityReq securityReq) {
        List<SecurityContentAreaSignin> signinList = securityContentAreaSigninMapper.findBySigninId(securityReq);
        if (signinList.size() > Quantity.ZERO) {
            return SecurityCode.CONTENT_UNDER_SIGNIN;
        }
        securitySigninAddressMapper.deleteByPrimaryKey(securityReq.getSigninId());
        return StatusCode.SUCCESS;
    }

    private SecurityReq trim(SecurityReq securityReq) {
        if (!StringUtil.isEmpty(securityReq.getAreaName())) {
            securityReq.setAreaName(securityReq.getAreaName().trim());
        }
        if (!StringUtil.isEmpty(securityReq.getSigninAddress())) {
            securityReq.setSigninAddress(securityReq.getSigninAddress().trim());
        }
        return securityReq;
    }

    /**
     * 校验
     *
     * @param securityReq
     * @param action      1 新增区域  2 新增区域下签到地点
     * @return
     */
    private String check(SecurityReq securityReq, int action) {
        if (action == Quantity.ONE) {
            if (StringUtil.isBlank(securityReq.getAreaName())) {
                return GlobalCode.PARAMETER_IS_NULL_ERROR;
            }
            if (!securityReq.getAreaName().matches(RegexRule.SPECIAL_WORD_RULE)) {
                return SecurityCode.AREA_NAME_IS_RILE_ERROR;
            }
        } else if (action == Quantity.TWO) {
            if (StringUtil.isBlank(securityReq.getSigninAddress())) {
                return GlobalCode.PARAMETER_IS_NULL_ERROR;
            }
            if (!securityReq.getSigninAddress().matches(RegexRule.SPECIAL_WORD_RULE)) {
                return SecurityCode.SIGNIN_ADDRESS_IS_RILE_ERROR;
            }
            if (StringUtil.isBlank(securityReq.getAreaId())) {
                return GlobalCode.Global_ID_NULL_ERROR;
            }
        }
        return null;
    }
}

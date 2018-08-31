package com.betel.estatemgmt.business.web.security.service.impl;

import com.betel.estatemgmt.business.web.security.code.SecurityCode;
import com.betel.estatemgmt.business.web.security.model.ContextInfo;
import com.betel.estatemgmt.business.web.security.model.SecurityContentResp;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.business.web.security.service.ContentService;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.mapper.security.*;
import com.betel.estatemgmt.common.model.security.*;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 安保巡逻内容实现接口
 * </p>
 * ClassName: ContentServiceImpl <br/>
 * Author:jians.z  <br/>
 * Date:  2018/2/28 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentServiceImpl implements ContentService {
    private static Logger LOG = LoggerFactory.getLogger(ContentServiceImpl.class);
    @Autowired
    private SecurityContentMapper securityContentMapper;
    @Autowired
    private SecurityContentAreaSigninMapper securityContentAreaSigninMapper;
    @Autowired
    private SecurityAreaMapper securityAreaMapper;
    @Autowired
    private SecurityRecordMapper securityRecordMapper;
    @Autowired
    private SecuritySigninNumberMapper securitySigninNumberMapper;
    @Autowired
    private SecuritySigninAddressMapper securitySigninAddressMapper;

    @Override
    public List<SecurityResp> findAllContentList(Paging<SecurityResp> pager, SecurityReq securityReq) {
        List<SecurityResp> securityRespList = securityContentMapper.findAllContentList(pager.getRowBounds(), securityReq);
        for (int i = 0; i < securityRespList.size(); i++) {
            String name = "";
            String contentId = securityRespList.get(i).getContentId();
            SecurityReq securityReq_temp = new SecurityReq();
            securityReq_temp.setContentId(contentId);
            List<SecurityContentAreaSignin> scas = securityContentAreaSigninMapper.findByContentId(securityReq_temp);
            for (int j = 0; j < scas.size(); j++) {
                SecuritySigninAddress securitySigninAddress = securitySigninAddressMapper.selectByPrimaryKey(scas.get(j).getSigninId());
                if (securitySigninAddress != null) {
                    String address = securitySigninAddress.getSigninAddress();
                    if (j == (scas.size() - 1)) {
                        name = name + address;
                    } else {
                        name = name + address + ",";
                    }
                }
            }
            securityRespList.get(i).setSigninAddress(name);
        }
        return securityRespList;
    }

    @Override
    public SecurityResp findContent(SecurityReq securityReq) {
        SecurityResp securityResp = securityContentMapper.findContent(securityReq);
        if (securityResp != null) {
            //签到地点集合
            List<SecuritySigninAddress> securitySigninAddress = securityContentMapper.findSigninByContentId(securityReq);
            securityResp.setSigninAddressList(securitySigninAddress);
        }
        return securityResp;
    }

    @Override
    public String updateContent(SecurityReq securityReq) {
        String code = check(trim(securityReq));
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            String temp = isExist(securityReq);
            if (!StringUtil.isBlank(temp)) {
                return temp;
            }
            String temp_code = contentIsExist(securityReq);
            if (!StringUtil.isBlank(temp_code)) {
                return temp_code;
            }
            securityContentAreaSigninMapper.deleteByContentId(securityReq);
            String[] signinIds = Tool.getIdArrOfStringType(securityReq.getSigninId());
            for (int i = 0; i < signinIds.length; i++) {
                SecurityContentAreaSignin scas = new SecurityContentAreaSignin();
                scas.setContSignId(UuidUtil.create());
                scas.setContentId(securityReq.getContentId());
                scas.setSigninId(signinIds[i]);
                scas.setCreateTime(new Date());
                scas.setUpdateTime(new Date());
                securityContentAreaSigninMapper.insertSelective(scas);
            }
            SecurityContent sc = new SecurityContent();
            sc.setContentId(securityReq.getContentId());
            sc.setAreaId(securityReq.getAreaId());
            sc.setInspectionTime(securityReq.getInspectionTime());
            sc.setUpdateTime(new Date());
            sc.setContentDesc(securityReq.getContentDesc());
            securityContentMapper.updateByPrimaryKeySelective(sc);
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public String addContent(SecurityReq securityReq) {
        String code = addCheck((trim(securityReq)));
        if (!StringUtil.isBlank(code)) {
            return code;
        } else {
            String temp = isExist(securityReq);
            if (!StringUtil.isBlank(temp)) {
                return temp;
            }
            String temp_code = contentIsExist(securityReq);
            if (!StringUtil.isBlank(temp_code)) {
                return temp_code;
            }
            SecurityContent sc = new SecurityContent();
            sc.setContentId(UuidUtil.create());
            sc.setContentNo("XL" + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()));
            String[] signinIds = Tool.getIdArrOfStringType(securityReq.getSigninId());
            sc.setAreaId(securityReq.getAreaId());
            sc.setInspectionTime(securityReq.getInspectionTime());
            sc.setContentDesc(securityReq.getContentDesc());
            sc.setCreateTime(new Date());
            sc.setEstateId(securityReq.getEstateId());
            securityContentMapper.insert(sc);
            for (int i = 0; i < signinIds.length; i++) {
                SecurityContentAreaSignin scas = new SecurityContentAreaSignin();
                scas.setContSignId(UuidUtil.create());
                scas.setContentId(sc.getContentId());
                scas.setSigninId(signinIds[i]);
                scas.setCreateTime(new Date());
                scas.setUpdateTime(new Date());
                securityContentAreaSigninMapper.insertSelective(scas);
            }
        }
        return StatusCode.SUCCESS;
    }

    /**
     * 校验是否存在
     *
     * @param securityReq
     * @return
     */
    private String contentIsExist(SecurityReq securityReq) {
        boolean flag = compareMapAndParmReq(getAreaSignMap(securityReq), securityReq);
        if (flag == true) {
            return SecurityCode.AREA_SIGNIN_ADDRESS_IS_EXIST;
        } else {
            return null;
        }
    }

    /**
     * 获得数据库中已有的巡逻任务Map集合
     *
     * @return key=区域id+签到id集合  value=发布时间
     */
    private Map<String, String[]> getAreaSignMap(SecurityReq securityReq) {
        List<SecurityContent> securityContentList = securityContentMapper.selectContent(securityReq);
        Map<String, String[]> map = new HashMap();
        for (int i = 0; i < securityContentList.size(); i++) {
            String contentId = securityContentList.get(i).getContentId();
            String areaId = securityContentList.get(i).getAreaId();
            String inspectionTime = securityContentList.get(i).getInspectionTime();
            //敲到时间转换为
            String[] inspectionTimes = inspectionTime.split(",");
            SecurityReq securityReq1 = new SecurityReq();
            securityReq1.setContentId(contentId);
            List<SecurityContentAreaSignin> signinList = securityContentAreaSigninMapper.findByContentId(securityReq1);
            String signinIdList = "";
            //取得签到地址id字符串，号隔开
            for (int j = 0; j < signinList.size(); j++) {
                String signinId = signinList.get(j).getSigninId();
                if (j == signinList.size() - 1) {
                    signinIdList = signinId + "";
                } else {
                    signinIdList = signinId + "" + ",";
                }
            }
            //创建Map集合Key=区域id+签到地址id集合，value=签到时间
            map.put(areaId + signinIdList, inspectionTimes);
        }
        return map;
    }

    /**
     * 前端入参中发布时间和数据库中发布时间对比
     *
     * @param map
     * @param securityReq
     * @return trur 表示已经存在，false 不存在
     */
    private boolean compareMapAndParmReq(Map<String, String[]> map, SecurityReq securityReq) {
        //获得Key
        String key = securityReq.getAreaId() + securityReq.getSigninId();
        String[] inspectionTimes = map.get(key);
        String tempTime = securityReq.getInspectionTime();
        String[] paramInspectionTime = tempTime.split(",");
        if (inspectionTimes != null) {
            for (int i = 0; i < inspectionTimes.length; i++) {
                for (int j = 0; j < paramInspectionTime.length; j++) {
                    if (inspectionTimes[i].equals(paramInspectionTime[j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public String deleteContent(SecurityReq securityReq) {
        String code = Tool.checkIdIsNull(securityReq.getContentId());
        if (code == null) {
            return GlobalCode.Global_ID_NULL_ERROR;
        } else {
            String[] contentIds = Tool.getIdArrOfStringType(securityReq.getContentId());
            securityContentMapper.deleteBatchContent(contentIds);
            securityContentAreaSigninMapper.deleteBatchByContentId(contentIds);
        }
        return StatusCode.SUCCESS;
    }

    @Override
    public List<SecurityResp> findAllRecord(Paging<SecurityResp> pager, SecurityReq securityReq) {
        List<SecurityResp> securityList = securityContentMapper.findAllRecord(pager.getRowBounds(), securityReq);
        for (int i = 0; i < securityList.size(); i++) {
            SecurityReq securityReq1 = new SecurityReq();
            securityReq1.setRecordId(securityList.get(i).getRecordId());
            List<SecuritySigninNumber> ssn = securitySigninNumberMapper.findByRecordId(securityReq1);
            securityList.get(i).setSigninNumRecordList(ssn);
        }
        return securityList;
    }

    @Override
    public SecurityContentResp findRecordContent(SecurityReq securityReq) {
        SecurityContentResp securityContentResp = new SecurityContentResp();
        SecurityContent securityContent = securityContentMapper.selectByPrimaryKey(securityReq.getContentId());
        if (securityContent != null) {
            securityContentResp.setContentId(securityContent.getContentId());
            securityContentResp.setContentNo(securityContent.getContentNo());
            securityContentResp.setAreaId(securityContent.getAreaId());
            SecurityArea securityArea = securityAreaMapper.selectByPrimaryKey(securityContent.getAreaId());
            if (null != securityArea) {
                securityContentResp.setAreaName(securityArea.getAreaName());
            }
            String contentId = securityContent.getContentId();
            String name = createSigninAddress(contentId);
            securityContentResp.setSigninAddress(name);

        }
        return securityContentResp;
    }

    /**
     * 设置签到地址
     *
     * @return
     */
    private String createSigninAddress(String contentId) {
        String name = "";
        SecurityReq securityReq_temp = new SecurityReq();
        securityReq_temp.setContentId(contentId);
        List<SecurityContentAreaSignin> scas = securityContentAreaSigninMapper.findByContentId(securityReq_temp);
        for (int j = 0; j < scas.size(); j++) {
            SecuritySigninAddress securitySigninAddress = securitySigninAddressMapper.selectByPrimaryKey(scas.get(j).getSigninId());
            if (securitySigninAddress != null) {
                String address = securitySigninAddress.getSigninAddress();
                if (j == (scas.size() - 1)) {
                    name = name + address;
                } else {
                    name = name + address + ",";
                }
            }
        }
        return name;
    }

    private String isExist(SecurityReq securityReq) {
        String[] signinIds = Tool.getIdArrOfStringType(securityReq.getSigninId());
        List<SecuritySigninAddress> securitySigninAddressList = securityContentMapper.findSigninById(signinIds);
        if (securitySigninAddressList.size() < signinIds.length) {
            return SecurityCode.SIGNIN_ADDRESS_IS_NOT_EXIST;
        }
        if (null == securityAreaMapper.selectByPrimaryKey(securityReq.getAreaId())) {
            return SecurityCode.AREA_IS_NOT_EXIST;
        }
        if (securityReq.getContentId() != null) {
            if (null == securityContentMapper.selectByPrimaryKey(securityReq.getContentId())) {
                return SecurityCode.CONTENT_IS_NOT_EXIST;
            }
        }
        return null;
    }

    private SecurityReq trim(SecurityReq securityReq) {
        if (!StringUtil.isEmpty(securityReq.getContentDesc())) {
            securityReq.setContentDesc(securityReq.getContentDesc().trim());
        }
        return securityReq;
    }

    private String check(SecurityReq securityReq) {
        if (StringUtil.isBlank(securityReq.getContentId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        }
        if (StringUtil.isBlank(securityReq.getAreaId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        }
        if (StringUtil.isBlank(securityReq.getSigninId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        } else {
            String code = Tool.checkIdIsNull(securityReq.getSigninId());
            if (code == null) {
                return GlobalCode.Global_ID_NULL_ERROR;
            }
        }
        if (StringUtil.isBlank(securityReq.getInspectionTime())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            //最大10
            String[] temp = securityReq.getInspectionTime().split(",");
            if (temp.length > Quantity.TEN) {
                return SecurityCode.INSPECTIONTIME_MORE_THAN_TEN;
            }
        }
        if (StringUtil.isBlank(securityReq.getContentDesc())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            if (securityReq.getContentDesc().length() > Quantity.FIVE_HUNDRED) {
                return SecurityCode.CONTENT_DESC_MORE_FIVE_HUNDRED;
            }
        }
        return null;
    }

    private String addCheck(SecurityReq securityReq) {
        if (StringUtil.isBlank(securityReq.getAreaId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        }
        if (StringUtil.isBlank(securityReq.getSigninId())) {
            return GlobalCode.Global_ID_NULL_ERROR;
        } else {
            String code = Tool.checkIdIsNull(securityReq.getSigninId());
            if (code == null) {
                return GlobalCode.Global_ID_NULL_ERROR;
            }
        }
        if (StringUtil.isBlank(securityReq.getInspectionTime())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            //最大10
            String[] temp = securityReq.getInspectionTime().split(",");
            for (int i = 0; i < temp.length; i++) {
                String curTime = "2018-03-03 " + temp[i] + ":00";
                try {
                    DateUtil.toDate(curTime, RegexRule.TIME_FARMAT_Y_M_D_H_M_S);
                } catch (ParseException e) {
                    return SecurityCode.INSPECTION_TIME_IS_NOT_TRUE;
                }
            }
            if (temp.length > Quantity.TEN) {
                return SecurityCode.INSPECTIONTIME_MORE_THAN_TEN;
            }
        }
        if (StringUtil.isBlank(securityReq.getContentDesc())) {
            return GlobalCode.PARAMETER_IS_NULL_ERROR;
        } else {
            if (securityReq.getContentDesc().length() > Quantity.FIVE_HUNDRED) {
                return SecurityCode.CONTENT_DESC_MORE_FIVE_HUNDRED;
            }
        }
        return null;
    }
}

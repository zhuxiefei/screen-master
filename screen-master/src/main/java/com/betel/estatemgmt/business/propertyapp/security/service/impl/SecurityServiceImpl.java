package com.betel.estatemgmt.business.propertyapp.security.service.impl;

import com.betel.estatemgmt.business.propertyapp.security.model.SecurityInfo;
import com.betel.estatemgmt.business.propertyapp.security.model.SignInfo;
import com.betel.estatemgmt.business.propertyapp.security.model.SignReq;
import com.betel.estatemgmt.business.propertyapp.security.service.SecurityService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.mapper.security.SecurityRecordMapper;
import com.betel.estatemgmt.common.mapper.security.SecuritySigninAddressMapper;
import com.betel.estatemgmt.common.mapper.security.SecuritySigninNumberMapper;
import com.betel.estatemgmt.common.model.security.SecurityRecord;
import com.betel.estatemgmt.common.model.security.SecuritySigninAddress;
import com.betel.estatemgmt.common.model.security.SecuritySigninNumber;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * app安保巡逻实现类...
 * </p>
 * ClassName: CheckServiceImpl <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 11:35 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    SecurityRecordMapper recordMapper;

    @Autowired
    SecuritySigninNumberMapper securitySigninNumberMapper;

    @Autowired
    SecuritySigninAddressMapper securitySigninAddressMapper;

    @Override
    public List<SecurityInfo> findAllSecurityList(Paging<SecurityInfo> pager, String status,String estateId) {
        List<SecurityInfo>  list =  recordMapper.findAllSecurityList(pager.getRowBounds(),status,estateId);
        for(SecurityInfo securityInfo:list){
            //根据recordId查询巡逻内容对应的签到地点
            List<SecuritySigninAddress> addressInfoList = securitySigninAddressMapper.findAddressByRecordId(securityInfo.getRecordId());
            StringBuilder addressNames = new StringBuilder();
            for(int i = 0; i < addressInfoList.size(); i++){
                if (i == 0) {
                    addressNames.append(addressInfoList.get(i).getSigninAddress());
                } else {
                    addressNames.append("," + addressInfoList.get(i).getSigninAddress());
                }
            }
            securityInfo.setSigninAddress(addressNames.toString());
        }
        return list;
    }

    @Override
    public List<SignInfo> findAllSignList(String recordId) {
        return securitySigninNumberMapper.findAllSignList(recordId);
    }

    @Override
    public void sign(SignReq signReq, String userId, HttpServletRequest request) throws Exception{
        SecuritySigninNumber securitySigninNumber = new SecuritySigninNumber();
        securitySigninNumber.setRecordId(signReq.getRecordId());
        securitySigninNumber.setLongitude(Double.parseDouble(signReq.getLongitude()));
        securitySigninNumber.setLatitude(Double.parseDouble(signReq.getLatitude()));
        securitySigninNumber.setCreateTime(new Date());
        securitySigninNumber.setUpdateTime(new Date());
        securitySigninNumber.setAddress(signReq.getAddress());
        securitySigninNumber.setRemarks(signReq.getRemarks());
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("employeeId", userId);
        Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/common/v1/findUserInfo",jsonParam,request);
        securitySigninNumber.setSigninEmpId(userId);
        if(null != r){
            JSONObject json = JSONObject.fromObject(r.getData());
            if(null != json.get("employeeName") && null != json.get("departmentName")){
                securitySigninNumber.setSigninEmpName(json.get("departmentName").toString()+"-"+json.get("employeeName").toString());
            }
        }
        securitySigninNumberMapper.insertSelective(securitySigninNumber);

        //修改签到次数
        SecurityRecord securityRecord = recordMapper.selectByPrimaryKey(signReq.getRecordId());
        SecurityRecord record = new SecurityRecord();
        record.setRecordId(signReq.getRecordId());
        record.setSigninNum(securityRecord.getSigninNum()+1);
        record.setUpdateTime(new Date());
        recordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void finish(String recordId, String userId, HttpServletRequest request) throws Exception{
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("employeeId", userId);
        Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/common/v1/findUserInfo",jsonParam,request);
        SecurityRecord record = new SecurityRecord();
        record.setEmployeeId(userId);
        if(null != r){
            JSONObject json = JSONObject.fromObject(r.getData());
            if(null != json.get("employeeName") && null != json.get("departmentName")){
                record.setEmployeeName(json.get("departmentName").toString()+"-"+json.get("employeeName").toString());
            }
        }
        record.setRecordId(recordId);
        record.setFinishTime(new Date());
        record.setIsPatrol(2);
        recordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SecurityRecord findSecurityRecordById(String recordId) {
        return recordMapper.selectByPrimaryKey(recordId);
    }
}

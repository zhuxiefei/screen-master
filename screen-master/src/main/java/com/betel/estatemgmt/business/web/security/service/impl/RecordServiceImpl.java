package com.betel.estatemgmt.business.web.security.service.impl;


import com.betel.estatemgmt.business.propertyapp.check.model.EmployeeResp;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.security.constant.SecurityConstant;
import com.betel.estatemgmt.business.web.security.model.SecurityReq;
import com.betel.estatemgmt.business.web.security.model.SecurityResp;
import com.betel.estatemgmt.business.web.security.service.RecordService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.security.*;
import com.betel.estatemgmt.common.model.security.*;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: RecordServiceImpl <br/>
 * Author: jians.z  <br/>
 * Date: 2018/2/12 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecordServiceImpl implements RecordService {
    private static Logger LOG = LoggerFactory.getLogger(ContentServiceImpl.class);
    @Autowired
    private SecurityRecordMapper securityRecordMapper;
    @Autowired
    private SecuritySigninNumberMapper securitySigninNumberMapper;
    @Autowired
    private SecurityContentMapper securityContentMapper;
    @Autowired
    private SecurityContentAreaSigninMapper securityContentAreaSigninMapper;
    @Autowired
    private SecuritySigninAddressMapper securitySigninAddressMapper;

    @Override
    public List<SecurityResp> findAllRecordList(Paging<SecurityResp> pager, SecurityReq securityReq) {
        List<SecurityResp> securityRespList = securityRecordMapper.findAllRecordList(pager.getRowBounds(), securityReq);
        return createSecurityRespList(securityRespList);
    }

    /**
     * 设置签到地址
     *
     * @param securityRespList
     * @return
     */
    private List<SecurityResp> createSecurityRespList(List<SecurityResp> securityRespList) {
        for (int i = 0; i < securityRespList.size(); i++) {
            String contentId = securityRespList.get(i).getContentId();
            SecurityReq securityReq_temp = new SecurityReq();
            securityReq_temp.setContentId(contentId);
            List<SecurityContentAreaSignin> scas = securityContentAreaSigninMapper.findByContentId(securityReq_temp);
            //获得签到地址
            String name = "";
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
    public SecurityResp findRecord(SecurityReq securityReq) {
        SecurityResp securityResp = securityRecordMapper.findRecord(securityReq);
        if (null != securityResp) {
            securityReq.setRecordId(securityResp.getRecordId());
            List<SecuritySigninNumber> ssn = securitySigninNumberMapper.findByRecordId(securityReq);
            securityResp.setSigninNumRecordList(ssn);
        }
        List<SecurityResp> securityRespList = new ArrayList<>();
        securityRespList.add(securityResp);
        List<SecurityResp> securityResps = createSecurityRespList(securityRespList);
        return securityResps.get(0);
    }

    @Override
    public List<SecurityResp> exportList(SecurityReq securityReq) {
        List<SecurityResp> securityRespList = null;
        if (!StringUtil.isBlank(securityReq.getRecordId())) {
            //id导出
            String[] recordIds = Tool.getIdArrOfStringType(securityReq.getRecordId());
            securityRespList = securityRecordMapper.exportListByRecordId(recordIds);
        } else {
            securityRespList = securityRecordMapper.exportList(securityReq);
        }
        return createSecurityRespList(securityRespList);
    }

    @Override
    public void quartzSecurity(long curDate) throws Exception {
        List<SecurityContent> securityContentList = securityContentMapper.selectList();
        if (securityContentList.size() > Quantity.ZERO) {
            for (int i = 0; i < securityContentList.size(); i++) {
                String inspectionTime = securityContentList.get(i).getInspectionTime();
                String estateId = securityContentList.get(i).getEstateId();
                String[] inspectionTimes = inspectionTime.split(",");
                for (int j = 0; j < inspectionTimes.length; j++) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String temp = inspectionTimes[j];
                    String strTime = simpleDateFormat.format(new Date());
                    String createRecordTime = strTime + " " + temp + ":00";
                    Date date = sdf.parse(createRecordTime);
                    long bbb = date.getTime();
                    //60000代表一分钟
                    long rrr = bbb + 60000;
                    //如果在一分钟返回内执行生成待巡逻任务
                    if (date != null && bbb <= curDate && curDate < rrr) {
                        SecurityRecord securityRecord = new SecurityRecord();
                        securityRecord.setRecordId(UuidUtil.create());
                        securityRecord.setContentId(securityContentList.get(i).getContentId());
                        securityRecord.setCreateTime(date);
                        securityRecord.setIsPatrol(SecurityConstant.IS_PATROL_ONE);
                        securityRecord.setSigninNum(Quantity.ZERO);
                        securityRecord.setUpdateTime(new Date());
                        securityRecord.setEstateId(estateId);
                        int flag = securityRecordMapper.insert(securityRecord);
                        if (LOG.isDebugEnabled()) {
                            if (flag > Quantity.ZERO) {
                                LOG.debug("定时任务，生成巡逻任务成功：", securityRecord);
                            }
                        }
                        if (flag > Quantity.ZERO) {
                            push(securityRecord);
                        }
                    }
                }
            }
        }
    }

    /**
     * 推送
     *
     * @param securityRecord
     * @throws Exception
     */
    private void push(SecurityRecord securityRecord) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("privilegeId", 464);
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL) + "/httpclientOA/estatemgmt/v1/findEmp", jsonParam, null);
        List<String> phone = new ArrayList<>();
        if (response != null) {
            if (null != response.getData()) {
                LOG.debug("=======================================" + response.getData().toString());
                Type type = new TypeToken<List<EmployeeResp>>() {
                }.getType();
                List<EmployeeResp> lists = gson.fromJson(response.getData().toString(), type);
                for (int i = 0; i < lists.size(); i++) {
                    phone.add(lists.get(i).getPhoneNum());
                }
            }
        }
        SendMessage send = new SendMessage();
        send.setSendId(securityRecord.getRecordId());
        send.setSendTitle(SecurityConstant.TO_SECURITY_TITLE);
        send.setSendNo(SecurityConstant.TO_SECURITY_SENDNO);
        send.setSendType(SecurityConstant.PUSH_SENDTYPE);
        send.setSendContent(SecurityConstant.TO_SENDTYPE__SEND_CONTENT);
        //转json
        String pushInfo = GsonUtil.object2Gson(send);
        //推送
        if (phone.size() > 0) {
            PropertyPushUtil.pushList(phone, pushInfo, false);
        }
    }
}

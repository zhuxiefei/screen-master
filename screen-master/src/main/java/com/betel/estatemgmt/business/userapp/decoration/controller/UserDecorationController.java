package com.betel.estatemgmt.business.userapp.decoration.controller;

import com.betel.estatemgmt.business.userapp.decoration.code.DecorationCode;
import com.betel.estatemgmt.business.userapp.decoration.constant.DecorationConstant;
import com.betel.estatemgmt.business.userapp.decoration.model.AddDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.model.EmpInfo;
import com.betel.estatemgmt.business.userapp.decoration.service.DecorationService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.msgpush.PropertyPushUtil;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cn.jpush.api.push.model.PushModel.gson;

/**
 * <p>
 * 用户app装修申请
 * </p>
 * ClassName: UserDecorationController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/23 10:22 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/userDecoration")
public class UserDecorationController {

    private static final Logger LOG = LoggerFactory.getLogger(UserDecorationController.class);

    @Autowired
    DecorationService decorationService;

    @Autowired
    HouseService houseService;

    /**
     * <p>
     * 添加用户装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/23 11:54
     * @param addDecorationReq
     * @return
     */
    @RequestMapping(value = "v1/addDecoration",method = RequestMethod.POST)
    public Response addDecoration(@RequestBody@Valid AddDecorationReq addDecorationReq
                                    , BindingResult bindingResult
                                    , HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/addDecoration--------------start====addDecorationReq===" + addDecorationReq);
        }
        Response response = new Response();
        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        try {
            String estateId = request.getHeader("estateId");
            //如果没有传 就默认塞个1，代表是锋尚。
            if(null == estateId){
                estateId = AESUtil.encrypt("1");
            }
            //判断房屋是否被删除
            boolean isDeleted = decorationService.houseIsDeleted(addDecorationReq.getHouseId(),AESUtil.decrypt(estateId));
            if(isDeleted){
                response.setCode(DecorationCode.HOUSE_IS_DELETED);
            }else {
                String recordId = decorationService.addDecoration(addDecorationReq);
                //推送給物業人員
                addPush(recordId,request);
            }
        }catch (Exception e){
            LOG.error("---app/userDecoration/v1/addDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/userDecoration/v1/addDecoration--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 装修申请列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/23 11:54
     * @return
     */
    @RequestMapping(value = "v1/decorationList",method = RequestMethod.POST)
    public Response decorationList(@RequestHeader("userId") String userId,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/decorationList--------------start=====" );
        }
        Response response = new Response();
        try {
            //如果没有传 就默认塞个1，代表是锋尚。
            String estateId = request.getHeader("estateId");
            if(null == estateId){
                estateId = AESUtil.encrypt("1");
            }
            List<DecoApplyRecord> list = decorationService.findMyDecorationList(AESUtil.decrypt(userId),AESUtil.decrypt(estateId));
            response.setData(list);
        }catch (Exception e){
            LOG.error("---app/userDecoration/v1/decorationList--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/userDecoration/v1/decorationList--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 装修申请详情
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 9:24
     * @param
     * @return
     */
    @RequestMapping(value = "v1/decorationInfo",method = RequestMethod.POST)
    public Response decorationInfo(@RequestBody AddDecorationReq addDecorationReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/decorationInfo--------------start=====" );
        }
        Response response = new Response();
        try {
            String estateId = request.getHeader("estateId");
            //如果没有传 就默认塞个1，代表是锋尚。
            if(null == estateId){
                estateId = AESUtil.encrypt("1");
            }
            DecoApplyRecord decorationInfo = decorationService.findDecorationInfo(addDecorationReq.getRecordId(),AESUtil.decrypt(estateId));
            if(decorationInfo.getIsDelete()==1){
                response.setCode(DecorationCode.DECORATION_IS_DELETED);
            }else {
                response.setData(decorationInfo);
            }
        }catch (Exception e){
            LOG.error("---app/userDecoration/v1/decorationInfo--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/userDecoration/v1/decorationInfo--------------end"+response);
        }
        return response;
    }

    /** 
     * <p>
     * 用户取消装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 9:51 
     * @param addDecorationReq
     * @return
     */
    @RequestMapping(value = "v1/cancelDecoration",method = RequestMethod.POST)
    public Response cancelDecoration(@RequestBody AddDecorationReq addDecorationReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/cancelDecoration--------------start=====" );
        }
        Response response = new Response();
        try {
            DecoApplyRecord decoApplyRecord = new DecoApplyRecord();
            decoApplyRecord.setRecordId(addDecorationReq.getRecordId());
            decoApplyRecord.setCancelTime(new Date());
            decoApplyRecord.setStatus(DecorationConstant.DECORATION_STATUS_CANCEL);
            decorationService.updateDecoration(decoApplyRecord);
            //推送給物業人員
            cancelPush(request);
        }catch (Exception e){
            LOG.error("---app/userDecoration/v1/cancelDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/userDecoration/v1/cancelDecoration--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 用户再次申请装修
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 10:27
     * @param addDecorationReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "v1/submitDecorationAgain",method = RequestMethod.POST)
    public Response submitDecorationAgain(@RequestBody@Valid AddDecorationReq addDecorationReq
                                            , BindingResult bindingResult
                                            , HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/submitDecorationAgain--------------start=====" );
        }
        Response response = new Response();
        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        try {
            //判斷該記錄是不是被拒絕的狀態,只有被拒絕的狀態才能再次提交
            DecoApplyRecord decoApplyRecord = decorationService.findDecoration(addDecorationReq.getRecordId());
            if(DecorationConstant.DECORATION_STATUS_REFUSE.equals(decoApplyRecord.getStatus())){
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
                Date date =  formatter.parse(addDecorationReq.getStartTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MONTH, addDecorationReq.getDecorationCycle());
                addDecorationReq.setEndTime(formatter.format(calendar.getTime()));
                decorationService.submitDecorationAgain(addDecorationReq);
                //推送給物業人員
                addPush(addDecorationReq.getRecordId(),request);
            }else {
                response.setCode(DecorationCode.NOT_REFUSE_STATUS);
            }
        }catch (Exception e){
            LOG.error("---app/userDecoration/v1/submitDecorationAgain--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/userDecoration/v1/submitDecorationAgain--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 添加或者再次提交時推送給物業人員
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/29 17:50
     * @throws Exception
     */
    private void addPush(String recordId, HttpServletRequest request)throws Exception{
        //推送給物業人員，先查出擁有裝修管理權限的物業人員
        List<EmpInfo> empInfos = findDecorationEmpInfo(request);
        List<String> phones = new ArrayList<>();
        if (null != empInfos && empInfos.size() > 0){
            for(EmpInfo empInfo:empInfos){
                phones.add(empInfo.getPhoneNum());
            }
        }
        SendMessage send = new SendMessage();
        send.setSendId(recordId);
        send.setSendTitle("用户提交装修申请");
        send.setSendNo("11");
        send.setSendType("smart05");
        send.setSendContent("您有一个新的装修申请，请及时处理");
        String pushInfo = GsonUtil.object2Gson(send);
        if(phones != null && phones.size()>0){
            //推送
            PropertyPushUtil.pushList(phones,pushInfo,false);
        }
    }

    /**
     * <p>
     * 取消時推送給物業人員
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/29 18:08
     * @throws Exception
     */
    private void cancelPush(HttpServletRequest request)throws Exception{
        List<EmpInfo> empInfos = findDecorationEmpInfo(request);
        List<String> phones = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if (null != empInfos && empInfos.size() > 0) {
            for (EmpInfo empInfo : empInfos) {
                phones.add(empInfo.getPhoneNum());
                ids.add(empInfo.getEmployeeId());
            }
            List<Notice> notices = new ArrayList<>();
            for (String userId:
                    ids) {
                //创建系统通知对象，将通知存到数据库
                Notice notice = new Notice();
                notice.setNoticeStatus(1);
                notice.setNoticeType(12);
                notice.setCreateTime(new Date(System.currentTimeMillis()));
                notice.setNoticeUserId(userId);
                notice.setNoticeContent("您有一个装修申请被用户取消");
                notices.add(notice);
            }
            decorationService.insertNotices(notices);
            SendMessage send = new SendMessage();
            send.setSendId(notices.get(0).getNoticeId().toString());
            send.setSendTitle("装修申请被取消");
            send.setSendNo("12");
            send.setSendType("smart05");
            send.setSendContent("您有一个装修申请被用户取消");
            String pushInfo = GsonUtil.object2Gson(send);
            if(phones != null && phones.size()>0){
                //推送
                PropertyPushUtil.pushList(phones,pushInfo,false);
            }
        }
    }

    /**
     * <p>
     * 查詢擁有裝修申請管理權限的物業人員信息（id和手機號）
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/29 18:11
     * @return
     * @throws Exception
     */
    private List<EmpInfo> findDecorationEmpInfo(HttpServletRequest request)throws Exception{
        JSONObject jsonParam = new JSONObject();
        Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/estatemgmt/v1/findDecorationEmps",jsonParam,request);
        if (null != r){
            Type type = new TypeToken<List<EmpInfo>>() {
            }.getType();
            List<EmpInfo> empInfos = gson.fromJson(r.getData().toString(), type);
            return empInfos;
        }
        return null;
    }


    /**
     * <p>
     * 用戶刪除裝修申請
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/30 17:03
     * @param addDecorationReq
     * @return
     */
    @RequestMapping(value = "v1/deleteDecoration",method = RequestMethod.POST)
    public Response submitDecorationAgain(@RequestBody AddDecorationReq addDecorationReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/deleteDecoration--------------start=====" );
        }
        Response response = new Response();
        try {
            DecoApplyRecord decoApplyRecord = new DecoApplyRecord();
            decoApplyRecord.setRecordId(addDecorationReq.getRecordId());
            decoApplyRecord.setIsDelete(1);
            decorationService.updateDecoration(decoApplyRecord);
        }catch (Exception e){
            LOG.error("---app/userDecoration/v1/deleteDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/userDecoration/v1/deleteDecoration--------------start=====" );
        }
        return response;
    }
}

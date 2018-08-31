package com.betel.estatemgmt.business.userapp.decoration.service.impl;

import com.betel.estatemgmt.business.propertyapp.decoration.model.DoDecorationReq;
import com.betel.estatemgmt.business.propertyapp.decoration.model.QueryDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.constant.DecorationConstant;
import com.betel.estatemgmt.business.userapp.decoration.model.AddDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.service.DecorationService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.SendMessage;
import com.betel.estatemgmt.common.mapper.admin.AdminMapper;
import com.betel.estatemgmt.common.mapper.decoration.DecoApplyRecordMapper;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingMapper;
import com.betel.estatemgmt.common.mapper.house.BuildingUnitMapper;
import com.betel.estatemgmt.common.mapper.house.HouseMapper;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.system.NoticeMapper;
import com.betel.estatemgmt.common.model.admin.Admin;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.system.Notice;
import com.betel.estatemgmt.common.msgpush.MsgPushUtils;
import com.betel.estatemgmt.utils.GsonUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 装修
 * </p>
 * ClassName: DecorationServiceImpl <br/>
 * Author: Cui.xx  <br/>
 * Date: 2018/1/23 11:44 <br/>
 * Version: 1.0 <br/>
 */
@Service("DecorationService")
@Transactional
public class DecorationServiceImpl implements DecorationService{

    private static final Logger LOG = LoggerFactory.getLogger(DecorationServiceImpl.class);

    private static String estateType_house = "1";

    @Autowired
    DecoApplyRecordMapper decoApplyRecordMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    BuildingMapper buildingMapper;

    @Autowired
    BuildingUnitMapper unitMapper;

    @Autowired
    EstateMapper estateMapper;

    @Autowired
    OfficeHouseMapper officeHouseMapper;

    @Override
    public String addDecoration(AddDecorationReq addDecorationReq) throws Exception{
        DecoApplyRecord decoApplyRecord = new DecoApplyRecord();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Date date =  formatter.parse(addDecorationReq.getStartTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addDecorationReq.getDecorationCycle());
        decoApplyRecord.setHouseId(addDecorationReq.getHouseId());
        decoApplyRecord.setDecorationCompany(addDecorationReq.getDecorationCompany());
        decoApplyRecord.setStartTime(date);
        decoApplyRecord.setEndTime(calendar.getTime());
        decoApplyRecord.setDecorationCycle(addDecorationReq.getDecorationCycle());
        decoApplyRecord.setDecorationDesc(addDecorationReq.getDecorationDesc());
        decoApplyRecord.setCreateTime(new Date());
        decoApplyRecordMapper.insertSelective(decoApplyRecord);
        return decoApplyRecord.getRecordId();
    }

    @Override
    public List<DecoApplyRecord> findMyDecorationList(String userId,String estateId) {
        return decoApplyRecordMapper.findMyDecorationList(userId,estateId);
    }

    @Override
    public DecoApplyRecord findDecorationInfo(String recordId,String estateId) {
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        DecoApplyRecord record = new DecoApplyRecord();
        if(estateType_house.equals(estate.getEstateType())){
            record = decoApplyRecordMapper.findDecorationInfo(recordId);
        }else {
            record = decoApplyRecordMapper.findOfficeDecorationInfo(recordId);
        }
        return record;
    }

    @Override
    public DecoApplyRecord findDecoration(String recordId) {
        return decoApplyRecordMapper.selectByPrimaryKey(recordId);
    }

    @Override
    public void updateDecoration(DecoApplyRecord decoApplyRecord) {
        decoApplyRecordMapper.updateByPrimaryKeySelective(decoApplyRecord);
    }

    @Override
    public void submitDecorationAgain(AddDecorationReq addDecorationReq) {
        decoApplyRecordMapper.submitDecorationAgain(addDecorationReq);
    }

    @Override
    public void doDecoration(DoDecorationReq doDecorationReq,String userId,String type,HttpServletRequest request) throws Exception{
        DecoApplyRecord decoApplyRecord = new DecoApplyRecord();
        if(DecorationConstant.DECORATION_STATUS_AGREE.equals(doDecorationReq.getStatus())){
            //同意
            decoApplyRecord.setStatus(DecorationConstant.DECORATION_STATUS_AGREE);
            //处理时间
            decoApplyRecord.setUpdateTime(new Date());
            decoApplyRecord.setRecordId(doDecorationReq.getRecordId());
            //物业备注
            decoApplyRecord.setDescription(doDecorationReq.getDescription());
        }else if(DecorationConstant.DECORATION_STATUS_REFUSE.equals(doDecorationReq.getStatus())){
            //拒绝
            decoApplyRecord.setStatus(DecorationConstant.DECORATION_STATUS_REFUSE);
            //处理时间
            decoApplyRecord.setUpdateTime(new Date());
            //拒绝理由
            decoApplyRecord.setReason(doDecorationReq.getReason());
            //物业备注
            decoApplyRecord.setDescription(doDecorationReq.getDescription());
            decoApplyRecord.setRecordId(doDecorationReq.getRecordId());
        }else {
            //取消
            decoApplyRecord.setStatus(DecorationConstant.DECORATION_STATUS_CANCEL);
            //取消时间
            decoApplyRecord.setCancelTime(new Date());
            //取消原因
            decoApplyRecord.setCancelReason(doDecorationReq.getCancelReason());
            //查询登录的物业人员的信息
            String operator = null;
            if("app".equals(type)){
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("employeeId", AESUtil.decrypt(userId));
                Response r = HttpClientUtil.post(
                        ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/common/v1/findUserInfo",jsonParam,request);
                if (null != r){
                    JSONObject json = JSONObject.fromObject(r.getData());
                    if(json.get("departmentName") != null && json.get("employeeName") != null){
                        operator = json.get("departmentName").toString()+"-"+json.get("employeeName").toString();
                    }
                }
            }else {
                Admin admin = adminMapper.selectByPrimaryKey(AESUtil.decrypt(userId));
                operator = admin.getAdminName();
            }
            decoApplyRecord.setOperator(operator);
            decoApplyRecord.setRecordId(doDecorationReq.getRecordId());
        }
        decoApplyRecordMapper.updateByPrimaryKeySelective(decoApplyRecord);

        //起线程发推送
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    push(doDecorationReq,userId,request);
                }catch (Exception e){
                    LOG.error("==========push error===========",e);
                }
            }
        });
    }

    @Override
    public List<DecoApplyRecord> findAllDecorationList(QueryDecorationReq queryReq,Paging<DecoApplyRecord> pager) {
        List<DecoApplyRecord> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(queryReq.getEstateId());
        if(estateType_house.equals(estate.getEstateType())){
            //如果是小区楼盘
            list =  decoApplyRecordMapper.findAllDecorationList(queryReq,pager.getRowBounds());
        }else {
            //如果是写字楼
            list =  decoApplyRecordMapper.findAllOfficeDecorationList(queryReq,pager.getRowBounds());
        }
        //添加房屋信息
        if (list != null && list.size() > 0) {
            for (DecoApplyRecord info :
                    list) {
                if(estateType_house.equals(estate.getEstateType())){
                    House house = houseMapper.selectByPrimaryKey(info.getHouseId());
                    if(null != house){
                        info.setHouseName(house.getHouseNum());
                        if(house.getBuildingId()!= null){
                            Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
                            if (building != null) {
                                info.setBuildingName(building.getBuildingName());
                            }
                        }

                        if(house.getUnitId()!= null){
                            BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
                            if (unit != null) {
                                info.setUnitName(unit.getUnitName());
                            }
                        }
                    }
                }else {
                    OfficeHouse house = officeHouseMapper.selectByPrimaryKey(info.getHouseId());
                    if(null != house){
                        info.setHouseName(house.getHouseNum());
                        if(house.getBuildingId()!= null){
                            Building building = buildingMapper.selectByPrimaryKey(house.getBuildingId());
                            if (building != null) {
                                info.setBuildingName(building.getBuildingName());
                            }
                        }

                        if(house.getUnitId()!= null){
                            BuildingUnit unit = unitMapper.selectByPrimaryKey(house.getUnitId());
                            if (unit != null) {
                                info.setUnitName(unit.getUnitName());
                            }
                        }
                    }
                }

            }
        }
        return list;
    }

    private void push(DoDecorationReq doDecorationReq,String userId,HttpServletRequest request)throws Exception{
        //根据recordId查找房屋对应的户主和成员
        String ownerId = decoApplyRecordMapper.findOwnerByRecordId(doDecorationReq.getRecordId());
        List<String> members = decoApplyRecordMapper.findMembersByRecordId(doDecorationReq.getRecordId());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ownerId);
        for(String member:members){
            stringBuilder.append(",");
            stringBuilder.append(member);
        }
        String ids[] = stringBuilder.toString().split(",");
        for(String id:ids){
            Notice notice = new Notice();
            SendMessage send = new SendMessage();
            String noticeContent = null;
            if(DecorationConstant.DECORATION_STATUS_AGREE.equals(doDecorationReq.getStatus())){
                noticeContent = "尊敬的用户，你提交的装修申请已通过，请注意查看";
                notice.setNoticeType(31);
                send.setSendNo("31");
                send.setSendTitle("装修申请已通过");
            }else if(DecorationConstant.DECORATION_STATUS_REFUSE.equals(doDecorationReq.getStatus())){
                noticeContent = "尊敬的用户，你提交的装修申请未通过，未通过原因："+doDecorationReq.getReason()+"，如有疑问，请及时联系物业";
                notice.setNoticeType(32);
                send.setSendNo("32");
                send.setSendTitle("装修申请未通过");
            }else {
                noticeContent = "尊敬的用户，你提交的装修申请已被取消，取消原因："+doDecorationReq.getCancelReason()+"，如有疑问，请及时联系物业";
                notice.setNoticeType(33);
                send.setSendNo("33");
                send.setSendTitle("装修申请被取消");
            }
            notice.setCreateTime(new Date());
            notice.setNoticeStatus(1);
            notice.setNoticeContent(noticeContent);
            send.setSendType("smart02");
            send.setSendContent(noticeContent);

            notice.setNoticeUserId(id);
            noticeMapper.insertSelective(notice);
            send.setSendId(notice.getNoticeId().toString());
            String pushInfo = GsonUtil.object2Gson(send);
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("userId", id);
            Response response = HttpClientUtil.post(
                    ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/findUserAccount",jsonParam,request);
            if (null != response){
                JSONObject json = JSONObject.fromObject(response.getData());
                if(null != json.get("acctName")){
                    MsgPushUtils.push(json.get("acctName").toString(), pushInfo);
                }
            }
        }
    }

    @Override
    public void insertNotices(List<Notice> notices) {
        noticeMapper.insertNotices(notices);
    }

    @Override
    public boolean houseIsDeleted(String houseId, String estateId) {
        boolean isDeleted = false;
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            House house = houseMapper.selectByPrimaryKey(houseId);
            if(house == null){
                isDeleted = true;
            }
        }else {
            OfficeHouse house = officeHouseMapper.selectByPrimaryKey(houseId);
            if(house == null){
                isDeleted = true;
            }
        }
        return isDeleted;
    }
}

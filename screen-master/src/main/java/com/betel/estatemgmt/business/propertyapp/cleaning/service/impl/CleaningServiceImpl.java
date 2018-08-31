package com.betel.estatemgmt.business.propertyapp.cleaning.service.impl;

import com.betel.estatemgmt.business.propertyapp.cleaning.model.*;
import com.betel.estatemgmt.business.propertyapp.cleaning.service.CleaningService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.mapper.cleaning.*;
import com.betel.estatemgmt.common.mapper.system.SystemPictureMapper;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningRecord;
import com.betel.estatemgmt.common.model.cleaning.CleaningRecordPic;
import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * app保洁实现类...
 * </p>
 * ClassName: CleaningServiceImpl <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/4 11:35 <br/>
 * Version: 1.0 <br/>
 */
@Service("AppCleaningService")
@Transactional(rollbackFor = Exception.class)
public class CleaningServiceImpl implements CleaningService {

    private static final Logger LOG = LoggerFactory.getLogger(CleaningServiceImpl.class);

    @Autowired
    CleaningContentMapper cleaningContentMapper;

    @Autowired
    SystemPictureMapper pictureMapper;

    @Autowired
    CleaningRecordMapper cleaningRecordMapper;

    @Autowired
    CleaningRecordPicMapper cleaningRecordPicMapper;

    @Autowired
    CleaningAreaMapper areaMapper;

    @Autowired
    CleaningTypeMapper typeMapper;

    @Override
    public List<CleaningInfo> findAllCleaningList(FindCleaningListReq findCleaningListReq, Paging<CleaningInfo> pager) {
        return cleaningContentMapper.findAllCleaningList(findCleaningListReq,pager.getRowBounds());
    }

    @Override
    public void savePictures(List<SystemPicture> pictures) {
        pictureMapper.savePictures(pictures);
    }

    @Override
    public void addAssessment(AddAssessmentReq addReq,String userId,String estateId) {
        //先保存记录
        CleaningRecord record = new CleaningRecord();
        record.setContentId(addReq.getContentId());
        record.setIsStandard(Integer.parseInt(addReq.getIsStandard()));
        record.setProblemRecord(addReq.getProblemRecord());
        record.setExaminer(userId);
        record.setCreateTime(new Date());
        record.setEstateId(estateId);
        cleaningRecordMapper.insertSelective(record);
        //再保存记录对应的图片
        List<CleaningRecordPic> pics = new ArrayList<>();
        if (!StringUtil.isBlank(addReq.getPicIds())) {
            String[] ids = addReq.getPicIds().split(",");
            for (String id : ids) {
                CleaningRecordPic pic = new CleaningRecordPic();
                pic.setCreateTime(new Date());
                pic.setPictureId(id);
                pic.setRecordId(record.getRecordId());
                pics.add(pic);
            }
            cleaningRecordPicMapper.addPics(pics);
        }
    }

    @Override
    public List<CleaningRecordInfo> findAllAssessmentList(Paging<CleaningRecordInfo> pager, FindAssessmentListReq findAssessmentListReq,HttpServletRequest request) {
        List<CleaningRecordInfo> records = cleaningRecordMapper.findAllAssessmentList(pager.getRowBounds(),findAssessmentListReq);
        for(CleaningRecordInfo cleaningRecordInfo:records){
            //查询图片
            List<SystemPicture> systemPictures = cleaningRecordPicMapper.findPictureByRecordId(cleaningRecordInfo.getRecordId());
            List<String> pictureUrls = new ArrayList<>();
            for (int i = 0; i < systemPictures.size(); i++) {
                if (!StringUtil.isBlank(systemPictures.get(i).getPictureUrl())) {
                    pictureUrls.add(ConfigManager.read(ConfigName.FILE_SERVER) + systemPictures.get(i).getPictureUrl());
                }
            }
            cleaningRecordInfo.setPictureUrls(pictureUrls);

            //查询考核者的信息
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("employeeId", cleaningRecordInfo.getExaminer());
            try {
                Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/common/v1/findUserInfo",jsonParam,request);
                if(null != r){
                    JSONObject json = JSONObject.fromObject(r.getData());
                    if(null != json.get("employeeName")){
                        cleaningRecordInfo.setExaminer(json.get("employeeName").toString());
                    }else {
                        cleaningRecordInfo.setExaminer("");
                    }
                }else {
                    cleaningRecordInfo.setExaminer("");
                }
            }catch (Exception e){
                LOG.error("=====find employeeInfo error===",e);
            }
        }
        return records;
    }

    @Override
    public List<CleaningArea> findAllAreas(String estateId) {
        return areaMapper.findAreas(estateId);
    }

    @Override
    public List<CleaningType> findTypes(String areaId) {
        return typeMapper.findByAreaId(areaId);
    }
}

package com.betel.estatemgmt.business.web.cleaning.service.impl;

import com.betel.estatemgmt.business.web.cleaning.code.CleaningCode;
import com.betel.estatemgmt.business.web.cleaning.constant.CleaningConstant;
import com.betel.estatemgmt.business.web.cleaning.model.*;
import com.betel.estatemgmt.business.web.cleaning.service.CleaningService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.mapper.cleaning.*;
import com.betel.estatemgmt.common.model.cleaning.CleaningArea;
import com.betel.estatemgmt.common.model.cleaning.CleaningContent;
import com.betel.estatemgmt.common.model.cleaning.CleaningType;
import com.betel.estatemgmt.common.model.system.SystemPicture;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.Validate;
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
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: CleaningServiceImpl <br/>
 * Author: Xia.yx  <br/>
 * Date: 2018/2/28 9:22 <br/>
 * Version: 1.0 <br/>
 */
@Service("CleaningService")
@Transactional(rollbackFor = Exception.class)
public class CleaningServiceImpl implements CleaningService {

    private static final Logger LOG = LoggerFactory.getLogger(CleaningServiceImpl.class);

    @Autowired
    CleaningAreaMapper areaMapper;

    @Autowired
    CleaningTypeMapper typeMapper;

    @Autowired
    CleaningContentMapper contentMapper;

    @Autowired
    CleaningRecordMapper recordMapper;

    @Autowired
    CleaningRecordPicMapper recordPicMapper;

    @Override
    public String addArea(AddAreaReq addAreaReq) {
        String code = validateAreaReq(addAreaReq);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        //添加区域
        CleaningArea area = new CleaningArea();
        area.setAreaName(addAreaReq.getAreaName());
        area.setCreateTime(new Date(System.currentTimeMillis()));
        area.setEstateId(addAreaReq.getEstateId());
        areaMapper.insertSelective(area);
        return null;
    }

    @Override
    public String deleteArea(DeleteAreaReq areaReq) {
        if (!StringUtil.isBlank(areaReq.getAreaId())){
            String[] areaIdArray = areaReq.getAreaId().split(",");
            for (String id:
                 areaIdArray) {
                List<CleaningType> types = typeMapper.findByAreaId(id);
                //区域下有类型，不能删
                if (types != null && types.size() > 0){
                    return CleaningCode.AREA_HAS_TYPE;
                }
            }
            //删除区域
            areaMapper.deleteByAreaIds(areaIdArray);
        }
        return null;
    }

    @Override
    public String addType(AddTypeReq addTypeReq) {
        String code = validateTypeReq(addTypeReq);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        //添加类型
        CleaningType type = new CleaningType();
        type.setTypeName(addTypeReq.getTypeName());
        type.setCreateTime(new Date(System.currentTimeMillis()));
        type.setAreaId(addTypeReq.getAreaId());
        typeMapper.insertSelective(type);
        return null;
    }

    @Override
    public String deleteType(DeleteTypeReq deleteTypeReq) {
        if (!StringUtil.isBlank(deleteTypeReq.getTypeId())){
            String[] typeIdArray = deleteTypeReq.getTypeId().split(",");
            for (String id:
                    typeIdArray) {
                List<CleaningContent> contents = contentMapper.findByTypeId(id);
                //类型下有内容，不能删
                if (contents != null && contents.size() > 0){
                    return CleaningCode.TYPE_HAS_CONTENT;
                }
            }
            //删除类型
            typeMapper.deleteByTypeIds(typeIdArray);
        }
        return null;
    }

    @Override
    public String updateArea(UpdateAreaReq areaReq) {
        String code = validateUpdateAreaReq(areaReq);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        CleaningArea area = new CleaningArea();
        area.setAreaId(areaReq.getAreaId());
        area.setAreaName(areaReq.getAreaName());
        areaMapper.updateByPrimaryKeySelective(area);
        return null;
    }

    @Override
    public String updateType(UpdateTypeReq typeReq) {
        String code = validateUpdateTypeReq(typeReq);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        CleaningType type = new CleaningType();
        type.setTypeName(typeReq.getTypeName());
        type.setTypeId(typeReq.getTypeId());
        typeMapper.updateByPrimaryKeySelective(type);
        return null;
    }

    @Override
    public List<CleaningArea> findAllAreas(String estateId) {
        return areaMapper.findAreas(estateId);
    }

    @Override
    public List<CleaningType> findTypes(String areaId) {
        return typeMapper.findByAreaId(areaId);
    }

    @Override
    public String addContent(AddContentReq contentReq) {
        String code = validateContentReq(contentReq);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        //添加
        CleaningContent content = new CleaningContent();
        content.setEstateId(contentReq.getEstateId());
        content.setContent(contentReq.getContent());
        content.setLocation(contentReq.getLocation());
        content.setCreateTime(new Date(System.currentTimeMillis()));
        content.setTypeId(contentReq.getTypeId());
        content.setAreaId(contentReq.getAreaId());
        content.setUpdateTime(new Date(System.currentTimeMillis()));
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        content.setContentNo("BJ"+dateTime);
        content.setCycle(Integer.parseInt(contentReq.getCycle()));
        contentMapper.insertSelective(content);
        return null;
    }

    @Override
    public List<FindAllContentResp> findAllContent(FindAllContentReq contentReq, Paging<FindAllContentResp> paging) {
        return contentMapper.findAllContent(contentReq,paging.getRowBounds());
    }

    @Override
    public CleaningContent findByContentId(String contentId) {
        return contentMapper.selectByPrimaryKey(contentId);
    }

    @Override
    public FindContentResp findContent(String contentId) {
        return contentMapper.findByContentId(contentId);
    }

    @Override
    public CleaningArea findArea(String areaId) {
        return areaMapper.selectByPrimaryKey(areaId);
    }

    @Override
    public CleaningType findType(String typeId) {
        return typeMapper.selectByPrimaryKey(typeId);
    }

    @Override
    public String updateContent(UpdateContentReq contentReq) {
        if (contentMapper.selectByPrimaryKey(contentReq.getContentId())==null){
            return CleaningCode.CONTENT_IS_DELETE;
        }
        AddContentReq req = new AddContentReq();
        req.setContent(contentReq.getContent());
        req.setLocation(contentReq.getLocation());
        req.setAreaId(contentReq.getAreaId());
        req.setCycle(contentReq.getCycle());
        req.setTypeId(contentReq.getTypeId());
        String code = validateContentReq(req);
        if (!StringUtil.isBlank(code)){
            return code;
        }
        //修改
        CleaningContent content = new CleaningContent();
        content.setCycle(Integer.parseInt(contentReq.getCycle()));
        content.setTypeId(contentReq.getTypeId());
        content.setLocation(contentReq.getLocation());
        content.setContent(contentReq.getContent());
        content.setUpdateTime(new Date(System.currentTimeMillis()));
        content.setAreaId(contentReq.getAreaId());
        content.setContentId(contentReq.getContentId());
        contentMapper.updateByPrimaryKeySelective(content);
        return null;
    }

    @Override
    public String deleteContent(String contentId) {
        if (!StringUtil.isBlank(contentId)){
            String[] contentIdArray = contentId.split(",");
            contentMapper.deleteByContentIds(contentIdArray);
        }
        return null;
    }

    @Override
    public List<ContentRecordInfo> findContentRecords(FindAllContentRecordsReq recordsReq
            , Paging<ContentRecordInfo> paging, HttpServletRequest request) {
        //分页条件查询记录列表
        List<ContentRecordInfo> allContentRecords = recordMapper.findAllContentRecords(recordsReq, paging.getRowBounds());
        if (allContentRecords!=null && allContentRecords.size()>0){
            for (ContentRecordInfo info:
                 allContentRecords) {
                //查询人员名字
                info.setExaminer(findEmp(info.getExaminer(),request));
                //定义图片url集合
                List<String> pics = new ArrayList<>();
                List<SystemPicture> pictures = recordPicMapper.findPictureByRecordId(info.getRecordId());
                if (pictures!=null && pictures.size()>0){
                    for (SystemPicture pic:
                         pictures) {
                        pics.add(ConfigManager.read(ConfigName.FILE_SERVER) + pic.getPictureUrl());
                    }
                    info.setPictureUrls(pics);
                }
            }
        }
        return allContentRecords;
    }

    @Override
    public List<FindAllRecordsResp> findAllRecords(FindAllRecordsReq recordsReq, Paging<FindAllRecordsResp> paging) {
        return recordMapper.findAllRecords(recordsReq,paging.getRowBounds());
    }

    @Override
    public FindRecordResp findRecord(String recordId, HttpServletRequest request) {
        FindRecordResp record = recordMapper.findRecord(recordId);
        if (record != null){
            //查询人员名字
            record.setExaminer(findEmp(record.getExaminer(),request));
            //定义图片url集合
            List<String> pics = new ArrayList<>();
            List<SystemPicture> pictures = recordPicMapper.findPictureByRecordId(record.getRecordId());
            if (pictures!=null && pictures.size()>0){
                for (SystemPicture pic:
                        pictures) {
                    pics.add(ConfigManager.read(ConfigName.FILE_SERVER) + pic.getPictureUrl());
                }
                record.setPictureUrls(pics);
            }
        }
        return record;
    }

    @Override
    public List<FindAllRecordsResp> exportByIds(String recordIds) {
        String[] ids = recordIds.split(",");
        return recordMapper.findByRecordIds(ids);
    }

    @Override
    public List<FindAllRecordsResp> exportByParams(ExportRecordsReq recordsReq) {
        return recordMapper.findByParams(recordsReq);
    }

    private String findEmp(String empId,HttpServletRequest request){
        //httpClient查询考核人员姓名
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("employeeId", empId);
        try {
            Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/common/v1/findUserInfo",jsonParam,request);
            if(null != r){
                JSONObject json = JSONObject.fromObject(r.getData());
                if(null != json.get("employeeName")){
                   return json.get("employeeName").toString();
                }else {
                    return "";
                }
            }else {
                return "";
            }
        }catch (Exception e){
            LOG.error("=====find employeeInfo error===",e);
        }
        return "";
    }

    private String validateContentReq(AddContentReq contentReq){
        //去空格
        if (!StringUtil.isEmpty(contentReq.getContent())){
            contentReq.setContent(contentReq.getContent().trim());
        }
        if (!StringUtil.isEmpty(contentReq.getLocation())){
            contentReq.setLocation(contentReq.getLocation().trim());
        }
        //校验
        if (StringUtil.isBlank(contentReq.getAreaId())){
            return CleaningCode.AREA_ID_NULL;
        }else if (areaMapper.selectByPrimaryKey(contentReq.getAreaId()) == null){
            return CleaningCode.AREA_IS_DELETE;
        }else if (StringUtil.isBlank(contentReq.getTypeId())){
            return CleaningCode.TYPE_ID_NULL;
        }else if (typeMapper.selectByPrimaryKey(contentReq.getTypeId()) == null){
            return CleaningCode.TYPE_IS_DELETE;
        }else if (StringUtil.isBlank(contentReq.getLocation())){
            return CleaningCode.LOCATION_NULL;
        }else if (!Validate.isCommonString(contentReq.getLocation(),CleaningConstant.AREA_NAME_LENGTH)){
            return CleaningCode.LOCATION_ERROR;
        }else if (StringUtil.isBlank(contentReq.getCycle())){
            return CleaningCode.CYCLE_NULL;
        }else if (StringUtil.isBlank(contentReq.getContent())){
            return CleaningCode.CONTENT_NULL;
        }else if (contentReq.getContent().length()>CleaningConstant.CONTENT_MAX_LENGTH){
            return CleaningCode.CONTENT_ERROR;
        }
        return null;
    }

    private String validateTypeReq(AddTypeReq typeReq){
        //去空格
        if (!StringUtil.isEmpty(typeReq.getTypeName())){
            typeReq.setTypeName(typeReq.getTypeName().trim());
        }
        //校验
        if (StringUtil.isBlank(typeReq.getTypeName())){
            return CleaningCode.TYPE_NAME_NULL;
        }else if (!Validate.isCommonString(typeReq.getTypeName(), CleaningConstant.AREA_NAME_LENGTH)){
            return CleaningCode.TYPE_NAME_ERROR;
        }else if (StringUtil.isBlank(typeReq.getAreaId())){
            return CleaningCode.AREA_ID_NULL;
        }else if (areaMapper.selectByPrimaryKey(typeReq.getAreaId()) == null){
            return CleaningCode.AREA_IS_DELETE;
        }else if (typeMapper.findByAreaIdAndTypeName(typeReq.getAreaId(),typeReq.getTypeName()) != null){
            return CleaningCode.TYPE_NAME_SAME;
        }
        return null;
    }

    private String validateUpdateTypeReq(UpdateTypeReq typeReq){
        //去空格
        if (!StringUtil.isEmpty(typeReq.getTypeName())){
            typeReq.setTypeName(typeReq.getTypeName().trim());
        }
        //校验
        if (StringUtil.isBlank(typeReq.getTypeName())){
            return CleaningCode.TYPE_NAME_NULL;
        }else if (!Validate.isCommonString(typeReq.getTypeName(), CleaningConstant.AREA_NAME_LENGTH)){
            return CleaningCode.TYPE_NAME_ERROR;
        }else if (typeMapper.selectByPrimaryKey(typeReq.getTypeId()) == null){
            return CleaningCode.TYPE_IS_DELETE;
        }else{
            CleaningType oldType = typeMapper.selectByPrimaryKey(typeReq.getTypeId());
            CleaningType type = typeMapper.findByAreaIdAndTypeName(oldType.getAreaId(),typeReq.getTypeName());
            //类型重复
            if (!oldType.getTypeName().equals(typeReq.getTypeName())
                    && type != null){
                return CleaningCode.TYPE_NAME_SAME;
            }
        }
        return null;
    }

    private String validateAreaReq(AddAreaReq areaReq){
        //去空格
        if (!StringUtil.isEmpty(areaReq.getAreaName())){
            areaReq.setAreaName(areaReq.getAreaName().trim());
        }
        //校验
        if (StringUtil.isBlank(areaReq.getAreaName())){
            return CleaningCode.AREA_NAME_NULL;
        }else if (!Validate.isCommonString(areaReq.getAreaName(), CleaningConstant.AREA_NAME_LENGTH)){
            return CleaningCode.AREA_NAME_ERROR;
        }else if (areaMapper.findByName(areaReq.getAreaName()) != null){
            return CleaningCode.AREA_NAME_SAME;
        }
        return null;
    }

    private String validateUpdateAreaReq(UpdateAreaReq areaReq){
        //去空格
        if (!StringUtil.isEmpty(areaReq.getAreaName())){
            areaReq.setAreaName(areaReq.getAreaName().trim());
        }
        //校验
        if (StringUtil.isBlank(areaReq.getAreaName())){
            return CleaningCode.AREA_NAME_NULL;
        }else if (!Validate.isCommonString(areaReq.getAreaName(), CleaningConstant.AREA_NAME_LENGTH)){
            return CleaningCode.AREA_NAME_ERROR;
        }else if (areaMapper.selectByPrimaryKey(areaReq.getAreaId()) == null){
            return CleaningCode.AREA_IS_DELETE;
        } else {
            CleaningArea area = areaMapper.findByName(areaReq.getAreaName());
            CleaningArea oldArea = areaMapper.selectByPrimaryKey(areaReq.getAreaId());
            //区域名称重复
            if (!oldArea.getAreaName().equals(areaReq.getAreaName())
                    && area != null){
                return CleaningCode.AREA_NAME_SAME;
            }
        }
        return null;
    }
}

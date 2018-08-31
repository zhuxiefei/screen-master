package com.betel.estatemgmt.business.web.lease.service.impl;

import com.betel.estatemgmt.business.propertyapp.lease.model.SaveLeaseReq;
import com.betel.estatemgmt.business.web.lease.model.*;
import com.betel.estatemgmt.business.web.lease.service.WebLeaseService;
import com.betel.estatemgmt.common.mapper.house.OfficeHouseMapper;
import com.betel.estatemgmt.common.mapper.lease.RentMapper;
import com.betel.estatemgmt.common.mapper.system.SystemFileMapper;
import com.betel.estatemgmt.common.model.house.OfficeHouse;
import com.betel.estatemgmt.common.model.lease.Rent;
import com.betel.estatemgmt.common.model.system.SystemFile;
import com.betel.estatemgmt.utils.UuidUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文件说明
 * </p>
 * Author: geyf  <br/>
 * Date: 2018/5/8 15:51 <br/>
 * Version: 1.0 <br/>
 */
@Service
@Transactional
public class WebLeaseServiceImpl implements WebLeaseService{

    private static final Logger LOG = LoggerFactory.getLogger(WebLeaseServiceImpl.class);

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private SystemFileMapper systemFileMapper;

    @Autowired
    private OfficeHouseMapper officeHouseMapper;

    @Override
    public boolean houseIsDelete(String houseId) {
        OfficeHouse officeHouse = officeHouseMapper.selectByPrimaryKey(houseId);
        return officeHouse == null;
    }

    @Override
    public void modifyLease(SaveLeaseReq req) {
        Rent rent = new Rent();
        BeanUtils.copyProperties(req, rent);
        rent.setHouseId(req.getAddress());
        rent.setIsUpload(0);
        rent.setRentId(req.getRentId());
        rentMapper.updateByPrimaryKeySelective(rent);

        if(req.getStartTime().getTime() <= System.currentTimeMillis()){
            // 计租时间小于等于当前时间，即刻修改房屋状态为出租中
            OfficeHouse officeHouse = new OfficeHouse();
            officeHouse.setHouseId(rent.getHouseId());
            officeHouse.setHouseStatus(3);
            officeHouseMapper.updateByPrimaryKeySelective(officeHouse);
        }

    }

    @Override
    public WebLeaseInfo findWebLeaseInfo(String rentId) {
        WebLeaseInfo webLeaseInfo = rentMapper.findWebLeaseInfo(rentId);
        Date date = new Date();
        if(webLeaseInfo != null){
            if(webLeaseInfo.getEndTime().getTime() < date.getTime()){
                // 失效
                webLeaseInfo.setIsInvalid(2);
                webLeaseInfo.setHouseStatus(null);
            }else{
                if(webLeaseInfo.getStartTime().getTime() > date.getTime()){
                    // 待生效
                    webLeaseInfo.setIsInvalid(3);
                }else {
                    // 已生效
                    webLeaseInfo.setIsInvalid(1);
                }
            }
        }
        return webLeaseInfo;
    }

    @Override
    public DownloadPDFResp findPDFUrl(String rentId) {
        Rent rent = rentMapper.selectByPrimaryKey(rentId);
        SystemFile systemFile = systemFileMapper.selectByPrimaryKey(rent.getFileId());
        DownloadPDFResp PDF = new DownloadPDFResp();
        PDF.setName(systemFile.getFileName());
        PDF.setURL(systemFile.getFileUrl());
        return PDF;
    }

    @Override
    public void savePDF(String rentId, String filename, String newFilename) {
        SystemFile sysFile = new SystemFile();
        sysFile.setFileId(UuidUtil.create());
        sysFile.setFileName(filename);
        sysFile.setFileUrl("/lease/" + newFilename);
        sysFile.setCreateTime(new Date());
        systemFileMapper.insert(sysFile);
        Rent r = new Rent();
        r.setRentId(rentId);
        r.setIsUpload(1);
        r.setFileId(sysFile.getFileId());
        rentMapper.updateByPrimaryKeySelective(r);
    }

    @Override
    public List<WebLeaseList> findAll(Paging<WebLeaseList> pager, WebFindAllLeaseReq req) {
        List<WebLeaseList> allWebList = rentMapper.findAllWebList(pager.getRowBounds(), req);
        Date date = new Date();
        if(!allWebList.isEmpty()){
            if(req.getIsInvalid() == 0){
                for(WebLeaseList wll : allWebList){
                    if(wll.getEndTime().getTime() < date.getTime()){
                        // 失效
                        wll.setIsInvalid(2);
                    }else{
                        if(wll.getStartTime().getTime() > date.getTime()){
                            // 待生效
                            wll.setIsInvalid(3);
                        }else {
                            // 已生效
                            wll.setIsInvalid(1);
                        }
                    }
                }
            }else{
                for(WebLeaseList wll : allWebList){
                    wll.setIsInvalid(req.getIsInvalid());
                }
            }
        }
        return allWebList;
    }

    @Override
    public boolean isLeaseTimeConflict(TimeConflict timeConflict) {
        List<Rent> leaseTimeConflict = rentMapper.isLeaseTimeConflict(timeConflict);
        return !(leaseTimeConflict == null || leaseTimeConflict.isEmpty());
    }

    @Override
    public boolean isLeaseTimeConflictModify(TimeConflict timeConflict) {
        List<Rent> leaseTimeConflict = rentMapper.isLeaseTimeConflict(timeConflict);
        if(leaseTimeConflict != null && leaseTimeConflict.size() == 1){
            if(leaseTimeConflict.get(0).getRentId().equals(timeConflict.getRentId())){
                return false;
            }
        }
        return !(leaseTimeConflict == null || leaseTimeConflict.isEmpty());
    }
}
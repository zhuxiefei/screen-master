package com.betel.estatemgmt.business.userapp.house.service.impl;

import com.betel.estatemgmt.business.userapp.house.model.HousePicture;
import com.betel.estatemgmt.business.userapp.house.model.ShareURL;
import com.betel.estatemgmt.business.userapp.house.model.UserHouseAuth;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.model.HouseInfo;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.mapper.estate.EstateMapper;
import com.betel.estatemgmt.common.mapper.house.*;
import com.betel.estatemgmt.common.mapper.system.PictureMapper;
import com.betel.estatemgmt.common.model.estate.Estate;
import com.betel.estatemgmt.common.model.house.*;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: HouseServiceImp <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/20 11:10 <br/>
 * Version: 1.0 <br/>
 */
@Transactional
@Service
public class HouseServiceImpl implements HouseService{
   private static final Logger LOG = LoggerFactory.getLogger(HouseServiceImpl.class);

    private static String estateType_house = "1";
    @Autowired
    HouseMapper houseMapper;
    @Autowired
    HouseMemberMapper houseMemberMapper;
    @Autowired
    BuildingMapper buildingMapper;
    @Autowired
    BuildingUnitMapper buildingUnitMapper;
    @Autowired
    HousePicMapper housePicMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    HousePicShareMapper housePicShareMapper;

    @Autowired
    OfficeHouseMapper officeHouseMapper;

    @Autowired
    EstateMapper estateMapper;

    @Override
    public List<House> selecteHouseVillas(String estateId) {
        List<House> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            list = houseMapper.selecteHouseVillas(estateId);
        }else {
            list = officeHouseMapper.selecteHouseVillas(estateId);
        }
        return list;
    }

    @Override
    public List<Building> selecteAllBuilding(String estateId) {
        return buildingMapper.findAllBuildings(estateId);
    }

    @Override
    public List<BuildingUnit> selecteUnitByBuildId(Long buildId) {
        return buildingUnitMapper.selecteUnitByBuildId(buildId);
    }

    @Override
    public List<House> selecteUnitByBuildIdUnitNull(Long buildId,String estateId) {
        List<House> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            list = houseMapper.selectHouseByBuildIdUnitNull(buildId);
        }else {
            list = officeHouseMapper.selectHouseByBuildIdUnitNull(buildId);
        }
        return list;
    }

    @Override
    public List<House> selecteHouseByUnitId(Long unitId,String estateId) {
        List<House> list = new ArrayList<>();
        Estate estate = estateMapper.selectByPrimaryKey(estateId);
        if(estateType_house.equals(estate.getEstateType())){
            list = houseMapper.selectByUnitId(unitId);
        }else {
            list = officeHouseMapper.selectByUnitId(unitId);
        }
        return list;
    }

    @Override
    public List<HousePicture> findHousePicByHouseId(String houseId) {
        return housePicMapper.selectHousePicByHouseId(houseId);
    }

    @Override
    public HousePicture findHousePictureByHousePicID(Long housepicId) {
        return housePicMapper.selectHouseCadPicByhousePicId(housepicId);
    }

    @Override
    public UserHouseAuth findHouseBuildUnitByHouseId(String houseId) {
        return houseMapper.findHouseBuildUnitByHouseId(houseId);
    }

    @Override
    public void addShareAndPic(HousePictureShare housePicShare, Picture picture) {
        pictureMapper.insertSelective(picture);
        if(LOG.isDebugEnabled()){
            LOG.debug("-------picture----------"+picture);
        }
        housePicShare.setShareUrl(picture.getPictureId());
        if(LOG.isDebugEnabled()){
            LOG.debug("-------picture----------"+picture);
        }
        housePicShareMapper.insertHousePicShare(housePicShare);
    }

    @Override
    public void deleteSharePicture() {
        List<ShareURL> shareURLs= housePicShareMapper.selectShareOutOfTime();
        if(shareURLs!=null){
            if(LOG.isDebugEnabled()){
                LOG.debug("-------shareURLs----------"+shareURLs);
            }
            for (ShareURL shareUrl:shareURLs) {
                pictureMapper.deleteByPrimaryKey(shareUrl.getPictureId());
                FileUtil.deletefile(ConfigManager.read(ConfigName.FILE_DIR)+shareUrl.getUrl());
            }
            if(LOG.isDebugEnabled()){
                LOG.debug("-------shareURLs----------"+shareURLs);
            }
        }
    }

    @Override
    public ShareURL selectShareByShareId(String id) {
        return housePicShareMapper.selectShareByShareId(id);
    }

    @Override
    public House findHouseByHouseId(String houseId) {
        return houseMapper.selectByPrimaryKey(houseId);
    }

    @Override
    public List<OfficeHouse> selecteHouseByUnitIdOffice(String id) {
        List<HouseInfo> list = officeHouseMapper.selectByIds(null, id);
        List<OfficeHouse> officeHouses = new ArrayList<>();
        for(HouseInfo h : list){
            OfficeHouse officeHouse = new OfficeHouse();
            officeHouse.setHouseId(h.getHouseId());
            officeHouse.setHouseNum(h.getHouseNum());
            officeHouses.add(officeHouse);
        }
        return officeHouses;
    }
}

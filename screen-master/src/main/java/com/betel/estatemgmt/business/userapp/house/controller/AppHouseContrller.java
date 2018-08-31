package com.betel.estatemgmt.business.userapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.*;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.userapp.house.service.AppMemberService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.house.model.HouseIdsReq;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HousePictureShare;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 我的家图纸模块控制层
 * </p>
 * ClassName: HouseContrller <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/21 8:49 <br/>
 * Version: 1.0 <br/>
 */
@Controller
@RequestMapping("userApp/house")
public class AppHouseContrller extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AppHouseContrller.class);
    @Autowired
    HouseService houseService;

    @Autowired
    AppMemberService appMemberService;

    @Autowired
    AppAuthService appAuthService;

    /**
     * <p>
     * 查询所有的房屋
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 13:02
     * return response
     */
    @RequestMapping(value = "v1/findAllHouse", method = RequestMethod.GET)
    @ResponseBody
    public Response<AllHouse> findAllHouse(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/house/v1/findAllHouse---------start");
        }
        Response<AllHouse> response = new Response<>();
        //房屋分类模型
        AllHouse allHouse = new AllHouse();
        //所有的楼宇
        List<Builds> builds = new ArrayList<>();
        List<Building> buildings;
        try {
            String estateId = request.getHeader("estateId");
            //如果为空 默认为锋尚
            if(null == estateId){
                estateId = AESUtil.encrypt("1");
            }
            buildings = houseService.selecteAllBuilding(AESUtil.decrypt(estateId));

            //每个楼宇里分类单元和房屋
            for (Building building : buildings) {
                Builds build = new Builds();
                build.setBuildingName(building.getBuildingName());
                List<UnitOne> unitOnes = new ArrayList<>();
                //楼宇下的单元
                List<BuildingUnit> buildingUnits;
                buildingUnits = houseService.selecteUnitByBuildId(building.getBuildingId());

                //每个单元里分类房屋
                for (BuildingUnit buildingUnit : buildingUnits) {
                    UnitOne unitone = new UnitOne();
                    unitone.setUnitName(buildingUnit.getUnitName());
                    unitone.setHouses(houseService.selecteHouseByUnitId(buildingUnit.getUnitId(),AESUtil.decrypt(estateId)));
                    unitOnes.add(unitone);
                }
                build.setUnits(unitOnes);
                //楼宇下单元为空的房屋
                List<House> houses = houseService.selecteUnitByBuildIdUnitNull(building.getBuildingId(),AESUtil.decrypt(estateId));
                build.setLittltvilla(houses);
                builds.add(build);
            }
            allHouse.setBuilds(builds);
            allHouse.setVilla(houseService.selecteHouseVillas(AESUtil.decrypt(estateId)));
            response.setData(allHouse);
        } catch (Exception e) {
            LOG.error("-----app/house/v1/findAllHouse---------error" + e);
            response.setCode(StatusCode.FAILURE);
            return response;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-------app/house/v1/findAllHouse----------" + response.toString());
        }
        return response;
    }

    /**
     * <p>
     * 查询房屋图纸
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 14:04
     * return response
     */
    @RequestMapping(value = "v1/findHousePicture", method = RequestMethod.GET)
    @ResponseBody
    public Response findHousePicture(House house, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/house/v1/findHousePicture---------start" + house.getHouseId());
        }
        Response<List> response = new Response<>();
        String houseId = house.getHouseId();
        //验证房屋id是否为空
        if (houseId == null) {
            response.setCode(HouseCode.HOUSEIDNULL);
        } else {
            //验证房屋是否存在
            House housees = houseService.findHouseByHouseId(houseId);
            if (LOG.isDebugEnabled()) {
                LOG.debug("-------houses----------" + housees);
            }
            if (housees == null) {
                response.setCode(HouseCode.HOUSEDELETE);
            } else {
                //验证是否是房屋的成员
                String userId = request.getHeader("userId");
                List<AuthHouse> lists = null;
                try {
                    lists = appAuthService.findHouseAuth(AESUtil.decrypt(userId), HouseConstant.TWO);
                } catch (Exception e) {
                    LOG.error("-----app/house/v1/findHousePicture---------error------", e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
                boolean flag = false;
                for (AuthHouse list : lists) {
                    if (list.getHouseId().equals(houseId.toString())) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    List<HousePicture> housePictures;
                    try {
                        //查询房屋图纸
                        housePictures = houseService.findHousePicByHouseId(house.getHouseId());
                        if (LOG.isInfoEnabled()) {
                            LOG.info("-------housePictures----------" + housePictures);
                        }
                    } catch (Exception e) {
                        LOG.error("-----app/house/v1/findHousePicture---------error------", e);
                        response.setCode(StatusCode.FAILURE);
                        return response;
                    }
                    //处理图片路径
                    for (HousePicture housePicture : housePictures) {
                        housePicture.setPictureUrl(ConfigManager.read(ConfigName.FILE_SERVER) + housePicture.getPictureUrl());
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("-----housePictures---------" + housePictures);
                    }
                    response.setData(housePictures);
                } else {
                    response.setCode(HouseCode.NOT_HOUSE_MEMBER);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/house/v1/findHousePicture---------end----" + response);
        }
        return response;
    }

    /**
     * <p>
     * 分享图纸
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 11:58
     * return response
     */
    @RequestMapping("v1/sharePicture")
    @ResponseBody
    public Response sharePicture(@RequestBody HousePicture housePicture, HttpServletRequest request) throws UnsupportedEncodingException, ParseException {
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/house/v1/sharePicture---------start" + housePicture.getHpId());
        }
        Response<ShareURL> response = new Response<>();
        String userId = request.getHeader("userId");
        if (StringUtil.isEmpty(userId)) {
            response.setCode(StatusCode.UNAUTHORIZED);
        } else {
            try {
                userId = AESUtil.decrypt(userId);
            } catch (Exception e) {
                LOG.error("-----app/house/v1/sharePicture---------error", e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
            Long hpId = housePicture.getHpId();
            if (hpId == null) {
                response.setCode(HouseCode.HOUSEPICIDNULL);
            } else {
                //查询分享的房屋图片和CAD
                try {
                    housePicture = houseService.findHousePictureByHousePicID(hpId);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("-----housePicture---------" + housePicture);
                    }
                } catch (Exception e) {
                    LOG.error("-----app/house/v1/sharePicture---------error", e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
                if (housePicture != null) {
                    if (!StringUtil.isEmpty(housePicture.getPictureUrl())) {
                        //获取该图纸对应的房屋信息
                        UserHouseAuth userHouseAuth;
                        try {
                            userHouseAuth = houseService.findHouseBuildUnitByHouseId(housePicture.getHouseId());
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("-----userHouseAuth---------" + userHouseAuth);
                            }
                        } catch (Exception e) {
                            LOG.error("-----app/house/v1/sharePicture---------error", e);
                            response.setCode(StatusCode.FAILURE);
                            return response;
                        }
                        String fileName = dealHouseName(userHouseAuth);
                        //创建压缩文件
                        List<File> files = new ArrayList<>();
                        //记录复制文件的路径
                        String deleteFile1 = null;
                        String deleteFile2 = null;
                        //获取房屋的pic和cad图纸文件
                        if (!StringUtil.isEmpty(housePicture.getPictureUrl())) {
                            File file1 = new File(ConfigManager.read(ConfigName.FILE_DIR) + housePicture.getPictureUrl());
                            //文件修改名称
                            String name = ConfigManager.read(ConfigName.FILE_DIR) + "house/" + housePicture.getPictureType() + "图片.jpg";
                            File file = new File(name);
                            deleteFile1 = name;
                            try {
                                copyfile(file1, file);
                            } catch (Exception e) {
                                response.setCode(StatusCode.FAILURE);
                                LOG.error("-----app/house/v1/sharePicture---------error", e);
                                return response;
                            }
                            files.add(file);
                        }
                        if (!StringUtil.isEmpty(housePicture.getCadUrl())) {
                            File file2 = new File(ConfigManager.read(ConfigName.FILE_DIR) + housePicture.getCadUrl());
                            //重命名
                            String name = ConfigManager.read(ConfigName.FILE_DIR) + "house/" + housePicture.getPictureType() + "图纸.dwt";

                            File file = new File(name);
                            deleteFile2 = name;
                            try {
                                copyfile(file2, file);
                            } catch (Exception e) {
                                LOG.error("-----app/house/v1/sharePicture---------error", e);
                                response.setCode(StatusCode.FAILURE);
                                return response;
                            }
                            files.add(file);
                        }
                        if (files.size() > 0) {
                            //生成压缩包
                            Zip.createZip(fileName + ".zip", HouseConstant.ZIP, files);
                        } else {
                            response.setCode(HouseCode.HOUSEPICIDNULL);
                            return response;
                        }
                        //删除复制的文件
                        if (deleteFile2 != null) {
                            new File(deleteFile2).delete();
                        }
                        if (deleteFile1 != null) {
                            new File(deleteFile1).delete();
                        }
                        //生成随机密码
                        String key = getRandomString(4);
                        //将数据加入图片分享表和图片表
                        HousePictureShare housePicShare = new HousePictureShare();
                        Date date = new Date();
                        housePicShare.setCreateTime(date);
                        housePicShare.setShareKey(key);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        housePicShare.setDatetime(format.parse(plusDay(Integer.parseInt(ConfigManager.read("share.expire.time")), format.format(date))));
                        housePicShare.setShareUser(userId);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("-----housePicShare---------" + housePicShare);
                        }
                        //封装picture
                        Picture picture = new Picture();
                        picture.setCreateTime(date);
                        picture.setPictureName(fileName);
                        picture.setPictureUrl("shareZip/" + fileName + ".zip");
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("-----picture---------" + picture);
                        }
                        //压缩文件保存到数据库
                        try {
                            houseService.addShareAndPic(housePicShare, picture);
                        } catch (Exception e) {
                            LOG.error("-----app/house/v1/sharePicture---------error", e);
                            response.setCode(StatusCode.FAILURE);
                            FileUtil.deletefile(HouseConstant.ZIP + fileName + ".zip");
                            return response;
                        }
                        //返回加密路径和随机密码
                        String url;
                        try {
                            url = ConfigManager.read(HouseConstant.SHARE) + "share/" + AESUtil.encrypt(housePicShare.getShareId().toString());
                        } catch (Exception e) {
                            LOG.error("-----app/house/v1/sharePicture---------error", e);
                            response.setCode(StatusCode.FAILURE);
                            return response;
                        }
                        ShareURL shareUrl = new ShareURL(url, key);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("-----shareUrl---------" + shareUrl);
                        }
                        response.setData(shareUrl);
                    } else {
                        response.setCode(HouseCode.FILEPATHWRONG);
                    }
                } else {
                    response.setCode(HouseCode.HOUSEPICIDNULL);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/house/v1/sharePicture---------end----" + response.toString());
        }
        return response;
    }





    /**
     * <p>
     * 处理房屋名称
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 17:11
     * return String
     */
    private String dealHouseName(UserHouseAuth userHouseAuth) {
        String buildingName = userHouseAuth.getBuildingName();
        String unitName = userHouseAuth.getUnitName();
        String houseNum = userHouseAuth.getHouseNum();
        if (StringUtil.isEmpty(buildingName)) {
            buildingName = "";
        }
        if (StringUtil.isEmpty(unitName)) {
            unitName = "";
        }
        if (StringUtil.isEmpty(houseNum)) {
            houseNum = "";
        }
        return buildingName + unitName + houseNum;
    }

    /**
     * <p>
     * 随机生成自定义长度的字符串
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 9:30
     * return String
     */
    private static String getRandomString(int length) { // length 字符串长度
        StringBuilder sb = new StringBuilder();
        StringBuilder buffer = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * <p>
     * 文件复制
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/7/7 11:06
     * return
     */
    private static void copyfile(File file1, File file2) {
        try {
            FileUtils.copyFile(file1, file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * * 指定日期加上天数后的日期
     * * @param num 为增加的天数
     * * @param newDate 创建时间
     * * @return
     * * @throws ParseException
     */
    private static String plusDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        // num为增加的天数，可以改变的
        ca.add(Calendar.DATE, num);
        curate = ca.getTime();
        String enddate = format.format(curate);
        return enddate;
    }
}

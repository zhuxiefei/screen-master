package com.betel.estatemgmt.business.userapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.constant.HouseConstant;
import com.betel.estatemgmt.business.userapp.house.model.AuthHouse;
import com.betel.estatemgmt.business.userapp.house.model.Materials;
import com.betel.estatemgmt.business.userapp.house.model.Page;
import com.betel.estatemgmt.business.userapp.house.service.AppAuthService;
import com.betel.estatemgmt.business.userapp.house.service.AppMaterialService;
import com.betel.estatemgmt.business.userapp.house.service.AppMemberService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HouseMaterialParm;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 家装材料模块控制层.
 * </p>
 * ClassName: AppMaterialContrller <br/>
 * Author: zhouye  <br/>
 * Date: 2017/6/22 18:35 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("userApp/material")
public class AppMaterialContrller {
    private static final Logger LOG = LoggerFactory.getLogger(AppMaterialContrller.class);

    @Autowired
    AppMaterialService appMaterialService;

    @Autowired
    AppMemberService appMemberService;

    @Autowired
    HouseService houseService;

    @Autowired
    AppAuthService appAuthService;

    /**
     * <p>
     * 查询家装材料
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/22 19:01
     * return response
     */
    @RequestMapping(value = "v1/findAllMaterial", method = RequestMethod.GET)
    public Response findAllMaterial(@ModelAttribute Page page, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("------------app/material/v1/findAllMaterial---------------start---" + page);
        }
        Response<Paging> response = new Response<>();
        //验证房屋id不能为空
        if (null == page.getHouseId()) {
            response.setCode(HouseCode.HOUSEIDNULL);
        } else {
            House house = houseService.findHouseByHouseId(page.getHouseId());
            if (house == null) {
                response.setCode(HouseCode.HOUSEDELETE);
            } else {
                //验证是否是房屋的成员
                String userId = request.getHeader("userId");
                List<AuthHouse> lists = null;
                try {
                    lists = appAuthService.findHouseAuth(AESUtil.decrypt(userId), HouseConstant.TWO);
                } catch (Exception e) {
                    LOG.info("app/material/v1/findAllMaterial---------------error---" + e);
                    response.setCode(StatusCode.FAILURE);
                    return response;
                }
                boolean flag = false;
                for (AuthHouse list : lists) {
                    if (list.getHouseId().equals(page.getHouseId())) {
                        flag = true;
                    }
                }
                if (flag) {
                    Paging<Materials> paging = new Paging<>(page.getCurPage(), page.getPageSize());
                    //根据条件房屋ID 和 功能区域id 查询材料的列表
                    List<Materials> houseMaterials;
                    try {
                        houseMaterials = appMaterialService.findAllMaterialByHouseId(paging, page);
                    } catch (Exception e) {
                        LOG.info("app/material/v1/findAllMaterial---------------error---" + e);
                        response.setCode(StatusCode.FAILURE);
                        return response;
                    }
                    //处理时间格式（-年-月-日）和图片服务器路径
                    for (Materials houseMaterial : houseMaterials) {
                        if (!StringUtil.isEmpty(houseMaterial.getMaterialPic())) {
                            houseMaterial.setMaterialPic(ConfigManager.read(ConfigName.FILE_SERVER) + houseMaterial.getMaterialPic());
                        }
                        String createTime = updateDate(houseMaterial.getMaterialWarrantyDate());
                        houseMaterial.setMaterialWarrantyDate(createTime);
                    }
                    paging.result(houseMaterials);
                    response.setData(paging);
                }else {
                    response.setCode(HouseCode.NOT_HOUSE_MEMBER);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("app/material/v1/findAllMaterial---------------end---" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询家装材料的详情
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 11:10
     * return response
     */

    @RequestMapping(value = "v1/findMaterial", method = RequestMethod.GET)
    public Response findMaterial(Materials materials) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-----app/material/v1/findMaterial---------------start---" + materials.toString());
        }
        Response<Materials> response = new Response<>();
        Long materialsId = materials.getMaterialId();
        List<HouseMaterialParm> materialParms;
        //验证材料id不为空
        if (materialsId == null) {
            response.setCode(HouseCode.MATERIAL_ID_NULL);
        } else {
            try {
                materials = appMaterialService.findMaterialBymaterialId(materialsId);
                //查询该材料的自定义参数
                materialParms = appMaterialService.finAllmaterialParmsBymaterialsId(materialsId);
            } catch (Exception e) {
                LOG.error("app/material/v1/findMaterial---------------error---", e);
                response.setCode(StatusCode.FAILURE);
                return response;
            }
            if (materials == null) {
                response.setCode(HouseCode.MATERIALDELETE);
            } else {
                if (!StringUtil.isEmpty(materials.getMaterialPic())) {
                    //处理图片路径
                    materials.setMaterialPic(ConfigManager.read(ConfigName.FILE_SERVER) + materials.getMaterialPic());
                }
                //处理时间类型
                String produceDate = updateDate(materials.getMaterialProduceDate());
                String warrantyDate = updateDate(materials.getMaterialWarrantyDate());
                materials.setMaterialWarrantyDate(warrantyDate);
                materials.setMaterialProduceDate(produceDate);
                //放置自定义参数
                materials.setMaterialparms(materialParms);
                response.setData(materials);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("app/material/v1/findMaterial---------------end---" + response);
        }
        return response;
    }

    /**
     * <p>
     * 处理家装材料的日期处理
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/23 10:58
     * return response
     */
    private String updateDate(String date) {
        if (StringUtil.isEmpty(date)) {
            return null;
        } else {
            date = date.substring(0, 10);
            String[] dates = date.split("-");
            date = dates[0] + "年" + dates[1] + "月" + dates[2] + "日";
            return date;
        }
    }
}

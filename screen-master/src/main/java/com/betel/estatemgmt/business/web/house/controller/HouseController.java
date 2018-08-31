package com.betel.estatemgmt.business.web.house.controller;

import com.betel.estatemgmt.business.web.ad.model.Pic;
import com.betel.estatemgmt.business.web.house.code.HouseCode;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.house.model.*;
import com.betel.estatemgmt.business.web.house.service.HouseService;
import com.betel.estatemgmt.business.web.housetype.code.HouseTypeCode;
import com.betel.estatemgmt.business.web.housetype.constant.HouseTypeConstant;
import com.betel.estatemgmt.common.BaseController;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HousePic;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.returncode.GlobalCode;
import com.betel.estatemgmt.utils.*;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.betel.estatemgmt.business.web.login.service.WebLoginService;

/**
 * <p>
 * 房屋信息维护接口
 * </p>
 * ClassName: HouseController <br/>
 * Author: xiayanxin <br/>
 * Date: 2017/6/19 14:07 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/house")
public class HouseController extends BaseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private WebLoginService webLoginService;

    private static final Logger LOG = LoggerFactory.getLogger(HouseController.class);

    /**
     * <p>
     * 查询房屋列表（分页）
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/19 15:51
     *
     * @param housePage 分页入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-findAllHouse")
    @RequestMapping(value = "v1/findAllHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Paging<HouseInfo>> findAllHouse(@RequestBody HousePageReq housePage, HttpServletRequest request) throws Exception {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findAllHouse start========housePage=" + housePage);
        }
        Response<Paging<HouseInfo>> response = new Response<>();
        Paging<HouseInfo> pager = new Paging<>(housePage.getCurPage(), housePage.getPageSize());
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            housePage.setEstateId(estateId);
            String code = check(trim(housePage));
            if (null == code) {
                //判断楼宇单元是否存在
                if (housePage.getBuildingId() != null && houseService.findByBuildingId(housePage.getBuildingId()) == null) {
                    response.setCode(HouseCode.BUILDING_NOT_EXIST);
                } else if (housePage.getUnitId() != null && houseService.findByUnitId(housePage.getUnitId()) == null) {
                    response.setCode(HouseCode.UNIT_IS_NULL);
                } else {
                    if (!StringUtil.isBlank(housePage.getHouseNum()) && housePage.getHouseNum().contains("_")) {
                        housePage.setHouseNum(housePage.getHouseNum().replace("_", "\\_"));
                    }
                    if (!StringUtil.isBlank(housePage.getTypeName()) && housePage.getTypeName().contains("_")) {
                        housePage.setTypeName(housePage.getTypeName().replace("_", "\\_"));
                    }
                    if (!StringUtil.isBlank(housePage.getOwnerName()) && housePage.getOwnerName().contains("_")) {
                        housePage.setOwnerName(housePage.getOwnerName().replace("_", "\\_"));
                    }
                    //查询房屋列表
                    List<HouseInfo> list = houseService.findAllHouse(pager, housePage);
                    pager.result(list);
                    response.setData(pager);
                }
            } else {
                response.setCode(code);
            }
        } catch (Exception e) {
            LOG.error("========web/house/v1/findAllHouse error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findAllHouse end========response" + response);
        }
        return response;
    }

    private HousePageReq trim(HousePageReq housePage) {
        if (!StringUtil.isEmpty(housePage.getHouseNum())) {
            housePage.setHouseNum(housePage.getHouseNum().trim());
        }
        if (!StringUtil.isEmpty(housePage.getTypeName())) {
            housePage.setTypeName(housePage.getTypeName().trim());
        }
        if (!StringUtil.isEmpty(housePage.getOwnerName())) {
            housePage.setOwnerName(housePage.getOwnerName().trim());
        }
        return housePage;
    }

    private String check(HousePageReq housePage) {
        if (!StringUtil.isBlank(housePage.getHouseNum())) {
            if (!housePage.getHouseNum().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
                return HouseCode.HOUSE_NUM_RULE;
            }
        }
        if (!StringUtil.isBlank(housePage.getTypeName())) {
            if (!housePage.getTypeName().matches(HouseTypeConstant.HOUSETYPE_FORMAT)) {
                return HouseTypeCode.HOUSETYPE_FORMAT_ERROR;
            }
        }
        if (!StringUtil.isBlank(housePage.getOwnerName())) {
            if (!housePage.getOwnerName().matches(HouseTypeConstant.HOUSETYPE_FORMAT)) {
                return HouseCode.HOUSE_OWER_FIND_ERROR;
            }
        }
        if (!StringUtil.isBlank(housePage.getIsBuy())) {
            if (!housePage.getIsBuy().matches(HouseDataValidation.SPACE_TYPE_RULE)) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        if (!StringUtil.isBlank(housePage.getIsRent())) {
            if (!housePage.getIsRent().matches(HouseDataValidation.SPACE_TYPE_RULE)) {
                return GlobalCode.STATUS_CODE_IS_ERROR;
            }
        }
        return null;
    }

    /**
     * <p>
     * 添加房屋
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/19 16:29
     *
     * @param updateHouseReq 添加房屋入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-addHouse")
    @RequestMapping(value = "v1/addHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> addHouse(@RequestBody UpdateHouseReq updateHouseReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/addHouse start========addHouseReq=" + updateHouseReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            updateHouseReq.setEstateId(estateId);
            //校验
            if (validateAddHouse(updateHouseReq) != null) {
                response.setCode(validateAddHouse(updateHouseReq));
            } else {
                //将数据封装到house对象中
                House house = new House();
                if (StringUtil.isBlank(updateHouseReq.getBuildingId())) {
                    house.setBuildingId(null);
                } else {
                    house.setBuildingId(Long.parseLong(updateHouseReq.getBuildingId()));
                }
                if (StringUtil.isBlank(updateHouseReq.getUnitId())) {
                    house.setUnitId(null);
                } else {
                    house.setUnitId(Long.parseLong(updateHouseReq.getUnitId()));
                }
                house.setCreateTime(new Date(System.currentTimeMillis()));
                house.setHouseNum(updateHouseReq.getHouseNum());
                if (!StringUtil.isBlank(updateHouseReq.getTypeId())) {
                    house.setTypeId(Long.parseLong(updateHouseReq.getTypeId()));
                }
                house.setFloorArea(Double.parseDouble(updateHouseReq.getFloorArea()));
                house.setInterFloorArea(Double.parseDouble(updateHouseReq.getInterFloorArea()));
                house.setHouseStatus(HouseDataValidation.HOUSE_IS_EMPTY);
                house.setPetStatus(HouseDataValidation.PET_IS_EMPTY);
                if (!StringUtil.isBlank(updateHouseReq.getDisplayOrder())) {
                    house.setDisplayOrder(Integer.parseInt(updateHouseReq.getDisplayOrder()));
                }
                house.setEstateId(estateId);
                houseService.addHouse(house, updateHouseReq);
            }
        } catch (Exception e) {
            LOG.error("========web/house/v1/addHouse error!=========", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/addHouse end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 批量导入房屋
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/20 16:51
     *
     * @param multipartFile Excel文件入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-importHouse")
    @RequestMapping(value = "v1/importHouse", method = RequestMethod.POST)
    public Response<Object> importHouse(MultipartFile multipartFile, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/importHouse start========multipartFile=" + multipartFile);
        }
        Response<Object> response = new Response<>();
        if (multipartFile == null) {
            response.setCode(HouseCode.HOUSE_FILE_NULL);
        } else {
            //获取原始文件名（包括类型）
            String fileName = multipartFile.getOriginalFilename();
            if (LOG.isDebugEnabled()) {
                LOG.debug("==========originalFilename==========" + fileName);
            }
            //获取文件类型
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (LOG.isDebugEnabled()) {
                LOG.debug("==========fileType==========" + fileType);
            }
            if (multipartFile.getSize() > HouseDataValidation.EXCEL_MAX_SIZE) {
                response.setCode(HouseCode.EXCEL_SIZE);
            } else if (!fileType.toLowerCase().matches(HouseDataValidation.EXCEL_RULE)) {
                response.setCode(HouseCode.EXCEL_RULE);
            } else {
                HSSFWorkbook workbook;
                Sheet sheet;
                try {
                    workbook = new HSSFWorkbook(multipartFile.getInputStream());
                    sheet = workbook.getSheetAt(0);
                } catch (IOException e) {
                    LOG.error("========web/user/v1/importHouse error========error", e);
                    response.setCode(HouseCode.EXCEL_MODEL_WRONG);
                    return response;
                }
                try {
                    String estateId = AESUtil.decrypt(request.getHeader("estateId"));
                    // 获取到excel文件中的所有行数
                    int rows = sheet.getPhysicalNumberOfRows();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("==========excel rows==========" + rows);
                    }
                    int lastRow = sheet.getLastRowNum();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("==========excel lastRow==========" + lastRow);
                    }
                    if (rows <= 1) {
                        response.setCode(HouseCode.HOUSE_FILE_NULL);
                    } else {
                        //数据校验失败的房屋列表
                        List<ImportMsg> list = new ArrayList<>();
                        //插入数据库的房屋列表
                        List<House> addList = new ArrayList<>();
                        //数据库校验成功的房屋列表（方便进行二次校验）
                        List<ImportMsg> checkList = new ArrayList<>();
                        //验证excel的模型是否正确
                        if (!validateExcel(sheet.getRow(0))) {
                            response.setCode(HouseCode.EXCEL_MODEL_WRONG);
                            return response;
                        }
                        for (int i = 1; i <= lastRow; i++) {
                            // 检测去除空格时是否存在数据 导入时存在空格会默认为一条数据 1去除空格为没有数据 2为有数据
                            int data = 1;
                            Row row = sheet.getRow(i);
                            if (row != null) {
                                ImportMsg importMsg = new ImportMsg();
                                //楼宇名称
                                Cell cell = row.getCell(0);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setBuildingName(cell.getStringCellValue().trim());
                                    }
                                }
                                //单元名称
                                cell = row.getCell(1);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setUnitName(cell.getStringCellValue().trim());
                                    }
                                }
                                //房号
                                cell = row.getCell(2);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setHouseNum(cell.getStringCellValue().trim());
                                    }
                                }
                                //户型
                                cell = row.getCell(3);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setTypeName(cell.getStringCellValue().trim());
                                    }
                                }
                                //房屋排序
                                cell = row.getCell(4);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setDisplayOrder(cell.getStringCellValue().trim());
                                    }
                                }
                                //建筑面积
                                cell = row.getCell(5);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setFloorArea(cell.getStringCellValue().trim());
                                    }
                                }
                                //套内面积
                                cell = row.getCell(6);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                                        data = 2;
                                        importMsg.setInterFloorArea(cell.getStringCellValue().trim());
                                    }
                                }
                                if (data == 2) {
                                    // 进行数据校验
                                    HashMap<String, String> result = checkImportInfo(importMsg, estateId);
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("==========validate failure result==========" + result);
                                    }
                                    // 判断是否存在错误信息
                                    if (result.size() == 0) {
                                        boolean flag = true;
                                        //二次校验
                                        checkList.add(importMsg);
                                        if (checkList.size() > 1) {
                                            for (int j = 0; j < checkList.size() - 1; j++) {
                                                //如果有相同房屋，则排除
                                                if (StringUtil.isEmpty(importMsg.getUnitName()) && !StringUtil.isEmpty(importMsg.getBuildingName())) {
                                                    if (importMsg.getBuildingName().equals(checkList.get(j).getBuildingName())
                                                            && StringUtil.isEmpty(checkList.get(j).getUnitName())
                                                            && importMsg.getHouseNum().equals(checkList.get(j).getHouseNum())) {
                                                        flag = false;
                                                    }
                                                }
                                                if (!StringUtil.isEmpty(importMsg.getUnitName()) && !StringUtil.isEmpty(importMsg.getBuildingName())) {
                                                    if (importMsg.getBuildingName().equals(checkList.get(j).getBuildingName())
                                                            && importMsg.getUnitName().equals(checkList.get(j).getUnitName())
                                                            && importMsg.getHouseNum().equals(checkList.get(j).getHouseNum())) {
                                                        flag = false;
                                                    }
                                                }
                                            }
                                        }
                                        //如果和前面的导入数据不重复，则插入
                                        if (flag) {
                                            // 添加房屋
                                            House house = new House();
                                            house.setHouseId(UuidUtil.create());
                                            house.setCreateTime(new Date(System.currentTimeMillis()));
                                            if (!StringUtil.isBlank(importMsg.getDisplayOrder())) {
                                                house.setDisplayOrder(Integer.parseInt(importMsg.getDisplayOrder()));
                                            }
                                            house.setHouseNum(importMsg.getHouseNum());
                                            house.setFloorArea(Double.parseDouble(importMsg.getFloorArea()));
                                            house.setInterFloorArea(Double.parseDouble(importMsg.getInterFloorArea()));
                                            house.setHouseStatus(HouseDataValidation.HOUSE_IS_EMPTY);
                                            house.setPetStatus(HouseDataValidation.PET_IS_EMPTY);
                                            if (importMsg.getTypeName() != null
                                                    && houseService.findByTypeName(importMsg.getTypeName()) != null) {
                                                house.setTypeId(houseService.findByTypeName(importMsg.getTypeName()).getTypeId());
                                            }
                                            if (importMsg.getBuildingName() != null
                                                    && houseService.findByBuildingName(importMsg.getBuildingName(), estateId) != null) {
                                                house.setBuildingId(houseService.findByBuildingName(importMsg.getBuildingName(), estateId).getBuildingId());
                                            }
                                            if (importMsg.getUnitName() != null
                                                    && houseService.findByBuildingName(importMsg.getBuildingName(), estateId) != null) {
                                                Long buildingId = houseService.findByBuildingName(importMsg.getBuildingName(), estateId).getBuildingId();
                                                house.setUnitId(houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), buildingId).get(0).getUnitId());
                                            }
                                            house.setEstateId(estateId);
                                            addList.add(house);
                                        } else {
                                            importMsg.setFailureMsg("该房屋已存在");
                                            list.add(importMsg);
                                        }
                                    } else {
                                        importMsg.setFailureMsg(result.get("message"));
                                        list.add(importMsg);
                                    }
                                }
                            }
                        }
                        //批量插入数据库
                        if (addList.size() != 0) {
                            houseService.addHouseList(addList);
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========importMsgList========" + list);
                        }
                        if (list.size() != 0) {
                            response.setData(list);
                        }
                        //判断是否插入的都是空行
                        if (addList.size() == 0 && list.size() == 0) {
                            response.setCode(HouseCode.HOUSE_FILE_NULL);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("========web/user/v1/importHouse error========error", e);
                    response.setCode(StatusCode.FAILURE);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/importHouse end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询房屋信息接口
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/21 14:13
     *
     * @param houseIdReq 房屋编号入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-findHouse")
    @RequestMapping(value = "v1/findHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<HouseInformation> findHouse(@RequestBody HouseIdReq houseIdReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findHouse start========houseIdReq=" + houseIdReq);
        }
        Response<HouseInformation> response = new Response<>();
        //判断编号是否为空
        if (houseIdReq.getHouseId() == null) {
            response.setCode(HouseCode.HOUSE_ID_NULL);
        } else if (houseService.findByHouseId(houseIdReq.getHouseId()) == null) {
            response.setCode(HouseCode.HOUSE_IS_DELETE);
        } else {
            try {
                HouseInformation info = houseService.findHouseInformationByHouseId(houseIdReq.getHouseId());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("=========response houseInfo=========" + info);
                }
                response.setData(info);
            } catch (Exception e) {
                LOG.error("========web/house/v1/findHouse error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findHouse end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 删除房屋接口
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/21 14:41
     *
     * @param houseIdsReq 房屋编号入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-deleteHouse")
    @RequestMapping(value = "v1/deleteHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteHouse(@RequestBody HouseIdsReq houseIdsReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deleteHouse start========houseIdsReq=" + houseIdsReq);
        }
        Response<String> response = new Response<>();
        //判断编号是否为空
        if (StringUtil.isEmpty(houseIdsReq.getHouseIds())) {
            response.setCode(HouseCode.HOUSE_ID_NULL);
        } else {
            try {
                //删除房屋表，房屋成员表，房屋图纸表，房屋认证表，认证通知表,账单表，流水表，维修单表，维修单评价表，维修单图片表
                houseService.deleteHouseByHouseIds(houseIdsReq.getHouseIds(), request);
            } catch (Exception e) {
                LOG.error("========web/house/v1/deleteHouse error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deleteHouse end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 编辑房屋接口
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/21 14:41
     *
     * @param updateHouseReq 编辑房屋信息入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-updateHouse")
    @RequestMapping(value = "v1/updateHouse", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateHouse(@RequestBody UpdateHouseReq updateHouseReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/updateHouse start========updateHouseReq=" + updateHouseReq);
        }
        Response<String> response = new Response<>();
        try {
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            updateHouseReq.setEstateId(estateId);
            //判断编号是否为空
            if (updateHouseReq.getHouseId() == null) {
                response.setCode(HouseCode.HOUSE_ID_NULL);
            } else if (validateUpdateHouse(updateHouseReq) != null) {
                response.setCode(validateUpdateHouse(updateHouseReq));
            } else {
                //将数据封装到house对象中
                House house = new House();
                house.setHouseId(updateHouseReq.getHouseId().toString());
                if (StringUtil.isBlank(updateHouseReq.getBuildingId())) {
                    house.setBuildingId(null);
                } else {
                    house.setBuildingId(Long.parseLong(updateHouseReq.getBuildingId()));
                }
                if (StringUtil.isBlank(updateHouseReq.getUnitId())) {
                    house.setUnitId(null);
                } else {
                    house.setUnitId(Long.parseLong(updateHouseReq.getUnitId()));
                }
                house.setHouseNum(updateHouseReq.getHouseNum());
                if (!StringUtil.isBlank(updateHouseReq.getTypeId())) {
                    house.setTypeId(Long.parseLong(updateHouseReq.getTypeId()));
                }
                house.setFloorArea(Double.parseDouble(updateHouseReq.getFloorArea()));
                house.setInterFloorArea(Double.parseDouble(updateHouseReq.getInterFloorArea()));
                if (!StringUtil.isBlank(updateHouseReq.getDisplayOrder())) {
                    house.setDisplayOrder(Integer.parseInt(updateHouseReq.getDisplayOrder()));
                }
                String code = houseService.updateHouse(house, updateHouseReq);
                if (!StringUtil.isBlank(code)) {
                    response.setCode(code);
                }
            }
        } catch (Exception e) {
            LOG.error("========web/house/v1/updateHouse error========error", e);
            response.setCode(StatusCode.FAILURE);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/updateHouse end========response=" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查看房屋图纸信息
     * </p>
     * Author: zhangli <br/>
     * Date: 2017/6/22 14:13
     *
     * @param houseIdReq 房屋编号入参
     * @return response
     */
    @RequiresPermissions(value = "houseManage-findPicture")
    @RequestMapping(value = "v1/findPicture", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<PictureInfo>> findPicture(@RequestBody HouseIdReq houseIdReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findPicture start========houseIdReq=" + houseIdReq.getHouseId());
        }
        Response<List<PictureInfo>> response = new Response<>();
        if (null == houseIdReq.getHouseId()) {
            response.setCode(HouseCode.HOUSE_ID_NULL);
        } else if (houseService.findByHouseId(houseIdReq.getHouseId()) == null) {
            response.setCode(HouseCode.HOUSE_IS_DELETE);
        } else {
            try {//查所有图纸类型
                List<PictureInfo> pictureInfoList = houseService.findPictureType();
                //遍历图纸类型
                for (PictureInfo pictureInfo : pictureInfoList) {
                    //根据房屋id和图纸类型查图纸
                    PictureInfo picture = houseService.findByHouseIdAndTypeName(houseIdReq.getHouseId(), pictureInfo.getTypeName());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========findPictureByHouseIdAndTypeName========" + picture);
                    }
                    if (picture != null) {
                        if (!StringUtil.isEmpty(picture.getPictureUrl())) {
                            pictureInfo.setPictureUrl(HouseDataValidation.FILE_SERVER_PATH + picture.getPictureUrl());
                        }
                        pictureInfo.setCadId(picture.getCadId());
                        pictureInfo.setPictureId(picture.getPictureId());
                        pictureInfo.setCadName(picture.getCadName());
                        pictureInfo.setHpId(picture.getHpId());
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("========response pictureInfoList========" + pictureInfoList);
                }
                response.setData(pictureInfoList);
            } catch (Exception e) {
                LOG.error("========web/house/v1/findPicture error========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findPicture end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 上传（修改）图纸、CAD文件
     * </p>
     * Author: zhangli <br/>
     * Date: 2017/6/23 10:18
     *
     * @param multipartFile uploadPic
     * @return response
     */
    @RequiresPermissions(value = "houseManage-uploadPicture")
    @RequestMapping(value = "v1/uploadPicture", method = RequestMethod.POST)
    public Response<Object> uploadPicture(MultipartFile multipartFile, UploadPictureReq uploadPic) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/uploadPicture start========uploadPic" + uploadPic);
        }
        Response<Object> response = new Response<>();

        if (null == multipartFile) {
            response.setCode(HouseCode.HOUSE_FILE_NULL);
        } else if (null == uploadPic.getHouseId()) {
            response.setCode(HouseCode.HOUSE_ID_NULL);
        } else if (StringUtil.isEmpty(uploadPic.getPictureType().trim())) {
            response.setCode(HouseCode.HOUSE_TYPE_NULL);
        } else if (null == uploadPic.getPictureFlag()) {
            response.setCode(HouseCode.FILE_FLAG_NULL);
        } else {
            //获取原始文件名
            String picName = multipartFile.getOriginalFilename();
            if (LOG.isDebugEnabled()) {
                LOG.debug("========originalFilename=========" + picName);
            }
            //获取文件格式
            String format = picName.substring(picName.lastIndexOf(".") + 1);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========fileType=========" + format);
            }
            String name = "";
            //预览图片标识
            if (uploadPic.getPictureFlag().equals(HouseDataValidation.PICTURE_FLAG)) {
                if (picName.length() > HouseDataValidation.PIC_NAME_SIZE) {
                    response.setCode(HouseCode.PICTURE_NAME_MAX);
                } else if (!format.toLowerCase().matches(HouseDataValidation.PICTURE_RULE)) {
                    //预览图片格式错误
                    response.setCode(HouseCode.HOUSE_PICTURE_RULE);
                } else if (multipartFile.getSize() > HouseDataValidation.PICTURE_MAX_SIZE) {
                    //预览图片大小超过20M
                    response.setCode(HouseCode.HOUSE_PICTURE_SIZE);
                    //图片格式 与 大小都满足
                } else {
                    //设置图片保存路径
                    String path = HouseDataValidation.FILE_DIR_PATH + "house/";
                    try {
                        //上传后的新文件名
                        name = FileUtil.uploadFile(multipartFile, path);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========newFilename=========" + name);
                        }
                    } catch (Exception e) {
                        LOG.error("========/web/ad/v1/uploadPic error!=========", e);
                        response.setCode(HouseCode.READ_FILE_FAILUER);
                    }
                    addHousePicture(name, picName, uploadPic, response);
                    try {
                        //根据房屋id和图纸类型，查找出房屋图纸
                        HousePic housePic = houseService.findHousePicByHouseIdAndPictureType(uploadPic);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========findHousePicByHouseIdAndPictureType=========housePic=" + housePic);
                        }
                        //创建返回实体
                        UploadPic pic = new UploadPic();
                        pic.setHpId(housePic.getHpId());
                        pic.setPictureUrl(HouseDataValidation.FILE_SERVER_PATH + "house/" + name);
                        response.setData(pic);
                    } catch (Exception e) {
                        response.setCode(StatusCode.FAILURE);
                    }
                }
                //CAD标识
            } else if (uploadPic.getPictureFlag().equals(HouseDataValidation.CAD_FLAG)) {
                if (picName.length() > HouseDataValidation.PIC_NAME_SIZE) {
                    response.setCode(HouseCode.PICTURE_NAME_MAX);
                } else if (!format.toLowerCase().matches(HouseDataValidation.CAD_RULE)) {
                    //cad文件格式错误
                    response.setCode(HouseCode.HOUSE_CAD_RULE);
                } else if (multipartFile.getSize() > HouseDataValidation.CAD_MAX_SIZE) {
                    //cad文件大小超过100M
                    response.setCode(HouseCode.HOUSE_CAD_SIZE);
                    //图片格式 与 大小都满足
                } else {
                    try {
                        //设置图片保存路径
                        String path = HouseDataValidation.FILE_DIR_PATH + "house/";
                        //上传后的新文件名
                        name = FileUtil.uploadFile(multipartFile, path);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========newFilename=========" + name);
                        }
                    } catch (Exception e) {
                        LOG.error("========/web/ad/v1/uploadPic error!=========", e);
                        response.setCode(HouseCode.READ_FILE_FAILUER);
                    }
                    addHousePicture(name, picName, uploadPic, response);
                    try {
                        //根据房屋id和图纸类型，查找出房屋图纸
                        HousePic housePic = houseService.findHousePicByHouseIdAndPictureType(uploadPic);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========findHousePicByHouseIdAndPictureType=========housePic=" + housePic);
                        }
                        //创建返回实体
                        UploadPic pic = new UploadPic();
                        pic.setHpId(housePic.getHpId());
                        pic.setPictureName(picName);
                        response.setData(pic);
                    } catch (Exception e) {
                        LOG.error("========web/house/v1/uploadPicture error!========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                }
            }
        }
        return response;
    }

    /**
     * <p>
     * 删除图纸、CAD文件
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/6/21 8:43
     */
    @RequiresPermissions(value = "houseManage-deletePicture")
    @RequestMapping(value = "v1/deletePicture", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deletePicture(@RequestBody PicReq deletePicReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deletePicture start========deletePicReq=" + deletePicReq);
        }
        Response<String> response = new Response<>();
        if (null == deletePicReq.getHpId()) {
            response.setCode(HouseCode.HOUSE_PICID_NULL);
        } else if (null == deletePicReq.getPictureFlag()) {
            response.setCode(HouseCode.FILE_FLAG_NULL);
        } else {
            //查询图片是否存在
            HousePic housePic = houseService.findHousePicByHpId(deletePicReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHousePicByHpId=========housePic=" + housePic);
            }
            if (HouseDataValidation.PICTURE_FLAG.equals(deletePicReq.getPictureFlag())) {
                if (null == housePic) {
                    response.setCode(HouseCode.PICTURE_IS_DELETE);
                } else if (null == housePic.getPictureId()) {
                    response.setCode(HouseCode.PICTURE_IS_DELETE);
                } else if (null == housePic.getCadId()) {
                    houseService.deleteHousePic(deletePicReq);
                } else {
                    houseService.updateHousePic(deletePicReq);
                }
            } else if (HouseDataValidation.CAD_FLAG.equals(deletePicReq.getPictureFlag())) {
                if (null == housePic) {
                    response.setCode(HouseCode.CAD_IS_DELETE);
                } else if (null == housePic.getCadId()) {
                    response.setCode(HouseCode.CAD_IS_DELETE);
                } else if (null == housePic.getPictureId()) {
                    houseService.deleteHousePic(deletePicReq);
                } else {
                    houseService.updateHousePic(deletePicReq);
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deletePicture end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 下载图纸、CAD文件
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/6/21 14:10
     */
    @RequestMapping(value = "v1/downloadPicture")
    public ModelAndView downloadPicture(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/downloadPicture start========request=" + request);
        }
        Long hpId = Long.parseLong(request.getParameter("hpId"));
        if (LOG.isDebugEnabled()) {
            LOG.debug("========request hpId=========" + hpId);
        }
        Integer pictureFlag = Integer.parseInt(request.getParameter("pictureFlag"));
        if (LOG.isDebugEnabled()) {
            LOG.debug("========request pictureFlag=========" + pictureFlag);
        }
        String userId = null;
        try {
            userId = AESUtil.decrypt(request.getParameter("userId"));
        } catch (Exception e) {
            LOG.error("========web/house/v1/downloadPicture error!========", e);
            return null;
        }
        String token = request.getParameter("token");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========request token=========" + token);
        }
        PicReq picReq = new PicReq();
        picReq.setHpId(hpId);
        picReq.setPictureFlag(pictureFlag);
//        jisn.z 2018/1/20 ======================================注销
        boolean isExist = webLoginService.checkWebToken(token, userId);
        if (null == token || null == userId || !isExist) {
            ModelAndView mav = new ModelAndView("redirect:/login.html");
            return mav;
        }
        if (null == picReq.getHpId()) {
            return null;
        } else if (null == picReq.getPictureFlag()) {
            return null;
        } else {
            //查询图片是否存在
            HousePic housePic = houseService.findHousePicByHpId(picReq);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHousePicByHpId=========" + housePic);
            }
            if (HouseDataValidation.PICTURE_FLAG.equals(picReq.getPictureFlag())) {
                if (null == housePic) {
                    return null;
                } else if (null == housePic.getPictureId()) {
                    return null;
                } else {
                    Pic pic = houseService.findPicById(housePic.getPictureId());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========findPicById=========" + pic);
                    }
                    ModelAndView mav = new ModelAndView("redirect:./download");
                    mav.addObject("picName", pic.getPictureName());
                    mav.addObject("filePath", HouseDataValidation.FILE_DIR_PATH + pic.getPictureUrl());
                    return mav;
                }
            } else if (HouseDataValidation.CAD_FLAG.equals(picReq.getPictureFlag())) {
                if (null == housePic) {
                    return null;
                } else if (null == housePic.getCadId()) {
                    return null;
                } else {
                    Pic pic = houseService.findPicById(housePic.getCadId());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========findPicById=========" + pic);
                    }
                    ModelAndView mav = new ModelAndView("redirect:./download");
                    mav.addObject("picName", pic.getPictureName());
                    mav.addObject("filePath", HouseDataValidation.FILE_DIR_PATH + pic.getPictureUrl());
                    return mav;
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/downloadPicture end========");
        }
        return null;
    }

    @RequestMapping("v1/download")
    public void download(@ModelAttribute("picName") String picName, @ModelAttribute("filePath") String filePath, HttpServletResponse response, HttpServletRequest request) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("========v1/download start=========picName=" + picName + ",filePath=" + filePath);
        }
        File downloadFile = new File(filePath);
        InputStream in = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(downloadFile);
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                // win10 ie edge 浏览器 和其他系统的ie
                picName = URLEncoder.encode(picName, "UTF-8");
            } else {
                // 非IE
                picName = new String(picName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + picName);
            os = response.getOutputStream();
            byte[] b = new byte[6 * 1024];
            int length;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭io流
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(os);

        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("========v1/download end=========");
        }
    }

    /**
     * <p>
     * 下载模板
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/29 13:38
     */
    @RequiresPermissions(value = "houseManage-downloadTemplate")
    @RequestMapping(value = "v1/downloadTemplate", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<Object> downloadTemplate(@RequestBody TemplateReq templateReq) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/downloadTemplate start========templateReq=" + templateReq);
        }
        Response<Object> response = new Response<>();
        if (null == templateReq.getTemplateFlag()) {
            response.setCode(HouseCode.FILE_FLAG_NULL);
        } else if (templateReq.getTemplateFlag().equals(HouseDataValidation.HOUSE_TEMPLATE_FLAG)) {
            Template template = new Template();
            template.setTemplateUrl(HouseDataValidation.FILE_SERVER_PATH + HouseDataValidation.HOUSE_TEMPLATE_URL);
            response.setData(template);
        } else if (templateReq.getTemplateFlag().equals(HouseDataValidation.MATERIAL_TEMPLATE_FLAG)) {
            Template template = new Template();
            template.setTemplateUrl(HouseDataValidation.FILE_SERVER_PATH + HouseDataValidation.MATERIAL_TEMPLATE_URL);
            response.setData(template);
        } else if (templateReq.getTemplateFlag().equals(HouseDataValidation.HOUSEPIC_TEMPLATE_FLAG)) {
            Template template = new Template();
            template.setTemplateUrl(HouseDataValidation.FILE_SERVER_PATH + HouseDataValidation.HOUSEPIC_TEMPLATE_URL);
            response.setData(template);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/downloadTemplate end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 查询所有户型
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/29 13:38
     */
    @RequiresPermissions(value = "houseManage-findHouseTypes")
    @RequestMapping(value = "v1/findHouseTypes", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<List<HouseTypeResp>> findHouseTypes() {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findHouseTypes start========");
        }
        Response<List<HouseTypeResp>> response = new Response<>();
        response.setData(houseService.findHouseTypes());
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/findHouseTypes end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 批量修改入住时间
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/14 13:56
     *
     * @param req 入参
     * @return response 回参
     */
    @RequiresPermissions(value = "houseManage-updateDeliverTime")
    @RequestMapping(value = "v1/updateDeliverTime", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> updateDeliverTime(@RequestBody UpdateDeliverTimeReq req) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/updateDeliverTime start========");
        }
        Response<String> response = new Response<>();
        if (StringUtil.isBlank(req.getHouseIds())) {
            response.setCode(HouseCode.HOUSE_ID_NULL);
        } else if (StringUtil.isBlank(req.getDeliverTime())) {
            response.setCode(HouseCode.DELIVERTIME_IS_NULL);
        } else if (!validateTime(req.getDeliverTime().trim())) {
            response.setCode(HouseCode.DELIVERTIME_RULE_ERROR);
        } else if (!checkHouseExist(req.getHouseIds())) {
            response.setCode(HouseCode.HOUSE_IS_DELETE);
        } else {
            try {
                houseService.updateDeliverTime(req);
            } catch (Exception e) {
                LOG.error("========web/house/v1/updateDeliverTime error========", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/updateDeliverTime end========response" + response);
        }
        return response;
    }

    /**
     * <p>
     * 判断房屋是否存在
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/14 14:13
     *
     * @param houseIds 多个房屋编号
     * @return boolean 是否存在
     */
    private boolean checkHouseExist(String houseIds) {
        boolean flag = true;
        String[] stringArray = houseIds.split(",");
        if (LOG.isDebugEnabled()) {
            LOG.debug("========houseIds to stringArray========" + stringArray);
        }
        for (String a :
                stringArray) {
            if (null == houseService.findByHouseId(a)) {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * <p>
     * 将预览图或CAD图插入或更新到数据库
     * </p>
     * Author: zhangli <br/>
     * Date: 2017/6/23 10:18
     *
     * @param name        图片URL
     * @param pictureName 图片名称
     * @param uploadPic   上传图片入参
     * @param response    返回值
     */
    private void addHousePicture(String name, String pictureName, UploadPictureReq uploadPic, Response response) {
        String pictureId = null;
        try {
            //将图片写入Picture表
            Picture picture = new Picture();
            picture.setPictureUrl("house/" + name);
            picture.setPictureName(pictureName);
            picture.setCreateTime(new Date(System.currentTimeMillis()));

            //将CAD图或预览图 插入图片表里，并返回主键
            pictureId = houseService.addPicture(picture);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========addPicture reture pictureId=========pictureId=" + pictureId);
            }
        } catch (Exception e) {
            LOG.error("========web/house/v1/uploadPicture error!========", e);
            response.setCode(StatusCode.FAILURE);
        }

        try {
            //根据房屋id和图纸类型查房屋图纸表
            HousePic housePic = houseService.findHousePicByHouseIdAndPictureType(uploadPic);
            if (LOG.isDebugEnabled()) {
                LOG.debug("========findHousePicByHouseIdAndPictureType=========housePic=" + housePic);
            }
            if (null != housePic) {
                //预览图更新
                if (uploadPic.getPictureFlag().equals(HouseDataValidation.PICTURE_FLAG)) {
                    housePic.setPictureId(pictureId);
                    houseService.updateHousePicture(housePic);
                    //CAD图更新
                } else if (uploadPic.getPictureFlag().equals(HouseDataValidation.CAD_FLAG)) {
                    housePic.setCadId(pictureId);
                    houseService.updateHousePicture(housePic);
                }
            } else {
                //预览图或CAD图 id插入到房屋图纸表里
                HousePic housePicture = new HousePic();
                if (uploadPic.getPictureFlag().equals(HouseDataValidation.PICTURE_FLAG)) {
                    //预览图 初始化各种参数
                    housePicture.setHouseId(uploadPic.getHouseId());
                    housePicture.setPictureId(pictureId);
                    housePicture.setPictureType(uploadPic.getPictureType());
                    housePicture.setCreateTime(new Date(System.currentTimeMillis()));
                    houseService.insertHousePicture(housePicture);
                } else if (uploadPic.getPictureFlag().equals(HouseDataValidation.CAD_FLAG)) {
                    //CAD图 初始化各种参数
                    housePicture.setHouseId(uploadPic.getHouseId());
                    housePicture.setCadId(pictureId);
                    housePicture.setPictureType(uploadPic.getPictureType());
                    housePicture.setCreateTime(new Date(System.currentTimeMillis()));
                    houseService.insertHousePicture(housePicture);
                }
            }

        } catch (Exception e) {
            LOG.error("========web/house/v1/uploadPicture error!========", e);
            response.setCode(StatusCode.FAILURE);
        }
    }

    /**
     * <p>
     * 校验导入的数据
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/20 17:30
     *
     * @param importMsg 导入数据入参
     */
    private HashMap<String, String> checkImportInfo(ImportMsg importMsg, String estateId) {
        HashMap<String, String> map = new LinkedHashMap<>();
        if (importMsg.getBuildingName() != null && houseService.findByBuildingName(importMsg.getBuildingName(), estateId) == null) {
            map.put("code", HouseCode.BUILDING_NOT_EXIST);
            map.put("message", "该楼宇不存在");
            return map;
        }
        if (importMsg.getUnitName() != null
                && (houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), null) == null
                || houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), null).size() == 0)) {
            map.put("code", HouseCode.UNIT_IS_NULL);
            map.put("message", "该单元不存在");
            return map;
        }
        if (importMsg.getTypeName() != null && houseService.findByTypeName(importMsg.getTypeName()) == null) {
            map.put("code", HouseCode.HOUSETYPE_IS_NULL);
            map.put("message", "该户型不存在");
            return map;
        }
        if (importMsg.getUnitName() != null && importMsg.getBuildingName() == null) {
            map.put("code", HouseCode.BUILDING_IS_NULL);
            map.put("message", "楼宇不能为空");
            return map;
        }
        if (importMsg.getUnitName() != null && importMsg.getBuildingName() != null) {
            int flag = 0;
            Building building = houseService.findByBuildingName(importMsg.getBuildingName(), estateId);
            if (building != null
                    && houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), building.getBuildingId()) != null
                    && houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), building.getBuildingId()).size() != 0) {
                flag = 1;
                if (!StringUtil.isEmpty(importMsg.getHouseNum())) {
                    AddHouseReq addHouseReq = new AddHouseReq();
                    addHouseReq.setHouseNum(importMsg.getHouseNum());
                    addHouseReq.setUnitId(houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), building.getBuildingId()).get(0).getUnitId());
                    addHouseReq.setBuildingId(building.getBuildingId());
                    addHouseReq.setEstateId(estateId);
                    if (houseService.findByMultipleParams(addHouseReq) != null) {
                        map.put("code", HouseCode.HOUSE_IS_EXIST);
                        map.put("message", "该房屋已存在");
                        return map;
                    }
                }
            }
            if (flag == 0) {
                map.put("code", HouseCode.UNIT_BUILDING_MATCH);
                map.put("message", "该单元不属于该楼宇");
                return map;
            }
        }
        if ((importMsg.getUnitName() == null && importMsg.getBuildingName() == null) && !StringUtil.isEmpty(importMsg.getHouseNum())) {
            AddHouseReq addHouseReq = new AddHouseReq();
            addHouseReq.setHouseNum(importMsg.getHouseNum());
            addHouseReq.setEstateId(estateId);
            if (houseService.findByMultipleParams(addHouseReq) != null) {
                map.put("code", HouseCode.HOUSE_IS_EXIST);
                map.put("message", "该房屋已存在");
                return map;
            }
        }
        if ((importMsg.getUnitName() == null && importMsg.getBuildingName() != null) && !StringUtil.isEmpty(importMsg.getHouseNum())) {
            AddHouseReq addHouseReq = new AddHouseReq();
            Building building = houseService.findByBuildingName(importMsg.getBuildingName(), estateId);
            if (building != null) {
                addHouseReq.setBuildingId(building.getBuildingId());
            }
            addHouseReq.setHouseNum(importMsg.getHouseNum());
            addHouseReq.setEstateId(estateId);
            if (houseService.findByMultipleParams(addHouseReq) != null) {
                map.put("code", HouseCode.HOUSE_IS_EXIST);
                map.put("message", "该房屋已存在");
                return map;
            }
        }
        if (StringUtil.isEmpty(importMsg.getHouseNum())) {
            map.put("code", HouseCode.HOUSE_NUM_NULL);
            map.put("message", "房号不能为空");
        } else if (!importMsg.getHouseNum().trim().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
            map.put("code", HouseCode.HOUSE_NUM_RULE);
            map.put("message", "房号不能输入\\<>’”%且长度不超过6位");
        } else if (StringUtil.isEmpty(importMsg.getDisplayOrder())) {
            map.put("code", HouseCode.ORDER_NULL);
            map.put("message", "房屋序号不能为空");
        } else if (!StringUtil.isBlank(importMsg.getDisplayOrder())
                && !Validate.isNum(importMsg.getDisplayOrder(), HouseDataValidation.ORDER_MAX_LENGTH)) {
            map.put("code", HouseCode.ORDER_RULE);
            map.put("message", "房屋排序只能输入正整数，且不超过10000");
        } else if (StringUtil.isEmpty(importMsg.getFloorArea())) {
            map.put("code", HouseCode.FLOORAREA_IS_NULL);
            map.put("message", "建筑面积不能为空");
        } else if (!importMsg.getFloorArea().matches(HouseDataValidation.NUMBER_ONLY)) {
            map.put("code", HouseCode.FLOORAREA_RULE_ERROR);
            map.put("message", "建筑面积只能输入整数或小数");
        } else if (!importMsg.getFloorArea().matches(HouseDataValidation.AREA_RULE)) {
            map.put("code", HouseCode.FLOORAREA_RULE_ERROR);
            map.put("message", "建筑面积整数部分不能超过5位，小数部分不能超过2位");
        } else if (Double.parseDouble(importMsg.getFloorArea()) == 0) {
            map.put("code", HouseCode.FLOORAREA_RULE_ERROR);
            map.put("message", "建筑面积不能为0");
        } else if (StringUtil.isEmpty(importMsg.getInterFloorArea())) {
            map.put("code", HouseCode.INTERFLOORAREA_IS_NULL);
            map.put("message", "套内面积不能为空");
        } else if (!importMsg.getInterFloorArea().matches(HouseDataValidation.NUMBER_ONLY)) {
            map.put("code", HouseCode.INTERFLOORAREA_RULE_ERROR);
            map.put("message", "套内面积只能输入整数或小数");
        } else if (!importMsg.getInterFloorArea().matches(HouseDataValidation.AREA_RULE)) {
            map.put("code", HouseCode.INTERFLOORAREA_RULE_ERROR);
            map.put("message", "套内面积整数部分不能超过5位，小数部分不能超过2位");
        } else if (Double.parseDouble(importMsg.getInterFloorArea()) == 0) {
            map.put("code", HouseCode.INTERFLOORAREA_RULE_ERROR);
            map.put("message", "套内面积不能为0");
        }
        return map;
    }

    /**
     * <p>
     * 校验添加房屋入参
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/26 19:52
     *
     * @param houseReq 房屋入参
     * @return 返回code码
     */
    private String validateAddHouse(UpdateHouseReq houseReq) {
        //定义code码
        String code = null;
        if (!StringUtil.isBlank(houseReq.getBuildingId())
                && houseService.findByBuildingId(Long.parseLong(houseReq.getBuildingId())) == null) {
            //楼宇不存在
            code = HouseCode.BUILDING_NOT_EXIST;
        } else if (!StringUtil.isBlank(houseReq.getUnitId())
                && houseService.findByUnitId(Long.parseLong(houseReq.getUnitId())) == null) {
            //单元不存在
            code = HouseCode.UNIT_IS_NULL;
        } else if (!StringUtil.isBlank(houseReq.getTypeId()) && houseService.findByTypeId(Long.parseLong(houseReq.getTypeId())) == null) {
            //户型不存在
            code = HouseCode.HOUSETYPE_IS_NULL;
        } else if (!StringUtil.isBlank(houseReq.getUnitId())
                && StringUtil.isBlank(houseReq.getBuildingId())) {
            //有单元无楼宇
            code = HouseCode.BUILDING_IS_NULL;
        } else if (!StringUtil.isBlank(houseReq.getUnitId()) && !StringUtil.isBlank(houseReq.getBuildingId())
                && houseService.findByUnitId(Long.parseLong(houseReq.getUnitId())) != null
                && !houseService.findByUnitId(Long.parseLong(houseReq.getUnitId())).getBuildingId().equals(Long.parseLong(houseReq.getBuildingId()))) {
            //单元楼宇不匹配
            code = HouseCode.UNIT_BUILDING_MATCH;
        } else if (StringUtil.isEmpty(houseReq.getHouseNum())) {
            //房号为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (StringUtil.isEmpty(houseReq.getHouseNum().trim())) {
            //房号去空格为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (!houseReq.getHouseNum().trim().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
            //房号格式不符合
            code = HouseCode.HOUSE_NUM_RULE;
        } else if (houseService.findByHouseReq(houseReq) != null) {
            //该房屋已存在
            code = HouseCode.HOUSE_IS_EXIST;
        } else if (StringUtil.isBlank(houseReq.getFloorArea())) {
            //建筑面积为空
            code = HouseCode.FLOORAREA_IS_NULL;
        } else if (!houseReq.getFloorArea().trim().matches(HouseDataValidation.AREA_RULE)) {
            //建筑面积格式错误
            code = HouseCode.FLOORAREA_RULE_ERROR;
        } else if (StringUtil.isBlank(houseReq.getInterFloorArea())) {
            //套内面积为空
            code = HouseCode.INTERFLOORAREA_IS_NULL;
        } else if (!houseReq.getInterFloorArea().trim().matches(HouseDataValidation.AREA_RULE)) {
            //套内面积格式错误
            code = HouseCode.INTERFLOORAREA_RULE_ERROR;
        } else if (!StringUtil.isBlank(houseReq.getBuyPark()) && !Validate.isSearchNum(houseReq.getBuyPark(), HouseDataValidation.BUY_PARKING_SIZE)) {
            //产权车位数过多
            code = HouseCode.BUY_PARKING_SIZE;
        } else if (!StringUtil.isBlank(houseReq.getRentalPark()) && !Validate.isSearchNum(houseReq.getRentalPark(), HouseDataValidation.BUY_PARKING_SIZE)) {
            //租赁车位数过多
            code = HouseCode.RENT_PARKING_SIZE;
        } else if (!StringUtil.isBlank(houseReq.getCarSeatNumBuy())) {
            String[] buySpaceNum = houseReq.getCarSeatNumBuy().split(",");
            for (int i = 0; i < buySpaceNum.length; i++) {
                if (!Validate.isCommonString(buySpaceNum[i], HouseDataValidation.SPACE_NUM_LENGTH)) {
                    //产权车位号字符错误
                    code = HouseCode.SPACE_NUM_RULE;
                }
                if (null != houseService.findBySpaceNum(buySpaceNum[i])) {
                    //产权车位号已存在
                    code = HouseCode.BUY_PARKING_EXIST;
                }
            }
        } else if (!StringUtil.isBlank(houseReq.getCarSeatNumRent())) {
            String[] rentSpaceNum = houseReq.getCarSeatNumRent().split(",");
            for (int i = 0; i < rentSpaceNum.length; i++) {
                if (!Validate.isCommonString(rentSpaceNum[i], HouseDataValidation.SPACE_NUM_LENGTH)) {
                    //租赁车位号字符错误
                    code = HouseCode.SPACE_NUM_RULE;
                }
                if (null != houseService.findBySpaceNum(rentSpaceNum[i])) {
                    //租赁车位号已存在
                    code = HouseCode.RENT_PARKING_EXIST;
                }
            }
        } else if (!StringUtil.isBlank(houseReq.getPlateNumBuy())) {
            String[] buyPlate = houseReq.getPlateNumBuy().split(",");
            for (int i = 0; i < buyPlate.length; i++) {
                if (!Validate.isCommonString(buyPlate[i], HouseDataValidation.LICENSE_PLATE_LENGTH)) {
                    //产权车牌号字符错误
                    code = HouseCode.LICENSE_PLATE_RULE;
                }
            }
        } else if (!StringUtil.isBlank(houseReq.getPlateNumRent())) {
            String[] rentPlate = houseReq.getPlateNumRent().split(",");
            for (int i = 0; i < rentPlate.length; i++) {
                if (!Validate.isCommonString(rentPlate[i], HouseDataValidation.LICENSE_PLATE_LENGTH)) {
                    //租赁车牌号字符错误
                    code = HouseCode.LICENSE_PLATE_RULE;
                }
            }
        } else if (!StringUtil.isBlank(houseReq.getDisplayOrder())
                && !Validate.isNum(houseReq.getDisplayOrder(), HouseDataValidation.ORDER_MAX_LENGTH)) {
            //排序字段错误
            code = HouseCode.ORDER_RULE;
        }
        return code;
    }

    /**
     * <p>
     * 校验编辑房屋入参
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/26 19:52
     *
     * @param houseReq 房屋入参
     * @return 返回code码
     */
    private String validateUpdateHouse(UpdateHouseReq houseReq) {
        //定义code码
        String code = null;
        if (!StringUtil.isBlank(houseReq.getBuildingId())
                && houseService.findByBuildingId(Long.parseLong(houseReq.getBuildingId())) == null) {
            //楼宇不存在
            code = HouseCode.BUILDING_NOT_EXIST;
        } else if (!StringUtil.isBlank(houseReq.getUnitId())
                && houseService.findByUnitId(Long.parseLong(houseReq.getUnitId())) == null) {
            //单元不存在
            code = HouseCode.UNIT_IS_NULL;
        } else if (!StringUtil.isBlank(houseReq.getTypeId()) && houseService.findByTypeId(Long.parseLong(houseReq.getTypeId())) == null) {
            //户型不存在
            code = HouseCode.HOUSETYPE_IS_NULL;
        } else if (!StringUtil.isBlank(houseReq.getUnitId())
                && StringUtil.isBlank(houseReq.getBuildingId())) {
            //有单元无楼宇
            code = HouseCode.BUILDING_IS_NULL;
        } else if (!StringUtil.isBlank(houseReq.getUnitId()) && !StringUtil.isBlank(houseReq.getBuildingId())
                && houseService.findByUnitId(Long.parseLong(houseReq.getUnitId())) != null
                && !houseService.findByUnitId(Long.parseLong(houseReq.getUnitId())).getBuildingId().equals(Long.parseLong(houseReq.getBuildingId()))) {
            //单元楼宇不匹配
            code = HouseCode.UNIT_BUILDING_MATCH;
        } else if (StringUtil.isEmpty(houseReq.getHouseNum())) {
            //房号为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (StringUtil.isEmpty(houseReq.getHouseNum().trim())) {
            //房号去空格为空
            code = HouseCode.HOUSE_NUM_NULL;
        } else if (!houseReq.getHouseNum().trim().matches(HouseDataValidation.HOUSE_NUM_RULE)) {
            //房号格式不符合
            code = HouseCode.HOUSE_NUM_RULE;
        } else if (houseService.findByHouseReq(houseReq) != null) {
            House house = houseService.findByHouseId(houseReq.getHouseId());
            if (null != house && !house.getHouseNum().equals(houseService.findByHouseReq(houseReq).getHouseNum())){
                //该房屋已存在
                code = HouseCode.HOUSE_IS_EXIST;
            }
        } else if (StringUtil.isBlank(houseReq.getFloorArea())) {
            //建筑面积为空
            code = HouseCode.FLOORAREA_IS_NULL;
        } else if (!houseReq.getFloorArea().trim().matches(HouseDataValidation.AREA_RULE)) {
            //建筑面积格式错误
            code = HouseCode.FLOORAREA_RULE_ERROR;
        } else if (StringUtil.isBlank(houseReq.getInterFloorArea())) {
            //套内面积为空
            code = HouseCode.INTERFLOORAREA_IS_NULL;
        } else if (!houseReq.getInterFloorArea().trim().matches(HouseDataValidation.AREA_RULE)) {
            //套内面积格式错误
            code = HouseCode.INTERFLOORAREA_RULE_ERROR;
        } else if (!StringUtil.isBlank(houseReq.getDisplayOrder())
                && !Validate.isNum(houseReq.getDisplayOrder(), HouseDataValidation.ORDER_MAX_LENGTH)) {
            //排序字段错误
            code = HouseCode.ORDER_RULE;
        }
        return code;
    }

    /**
     * <p>
     * 判断excel文件模板是否正确
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/7/10 13:22
     * return boolean
     */
    private boolean validateExcel(Row row) {
        if (!"楼宇名称".equals(row.getCell(0).getStringCellValue().trim())) {
            return false;
        }
        if (!"单元名称".equals(row.getCell(1).getStringCellValue().trim())) {
            return false;
        }
        if (!"房号".equals(row.getCell(2).getStringCellValue().trim())) {
            return false;
        }
        if (!"户型".equals(row.getCell(3).getStringCellValue().trim())) {
            return false;
        }
        if (!"房屋排序".equals(row.getCell(4).getStringCellValue().trim())) {
            return false;
        }
        if (!"建筑面积".equals(row.getCell(5).getStringCellValue().trim())) {
            return false;
        }
        if (!"套内面积".equals(row.getCell(6).getStringCellValue().trim())) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * 校验日期格式
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/8/1 16:15
     *
     * @param time 入参
     * @return flag
     */
    private boolean validateTime(String time) {
        boolean flag = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(time);
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * <p>
     * 根据用户id删除房屋接口
     * </p>
     * Author: xiayanxin <br/>
     * Date: 2017/6/21 14:41
     *
     * @param houseIdsReq 房屋编号入参
     * @return response
     */
    @RequestMapping(value = "v1/deleteOwnerMemberByUserId", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Response<String> deleteOwnerMemberByUserId(@RequestBody HouseIdsReq houseIdsReq, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deleteOwnerMemberByUserId start========houseIdsReq=" + houseIdsReq);
        }
        Response<String> response = new Response<>();
        //判断编号是否为空
        if (StringUtil.isEmpty(houseIdsReq.getHouseIds())) {
            response.setCode(HouseCode.HOUSE_ID_NULL);
        } else {
            try {
                String[] userIds = Tool.getIdArrOfStringType(houseIdsReq.getUserId());
                houseService.deleteOwnerMemberByUserId(userIds, request);
            } catch (Exception e) {
                LOG.error("========web/house/v1/deleteOwnerMemberByUserId error========error", e);
                response.setCode(StatusCode.FAILURE);
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/deleteOwnerMemberByUserId end========response=" + response);
        }
        return response;
    }
}

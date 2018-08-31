package com.betel.estatemgmt.business.web.house.controller;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.house.code.HouseCode;
import com.betel.estatemgmt.business.web.house.constant.HouseDataValidation;
import com.betel.estatemgmt.business.web.house.model.AddHouseReq;
import com.betel.estatemgmt.business.web.house.model.PicImportMsg;
import com.betel.estatemgmt.business.web.house.model.UploadPictureReq;
import com.betel.estatemgmt.business.web.house.service.HouseService;
import com.betel.estatemgmt.business.web.material.controller.util.DeletFileUtil;
import com.betel.estatemgmt.business.web.material.controller.util.UpZipFile;
import com.betel.estatemgmt.business.web.material.controller.util.ZipUtils;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialFileStream;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.house.Building;
import com.betel.estatemgmt.common.model.house.BuildingUnit;
import com.betel.estatemgmt.common.model.house.House;
import com.betel.estatemgmt.common.model.house.HousePic;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 * 导入房屋图纸接口
 * </p>
 * ClassName: ImportPicController <br/>
 * Author: Xia.yx  <br/>
 * Date: 2017/9/23 10:33 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/house")
public class ImportPicController extends BaseController {

    @Autowired
    private HouseService houseService;

    private static final Logger LOG = LoggerFactory.getLogger(ImportPicController.class);

    /**
     * <p>
     * 批量导入房屋图纸
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/23 17:15
     *
     * */
@RequiresPermissions(value = "houseManage-importHousePic")
    @RequestMapping(value = "v1/importHousePic", method = RequestMethod.POST)
    public Response<List<PicImportMsg>> importHousePic(MultipartFile zipFileUrl, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/importHousePic start========zipFileUrl=" + zipFileUrl);
        }
        Response<List<PicImportMsg>> response = new Response<>();
        //判断文件是否为空
        if (null == zipFileUrl){
            response.setCode(HouseCode.HOUSE_FILE_NULL);
            return response;
        }
        //判断压缩文件是否是zip文件格式
        String[] allowedZipType = {"application/x-zip-compressed","application/octet-stream","application/zip"};
        if (!Arrays.asList(allowedZipType).contains(zipFileUrl.getContentType())){
            response.setCode(HouseCode.ZIP_RULE_ERROR);
            return response;
        }
        if (zipFileUrl.getSize() > HouseDataValidation.ZIP_SIZE){
            response.setCode(HouseCode.ZIP_SIZE_ERROR);
            return response;
        }
        try{
            String estateId = AESUtil.decrypt(request.getHeader("estateId"));
            //将zip转存至服务器
            String oldFilePath= ZipUtils.archivedZipFiles(zipFileUrl);
            //解压zip包，在文件夹后加时间戳以示区别
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String targetPath= ConfigManager.read(ConfigName.FILE_DIR)+"importBuildMaterial/"+format.format(new Date());
            UpZipFile.unzip(oldFilePath, targetPath);
            //判断zip目录下是否只存在指定文件夹
            String code=checkFileExistRoot(targetPath);
            if (!StringUtil.isBlank(code)){
                response.setCode(code);
                return response;
            }
            //读取zip
            List<BuildMaterialFileStream> buildMaterialFileStreamList=ZipUtils.readUnzipFileStream(targetPath);
            //读取图片
            List<BuildMaterialFileStream> pics = findPic(buildMaterialFileStreamList);
            //读取excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet;
            for (BuildMaterialFileStream file:
                 buildMaterialFileStreamList) {
                if (ZipUtils.checkExcel2003(file.getFileName())){
                    try {
                        workbook = new HSSFWorkbook(file.getIn());
                    } catch (IOException e) {
                        LOG.error("========web/user/v1/importHousePic error========error", e);
                        response.setCode(HouseCode.EXCEL_MODEL_WRONG);
                        return response;
                    }
                }
            }
            sheet = workbook.getSheetAt(0);
            // 获取到excel文件中的所有行数
            int rows = sheet.getPhysicalNumberOfRows();
            if(LOG.isDebugEnabled()){
                LOG.debug("==========excel rows==========" + rows);
            }
            int lastRow = sheet.getLastRowNum();
            if(LOG.isDebugEnabled()){
                LOG.debug("==========excel lastRow==========" + lastRow);
            }
            //数据校验失败的房屋列表
            List<PicImportMsg> list = new ArrayList<>();
            //数据库校验成功的图纸列表（方便进行二次校验）
            List<HousePic> checkList = new ArrayList<>();
            //新增房屋图纸列表
            List<HousePic> addPicList = new ArrayList<>();
            //修改房屋图纸列表
            List<HousePic> updatePicList = new ArrayList<>();
            //上传图片后保存图片信息map
            HashMap<String,String> picMap = new LinkedHashMap<>();
            //判断excel是否为空
            if (rows <= 1){
                response.setCode(HouseCode.EXCEL_NULL_ERROR);
                return response;
            }
            //判断excel模型是否正确
            if (!validateExcel(sheet.getRow(0))) {
                response.setCode(HouseCode.EXCEL_MODEL_WRONG);
                return response;
            }
            for (int i = 1; i <= lastRow; i++) {
                // 检测去除空格时是否存在数据 导入时存在空格会默认为一条数据 1去除空格为没有数据 2为有数据
                int data = 1;
                Row row = sheet.getRow(i);
                if (row != null) {
                    PicImportMsg importMsg = new PicImportMsg();
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
                    //图纸类型
                    cell = row.getCell(3);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                            data = 2;
                            importMsg.setHousePicType(cell.getStringCellValue().trim());
                        }
                    }
                    //预览图片名
                    cell = row.getCell(4);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                            data = 2;
                            importMsg.setPictureName(cell.getStringCellValue().trim());
                        }
                    }
                    //cad图片名
                    cell = row.getCell(5);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isEmpty(cell.getStringCellValue().trim())) {
                            data = 2;
                            importMsg.setCadName(cell.getStringCellValue().trim());
                        }
                    }
                    if (data == 2) {
                        // 进行数据校验
                        HashMap<String, Object> result = checkImportInfo(importMsg,pics,picMap,estateId);
                        // 判断是否存在错误信息
                        if (result.get("housePic") != null) {
                            HousePic pic = (HousePic) result.get("housePic");
                            boolean flag = true;
                            //二次校验，判断是否有两条相同房屋相同图纸类型的数据
                            checkList.add(pic);
                            if(checkList.size() > 1){
                                for (int j = 0; j < checkList.size()-1; j++) {
                                    //存在相同数据
                                    if (pic.getHouseId().equals(checkList.get(j).getHouseId())
                                            && pic.getPictureType().equals(checkList.get(j).getPictureType())){
                                        flag = false;
                                    }
                                }
                            }
                            //判断是新增还是修改房屋图纸
                            UploadPictureReq req = new UploadPictureReq();
                            req.setPictureType(pic.getPictureType());
                            req.setHouseId(pic.getHouseId());
                            if (houseService.findHousePicByHouseIdAndPictureType(req) == null && flag){
                                addPicList.add(pic);
                            }else {
                                updatePicList.add(pic);
                            }
                        }else if (result.get("message") != null){
                            importMsg.setFailureMsg((String) result.get("message"));
                            list.add(importMsg);
                        }
                    }
                }
            }
            //批量插入数据库
            if(addPicList.size() != 0){
                houseService.insertHousePicList(addPicList);
            }
            //批量修改数据库
            if (updatePicList.size() != 0){
                houseService.updateHousePicList(updatePicList);
            }
            if(LOG.isDebugEnabled()){
                LOG.debug("========importMsgList========" + list);
            }
            if (list.size() != 0) {
                response.setData(list);
            }
            //判断是否插入的都是空行
            if(addPicList.size() == 0 && updatePicList.size() == 0 && list.size() == 0){
                response.setCode(HouseCode.HOUSE_FILE_NULL);
            }
        }catch (Exception e){
            DeletFileUtil.delAllFileOfGarbageCollection(ConfigManager.read(ConfigName.FILE_DIR)+"importBuildMaterial/");
            response.setCode(StatusCode.FAILURE);
            LOG.error("========web/user/v1/importHousePic error========" ,e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========web/house/v1/importHousePic end========response" + response);
        }
        return response;
    }


    /**
     * <p>
     * 判断zip根目录下是否只存在指定文件
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/23 11:24
     * @param targetPath
     * @return code
     *
     * */
    public static String checkFileExistRoot(String targetPath) {
        String code="";
        //excel文件个数
        int excelCount = 0;
        //图片文件个数
        int picCount = 0;
        //由指定路径创建目标文件
        File file=new File(targetPath);
        //获得指定路径下的文件集合，包含文件夹
        File[] tempList = file.listFiles();
        if(tempList.length==0) {
            code = HouseCode.HOUSE_FILE_NULL;
            return code;
        }
        //遍历文件集合，解析目标文件集合内容
        for (int i = 0; i < tempList.length; i++) {
            if(tempList[i].isFile()){
                String fileName=tempList[i].getName();
                String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
                if (!StringUtil.isBlank(prefix)){
                    if (!(prefix.toLowerCase().matches(HouseDataValidation.CAD_RULE)
                            || prefix.toLowerCase().matches(HouseDataValidation.PICTURE_RULE)
                            || prefix.toLowerCase().matches(HouseDataValidation.EXCEL_RULE))){
                        code = HouseCode.FILE_RULE_ERROR;
                        return code;
                    }
                    if (prefix.toLowerCase().matches(HouseDataValidation.EXCEL_RULE)){
                        excelCount++;
                    }
                    if (prefix.toLowerCase().matches(HouseDataValidation.PICTURE_RULE)
                            || prefix.toLowerCase().matches(HouseDataValidation.CAD_RULE)){
                        picCount++;
                    }
                }
            }else if (tempList[i].isDirectory()){
                code= HouseCode.ZIP_EXIST_DIRECTORY;
                return code;
            }
        }
        //上传excel文件个数不匹配
        if (excelCount != 1){
            code = HouseCode.ONLY_ONE_EXCEL;
        }
        //无图片
        if (picCount == 0){
            code = HouseCode.NO_PICTURE;
        }
        return code;
    }

    /**
     * <p>
     * 获取所有图片
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/23 13:48
     *
     * */
    private List<BuildMaterialFileStream> findPic(List<BuildMaterialFileStream> bms){
        List<BuildMaterialFileStream> pics = new ArrayList<>();
        for (BuildMaterialFileStream bb:
             bms) {
            String fileName=bb.getFileName();
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            if (prefix.toLowerCase().matches(HouseDataValidation.PICTURE_RULE)
                    || prefix.toLowerCase().matches(HouseDataValidation.CAD_RULE)){
                pics.add(bb);
            }
        }
        return pics;
    }

    /**
     * <p>
     * 判断excel文件模板是否正确
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/7/10 13:22
     *return boolean
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
        if (!"图纸类型".equals(row.getCell(3).getStringCellValue().trim())) {
            return false;
        }
        if (!"预览图片名".equals(row.getCell(4).getStringCellValue().trim())) {
            return false;
        }
        if (!"cad图片名".equals(row.getCell(5).getStringCellValue().trim())) {
            return false;
        }
        return true;
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
    private HashMap<String, Object> checkImportInfo(PicImportMsg importMsg,
                                                    List<BuildMaterialFileStream> bms,
                                                    HashMap<String,String> picMap,
                                                    String estateId) throws Exception {
        HashMap<String, Object> map = new LinkedHashMap<>();

        //楼宇不存在
        if (importMsg.getBuildingName() != null && houseService.findByBuildingName(importMsg.getBuildingName(),estateId) == null) {
            map.put("message", "该楼宇不存在");
            return map;
        }

        //单元不存在
        if (importMsg.getUnitName() != null
                && (houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), null) == null
                || houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(), null).size() == 0)) {
            map.put("message", "该单元不存在");
            return map;
        }

        //单元不为空时楼宇为空
        if (importMsg.getUnitName() != null && importMsg.getBuildingName() == null) {
            map.put("message", "楼宇不能为空");
            return map;
        }

        //该单元不属于该楼宇
        if (importMsg.getUnitName() != null){
            Building building = houseService.findByBuildingName(importMsg.getBuildingName(),estateId);
            List<BuildingUnit> unit = houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(),building.getBuildingId());
            if (unit == null || (unit != null && unit.size() == 0)){
                map.put("message", "该单元不属于该楼宇");
                return map;
            }
        }

        //房号为空
        if (StringUtil.isEmpty(importMsg.getHouseNum())) {
            map.put("message", "房号不能为空");
            return map;
        }

        //图纸类型为空
        if (StringUtil.isEmpty(importMsg.getHousePicType())) {
            map.put("message", "图纸类型不能为空");
            return map;
        }

        //图纸类型不存在
        if (houseService.findByHouseTypeName(importMsg.getHousePicType()) == null) {
            map.put("message", "图纸类型不存在");
            return map;
        }

        //预览图片和cad文件都为空
        if (StringUtil.isBlank(importMsg.getPictureName()) && StringUtil.isBlank(importMsg.getCadName())){
            map.put("message", "请至少上传一张图片");
            return map;
        }

        //预览图片名是否超过120个字
        if (!StringUtil.isBlank(importMsg.getPictureName()) && (importMsg.getPictureName().length() > HouseDataValidation.PIC_NAME_SIZE)){
            map.put("message", "预览图文件名称不能超过120个字");
            return map;
        }

        //预览图片格式是否正确
        if (!StringUtil.isBlank(importMsg.getPictureName())){
            String prefix=importMsg.getPictureName().substring(importMsg.getPictureName().lastIndexOf(".")+1);
            boolean flag = false;
            if (!StringUtil.isBlank(prefix) && prefix.toLowerCase().matches(HouseDataValidation.PICTURE_RULE)){
                flag = true;
            }
            if (!flag){
                map.put("message", "预览图片需以jpg、.jpeg、.bmp、.png为后缀");
                return map;
            }
        }

        //预览图片是否存在
        if (!StringUtil.isBlank(importMsg.getPictureName()) && validatePicExist(importMsg.getPictureName(),bms) == null){
            map.put("message", "预览图片不存在");
            return map;
        }

        //预览图片大小是否超过5M
        if (!StringUtil.isBlank(importMsg.getPictureName())
                && validatePicExist(importMsg.getPictureName(),bms).getFileSize() > HouseDataValidation.PICTURE_MAX_SIZE){
            map.put("message", "压缩包中的每个图片不能超过5M");
            return map;
        }

        //cad文件名是否超过120个字
        if (!StringUtil.isBlank(importMsg.getCadName()) && (importMsg.getCadName().length() > HouseDataValidation.PIC_NAME_SIZE)){
            map.put("message", "dwt文件名称不能超过120个字");
            return map;
        }

        //cad图片格式是否正确
        if (!StringUtil.isBlank(importMsg.getCadName())){
            String prefix=importMsg.getCadName().substring(importMsg.getCadName().lastIndexOf(".")+1);
            boolean flag = false;
            if (!StringUtil.isBlank(prefix) && prefix.toLowerCase().matches(HouseDataValidation.CAD_RULE)){
                flag = true;
            }
            if (!flag){
                map.put("message", "CAD图片名称要以.dwt为后缀");
                return map;
            }
        }

        //cad图片是否存在
        if (!StringUtil.isBlank(importMsg.getCadName()) && validatePicExist(importMsg.getCadName(),bms) == null){
            map.put("message", "cad文件不存在");
            return map;
        }

        //cad图片大小是否超过5M
        if (!StringUtil.isBlank(importMsg.getCadName())
                && validatePicExist(importMsg.getCadName(),bms).getFileSize() > HouseDataValidation.CAD_MAX_SIZE){
            map.put("message", "压缩包中的每个cad文件大小不能超过5M");
            return map;
        }

        //判断房屋是否存在
        AddHouseReq addHouseReq = new AddHouseReq();
        addHouseReq.setHouseNum(importMsg.getHouseNum());
        if (!StringUtil.isBlank(importMsg.getBuildingName())){
            addHouseReq.setBuildingId(houseService.findByBuildingName(importMsg.getBuildingName(),estateId).getBuildingId());
        }
        if (!StringUtil.isBlank(importMsg.getUnitName())){
            addHouseReq.setUnitId(houseService.findByUnitNameAndBuildingId(importMsg.getUnitName(),addHouseReq.getBuildingId()).get(0).getUnitId());
        }
        addHouseReq.setEstateId(estateId);
        House house = houseService.findByMultipleParams(addHouseReq);
        if (house == null) {
            map.put("message", "该房屋不存在");
            return map;
        }

        //如果以上条件都满足，则生成housePicture对象
        HousePic pic = new HousePic();
        pic.setHouseId(house.getHouseId());
        pic.setCreateTime(new Date(System.currentTimeMillis()));
        pic.setPictureType(importMsg.getHousePicType());
        //上传图片
        if (!StringUtil.isBlank(importMsg.getPictureName())){
            pic.setPictureId(uploadPic(importMsg.getPictureName(),validatePicExist(importMsg.getPictureName(),bms).getIn(),picMap));
        }
        if (!StringUtil.isBlank(importMsg.getCadName())){
            pic.setCadId(uploadPic(importMsg.getCadName(),validatePicExist(importMsg.getCadName(),bms).getIn(),picMap));
        }
        map.put("housePic",pic);
        return map;
    }

    /**
     * <p>
     * 校验图片是否存在
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/23 13:57
     *
     * */
    private BuildMaterialFileStream validatePicExist(String picName, List<BuildMaterialFileStream> bms){
        BuildMaterialFileStream in = null;
        for (BuildMaterialFileStream bb:
             bms) {
            if (bb.getFileName().equals(picName)){
                in = bb;
                break;
            }
        }
        return in;
    }

    /**
     * <p>
     * 上传图片
     * </p>
     * Author: Xia.yx <br/>
     * Date: 2017/9/23 15:15
     * @param picName
     * @param in
     * @return picId
     *
     * */
    private String uploadPic(String picName,InputStream in,HashMap<String,String> picMap) throws Exception {
        //判断上传的是不是同一张图片
        if (null != picMap.get(picName) && !"".equals(picMap.get(picName))){
            return picMap.get(picName);
        }
        //原文件名加密
        String name = FileUtil.getFileName(picName);
        //设置图片保存全路径
        String path = ConfigManager.read(ConfigName.FILE_DIR) + "house/" + name;
        //创建文件对象
        File file = new File(path);
        FileInputStream inputStream = (FileInputStream) in;
        //上传文件
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            LOG.error("============uploadPic error=============",e);
        }finally {
            if (null != inputStream) {inputStream.close();}
        }
        //创建图片对象
        Picture picture = new Picture();
        picture.setPictureName(picName);
        String imgUrl = "house/" + name;
        picture.setPictureUrl(imgUrl);
        picture.setCreateTime(new Date(System.currentTimeMillis()));
        //上传图片
        houseService.insertPicture(picture);
        //将上传后的picture保存到map中
        picMap.put(picName,picture.getPictureId());
        return picture.getPictureId();
    }
}

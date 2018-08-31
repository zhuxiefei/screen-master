package com.betel.estatemgmt.business.web.material.controller;

import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.material.code.BuildMaterialCode;
import com.betel.estatemgmt.business.web.material.constant.BuildMaterialConstant;
import com.betel.estatemgmt.business.web.material.constant.BuildMaterialValidation;
import com.betel.estatemgmt.business.web.material.controller.util.*;
import com.betel.estatemgmt.business.web.material.model.BuildMaterialFileStream;
import com.betel.estatemgmt.business.web.material.model.DefalutBuildMaterial;
import com.betel.estatemgmt.business.web.material.model.HouseMaterialExcel;
import com.betel.estatemgmt.business.web.material.service.BuildMaterialService;
import com.betel.estatemgmt.common.*;
import com.betel.estatemgmt.common.model.house.HouseMaterial;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.StringUtil;
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

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.betel.estatemgmt.business.web.material.controller.util.ZipUtils.checZipFile;


/**
 * <p>
 * 批量导入家具建材信息
 * </p>
 * ClassName: BuildMaterialImportController <br/>
 * Author: 张建 <br/>
 * Date: 2017/6/28 14:07 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("web/material")
public class BuildMaterialImportController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BuildMaterialImportController.class);

    @Autowired
    private BuildMaterialService buildMaterialService;

    /**
     * <p>
     * 批量导入建材
     * </P>
     *
     * @param zipFileUrl zip文件流
     *                   Author: zhangjian <br/>
     *                   Date: 2017/6/21 13:44
     * @return
     */
    @RequiresPermissions(value = "materialManage-importBulkBuildMaterial")
    @RequestMapping(value = "v1/importBulkBuildMaterial", method = RequestMethod.POST)
    public Response<Object> importBulkBuildMaterial(MultipartFile zipFileUrl) {
        if (LOG.isInfoEnabled()) {
            LOG.info("======web/material/v1/importBulkBuildMaterial start========importBulkBuildMaterial:=" + zipFileUrl);
        }

        Response<Object> response = new Response<Object>();
        //判断文件是否为空
        if (null == zipFileUrl) {
            response.setCode(BuildMaterialCode.MATERIAL_UPLOAD_ZIP_ISNULL);
            return response;
        }
        //判断压缩文件是否是zip文件格式
        String[] allowedZipType = {"application/x-zip-compressed", "application/octet-stream","application/zip"};
        if (LOG.isDebugEnabled()){
            LOG.debug("---------------importBulkBuildMaterial----------------->>>>>>>--zipFileUrl.getContentType()="+zipFileUrl.getContentType());
        }
        boolean allowedZip = Arrays.asList(allowedZipType).contains(zipFileUrl.getContentType());
        if (LOG.isDebugEnabled()) {
            LOG.debug("前端传的压缩文件格式，是否符合要求" + allowedZip);
        }
        if (!allowedZip) {
            response.setCode(BuildMaterialCode.MATERIAL_ZIP_FORMAT_ERROR);
            return response;
        }
        // 判断压缩文件大小限制
        if (zipFileUrl.getSize() > BuildMaterialValidation.MATERIAL_ZIP_LENGTH_MAX_RULE) {
            response.setCode(BuildMaterialCode.MATERIAL_ZIP_SIZE_OVER_MAX_LIMITE);
            return response;
        }
        try {
            /**
             * 转存
             */
            String oldFilePath = ZipUtils.archivedZipFiles(zipFileUrl);

            /**
             * 解压
             */
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String targetPath = ConfigManager.read(ConfigName.FILE_DIR) + "importBuildMaterial/" + format.format(new Date());
            UpZipFile.unzip(oldFilePath, targetPath);
            /**
             * ==============必要==不然会报错====================
             * 解压后读取文件前必须判断是否在根路径下
             */
            String code = checkIsCompressionUnderRootPath(targetPath);
            if (null != code && !"".equalsIgnoreCase(code)) {
                response.setCode(code);
                return response;
            }
            /**
             * =====================开始读取zip里面的内容开始=================================
             */

            List<BuildMaterialFileStream> buildMaterialFileStreamList = ZipUtils.readUnzipFileStream(targetPath);
            //判断zip包内是否有文件夹，判断zip包内有几个excel文件，判断zip包内图片是大小是否超过5M/张
            code = checZipFile(buildMaterialFileStreamList);
            if (!"".equals(code) && null != code) {
                response.setCode(code);
                return response;
            }
            /**
             * 开始读取excel，这里是为了后期多个excel:
             */
            List<InputStream> excelFileStreams = new ArrayList<>();
            for (BuildMaterialFileStream bms : buildMaterialFileStreamList) {
                if (ZipUtils.checkExcel2003(bms.getFileName())) {
                    InputStream inputStream = bms.getIn();
                    excelFileStreams.add(inputStream);
                }
            }
            /**
             * 遍历excel文件流对象
             */
            Map<String, Object> picUploadNames = new HashMap<>();//存着已经上传的图片集合（只有一个sheet）
            List<String> excelMaterialNames = new ArrayList<>();
            List<String> houseMaterialsNamesAdd = new ArrayList<String>();
            int countRow = 0;//计算遍历单元格的次数
            for (InputStream inStream : excelFileStreams) {
                HSSFWorkbook workbook = new HSSFWorkbook(inStream);
                Sheet sheet = workbook.getSheetAt(0);
                // 获取到excel文件中的所有行数
                int rows = sheet.getPhysicalNumberOfRows();
                int lastrow = sheet.getLastRowNum();

                /**
                 * 由于excel兼容问题
                 * excel里没有任何数据时直接返回code
                 * 第一种判空的情况
                 */
                if (rows <= 1) {
                    response.setCode(BuildMaterialCode.METERIAL_UPLOAD_ZIP_EXCEL_IS_NULL);
                    return response;
                }

                /**
                 * 当有数据时遍历每行数据
                 */

                List<DefalutBuildMaterial> defalutBuildMaterials = new ArrayList<>();//创建错误集合
                List<HouseMaterial> houseMaterials = new ArrayList<>();//合法的信息

                for (int i = 1; i <= lastrow; i++) {
                    Row row = sheet.getRow(i);
                    if (null == row) {//提交点速度
                        continue;
                    }
                    /**
                     * 解决excel兼容性：excel有时候会把空行当做空字符串行，row！=null
                     */
                    if (isRowEmpty(row)) {
                        continue;
                    }

                    HouseMaterialExcel hmx = new HouseMaterialExcel();

                    //=====================建材类型=====================
                    Cell cell = row.getCell(0);

                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialType(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材名称=====================
                    cell = row.getCell(1);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialName(cell.getStringCellValue().trim());
                        }
                    }
                    cell = row.getCell(2);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialPicName(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材品牌=====================
                    cell = row.getCell(3);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialBrand(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材型号=====================
                    cell = row.getCell(4);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialModel(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材规格=====================
                    cell = row.getCell(5);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialSpecification(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材产地=====================
                    cell = row.getCell(6);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialOrigin(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材生产日期=====================
                    cell = row.getCell(7);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialProduceDate(cell.getStringCellValue().trim());
                        }
                    }
                    //=====================建材截止日期=====================
                    cell = row.getCell(8);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!StringUtil.isBlank(cell.getStringCellValue())) {
                            hmx.setMaterialWarrantyDate(cell.getStringCellValue().trim());
                        }
                    }
                    countRow++;
                    //进行校验
                    Map<String, Object> result = checkExcelRowsIsLegal(hmx, buildMaterialService, buildMaterialFileStreamList, excelMaterialNames);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("校验前端参数，不符合要求=" + result);
                    }
                    //不合法，把数据返回前端
                    if (0 < result.size()) {
                        DefalutBuildMaterial defalutBuildMaterial = new DefalutBuildMaterial();
                        defalutBuildMaterial.setMaterialName(hmx.getMaterialName());
                        defalutBuildMaterial.setMaterialType(hmx.getMaterialType());
                        defalutBuildMaterial.setMaterialPicName(hmx.getMaterialPicName());
                        defalutBuildMaterial.setMaterialBrand(hmx.getMaterialBrand());
                        defalutBuildMaterial.setMaterialModel(hmx.getMaterialModel());
                        defalutBuildMaterial.setMaterialSpecification(hmx.getMaterialSpecification());
                        defalutBuildMaterial.setMaterialOrigin(hmx.getMaterialOrigin());
                        defalutBuildMaterial.setMaterialProduceDate(hmx.getMaterialProduceDate());
                        defalutBuildMaterial.setMaterialWarrantyDate(hmx.getMaterialWarrantyDate());
                        defalutBuildMaterial.setResult(result.get("message").toString());
                        defalutBuildMaterials.add(defalutBuildMaterial);
                    }
                    //合法的时候
                    if (result.size() == 0) {
                        //长传图片:返回
                        String picId = findTruePicUpload(hmx.getMaterialPicName(), ZipUtils.getImagelMapStrem(buildMaterialFileStreamList), buildMaterialService, picUploadNames);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("找到zip里对应图片上传后返回图片id=" + picId);
                        }
                        //设置值
                        HouseMaterial hm = new HouseMaterial();
                        hm.setMaterialName(hmx.getMaterialName());
                        hm.setMaterialType(hmx.getMaterialType());
                        hm.setMaterialPic(picId);
                        hm.setMaterialBrand(hmx.getMaterialBrand());
                        hm.setMaterialModel(hmx.getMaterialModel());
                        hm.setMaterialSpecification(hmx.getMaterialSpecification());
                        hm.setMaterialOrigin(hmx.getMaterialOrigin());

                        if (!StringUtil.isBlank(hmx.getMaterialProduceDate())) {
                            hm.setMaterialProduceDate(new SimpleDateFormat("yyyy-MM-dd").parse(hmx.getMaterialProduceDate()));
                        }
                        if (!StringUtil.isBlank(hmx.getMaterialWarrantyDate())) {
                            hm.setMaterialWarrantyDate(new SimpleDateFormat("yyyy-MM-dd").parse(hmx.getMaterialWarrantyDate()));
                        }
                        hm.setCreateTime(new Date());
                        //新增名称集合
                        houseMaterialsNamesAdd.add(hm.getMaterialName());
                        houseMaterials.add(hm);
                    }

                }//-------------------------------------一次循环结束，返回前端数据-----------------------------------------------------
                /**
                 * ====================批量插入==============================
                 */
                //进行批量插入
                if (null != houseMaterials && houseMaterials.size() > 0) {
                    int insertFlag = buildMaterialService.bulkImportHouseMaterial(houseMaterials);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("========web/user/v1/importBuildMaterial 插入材料对象信息    end========response" + insertFlag);
                    }
                    //更新材料名称集合内存
                    if (insertFlag > 0) {
                        boolean flag = MaterialUtil.materialNameList.addAll(houseMaterialsNamesAdd);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("========web/user/v1/importBuildMaterial 材料名称materialNameList新增    end========response" + flag);
                        }
                    }
                }
                if (null != defalutBuildMaterials && !defalutBuildMaterials.isEmpty()) {
                    response.setData(defalutBuildMaterials);
                }

                if (houseMaterials.size() == 0 && defalutBuildMaterials.size() == 0) {
                    response.setCode(BuildMaterialCode.METERIAL_UPLOAD_ZIP_EXCEL_IS_NULL);
                    return response;
                }
            }

        } catch (Exception e) {
            DeletFileUtil.delAllFileOfGarbageCollection(ConfigManager.read(ConfigName.FILE_DIR) + "importBuildMaterial/");
            response.setCode(StatusCode.FAILURE);
            LOG.error("========web/user/v1/importBuildMaterial error========", e);
        }


        // 生成日志

        if (LOG.isInfoEnabled()) {
            LOG.debug("========web/user/v1/importBuildMaterial end========response" + response);
        }

        if (null != response.getData()) {
            DeletFileUtil.delAllFileOfGarbageCollection(ConfigManager.read(ConfigName.FILE_DIR) + "importBuildMaterial/");
        } else {
            DeletFileUtil.delAllFile(ConfigManager.read(ConfigName.FILE_DIR) + "importBuildMaterial/");
        }
        return response;
    }

    /**
     * <p>
     * 查找zip包里正确的图片实体，上传服务器
     * </p>
     *
     * @param excelPicName         Excel里图片名称
     * @param getImagelListMap     zip图片流集合
     * @param buildMaterialService 服务层
     *                             Author: zhangjian <br/>
     *                             Date: 2017/6/21 13:44
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String findTruePicUpload(String excelPicName, Map<String, Object> getImagelListMap, BuildMaterialService buildMaterialService, Map<String, Object> picUploadNames) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        for (int i=0;i<getImagelListMap.size();i++){
        //获取图片流对象
        String picId = "";
        if (null != picUploadNames.get(excelPicName) && !"".equals(picUploadNames.get(excelPicName))) {
            picId = (String) picUploadNames.get(excelPicName);
            return picId;
        }
        FileInputStream picStream = (FileInputStream) getImagelListMap.get(excelPicName);
        //拼接时间名字，防止名称重复
//            String timePathFile=new  SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+"_"+excelPicName;
        if (StringUtil.isBlank(excelPicName)) {
            picUploadNames.put(excelPicName, picId);
            return picId;
        }
        String excelPicNameNew = FileUtil.getFileName(excelPicName);
        String fullPath = ConfigManager.read(ConfigName.FILE_DIR) + "buildMaterialPic/" + excelPicNameNew;
        //创建文件对象
        File file = new File(fullPath);
        //上传文件(url)
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                try {
                    if (null != picStream) {
                        picStream.close();
                    }
                } catch (IOException e1) {
                    LOG.error("=========web/material/v1/createNew !=========" + e1);
                }
                LOG.error("=========web/material/v1/createNew !=========" + e);
            }
        }
        //上传文件(url)
        try {
            FileUtils.copyInputStreamToFile(picStream, file);
        } catch (IOException e) {
            LOG.error("=========web/material/v1/findTruePicUpload !=========" + e);
        } finally {
            try {
                if (null != picStream) {
                    picStream.close();
                }
            } catch (IOException e) {
                LOG.error("=========web/material/v1/createNew !=========" + e);
            }
        }
        String imgUrl = "buildMaterialPic/" + excelPicNameNew;
        //创建图片对象
        Picture picture = new Picture();
        picture.setPictureName(excelPicNameNew);
        picture.setPictureUrl(imgUrl);
        picture.setCreateTime(new Date());
        //将建材图片插入数据库  增加图片自动增加pictureId(主键)
        buildMaterialService.addBuildMaterilPicture(picture);
        picId = picture.getPictureId();
        picUploadNames.put(excelPicName, picId);
        return picId;
    }

    /**
     * <p>
     * 校验excel每行代码的合法性
     * </p>
     *
     * @param hmx
     * @param buildMaterialService
     * @param bmfs                 Author: zhangjian <br/>
     *                             Date: 2017/6/21 13:44
     * @return
     */
    public static Map<String, Object> checkExcelRowsIsLegal(HouseMaterialExcel hmx, BuildMaterialService buildMaterialService, List<BuildMaterialFileStream> bmfs, List<String> excelMaterialNames) {
        Map<String, Object> map = new HashMap<>();
        //=======================验证建材类型名称========================================
        //1.校验是否为空
        if (StringUtil.isBlank(hmx.getMaterialType())) {
            map.put("code", BuildMaterialCode.MATERIAL_TYPE_ISNULL);
            map.put("message", "材料类型名称为空");
            return map;
        }
        //2.是否含有特殊字符
        if (!StringUtil.isBlank(hmx.getMaterialType())) {
            if (!hmx.getMaterialType().trim().matches(BuildMaterialValidation.MATERIAL_NAME_RULE)) {
                map.put("code", BuildMaterialCode.MATERIAL_TYPE_NAME_ERROR);
                map.put("message", "平台中不存在该建材类型");
                return map;
            } else {
                boolean isExist = MaterialUtil.findCurMaterialTypeNameIsExist(hmx.getMaterialType().trim());
                if (isExist == false) {
                    map.put("code", BuildMaterialCode.MATERIAL_TYPE_NAME_ERROR);
                    map.put("message", "平台中不存在该建材类型");
                    return map;
                }
            }
        }
        //======================验证建材名==============================
        //1.校验是否为空
        if (StringUtil.isBlank(hmx.getMaterialName())) {
            map.put("code", BuildMaterialCode.MATERIAL_NAME_ISNULL);
            map.put("message", "材料名称为空");
            return map;
        }
        //2、校验是否有非法字符
        if (!StringUtil.isBlank(hmx.getMaterialName())) {
            if (hmx.getMaterialName().trim().length() > BuildMaterialConstant.NAME_LENGTH) {
                map.put("code", BuildMaterialCode.MATERIAL_NAME_RULE_NOT_TRUE);
                map.put("message", "材料名称长度不能超过20个字符");
                return map;
            }
            boolean flag = hmx.getMaterialName().trim().matches(BuildMaterialValidation.MATERIAL_NAME_RULE);
            if (flag == false) {
                map.put("code", BuildMaterialCode.MATERIAL_NAME_RULE_NOT_TRUE);
                map.put("message", "材料名称不能为\\<>’”%");
                return map;
            } else {
                boolean isExist = isMaterialNameExist(hmx.getMaterialName().trim(), excelMaterialNames);
                if (isExist) {
                    map.put("code", BuildMaterialCode.MATERIAL_NAME_RULE_NOT_TRUE);
                    map.put("message", "材料名称重复");
                }
            }
        }


        //3.=======================验证建材图片名称========================================
        if (!StringUtil.isBlank(hmx.getMaterialPicName())) {
            if (hmx.getMaterialPicName().length() > BuildMaterialCode.PIC_NAME_SIZE) {
                map.put("code", BuildMaterialCode.PICTURE_NAME_MAX);
                map.put("message", "图片名称长度不能超过120个字");
                return map;
            }
            //校验尾巴是否含有合法格式
            if (!ZipUtils.checkImage(hmx.getMaterialPicName().trim())) {
                map.put("code", BuildMaterialCode.MATERIAL_ZIP_EXCEL_IMAGE_ISLEGAL);
                map.put("message", "“请输入jpg、.jpeg、.bmp、.png为后缀”的图片名称");
                return map;
            } else {
                //通过图片名称到zip包里找实体图片
                List<String> zipPicNames = ZipUtils.getImagelList(bmfs);
                /**
                 * 备注：没有图片会是否报错
                 */
                boolean isExist = false;
                for (int i = 0; i < zipPicNames.size(); i++) {
                    String curPicName = hmx.getMaterialPicName();//没填就是空
                    if (!StringUtil.isBlank(curPicName)) {
                        if (curPicName.equalsIgnoreCase(zipPicNames.get(i))) {
                            isExist = true;
                            break;
                        }
                    }
                }
                if (!isExist) {
                    map.put("code", BuildMaterialCode.MATERIAL_EXCEL__IMAGE_NOT_IN_ZIP);
                    map.put("message", "ZIP包内未找到该名称的图片文件");
                    return map;
                }
            }
        }
        //3.=======================验证建材品牌========================================
        //校验不为空时
        if (!StringUtil.isBlank(hmx.getMaterialBrand())) {
            if (hmx.getMaterialBrand().trim().length() > BuildMaterialValidation.MATERIAL_BRAND_RULE) {
                map.put("code", BuildMaterialCode.MATERIAL_BRAND_DESCRIP_WORD_NUM_MORE_MOST);
                map.put("message", "材料品牌长度不能超过20个字符");
                return map;
            }
        }

        //4.=======================验证建材型号========================================
        if (!StringUtil.isBlank(hmx.getMaterialModel())) {
            if (hmx.getMaterialModel().trim().length() > BuildMaterialValidation.MATERIAL_MODEL_RULE) {
                map.put("code", BuildMaterialCode.MATERIAL_MODEL_DESCRIP_WORD_NUM_MORE_MOST);
                map.put("message", "材料型号长度不能超过20个字符");
                return map;
            }
        }
        //5.=======================验证建材规格========================================
        if (!StringUtil.isBlank(hmx.getMaterialSpecification())) {
            if (hmx.getMaterialSpecification().trim().length() > BuildMaterialValidation.MATERIAL_SPECIFICATION_RULE) {
                map.put("code", BuildMaterialCode.MATERIAL_SPECIFICATION_DESCRIP_WORD_NUM_MORE_MOST);
                map.put("message", "材料规格长度不能超过20个字符");
                return map;
            }
        }
        //6.=======================验证建材产地========================================
        if (!StringUtil.isBlank(hmx.getMaterialOrigin())) {
            if (hmx.getMaterialOrigin().trim().length() > BuildMaterialValidation.MATERIAL_ORIGIN_RULE) {
                map.put("code", BuildMaterialCode.MATERIAL_ORIGIN_DESCRIP_WORD_NUM_MORE_MOST);
                map.put("message", "材料产地长度不能超过50个字符");
                return map;
            }
        }
        //7.=======================验证建材生产日期====================================
        if (null != hmx.getMaterialProduceDate()) {
            Boolean flag = hmx.getMaterialProduceDate().matches(BuildMaterialValidation.MATERIAL_PRODUCE_DATE_RULE);
            if (flag == false) {
                map.put("code", BuildMaterialCode.MATERIAL_PRODUCE_DATE_RULE_ILLEGAL);
                map.put("message", "材料生产日期格式不正确，格式应为yyyy-mm-dd，其中mm为01~12之间的整数，dd为01~31之间的整数");
                return map;
            }
        }
        //8.=======================验证建材截止日期====================================
        if (null != hmx.getMaterialWarrantyDate()) {
            Boolean flag = hmx.getMaterialWarrantyDate().matches(BuildMaterialValidation.MATERIAL_WARRANTY_DATE_RULE_RULE);
            if (flag == false) {
                map.put("code", BuildMaterialCode.MATERIAL_WARRANTY_DATE_RULE_ILLEGAL);
                map.put("message", "材料截止日期格式不正确，格式应为yyyy-mm-dd，其中mm为01~12之间的整数，dd为01~31之间的整数");
                return map;
            }
        }
        //9.=======================验证生产日期<建材截止日期====================================
        if (!StringUtil.isBlank(hmx.getMaterialProduceDate()) && !StringUtil.isBlank(hmx.getMaterialWarrantyDate())) {
            if (DateUtil.compareDate(hmx.getMaterialProduceDate(), hmx.getMaterialWarrantyDate())) {
                map.put("code", BuildMaterialCode.MATERIAL_PRODUCTDATE_MORE_THAN_WARRANTYDATE);
                map.put("message", "生产日期不能大于截止日期");
                return map;
            }
        }
        return map;
    }

    /**
     * <p>
     * 校验excel文件和图片是否在根路径下压缩
     * </p>
     *
     * @param targetPath Author: zhangjian <br/>
     *                   Date: 2017/6/21 13:44
     * @return
     */
    public static String checkIsCompressionUnderRootPath(String targetPath) {
        String code = "";
        //由指定路径创建目标文件
        File file = new File(targetPath);
        //获得指定路径下的文件集合，包含文件夹
        File[] tempList = file.listFiles();
        LOG.info("该目标目录下文件个数：" + tempList.length);//打印长度
        if (tempList.length == 0) {
            LOG.info("============importBuildMaterial=====zipFile========文件为空");
            return code = BuildMaterialCode.METERIAL_TARGET_FILE_IS_NULL;
        }
        //遍历文件集合，解析目标文件集合内容
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                LOG.info("========web/user/v1/importUser end========isFile" + true);
                continue;
            } else if (tempList[i].isDirectory()) {
                LOG.info("========web/user/v1/importUser end========isFile" + false);
                return code = BuildMaterialCode.MATERIAL_ZIP_EXCEL_PIC_NOT_ROOT_DIRECTORY;
            }
            return code;
        }
        return code;
    }

    /**
     * <p>
     * 校验excel单元格是否是空行
     * </p>
     *
     * @param row Author: zhangjian <br/>
     *            Date: 2017/6/21 13:44
     * @return true 空  false  非空
     */
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }


    /**
     * <p>
     * 校验excel中材料名称是否重复
     * </p>
     *
     * @param curMaterialName    当前材料名称
     * @param excelMaterialNames 动态的遍历中excel里材料名称集合
     *                           Author: zhangjian <br/>
     *                           Date: 2017/6/21 13:44
     * @return true 空  false  非空
     */
    public static boolean isMaterialNameExist(String curMaterialName, List<String> excelMaterialNames) {
        /**
         * =====================批量插入==================================
         */

        if (excelMaterialNames.contains(curMaterialName)) {
            return true;
        } else {
            excelMaterialNames.add(curMaterialName);
        }
        boolean isExist = MaterialUtil.findCurMaterialNameListIsExist(curMaterialName);
        if (isExist) {
            return true;
        }
        return false;
    }
}

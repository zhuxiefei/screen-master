package com.betel.estatemgmt.business.propertyapp.solve.controller;

import com.betel.estatemgmt.business.propertyapp.assign.model.FindRepairListResp;
import com.betel.estatemgmt.business.propertyapp.assign.service.AssignService;
import com.betel.estatemgmt.business.propertyapp.solve.model.RepairListStatusReq;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.repair.code.RepairCode;
import com.betel.estatemgmt.business.web.repair.constant.RepairDataValidation;
import com.betel.estatemgmt.business.web.repair.model.UploadOrderPicReq;
import com.betel.estatemgmt.business.web.repair.service.RepairService;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.system.Picture;
import com.betel.estatemgmt.common.model.repair.RepairOrder;
import com.betel.estatemgmt.utils.FileUtil;
import com.betel.estatemgmt.utils.HttpClientUtil;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Describe this class...
 * </p>
 * ClassName: SolveController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/23 14:26 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/solve")
public class SolveController {

    private static final Logger LOG = LoggerFactory.getLogger(SolveController.class);

    @Autowired
    AssignService assignService;

    @Autowired
    RepairService repairService;

    /**
     * <p>
     * 报修单列表:已接单、已取消、已完成（分页）
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/22 16:07
     * @param repairListStatusReq
     * @return
     */
    @RequestMapping(value = "v1/findRepairListByStatus",method = RequestMethod.GET)
    public Response findRepairListByStatus(RepairListStatusReq repairListStatusReq,HttpServletRequest request){
        if(LOG.isInfoEnabled()){
            LOG.info("---app/solve/v1/findRepairListByStatus--------------start===repairListStatusReq="+repairListStatusReq);
        }
        Response response = new Response();
        try{
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            //获取当前物业人员ID，作为指派者id
            String userId = request.getHeader("userId");
            List<FindRepairListResp> repairLists = null;
            Paging<FindRepairListResp> page = new Paging<FindRepairListResp>(repairListStatusReq.getCurPage(),repairListStatusReq.getPageSize());
            repairLists = assignService.findRepairListByStatus(page,repairListStatusReq.getOrderStatus(),
                    AESUtil.decrypt(userId),AESUtil.decrypt(estateId));
            page.result(repairLists);
            response.setData(page);
        }catch (Exception e){
            LOG.error("---app/solve/v1/findRepairListByStatus-----error--");
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/solve/v1/findRepairListByStatus--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 完成维修单
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2017/12/23 15:24
     * @param picReq
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "v1/solveRepair",method = RequestMethod.POST)
    public Response solveRepair(UploadOrderPicReq picReq, MultipartFile multipartFile,HttpServletRequest request){
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/solve/v1/solveRepair start========multipartFile" + multipartFile);
        }
        Response<String> response = new Response<>();
        if (null == multipartFile) {
            response.setCode(RepairCode.FILE_NULL_ERROR);
        } else if (StringUtil.isBlank(picReq.getOrderNo())){
            response.setCode(RepairCode.REPAIRID_NULL_ERROR);
        } else {
            RepairOrder order = repairService.findByOrderNo(picReq.getOrderNo());
            if (order == null){
                response.setCode(RepairCode.REPAIR_NOT_EXIST);
            }else if (order.getOrderStatus().equals(RepairDataValidation.ORDER_FINISH_STATUS)){
                response.setCode(RepairCode.REPAIR_IS_DONE);
            }else if (order.getOrderStatus().equals(RepairDataValidation.ORDER_QUXIAO_STATUS)){
                response.setCode(RepairCode.REPAIR_IS_CANCEL);
            }else {
                //获取原始文件名（包括类型）
                String picName = multipartFile.getOriginalFilename();
                if (LOG.isDebugEnabled()){
                    LOG.debug("========originalFilename=========" + picName);
                }
                //获取文件类型
                String picType = picName.substring(picName.lastIndexOf(".") + 1);
                if (LOG.isDebugEnabled()){
                    LOG.debug("========originalFileType=========" + picType);
                }
                //判断文件格式和大小
                if (picName.length() > RepairDataValidation.PICNAME_MAX_LENGTH){
                    response.setCode(RepairCode.FILENAME_SIZE_ERROR);
                } else if(multipartFile.getSize() > RepairDataValidation.PIC_MAX_SIZE){
                    response.setCode(RepairCode.FILE_SIZE_ERROR);
                } else if(!picType.toLowerCase().matches(RepairDataValidation.PIC_TYPE_RULE)){
                    response.setCode(RepairCode.FILE_RULE_ERROR);
                } else {
                    //设置图片保存路径
                    String path = ConfigManager.read(ConfigName.FILE_DIR) + "repair/";
                    String name = "";
                    try {
                        //上传后的新文件名
                        name = FileUtil.uploadFile(multipartFile, path);
                        if (LOG.isDebugEnabled()){
                            LOG.debug("========newFilename=========" + name);
                        }
                    } catch (NoSuchAlgorithmException | IOException e) {
                        LOG.error("========app/solve/v1/solveRepair error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                    //将数据写入Picture对象中
                    Picture picture = new Picture();
                    picture.setCreateTime(new Date(System.currentTimeMillis()));
                    picture.setPictureName(picName);
                    picture.setPictureUrl("repair/" + name);
                    try {
                        String assigner = request.getHeader("userId");
                        //查询指派者的信息
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("employeeId", AESUtil.decrypt(assigner));
                        Response r = HttpClientUtil.post(ConfigManager.read(ConfigName.OA_PROJECT_URL)+"httpclientOA/common/v1/findUserInfo",jsonParam,request);
                        if (null != r){
                            JSONObject json = JSONObject.fromObject(r.getData());
                            assignService.uploadPic(picture,picReq,json,AESUtil.decrypt(assigner));
                        }
                    } catch (Exception e) {
                        LOG.error("========app/solve/v1/solveRepair error!=========", e);
                        response.setCode(StatusCode.FAILURE);
                    }
                }
            }
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========app/solve/v1/uploadOrderPic end========response" + response);
        }
        return response;
    }

}

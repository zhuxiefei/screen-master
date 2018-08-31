package com.betel.estatemgmt.business.propertyapp.decoration.controller;

import com.betel.estatemgmt.business.propertyapp.decoration.model.DoDecorationReq;
import com.betel.estatemgmt.business.propertyapp.decoration.model.QueryDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.code.DecorationCode;
import com.betel.estatemgmt.business.userapp.decoration.constant.DecorationConstant;
import com.betel.estatemgmt.business.userapp.decoration.model.AddDecorationReq;
import com.betel.estatemgmt.business.userapp.decoration.service.DecorationService;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.common.model.decoration.DecoApplyRecord;
import com.betel.estatemgmt.utils.StringUtil;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import com.betel.estatemgmt.utils.pagination.model.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * App-物业app装修申请管理
 * </p>
 * ClassName: DecorationController <br/>
 * Author: Cui.xx  <br/>
 * Date: 2017/12/5 13:52 <br/>
 * Version: 1.0 <br/>
 */
@RestController
@RequestMapping("propertyApp/decoration")
public class DecorationController {

    private static final Logger LOG = LoggerFactory.getLogger(DecorationController.class);

    @Autowired
    DecorationService decorationService;

    @Autowired
    HouseService houseService;

    /**
     * <p>
     * 物业人员添加装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 15:08
     * @param addDecorationReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "v1/addDecoration",method = RequestMethod.POST)
    public Response addDecoration(@RequestBody @Valid AddDecorationReq addDecorationReq, BindingResult bindingResult,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/decoration/v1/addDecoration--------------start====addDecorationReq===" + addDecorationReq);
        }
        Response response = new Response();
        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        try {
            //判断房屋是否被删除
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            boolean isDeleted = decorationService.houseIsDeleted(addDecorationReq.getHouseId(), AESUtil.decrypt(estateId));
            if(isDeleted){
                response.setCode(DecorationCode.HOUSE_IS_DELETED);
            }else {
                decorationService.addDecoration(addDecorationReq);
            }
        }catch (Exception e){
            LOG.error("---app/decoration/v1/addDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/decoration/v1/addDecoration--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 物业人员处理装修申请
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 15:08
     * @return
     */
    @RequestMapping(value = "v1/doDecoration",method = RequestMethod.POST)
    public Response doDecoration(@RequestBody @Valid DoDecorationReq doDecorationReq,
                                 BindingResult bindingResult,
                                 @RequestHeader("userId") String userId, HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/decoration/v1/doDecoration--------------start=======");
        }
        Response response = new Response();
        //校验入参
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            response.setCode(errorList.get(0).getDefaultMessage());
            return response;
        }
        try {
            DecoApplyRecord decoApplyRecord = decorationService.findDecoration(doDecorationReq.getRecordId());
            if(null != decoApplyRecord){
                //已处理的、已取消的不能再处理
                if(!DecorationConstant.DECORATION_STATUS_SOLVING.equals(decoApplyRecord.getStatus())){
                    if(DecorationConstant.DECORATION_STATUS_REFUSE.equals(doDecorationReq.getStatus())||
                            DecorationConstant.DECORATION_STATUS_AGREE.equals(doDecorationReq.getStatus())){
                        response.setCode(DecorationCode.STATUS_IS_CHANGED);
                    }else {
                        decorationService.doDecoration(doDecorationReq,userId,"app",request);
                    }
                }else {
                    decorationService.doDecoration(doDecorationReq,userId,"app",request);
                }
            }
        }catch (Exception e){
            LOG.error("---app/decoration/v1/doDecoration--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/decoration/v1/doDecoration--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 装修申请详情
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/24 9:24
     * @param
     * @return
     */
    @RequestMapping(value = "v1/decorationInfo",method = RequestMethod.POST)
    public Response decorationInfo(@RequestBody AddDecorationReq addDecorationReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/decoration/v1/decorationInfo--------------start=====" );
        }
        Response response = new Response();
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            DecoApplyRecord decorationInfo = decorationService.findDecorationInfo(addDecorationReq.getRecordId(),AESUtil.decrypt(estateId));
            response.setData(decorationInfo);
        }catch (Exception e){
            LOG.error("---app/decoration/v1/decorationInfo--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/decoration/v1/decorationInfo--------------end"+response);
        }
        return response;
    }

    /**
     * <p>
     * 物业App装修申请列表
     * </p>
     * Author: Cui.xx <br/>
     * Date: 2018/1/23 11:54
     * @return
     */
    @RequestMapping(value = "v1/decorationList",method = RequestMethod.POST)
    public Response decorationList(@RequestBody QueryDecorationReq queryReq,HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("---app/decoration/v1/decorationList--------------start=====" );
        }
        Response response = new Response();
        Paging<DecoApplyRecord> pager = new Paging<DecoApplyRecord>(queryReq.getCurPage(), queryReq.getPageSize());
        try {
            String estateId = request.getHeader("estateId");
            if (StringUtil.isBlank(estateId)){
                estateId = AESUtil.encrypt("1");
            }
            queryReq.setEstateId(AESUtil.decrypt(estateId));
            List<DecoApplyRecord> list = decorationService.findAllDecorationList(queryReq,pager);
            pager.result(list);
            response.setData(pager);
        }catch (Exception e){
            LOG.error("---app/decoration/v1/decorationList--------------error=====",e);
            response.setCode(StatusCode.FAILURE);
        }
        if(LOG.isInfoEnabled()){
            LOG.info("---app/decoration/v1/decorationList--------------end"+response);
        }
        return response;
    }
}

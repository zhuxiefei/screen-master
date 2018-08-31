package com.betel.estatemgmt.business.userapp.house.controller;

import com.betel.estatemgmt.business.userapp.house.code.HouseCode;
import com.betel.estatemgmt.business.userapp.house.model.ShareURL;
import com.betel.estatemgmt.business.userapp.house.service.HouseService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.StatusCode;
import com.betel.estatemgmt.utils.encrypt.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p>
 * 分享处理
 * </p>
 * ClassName: ShareController <br/>
 * Author: zhouye  <br/>
 * Date: 2017/7/3 8:49 <br/>
 * Version: 1.0 <br/>
 */
@Controller
public class ShareController {

    private static final Logger LOG = LoggerFactory.getLogger(ShareController.class);
    @Autowired
    HouseService houseService;

    /**
     * <p>
     * 进入获取文件的页面
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 15:25
     * return response
     */
    @RequestMapping(value = "/share/{path}")
    public ModelAndView enterUpload(@PathVariable("path") String path) throws ParseException {
        if (LOG.isInfoEnabled()) {
            LOG.info("/share------------start---" + path);
        }
        //路径解密
        String shareId = path;
        try {
            path = AESUtil.decrypt(path);
        } catch (Exception e) {
            LOG.error("/share------------error---",e);
            return null;
        }
        ShareURL share;
        //查询分享的zip信息
        try{
            share = houseService.selectShareByShareId(path);
        }catch (Exception e){
            LOG.error("/share------------error---",e);
            return null;
        }
        ModelAndView moedelAndView = new ModelAndView("/house/share.jsp");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //判断链接是否失效
        if (simpleDateFormat.parse(share.getExpireTime()).before(new Date())) {
            moedelAndView.addObject("code", HouseCode.SHAREOUTOFTIME);
            return moedelAndView;
        } else {
            moedelAndView.addObject("fileName", share.getPictureName());
            moedelAndView.addObject("code", StatusCode.SUCCESS);
            moedelAndView.addObject("shareId", shareId);
            return moedelAndView;
        }
    }

    /**
     * <p>
     * 验证密码是否正确
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/21 8:59
     * return ModelAndView
     */
    @RequestMapping(value = "share/downloadPicture")
    public ModelAndView validatePwd(String shareId,String randomPassword) {
        if (LOG.isInfoEnabled()) {
            LOG.info("share/downloadPicture---------------start---" + shareId+"-----"+randomPassword);
        }
        String path = shareId;
        try {
            shareId = AESUtil.decrypt(shareId);
        } catch (Exception e) {
            LOG.error("share/downloadPicture---------------error---",e);
            return null;
        }
        //获取分享的内容
        ShareURL shareURL = houseService.selectShareByShareId(shareId);
        //验证密码是否正确
        if (randomPassword.trim().equals(shareURL.getRandomPassWord())) {
            ModelAndView moedelAndView = new ModelAndView("redirect:./uploadZip");
            moedelAndView.addObject("file", ConfigManager.read(ConfigName.FILE_DIR) + shareURL.getPictureUrl());
            return moedelAndView;
        } else {
            ModelAndView moedelAndView = new ModelAndView("/house/share.jsp");
            moedelAndView.addObject("fileName", shareURL.getPictureName());
            moedelAndView.addObject("code", HouseCode.SHAREPWDWRONG);
            moedelAndView.addObject("shareId", path);
            return moedelAndView;
        }
    }

    /**
     * <p>
     * 下载分享图纸zip的接口
     * </p>
     * Author: zhouye <br/>
     * Date: 2017/6/24 13:13
     * return response
     */
    @RequestMapping("share/uploadZip")
    public void upload(@ModelAttribute("file") String filePath, HttpServletResponse response) throws IOException {
       if (LOG.isInfoEnabled()) {
            LOG.info("app/auth/v1/uploadZip---------------start---" + filePath);
        }
        File file = new File(filePath);
        response.setContentType("application/OCTET-STREAM;charset=UTF-8");
        String filename = new String(file.getName().getBytes("UTF-8"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;fileName="+filename);
        FileInputStream inputStream = null;
        OutputStream out = null;
        try {
             inputStream = new FileInputStream(file);
            //通过response获取ServletOutputStream对象(out)
             out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[1024];
            int len;
            while((len = inputStream.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            out.flush();
        } catch (Exception e) {
            LOG.error("share/uploadZip---------------error---",e);
        }finally {
            if(inputStream !=null){
                inputStream.close();
            }
            if(out !=null){
                out.close();
            }
        }
    }
}

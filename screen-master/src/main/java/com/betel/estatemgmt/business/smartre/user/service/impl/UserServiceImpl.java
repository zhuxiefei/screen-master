package com.betel.estatemgmt.business.smartre.user.service.impl;


import com.betel.estatemgmt.business.smartre.user.model.UserReq;
import com.betel.estatemgmt.business.smartre.user.service.UserService;
import com.betel.estatemgmt.business.web.config.model.ConfigName;
import com.betel.estatemgmt.business.web.housestatus.model.HouseStatusRegisterUser;
import com.betel.estatemgmt.common.ConfigManager;
import com.betel.estatemgmt.common.RedisManager;
import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.common.model.user.User;
import com.betel.estatemgmt.common.model.user.UserAccount;
import com.betel.estatemgmt.utils.HttpClientUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangjian on 2018/1/20.
 */
@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {


    @Override
    public UserAccount findByAccountName(String accountName, HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("accountName", accountName);
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/findUserAccount", jsonParam, request);
        if (null!=response&&null!=response.getData()){
            JSONObject json = JSONObject.fromObject(response.getData());
            UserAccount acount = new UserAccount();
            acount.setAcctName(json.get("acctName").toString());
            acount.setUserId(json.get("userId").toString());
            return acount;
        }
        return null;
    }

    @Override
    public UserAccount findByUserId(String userId, HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", userId);
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/findUserAccount", jsonParam, request);
        if (null!=response&&null!=response.getData()){
            JSONObject json = JSONObject.fromObject(response.getData());
            UserAccount acount = new UserAccount();
            acount.setAcctName(json.get("acctName").toString());
            acount.setUserId(json.get("userId").toString());
            return acount;
        }
        return null;
    }

    @Override
    public User findUser(UserReq userReq, HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", userReq.getUserId());
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/findUser", jsonParam, request);
        if (null != response){
            if (null == response.getData()) {
                return null;
            } else {
                JSONObject json = JSONObject.fromObject(response.getData());
                User user = new User();
                user.setUserId(json.get("userId").toString());
                user.setUserName(json.get("userName").toString());
                return user;
            }
        }
        return null;
    }

    @Override
    public Boolean validateUserName(String userName, HttpServletRequest request) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userName", userName);
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/validateUserName", jsonParam, request);
       if (null != response && null!= response.getData()){
           String flag = response.getData().toString();
           if ("false".equals(flag)) {
               return false;
           } else if ("true".equals(flag)) {
               return true;
           }
       }
        return false;
    }

    @Override
    public HouseStatusRegisterUser register(String memberRealName, String memberPhoneNum, HttpServletRequest request) throws Exception {
        HouseStatusRegisterUser hsreu = new HouseStatusRegisterUser();
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("memberRealName", memberRealName);
        jsonParam.put("memberPhoneNum", memberPhoneNum);
        Response response = HttpClientUtil.post(ConfigManager.read(ConfigName.SMARTRE_PROJECT_URL)+"httpclientSocial/estatemgmt/v1/registerUser", jsonParam,request);
        if (null!=response&&null!=response.getData()){
            JSONObject json = JSONObject.fromObject(response.getData());
            hsreu.setMemberUserId(json.get("memberUserId").toString());
            hsreu.setMemberNickName(json.get("memberRealName").toString());
            hsreu.setMemberPwd(memberPhoneNum.substring(memberPhoneNum.length() - 6));
            hsreu.setMemberRealName(memberRealName);
            hsreu.setPhoneNum(memberPhoneNum);
            return hsreu;
        }
        return null;
    }
}

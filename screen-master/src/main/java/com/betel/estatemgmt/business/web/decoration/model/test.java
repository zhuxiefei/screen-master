package com.betel.estatemgmt.business.web.decoration.model;

import com.betel.estatemgmt.common.Response;
import com.betel.estatemgmt.utils.HttpClientUtil;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2018/4/24/024.
 */
public class test {
    public static void main(String[] args) {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", "1");
        Response response = HttpClientUtil.post("http://localhost:8080/httpclientSocial/user/v1/findReplyNoticeNumber", jsonParam, null);
        if (null != response && null != response.getData()){
            System.out.println(Integer.parseInt(response.getData().toString()));
        }
    }
}

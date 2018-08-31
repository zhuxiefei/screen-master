/*
 * 文 件 名:  CcpMqqtCallback.java
 * 版    权:  Wulian Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  姓名 工号
 * 修改时间:  2015年11月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package smartCouplet.utils.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import smartCouplet.business.model.PushResp;
import smartCouplet.utils.websocket.WebSocket;
import smartCouplet.utils.wlutils.GsonUtil;
import smartCouplet.utils.wlutils.SecretUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author 姓名 工号
 * @version [版本号, 2015年11月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MqttsClientCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connectionLost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttmessage)
            throws Exception {
        if (mqttmessage != null && mqttmessage.toString() != null && !("".equals(mqttmessage))) {
            System.out.println("this is test." + topic + " " + mqttmessage.toString());
            String returnMeg = mqttmessage.toString();
            JSONObject j = JSONObject.parseObject(returnMeg);
            String timestamp = j.get("timestamp") == null ? "" : j.get("timestamp").toString();
            String nonce = j.get("nonce") == null ? "" : j.get("nonce").toString();
            String signature = j.get("signature") == null ? "" : j.get("signature").toString();
            String msgContent = j.get("msgContent") == null ? "" : j.get("msgContent").toString();
            String key = "esy7flqzfs9qsmc6pvjvl0"; //后期写成常量
            String rightMsg = SecretUtil.decrypt(key, timestamp, nonce, signature, msgContent);

            JSONObject json = JSON.parseObject(rightMsg);
            String cmd = json.get("cmd") == null ? "" : json.get("cmd").toString();
            String messageCode = json.get("messageCode") == null ? "" : json.get("messageCode").toString();
            String gwId = json.get("gwID") == null ? "" : json.get("gwID").toString();
            String gwPassword = json.get("gwPwd") == null ? "" : json.get("gwPwd").toString();
            String data = json.get("data") == null ? "" : json.get("data").toString();
            String devId = json.get("devID") == null ? "" : json.get("devID").toString();
            String devType = "";
            String level = "";

            if (devId.equals("D262C403004B1200")){
                if (cmd.equals("500")&&messageCode.equals("0101041")){
                    devType = "001";
                    level = "1";
                    System.out.println("设备被破坏");
                }else if (cmd.equals("500")){
                    if (messageCode.equals("0101031") ){
                        devType = "001";
                        level = "2";
                        System.out.println("检测到电量低，请及时更换电池");
                    }
                    if (messageCode.equals("0101021") ){
                        devType = "001";
                        level = "2";
                        System.out.println("检测到被删除");
                    }
                    if (messageCode.equals("0102051") ){
                        devType = "001";
                        level = "2";
                        System.out.println("检测到被打开");
                    }
                    if (messageCode.equals("0101012") ){
                        devType = "001";
                        level = "2";
                        System.out.println("已设防");
                    }
                    if (messageCode.equals("0101022") ){
                        devType = "001";
                        level = "2";
                        System.out.println("已撤防");
                    }
                    if (messageCode.equals("0101012") ){
                        devType = "001";
                        level = "2";
                        System.out.println("设备上线");
                    }
                    if (messageCode.equals("0101022") ){
                        devType = "001";
                        level = "2";
                        System.out.println("设备下线");
                    }
                }
            }

            //下面用于演示 后期要更改
/*            String devType = "";
            String level = "";
            JSONObject json = JSONObject.parseObject(rightMsg);
            String devID = json.get("devID").toString();
            if (devID.equals("D262C403004B1200")||devID.equals("0450C403004B1200")) { //门窗
                devType = "001";
                level = "1";
            } else if (devID.equals("14F5CB0A004B1200")) {//燃气
                devType = "002";
                level = "1";
            } else if (devID.equals("0ABC6B0F004B1200")) {//水浸
                devType = "003";
                level = "2";
            } else if (devID.equals("323C4E09004B1200")) {//烟雾
                devType = "004";
                level = "1";
            } else if (devID.equals("85BDA313004B1200")) {//门禁
                //门被损坏的告警码
                String messageCode=json.get("messageCode")==null?"":json.get("messageCode").toString();
                if(messageCode.equals("0103031")){
                    devType = "005";
                    level = "1";
                }
                JSONArray endpoints = (JSONArray) json.get("endpoints");
                JSONObject endpoint = (JSONObject) endpoints.get(0);
                JSONArray clusters = (JSONArray) endpoint.get("clusters");
                JSONObject cluster = (JSONObject) clusters.get(0);
                JSONArray attributes = (JSONArray) cluster.get("attributes");
                JSONObject attribute = (JSONObject) attributes.get(0);
                String attributeValue = attribute.get("attributeValue") == null ? "为空" : attribute.get("attributeValue").toString();
                //   System.out.println(attributeValue);
                //如果是特定账号开门，则发出警报
                if (attributeValue.equals("1006")) {
                    devType = "006";
                    level = "1";
                }
            }else if (devID.equals("")){

            }*/
            if (devType != null && !"".equals(devType)) {
                List<PushResp> list = new ArrayList<>();
                PushResp pushResp = new PushResp();
                pushResp.setDevType(devType);
                pushResp.setLevel(level);
                list.add(pushResp);
                System.out.println("********************************************************************");
                System.out.println(pushResp);
                WebSocket web = new WebSocket();
                web.sendMessageToUser(1L, GsonUtil.object2Gson(list));     //演示用户id为1
                System.out.println("webSocket  end successful");
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete");
    }
}

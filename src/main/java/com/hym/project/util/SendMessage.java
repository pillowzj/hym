package com.hym.project.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.redis.RedisCache;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SendMessage {
    private static HttpClient httpClient = HttpClients.createDefault();

    private static final Logger log = LoggerFactory.getLogger(SendMessage.class);

    @Autowired
    private RedisCache redisCache;
    /**
     * 发送验证码
     *
     * @param MobilePhone 手机号
     * @return 是否发送成功
     */
    public  Boolean msgSend(String MobilePhone) {

//        if(true){
//            String vCode ="6666";
//            redisCache.setCacheObject("Constant.SMS_PREFIX" + MobilePhone+vCode, "Constant.SMS_PREFIX" + MobilePhone+vCode, 2, TimeUnit.MINUTES);
//        return true;
//        }
        String url = "https://api.mysubmail.com/message/xsend.json";
        //String url = "https://api.mysubmail.com/message/xsend";

        String vCode =String.valueOf((int)((Math.random()*9+1)*1000));

        if (MobilePhone.length() != 11) return false;

            try {
                List<NameValuePair> list = new ArrayList<>();
                //String timestamp = String.valueOf(System.currentTimeMillis());
                list.add(new BasicNameValuePair("appid", "46963"));
                //list.add(new BasicNameValuePair("timestamp", timestamp));
                list.add(new BasicNameValuePair("signature", "2b2887da2fbb039fb1b51df8bd820ef9"));
                list.add(new BasicNameValuePair("to", MobilePhone));
                list.add(new BasicNameValuePair("project", "2w8FM"));

                JSONObject vars = new JSONObject();
                vars.put("code",vCode);
                list.add(new BasicNameValuePair("vars", vars.toString()));

                HttpPost hp = new HttpPost(url);
                hp.addHeader("charset", "UTF-8");
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                hp.setEntity(entity);
                HttpResponse response = httpClient.execute(hp);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, "UTF-8");
                        JSONObject object = JSON.parseObject(result);
                        String status = object.getString("status");
                        if (status.equals("success")) {
                            redisCache.setCacheObject("Constant.SMS_PREFIX" + MobilePhone+vCode, "Constant.SMS_PREFIX" + MobilePhone+vCode, 60*24*100, TimeUnit.MINUTES);
                        } else {
                            return false;
                        }
                    }
                }
            } catch (Exception ex) {
                return false;
            }

        return true;

    }
    public static String byteArrayToHex(byte[] byteArray){
        //首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        //new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符）
        char[] resultCharArray = new char[byteArray.length*2];
        //遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for(byte b : byteArray){
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }

        //字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}

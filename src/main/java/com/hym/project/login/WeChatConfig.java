package com.hym.project.login;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.hym.project.login.domain.WXOpenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hym.project.util.Http;
@Component
public class WeChatConfig {

    @Autowired
    private WeChatProperty weChatProperty;

    /**
     * 获取用户的openid和access_token
     * @param code
     * @return
     */
    public WXOpenInfo getOpenIdAndToken(String code){
        //获取APPID和APPSecret
        String appId=weChatProperty.getAPPID();
        String appSecret=weChatProperty.getAPPSecret();
        //获取openid和access_token的连接
        String getOpenIdUrl=weChatProperty.getOpenIdAndTokenUrl();
        //获取返回的code
        //System.out.println("requestUrl"+getOpenIdUrl);
        String requestUrl = getOpenIdUrl.replace("CODE", code).replace("APPID",appId).replace("APPSECRET",appSecret);
        //System.out.println("requestUrl"+requestUrl);
        String result= Http.get(requestUrl);
        JSONObject object=JSON.parseObject(result);
        return new WXOpenInfo(object.getString("openid"),object.getString("unionid"),object.getString("session_key"));
    }
}

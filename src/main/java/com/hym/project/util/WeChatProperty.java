package com.hym.project.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:wechat.properties")
public class WeChatProperty {

    @Value("${appId}")
    private String APPID;
    @Value("${appSecret}")
    private String APPSecret;
    @Value("${openIdAndTokenUrl}")
    private String openIdAndTokenUrl;
    @Value("${userInfoUrl}")
    private String userInfoUrl;

    public String getAPPID() {
        return APPID;
    }

    public String getAPPSecret() {
        return APPSecret;
    }

    public String getOpenIdAndTokenUrl() {
        return openIdAndTokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}

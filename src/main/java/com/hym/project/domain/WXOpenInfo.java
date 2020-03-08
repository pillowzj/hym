package com.hym.project.domain;


/**
 * @Auther: koulijun
 * @Date: 2018/11/29 11:12
 * @Description:
 */
public class WXOpenInfo {

    private String openid;
    private String unionid;
    private String sessionKey;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public WXOpenInfo(String openid, String unionid, String sessionKey) {
        this.openid = openid;
        this.unionid = unionid;
        this.sessionKey = sessionKey;
    }
}


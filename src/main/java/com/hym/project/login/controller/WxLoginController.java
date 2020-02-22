package com.hym.project.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.utils.StringUtils;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.login.WXLoginService;
import com.hym.project.login.domain.WXOpenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hym/login")
public class WxLoginController {


    @Autowired
    private WXLoginService wxLoginService;


    @PostMapping
    public AjaxResult login(String data){

        if(StringUtils.isEmpty(data)){
           return AjaxResult.error();
        }
        JSONObject reqbody = JSON.parseObject(data);
        String openid = reqbody.getString("openid");
        String name = reqbody.getString("name");
        String face = reqbody.getString("face");
        if(StringUtils.isEmpty(openid)){
            return AjaxResult.error();
        }


        Map res =  wxLoginService.login(openid,name,face);
        System.out.println("login -->JSON.toJSON-->"+JSON.toJSON(res));
        return  AjaxResult.success(res);
    }

    @PostMapping("/getOpenid")
    public AjaxResult getOpenid(String data){

    JSONObject reqbody = JSON.parseObject(data);
    String code = reqbody.getString("Code");
    WXOpenInfo res =  wxLoginService.getWXInfo(code);

    System.out.println("wxlogin-->WXOpenInfo --->"+JSON.toJSON(res));
    return  AjaxResult.success(res);
    }

    @PostMapping("/checktoken")
    public AjaxResult checktoken(String data){

        JSONObject reqbody = JSON.parseObject(data);
        String token = reqbody.getString("token");
        boolean isTrue =  wxLoginService.checkToken(token);
        if(isTrue)return  AjaxResult.success();
        return  AjaxResult.error();
    }

    @PostMapping("/register")
    public AjaxResult register(String data){

        JSONObject reqbody = JSON.parseObject(data);
        String iv = reqbody.getString("iv");
        String code = reqbody.getString("code");
        String rawData = reqbody.getString("rawData");
        String signature = reqbody.getString("signature");
        String encryptedData = reqbody.getString("encryptedData");
        System.out.println("iv--->"+iv);
        System.out.println("code--->"+code);
        System.out.println("rawData--->"+rawData);
        System.out.println("signature--->"+signature);
        System.out.println("encryptedData--->"+encryptedData);

//        boolean isTrue =  wxLoginService.checkToken(token);
//        if(isTrue)return  AjaxResult.success();
        return  AjaxResult.success();
    }
}

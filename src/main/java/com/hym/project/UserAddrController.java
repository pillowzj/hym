package com.hym.project;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户地址Controller
 * 
 * @author wukk
 * @date 2019-11-22
 */
@RestController
@RequestMapping("api/common")
public class UserAddrController{
    @PostMapping("/getNews")
    public String wxLogin(String data){
        String str = JSON.toJSONString(new ResponseWraper("200", "ok","123456"));
        System.out.println("message --->"+str);
        return  str;
    }
}

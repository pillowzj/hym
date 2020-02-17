package com.hym.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.Account;
import com.hym.project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hym")
public class AccountController {


    @Autowired
    private AccountService accountService;
    /**
     * 获取account info
     * @return
     */
    @PostMapping("/gwtAssets")
    public AjaxResult getAccount(String data){
        JSONObject reqbody = JSON.parseObject(data);
        String id = reqbody.getString("uid");
       Account account = accountService.selectByPrimaryKey(id);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok",account));
        return AjaxResult.success(account);
    }
}

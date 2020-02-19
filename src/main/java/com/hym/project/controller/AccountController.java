package com.hym.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.Account;
import com.hym.project.domain.User;
import com.hym.project.service.AccountService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hym/assets")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    /**
     * 获取account info
     *
     * @return
     */
    @PostMapping("/getAssets")
    public AjaxResult getAccount(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String id = reqbody.getString("uid");
        Account account = accountService.selectByPrimaryKey(id);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok", account));
        return AjaxResult.success(account);
    }

    @PostMapping("/submitMyOrder")
    public AjaxResult submitMyOrder(String data) {
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String isAutho = reqbody.getString("isAutho");
        String status = reqbody.getString("status");
        String hym = reqbody.getString("hym");

        User myUser = userService.selectByPrimaryKey(uid);
        Account myAccount = accountService.getUserHYMByPrimaryKey(myUser.getId());


        Map map = new HashMap();
        map.put("is_autho", isAutho);
        map.put("status", status);
        String id = userService.getUserHMY(map);
        Account account = accountService.getUserHYMByPrimaryKey(id);
        if (isAutho.equals("8") && status.equals("8")) {
            // 卖出
            myAccount.setToken(myAccount.getToken().subtract(new BigDecimal(hym)));
            account.setToken(account.getToken().add(new BigDecimal(hym)));
        } else if (isAutho.equals("9") && status.equals("9")) {
            // 买入
            myAccount.setToken(myAccount.getToken().add(new BigDecimal(hym)));
            account.setToken(account.getToken().subtract(new BigDecimal(hym)));
        }

        accountService.updateByPrimaryKeySelective(myAccount);
        accountService.updateByPrimaryKeySelective(account);

        String str = JSON.toJSONString(new ResponseWraper("200", "ok", account));
        return AjaxResult.success(account);
    }
}

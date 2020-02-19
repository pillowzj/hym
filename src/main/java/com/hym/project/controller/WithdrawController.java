package com.hym.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hym.common.utils.IdUtils;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.Account;
import com.hym.project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("api/hym")
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/createWithdrawal")
    public AjaxResult createWithdrawal(String data) {

        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String cellPhone = reqbody.getString("cellPhone");
        String verifyCode = reqbody.getString("verifyCode");
        BigDecimal withDrawAmount = reqbody.getBigDecimal("withDrawAmount");
        String tradePassword = reqbody.getString("tradePassword");

        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            return AjaxResult.error("403", " verify is error");
        }
        //2 密码验证
        //3 创建提现订单

        Account account = accountService.selectByPrimaryKey(uid);
        account.setCny(account.getCny().subtract(withDrawAmount));
        accountService.updateByPrimaryKeySelective(account);

        Withdraw withdraw = new Withdraw();
        withdraw.setId(IdUtils.fastUUID());
        withdraw.setUid(uid);
        withdraw.setAmount(withDrawAmount);
        withdraw.setChannel("微信");
        withdraw.setStatus(0);
        withdraw.setInsertDate(new Date());
        withdrawService.insert(withdraw);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));
        return AjaxResult.success();

    }

    @PostMapping("/cashOutRecord")
    public AjaxResult cashOutRecord(String data) {

        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        int pageSize = reqbody.getInteger("pageSize");
        int pageNum = reqbody.getInteger("pageNum");
        PageInfo<Withdraw> page = withdrawService.selectWithdrawPage(uid, pageNum, pageSize);
        String str = JSON.toJSONString(new ResponseWraper("200", "ok", page));
        return AjaxResult.success(page);
    }

}

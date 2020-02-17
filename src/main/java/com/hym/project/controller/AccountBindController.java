package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.utils.IdUtils;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.AccountBind;
import com.hym.project.domain.User;
import com.hym.project.service.AccountBindService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/hym")
public class AccountBindController {
    @Autowired
    private AccountBindService accountBindService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 绑定收款账号
     * @param data
     * @return
     */
    @RequestMapping("/accountBind")
    public AjaxResult accountBind(String data){
         JSONObject reqbody = JSON.parseObject(data);
         String uid = reqbody.getString("uid");
         String reType = reqbody.getString("reType");
         String accountNo = reqbody.getString("accountNo");
         String cellPhone = reqbody.getString("cellPhone");
         String verifyCode = reqbody.getString("verifyCode");
         String newPassword = reqbody.getString("newPassword");
        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX"+cellPhone+verifyCode);
        if(!vc.equals("Constant.SMS_PREFIX"+cellPhone+verifyCode)){
            return AjaxResult.error("403", " verify is error");
        }

         User user = new User();
        user.setId(uid);
        user.setMobile(cellPhone);
        user.setTradePassword(newPassword);
        user.setStatus("1");
        userService.updateByPrimaryKeySelective(user);

        AccountBind accountBind = new AccountBind();
        accountBind.setId(IdUtils.fastUUID());
        accountBind.setUid(uid);
        accountBind.setAccType(reType);
        accountBind.setAccName(accountNo);
        accountBind.setStatus(0);
        accountBind.setInsertDate(new Date());
        accountBindService.insert(accountBind);

        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));
        return AjaxResult.success();
    }
}
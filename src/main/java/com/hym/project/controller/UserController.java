package com.hym.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.ResponseWraper;
import com.hym.project.domain.User;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hym")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/setTradePassword")
    public AjaxResult setTradePassword(String data){
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String verifyCode = reqbody.getString("verifyCode");
        String newTradePassword = reqbody.getString("newTradePassword");
        String cellPhone = reqbody.getString("cellPhone");

        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX"+cellPhone+verifyCode);
        if(!vc.equals("Constant.SMS_PREFIX"+cellPhone+verifyCode)){
            return AjaxResult.error("403", " verify is error");
        }

        User user = userService.selectByPrimaryKey(uid);
        user.setTradePassword(newTradePassword);
        userService.updateByPrimaryKeySelective(user);

        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));
        return AjaxResult.success();

    }


    /**
     * 提现前身份信息采集
     * @param data
     * @return
     */
    @RequestMapping("/idAuthe")
    public AjaxResult idAuthe(String data){
        JSONObject reqbody = JSON.parseObject(data);
        String uid = reqbody.getString("uid");
        String name = reqbody.getString("name");
        String idNumber = reqbody.getString("idNumber");
        String gender = reqbody.getString("gender");
        String nation = reqbody.getString("nation");
        String address = reqbody.getString("address");
        String birthDay = reqbody.getString("birthDay");

        String idAuthoIssue = reqbody.getString("idAuthoIssue");
        String idExpirDate = reqbody.getString("idExpirDate");
        String idPositive = reqbody.getString("idPositive");
        String idOpposite = reqbody.getString("idOpposite");

        User user = userService.selectByPrimaryKey(uid);
        user.setAddress(address);
        user.setBirthday(birthDay);
        user.setGender(gender);
        user.setIdNumber(idNumber);
        user.setIdAuthoIssue(idAuthoIssue);
        user.setNation(nation);
        user.setName(name);
        user.setIdExpirDate(idExpirDate);
        user.setIdPositive(idPositive);
        user.setIdOpposite(idOpposite);
        user.setStatus("2");
        userService.updateByPrimaryKeySelective(user);

        String str = JSON.toJSONString(new ResponseWraper("200", "ok"));
        return AjaxResult.success();

    }
}

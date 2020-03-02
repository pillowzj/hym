package com.hym.project.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.constant.HttpStatus;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.User;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hym/login")
public class WxLoginController {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserService userService;

    @PostMapping("/wxLogin")
    public AjaxResult wxLogin() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        if (StringUtils.isEmpty(reqbody)) {
            return AjaxResult.error();
        }
        String uid = reqbody.getString("uid");
        String name = reqbody.getString("name");
        String face = reqbody.getString("face");
        String cellPhone = reqbody.getString("cellPhone");
        String verifyCode = reqbody.getString("verifyCode");

        //1 验证码验证
        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            return AjaxResult.error(HttpStatus.BAD_REPWD, " verify is error");
        }
        User user = userService.selectByPrimaryKey(uid);
        if(StringUtils.isEmpty(user.getMobile())){
            user = new User(uid,name,face,cellPhone);
            userService.updateByPrimaryKeySelective(user);
        }
        return AjaxResult.success(user);
    }
}

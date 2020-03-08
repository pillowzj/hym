package com.hym.project.login.controller;

import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hym/login")
public class WxLoginController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/quit")
    public AjaxResult quit() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        String token = requestData.getToken();
        LoginUser user = tokenService.psrseUser(token);
        tokenService.delLoginUser(user.getToken());
        return AjaxResult.success();
    }
}

package com.hym.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.domain.ThreadCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.User;
import com.hym.project.service.InviteCodeService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 邀请码
 *
 * @Author lijun kou
 */
@RestController
@RequestMapping("/api/hym/code")
public class InviteCodeController {


    @Autowired
    private InviteCodeService inviteCodeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/getInvitedCode")
    private AjaxResult getInvitedCode(){
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String invitedCode = reqbody.getString("invitedCode");
        String token = requestData.getToken();
        invitedCode = userService.getInviteCode(invitedCode);
        if(StringUtils.isEmpty(invitedCode)){
            return AjaxResult.error("没有找到被邀请码,请核实后重新输入");
        }
        String myInviteCode = inviteCodeService.getByInviteCode().getInviteCode().toString();
        LoginUser loginUser = tokenService.psrseUser(token);
        User user = userService.selectByPrimaryKey(loginUser.getUser().getId());
        user.setInvitedCode(invitedCode);
        user.setMyInviteCode(myInviteCode);
        userService.updateByPrimaryKeySelective(user);
        Map map = new HashMap<>();
        map.put("invitedCode",invitedCode);
        map.put("myInviteCode",myInviteCode);
        return AjaxResult.success(map);
    }
}

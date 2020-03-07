package com.hym.project.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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


//    @PostMapping("/wx")
//    public AjaxResult wxLogin() {
//        RequestData requestData = ThreadCache.getPostRequestParams();
//        JSONObject reqbody = JSON.parseObject(requestData.getData());
//        if (StringUtils.isEmpty(reqbody)) {
//            return AjaxResult.error();
//        }
//        String token = reqbody.getString("token");
//        LoginUser loginUser = tokenService.psrseUser(token);
//
//        String name = reqbody.getString("name");
//        String face = reqbody.getString("face");
//        String cellPhone = reqbody.getString("cellPhone");
//        String verifyCode = reqbody.getString("verifyCode");
//        //1 验证码验证
//        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
//        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
//            return AjaxResult.error(HttpStatus.BAD_REPWD, " verify is error");
//        }
//        User user = null;
//        if (!StringUtils.isEmpty(cellPhone)) {
//            user = loginUserService.loginByCellPhone(cellPhone);
//        }
//        if (StringUtils.isNull(user)) {
//            user = loginUserService.getOpenid(loginUser.getUser().getOpenid());
//        }
//
//        //执行注册逻辑
//        if (StringUtils.isNull(user)) {
//            user = new User();
//            user.setId(IdUtils.fastSimpleUUID());
////            user.setOpenid(loginUser.getUser().getOpenid());
////            user.setSessionkey(loginUser.getUser().getSessionkey());
//            user.setWxNickname(name);
//            user.setWxAvatarUrl(face);
//
////            user.setInvitedCode(invitedCode);//被邀请码
////            user.setMyInviteCode(inviteCodeService.getByInviteCode().getInviteCode().toString());// 我的邀请码
////            user.setIsAutho(WorkflowConstants.ZERO);
////            user.setStatus(WorkflowConstants.ZERO);
////            user.setInsertTime(new Date());
//
//
//            userService.updateByPrimaryKeySelective(user);
//
//        }else{
//            user.setOpenid(loginUser.getUser().getOpenid());
//            user.setSessionkey(loginUser.getUser().getSessionkey());
//            user.setWxNickname(name);
//            user.setWxAvatarUrl(face);
//
//            userService.updateByPrimaryKeySelective(user);
//        }
//
//        return AjaxResult.success(user);
//    }



    @PostMapping("/quit")
    public AjaxResult quit() {
        RequestData requestData = ThreadCache.getPostRequestParams();
        JSONObject reqbody = JSON.parseObject(requestData.getData());
        String token = reqbody.getString("token");
        LoginUser user = tokenService.psrseUser(token);
        tokenService.delLoginUser(user.getToken());
        return AjaxResult.success();
    }

}

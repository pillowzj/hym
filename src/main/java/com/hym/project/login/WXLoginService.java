package com.hym.project.login;

import com.hym.common.constant.WorkflowConstans;
import com.hym.common.utils.IdUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.project.domain.Asset;
import com.hym.project.domain.InviteCode;
import com.hym.project.domain.User;
import com.hym.project.login.domain.WXOpenInfo;
import com.hym.project.login.service.LoginUserService;
import com.hym.project.service.AssetService;
import com.hym.project.service.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录校验方法
 * 
 * @author hym
 */
@Component
public class WXLoginService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private AssetService assetService;

    @Autowired
    private InviteCodeService inviteCodeService;

    public Map loginByCellPhone(String cellPhone,String verifyCode) {

        User user = loginUserService.loginByCellPhone(cellPhone);
        if(user == null){
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setStatus(WorkflowConstans.ZERO);
            user.setMobile(cellPhone);
            user.setInsertTime(new Date());
            loginUserService.insert(user);

            createAsset(user);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        // 生成token
        String token = tokenService.createToken(loginUser);
        Map map = new HashMap();


        map.put("token",token);
        map.put("uid",user.getId());
        map.put("cellPhone",user.getMobile());
        map.put("invitationCode",user.getMyInviteCode());
        map.put("status",user.getStatus());
        map.put("autho",user.getIsAutho());
        return map;
    }



    public WXOpenInfo getWXInfo(String code) {
        WXOpenInfo wxOpenInfo = weChatConfig.getOpenIdAndToken(code);

        return wxOpenInfo;
    }
    public Map wxLogin(String openid,String name,String face,String cellPhone,String verifyCode) {

        User user = loginUserService.getOpenid(openid);
        if(StringUtils.isNull(user)){
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setOpenid(openid);
            user.setWxNickname(name);
            user.setWxAvatarUrl(face);
            user.setMobile(cellPhone);
            user.setIsAutho(WorkflowConstans.ZERO);
            user.setStatus(WorkflowConstans.ZERO);
            user.setInsertTime(new Date());
            loginUserService.insert(user);

            createAsset(user);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        // 生成token
        String token =tokenService.createToken(loginUser);
        Map map = new HashMap();
        map.put("face",face);
        map.put("name", name);
        map.put("openid",openid);
        map.put("token",token);
        map.put("uid",user.getId());
        map.put("cellPhone",user.getMobile());
        map.put("invitationCode",user.getMyInviteCode());
        map.put("status",user.getStatus());
        map.put("autho",user.getIsAutho());
        return map;
    }
    private void createAsset(User user) {
        Asset asset = new Asset();
        asset.setId(user.getId());
        asset.setUid(user.getId());
        String tokenInit = new BigDecimal(WorkflowConstans.ZERO).setScale(WorkflowConstans.FOUR).toString();
        asset.setFrozenToken(tokenInit);
        asset.setToken(tokenInit);
        asset.setInsertDate(new Date());
        assetService.insert(asset);
    }

    /**
     * 创建邀请码
     * @param num
     */
    private void createInviteCode(int num) {
        for (int i=0;i<num;i++){
            InviteCode inviteCode  = new InviteCode();
            inviteCode.setInviteCode(IdUtils.inviteCode());
            inviteCode.setStatus(0);
            inviteCode.setInsertDate(new Date());
            inviteCodeService.add(inviteCode);
        }
    }
}

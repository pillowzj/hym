package com.hym.project.login;

import com.hym.common.utils.IdUtils;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.project.domain.Asset;
import com.hym.project.domain.User;
import com.hym.project.login.domain.WXOpenInfo;
import com.hym.project.login.service.LoginUserService;
import com.hym.project.service.AssetService;
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
    private RedisCache redisCache;
    /**
     * 登录验证
     * 
     * @param code 微信code

     * @return 结果
     */


    public WXOpenInfo getWXInfo(String code) {
        WXOpenInfo wxOpenInfo = weChatConfig.getOpenIdAndToken(code);

        return wxOpenInfo;
    }
    public Map login(String openid,String name,String face) {

        User user = loginUserService.getOpenid(openid);

        if(user == null){
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setOpenid(openid);
            user.setWxNickname(name);
            user.setWxAvatarUrl(face);
            user.setStatus("0");
            user.setInsertTime(new Date());
            loginUserService.insert(user);

            Asset asset = new Asset();
            asset.setId(user.getId());
            asset.setUid(user.getId());
            asset.setFrozenToken(new BigDecimal(0));
            asset.setToken(new BigDecimal(0));
            asset.setInsertDate(new Date());
            assetService.insert(asset);
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
    public boolean checkToken( String token) {
        // 生成token
            LoginUser loginUser = tokenService.psrseUser(token);
            if(loginUser == null) return false;
        return true;
    }
}

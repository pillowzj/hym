package com.hym.framework.security.opeinid;

import com.hym.common.constant.WorkflowConstans;
import com.hym.common.utils.IdUtils;
import com.hym.framework.security.LoginUser;
import com.hym.project.domain.Asset;
import com.hym.project.domain.User;
import com.hym.project.login.service.LoginUserService;
import com.hym.project.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 真正执行认证逻辑的manager, {@link WxAuthenticationFilter}会将认证委托给{@link WxAuthenticationManager}来做
 *
 * @author lijun kou
 * @Date 03012020
 */
public class WxAuthenticationManager implements AuthenticationManager {
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private AssetService assetService;

    @Override
    public WxAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("----------WxAuthenticationManager------------");
        WxAuthenticationToken wxAppletAuthenticationToken = null;
        if (authentication instanceof WxAuthenticationToken) {
            wxAppletAuthenticationToken = (WxAuthenticationToken) authentication;
        }
        String openid = ((LoginUser) wxAppletAuthenticationToken.getPrincipal()).getUser().getOpenid();
        User user = loginUserService.getOpenid(openid);
        LoginUser loginUser = new LoginUser();

        //执行注册逻辑
        if (user == null) {
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setOpenid(openid);
            user.setIsAutho(WorkflowConstans.ZERO);
            user.setStatus(WorkflowConstans.ZERO);
            user.setInsertTime(new Date());
            if (loginUserService.insert(user) > 0) {
                this.createAsset(user);
            }
        }
        loginUser.setUser(user);
        return new WxAuthenticationToken(loginUser, null, null);
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
}

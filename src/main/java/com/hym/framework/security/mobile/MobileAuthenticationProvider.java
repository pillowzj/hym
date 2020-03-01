package com.hym.framework.security.mobile;

import com.hym.common.constant.WorkflowConstans;
import com.hym.common.utils.IdUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.security.LoginUser;
import com.hym.project.domain.Asset;
import com.hym.project.domain.User;
import com.hym.project.login.service.LoginUserService;
import com.hym.project.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.math.BigDecimal;
import java.util.Date;

public class MobileAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private AssetService assetService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("----------MobileAuthenticationProvider------------");

        MobileAuthenticationToken wxAppletAuthenticationToken = null;

        if (authentication instanceof MobileAuthenticationToken) {
            wxAppletAuthenticationToken = (MobileAuthenticationToken) authentication;
        }
        String mobile = ((LoginUser) wxAppletAuthenticationToken.getPrincipal()).getUser().getMobile();
        User user = loginUserService.loginByCellPhone(mobile);
        LoginUser loginUser = new LoginUser();

        //执行注册逻辑
        if (StringUtils.isNull(user)) {
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setMobile(mobile);
            user.setIsAutho(WorkflowConstans.ZERO);
            user.setStatus(WorkflowConstans.ZERO);
            user.setInsertTime(new Date());
            if (loginUserService.insert(user) > 0) {
                this.createAsset(user);
            }
        }
        loginUser.setUser(user);
        return new MobileAuthenticationToken(loginUser, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (MobileAuthenticationToken.class.isAssignableFrom(authentication));
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

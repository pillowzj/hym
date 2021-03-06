package com.hym.framework.security.mobile;


import com.hym.common.constant.Constants;
import com.hym.common.constant.WorkflowConstants;
import com.hym.common.utils.IdUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.security.LoginUser;
import com.hym.project.domain.Asset;
import com.hym.project.domain.User;
import com.hym.project.service.LoginUserService;
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
//        System.out.println("----------MobileAuthenticationProvider------------");
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
            user.setIsAutho(WorkflowConstants.ZERO);
            user.setStatus(WorkflowConstants.ZERO);
            user.setInsertTime(new Date());

            Asset asset = new Asset();
            asset.setId(user.getId());
            asset.setUid(user.getId());
            String tokenInit = new BigDecimal(Constants.ZERO).setScale(Constants.DECIMAL_POINT).toString();
            asset.setFrozenToken(tokenInit);
            asset.setToken(tokenInit);
            asset.setInsertDate(new Date());
            loginUserService.insert(user);
            assetService.insert(asset);
        }
        loginUser.setUser(user);
        return new MobileAuthenticationToken(loginUser, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (MobileAuthenticationToken.class.equals(authentication));
    }
}

package com.hym.framework.security.opeinid;

import com.hym.common.constant.Constants;
import com.hym.common.constant.WorkflowConstants;
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

public class WxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private AssetService assetService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        System.out.println("----------WxAuthenticationProvider------------");
        WxAuthenticationToken wxAppletAuthenticationToken = null;
        if (authentication instanceof WxAuthenticationToken) {
            wxAppletAuthenticationToken = (WxAuthenticationToken) authentication;
        }
        LoginUser loginUser = (LoginUser) wxAppletAuthenticationToken.getPrincipal();
        User user = null;
        if(!StringUtils.isEmpty(loginUser.getUser().getMobile())){
            user = loginUserService.loginByCellPhone(loginUser.getUser().getMobile());
        }else if(!StringUtils.isEmpty(loginUser.getUser().getOpenid())){
            user = loginUserService.getOpenid(loginUser.getUser().getOpenid());
        }

        //执行注册逻辑
        if (StringUtils.isNull(user)) {
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setOpenid(loginUser.getUser().getOpenid());
            user.setSessionkey(loginUser.getUser().getSessionkey());
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

            int i =loginUserService.insert(user);
                   assetService.insert(asset);
             System.out.println("----------i-------->"+i);

        }
        loginUser.setUser(user);
        return new WxAuthenticationToken(loginUser, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WxAuthenticationToken.class.equals(authentication));
    }


}

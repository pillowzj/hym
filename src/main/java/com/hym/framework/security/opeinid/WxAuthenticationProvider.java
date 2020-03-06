package com.hym.framework.security.opeinid;

import com.hym.framework.security.LoginUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class WxAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxAuthenticationToken wxAppletAuthenticationToken = null;
        if (authentication instanceof WxAuthenticationToken) {
            wxAppletAuthenticationToken = (WxAuthenticationToken) authentication;
        }
        LoginUser loginUser = (LoginUser) wxAppletAuthenticationToken.getPrincipal();
        return new WxAuthenticationToken(loginUser, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WxAuthenticationToken.class.equals(authentication));
    }


}

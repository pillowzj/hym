package com.hym.framework.security.opeinid;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用于微信登录的认证
 * @author lijun kou
 * @Date 03012020
 */

public class WxAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;
    public WxAuthenticationToken(Object principal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(anAuthorities);
        this.principal = principal;
        this.credentials = aCredentials;
        super.setAuthenticated(true);
        System.out.println("----------WxAuthenticationToken------------");
    }
    public Object getCredentials() {
        return this.credentials;
    }
    public Object getPrincipal() {
        return this.principal;
    }
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}

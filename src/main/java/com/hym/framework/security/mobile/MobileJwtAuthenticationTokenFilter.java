package com.hym.framework.security.mobile;

import com.hym.common.utils.SecurityUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来校验请求头中的jwt是否有效，以此为依据来认证用户是否登录
 * @author lijun kou
 * @Date 03012020
 */
public class MobileJwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("-----MobileJwtAuthenticationTokenFilter-----doFilterInternal------------");
        LoginUser loginUser = tokenService.getLoginUser(request);//根据token 查询redis 是否存在这个用户信息

        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(loginUser);
            MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

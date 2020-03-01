package com.hym.framework.security.opeinid.handler;

import com.hym.common.enums.ExceptionEnum;
import com.hym.common.exception.BaseException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录时的处理端点, 一般是抛出AuthenticationException时会进入
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        throw new BaseException(ExceptionEnum.NOT_LOGIN.toString());
    }
}

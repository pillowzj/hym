package com.hym.framework.security.opeinid.handler;

import com.hym.common.enums.ExceptionEnum;
import com.hym.common.exception.BaseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问接口无权限时的处理端点, 一般是抛出AccessDeniedException异常时会进入
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 删除框架抛出的异常，改为自定义异常
        httpServletRequest.removeAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
        throw new BaseException(ExceptionEnum.ACCESS_DENIED.toString());
    }
}

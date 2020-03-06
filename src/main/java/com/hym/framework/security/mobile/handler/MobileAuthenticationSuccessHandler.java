package com.hym.framework.security.mobile.handler;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户认证通过的处理handler
 * @author lijun kou
 * @Date 03012020
 */
public class MobileAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 使用jwt管理，所以封装用户信息生成jwt响应给前端
        String json = JSONObject.toJSONString(authentication.getPrincipal(), SerializerFeature.WriteMapNullValue);
        LoginUser loginUser = JSON.parseObject(json, LoginUser.class);
        String token = tokenService.createToken(loginUser);
        Map<String, Object> result = Maps.newHashMap();
        result.put("token", token);
        result.put("user", loginUser.getUser());

        httpServletResponse.setContentType(ContentType.JSON.toString());
        AjaxResult  ajaxResult = AjaxResult.success(result);
        httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult));
    }
}

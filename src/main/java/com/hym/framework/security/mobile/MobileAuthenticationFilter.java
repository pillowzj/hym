package com.hym.framework.security.mobile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.exception.BaseException;
import com.hym.common.exception.PFException;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import com.hym.framework.storage.Storage;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.User;
import com.hym.project.login.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于用户认证的filter，但是真正的认证逻辑会委托给{@link MobileAuthenticationManager}
 * @author lijun kou
 * @Date 03012020
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private Storage storage;

    public MobileAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//        String cellPhone = httpServletRequest.getParameter("cellPhone");
//        String verifyCode = httpServletRequest.getParameter("verifyCode");

        String cellPhone = "";
        String verifyCode = "";

        try {
            RequestData requestData = this.storage.getRequestData(httpServletRequest);
            JSONObject reqbody = JSON.parseObject(requestData.getData());
            cellPhone = reqbody.getString("cellPhone");
            verifyCode =reqbody.getString("verifyCode");

        } catch (PFException e) {
            e.printStackTrace();
            AjaxResult ajaxResult = AjaxResult.error("参数有误");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult));
            //throw new BaseException(StringUtils.format("code is null"));
            return null;
        }


        if (StringUtils.isBlank(cellPhone)) {
            throw new BaseException(StringUtils.format("cellPhone is null"));
        }

        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            throw new BaseException(StringUtils.format(" verify code is error"));
        }
        LoginUser loginUser = new LoginUser();
        User user = new User();
        user.setMobile(cellPhone);
        loginUser.setUser(user);
        return this.getAuthenticationManager().authenticate(new MobileAuthenticationToken(loginUser, null, null));
    }
}

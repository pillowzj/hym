package com.hym.framework.security.opeinid;

import com.hym.common.exception.BaseException;
import com.hym.common.utils.StringUtils;
import com.hym.framework.security.LoginUser;
import com.hym.project.domain.User;
import com.hym.project.login.WeChatConfig;
import com.hym.project.login.domain.WXOpenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 用于用户认证的filter，但是真正的认证逻辑会委托给{@link WxAuthenticationManager}
 * @author lijun kou
 * @Date 03012020
*/
public class WxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private WeChatConfig weChatConfig;

    public WxAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        System.out.println("----------WxAuthenticationFilter------------");
        String code = httpServletRequest.getParameter("Code");
        if(StringUtils.isBlank(code)){
            throw new BaseException(StringUtils.format("code is null"));
        }
        WXOpenInfo wxOpenInfo = weChatConfig.getOpenIdAndToken(code);
        User user = new User();
        user.setOpenid(wxOpenInfo.getOpenid());
        user.setSessionkey(wxOpenInfo.getSessionKey());
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        return this.getAuthenticationManager().authenticate(new WxAuthenticationToken(loginUser,null,null));
    }
}

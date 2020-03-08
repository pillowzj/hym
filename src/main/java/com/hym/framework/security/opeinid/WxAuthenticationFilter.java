package com.hym.framework.security.opeinid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hym.common.constant.Constants;
import com.hym.common.constant.WorkflowConstants;
import com.hym.common.exception.PFException;
import com.hym.common.utils.IdUtils;
import com.hym.common.utils.StringUtils;
import com.hym.framework.domain.RequestData;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.storage.Storage;
import com.hym.framework.web.domain.AjaxResult;
import com.hym.project.domain.Asset;
import com.hym.project.domain.User;
import com.hym.project.login.WeChatConfig;
import com.hym.project.login.domain.WXOpenInfo;
import com.hym.project.login.service.LoginUserService;
import com.hym.project.service.AssetService;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
* 用于用户认证的filter，但是真正的认证逻辑会委托给{@link WxAuthenticationManager}
 * @author lijun kou
 * @Date 03012020
*/
public class WxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private Storage storage;

    public WxAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

//        String code = httpServletRequest.getParameter("wxCode");
//        String name = httpServletRequest.getParameter("name");
//        String face = httpServletRequest.getParameter("face");
//        String cellPhone = httpServletRequest.getParameter("cellPhone");
//        String verifyCode = httpServletRequest.getParameter("verifyCode");
        String code ="";
        String name ="";
        String face = "";
        String cellPhone = "";
        String verifyCode ="";
        try {
            RequestData requestData = this.storage.getRequestData(httpServletRequest);
            JSONObject reqbody = JSON.parseObject(requestData.getData());
             code = reqbody.getString("wxCode");
             name =reqbody.getString("name");
             face = reqbody.getString("face");
             cellPhone = reqbody.getString("cellPhone");
             verifyCode = reqbody.getString("verifyCode");
        } catch (PFException e) {
            e.printStackTrace();
            AjaxResult ajaxResult = AjaxResult.error("参数有误");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult));
            //throw new BaseException(StringUtils.format("code is null"));
            return null;
        }


        //1 验证码验证
        if(StringUtils.isBlank(code)){
            AjaxResult ajaxResult = AjaxResult.error("验证码不能为空");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult));
            //throw new BaseException(StringUtils.format("code is null"));
            return null;
        }

        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
        if(StringUtils.isEmpty(vc)){
            AjaxResult ajaxResult = AjaxResult.error("验证码错误");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult));
            //throw new BaseException(StringUtils.format("code is null"));
            return null;
        }
        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
            AjaxResult ajaxResult = AjaxResult.error("验证码错误");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ajaxResult));
            //throw new BaseException(StringUtils.format("code is null"));
            return null;
        }

        WXOpenInfo wxOpenInfo = weChatConfig.getOpenIdAndToken(code);
        User user = userService.loginByCellPhone(cellPhone);
        if(StringUtils.isNull(user)){
            user =  userService.getOpenid(wxOpenInfo.getOpenid());
        }

        if(StringUtils.isNull(user)){
            user = new User();
            user.setId(IdUtils.fastSimpleUUID());
            user.setWxAvatarUrl(face);
            user.setWxNickname(name);
            user.setOpenid(wxOpenInfo.getOpenid());
            user.setSessionkey(wxOpenInfo.getSessionKey());
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
        }else{
            user.setSessionkey(wxOpenInfo.getSessionKey());
            userService.updateByPrimaryKeySelective(user);
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        return this.getAuthenticationManager().authenticate(new WxAuthenticationToken(loginUser,null,null));
    }
}

package com.hym.framework.security.pwd;


import com.hym.common.constant.Constants;
import com.hym.common.exception.CustomException;
import com.hym.common.exception.user.CaptchaException;
import com.hym.common.exception.user.CaptchaExpireException;
import com.hym.common.exception.user.UserPasswordNotMatchException;
import com.hym.common.utils.MessageUtils;
import com.hym.framework.manager.AsyncManager;
import com.hym.framework.manager.factory.AsyncFactory;
import com.hym.framework.redis.RedisCache;
import com.hym.framework.security.LoginUser;
import com.hym.framework.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.Resource;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */

public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码

     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String cellPhone) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + cellPhone;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
//        if (captcha == null)
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
//            throw new CaptchaExpireException();
//        }
//        if (!code.equalsIgnoreCase(captcha))
//        {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
//            throw new CaptchaException();
//        }
        return Authe(username);
    }
    public String login(String openid,String cellPhone,String verifyCode) {
        String verifyKey = "Constant.SMS_PREFIX" + cellPhone + verifyCode;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);

        //1 验证码验证
//        String vc = redisCache.getCacheObject("Constant.SMS_PREFIX" + cellPhone + verifyCode);
//        if (!vc.equals("Constant.SMS_PREFIX" + cellPhone + verifyCode)) {
//            throw new CaptchaException();
//            return AjaxResult.error(HttpStatus.BAD_REPWD, " verify is error");
//        }
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(cellPhone, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!captcha.equalsIgnoreCase(("Constant.SMS_PREFIX" + cellPhone + verifyCode)))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(cellPhone, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        return Authe(openid);
    }
    private String Authe(String username) {
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, ""));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}

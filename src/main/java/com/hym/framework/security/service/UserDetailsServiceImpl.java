package com.hym.framework.security.service;

import com.hym.project.domain.User;
import com.hym.project.login.service.LoginUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hym.common.utils.StringUtils;
import com.hym.framework.security.LoginUser;

/**
 * 用户验证处理
 *
 * @author hym
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private LoginUserService dlUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = dlUserService.getOpenid(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
//        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
//        {
//            log.info("登录用户：{} 已被删除.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
//        }
//        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
//        {
//            log.info("登录用户：{} 已被停用.", username);
//            throw new BaseException("对不起，您的账号：" + username + " 已停用");
//        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user)
    {
        return new LoginUser(user);
    }
}

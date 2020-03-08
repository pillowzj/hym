package com.hym.project.service.impl;

import com.hym.project.domain.User;
import com.hym.project.service.LoginUserService;
import com.hym.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User record) {
        userMapper.insert(record);
        return 0;
    }

    @Override
    public User getOpenid(String openid) {
        return userMapper.selectByOpenID(openid);
    }

    @Override
    public User loginByCellPhone(String cellPhone){
        return userMapper.loginByCellPhone(cellPhone);
    }
}

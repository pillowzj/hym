package com.hym.project.service.impl;

import com.hym.project.domain.User;
import com.hym.project.mapper.UserMapper;
import com.hym.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginByCellPhone(String cellPhone){
        return userMapper.loginByCellPhone(cellPhone);
    }
    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getOpenid(String openid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public String getUserHMY(Map map) {
        return userMapper.selectUserHMYByStatus(map);
    }

    @Override
    public String getInviteCode(String inviteCode) {
        return userMapper.selectByInviteCode(inviteCode);
    }
}

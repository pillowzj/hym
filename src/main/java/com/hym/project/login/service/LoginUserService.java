package com.hym.project.login.service;

import com.hym.project.domain.User;

public interface LoginUserService {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    User getOpenid(String openid);
    User loginByCellPhone(String cellPhone);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}

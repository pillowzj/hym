package com.hym.project.login.service;

import com.hym.project.domain.User;

public interface LoginUserService {

    int insert(User record);
    User getOpenid(String openid);
    User loginByCellPhone(String cellPhone);

}

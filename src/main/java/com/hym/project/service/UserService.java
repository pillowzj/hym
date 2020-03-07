package com.hym.project.service;

import com.hym.project.domain.User;

import java.util.Map;

public interface UserService {


    int insert(User record);


    User selectByPrimaryKey(String id);


    User getOpenid(String openid);

    int updateByPrimaryKeySelective(User record);

    User loginByCellPhone(String cellPhone);

    String getUserHMY(Map map);

    String getInviteCode(String inviteCode);
}

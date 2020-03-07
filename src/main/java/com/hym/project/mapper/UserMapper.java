package com.hym.project.mapper;

import com.hym.project.domain.User;

import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenID(String openid);
    User loginByCellPhone(String cellPhone);
    String selectUserHMYByStatus(Map map);
    String selectByInviteCode(String inviteCode);
}
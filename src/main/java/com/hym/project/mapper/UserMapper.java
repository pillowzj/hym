package com.hym.project.mapper;

import com.hym.project.domain.User;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenID(String openid);

    String selectUserHMYByStatus(Map map);
}
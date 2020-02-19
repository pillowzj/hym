package com.hym.project.service;

import com.hym.project.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface UserService {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);


    User getOpenid(String openid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String getUserHMY(Map map);
}

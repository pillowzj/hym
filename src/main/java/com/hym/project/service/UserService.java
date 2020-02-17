package com.hym.project.service;

import com.hym.project.domain.User;
import org.apache.ibatis.annotations.Select;

public interface UserService {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    @Select("select * from user where openid=#{openid}")
    User selectByOpenID(String openid);
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}

package com.hym.project.mapper;

import com.hym.project.domain.SignIn;

public interface SignInMapper {
    int deleteByPrimaryKey(String id);

    int insert(SignIn record);

    int insertSelective(SignIn record);

    SignIn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SignIn record);

    int updateByPrimaryKey(SignIn record);
}
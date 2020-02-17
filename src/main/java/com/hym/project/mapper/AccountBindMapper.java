package com.hym.project.mapper;

import com.hym.project.domain.AccountBind;

public interface AccountBindMapper {
    int deleteByPrimaryKey(String id);

    int insert(AccountBind record);

    int insertSelective(AccountBind record);

    AccountBind selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccountBind record);

    int updateByPrimaryKey(AccountBind record);
}
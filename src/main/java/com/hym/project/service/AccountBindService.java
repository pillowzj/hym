package com.hym.project.service;

import com.hym.project.domain.AccountBind;

public interface AccountBindService {
    int deleteByPrimaryKey(String id);

    int insert(AccountBind record);

    int insertSelective(AccountBind record);

    AccountBind selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccountBind record);

    int updateByPrimaryKey(AccountBind record);
}

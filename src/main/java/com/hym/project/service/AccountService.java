package com.hym.project.service;

import com.hym.project.domain.Account;

public interface AccountService {

    Account selectByPrimaryKey(String id);

    int insert(Account record);

    int updateByPrimaryKeySelective(Account record);

    Account getUserHYMByPrimaryKey(String id);
}

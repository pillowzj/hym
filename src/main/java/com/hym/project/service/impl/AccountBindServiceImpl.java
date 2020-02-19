package com.hym.project.service.impl;

import com.hym.project.service.AccountBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBindServiceImpl implements AccountBindService {

    @Autowired
    private AccountBindMapper accountBindMapper;
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(AccountBind record) {
        return accountBindMapper.insert(record);
    }

    @Override
    public int insertSelective(AccountBind record) {
        return 0;
    }

    @Override
    public AccountBind selectByPrimaryKey(String id) {
        return accountBindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AccountBind record) {
        return accountBindMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AccountBind record) {
        return 0;
    }
}

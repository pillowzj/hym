package com.hym.project.service.impl;

import com.hym.common.utils.IdUtils;
import com.hym.project.domain.Account;
import com.hym.project.domain.Exchange;
import com.hym.project.mapper.ExchangeMapper;
import com.hym.project.service.AccountService;
import com.hym.project.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeMapper exchangeMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Exchange record) {
        record.setId(IdUtils.fastUUID());
        Account account = accountService.selectByPrimaryKey(record.getUid());
        // 伪代码 计算兑换后的cny
        account.setCny(account.getCny().add(record.getToken()));
        account.setToken(account.getToken().subtract(record.getToken()));
        accountService.updateByPrimaryKeySelective(account);
        return exchangeMapper.insert(record);
    }

    @Override
    public int insertSelective(Exchange record) {
        return 0;
    }

    @Override
    public Exchange selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public List<Exchange> selectByUid(String uid){
        return exchangeMapper.selectByUid(uid);

    }

    @Override
    public int updateByPrimaryKeySelective(Exchange record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Exchange record) {
        return 0;
    }
}

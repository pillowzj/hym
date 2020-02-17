package com.hym.project.service;

import com.hym.project.domain.Exchange;

import java.util.List;

public interface ExchangeService {

    int deleteByPrimaryKey(String id);

    int insert(Exchange record);

    int insertSelective(Exchange record);

    Exchange selectByPrimaryKey(String id);

    List<Exchange> selectByUid(String uid);

    int updateByPrimaryKeySelective(Exchange record);

    int updateByPrimaryKey(Exchange record);
}

package com.hym.project.mapper;

import com.hym.project.domain.Exchange;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExchangeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Exchange record);

    int insertSelective(Exchange record);

    Exchange selectByPrimaryKey(String id);

    @Select("select * from exchange where uid=#{uid}")
    List<Exchange> selectByUid(String uid);

    int updateByPrimaryKeySelective(Exchange record);

    int updateByPrimaryKey(Exchange record);
}
package com.hym.project.mapper;

import com.hym.project.domain.TransOrder;

import java.util.List;

public interface TransOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(TransOrder record);

    TransOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TransOrder record);

    List<TransOrder> selectById(String sellerId);
}
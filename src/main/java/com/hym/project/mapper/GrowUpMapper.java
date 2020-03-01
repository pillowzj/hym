package com.hym.project.mapper;

import com.hym.project.domain.GrowUp;

public interface GrowUpMapper {
    int deleteByPrimaryKey(String id);

    int insert(GrowUp record);

    int insertSelective(GrowUp record);

    GrowUp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GrowUp record);

    int updateByPrimaryKey(GrowUp record);
}
package com.hym.project.mapper;

import com.hym.project.domain.Asset;

public interface AssetMapper {
    int deleteByPrimaryKey(String id);

    int insert(Asset record);

    int insertSelective(Asset record);

    Asset selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Asset record);

    int updateByPrimaryKey(Asset record);
}
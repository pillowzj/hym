package com.hym.project.service;

import com.hym.project.domain.Asset;

public interface AssetService {

    Asset selectByPrimaryKey(String id);

    int insert(Asset record);

    int updateByPrimaryKeySelective(Asset record);

    Asset getUserHYMByPrimaryKey(String id);
}
